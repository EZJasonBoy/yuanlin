package com.ninetowns.tootooplus.bean;

import java.util.List;

public class GoodsAreaBean {
	
	private List<GoodsAddressBean> area_provinces;
	
	private List<GoodsAddressBean> area_citys;
	
	private List<GoodsAddressBean> area_districts;

	public List<GoodsAddressBean> getArea_provinces() {
		return area_provinces;
	}

	public void setArea_provinces(List<GoodsAddressBean> area_provinces) {
		this.area_provinces = area_provinces;
	}

	public List<GoodsAddressBean> getArea_citys() {
		return area_citys;
	}

	public void setArea_citys(List<GoodsAddressBean> area_citys) {
		this.area_citys = area_citys;
	}

	public List<GoodsAddressBean> getArea_districts() {
		return area_districts;
	}

	public void setArea_districts(List<GoodsAddressBean> area_districts) {
		this.area_districts = area_districts;
	}

	@Override
	public String toString() {
		return "GoodsAreaBean [area_provinces=" + area_provinces
				+ ", area_citys=" + area_citys + ", area_districts="
				+ area_districts + "]";
	}


}
