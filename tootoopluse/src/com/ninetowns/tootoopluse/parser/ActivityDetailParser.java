package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.ActivityDetailBean;
import com.ninetowns.tootoopluse.bean.ActivityInfoBean;
import com.ninetowns.tootoopluse.bean.AreSubBean;
import com.ninetowns.tootoopluse.bean.PostAresBean;
import com.ninetowns.tootoopluse.bean.WishDetailBean;
import com.ninetowns.tootoopluse.bean.WishDetailPageListBean;

/**
 * 
 * @ClassName: WishDetailParser
 * @Description: 解析心愿详情的数据
 * @author wuyulong
 * @date 2015-2-5 上午10:51:03
 * 
 */

public class ActivityDetailParser extends AbsParser<ActivityDetailBean> {

	public ActivityDetailParser(String str) {
		super(str);
	}

	@Override
	public ActivityDetailBean getParseResult(String netStr) {
		ActivityDetailBean activityBean = new ActivityDetailBean();
		List<WishDetailBean> listWishDetailBean = new ArrayList<WishDetailBean>();
		ActivityInfoBean activityInfoBean = new ActivityInfoBean();
		List<PostAresBean> cityList = new ArrayList<PostAresBean>();
		List<AreSubBean> areSubList = new ArrayList<AreSubBean>();
		List<WishDetailPageListBean> wishList = new ArrayList<WishDetailPageListBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);
					if (objList.has("ActivityInfo")) {
						String activityInfo = objList.getString("ActivityInfo");
						JSONObject jsobjActInfo = new JSONObject(activityInfo);
						if (jsobjActInfo.has("DateRegisterStart")) {
							activityInfoBean.setDateRegisterStart(jsobjActInfo
									.getString("DateRegisterStart"));
						}
						if (jsobjActInfo.has("DateRegisterEnd")) {
							activityInfoBean.setDateRegisterEnd(jsobjActInfo
									.getString("DateRegisterEnd"));
						}
						if (jsobjActInfo.has("DateStart")) {

							activityInfoBean.setDateStart(jsobjActInfo
									.getString("DateStart"));
						}
						if (jsobjActInfo.has("DateEnd")) {
							activityInfoBean.setDateEnd(jsobjActInfo
									.getString("DateEnd"));
						}
						if (jsobjActInfo.has("CountParticipant")) {

							activityInfoBean.setCountParticipant(jsobjActInfo
									.getString("CountParticipant"));
						}
						if (jsobjActInfo.has("Location")) {

							activityInfoBean.setLocation(jsobjActInfo
									.getString("Location"));
						}
						if (jsobjActInfo.has("Place")) {
							activityInfoBean.setPlace(jsobjActInfo
									.getString("Place"));
						}
						if (jsobjActInfo.has("PlaceMap")) {
							activityInfoBean.setPlaceMap(jsobjActInfo
									.getString("PlaceMap"));
						}
						if (jsobjActInfo.has("SupplierName")) {
							activityInfoBean.setSupplierName(jsobjActInfo
									.getString("SupplierName"));

						}
						if (jsobjActInfo.has("DateUpdate")) {

							activityInfoBean.setDateUpdate(jsobjActInfo
									.getString("DateUpdate"));
						}
						if (jsobjActInfo.has("Type")) {
							activityInfoBean.setType(jsobjActInfo
									.getString("Type"));
						}
						if (jsobjActInfo.has("Category")) {

							activityInfoBean.setCategory(jsobjActInfo
									.getString("Category"));
						}
						if (jsobjActInfo.has("Postage")) {
							activityInfoBean.setPostage(jsobjActInfo
									.getString("Postage"));
						}
						if (jsobjActInfo.has("PostAres")) {
							String strPostAre = jsobjActInfo
									.getString("PostAres");
							JSONArray cityArray = new JSONArray(strPostAre);
							for (int j = 0; j < cityArray.length(); j++) {
								PostAresBean postArresBean = new PostAresBean();
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
											.getString("AreaSub");
									JSONArray citySubArray = new JSONArray(
											strSub);
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
									postArresBean.setAreSubList(areSubList);
								}
								cityList.add(postArresBean);
							}
							activityInfoBean.setPostAresList(cityList);

						}

					}
					if (objList.has("ActivityId")) {

						activityBean.setActivityId(objList
								.getString("ActivityId"));

					}
					if (objList.has("ActivityName")) {

						activityBean.setActivityName(objList
								.getString("ActivityName"));

					}
					if (objList.has("Type")) {

						activityBean.setType(objList.getString("Type"));

					}
					if (objList.has("UserId")) {

						activityBean.setUserId(objList.getString("UserId"));

					}
					if (objList.has("UserName")) {
						activityBean.setUserName(objList.getString("UserName"));

					}
					if (objList.has("LogoUrl")) {
						activityBean.setLogoUrl(objList.getString("LogoUrl"));

					}
					if (objList.has("UserGrade")) {

						activityBean.setUserGrade(objList
								.getString("UserGrade"));

					}
					if (objList.has("DateUpdate")) {
						activityBean.setDateUpdate(objList
								.getString("DateUpdate"));

					}
					if (objList.has("CountCollent")) {
						activityBean.setCountCollent(objList
								.getString("CountCollent"));

					}
					if(objList.has("ActivityStatus")){
						activityBean.setActivityStatus(objList.getString("ActivityStatus"));
					}
					if (objList.has("Chat")) {

						activityBean.setChat(objList.getString("Chat"));

					}
					if (objList.has("Comment")) {
						activityBean.setComment(objList.getString("Comment"));

					}
					if (objList.has("CountRecommend")) {
						activityBean.setCountRecommend(objList
								.getString("CountRecommend"));

					}
					if (objList.has("GroupMembers")) {
						activityBean.setGroupMembers(objList
								.getString("GroupMembers"));

					}
					if(objList.has("TopCoefficient")){
						int topCoefficient = objList.getInt("TopCoefficient");
						activityBean.setTopCoefficient(topCoefficient);
						
					}
					String strList = objList.getString("StoryList");
					JSONArray jsonArray = new JSONArray(strList);
					for (int i = 0; i < jsonArray.length(); i++) {
						WishDetailBean wishDetailBean = new WishDetailBean();
						JSONObject objStory = (JSONObject) jsonArray.get(i);
						if (objStory.has("PageList")) {
							String strPageList = objStory.getString("PageList");
							Gson gson = new Gson();
							wishList = gson
									.fromJson(
											strPageList,
											new TypeToken<List<WishDetailPageListBean>>() {
											}.getType());
							wishDetailBean.setWishList(wishList);
						}

						if (objStory.has("StoryCover")) {
							String storyCovet = objStory
									.getString("StoryCover");
							JSONObject jsonobject = new JSONObject(storyCovet);
							if (jsonobject.has("StoryType")) {
								wishDetailBean.setStoryType(jsonobject
										.getString("StoryType"));
							}
							if (jsonobject.has("CoverThumb")) {
								wishDetailBean.setCoverThumb(jsonobject
										.getString("CoverThumb"));
							}
							if (jsonobject.has("CoverImg")) {
								wishDetailBean.setCoverImg(jsonobject
										.getString("CoverImg"));
							}
							if (jsonobject.has("StoryVideoUrl")) {
								wishDetailBean.setStoryVideoUrl(jsonobject
										.getString("StoryVideoUrl"));
							}
							if(jsonobject.has("ShoppingUrl")){
								wishDetailBean.setShoppingUrl(jsonobject.getString("ShoppingUrl"));
							}

						}
						if (objStory.has("CreateUser")) {
							String createUser = objStory
									.getString("CreateUser");
							JSONObject jsonObject = new JSONObject(createUser);

							if (jsonObject.has("UserId")) {
								wishDetailBean.setUserId(jsonObject.getString("UserId"));

							}
							if (jsonObject.has("LogoUrl")) {
								wishDetailBean.setLogoUrl(jsonObject.getString("LogoUrl"));

							}
							if (jsonObject.has("UserName")) {
								wishDetailBean.setUserName(jsonObject.getString("UserName"));
								

							}
							if (jsonObject.has("UserGrade")) {
								wishDetailBean.setUserGrade(jsonObject.getString("UserGrade"));

							}

						}
						if (objStory.has("StoryId")) {
							wishDetailBean.setStoryId(objStory
									.getString("StoryId"));
						}
						if (objStory.has("StoryName")) {
							wishDetailBean.setStoryName(objStory
									.getString("StoryName"));
						}
						
						if(objStory.has("Comment")){
							wishDetailBean.setComment(objStory.getString("Comment"));
						}
						if(objStory.has("CountRecommend")){
							wishDetailBean.setCountRecommend(objStory.getString("CountRecommend"));
						}

						listWishDetailBean.add(wishDetailBean);
					}

				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		activityBean.setListWish(listWishDetailBean);
		activityBean.setActivityInfoBean(activityInfoBean);
		return activityBean;
	}
}
