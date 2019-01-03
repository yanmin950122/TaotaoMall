package com.taotao.content.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper mapper;
	
	@Autowired
	private JedisClient client; 
	
	@Value("CONTENT_KEY")
	private String CONTENT_KEY;
	
	
	@Override
	public TaotaoResult saveContent(TbContent content) {
		// TODO Auto-generated method stub
		//1.注入mapper
		//2.补全其他属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		//3.插入内容表中
		mapper.insertSelective(content);
		try {
			//当添加内容时，需要清空此分类所属的分类下所有缓存
			client.hdel(CONTENT_KEY, content.getCategoryId() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	@Override
	public EasyUIDataGridResult getContentList(Integer page, Integer rows) {
		//1.设置分页的信息 使用pagehelper
				if(page==null)page=1;
				if(rows==null)rows=30;
				PageHelper.startPage(page, rows);
				//2.注入mapper
				//3.创建example 对象 不需要设置查询条件
				TbContentExample example = new TbContentExample();
				//4.根据mapper调用查询所有数据的方法
				List<TbContent> list = mapper.selectByExample(example);
				//5.获取分页的信息
				PageInfo<TbContent> info = new PageInfo<>(list);
				//6.封装到EasyUIDataGridResult
				EasyUIDataGridResult result = new EasyUIDataGridResult();
				result.setTotal((int)info.getTotal());
				result.setRows(info.getList());
				//7.返回
				return result;
	}
	@Override
	public TaotaoResult deleteContent(String ids) {
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for(String id : idsArray) {
				values.add(Long.parseLong(id));
				try {
					//当删除内容时，需要清空此分类所属的分类下所有缓存
					client.hdel(CONTENT_KEY, id);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TbContentExample e = new TbContentExample();
			TbContentExample.Criteria c = e.createCriteria();
			c.andIdIn(values);
			mapper.deleteByExample(e);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		// TODO Auto-generated method stub
		//添加缓存不能影响正常的业务逻辑
		//判断redis中是否有数据，如果有，直接从redis中获取数据 返回
		//如果存在，说明有缓存，从redis数据库中获取下的所有内容
		try {
			String jsonStr = client.hget(CONTENT_KEY, categoryId+"");
			if(StringUtils.isNotBlank(jsonStr)) {
				return JsonUtils.jsonToList(jsonStr, TbContent.class);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//1.注入mapper
		//2.创建example
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		//3.设置查询的条件
		criteria.andCategoryIdEqualTo(categoryId); //select * from tbcontent where category_id = ?
		//4.执行查询
		//返回
		List<TbContent> list = mapper.selectByExample(example);
		//将数据写入到redis数据库中
		//1.注入jedisClient
		//调用方法，写入redis key field value
		try {
			//没有缓存
			client.hset(CONTENT_KEY, categoryId.toString(), JsonUtils.objectToJson(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
