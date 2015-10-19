package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: MyConstanctBean
 * @Description: 我的联系方式
 * @author wuyulong
 * @date 2015-6-17 下午4:40:24
 * 
 */
@SuppressWarnings("serial")
public class MyConstanctBean implements Serializable {
	private String MemberCount;
	private String Name;
	private String Phone;

	public String getMemberCount() {
		return MemberCount;
	}

	public void setMemberCount(String memberCount) {
		MemberCount = memberCount;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

}
