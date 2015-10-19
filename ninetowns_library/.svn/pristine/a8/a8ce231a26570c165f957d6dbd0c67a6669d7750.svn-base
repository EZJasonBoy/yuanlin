package com.ninetowns.library.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
* @Title: BasicResponse.java  
* @Description: 处理网络数据，具体处理什么样的数据由它的子类去完成， 模版类
* Bean 泛型指的是object子类类型的数据
* @author wuyulong
* @date 2015-1-7 下午5:23:42  
* @version V1.0
 */
public abstract class BasicResponse <Bean extends Object>{
	private List<Bean> list;
	public int status;//接口返回的json状态
	public String msg;//获得的data数据
	public BasicResponse(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	/**
	 * 声明泛型方法
	 * 处理json 数据穿
	 * @param json
	 * @throws JSONException
	 */
	public BasicResponse(JSONObject json) throws JSONException {
		if(json!=null){
			if (json.has("Status")) {// 0是失败不显示=== 1是成功获取数据data
				status = json.getInt("Status");
				if (status == 1) {// 成功
					if (json.has("Data")) {
						msg = json.getString("Data");
						JSONObject obj = new JSONObject(msg);
						list= getList(obj);
						// 在这里复写方法 回调
					} else {
						getList(json);
					}
				} else {
					msg = "";// 否则data是空串
					list=getList(json);
				}
			}
		}else{
			list=getList(json);//json可以示Null
		}
	
	}
	public abstract List<Bean> getList(JSONObject objJSONObject) throws JSONException;
	public List<Bean> getDataList(){
		return this.list;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Status = " + status).append(" ");
		sb.append("msg = " + msg).append(" ");
		return sb.toString();
	}

}
