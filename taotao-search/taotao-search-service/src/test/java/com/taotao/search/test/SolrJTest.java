package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	@Test
	public void add() throws Exception {
		//1.创建solrserver	建立连接 需要指定地址
		SolrServer solrServer = new HttpSolrServer("http://119.23.51.57:8080/solr");
		//2.创建solrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//3.向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title", "这是一个测试");
		//4.将文档提交到索引库中
		solrServer.add(document);
		//5.提交
		solrServer.commit();
	}
	
	@Test
	public void testQuery() throws Exception {
		//1.创建solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://119.23.51.57:8080/solr");
		//2.创建solrQuery对象	设置过滤条件，主查询条件  排序
		SolrQuery query = new SolrQuery();
		//3.设置查询的条件
		query.setQuery("item_title:阿尔卡特");
		query.addFilterQuery("item_price:[0 TO 30000000]");
		query.set("df", "item_title");
		//4.执行查询
		QueryResponse response = solrServer.query(query);
		//5.获取结果集
		SolrDocumentList list = response.getResults();
		System.out.println("查询的总记录数"+list.getNumFound());
		//6.遍历结果集 测试时打印
		for (SolrDocument solrDocument : list) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
		}
		
		
	}
	
}
