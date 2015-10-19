package com.ninetowns.tootooplus.bean;

public class CreateActStoryBean {
	
	/**创建故事的用户id**/
	private String create_act_userId;
	/**创建故事的用户名**/
	private String create_act_userName;
	
	private String create_act_story_id;
	
	private String create_act_story_Cover_thumb;
	
	private String create_act_story_story_name;

	public String getCreate_act_story_id() {
		return create_act_story_id;
	}

	public void setCreate_act_story_id(String create_act_story_id) {
		this.create_act_story_id = create_act_story_id;
	}

	public String getCreate_act_story_Cover_thumb() {
		return create_act_story_Cover_thumb;
	}

	public void setCreate_act_story_Cover_thumb(String create_act_story_Cover_thumb) {
		this.create_act_story_Cover_thumb = create_act_story_Cover_thumb;
	}

	public String getCreate_act_story_story_name() {
		return create_act_story_story_name;
	}

	public void setCreate_act_story_story_name(String create_act_story_story_name) {
		this.create_act_story_story_name = create_act_story_story_name;
	}

	public String getCreate_act_userId() {
		return create_act_userId;
	}

	public void setCreate_act_userId(String create_act_userId) {
		this.create_act_userId = create_act_userId;
	}

	public String getCreate_act_userName() {
		return create_act_userName;
	}

	public void setCreate_act_userName(String create_act_userName) {
		this.create_act_userName = create_act_userName;
	}

	@Override
	public String toString() {
		return "CreateActStoryBean [create_act_userId=" + create_act_userId
				+ ", create_act_userName=" + create_act_userName
				+ ", create_act_story_id=" + create_act_story_id
				+ ", create_act_story_Cover_thumb="
				+ create_act_story_Cover_thumb
				+ ", create_act_story_story_name="
				+ create_act_story_story_name + "]";
	}
	

}
