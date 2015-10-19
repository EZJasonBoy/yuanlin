package com.ninetowns.tootooplus.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootooplus.bean.PersonInfoDetailBean;

public class PersonInfoParser  extends AbsParser<PersonInfoDetailBean>{


	public PersonInfoParser(String str) {
		super(str);
	}

	public PersonInfoDetailBean getParseResult(String netStr) {
		try {
			JSONObject object=new JSONObject(netStr);
			if(null!=object&&object.has("Data")){
				String data=object.getString("Data");
				PersonInfoDetailBean personInfoDetailBean=JsonTools.jsonObj(data, PersonInfoDetailBean.class);
				return personInfoDetailBean;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e(e.getMessage());
			return null;
		}
		return null;
		
	}

}
