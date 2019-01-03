package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

/**
 * 定义Mapper 关联3张表 查询出搜索时的商品数据
 * ClassName: SearchItemMapper <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月31日 下午10:32:17 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
public interface SearchItemMapper {
	//查询所有的商品的数据
	public List<SearchItem> getSearchItemList();
}
