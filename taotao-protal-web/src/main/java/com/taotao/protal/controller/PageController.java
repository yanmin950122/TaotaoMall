package com.taotao.protal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.protal.pojo.Ad1Node;

/*
 * 展示首页
 */
@Controller
public class PageController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	@Value("${AD1_HEIGHT_B}")
	private String heightB;
	@Value("${AD1_HEIGHT}")
	private String height;
	@Value("${AD1_WIDTH_B}")
	private String widthB;
	@Value("${AD1_WIDTH}")
	private String width;
	
	
	
	
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		//引入服务
		//注入服务
		//添加业务逻辑，根据内容分类的id查询内容列表
		List<TbContent> contentList = contentService.getContentListByCatId(categoryId);
		//转换成自定义的POJO(Ad1Node)列表
		List<Ad1Node> nodeList = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setHeight(height);
			node.setHeightB(heightB);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setWidth(width);
			node.setWidthB(widthB);
			nodeList.add(node);
		}
		//传递数据到JSP
		model.addAttribute("ad1", JsonUtils.objectToJson(nodeList));
		return "index";
	}
}
