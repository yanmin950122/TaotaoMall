package com.taotao.sso.service;
/**
 * 用户登录接口
 * ClassName: UserLoginService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2019年1月12日 上午10:05:45 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */

import com.taotao.common.pojo.TaotaoResult;

public interface UserLoginService {
	/**
	 * 根据用户名和密码登陆
	 * login:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param userName
	 * @param passWord
	 * @return json数据，TaotaoResult。登陆成功返回200，并且包含一个token
	 * 								登陆失败，返回400
	 * @since JDK 1.8
	 */
	public TaotaoResult login(String userName, String passWord);
	/**
	 * 根据token获取用户的信息
	 * getUserByToken:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param token
	 * @return TaotaoResult	应该包含用户的信息
	 * @since JDK 1.8
	 */
	public TaotaoResult getUserByToken(String token);
	/**
	 * 根据Token删除用户已登录信息
	 * delUserByToken:(这里用一句话描述这个方法的作用). <br/> 
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
	public TaotaoResult delUserByToken(String token);
}
