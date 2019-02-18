package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 跳转页面
 * ClassName: PageController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2019年1月12日 下午6:20:42 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page, String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return page;
	}
}
