package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
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

public class WishDetailParser extends AbsParser<WishDetailBean> {

	public WishDetailParser(String str) {
		super(str);
	}

	@Override
	public WishDetailBean getParseResult(String netStr) {
		WishDetailBean wishDetailBean = new WishDetailBean();
		List<WishDetailPageListBean> wishList = new ArrayList<WishDetailPageListBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);
					String strList = objList.getString("PageList");
					Gson gson = new Gson();
					wishList = gson.fromJson(strList,
							new TypeToken<List<WishDetailPageListBean>>() {
							}.getType());
					if (objList.has("StoryName")) {
						wishDetailBean.setStoryName(objList.getString("StoryName"));

					}
					if (objList.has("CreateDate")) {
						wishDetailBean.setCreateDate(objList.getString("CreateDate"));

					}
					if (objList.has("CreateUser")) {
						String strUser=objList.getString("CreateUser");
						JSONObject   jsonUser=new JSONObject(strUser);
						// UserId
						// LogoUrl
						// UserName
						// Medal
						// UserGrade
						if(jsonUser.has("UserId")){
							wishDetailBean.setUserId(jsonUser.getString("UserId"));
						}
						if(jsonUser.has("LogoUrl")){
							
							wishDetailBean.setLogoUrl(jsonUser.getString("LogoUrl"));
						}
						if(jsonUser.has("UserName")){
							
							wishDetailBean.setUserName(jsonUser.getString("UserName"));
						}
						if(jsonUser.has("Medal")){
							wishDetailBean.setMedal(jsonUser.getString("Medal"));
						}
						if(jsonUser.has("UserGrade")){
							wishDetailBean.setUserGrade(jsonUser.getString("UserGrade"));
						}
					}
					if (objList.has("StoryCover")) {
						String storyCovet=objList.getString("StoryCover");
						JSONObject   jsonobject=new JSONObject(storyCovet);
						if(jsonobject.has("StoryType")){
							wishDetailBean.setStoryType(jsonobject.getString("StoryType"));
						}
						if(jsonobject.has("CoverThumb")){
							wishDetailBean.setCoverThumb(jsonobject.getString("CoverThumb"));
						}
						if(jsonobject.has("CoverImg")){
							wishDetailBean.setCoverImg(jsonobject.getString("CoverImg"));
						}
						if(jsonobject.has("StoryVideoUrl")){
							wishDetailBean.setStoryVideoUrl(jsonobject.getString("StoryVideoUrl"));
						}

					}
					if (objList.has("CountFree")) {
						wishDetailBean.setCountFree(objList.getString("CountFree"));

					}
					if (objList.has("IsRecommend")) {
						wishDetailBean.setIsRecommend(objList.getString("IsRecommend"));

					}
					if (objList.has("IsApproved")) {
						wishDetailBean.setIsApproved(objList.getString("IsApproved"));

					}
					if (objList.has("ShoppingUrl")) {
						wishDetailBean.setShoppingUrl(objList.getString("ShoppingUrl"));

					}
					if (objList.has("Free")) {
						wishDetailBean.setFree(objList.getString("Free"));

					}
					if (objList.has("Type")) {
						wishDetailBean.setType(objList.getString("Type"));

					}
					if (objList.has("CommentLike")) {
						wishDetailBean.setCommentLike(objList.getString("CommentLike"));

					}
					if (objList.has("CountRecommend")) {
						wishDetailBean.setCountRecommend(objList.getString("CountRecommend"));

					}
					if (objList.has("CountLike")) {
						wishDetailBean.setCountLike(objList.getString("CountLike"));

					}
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		wishDetailBean.setWishList(wishList);
		return wishDetailBean;
	}
}
