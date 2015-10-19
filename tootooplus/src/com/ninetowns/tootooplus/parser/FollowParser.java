package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.FollowBean;

public class FollowParser extends AbsParser<List<FollowBean>> {

	public FollowParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<FollowBean> getParseResult(String netStr) {
		// TODO Auto-generated method stub
		List<FollowBean> followList = new ArrayList<FollowBean>();
		
		try{
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("List")){
						JSONArray jsonList = jsonData.getJSONArray("List");
						for(int i = 0; i < jsonList.length(); i++){
							FollowBean followBean = new FollowBean();
							JSONObject jsonItem = jsonList.getJSONObject(i);
							if(jsonItem.has("FriendId")){
								followBean.setFol_friendId(jsonItem.getString("FriendId"));
							}
							
							if(jsonItem.has("FollowId")){
								followBean.setFol_followId(jsonItem.getString("FollowId"));
							}
							
							if(jsonItem.has("UserId")){
								followBean.setFol_usreId(jsonItem.getString("UserId"));
							}
							
							if(jsonItem.has("UserName")){
								followBean.setFol_userName(jsonItem.getString("UserName"));
							}
							
							if(jsonItem.has("LogoUrl")){
								followBean.setFol_logoUrl(jsonItem.getString("LogoUrl"));
							}
							
							if(jsonItem.has("UserGrade")){
								followBean.setFol_vip(jsonItem.getString("UserGrade"));
							}
							
							if(jsonItem.has("Relation")){
								followBean.setFol_relation(jsonItem.getString("Relation"));
							}
							followList.add(followBean);
						}
						
					}
					
				}
				
				
			}
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return followList;
	}

}
