package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootooplus.bean.HelpBean;


public class HelpParser  {
	
	public List<HelpBean> getParseResult(String string) {
		List<HelpBean> helpBeans = new ArrayList<HelpBean>();
		try {
			JSONObject  object=new JSONObject(string);
			if(null!=object&&1==object.getInt("Status")){
				if(object.has("Data")){
					JSONObject  dataJsonObject=object.getJSONObject("Data");
					if(null!=dataJsonObject&&dataJsonObject.has("List")){
						String list=dataJsonObject.getString("List");
						helpBeans=	JsonTools.jsonObjArray(list, HelpBean.class);
					}
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(objJSONObject.has("List")){
//			JSONArray jsonList = objJSONObject.getJSONArray("List");
//			for(int i = 0; i < jsonList.length(); i++){
//				HelpBean helpBean = new HelpBean();
//				JSONObject jsonItem = jsonList.getJSONObject(i);
//				if(jsonItem.has("HelpId")){
//					helpBean.setHelpId(jsonItem.getString("HelpId"));
//				}
//				
//				if(jsonItem.has("HelpTitle")){
//					helpBean.setHelp_title(jsonItem.getString("HelpTitle"));
//				}
//				
//				if(jsonItem.has("Images")){
//					helpBean.setHelp_image(jsonItem.getString("Images"));
//				}
//				
//				if(jsonItem.has("Content")){
//					helpBean.setHelp_content(jsonItem.getString("Content"));
//				}
//				
//				helpBeans.add(helpBean);
//			}
			
//		}
		
		
		return helpBeans;
	}
}