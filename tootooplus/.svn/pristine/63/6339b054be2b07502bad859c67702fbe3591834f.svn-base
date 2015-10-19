package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.GoodsAddressBean;
import com.ninetowns.tootooplus.bean.GoodsAreaBean;

public class GoodsAreaParser extends AbsParser<GoodsAreaBean> {


	public GoodsAreaParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public GoodsAreaBean getParseResult(String netStr) {
		// TODO Auto-generated method stub
		GoodsAreaBean areaBean = new GoodsAreaBean();
		try{
			JSONObject objJSONObject = new JSONObject(netStr);
			if(objJSONObject.has("Status") && objJSONObject.getString("Status").equals("1")){
				if(objJSONObject.has("Data")){
					JSONObject jsonData = objJSONObject.getJSONObject("Data");
					if(jsonData.has("province")){
						List<GoodsAddressBean> provinceBeans = new ArrayList<GoodsAddressBean>();
						JSONArray jsonProvinceArray = jsonData.getJSONArray("province");
						for(int i = 0; i < jsonProvinceArray.length(); i++){
							GoodsAddressBean provinceBean = new GoodsAddressBean();
							JSONObject jsonProvince = jsonProvinceArray.getJSONObject(i);
							if(jsonProvince.has("ProvinceId")){
								provinceBean.setGoods_address_id(jsonProvince.getString("ProvinceId"));
							}
							
							if(jsonProvince.has("ProvinceName")){
								provinceBean.setGoods_address_name(jsonProvince.getString("ProvinceName"));
							}
							provinceBeans.add(provinceBean);
						}
						areaBean.setArea_provinces(provinceBeans);
					}
			
			
					if(jsonData.has("city")){
						List<GoodsAddressBean> cityBeans = new ArrayList<GoodsAddressBean>();
						JSONArray jsonCityArray = jsonData.getJSONArray("city");
						for(int i = 0; i < jsonCityArray.length(); i++){
							GoodsAddressBean cityBean = new GoodsAddressBean();
							JSONObject jsonCity = jsonCityArray.getJSONObject(i);
							
							if(jsonCity.has("CityId")){
								cityBean.setGoods_address_id(jsonCity.getString("CityId"));
							}
							if(jsonCity.has("CityName")){
								cityBean.setGoods_address_name(jsonCity.getString("CityName"));
							}
							
							if(jsonCity.has("ParentId")){
								cityBean.setGoods_address_parent_id(jsonCity.getString("ParentId"));
							}
							cityBeans.add(cityBean);
						}
						areaBean.setArea_citys(cityBeans);
					}
					
					if(jsonData.has("district")){
						List<GoodsAddressBean> districtBeans = new ArrayList<GoodsAddressBean>();
						JSONArray jsonDistrictArray = jsonData.getJSONArray("district");
						for(int i = 0; i < jsonDistrictArray.length(); i++){
							GoodsAddressBean districtBean = new GoodsAddressBean();
							JSONObject jsonDistrict = jsonDistrictArray.getJSONObject(i);
							if(jsonDistrict.has("DistrictId")){
								districtBean.setGoods_address_id(jsonDistrict.getString("DistrictId"));
							}
							if(jsonDistrict.has("DistrictName")){
								districtBean.setGoods_address_name(jsonDistrict.getString("DistrictName"));
							}
							if(jsonDistrict.has("ParentId")){
								districtBean.setGoods_address_parent_id(jsonDistrict.getString("ParentId"));
							}		
							districtBeans.add(districtBean);
						}
						areaBean.setArea_districts(districtBeans);
					}
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return areaBean;
	}

}
