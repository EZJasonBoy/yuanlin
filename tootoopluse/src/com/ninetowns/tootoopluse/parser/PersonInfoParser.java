package com.ninetowns.tootoopluse.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.PersonInfoBean;

public class PersonInfoParser extends AbsParser<PersonInfoBean> {

	public PersonInfoParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PersonInfoBean getParseResult(String netStr) {
		// TODO Auto-generated method stub
		
		PersonInfoBean personInfoBean = new PersonInfoBean();
		
		try{
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					
					if(jsonData.has("UserId")){
						personInfoBean.setInfo_userId(jsonData.getString("UserId"));
					}
					
					if(jsonData.has("Source")){
						personInfoBean.setInfo_source(jsonData.getString("Source"));
					}
					
					if(jsonData.has("UserName")){
						personInfoBean.setInfo_userName(jsonData.getString("UserName"));
					}
					
					if(jsonData.has("LogoUrl")){
						personInfoBean.setInfo_logoUrl(jsonData.getString("LogoUrl"));
					}
					
					if(jsonData.has("VerifyType")){
						personInfoBean.setInfo_verifyType(jsonData.getString("VerifyType"));
					}
					
					if(jsonData.has("BusinessStatus")){
						personInfoBean.setInfo_businessStatus(jsonData.getString("BusinessStatus"));
					}
					
					if(jsonData.has("CoverImage")){
						personInfoBean.setInfo_coverImage(jsonData.getString("CoverImage"));
					}
					
					if(jsonData.has("Verified")){
						personInfoBean.setInfo_verified(jsonData.getString("Verified"));
					}
					
					if(jsonData.has("IDCardFront")){
						personInfoBean.setInfo_IDCardFront(jsonData.getString("IDCardFront"));
					}
					
					if(jsonData.has("IDCardFrontOk")){
						personInfoBean.setInfo_IDCardFrontOk(jsonData.getString("IDCardFrontOk"));
					}
					
					if(jsonData.has("IDCardReverse")){
						personInfoBean.setInfo_IDCardReverse(jsonData.getString("IDCardReverse"));
					}
					
					if(jsonData.has("IDCardReverseOk")){
						personInfoBean.setInfo_IDCardReverseOk(jsonData.getString("IDCardReverseOk"));
					}
					
					if(jsonData.has("BusinessLicence")){
						personInfoBean.setInfo_businessLicence(jsonData.getString("BusinessLicence"));
					}
					
					if(jsonData.has("BusinessLicenceOk")){
						personInfoBean.setInfo_businessLicenceOk(jsonData.getString("BusinessLicenceOk"));
					}
					
					if(jsonData.has("FoodProductionLicence")){
						personInfoBean.setInfo_foodProductionLicence(jsonData.getString("FoodProductionLicence"));
					}
					
					if(jsonData.has("FoodProductionLicenceOk")){
						personInfoBean.setInfo_foodProductionLicenceOk(jsonData.getString("FoodProductionLicenceOk"));
					}
					
					if(jsonData.has("FoodHygieneLicence")){
						personInfoBean.setInfo_foodHygieneLicence(jsonData.getString("FoodHygieneLicence"));
					}
					
					if(jsonData.has("FoodHygieneLicenceOk")){
						personInfoBean.setInfo_foodHygieneLicenceOk(jsonData.getString("FoodHygieneLicenceOk"));
					}
					
					if(jsonData.has("FoodCirculationPermits")){
						personInfoBean.setInfo_foodCirculationPermits(jsonData.getString("FoodCirculationPermits"));
					}
					
					if(jsonData.has("FoodCirculationPermitsOk")){
						personInfoBean.setInfo_foodCirculationPermitsOk(jsonData.getString("FoodCirculationPermitsOk"));
					}
					
					if(jsonData.has("attend")){
						personInfoBean.setInfo_attend(jsonData.getString("attend"));
					}
					
					if(jsonData.has("fans")){
						personInfoBean.setInfo_fans(jsonData.getString("fans"));
					}
					
					if(jsonData.has("UserGrade")){
						personInfoBean.setInfo_userGrade(jsonData.getString("UserGrade"));
					}
					
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return personInfoBean;
	}

}
