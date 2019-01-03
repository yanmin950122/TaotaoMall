package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面
 * ClassName: PgeController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月5日 下午1:54:44 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
@Controller
public class PgeController {
	@RequestMapping("/")
	public String showIndex() {
		return "index"; 
	}
	//显示商品的查询界面
	// url: / item-list
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
