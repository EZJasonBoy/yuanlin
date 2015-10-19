package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.FansBean;

public class FansParser extends AbsParser<List<FansBean>> {

	public FansParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<FansBean> getParseResult(String netStr) {
		// TODO Auto-generated method stub
		List<FansBean> fansList = new ArrayList<FansBean>();
		try{
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("List")){
						JSONArray jsonList = jsonData.getJSONArray("List");
						for(int i = 0; i < jsonList.length(); i++){
							FansBean fansBean = new FansBean();
							
							JSONObject jsonItem = jsonList.getJSONObject(i);
							
							if(jsonItem.has("FriendId")){
								fansBean.setFans_friendId(jsonItem.getString("FriendId"));
							}
							
							if(jsonItem.has("UserId")){
								fansBean.setFans_userId(jsonItem.getString("UserId"));
							}
							if(jsonItem.has("UserName")){
								fansBean.setFans_userName(jsonItem.getString("UserName"));
							}
							if(jsonItem.has("LogoUrl")){
								fansBean.setFans_logoUrl(jsonItem.getString("LogoUrl"));
							}
							if(jsonItem.has("UserGrade")){
								fansBean.setFans_userGrade(jsonItem.getString("UserGrade"));
							}
							if(jsonItem.has("Relation")){
								fansBean.setFans_relation(jsonItem.getString("Relation"));
							}
							fansList.add(fansBean);
						}
					}
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		return fansList;
	}

}
