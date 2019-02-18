package com.taotao.sso.service;
/**
 * 用户注册的接口
 * ClassName: UserRegisterService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2019年1月11日 下午7:56:56 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserRegisterService {
	/**
	 * 根据参数和类型进行校验
	 * checkData:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param param	要校验的数据
	 * @param type 1	2	3	分别代表：username，phone，email
	 * @return 
	 * @since JDK 1.8
	 */
	public TaotaoResult checkData(String param, Integer type);
	/**
	 * 注册用户
	 * register:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param user
	 * @return 
	 * @since JDK 1.8
	 */
	public TaotaoResult register(TbUser user);
}
