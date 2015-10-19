package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.ContactListBean;
import com.ninetowns.tootooplus.bean.FreeCommentBean;
/**
 * 
* @ClassName: ContactListParser 
* @Description: 我的联系人
* @author wuyulong
* @date 2015-4-15 下午4:57:56 
*
 */
public class ContactListParser extends AbsParser<List<ContactListBean>>{



    public ContactListParser(String str) {
		super(str);
	}

	@Override
    public List<ContactListBean> getParseResult(String netStr) {
        List<ContactListBean> commentList=new ArrayList<ContactListBean>();
        try {
            JSONObject obj=new JSONObject(netStr);
            if(obj.has("Status")&&obj.getString("Status").equals("1")){
                if(obj.has("Data")){
                    String strData=obj.getString("Data");
                    JSONObject objList=new JSONObject(strData);
                    String strList=objList.getString("List");
                    Gson gson=new Gson();
                    commentList=gson.fromJson(strList, new TypeToken<List<ContactListBean>>() {
                    }.getType());
                }
               
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
