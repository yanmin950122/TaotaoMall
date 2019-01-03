package com.taotao.content.service;
/**
 * 内容处理的接口
 * ClassName: ContentService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月30日 下午2:37:55 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	/**
	 * 插入内容表
	 */
	public TaotaoResult saveContent(TbContent content);
	/**
	 * 查询
	 * getContentList:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param page
	 * @param rows
	 * @return 
	 * @since JDK 1.8
	 */
	public EasyUIDataGridResult getContentList(Integer page, Integer rows);
	/**
	 * 删除
	 * deleteContent:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param ids
	 * @return 
	 * @since JDK 1.8
	 */
	public TaotaoResult deleteContent(String ids);
	/**
	 * 根据内容分类的Id,查询其下的内容列表
	 * getContentListByCatId:(这里用一句话描述这个方法的作用). <br/> 
	 * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author BetterMan 
	 * @param categoryId
	 * @return 
	 * @since JDK 1.8
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
