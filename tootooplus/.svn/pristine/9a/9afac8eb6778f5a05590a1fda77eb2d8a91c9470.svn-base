package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.CityBean;
import com.ninetowns.tootooplus.bean.ProvinceCityBean;
import com.ninetowns.tootooplus.bean.UpdateActThirdBean;

public class UpdateThirdParser extends AbsParser<UpdateActThirdBean> {

	public UpdateThirdParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public UpdateActThirdBean getParseResult(String netStr) {
		// TODO Auto-generated method stub
		UpdateActThirdBean updateBean = new UpdateActThirdBean();
		try {
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("ActivityId")){
						updateBean.setUp_actId(jsonData.getString("ActivityId"));
					}
					
					if(jsonData.has("ActivityName")){
						updateBean.setUp_actName(jsonData.getString("ActivityName"));
					}
					
					if(jsonData.has("Type")){
						updateBean.setUp_type(jsonData.getString("Type"));
					}
					
					if(jsonData.has("CountParticipant")){
						updateBean.setUp_countParticipant(jsonData.getString("CountParticipant"));
					}
					
					if(jsonData.has("DateRegisterStart")){
						updateBean.setUp_dateRegisterStart(jsonData.getString("DateRegisterStart"));
					}
					
					if(jsonData.has("DateRegisterEnd")){
						updateBean.setUp_dateRegisterEnd(jsonData.getString("DateRegisterEnd"));
					}
					
					if(jsonData.has("DateStart")){
						updateBean.setUp_dateStart(jsonData.getString("DateStart"));
					}
					
					if(jsonData.has("DateEnd")){
						updateBean.setUp_dateEnd(jsonData.getString("DateEnd"));
					}
					
					if(jsonData.has("Location")){
						updateBean.setUp_location(jsonData.getString("Location"));
					}
					
					if(jsonData.has("DispatchingType")){
						updateBean.setUp_dispatchingType(jsonData.getString("DispatchingType"));
					}
					
					if(jsonData.has("ExpressName")){
						updateBean.setUp_expressName(jsonData.getString("ExpressName"));
					}
					
					if(jsonData.has("Place")){
						updateBean.setUp_place(jsonData.getString("Place"));
					}
					
					if(jsonData.has("PlaceMap")){
						updateBean.setUp_placeMap(jsonData.getString("PlaceMap"));
					}
					
					if(jsonData.has("SupplierName")){
						updateBean.setUp_supplierName(jsonData.getString("SupplierName"));
					}
					
					if(jsonData.has("PostAres")){
						JSONArray aresArray = jsonData.getJSONArray("PostAres");
						List<ProvinceCityBean> provinces = new ArrayList<ProvinceCityBean>();
						for(int i = 0; i < aresArray.length(); i++){
							ProvinceCityBean provinceBean = new ProvinceCityBean();
							JSONObject aresItem = aresArray.getJSONObject(i);
							if(aresItem.has("AreaName")){
								provinceBean.setProCity_pro(aresItem.getString("AreaName"));
							}
							
							if(aresItem.has("AreaSub")){
								JSONArray subArray = aresItem.getJSONArray("AreaSub");
								List<CityBean> citys = new ArrayList<CityBean>();
								for(int j = 0; j < subArray.length(); j++){
									CityBean cityBean = new CityBean();
									JSONObject cityObject = subArray.getJSONObject(j);
									if(cityObject.has("AreaId")){
										cityBean.setCity_id(cityObject.getString("AreaId"));
									}
									
									if(cityObject.has("AreaName")){
										cityBean.setCity_name(cityObject.getString("AreaName"));
									}
									citys.add(cityBean);
								}
								
								provinceBean.setCityBeanList(citys);
							}
							
							provinces.add(provinceBean);
						}
						updateBean.setProvinceList(provinces);
					}
					
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		
		return updateBean;
	}

}
