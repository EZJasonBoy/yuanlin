package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.AddressListBean;

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
							if(jsonItem.has("AddressId")){
								addressBean.setAdd_addressId(jsonItem.getString("AddressId"));
							}
							
							if(jsonItem.has("UserId")){
								addressBean.setAdd_userId(jsonItem.getString("UserId"));
							}
							
							if(jsonItem.has("RealName")){
								addressBean.setAdd_realName(jsonItem.getString("RealName"));
							}
							
							if(jsonItem.has("PhoneNumber")){
								addressBean.setAdd_phoneNumber(jsonItem.getString("PhoneNumber"));
							}
							
							if(jsonItem.has("DetailedAddress")){
								addressBean.setAdd_detailedAddress(jsonItem.getString("DetailedAddress"));
							}
							
							if(jsonItem.has("Postcode")){
								addressBean.setAdd_postcode(jsonItem.getString("Postcode"));
							}
							
							if(jsonItem.has("ProvinceId")){
								addressBean.setAdd_provinceId(jsonItem.getString("ProvinceId"));
							}
							
							if(jsonItem.has("CityId")){
								addressBean.setAdd_cityId(jsonItem.getString("CityId"));
							}
							
							if(jsonItem.has("DistrictId")){
								addressBean.setAdd_districtId(jsonItem.getString("DistrictId"));
							}
							
							if(jsonItem.has("ProvinceName")){
								addressBean.setAdd_provinceName(jsonItem.getString("ProvinceName"));
							}
							
							if(jsonItem.has("CityName")){
								addressBean.setAdd_cityName(jsonItem.getString("CityName"));
							}
							
							if(jsonItem.has("DistrictName")){
								addressBean.setAdd_districtName(jsonItem.getString("DistrictName"));
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
