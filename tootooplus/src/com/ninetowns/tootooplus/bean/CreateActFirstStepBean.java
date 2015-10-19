package com.ninetowns.tootooplus.bean;

import java.util.List;

public class CreateActFirstStepBean {
	
	private String act_first_step_user_id;
	
	private String act_first_step_user_name;
	
	private List<CreateActStoryBean> act_first_step_story_list;
	
	private List<CreateActiveUserBean> act_first_step_user_list;

	public String getAct_first_step_user_id() {
		return act_first_step_user_id;
	}

	public void setAct_first_step_user_id(String act_first_step_user_id) {
		this.act_first_step_user_id = act_first_step_user_id;
	}

	public String getAct_first_step_user_name() {
		return act_first_step_user_name;
	}

	public void setAct_first_step_user_name(String act_first_step_user_name) {
		this.act_first_step_user_name = act_first_step_user_name;
	}

	public List<CreateActStoryBean> getAct_first_step_story_list() {
		return act_first_step_story_list;
	}

	public void setAct_first_step_story_list(
			List<CreateActStoryBean> act_first_step_story_list) {
		this.act_first_step_story_list = act_first_step_story_list;
	}

	public List<CreateActiveUserBean> getAct_first_step_user_list() {
		return act_first_step_user_list;
	}

	public void setAct_first_step_user_list(
			List<CreateActiveUserBean> act_first_step_user_list) {
		this.act_first_step_user_list = act_first_step_user_list;
	}

	@Override
	public String toString() {
		return "CreateActFirstStepBean [act_first_step_user_id="
				+ act_first_step_user_id + ", act_first_step_user_name="
				+ act_first_step_user_name + ", act_first_step_story_list="
				+ act_first_step_story_list + ", act_first_step_user_list="
				+ act_first_step_user_list + "]";
	}
	

}
