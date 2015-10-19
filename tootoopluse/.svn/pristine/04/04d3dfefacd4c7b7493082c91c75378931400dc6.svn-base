package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootoopluse.bean.GroupMember;

/** 
* @ClassName: GroupMemberParser 
* @Description: 群组成员解析类
* @author zhou
* @date 2015-3-4 上午11:15:24 
*  
*/
public class GroupMemberParser  {

	

	public List<GroupMember> getParseResult(String netStr) {
		List<GroupMember> lists = new ArrayList<GroupMember>();
		try {
			JSONObject object = new JSONObject(netStr);
			if (object != null) {
				if (object.has("Data")) {
					JSONObject data = object.getJSONObject("Data");
					if (null != data && data.has("List")) {
						
						String list=data.getString("List");
						
						if (TextUtils.isEmpty(list)) {
							return lists;
						}
						
						JsonParser parser = new JsonParser();
						JsonArray array = parser.parse(list).getAsJsonArray();
						array.size();
						
						for (JsonElement obj : array) {
							GroupMember groupMember= JsonTools.getGson().fromJson(obj, GroupMember.class);
							lists.add(groupMember);
						}
							
							
							return lists;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e(e.getMessage());
		}
		return lists;
	}


}
