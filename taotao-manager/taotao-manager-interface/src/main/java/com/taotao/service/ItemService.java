package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * 商品相关的处理的service
 * @title ItemService.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public interface ItemService {
	
	/**
	 *  根据当前的页码和每页 的行数进行分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDataGridResult getItemList(Integer page,Integer rows);
	/**
	 * 根据商品id查询商品
	 * getItem:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param itemId
	 * @return 
	 * @since JDK 1.8
	 */
	public TbItem getItemById(Long itemId);
	/**根据商品的基础数据 和商品的描述信息 插入商品（插入商品基础表  和商品描述表）
	 * @param item
	 * @param desc
	 * @return
	 */
	
	public TaotaoResult saveItem(TbItem item, String desc);
	/**根据商品的id查询商品
	 * @param item
	 * @param desc
	 * @return
	 */
//	public TbItem getItem(Long id);
	/**
	 * 根据商品ID返回商品所有信息
	 * getItemResult:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param itemID
	 * @return 
	 * @since JDK 1.8
	 */
//	public TaotaoResult getItemResult(Long itemID);
	/**
	 * 加载商品描述
	 * listItemDesc:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param itemID
	 * @return 
	 * @since JDK 1.8
	 */ 
	public TbItemDesc getItemDescById(Long itemID);
	/*
	 * 删除选择的商品
	 */
	public TaotaoResult deleteItem(String ids);
}
