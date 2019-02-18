package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserRegisterService;

@Controller
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;
	/**
	 * url：/user/check/{param}/{type}
	 * checkData:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param param
	 * @param type 1, 2, 3
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value="/user/check/{param}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
		//1.引入服务
		//2.注入
		//3.调用
		return userRegisterService.checkData(param, type);
	}
	/**
	 * 用户注册
	 * url：/user/register
	 * 
	 * register:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param user 表单的数据：username、password、phone、email
	 * @return 	json数据。TaotaoResult
	 * @since JDK 1.8
	 */
	@RequestMapping(value="/user/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		return userRegisterService.register(user);
	}
}
