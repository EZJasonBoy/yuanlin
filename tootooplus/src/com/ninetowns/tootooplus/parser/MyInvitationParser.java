package com.ninetowns.tootooplus.parser;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.bean.MyInvitationBean;

public class MyInvitationParser extends AbsParser<MyInvitationBean> {

	/** 
	* @Fields infoString : 关于邀请的一些信息
	*/ 
	private String infoString;
	/** 
	* @Fields inviteCode :表示是否被邀请过  "0"未被邀请    其他值是邀请过
	*/ 
	private  String inviteCode;
	private static final String TAG = "MyInvitationParser";

	public MyInvitationParser(String str) {
		super(str);
	}

	@Override
	public List<MyInvitationBean> getParseResult(String netStr) {
		try {
			JSONObject object = new JSONObject(netStr);

			if (null != object) {
				if(object.has("Info")){
					infoString= object.getString("Info");
				}
				if(object.has("InviteCode")){
					inviteCode= object.getString("InviteCode");
				}
				if (object.has("List")) {

					String listString = object.getString("List");
					List<MyInvitationBean> myInvitationBeans = JsonTools
							.jsonObjArray(listString, MyInvitationBean.class);
					return myInvitationBeans;
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.error(TAG, e.getMessage());
			return null;
		}
		return null;
	}

	public  String getInvitationInfo(){
		return infoString;
	}
	public String getInvitationCode(){
		return inviteCode;
	}
}
