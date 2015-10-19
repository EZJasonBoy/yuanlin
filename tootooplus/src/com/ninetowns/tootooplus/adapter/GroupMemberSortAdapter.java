package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.HomeActivity;
import com.ninetowns.tootooplus.activity.PersonalHomeActivity;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.GroupMember;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @ClassName: SortAdapter
 * @Description:群组成员列表
 * @author zhou
 * @date 2015-4-2 上午10:18:44
 * 
 */
public class GroupMemberSortAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<GroupMember> groupMembers;
	
	private String group_type;
	
	public GroupMemberSortAdapter(Context context, List<GroupMember> groupMembers, String group_type){
		this.context = context;
		
		this.groupMembers = groupMembers;
		
		this.group_type = group_type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(groupMembers != null && groupMembers.size() > 0){
			return groupMembers.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return groupMembers.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.groupmember_item, null);
			
			vh.catalog = (TextView)convertView.findViewById(R.id.catalog);
			vh.group_mem_item_head_photo = (ImageView)convertView.findViewById(R.id.group_mem_item_head_photo);
			vh.group_mem_item_vip = (ImageView)convertView.findViewById(R.id.group_mem_item_vip);
			vh.group_mem_user_name = (TextView)convertView.findViewById(R.id.group_mem_user_name);
			vh.group_mem_user_job = (TextView)convertView.findViewById(R.id.group_mem_user_job);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder)convertView.getTag();
		}
		if(groupMembers.get(position).getSortLetters() != null && !"".equals(groupMembers.get(position).getSortLetters())){
			//根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(position);
			//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if(position == getPositionForSection(section)){
				//这个判断用来把首字母为"@"的组，表示为团长或者商家
				if("@".equals(groupMembers.get(position).getSortLetters())){
					vh.catalog.setVisibility(View.VISIBLE);
					vh.catalog.setText(R.string.mem_store_or_admin);
				} else {
					vh.catalog.setVisibility(View.VISIBLE);
					vh.catalog.setText(groupMembers.get(position).getSortLetters());
				}
				
			}else{
				vh.catalog.setVisibility(View.GONE);
			}
		} else {
			vh.catalog.setText("");
		}
		
		ImageLoader.getInstance().displayImage(groupMembers.get(position).getLogoUrl(), vh.group_mem_item_head_photo, CommonUtil.OPTIONS_HEADPHOTO);
		
		if(groupMembers.get(position).getUserName() != null && !"".equals(groupMembers.get(position).getUserName())){
			vh.group_mem_user_name.setText(groupMembers.get(position).getUserName());
		} else {
			vh.group_mem_user_name.setText("");
		}
		
		CommonUtil.showVip(vh.group_mem_item_vip, groupMembers.get(position).getUserGrade());
		if(group_type.equals("-1")){
			vh.group_mem_user_job.setText("");
			vh.group_mem_user_job.setBackgroundResource(R.color.white);
		} else {
			if(!TextUtils.isEmpty(groupMembers.get(position).getBusinessStatus()) && groupMembers.get(position).getBusinessStatus().equals("1")){
				vh.group_mem_user_job.setText(R.string.mem_item_store);
				vh.group_mem_user_job.setBackgroundResource(R.drawable.bg_gray_corners_shape_drawable);
			} else if(!TextUtils.isEmpty(groupMembers.get(position).getIsAdmin()) && groupMembers.get(position).getIsAdmin().equals("1")){
				vh.group_mem_user_job.setText(R.string.mem_item_admin);
				vh.group_mem_user_job.setBackgroundResource(R.drawable.bg_gray_corners_shape_drawable);
			} else {
				vh.group_mem_user_job.setText("");
				vh.group_mem_user_job.setBackgroundResource(R.color.white);
			}
		}
		
		
		vh.group_mem_item_head_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!SharedPreferenceHelper.getLoginUserId(context).equals(groupMembers.get(position).getUserId())){
					UIUtils.startActivity(context, groupMembers.get(position).getUserId(),
							PersonalHomeActivity.class);
				} else {
					Bundle bundle=new Bundle();
					Intent intent = new Intent(TootooPlusApplication.getAppContext(),
							HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					bundle.putInt("tab_index", ConstantsTooTooEHelper.TAB_FIVE);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					context.startActivity(intent);
				}
				
			}
		});
		
		
		return convertView;
	}


	class ViewHolder{
		TextView catalog;
		
		ImageView group_mem_item_head_photo;
		
		ImageView group_mem_item_vip;
		
		TextView group_mem_user_name;
		
		TextView group_mem_user_job;
	}
	
	
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return groupMembers.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = groupMembers.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
}