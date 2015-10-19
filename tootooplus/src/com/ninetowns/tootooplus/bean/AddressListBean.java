package com.ninetowns.tootooplus.bean;

import java.io.Serializable;


public class AddressListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String add_addressId;
	
	private String add_userId;
	
	private String add_realName;
	
	private String add_phoneNumber;
	
	private String add_detailedAddress;
	
	private String add_postcode;
	
	private String add_provinceId;
	
	private String add_cityId;
	
	private String add_districtId;
	
	private String add_provinceName;
	
	private String add_cityName;
	
	private String add_districtName;

	public String getAdd_addressId() {
		return add_addressId;
	}

	public void setAdd_addressId(String add_addressId) {
		this.add_addressId = add_addressId;
	}

	public String getAdd_userId() {
		return add_userId;
	}

	public void setAdd_userId(String add_userId) {
		this.add_userId = add_userId;
	}

	public String getAdd_realName() {
		return add_realName;
	}

	public void setAdd_realName(String add_realName) {
		this.add_realName = add_realName;
	}

	public String getAdd_phoneNumber() {
		return add_phoneNumber;
	}

	public void setAdd_phoneNumber(String add_phoneNumber) {
		this.add_phoneNumber = add_phoneNumber;
	}

	public String getAdd_detailedAddress() {
		return add_detailedAddress;
	}

	public void setAdd_detailedAddress(String add_detailedAddress) {
		this.add_detailedAddress = add_detailedAddress;
	}

	public String getAdd_postcode() {
		return add_postcode;
	}

	public void setAdd_postcode(String add_postcode) {
		this.add_postcode = add_postcode;
	}

	public String getAdd_provinceId() {
		return add_provinceId;
	}

	public void setAdd_provinceId(String add_provinceId) {
		this.add_provinceId = add_provinceId;
	}

	public String getAdd_cityId() {
		return add_cityId;
	}

	public void setAdd_cityId(String add_cityId) {
		this.add_cityId = add_cityId;
	}

	public String getAdd_districtId() {
		return add_districtId;
	}

	public void setAdd_districtId(String add_districtId) {
		this.add_districtId = add_districtId;
	}

	public String getAdd_provinceName() {
		return add_provinceName;
	}

	public void setAdd_provinceName(String add_provinceName) {
		this.add_provinceName = add_provinceName;
	}

	public String getAdd_cityName() {
		return add_cityName;
	}

	public void setAdd_cityName(String add_cityName) {
		this.add_cityName = add_cityName;
	}

	public String getAdd_districtName() {
		return add_districtName;
	}

	public void setAdd_districtName(String add_districtName) {
		this.add_districtName = add_districtName;
	}

	@Override
	public String toString() {
		return "AddressListBean [add_addressId=" + add_addressId
				+ ", add_userId=" + add_userId + ", add_realName="
				+ add_realName + ", add_phoneNumber=" + add_phoneNumber
				+ ", add_detailedAddress=" + add_detailedAddress
				+ ", add_postcode=" + add_postcode + ", add_provinceId="
				+ add_provinceId + ", add_cityId=" + add_cityId
				+ ", add_districtId=" + add_districtId + ", add_provinceName="
				+ add_provinceName + ", add_cityName=" + add_cityName
				+ ", add_districtName=" + add_districtName + "]";
	}
	
}
