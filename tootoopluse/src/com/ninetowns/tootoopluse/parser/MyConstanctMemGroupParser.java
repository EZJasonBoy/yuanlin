package com.ninetowns.tootoopluse.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootoopluse.bean.GridViewGroupBean;
import com.ninetowns.tootoopluse.bean.MyConstanctBean;

/**
 * 
 * @ClassName: MyConstanctMemGroupParser
 * @Description: 解析联系方式
 * @author wuyulong
 * @date 2015-6-17 下午4:52:21
 * 
 */
public class MyConstanctMemGroupParser extends AbsParser<List<MyConstanctBean>> {

	public MyConstanctMemGroupParser(String str) {
		super(str);
	}

	@Override
	public List<MyConstanctBean> getParseResult(String netStr) {
		List<MyConstanctBean> listFreeGroupBean = new ArrayList<MyConstanctBean>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);

					String strList = objList.getString("List");
					Gson gson = new Gson();
					listFreeGroupBean = gson.fromJson(strList,
							new TypeToken<List<MyConstanctBean>>() {
							}.getType());
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listFreeGroupBean;
	}

}
