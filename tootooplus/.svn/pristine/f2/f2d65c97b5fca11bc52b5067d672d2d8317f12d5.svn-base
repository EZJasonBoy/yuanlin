package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.CreateActStoryBean;

public class CreateActPwLvAdapter extends BaseAdapter{
	
	private Context context;
	
	private List<CreateActStoryBean> list;
	
	
	public CreateActPwLvAdapter(Context context, List<CreateActStoryBean> list){
		this.context = context;
		this.list = list;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list != null && list.size() > 0){
			return list.size();
		} else {
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.create_act_down_item, null);
		}
		TextView create_act_down_item_tv = (TextView)convertView.findViewById(R.id.create_act_down_item_tv);
		
		create_act_down_item_tv.setText(list.get(position).getCreate_act_story_story_name());
		return convertView;
	}

}
