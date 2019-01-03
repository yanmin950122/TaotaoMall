package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
/*
 * 内容分类
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper categoryMapper; 
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentID) {
		// TODO Auto-generated method stub
		//1.注入mapper
		//2.创建example
		TbContentCategoryExample example = new TbContentCategoryExample();
		//3.设置条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentID); //select * from tbContentCategory where parent_id = ?
		//4.执行查询
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		//5.转成 EasyUITreeNode 列表
		//定义一个List<EasyUITreeNode>
		List<EasyUITreeNode> listEasy = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent()? "closed": "open");
			node.setText(tbContentCategory.getName());//分类名称
			listEasy.add(node);
		}
		//6.返回
		return listEasy;
	}
	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		//1.构建对象 补全其他的属性
		TbContentCategory category = new TbContentCategory();
		//2.插入contentCategory数据
		category.setParentId(parentId);
		category.setName(name);
		category.setIsParent(false);//新增的节点都是叶子节点
		category.setStatus(1);
		category.setCreated(new Date());
		category.setUpdated(category.getCreated());
		category.setSortOrder(1);
		
		//3.返回taotaoResult包含分类内容的id	需要主键返回
		category.setId((long) categoryMapper.insertSelective(category));
		//判断如果要添加节点的父节点本身是叶子节点	需要更新其为父节点
		TbContentCategory categoryFather = categoryMapper.selectByPrimaryKey(parentId);
		if(!categoryFather.getIsParent()) {
			categoryFather.setIsParent(true);
			categoryMapper.updateByPrimaryKeySelective(categoryFather);//更新节点的is_parent属性为true
		}
		
		return TaotaoResult.ok(category);
	}
	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		// TODO Auto-generated method stub
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		categoryMapper.updateByPrimaryKeySelective(category);
		return TaotaoResult.ok(category);
	}
	@Override
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {

		categoryMapper.deleteByPrimaryKey(id);
		//判断父节点下是否还有其他节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		//获取父节点
		TbContentCategory parentContent = categoryMapper.selectByPrimaryKey(parentId);
		if(list != null && list.size() > 0) {
			parentContent.setIsParent(true);
		}
		else {
			parentContent.setIsParent(false);
		}
		categoryMapper.updateByPrimaryKeySelective(parentContent);
		return TaotaoResult.ok();
	}
	

}
