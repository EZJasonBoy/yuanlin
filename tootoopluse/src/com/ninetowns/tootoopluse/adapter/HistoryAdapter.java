package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.HistoryBean;
import com.ninetowns.ui.widget.text.MarqueeTextView;

public class HistoryAdapter extends BaseAdapter{
	private Context context;
	private List<? extends HistoryBean> listHistory;
	public HistoryAdapter(Context context,List<? extends HistoryBean> listHistory) {
		this.context=context;
		this.listHistory=listHistory;
	}

	@Override
	public int getCount() {
		return listHistory.size();
	}

	@Override
	public Object getItem(int position) {
		return listHistory.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HistoryBean historyItem = listHistory.get(position);
		HistoryViewHolder historyHolder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.item_history, null);
			 historyHolder=new HistoryViewHolder();
			 ViewUtils.inject(historyHolder, convertView);
			 convertView.setTag(historyHolder);
		}else{
			historyHolder=(HistoryViewHolder) convertView.getTag();
		}
		String historyName = historyItem.getHistoryName();
		if(!TextUtils.isEmpty(historyName)){
			historyHolder.mTVHistory.setText(historyName);
		}else{
			LogUtil.error("历史记录HistoryName", "null");
		}
		return convertView;
	}
	public static class  HistoryViewHolder{
		@ViewInject(R.id.mtv_history)
		public MarqueeTextView mTVHistory;
	}

}
