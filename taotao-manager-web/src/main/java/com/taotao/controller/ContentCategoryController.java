package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
/*
 * 内容分类的处理
 */
@Controller
@RequestMapping("/content")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService serivce;
	/**
	 * 
	 * getContentCategoryList:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 	
	 * 	url : '/content/category/list',
		animate: true,
		method : "GET",
		参数:id
	 * @author BetterMan 
	 * @return 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentID){
		//1.引入服务
		//2.注入服务
		//3.调用
		return serivce.getContentCategoryList(parentID);
		
	}
	/*
	 * 添加节点
	 */
	@RequestMapping(value = "/category/create", method = RequestMethod.POST)
	@ResponseBody 
	public TaotaoResult createContentCategory(Long parentId, String name) {
		
		return serivce.createContentCategory(parentId, name);
		
	}
	/*
	 * 更新节点
	 */
	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	@ResponseBody 
	public TaotaoResult updateContentCategory(Long id, String name) {
		
		return serivce.updateContentCategory(id, name);
		
	}
	/*
	 * 删除节点
	 */
	@RequestMapping(value = "/category/delete/", method = RequestMethod.POST)
	@ResponseBody 
	public TaotaoResult deleteContentCategory(Long parentId,Long id) {
		return serivce.deleteContentCategory(parentId, id);
	}
}
