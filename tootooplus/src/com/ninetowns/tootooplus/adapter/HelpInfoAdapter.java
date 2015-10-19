package com.ninetowns.tootooplus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.PersonInfoDetailBean.HelpInfo;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/** 
* @ClassName: HelpInfoAdapter 
* @Description: 个人信息详情页面的帮助信息适配器
* @author zhou
* @date 2015-4-21 下午2:41:35 
*  
*/
public class HelpInfoAdapter extends BaseAdapter {

	private Context context;
	
	private List<HelpInfo> datas;
	
	public HelpInfoAdapter(Context context, List<HelpInfo> datas){
		this.context = context;
		this.datas = datas==null?new ArrayList<HelpInfo>():datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(datas != null && datas.size() > 0){
			return datas.size();
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
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.help_lv_item, null);
			
			viewHolder.help_lv_item_ques_layout = (LinearLayout)convertView.findViewById(R.id.help_lv_item_ques_layout);
			
			viewHolder.help_lv_item_ans_layout = (LinearLayout)convertView.findViewById(R.id.help_lv_item_ans_layout);
			
			viewHolder.help_lv_item_ques = (TextView)convertView.findViewById(R.id.help_lv_item_ques);
			
			viewHolder.help_lv_item_ans = (TextView)convertView.findViewById(R.id.help_lv_item_ans);
			
			viewHolder.help_lv_item_arrow = (ImageView)convertView.findViewById(R.id.help_lv_item_arrow);
			
			viewHolder.help_lv_item_iv = (ImageView)convertView.findViewById(R.id.help_lv_item_iv);
			
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		final HelpInfo  helpInfo=datas.get(position);
		if(!TextUtils.isEmpty(helpInfo.HelpTitle)){
			viewHolder.help_lv_item_ques.setText(helpInfo.HelpTitle);
		} else {
			viewHolder.help_lv_item_ques.setText("");
		}
		
		if(!TextUtils.isEmpty(helpInfo.Content)){
			viewHolder.help_lv_item_ans.setText(helpInfo.Content);
		} else {
			viewHolder.help_lv_item_ans.setText("");
		}
		
		if(!TextUtils.isEmpty(helpInfo.Images)){
			viewHolder.help_lv_item_iv.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(helpInfo.Images, viewHolder.help_lv_item_iv, CommonUtil.OPTIONS_ALBUM);
		} else {
			viewHolder.help_lv_item_iv.setVisibility(View.GONE);
		}
		
		//默认情况下，答案是隐藏起来的
		viewHolder.help_lv_item_ans_layout.setVisibility(View.GONE);
		viewHolder.help_lv_item_arrow.setImageResource(R.drawable.icon_arrow);
		
		
		quesLayoutClick(viewHolder);
		
		return convertView;
	}
	
	private void quesLayoutClick(final ViewHolder vh){
		OnClickListener quesClickLister = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(v.getId()){
				//点击问题布局
				case R.id.help_lv_item_ques_layout:
					if(vh.help_lv_item_ans_layout.getVisibility() == View.VISIBLE){
						vh.help_lv_item_ans_layout.setVisibility(View.GONE);
						vh.help_lv_item_arrow.setImageResource(R.drawable.icon_arrow);
					} else {
						vh.help_lv_item_ans_layout.setVisibility(View.VISIBLE);
						vh.help_lv_item_arrow.setImageResource(R.drawable.icon_arrow_down);
					}
					
					
					break;
				}
			}
		};
		vh.help_lv_item_ques_layout.setOnClickListener(quesClickLister);
	}
	
	class ViewHolder {
		LinearLayout help_lv_item_ques_layout, help_lv_item_ans_layout;
		TextView help_lv_item_ques, help_lv_item_ans;
		ImageView help_lv_item_arrow, help_lv_item_iv;
	}

}