package com.taotao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${EXPIRE_TIME}")
	private String EXPIRE_TIME;
	
	@Override
	public TaotaoResult login(String userName, String passWord) {
		//1.注入mapper
		//2.校验用户名和密码是否为空
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(passWord)) {
			return TaotaoResult.build(400, "用户名或密码为空");
		}
		//3.先校验用户名，再校验密码 先example后criteria
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(userName);
		//密码用MD5加密过，所以要先加密，后提交
		String md5Password = DigestUtils.md5DigestAsHex(passWord.getBytes());
		criteria.andPasswordEqualTo(md5Password);
		List<TbUser> list = userMapper.selectByExample(example);
		//4.如果校验成功：
		if(list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名或密码错误，请重新登陆");
		}
		//4.1生成UUID	token	设置token的有效期来模拟session 用户的数据存放在redis中（key：token，value：用户数据的json）
		String token = UUID.randomUUID().toString();
		//存放用户数据到redis中，使用jedis客户端,为了管理方便，加前缀"SESSION:"
		TbUser user = list.get(0);
		//设置密码为空
		user.setPassword(null);
		jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
		//设置过期时间	模拟SESSION
		jedisClient.expire(USER_INFO+":"+token, Integer.parseInt(EXPIRE_TIME));
		//4.2设置Cookie存储token 表现层设置
		//5.把token设置到cookie中 表现层设置
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		//1.注入JedisClient
		//2.根据token查询用户信息（JSON）的方法 get方法
		String userJson = jedisClient.get(USER_INFO+":"+token);
		//3.判断是否能够查询到
		if(StringUtils.isNotBlank(userJson)) {
			TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
			//重新设置过期时间
			jedisClient.expire(USER_INFO+":"+token, Integer.parseInt(EXPIRE_TIME));
			//5.如果查询到了 需要返回TaotaoResult200  包含用户的信息 用户信息转成对象
			return TaotaoResult.ok(user);
		}
		
		//4.如果查询不到，返回400
		return TaotaoResult.build(400, "用户已过期");
	}

	@Override
	public TaotaoResult delUserByToken(String token) {
		//1.注入JedisClient
		//2.根据token查询用户信息（JSON）的方法 get方法
		String userJson = jedisClient.get(USER_INFO+":"+token);
		//3.判断是否能够查询到
		if(StringUtils.isNotBlank(userJson)) {
			jedisClient.hdel(USER_INFO+":"+token);
			return TaotaoResult.ok();
		}
		//4.如果查询不到，返回400
		return TaotaoResult.build(400, "用户已过期");
	}
}
