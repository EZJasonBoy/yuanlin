package com.ninetowns.tootoopluse.bean;

import java.util.List;

public class ProvinceCityBean {
	
	private String proCity_pro;
	
	private List<CityBean> cityBeanList;

	public String getProCity_pro() {
		return proCity_pro;
	}

	public void setProCity_pro(String proCity_pro) {
		this.proCity_pro = proCity_pro;
	}

	public List<CityBean> getCityBeanList() {
		return cityBeanList;
	}

	public void setCityBeanList(List<CityBean> cityBeanList) {
		this.cityBeanList = cityBeanList;
	}

	@Override
	public String toString() {
		return "ProvinceCityBean [proCity_pro=" + proCity_pro
				+ ", cityBeanList=" + cityBeanList + "]";
	}



}
