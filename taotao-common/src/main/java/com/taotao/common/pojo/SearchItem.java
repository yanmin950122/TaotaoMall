package com.taotao.common.pojo;

import java.io.Serializable;
/**
 * 搜索的商品数据POJO
 * @title SearchItem.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public class SearchItem implements Serializable {
	private Long id;//商品的id 
	private String title;//商品标题
	private String sell_point;//商品卖点
	private Long price;//价格
	private String image;//商品图片的路径
	private String category_name;//商品分类名称
	private String item_desc;//商品的描述
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	public String[] getImages() {
		if(this.getImage() != null) {
			return this.getImage().split(",");
		}
		return null;
	}
}	
