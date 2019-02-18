package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public TaotaoResult importAllSearchItems() throws Exception {
		//1.注入mapper
		//2.调用mapper的方法 查询所有的商品的数据
		List<SearchItem> list = itemMapper.getSearchItemList();
		//3.通过solrJ将数据写入到索引库中
			//3.1创建httpsolrserver
			//3.2创建solrinputdocument 将列表中的元素一个个放到索引库中
		for (SearchItem searchItem : list) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId().toString());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_desc());
			//添加到索引库
			solrServer.add(document);
		}
		//提交
		solrServer.commit();
		return TaotaoResult.ok();
	}

	@Override
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
		//1.创建solrQuery对象
		SolrQuery query = new SolrQuery();
		//2.设置主查询条件
		if(StringUtils.isNotBlank(queryString)) {
			query.setQuery(queryString);
		}else {
			query.setQuery("*:*");
		}
		//3.设置过滤条件	设置分页
		if(page == null) page = 1;
		if(rows == null) rows = 60;
		query.setStart((page - 1) * rows); //start = (page - 1) * rows
		query.setRows(rows);
		//设置默认的搜索域
		query.set("df", "item_keywords");
		//设置高亮
		query.setHighlight(true);
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("item_title");//设置高亮显示的域
		//4.调用dao的方法，返回的是SearchResult 只包含了商品的记录数和商品的列表
		SearchResult result = searchDao.search(query);
		//5.设置SearchResult的总页数
		long pageCount = 0l;
		pageCount = result.getRecordCount()/rows;
		if(result.getRecordCount()%rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		//6.返回
		
		return result;
	}
	@Override
	public TaotaoResult updateSearchItemById(Long itemId) throws Exception {
		return searchDao.updateSearchItemById(itemId);
	}

}
