package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: CreateActivitySecondBean 
* @Description: 创建活动第二步
* @author wuyulong
* @date 2015-2-9 上午11:36:02 
*
 */
@SuppressWarnings("serial")
public class CreateActivitySecondBean implements Serializable{
	private List<StoryDetailListBean> wishDetailBean;//活动的详情
	public List<StoryDetailListBean> getWishDetailBean() {
		return wishDetailBean;
	}
	public void setWishDetailBean(List<StoryDetailListBean> wishDetailBean) {
		this.wishDetailBean = wishDetailBean;
	}
	private ConVertBean convertBean;
	public ConVertBean getConvertBean() {
		return convertBean;
	}
	public void setConvertBean(ConVertBean convertBean) {
		this.convertBean = convertBean;
	}
	@Override
	public String toString() {
		return "CreateActivitySecondBean [wishDetailBean=" + wishDetailBean
				+ ", convertBean=" + convertBean + "]";
	}


}
