package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * datagrid 展示数据的POJO 包括商品的POJO
 * ClassName: EasyUIDataGridResult <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年12月6日 下午6:49:46 <br/> 
 * 
 * @author BetterMan 
 * @version  
 * @since JDK 1.8
 */
public class EasyUIDataGridResult implements Serializable {
	private Integer total;
	private List rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
