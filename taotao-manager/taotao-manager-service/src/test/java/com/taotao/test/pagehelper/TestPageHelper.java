package com.taotao.test.pagehelper;

import java.util.List;

import javax.management.openmbean.TabularType;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {
	
	@Test
	public void testhelper(){
		
		//2.初始化spring 容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//3.获取mapper的代理对象
		TbItemMapper itemMapper = context.getBean(TbItemMapper.class);
		//1.设置分页信息
		PageHelper.startPage(1, 3);//3行  紧跟着的第一个查询才会被分页
		//4.调用mapper的方法查询数据
		TbItemExample example = new TbItemExample();//设置查询条件使用
		List<TbItem> list = itemMapper.selectByExample(example);//select * from tb_item;
		List<TbItem> list2 = itemMapper.selectByExample(example);//select * from tb_item;
		
		
		//取分页信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		
		System.out.println("第一个分页的list的集合长度"+list.size());
		System.out.println("第二个分页的list的集合长度"+list2.size());
		
		//5.遍历结果集  打印
		System.out.println("查询的总记录数数："+info.getTotal());
		
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getId()+"》》》mingch>>"+tbItem.getTitle());
		}
		
		
		
	}
}
