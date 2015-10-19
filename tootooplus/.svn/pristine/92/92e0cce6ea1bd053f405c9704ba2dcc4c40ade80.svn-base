package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.CreateActFirstStepBean;
import com.ninetowns.tootooplus.bean.CreateActStoryBean;
import com.ninetowns.tootooplus.bean.CreateActiveUserBean;

public class CreateActFirstStepParser extends AbsParser<CreateActFirstStepBean> {

	public CreateActFirstStepParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public CreateActFirstStepBean getParseResult(String netStr) {
		
		CreateActFirstStepBean createActFirstStepBean = new CreateActFirstStepBean();
		try {
			JSONObject jsonObj = new JSONObject(netStr);
			if(jsonObj.has("Status") && jsonObj.getString("Status").equals("1")){
				
				if(jsonObj.has("Data")){
					JSONObject jsonData = jsonObj.getJSONObject("Data");
					if(jsonData.has("UserId")){
						createActFirstStepBean.setAct_first_step_user_id(jsonData.getString("UserId"));
					}
					if(jsonData.has("UserName")){
						createActFirstStepBean.setAct_first_step_user_name(jsonData.getString("UserName"));
					}
					
					
					if(jsonData.has("StoryList")){
						List<CreateActStoryBean> act_first_step_story_list = new ArrayList<CreateActStoryBean>();
						JSONArray jsonStory = jsonData.getJSONArray("StoryList");
						act_first_step_story_list = getStoryList(jsonStory);
						createActFirstStepBean.setAct_first_step_story_list(act_first_step_story_list);
					}
					
					
					if(jsonData.has("userEList")){
						List<CreateActiveUserBean> act_first_step_user_list = new ArrayList<CreateActiveUserBean>();
						JSONArray jsonUser = jsonData.getJSONArray("userEList");
						for(int i = 0; i <jsonUser.length(); i++){
							CreateActiveUserBean createActiveUserBean = new CreateActiveUserBean();
							JSONObject jsonUserItem = jsonUser.getJSONObject(i);
							if(jsonUserItem.has("UserId")){
								createActiveUserBean.setCreate_act_user_id(jsonUserItem.getString("UserId"));
							}
							
							if(jsonUserItem.has("UserName")){
								createActiveUserBean.setCreate_act_user_name(jsonUserItem.getString("UserName"));
							}
							
							List<CreateActStoryBean> create_act_story_list = new ArrayList<CreateActStoryBean>();
							if(jsonUserItem.has("StoryList")){
								JSONArray jsonUserItemArray = jsonUserItem.getJSONArray("StoryList");
								create_act_story_list = getStoryList(jsonUserItemArray);
								createActiveUserBean.setCreate_act_story_list(create_act_story_list);
							}
							act_first_step_user_list.add(createActiveUserBean);
						}
						createActFirstStepBean.setAct_first_step_user_list(act_first_step_user_list);
					}
				}
				
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return createActFirstStepBean;
    }


	
	private List<CreateActStoryBean> getStoryList(JSONArray jsonStory) throws JSONException{
		List<CreateActStoryBean> list = new ArrayList<CreateActStoryBean>();
		for(int i = 0; i < jsonStory.length(); i++){
			JSONObject jsonItem = jsonStory.getJSONObject(i);
			CreateActStoryBean createActStoryBean = new CreateActStoryBean();
			if(jsonItem.has("StoryId")){
				createActStoryBean.setCreate_act_story_id(jsonItem.getString("StoryId"));
			}
			
			if(jsonItem.has("CoverThumb")){
				createActStoryBean.setCreate_act_story_Cover_thumb(jsonItem.getString("CoverThumb"));
			}
			
			if(jsonItem.has("StoryName")){
				createActStoryBean.setCreate_act_story_story_name(jsonItem.getString("StoryName"));
			}
			list.add(createActStoryBean);
		}
		
		return list;
	}
}
