package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.FansBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FansLvAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<FansBean> fansList;
	
	private String other_userId = "";
	
	public FansLvAdapter(Context context, List<FansBean> fansList, String other_userId){
		this.context = context;
		
		this.fansList = fansList;
		
		this.other_userId = other_userId;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(fansList != null && fansList.size() > 0){
			return fansList.size();
		}  else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.follow_lv_item, null);
			
			vh.follow_item_initial = (TextView)convertView.findViewById(R.id.follow_item_initial);
			
			vh.follow_item_user_layout = (RelativeLayout)convertView.findViewById(R.id.follow_item_user_layout);
			
			vh.follow_item_head_photo = (ImageView)convertView.findViewById(R.id.follow_item_head_photo);
			
			vh.follow_item_head_vip = (ImageView)convertView.findViewById(R.id.follow_item_head_vip);
			
			vh.follow_item_user_name = (TextView)convertView.findViewById(R.id.follow_item_user_name);
			
			vh.follow_item_relation = (ImageView)convertView.findViewById(R.id.follow_item_relation);
			
			convertView.setTag(vh);
			
		} else {
			vh = (ViewHolder)convertView.getTag();
		}
		
		if(fansList.get(position).getFans_initial() != null && !"".equals(fansList.get(position).getFans_initial())){
			//根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)){
				//这个判断用来把首字母为"@"的组隐藏，只显示子列表
				if("@".equals(fansList.get(position).getFans_initial())){
					vh.follow_item_initial.setVisibility(View.GONE);
				} else {
					vh.follow_item_initial.setVisibility(View.VISIBLE);
					vh.follow_item_initial.setText(fansList.get(position).getFans_initial());
				}
				
			}else{
				vh.follow_item_initial.setVisibility(View.GONE);
			}
		} else {
			vh.follow_item_initial.setText("");
		}
		
		if(!TextUtils.isEmpty(fansList.get(position).getFans_userName())){
			vh.follow_item_user_name.setText(fansList.get(position).getFans_userName());
		} else {
			vh.follow_item_user_name.setText("");
		}
		
		
		if(!TextUtils.isEmpty(fansList.get(position).getFans_logoUrl())){
			ImageLoader.getInstance().displayImage(fansList.get(position).getFans_logoUrl(), vh.follow_item_head_photo, CommonUtil.OPTIONS_HEADPHOTO);
		}
		
		if(!TextUtils.isEmpty(fansList.get(position).getFans_userGrade())){
			CommonUtil.showVip(vh.follow_item_head_vip, fansList.get(position).getFans_userGrade());
		}
		
		if(SharedPreferenceHelper.getLoginUserId(context).equals(other_userId)){
			//登录者的粉丝
			if(fansList.get(position).getFans_relation() != null && !"".equals(fansList.get(position).getFans_relation())){
				if("1".equals(fansList.get(position).getFans_relation())){
					vh.follow_item_relation.setImageResource(R.drawable.icon_follow_eachother);
				} else {
					vh.follow_item_relation.setImageResource(R.drawable.icon_addattention);
					vh.follow_item_relation.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//调用添加关注接口
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADD_FOLLOW_USERID, SharedPreferenceHelper.getLoginUserId(context));
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADD_FOLLOW_FOLLOWID, fansList.get(position).getFans_userId());
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.ADD_FOLLOW_URL, requestParamsNet, new RequestCallBack<String>(){

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									String addStr = new String(responseInfo.result);
									try {
										JSONObject jsonObj = new JSONObject(addStr);
										if(jsonObj.has("Status")){
											String addStatus = jsonObj.getString("Status");
											if(addStatus.equals("1")){
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_success));
												fansList.get(position).setFans_relation("1");
												notifyDataSetChanged();
												
												
											} else {
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_fail));
											}
											
										}
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_fail));
								}
								
							});
							
							
						}
					});
					
				
				}
				
			}
		} else {
			//别人的粉丝列表
			if(fansList.get(position).getFans_relation() != null && !"".equals(fansList.get(position).getFans_relation())){
				if("1".equals(fansList.get(position).getFans_relation()) || "0".equals(fansList.get(position).getFans_relation())){
					vh.follow_item_relation.setImageResource(R.drawable.icon_remove_attention);
					vh.follow_item_relation.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//调用取消关注接口
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CANCEL_FOLLOW_USERID, SharedPreferenceHelper.getLoginUserId(context));
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CANCEL_FOLLOW_FOLLOWID, fansList.get(position).getFans_userId());
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CANCEL_FOLLOW_URL, requestParamsNet, new RequestCallBack<String>(){

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									String cancelStr = new String(responseInfo.result);
									try {
										JSONObject jsonObj = new JSONObject(cancelStr);
										if(jsonObj.has("Status")){
											String cancelStatus = jsonObj.getString("Status");
											if(cancelStatus.equals("1")){
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_cancel_fol_success));
												fansList.get(position).setFans_relation("2");
												notifyDataSetChanged();
												
												
											} else {
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_cancel_fol_fail));
											}
											
										}
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_cancel_fol_fail));
								}
								
							});
							
							
						}
					});
				} else if("2".equals(fansList.get(position).getFans_relation())){
					vh.follow_item_relation.setImageResource(R.drawable.icon_addattention);
					vh.follow_item_relation.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//调用添加关注接口
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADD_FOLLOW_USERID, SharedPreferenceHelper.getLoginUserId(context));
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADD_FOLLOW_FOLLOWID, fansList.get(position).getFans_userId());
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.ADD_FOLLOW_URL, requestParamsNet, new RequestCallBack<String>(){

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									String addStr = new String(responseInfo.result);
									try {
										JSONObject jsonObj = new JSONObject(addStr);
										if(jsonObj.has("Status")){
											String addStatus = jsonObj.getString("Status");
											if(addStatus.equals("1")){
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_success));
												fansList.get(position).setFans_relation("1");
												notifyDataSetChanged();
												
												
											} else {
												ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_fail));
											}
											
										}
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									ComponentUtil.showToast(context, context.getResources().getString(R.string.per_home_add_fol_fail));
								}
								
							});
							
						}
					});
				} else if("-1".equals(fansList.get(position).getFans_relation())){
					//自己出现别人的粉丝列表中
//					vh.follow_item_relation.setVisibility(View.GONE);
					vh.follow_item_relation.setImageResource(R.drawable.icon_fans_follow_self);
				}
			}
		}
		
		
		
		
		return convertView;
	}
	
	class ViewHolder{
		
		//头像所对应布局
		RelativeLayout follow_item_user_layout;
		//头像
		ImageView follow_item_head_photo;
		//vip
		ImageView follow_item_head_vip;
		//用户名称
		TextView follow_item_user_name;
		//关系
		ImageView follow_item_relation;
		//大写字母
		TextView follow_item_initial;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return fansList.get(position).getFans_initial().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = fansList.get(i).getFans_initial();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}

}
