package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.FreeGroupBean;

public class MyFreeGroupParser extends AbsParser<List<FreeGroupBean>> {
	
	//总记录数
	private int totalCount;
	//总共页数
	private int totalPage;

	public MyFreeGroupParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

    public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public List<FreeGroupBean> getParseResult(String netStr) {
		// TODO Auto-generated method stub

		List<FreeGroupBean> freeGroupBeans = new ArrayList<FreeGroupBean>();
		try {
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
	            if(jsonObj.has("Data")){
	            	JSONObject jsonData = jsonObj.getJSONObject("Data");
	            	if(jsonData.has("List")){
	            		JSONArray jsonList = jsonData.getJSONArray("List");
	            		for(int i = 0; i < jsonList.length(); i++) {
	            			FreeGroupBean free_group = new FreeGroupBean();
	            			JSONObject jsonItem = jsonList.getJSONObject(i);
	            			if(jsonItem.has("GroupId")){
	            				free_group.setFree_group_id(jsonItem.getString("GroupId"));
	            			}
	            			
	            			if(jsonItem.has("GroupName")){
	            				free_group.setFree_group_name(jsonItem.getString("GroupName"));
	            			}
	            			
	            			if(jsonItem.has("ActivityId")){
	            				free_group.setFree_group_actId(jsonItem.getString("ActivityId"));
	            			}
	            			
	            			if(jsonItem.has("ActivityName")){
	            				free_group.setFree_group_actName(jsonItem.getString("ActivityName"));
	            			}
	            			
	            			if(jsonItem.has("MemberCount")){
	            				free_group.setFree_group_mumberCount(jsonItem.getString("MemberCount"));
	            			}
	            			
	            			if(jsonItem.has("Type")){
	            				free_group.setFree_group_type(jsonItem.getString("Type"));
	            			}
	            			
	            			if(jsonItem.has("CoverThumb")){
	            				free_group.setFree_group_coverThumb(jsonItem.getString("CoverThumb"));
	            			}
	            			
	            			if(jsonItem.has("Status")){
	            				free_group.setFree_group_status(jsonItem.getString("Status"));
	            			}
	            			
	            			if(jsonItem.has("OnLineStatus")){
	            				free_group.setFreee_group_OnLineStatus(jsonItem.getString("OnLineStatus"));
	            			}
	            			
	            			if(jsonItem.has("CountParticipant")){
	            				free_group.setFree_group_countParticipant(jsonItem.getString("CountParticipant"));
	            			}
	            			
	            			if(jsonItem.has("IsEnd")){
	            				free_group.setFree_group_isEnd(jsonItem.getString("IsEnd"));
	            			}
	            			freeGroupBeans.add(free_group);
	            		}
	            	}
		            
	            }
	            
	            if(jsonObj.has("TotalCount")){
                 	setTotalCount(jsonObj.getInt("TotalCount"));
                 }
                 if(jsonObj.has("TotalPage")){
                 	setTotalPage(jsonObj.getInt("TotalPage"));
                 } 
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return freeGroupBeans;
	}

}
