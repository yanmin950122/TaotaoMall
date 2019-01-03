package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	//通过节点的id，查询节点的子节点列表
	public List<EasyUITreeNode> getContentCategoryList(Long parentID);
	
	/**
	 * 
	 * createContentCategory:(添加内容分类). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * @author BetterMan 
	 * @param parentId 父节点的id
	 * @param name 节点名称
	 * @return TaotaoResult
	 * @since JDK 1.8
	 */
	public TaotaoResult createContentCategory(Long parentId, String name);
	/**
	 * 更新节点
	 * updateContentCategory:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param id
	 * @param name
	 * @return 
	 * @since JDK 1.8
	 */
	public TaotaoResult updateContentCategory(Long id, String name);
	/**
	 * 删除节点
	 * deleteContentCategory:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param id
	 * @return 
	 * @since JDK 1.8
	 */
	public TaotaoResult deleteContentCategory(Long parentId, Long id);
}
