package com.ninetowns.tootooplus.bean;

import java.io.Serializable;

/**
 * 
* @ClassName: GoodScreenMainBean 
* @Description: 子商品分类的bean
* @author wuyulong
* @date 2015-1-27 下午5:20:10 
*
 */
@SuppressWarnings("serial")
public class GoodsScreenSubBean implements Serializable{
	private String CategoryId;//商品分类id
	private String CategoryName;//商品名称
	public String getCategoryId() {
		return CategoryId;
	}
	public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

}
