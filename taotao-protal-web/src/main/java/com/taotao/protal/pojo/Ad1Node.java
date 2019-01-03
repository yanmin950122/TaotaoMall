package com.taotao.protal.pojo;

import java.io.Serializable;

/**
 * 首页大广告位轮播图数据展示POJO
 * @title Ad1Node.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
public class Ad1Node implements Serializable{

	private String srcB;//pic2
	private String height; 
	private String alt;//subtitle中获取
	private String width;
	private String src;//pic
	private String widthB;
	private String href;//url
	private String heightB;
	public String getSrcB() {
		return srcB;
	}
	public void setSrcB(String srcB) {
		this.srcB = srcB;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getWidthB() {
		return widthB;
	}
	public void setWidthB(String widthB) {
		this.widthB = widthB;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHeightB() {
		return heightB;
	}
	public void setHeightB(String heightB) {
		this.heightB = heightB;
	}
}

