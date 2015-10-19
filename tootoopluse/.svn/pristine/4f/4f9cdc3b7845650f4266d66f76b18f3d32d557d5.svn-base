package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.AddressListBean;

public class AddressListParser extends AbsParser<List<AddressListBean>> {

	public AddressListParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<AddressListBean> getParseResult(String netStr) {
		// TODO Auto-generated method stub
		
		List<AddressListBean> list = new ArrayList<AddressListBean>();
		try{
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("List")){
						JSONArray jsonList = jsonData.getJSONArray("List");
						for(int i = 0; i < jsonList.length(); i++){
							AddressListBean addressBean = new AddressListBean();
							JSONObject jsonItem = jsonList.getJSONObject(i);
							
							if(jsonItem.has("RealName")){
								addressBean.setAddress_realName(jsonItem.getString("RealName"));
							}
							
							if(jsonItem.has("PhoneNumber")){
								addressBean.setAddress_phoneNumber(jsonItem.getString("PhoneNumber"));
							}
							
							if(jsonItem.has("DetailedAddress")){
								addressBean.setAddress_detail(jsonItem.getString("DetailedAddress"));
							}
							
							if(jsonItem.has("Postcode")){
								addressBean.setAddress_postcode(jsonItem.getString("Postcode"));
							}
							
							if(jsonItem.has("MemberCount")){
								addressBean.setAddress_memberCount(jsonItem.getString("MemberCount"));
							}
							
							list.add(addressBean);
						}
					}
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return list;
	}

}
