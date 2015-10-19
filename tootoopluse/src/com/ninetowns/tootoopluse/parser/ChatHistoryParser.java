package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.wiriamubin.service.chat.MyChatData;

public class ChatHistoryParser {

	public List<MyChatData> parserData(Context context,String result) throws JSONException {
		List<MyChatData> myChatDatas=new ArrayList<MyChatData>();
		MyChatData myChatData;
		JSONObject object=new JSONObject(result);
		if(null!=object&&object.has("Data")){
			JSONObject dataJsonObject=object.getJSONObject("Data");
			if(null!=dataJsonObject&&dataJsonObject.has("List")){
				JSONArray list=dataJsonObject.getJSONArray("List");
					for (int i = 0; i < list.length(); i++) {
						myChatData=new MyChatData();
						JSONObject obj= list.getJSONObject(i);
						if(null==obj){
							return myChatDatas;
						}
							
							myChatData.groupId=obj.getString("GroupId");
							
							myChatData.type=obj.getString("MessageType");
							myChatData.body=obj.getString("Contents");
							if(SharedPreferenceHelper.getLoginUserId(context).equalsIgnoreCase(obj.getString("UserId"))){
								myChatData.isRecever=false;
							}else{
								myChatData.isRecever=true;
							}
							myChatData.name=obj.getString("UserName");
							myChatData.time=obj.getString("CreateTime");
							myChatData.profileImage=obj.getString("LogoUrl");
							myChatDatas.add(myChatData);
//						}
						
					}
					}
//					
				}
		return myChatDatas;
			}
		

}
