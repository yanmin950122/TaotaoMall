package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 全局异常处理器的类
 * ClassName: GlobalExceptionResolver <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2019年1月3日 上午12:00:28 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//1.日志写入到日志文件
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		//2.及时通知开发人员 发邮件 ，通过第三方
		System.out.println("发短信");
		//3.给用户友好提示：您的网络有异常，请重试
		ModelAndView modelAndView = new ModelAndView();
		//设置视图的信息
		//设置模型数据
		modelAndView.setViewName("error/exception"); //不需要配置后缀，有视图解析器
		modelAndView.addObject("message", "您的网络有异常，请重试!");
		return modelAndView;
	}

}
