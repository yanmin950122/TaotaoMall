package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.service.ItemService;
import com.taotao.sso.service.UserLoginService;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserLoginService userloginservice;
	@Autowired
	private CartService cartservice;
	
	
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Value("${TT_CART_KEY}")
	private String TT_CART_KEY;
	
	// /cart/add/149204693130763.html?num=4
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		//1.从cookie中获取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//2.根据token调用SSO服务，获取用户的信息
		TaotaoResult taotaoResult = userloginservice.getUserByToken(token);
		//3.1 首先调用商品服务的方法，获取商品数据TbItem
		TbItem item = itemService.getItemById(itemId);
		//3.判断，如果用户存在，则说明已经登陆成功
		if(200 == taotaoResult.getStatus()) {
			//3.2获取用户信息的UserId
			TbUser user = (TbUser)taotaoResult.getData();
			//3.3调用添加购物车的方法，将数据添加到redis中
			cartservice.addItemCart(item, num, user.getId());
		} else {
			// 5.如果没有登录 调用设置到cookie的方法
			// 先根据cookie获取购物车的列表
			List<TbItem> cartList = getCookieCartList(request);
			boolean flag = false;
			// 判断如果购物车中有包含要添加的商品 商品数量相加
			for (TbItem tbItem2 : cartList) {
				if (tbItem2.getId() == itemId.longValue()) {
					// 找到列表中的商品 更新数量
					tbItem2.setNum(tbItem2.getNum() + num);
					flag = true;
					break;
				}
			}
			if (flag == true) {
				// 如果找到对应的商品，更新数量后，还需要设置回cookie中
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
						true);
			} else {
				// 如果没有就直接添加到购物车
				// 调用商品服务
				// 设置数量
				item.setNum(num);
				// 设置图片为一张
				if (item.getImage() != null) {
					item.setImage(item.getImage().split(",")[0]);
				}
				// 添加商品到购物车中
				cartList.add(item);
				// 设置到cookie中
				CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
						true);
			}
		}

		return "cartSuccess";
		
	}
	// 展示购物车的列表
		@RequestMapping("/cart/cart")
		public String getCartList(HttpServletRequest request) {
			// 1.引入服务
			// 2.注入服务

			// 3.判断用户是否登录
			// 从cookie中获取用户的token信息
			String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
			// 调用SSO的服务查询用户的信息
			TaotaoResult result = userloginservice.getUserByToken(token);

			System.out.println(result.getData());
			// 获取商品的数据
			if (result.getStatus() == 200) {
				// 4.如果已登录，调用service的方法
				TbUser user = (TbUser) result.getData();
				List<TbItem> cartList = cartservice.getCartList(user.getId());
				System.out.println(cartList.size());
				request.setAttribute("cartList", cartList);
			} else {
				// 5.如果没有登录 调用cookie的方法 获取商品的列表
				List<TbItem> cartList = getCookieCartList(request);
				// 将数据传递到页面中
				request.setAttribute("cartList", cartList);
			}
			return "cart";
		}

		/**
		 * url:/cart/update/num/{itemId}/{num} 参数：商品的id 和更新后的数量 还有用户的id 返回值：json
		 */
		@RequestMapping("/cart/update/num/{itemId}/{num}")
		@ResponseBody
		public TaotaoResult updateItemCartByItemId(@PathVariable Long itemId, @PathVariable Integer num,
				HttpServletRequest request, HttpServletResponse response) {
			// 1.引入服务
			// 2.注入服务

			// 3.判断用户是否登录
			// 从cookie中获取用户的token信息
			String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
			// 调用SSO的服务查询用户的信息
			TaotaoResult result = userloginservice.getUserByToken(token);

			// 获取商品的数据
			if (result.getStatus() == 200) {
				// 4.如果已登录，调用service的方法
				TbUser user = (TbUser) result.getData();
				// 更新商品的数量
				cartservice.updateItemCartByItemId(user.getId(), itemId, num);
			} else {
				// 5.如果没有登录 调用cookie的方法 更新cookie中的商品的数量的方法
				updateCookieItemCart(itemId, num, request, response);
			}
			return TaotaoResult.ok();
		}

		/**
		 * url:/cart/delete/{itemId} 参数：用户的ID 还有商品的ID 返回值：string 逻辑视图
		 */
		// 删除购物车中的商品
		@RequestMapping("/cart/delete/{itemId}")
		public String deleteItemCartByItemId(@PathVariable Long itemId, HttpServletRequest request,
				HttpServletResponse response) {
			// 1.引入服务
			// 2.注入服务

			// 3.判断用户是否登录
			// 从cookie中获取用户的token信息
			String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
			// 调用SSO的服务查询用户的信息
			TaotaoResult result = userloginservice.getUserByToken(token);

			// 获取商品的数据
			if (result.getStatus() == 200) {
				// 4.如果已登录，调用service的方法
				TbUser user = (TbUser) result.getData();
				// 删除
				cartservice.deleteItemCartByItemId(user.getId(), itemId);
			} else {
				// 5.如果没有登录 调用cookie的方法 删除cookie中的商品
				deleteCookieItemCartByItemId(itemId, request, response);
			}
			return "redirect:/cart/cart.html";// 重定向
		}
	// -------------------------------------完美分割线------------------------------------------------------------
	// 获取购物车的列表
	public List<TbItem> getCookieCartList(HttpServletRequest request) {
		// 从cookie中获取商品的列表
		String jsonstr = CookieUtils.getCookieValue(request, TT_CART_KEY, true);// 商品的列表的JSON
		// 讲商品的列表的JSON转成 对象
		if (StringUtils.isNotBlank(jsonstr)) {
			List<TbItem> list = JsonUtils.jsonToList(jsonstr, TbItem.class);
			return list;
		}
		return new ArrayList<>();

	}
	/**
	 * 更新商品的数量
	 * 
	 * @param itemId
	 * @param num
	 */
	private void updateCookieItemCart(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 1.获取cookie中的购物车的商品列表
		List<TbItem> cartList = getCookieCartList(request);
		// 2.判断修改的商品是否在购物车的列表中
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			// 表示找到了要修改的商品
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(num);
				flag = true;
				break;
			}
		}
		if (flag == true) {
			// 3.如果存在 更新数量
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
					true);
		} else {
			// 4.如果不存在 不管啦。
		}
	}

	/**
	 * 删除cookie中的购物车的商品
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 */
	private void deleteCookieItemCartByItemId(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 1.从cookie中获取商品的列表
		List<TbItem> cartList = getCookieCartList(request);
		// 2.判断 商品是否存在于商品的列表中
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 找到要删除的商品
				cartList.remove(tbItem);
				flag = true;
				// break;
			}
		}
		if (flag == true) {
			// 3.如果存在，删除
			CookieUtils.setCookie(request, response, TT_CART_KEY, JsonUtils.objectToJson(cartList), 7 * 24 * 3600,
					true);
		}
		// 4.如果不存在，不管
	}

	public static void main(String[] args) {
		Long a = 129l;
		Long b = 129l;
		System.out.println(a == b);
		List list = new ArrayList<String>();
		list.add("一");
		list.add("二");
		list.add("三");
		list.add("四");
		for (Object o : list) {
			if ("二".equals(o.toString())) {
				list.remove(o);
			}
		}
	}
}
