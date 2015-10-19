package com.ninetowns.tootooplus.bean;

public class MyInvitationBean {
	public String UserId ;
	public String LogoUrl ;
	public String UserName ;
	/** 
	* @Fields UserGrade : 用户vip等级
	*/ 
	public String UserGrade ;
	@Override
	public String toString() {
		return "MyInvitationBean [UserId=" + UserId + ", LogoUrl=" + LogoUrl
				+ ", UserName=" + UserName + ", UserGrade=" + UserGrade + "]";
	}
	

}
