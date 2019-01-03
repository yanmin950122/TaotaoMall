package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 处理内容表相关的
 * ClassName: ContentController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月30日 下午2:53:37 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService service;
	//返回值是一个json
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveContent(TbContent tContent) {
		
		//1.引入服务
		//2.注入服务
		//3.调用
		return service.saveContent(tContent);
	}
	
	@RequestMapping(value = "/query/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page, Integer rows) {
		return service.getContentList(page, rows);
		
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		return service.deleteContent(ids);
		
	}
}
