package com.taotao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;

@Controller
public class OrderController {
	@Autowired
	private UserLoginService loginservice;
	
	@Autowired
	private CartService cartservice;
	
	@Autowired
	private OrderService orderservice;
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	/**
	 * url:/order/order-cart.html
	 * 参数：没有参数，但需要用户的id  从cookie中获取token 调用SSO的服务获取用户的ID
	 * 返回值：逻辑视图 （订单的确认页面）
	 */
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request){
		//System.out.println(request.getAttribute("USER_INFO"));
//		//1.从cookie中获取用户的token 
//		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
//		if(StringUtils.isNotBlank(token)){
//			//2.调用SSO的服务获取用户的信息
//			TaotaoResult result = loginservice.getUserByToken(token);
//			if(result.getStatus()==200){
//				//3.必须是用户登录了才展示
//				//4.展示用户的送货地址   根据用户的ID查询该用户的配送地址。静态数据
//				//5.展示支付方式    从数据库中获取支付的方式。静态数据
//				//6.调用购物车服务从redis数据库中获取购物车的商品的列表
//				TbUser user = (TbUser)result.getData();
//				List<TbItem> cartList = cartservice.getCartList(user.getId());
//				//7.将列表 展示到页面中(传递到页面中通过model)
//				request.setAttribute("cartList", cartList);
//			}
//		}
		
		TbUser user =(TbUser) request.getAttribute("USER_INFO");
		List<TbItem> cartList = cartservice.getCartList(user.getId());
		//7.将列表 展示到页面中(传递到页面中通过model)
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	/**
	 * url:/order/create
	 * 参数：表单使用orderinfo来接收
	 *返回值：逻辑视图
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(HttpServletRequest request,OrderInfo info){
		//1.引入服务
		//2.注入服务
		//3.调用
		//查询用户的信息  设置到info中
		TbUser user = (TbUser)request.getAttribute("USER_INFO");
		info.setUserId(user.getId());
		info.setBuyerNick(user.getUsername());
		TaotaoResult result = orderservice.createOrder(info);
		request.setAttribute("orderId", result.getData());
		System.out.println(">>>>>"+info.getPayment());
		request.setAttribute("payment", info.getPayment());
		DateTime dateTime = new DateTime();
		DateTime plusDays = dateTime.plusDays(3);//加3天
		request.setAttribute("date", plusDays.toString("yyyy-MM-dd"));
		return "success";
	}
}
