package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.RemarkStoryBean;

public class RemarkStoryParser extends AbsParser<List<RemarkStoryBean>> {
	
	//总记录数
	private int totalCount;
	//总共页数
	private int totalPage;

	public RemarkStoryParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

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
	
	@Override
	public List<RemarkStoryBean> getParseResult(String netStr) {
		// TODO Auto-generated method stub
		List<RemarkStoryBean> remarkStoryList = new ArrayList<RemarkStoryBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if(obj.has("Status")&&obj.getString("Status").equals("1")){
	            if(obj.has("Data")){
	            	JSONObject jsonData = obj.getJSONObject("Data");
	            	if(jsonData.has("List")){
//	            		JSONArray jsonList = jsonData.getJSONArray(jsonData.getString("List"));
	            		JSONArray jsonList = jsonData.getJSONArray("List");
	            		for(int i = 0; i < jsonList.length(); i++) {
	            			RemarkStoryBean remarkStoryBean = new RemarkStoryBean();
	            			JSONObject jsonItem = jsonList.getJSONObject(i);
	            			if(jsonItem.has("StoryId")){
	            				remarkStoryBean.setRemark_storyId(jsonItem.getString("StoryId"));
	            			}
	            			if(jsonItem.has("StoryName")){
	            				remarkStoryBean.setRemark_storyName(jsonItem.getString("StoryName"));
	            			}
	            			
							if(jsonItem.has("StoryType")){
								remarkStoryBean.setRemark_storyType(jsonItem.getString("StoryType"));
							}
							if(jsonItem.has("CoverThumb")){
								remarkStoryBean.setRemark_coverThumb(jsonItem.getString("CoverThumb"));
							}
							if(jsonItem.has("StoryVideoUrl")){
	            				remarkStoryBean.setRemark_storyVideoUrl(jsonItem.getString("StoryVideoUrl"));
	            			}
							if(jsonItem.has("CountLike")){
								remarkStoryBean.setRemark_countLike(jsonItem.getString("CountLike"));
							}
							if(jsonItem.has("CountRecommend")){
								remarkStoryBean.setRemark_countRecommend(jsonItem.getString("CountRecommend"));
							}
							if(jsonItem.has("UserId")){
								remarkStoryBean.setRemark_userId(jsonItem.getString("UserId"));
							}
							
							if(jsonItem.has("UserName")){
								remarkStoryBean.setRemark_userName(jsonItem.getString("UserName"));
							}
							if(jsonItem.has("LogoUrl")){
								remarkStoryBean.setRemark_logoUrl(jsonItem.getString("LogoUrl"));
							}
							
							if(jsonItem.has("UserGrade")){
								remarkStoryBean.setRemark_userGrade(jsonItem.getString("UserGrade"));
							}
							
							if(jsonItem.has("ActivityId")){
								remarkStoryBean.setRemark_actId(jsonItem.getString("ActivityId"));
							}
							
							if(jsonItem.has("ActivityName")){
								remarkStoryBean.setRemark_actName(jsonItem.getString("ActivityName"));
							}
							if(jsonItem.has("IsRecommend")){
								remarkStoryBean.setIsRecommend(jsonItem.getString("IsRecommend"));
							}
							if(jsonItem.has("WishStoryId")){
								remarkStoryBean.setWishStoryId(jsonItem.getString("WishStoryId"));
							}
							
							remarkStoryList.add(remarkStoryBean);
	            		}
	            		
	    
	            		
	            	}
	            	 if(jsonData.has("TotalCount")){
	                 	setTotalCount(jsonData.getInt("TotalCount"));
	                 }
	                 if(jsonData.has("TotalPage")){
	                 	setTotalPage(jsonData.getInt("TotalPage"));
	                 }
	            }
	        }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		return remarkStoryList;
	}

}
