package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.bean.GroupChatBean;
/**
 * 
* @ClassName: GetGroupChatParser 
* @Description: 活动详情里面
* @author wuyulong
* @date 2015-3-16 下午1:58:51 
*
 */
public class GetGroupChatParser extends AbsParser<List<GroupChatBean>> {
	private String name;//名字

	public String getName() {
		return name;
	}
	private int status;

	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public void setName(String name) {
		this.name = name;
	}
	

	public GetGroupChatParser(String str) {
		super(str);
	}

	@Override
	public List<GroupChatBean> getParseResult(String netStr) {
		List<GroupChatBean> groupChatBeanList= new ArrayList<GroupChatBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status")) {
				int status = obj.getInt("Status");
				setStatus(status);
				if (status == 1) {// 如果等于1
					if (obj.has("Data")) {
						String strData = obj.getString("Data");
						JSONObject jsobj=new JSONObject(strData);
						if(jsobj.has("List")){
							String jsArr=jsobj.getString("List");
							JSONArray jsonArray = new JSONArray(jsArr);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObj = (JSONObject) jsonArray.get(i);
								GroupChatBean  groupChatBean=new GroupChatBean();
								if (jsonObj.has("GroupId")) {
									groupChatBean.setGroupId(jsonObj.getString("GroupId"));
								}
								if (jsonObj.has("GroupName")) {
									groupChatBean.setGroupName(jsonObj.getString("GroupName"));
								}
								if (jsonObj.has("MemberCount")) {
									groupChatBean.setMemberCount(jsonObj.getString("MemberCount"));
								}
								
								if(jsonObj.has("Type")){
									groupChatBean.setType(jsonObj.getString("Type"));
								}
								groupChatBeanList.add(groupChatBean);
								
							}

						}
						if(jsobj.has("Name")){
							setName(jsobj.getString("Name"));
							
						}
						
					}

				} else {
					LogUtil.systemlogError("status", status + "");
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return groupChatBeanList;
	}

}
