package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper mapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	//消息队列
	@Autowired
	private JmsTemplate jmsTemplate; 
	@Autowired
	private JedisClient client;
	//Resource通过名称进行匹配
	//Autowired通过类型进行匹配
	@Resource(name = "topicDestination")
	private Destination destination;
	
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	
	
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
	public long saveItemToSQ(TbItem item, String desc) {
		// TODO Auto-generated method stub
//		1、生成商品id
		final long id = IDUtils.genItemId();
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
		return id;
	}
	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		final long id = saveItemToSQ(item, desc);
		if(id != -1) {
			//添加发送消息的业务逻辑
			//使用jsmTemplate匿名内部类发送消息
			jmsTemplate.send(destination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					//发送的消息
					return session.createTextMessage(id+"");
				}
			});
		}
		return TaotaoResult.ok();
	}
	@Override
	public TbItemDesc getItemDescById(Long itemID) {
		
		try {
			//添加缓存
			//1.从缓存中获取数据，如果有，直接返回
			String jsonStrItem = client.get(ITEM_INFO_KEY+":"+itemID+":ItemDESC");
			if(StringUtils.isNotEmpty(jsonStrItem)) {
				//重新设置商品描述的有效期
				//有缓存
				//System.out.println("有缓存");
				client.expire(ITEM_INFO_KEY+"ITEM_INFO:"+itemID+":ItemDESC", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonStrItem, TbItemDesc.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//如果没有查到数据 从数据库中查询
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemID);
		//添加缓存
		//3.添加缓存到redis数据库中
		//不能影响正常的业务逻辑，用try catch包裹
			try {
				 	//3.1注入jedisclient
					//ITEM_INFO:123456:BASE
					//ITEM_INFO:123456:DESC
					client.set(ITEM_INFO_KEY+":"+itemID+":ItemDESC", JsonUtils.objectToJson(itemDesc));
					//设置缓存的有效期
					client.expire(ITEM_INFO_KEY+"ITEM_INFO:"+itemID+":ItemDESC", ITEM_INFO_KEY_EXPIRE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return itemDesc;
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
			//添加发送消息的业务逻辑
			//发送要删除商品的ID
//			for (String id : idsArray) {
//				//使用jsmTemplate匿名内部类发送消息
//				jmsTemplate.send(destination, new MessageCreator() {
//					@Override
//					public Message createMessage(Session session) throws JMSException {
//						//发送的消息
//						return session.createTextMessage(id);
//					}
//				});
//			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public TbItem getItemById(Long itemId) {
		
		try {
			//添加缓存
			//1.从缓存中获取数据，如果有，直接返回
			String jsonStrItem = client.get(ITEM_INFO_KEY+":"+itemId+":Item");
			if(StringUtils.isNotEmpty(jsonStrItem)) {
				//重新设置商品的有效期
				client.expire(ITEM_INFO_KEY+"ITEM_INFO:"+itemId+":Item", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonStrItem, TbItem.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//2.如果没有，从数据库中查询
		
		
		//2.1.注入商品mapper
		//2.2.调用方法
//		TbItemExample example = new TbItemExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andIdEqualTo(itemId);
//		List<TbItem> list = mapper.selectByExample(example);
		//返回tbitem
		TbItem tbItem = mapper.selectByPrimaryKey(itemId);
		//3.添加缓存到redis数据库中
		//不能影响正常的业务逻辑，用try catch包裹
			try {
				//3.1注入jedisclient
				//ITEM_INFO:123456:BASE
				//ITEM_INFO:123456:DESC
				client.set(ITEM_INFO_KEY+":"+itemId+":Item", JsonUtils.objectToJson(tbItem));
				//设置缓存的有效期
				client.expire(ITEM_INFO_KEY+"ITEM_INFO:"+itemId+":Item", ITEM_INFO_KEY_EXPIRE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return tbItem;
	}
	


}
