package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.AreSubBean;
import com.ninetowns.tootoopluse.bean.ConVertBean;
import com.ninetowns.tootoopluse.bean.HomePageBean;
import com.ninetowns.tootoopluse.bean.PostAresBean;
/**
 * 
* @ClassName: HomePageParser 
* @Description: 活动解析类
* @author wuyulong
* @date 2015-3-27 下午2:18:16 
*
 */
public class HomePageParser extends AbsParser<List<HomePageBean>> {
	private int totalCount;//总记录数
	private int totalPage;//总共页数

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
	public HomePageParser(String str) {
		super(str);
	}

	@Override
	public List<HomePageBean> getParseResult(String netStr) {
		List<HomePageBean> homePageList = new ArrayList<HomePageBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);
					String strList = objList.getString("List");
					if(objList.has("TotalCount")){
						setTotalCount(objList.getInt("TotalCount"));
					}
					if(objList.has("TotalPage")){
						setTotalPage(objList.getInt("TotalPage"));
					}
					
					JSONArray objArr = new JSONArray(strList);
					for (int i = 0; i < objArr.length(); i++) {
						HomePageBean homebean = new HomePageBean();
						JSONObject jsonobj = (JSONObject) objArr.get(i);
						if (jsonobj.has("ActivityId")) {
							homebean.setActivityId(jsonobj
									.getString("ActivityId"));
						}
						if (jsonobj.has("ActivityName")) {
							homebean.setActivityName(jsonobj
									.getString("ActivityName"));
						}
						if (jsonobj.has("Type")) {
							homebean.setType(jsonobj.getString("Type"));
						}
						if (jsonobj.has("CountParticipant")) {
							homebean.setCountParticipant(jsonobj
									.getString("CountParticipant"));
						}
						if (jsonobj.has("CountCollent")) {

							homebean.setCountCollent(jsonobj
									.getString("CountCollent"));
						}
						if (jsonobj.has("Location")) {
							homebean.setLocation(jsonobj.getString("Location"));
						}
						if(jsonobj.has("Place")){
							homebean.setPlace(jsonobj.getString("Place"));
						}
						if(jsonobj.has("TopCoefficient")){
							homebean.setTopCoefficient(jsonobj.getInt("TopCoefficient"));
						}
						if (jsonobj.has("PostAres")) {
							List<PostAresBean> cityList = new ArrayList<PostAresBean>();//一级城市
						
//							List<List<AreSubBean>> areMainSubList = new ArrayList<List<AreSubBean>>();
							String strPostAre = jsonobj.getString("PostAres");
							if(!TextUtils.isEmpty(strPostAre)){
								JSONArray cityArray = new JSONArray(strPostAre);//一级城市
								for (int j = 0; j < cityArray.length(); j++) {
									PostAresBean postArresBean = new PostAresBean();//承载一级城市
									JSONObject cityJson = (JSONObject) cityArray
											.get(j);
									if (cityJson.has("AreaId")) {
										postArresBean.setAreaId(cityJson
												.getString("AreaId"));
									}
									if (cityJson.has("AreaName")) {
										postArresBean.setAreaName(cityJson
												.getString("AreaName"));
									}
									if (cityJson.has("AreaSub")) {
										String strSub = cityJson
												.getString("AreaSub");//二级城市
										if(!TextUtils.isEmpty(strSub)){
											JSONArray citySubArray = new JSONArray(
													strSub);
											List<AreSubBean> areSubList = new ArrayList<AreSubBean>();//每次创建一个新的
											for (int k = 0; k < citySubArray.length(); k++) {
												AreSubBean areSubBean = new AreSubBean();
												JSONObject citySubJson = (JSONObject) citySubArray
														.get(k);
												if (citySubJson.has("AreaId")) {
													areSubBean.setAreaId(citySubJson
															.getString("AreaId"));
												}
												if (citySubJson.has("AreaName")) {
													areSubBean.setAreaName(citySubJson
															.getString("AreaName"));
												}
												areSubList.add(areSubBean);
											}
//											areMainSubList.add(areSubList);
//											supcitypageList.add(areMainSubList);
											postArresBean.setSupcitypageList(areSubList);	
										}
										
									}
									cityList.add(postArresBean);//一级城市的集合
								}
								
							}else{
								//当为null
							}
							
//							citypageList.add(cityList);
							homebean.setPostAresList(cityList);

						}
						if (jsonobj.has("UserId")) {
							homebean.setUserId(jsonobj.getString("UserId"));
						}
						if (jsonobj.has("UserName")) {

							homebean.setUserName(jsonobj.getString("UserName"));
						}
						if (jsonobj.has("LogoUrl")) {
							homebean.setLogoUrl(jsonobj.getString("LogoUrl"));
						}
						if (jsonobj.has("UserGrade")) {
							homebean.setUserGrade(jsonobj
									.getString("UserGrade"));
						}
						if (jsonobj.has("ActivityStatus")) {

							homebean.setActivityStatus(jsonobj
									.getString("ActivityStatus"));
						}
						if (jsonobj.has("RemainingTime")) {
							homebean.setRemainingTime(jsonobj
									.getString("RemainingTime"));
						}
						if (jsonobj.has("StoryList")) {
							List<ConVertBean> activityConvertLisgt = new ArrayList<ConVertBean>();
							String storyList = jsonobj.getString("StoryList");
							JSONArray jsonArray = new JSONArray(storyList);
							for (int j = 0; j < jsonArray.length(); j++) {
								ConVertBean convertBean = new ConVertBean();
								JSONObject jsonConvert = (JSONObject) jsonArray
										.get(j);
								if (jsonConvert.has("StoryId")) {
									convertBean.setStoryId(jsonConvert
											.getString("StoryId"));
								}
								if (jsonConvert.has("StoryName")) {

									convertBean.setStoryName(jsonConvert
											.getString("StoryName"));
								}
								if (jsonConvert.has("StoryType")) {
									convertBean.setStoryType(jsonConvert
											.getString("StoryType"));
								}
								if (jsonConvert.has("CoverThumb")) {

									convertBean.setCoverThumb(jsonConvert
											.getString("CoverThumb"));
								}
								if (jsonConvert.has("StoryVideoUrl")) {
									convertBean.setStoryVideoUrl(jsonConvert
											.getString("StoryVideoUrl"));
								}
								activityConvertLisgt.add(convertBean);

							}
//							storypageList.add(activityConvertLisgt);
							homebean.setConvertBean(activityConvertLisgt);
							homePageList.add(homebean);
						}
					}
					
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return homePageList;
	}
}
