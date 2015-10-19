package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class PhotoSelectOrConvertBean implements Serializable{
	private HashMap<String,Integer> mHashMap;//记录位置的hashmap
    public HashMap<String, Integer> getmHashMap() {
		return mHashMap;
	}
	public void setmHashMap(HashMap<String, Integer> mHashMap) {
		this.mHashMap = mHashMap;
	}
	public ConVertBean getConvertBean() {
        return convertBean;
    }
    public void setConvertBean(ConVertBean convertBean) {
        this.convertBean = convertBean;
    }

    public List<StoryDetailListBean> getListBean() {
        return listBean;
    }
    public void setListBean(List<StoryDetailListBean> listBean) {
        this.listBean = listBean;
    }

    private ConVertBean  convertBean;
    private List<StoryDetailListBean>  listBean;

}
