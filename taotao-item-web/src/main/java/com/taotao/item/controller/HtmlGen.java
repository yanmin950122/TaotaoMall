package com.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class HtmlGen {
	
	@Autowired
	private FreeMarkerConfigurer config;
	
	@RequestMapping("/genHtml")
	@ResponseBody
	public String gen() throws Exception{
		//生成静态页面
		//1.根据config  获取configuration对象
		Configuration configuration = config.getConfiguration();
		//2.设置模板文件	加载模板文件  相对路径"/WEB-INF/ftl/"
		Template template = configuration.getTemplate("template.ftl");
		//3.创建数据集 --》从数据库中获取
		Map map = new HashMap<>();
		map.put("springtestkey", "hello");
		//4.创建writer
		Writer writer = new FileWriter(new File("G:/server/freemarker/springTestFreeMark.html"));
		//5.调用方法输出
		template.process(map, writer);
		//6.关闭流
		writer.close();
		return "ok";
	}
}
