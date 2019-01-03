package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.TestService;


/**
 * 测试使用的Controller 查询当前的时间
 * ClassName: TestController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月4日 下午9:41:27 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
@Controller
public class TestController {
	@Autowired
	private TestService testservice;
	
	@RequestMapping("/test/qureyNow")
	@ResponseBody
	public String queryNow(){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return testservice.queryNow();
	}
}
