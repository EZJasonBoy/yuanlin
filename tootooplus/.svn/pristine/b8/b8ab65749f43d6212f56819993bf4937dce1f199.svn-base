package com.ninetowns.tootooplus.parser;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;

public class PriorityCodeParser extends AbsParser<PriorityCodeBean> {

	public PriorityCodeParser(String str) {
		super(str);
	}

	@Override
	public List<PriorityCodeBean> getParseResult(String netStr) {
		try {
			JSONObject object=new JSONObject(netStr);
			if(null!=object&&object.has("Data")){
				JSONObject data=object.getJSONObject("Data");
				if(null!=data&&data.has("TotalPage")){
					totalPage=data.getInt("TotalPage");
				}
				if(null!=data&&data.has("List")){
				String listString=	data.getString("List");
				List<PriorityCodeBean> priorityCodeBeans=JsonTools.jsonObjArray(listString, PriorityCodeBean.class);
				return priorityCodeBeans;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	public int getTotalPage() {
		return totalPage;
	}
}
