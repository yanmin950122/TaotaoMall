package com.taotao.item.pojo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.taotao.pojo.TbItem;

public class Item extends TbItem{
	
	public Item(TbItem item) {
		//将原来数据有的属性的值拷贝到item有的属性中
		BeanUtils.copyProperties(item, this);
	}
	
	public String[] getImages() {
		if(StringUtils.isNotBlank(super.getImage())) {
			return super.getImage().split(",");
		}
		return null;
	}
}
