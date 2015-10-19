package com.ninetowns.tootoopluse.bean;

public class FansBean {
	
	private String fans_friendId;

	private String fans_userId;
	
	private String fans_userName;
	
	private String fans_logoUrl;
	
	private String fans_userGrade;
	
	private String fans_relation;
	
	private String fans_initial;

	public String getFans_friendId() {
		return fans_friendId;
	}

	public void setFans_friendId(String fans_friendId) {
		this.fans_friendId = fans_friendId;
	}

	public String getFans_userId() {
		return fans_userId;
	}

	public void setFans_userId(String fans_userId) {
		this.fans_userId = fans_userId;
	}

	public String getFans_userName() {
		return fans_userName;
	}

	public void setFans_userName(String fans_userName) {
		this.fans_userName = fans_userName;
	}

	public String getFans_logoUrl() {
		return fans_logoUrl;
	}

	public void setFans_logoUrl(String fans_logoUrl) {
		this.fans_logoUrl = fans_logoUrl;
	}

	public String getFans_userGrade() {
		return fans_userGrade;
	}

	public void setFans_userGrade(String fans_userGrade) {
		this.fans_userGrade = fans_userGrade;
	}

	public String getFans_relation() {
		return fans_relation;
	}

	public void setFans_relation(String fans_relation) {
		this.fans_relation = fans_relation;
	}

	public String getFans_initial() {
		return fans_initial;
	}

	public void setFans_initial(String fans_initial) {
		this.fans_initial = fans_initial;
	}

	@Override
	public String toString() {
		return "FansBean [fans_friendId=" + fans_friendId + ", fans_userId="
				+ fans_userId + ", fans_userName=" + fans_userName
				+ ", fans_logoUrl=" + fans_logoUrl + ", fans_userGrade="
				+ fans_userGrade + ", fans_relation=" + fans_relation
				+ ", fans_initial=" + fans_initial + "]";
	}
}
