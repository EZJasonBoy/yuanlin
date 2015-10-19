package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.ActWishDataItem;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ActWishLvAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<ActWishDataItem> actWishList;
	
	public ActWishLvAdapter(Context context, List<ActWishDataItem> actWishList){
		this.context = context;
		this.actWishList = actWishList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(actWishList != null && actWishList.size() > 0){
			return actWishList.size();
		} else {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.act_wish_data_lv_item, null);
			
			vh.act_wish_lv_item_iv = (ImageView)convertView.findViewById(R.id.act_wish_lv_item_iv);
			vh.act_wish_lv_name = (TextView)convertView.findViewById(R.id.act_wish_lv_name);
			vh.act_wish_lv_item_look_num = (TextView)convertView.findViewById(R.id.act_wish_lv_item_look_num);
			vh.act_wish_lv_item_look_change = (TextView)convertView.findViewById(R.id.act_wish_lv_item_look_change);
			convertView.setTag(vh);
			
		} else {
			vh = (ViewHolder)convertView.getTag();
		}
		
		ImageLoader.getInstance().displayImage(actWishList.get(position).getData_item_CoverThumb(), vh.act_wish_lv_item_iv, CommonUtil.OPTIONS_ALBUM_DETAIL);
		
		if(!TextUtils.isEmpty(actWishList.get(position).getData_item_Name())){
			vh.act_wish_lv_name.setText(actWishList.get(position).getData_item_Name());
		} else {
			vh.act_wish_lv_name.setText("");
		}
		
		if(!TextUtils.isEmpty(actWishList.get(position).getData_item_Pageviews())){
			vh.act_wish_lv_item_look_num.setText(actWishList.get(position).getData_item_Pageviews());
		} else {
			vh.act_wish_lv_item_look_num.setText("");
		}
		
		if(!TextUtils.isEmpty(actWishList.get(position).getUserConversion())){
			vh.act_wish_lv_item_look_change.setText(actWishList.get(position).getUserConversion());
		} else {
			vh.act_wish_lv_item_look_change.setText("");
		}
		
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView act_wish_lv_item_iv;
		
		TextView act_wish_lv_name;
		
		TextView act_wish_lv_item_look_num;
		
		TextView act_wish_lv_item_look_change;
	}

}
