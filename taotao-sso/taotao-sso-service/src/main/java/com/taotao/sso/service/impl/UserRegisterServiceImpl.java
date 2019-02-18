package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserRegisterService;
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public TaotaoResult checkData(String param, Integer type) {
		//1.注入mapper
		//2.根据参数动态的生成查询的条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(1 == type) {
			if(StringUtils.isEmpty(param)) {
				return TaotaoResult.ok(false);
			}
			//userName
			criteria.andUsernameEqualTo(param);
			
		}else if(2 == type){
			//phone
			criteria.andPhoneEqualTo(param);
		}else if(3 == type) {
			//email
			criteria.andEmailEqualTo(param);
			
		}else {
			//是非法的参数
			//return 非法
			//Http 400 bad request 错误的请求
			return TaotaoResult.build(400, "非法的参数");
		}
		//3.调用mapper的查询方法 获取数据
		List<TbUser> list = userMapper.selectByExample(example);
		//4.1如果查询到了数据	---数据不可用false
		if(list != null && list.size() > 0) {
			return TaotaoResult.ok(false);
		}
		//4.2如果每查到数据	---数据可用true
		return TaotaoResult.ok(true);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		//1.注入mapper
		//2.校验数据
		//2.1 校验用户名和密码不能为空
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "用户名或密码不能为空.请校验数据后请再提交数据");
		}
		//2.2校验用户名是否被注册了
		TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
		if(!(boolean)taotaoResult.getData()) {
			//数据不可用
			return TaotaoResult.build(400, "用户名已存在");
		}
		//2.3校验电话号码是否已经被注册
		if(StringUtils.isNotBlank(user.getPhone())) {
			TaotaoResult taotaoResultPhone = checkData(user.getPhone(), 2);
			if(!(boolean)taotaoResultPhone.getData()) {
				//数据不可用
				return TaotaoResult.build(400, "手机号已经被注册");
			}
		}
		//2.4校验邮箱是否已经被注册了
		if(StringUtils.isNotBlank(user.getEmail())) {
			TaotaoResult taotaoResultEmail = checkData(user.getEmail(), 3);
			if(!(boolean)taotaoResultEmail.getData()) {
				//数据不可用 
				return TaotaoResult.build(400, "邮箱已经被注册");
			}
		}
		//3.如果校验成功	补全其他的属性
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//4.对密码进行MD5加密
		String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Password);
		//5.插入数据
		//非空才插入
		userMapper.insertSelective(user);
		//6.返回TaotaoResult
		return TaotaoResult.ok();
	}

}
