package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.ConVertBean;
import com.ninetowns.tootoopluse.bean.PhotoSelectOrConvertBean;
import com.ninetowns.tootoopluse.bean.StoryDetailListBean;

/**
 * 
 * @ClassName: StroyWishParser
 * @Description: 编辑心愿故事列表解析
 * @author wuyulong
 * @date 2015-1-27 下午6:51:15
 * 
 */
public class StoryWishParser extends AbsParser<PhotoSelectOrConvertBean> {

	private String updateStoryId;
	private String storyId;
	private String storyName;

	public String getUpdateStoryId() {
		return updateStoryId;
	}

	public void setUpdateStoryId(String updateStoryId) {
		this.updateStoryId = updateStoryId;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public StoryWishParser(String str) {
		super(str);
	}

	@Override
	public PhotoSelectOrConvertBean getParseResult(String netStr) {
		PhotoSelectOrConvertBean photoSelectOrConvertBean = new PhotoSelectOrConvertBean();
		ConVertBean convertBean = new ConVertBean();
		List<StoryDetailListBean> wishList = new ArrayList<StoryDetailListBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);
					if (objList.has("UpdateStoryId")) {
						updateStoryId = objList.getString("UpdateStoryId");
						setUpdateStoryId(updateStoryId);
					}
					if (objList.has("StoryId")) {
						storyId = objList.getString("StoryId");
						setStoryId(storyId);
					}
					if (objList.has("StoryName")) {
						storyName = objList.getString("StoryName");
						convertBean.setStoryName(storyName);
						setStoryName(storyName);
					}
					if (objList.has("CategoryParentId")) {
						convertBean.setCategoryParentId(objList.getString("CategoryParentId"));
					}
					if (objList.has("CategoryId")) {
						 convertBean.setCategoryId(objList.getString("CategoryId"));
					}
					if (objList.has("ShoppingUrl")) {
						convertBean.setShoppingUrl(objList.getString("ShoppingUrl"));
					}
					if (objList.has("StoryCover")) {
						String storyConvert = objList.getString("StoryCover");
						JSONObject object = new JSONObject(storyConvert);
						if (object.has("StoryType")) {
							convertBean.setStoryType(object.getString("StoryType"));
						}
						if (object.has("CoverThumb")) {
							convertBean.setCoverThumb(object.getString("CoverThumb"));
						}
						if (object.has("CoverImg")) {
							convertBean.setCoverImg(object.getString("CoverImg"));
						}
						if (object.has("StoryVideoUrl")) {
							convertBean.setStoryVideoUrl(object.getString("StoryVideoUrl"));

						}
					}

					String strList = objList.getString("PageList");
					Gson gson = new Gson();
					wishList = gson.fromJson(strList,
							new TypeToken<List<StoryDetailListBean>>() {
							}.getType());
				}

			}
			photoSelectOrConvertBean.setConvertBean(convertBean);
			photoSelectOrConvertBean.setListBean(wishList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return photoSelectOrConvertBean;
	}
}
