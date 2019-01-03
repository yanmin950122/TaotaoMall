package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
@Autowired
private TbItemMapper mapper;
@Autowired
private TbItemDescMapper itemDescMapper;
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//1.设置分页的信息 使用pagehelper
		if(page==null)page=1;
		if(rows==null)rows=30;
		PageHelper.startPage(page, rows);
		//2.注入mapper
		//3.创建example 对象 不需要设置查询条件
		TbItemExample example = new TbItemExample();
		//4.根据mapper调用查询所有数据的方法
		List<TbItem> list = mapper.selectByExample(example);
		//5.获取分页的信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		//6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int)info.getTotal());
		result.setRows(info.getList());
		//7.返回
		return result;
	}
	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		// TODO Auto-generated method stub
//		1、生成商品id
		long id = IDUtils.genItemId();

//		2、补全TbItem对象的属性
		item.setId(id);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
//		3、向商品表插入数据
		mapper.insert(item);
//		4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
//		5、补全TbItemDesc的属性
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
//		6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
//		7、TaotaoResult.ok()
		
		return TaotaoResult.ok();
	}
	@Override
	public TbItemDesc listItemDesc(Long itemID) {
		
		return itemDescMapper.selectByPrimaryKey(itemID);
	}
	@Override
	public TaotaoResult deleteItem(String ids) {
		// TODO Auto-generated method stub
		//删除操作即把item的status置为3
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for (String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbItemExample e = new TbItemExample();
			TbItemExample.Criteria c = e.createCriteria();
			c.andIdIn(values);

			List<TbItem> list = mapper.selectByExample(e);
			if (list != null && list.size() > 0) {
				TbItem item = list.get(0);
				item.setStatus((byte) 3);
				mapper.updateByExample(item, e);
			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


}
