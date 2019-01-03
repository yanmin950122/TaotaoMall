package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

@Controller
public class ImportAllItems {

	@Autowired
	private SearchService searchService;
	/**
	 * 导入所有的商品数据到索引库中
	 * importAll:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @return
	 * @throws Exception 
	 * @since JDK 1.8
	 */
	@RequestMapping("/index/importAll")
	@ResponseBody
	public TaotaoResult importAll() throws Exception {
		
		//1.引入服务
		//2.注入服务
		
		//3.调用服务
		
		
		return searchService.importAllSearchItems();
		
	}
	
}
