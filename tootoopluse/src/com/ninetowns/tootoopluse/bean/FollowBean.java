package com.ninetowns.tootoopluse.bean;

public class FollowBean {
	
	private String fol_friendId;
	
	private String fol_followId;
	
	private String fol_userName;
	
	private String fol_usreId;
	
	private String fol_logoUrl;
	
	private String fol_relation;
	
	private String fol_initial;
	
	private String fol_vip;

	public String getFol_friendId() {
		return fol_friendId;
	}

	public void setFol_friendId(String fol_friendId) {
		this.fol_friendId = fol_friendId;
	}

	public String getFol_followId() {
		return fol_followId;
	}

	public void setFol_followId(String fol_followId) {
		this.fol_followId = fol_followId;
	}

	public String getFol_userName() {
		return fol_userName;
	}

	public void setFol_userName(String fol_userName) {
		this.fol_userName = fol_userName;
	}

	public String getFol_logoUrl() {
		return fol_logoUrl;
	}

	public void setFol_logoUrl(String fol_logoUrl) {
		this.fol_logoUrl = fol_logoUrl;
	}

	public String getFol_relation() {
		return fol_relation;
	}

	public void setFol_relation(String fol_relation) {
		this.fol_relation = fol_relation;
	}

	public String getFol_initial() {
		return fol_initial;
	}

	public void setFol_initial(String fol_initial) {
		this.fol_initial = fol_initial;
	}

	public String getFol_vip() {
		return fol_vip;
	}

	public void setFol_vip(String fol_vip) {
		this.fol_vip = fol_vip;
	}

	public String getFol_usreId() {
		return fol_usreId;
	}

	public void setFol_usreId(String fol_usreId) {
		this.fol_usreId = fol_usreId;
	}

	@Override
	public String toString() {
		return "FollowBean [fol_friendId=" + fol_friendId + ", fol_followId="
				+ fol_followId + ", fol_userName=" + fol_userName
				+ ", fol_usreId=" + fol_usreId + ", fol_logoUrl=" + fol_logoUrl
				+ ", fol_relation=" + fol_relation + ", fol_initial="
				+ fol_initial + ", fol_vip=" + fol_vip + "]";
	}


}
