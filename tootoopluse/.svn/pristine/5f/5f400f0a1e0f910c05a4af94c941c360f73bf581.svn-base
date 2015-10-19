package com.ninetowns.tootoopluse.parser;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.util.LogUtils;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.JsonTools;
import com.ninetowns.tootoopluse.bean.NoticeBean;

public class NoticeParser extends AbsParser<NoticeBean> {


	public NoticeParser(String str) {
		super(str);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getParseResult(String netStr) {
		try {
			JSONObject object = new JSONObject(netStr);
			if (object != null) {
				if (object.has("TotalPage")) {
					totalPage = object.getInt("TotalPage");
				}
				if (object.has("Data")) {
					JSONObject data = object.getJSONObject("Data");
					if (null != data && data.has("List")) {
						String list = data.getString("List");
						List<NoticeBean> noticeBeans = JsonTools.jsonObjArray(
								list, NoticeBean.class);
						return noticeBeans;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e(e.getMessage());
			return null;
		}
		return null;
	}

}
