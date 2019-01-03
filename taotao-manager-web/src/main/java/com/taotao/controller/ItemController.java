package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	// url : /item/list
	// method : get
	// 参数： page, rows
	//返回值： json
	@RequestMapping(value = "/item/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemService.getItemList(page, rows);
		
		
	}
	/**
	 * 保存商品
	 * saveItem:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param item
	 * @param desc
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping("/item/save")
	public TaotaoResult saveItem(TbItem item, String desc) {
		return itemService.saveItem(item, desc);
	}
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult delItem(String ids) {
		
		return itemService.deleteItem(ids);
	}
	/**
	 * 修改商品
	 * getItem:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param ItemID
	 * @return 
	 * @since JDK 1.8
	 */
//	@RequestMapping("/rest/page/item-edit")
//	public TbItem getItem(@RequestParam(value="id", defaultValue="0")Long ItemID) {
//		
//		return itemService.getItem(ItemID);
//		
//	}
	/**
	 * 加载商品描述
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TbItemDesc getItemDesc(@PathVariable Long id) {
		
		TbItemDesc itemDesc = itemService.listItemDesc(id);
		//System.out.println(itemDesc.getItemId() +"="+ itemDesc.getItemDesc());
		return itemDesc;
		
	}
	
	@RequestMapping("/rest/item/update")
	public String showPage(@PathVariable String page) {
		return "item-edit";
	}
	
}
