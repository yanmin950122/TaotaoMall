package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {
	//导入所有的商品数据到索引库中
	public TaotaoResult importAllSearchItems() throws Exception;
	//根据搜索的条件查询搜索的结果
	/**
	 * 
	 * search:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param queryString	查询的主条件
	 * @param page	查询的当前的页码
	 * @param rows	每页显示的行数 这个在Controller中写死
	 * @return
	 * @throws Exception 
	 * @since JDK 1.8
	 */
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception;
	TaotaoResult updateSearchItemById(Long itemId) throws Exception;
	
	
}
