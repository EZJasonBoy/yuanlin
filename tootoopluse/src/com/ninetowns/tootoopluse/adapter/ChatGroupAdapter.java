package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.ActivityDetailActivity;
import com.ninetowns.tootoopluse.activity.ChatActivity;
import com.ninetowns.tootoopluse.activity.WishDetailActivity;
import com.ninetowns.tootoopluse.bean.GroupChatList;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.viewbader.BadgeView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChatGroupAdapter extends BaseAdapter implements INetConstanst{
	
	private List<GroupChatList> groupChatLists;
	private Activity mContext;
	
	private String urlApi = "";
	
	public ChatGroupAdapter(Activity mContext, List<GroupChatList> parserResult, String urlApi) {
		this.mContext = mContext;
		this.groupChatLists = parserResult;
		
		this.urlApi = urlApi;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return groupChatLists == null ? 0 : groupChatLists.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.groupchat_item, null);
			viewHolder.iv_groupChatPic = (ImageView) convertView
					.findViewById(R.id.groupchatitem_iv_grouppic);
			viewHolder.tv_groupChatName = (TextView) convertView
					.findViewById(R.id.groupchatitem_tv_groupname);
			viewHolder.badgeView = new BadgeView(mContext,
					viewHolder.tv_groupChatName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final GroupChatList groupchatList = groupChatLists.get(position);
		if (null != groupchatList) {
			viewHolder.tv_groupChatName.setText(groupchatList.getActivityName()
					+ "(" + groupchatList.getCounts() + "人)");
			ImageLoader.getInstance().displayImage(
					groupchatList.getCoverThumb(), viewHolder.iv_groupChatPic,
					CommonUtil.OPTIONS_ALBUM);
			if(groupchatList.MsgCount>0){
				if(viewHolder.badgeView.isShown()){
					viewHolder.badgeView.increment(1);
				}else{
					viewHolder.badgeView.setText(groupchatList.MsgCount+"");
					viewHolder.badgeView.show();
				}
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewHolder.badgeView.hide();
					Intent intent = new Intent(mContext, ChatActivity.class);
					intent.putExtra("groupchatlist", groupchatList);
					mContext.startActivity(intent);
				}
			});
		}
		
		
		viewHolder.iv_groupChatPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(urlApi.equals(ACTIVITY_CHATGROUP_LIST_URL)){
					
					Bundle bundle = new Bundle();
					Intent intent = new Intent(mContext, ActivityDetailActivity.class);
					bundle.putString("activity", "");
					bundle.putString(ConstantsTooTooEHelper.USERID, SharedPreferenceHelper.getLoginUserId(mContext));
					bundle.putString(ConstantsTooTooEHelper.ACTIVITYID, groupchatList.getActivityId());
					bundle.putInt("currentPosition", 0);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					mContext.startActivity(intent);
					
				} else if(urlApi.equals(WISH_CHATGROUP_LIST_URL)){
					Intent intentToDetail=new Intent(mContext, WishDetailActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString(ConstantsTooTooEHelper.USERID, SharedPreferenceHelper.getLoginUserId(mContext));
					bundle.putString(ConstantsTooTooEHelper.STORYID, groupchatList.getActivityId());
					intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					mContext.startActivity(intentToDetail);
					
				}
			}
		});
		
		return convertView;
	}
	static class ViewHolder {
		ImageView iv_groupChatPic;// 聊天群组图片
		TextView tv_groupChatName;// 聊天群组名字
		BadgeView badgeView;
	}

}