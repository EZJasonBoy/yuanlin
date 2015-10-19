package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.CityBean;
import com.ninetowns.tootooplus.bean.ProvinceCityBean;

public class SelectCityParser extends AbsParser<List<ProvinceCityBean>> {

	public SelectCityParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ProvinceCityBean> getParseResult(String netStr) {
		
		List<ProvinceCityBean> proCityList = new ArrayList<ProvinceCityBean>();
		try{
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("List")){
						JSONArray jsonlist = jsonData.getJSONArray("List");
						for(int i = 0; i < jsonlist.length(); i++){
							JSONObject jsonItem = jsonlist.getJSONObject(i);
							ProvinceCityBean provinceCityBean = new ProvinceCityBean();
							if(jsonItem.has("Province")){
								provinceCityBean.setProCity_pro(jsonItem.getString("Province"));
							} 
							
							if(jsonItem.has("City")){
								List<CityBean> cityList = new ArrayList<CityBean>();
								JSONArray cityArray = jsonItem.getJSONArray("City");
								for(int j = 0; j < cityArray.length(); j++){
									JSONObject cityItem = cityArray.getJSONObject(j);
									CityBean cityBean = new CityBean();
									if(cityItem.has("Name")){
										cityBean.setCity_name(cityItem.getString("Name"));
									}
									
									if(cityItem.has("AreaId")){
										cityBean.setCity_id(cityItem.getString("AreaId"));
									}
									cityList.add(cityBean);
								}
								provinceCityBean.setCityBeanList(cityList);
							}
							proCityList.add(provinceCityBean);
						}
					}
					
				}
				
			}
			
		} catch(JSONException e){
			e.printStackTrace();
		}
		
		
		
		return proCityList;
	}

}
