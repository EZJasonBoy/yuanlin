package com.ninetowns.tootoopluse.bean;

import java.util.List;

public class CreateActiveUserBean {
	
	private String create_act_user_id;
	
	private String create_act_user_name;
	
	private List<CreateActStoryBean> create_act_story_list;

	public String getCreate_act_user_id() {
		return create_act_user_id;
	}

	public void setCreate_act_user_id(String create_act_user_id) {
		this.create_act_user_id = create_act_user_id;
	}

	public String getCreate_act_user_name() {
		return create_act_user_name;
	}

	public void setCreate_act_user_name(String create_act_user_name) {
		this.create_act_user_name = create_act_user_name;
	}

	public List<CreateActStoryBean> getCreate_act_story_list() {
		return create_act_story_list;
	}

	public void setCreate_act_story_list(
			List<CreateActStoryBean> create_act_story_list) {
		this.create_act_story_list = create_act_story_list;
	}

	@Override
	public String toString() {
		return "CreateActiveUserBean [create_act_user_id=" + create_act_user_id
				+ ", create_act_user_name=" + create_act_user_name
				+ ", create_act_story_list=" + create_act_story_list + "]";
	}


}
