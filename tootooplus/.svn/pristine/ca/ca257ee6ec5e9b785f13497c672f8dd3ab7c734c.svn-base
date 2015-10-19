package com.ninetowns.tootooplus.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.bean.NoticeSuccess;

public class MyNoticeParser extends AbsParser<NoticeSuccess> {


	public MyNoticeParser(String str) {
		super(str);
	}

	@Override
	public List<NoticeSuccess> getParseResult(String netStr) {

		List<NoticeSuccess> noticeSuccessList = new ArrayList<NoticeSuccess>();
		try {
			JSONObject obj = new JSONObject(netStr);
			if (obj.has("Status") && obj.getString("Status").equals("1")) {
				if (obj.has("Data")) {
					String strData = obj.getString("Data");
					JSONObject objList = new JSONObject(strData);
					String strList = objList.getString("List");

					Gson gson = new Gson();
					noticeSuccessList = gson.fromJson(strList,
							new TypeToken<List<NoticeSuccess>>() {
							}.getType());
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return noticeSuccessList;

	}



}
