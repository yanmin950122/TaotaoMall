package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {
	
	@Test
	public void testAdd() throws Exception {
		//1.创建SolrServer	集群的实现类
		CloudSolrServer cloudSolrServer = new CloudSolrServer("119.23.51.57:2181,119.23.51.57:2182,119.23.51.57:2183");
		//2.设置默认的搜索的collection 默认的索引库（不是core对应的，是指对整个collection索引集合）
		cloudSolrServer.setDefaultCollection("collection2");
		//3.创建solrinputdocument对象
		SolrInputDocument document = new SolrInputDocument();
		//4.添加域到文档
		document.addField("id", "testcloudid");
		document.addField("item_title", "tody");
		//5.将文档提交到索引库中
		cloudSolrServer.add(document);
		//6.提交
		cloudSolrServer.commit();
	}
}
