package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.sso.service.UserLoginService;

@Controller
public class UserLoginController {
	
	@Autowired
	private UserLoginService loginService;
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	/**
	 * url：/user/login
	 * login:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		//1.引入服务
		//2.注入服务
		//3.调用服务
		TaotaoResult result = loginService.login(username, password);
		//4.设置token到cookie，使用工具类 cookie需要跨域
		if(result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, TT_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	/**
	 * 从Token中获取用户数据，
	 * getUserByToken:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param token
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult getUserByToken(@PathVariable String token) {
		//调用服务
		return loginService.getUserByToken(token);
	}
	@RequestMapping(value = "/user/logout/{token}", method = RequestMethod.GET)
	@ResponseBody
	public String delUserByToken(@PathVariable String token,String callback) {
		// 调用服务
		//return loginService.delUserByToken(token);
		// 判断是否是Jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			// 如果是jsonp 需要拼接 类似于fun({id:1});
			TaotaoResult result = loginService.getUserByToken(token);
			String jsonpstr = callback + "(" + JsonUtils.objectToJson(result) + ")";
			return jsonpstr;
		}
		// 如果不是jsonp
		// 1.调用服务
		TaotaoResult result = loginService.getUserByToken(token);
		return JsonUtils.objectToJson(result);
	}
}
