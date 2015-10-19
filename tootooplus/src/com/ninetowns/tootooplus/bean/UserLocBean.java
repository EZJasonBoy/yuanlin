package com.ninetowns.tootooplus.bean;

public class UserLocBean {
	
	private String locCity;
	
	private String locLatitude;
	
	private String locLongitude;

	public String getLocCity() {
		return locCity;
	}

	public void setLocCity(String locCity) {
		this.locCity = locCity;
	}

	public String getLocLatitude() {
		return locLatitude;
	}

	public void setLocLatitude(String locLatitude) {
		this.locLatitude = locLatitude;
	}

	public String getLocLongitude() {
		return locLongitude;
	}

	public void setLocLongitude(String locLongitude) {
		this.locLongitude = locLongitude;
	}

	@Override
	public String toString() {
		return "UserLocBean [locCity=" + locCity + ", locLatitude="
				+ locLatitude + ", locLongitude=" + locLongitude + "]";
	}

}
