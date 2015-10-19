package com.ninetowns.tootooplus.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: ActivityInfoBean 
* @Description: 活动详情中，活动信息
* @author wuyulong
* @date 2015-2-10 下午2:49:18 
*
 */
@SuppressWarnings("serial")
public class ActivityInfoBean implements Serializable {
	private String DateRegisterStart;//活动报名开始
	private String DateRegisterEnd;//活动包名结束
	private String DateStart;//活动开始时间
	private String DateEnd;//活动截至时间
	private String CountParticipant;//活动人数
	private String Location;//配送范围发货地址
	private String Place;//地点
	private String PlaceMap;//地点来自百度地图
	private String SupplierName;//商家名称
	private String DateUpdate;//时间更新
	private String Type;//类型  线下还是线上
	private String Category;//分类
	private String Postage;//邮费
	public String getPostage() {
		return Postage;
	}
	public void setPostage(String postage) {
		Postage = postage;
	}
	private List<PostAresBean> postAresList;// 配送范围，配送地点
	public String getDateRegisterStart() {
		return DateRegisterStart;
	}
	public void setDateRegisterStart(String dateRegisterStart) {
		DateRegisterStart = dateRegisterStart;
	}
	public String getDateRegisterEnd() {
		return DateRegisterEnd;
	}
	public void setDateRegisterEnd(String dateRegisterEnd) {
		DateRegisterEnd = dateRegisterEnd;
	}
	public String getDateStart() {
		return DateStart;
	}
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}
	public String getDateEnd() {
		return DateEnd;
	}
	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}
	public String getCountParticipant() {
		return CountParticipant;
	}
	public void setCountParticipant(String countParticipant) {
		CountParticipant = countParticipant;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getPlace() {
		return Place;
	}
	public void setPlace(String place) {
		Place = place;
	}
	public String getPlaceMap() {
		return PlaceMap;
	}
	public void setPlaceMap(String placeMap) {
		PlaceMap = placeMap;
	}
	public String getSupplierName() {
		return SupplierName;
	}
	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}
	public String getDateUpdate() {
		return DateUpdate;
	}
	public void setDateUpdate(String dateUpdate) {
		DateUpdate = dateUpdate;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public List<PostAresBean> getPostAresList() {
		return postAresList;
	}
	public void setPostAresList(List<PostAresBean> postAresList) {
		this.postAresList = postAresList;
	}
	
	

}
