package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootooplus.bean.PrivateLetterMessageBean;


/** 
* @ClassName: PrivateLetterMessageParser 
* @Description: 私信 详情的解析 类 
* @author zhou
* @date 2015-3-9 下午5:00:35 
*  
*/
public class PrivateLetterMessageParser  {

	

	public List<PrivateLetterMessageBean> getParseResult(String netStr) {
		List<PrivateLetterMessageBean> privateLetterBeans=new ArrayList<PrivateLetterMessageBean>();
//		List<GroupMember> lists = new ArrayList<GroupMember>();
		try {
			JSONObject object = new JSONObject(netStr);
			if (object != null) {
				if (object.has("Data")) {
					JSONObject data = object.getJSONObject("Data");
					if (null != data && data.has("List")) {
						
						String list=data.getString("List");
						
						if (TextUtils.isEmpty(list)) {
							return privateLetterBeans;
						}
						 privateLetterBeans = JsonTools.jsonObjArray(
								list, PrivateLetterMessageBean.class);
							
							return privateLetterBeans;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e(e.getMessage());
		}
		return privateLetterBeans;
	}


}
