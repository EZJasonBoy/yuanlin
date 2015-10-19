package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.FreeCommentBean;
/**
 * 
* @ClassName: FreeCommentParser 
* @Description: 点评 首页
* @author wuyulong
* @date 2015-4-15 下午4:57:56 
*
 */
public class FreeCommentParser extends AbsParser<List<FreeCommentBean>>{
	private int totalCount;//总记录数
	private int totalPage;//总共页数
	private int storyDraftCount;//草稿
	private String NoCommentCount;//活动未点评数量

    public String getNoCommentCount() {
		return NoCommentCount;
	}

	public void setNoCommentCount(String noCommentCount) {
		NoCommentCount = noCommentCount;
	}

	public int getStoryDraftCount() {
		return storyDraftCount;
	}

	public void setStoryDraftCount(int storyDraftCount) {
		this.storyDraftCount = storyDraftCount;
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

	public FreeCommentParser(String str) {
        super(str);
    }

    @Override
    public List<FreeCommentBean> getParseResult(String netStr) {
        List<FreeCommentBean> commentList=new ArrayList<FreeCommentBean>();
        try {
            JSONObject obj=new JSONObject(netStr);
            if(obj.has("Status")&&obj.getString("Status").equals("1")){
                if(obj.has("Data")){
                    String strData=obj.getString("Data");
                    JSONObject objList=new JSONObject(strData);
                    String strList=objList.getString("List");
                    if(objList.has("TotalCount")){
                    	setTotalCount(objList.getInt("TotalCount"));
                    }
                    if(objList.has("TotalPage")){
                    	setTotalPage(objList.getInt("TotalPage"));
                    }
                    if(objList.has("StoryDraftCount")){
                    	setStoryDraftCount(objList.getInt("StoryDraftCount"));
                    }
                    if(objList.has("NoCommentCount")){
                    	setNoCommentCount(objList.getString("NoCommentCount"));
                    }
                    Gson gson=new Gson();
                    commentList=gson.fromJson(strList, new TypeToken<List<FreeCommentBean>>() {
                    }.getType());
                }
               
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
