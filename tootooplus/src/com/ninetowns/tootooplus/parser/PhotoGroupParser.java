package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.GridViewGroupBean;
/**
 * 
* @ClassName: PhotoGroupParser 
* @Description: 用户组团解析
* @author wuyulong
* @date 2015-3-25 下午3:38:49 
*
 */
public class PhotoGroupParser extends AbsParser<List<GridViewGroupBean>>{
	private int totalCount;//总记录数
	private int totalPage;//总共页数

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

	public PhotoGroupParser(String str) {
        super(str);
    }

    @Override
    public List<GridViewGroupBean> getParseResult(String netStr) {
        List<GridViewGroupBean> gridViewGroupist=new ArrayList<GridViewGroupBean>();
        try {
            JSONObject obj=new JSONObject(netStr);
            if(obj.has("Status")&&obj.getString("Status").equals("1")){
                if(obj.has("Data")){
                    String strData=obj.getString("Data");
                    JSONObject objList=new JSONObject(strData);
                    String strList=objList.getString("List");
                    Gson gson=new Gson();
                    gridViewGroupist=gson.fromJson(strList, new TypeToken<List<GridViewGroupBean>>() {
                    }.getType());
                }
                if(obj.has("TotalCount")){
                	setTotalCount(obj.getInt("TotalCount"));
                }
                if(obj.has("TotalPage")){
                	setTotalPage(obj.getInt("TotalPage"));
                }
                
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gridViewGroupist;
    }
}
