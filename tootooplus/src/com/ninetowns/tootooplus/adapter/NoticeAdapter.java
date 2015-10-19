package com.ninetowns.tootooplus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.NoticeBean;
import com.ninetowns.tootooplus.util.TimeUtil;

public class NoticeAdapter extends BaseAdapter {

	private List<NoticeBean> mNoticeBeans;
	private Activity mContext;

	public NoticeAdapter(Activity mContext, List<NoticeBean> mNoticeBeans) {
		this.mContext=mContext;
		this.mNoticeBeans=mNoticeBeans==null?new ArrayList<NoticeBean>():mNoticeBeans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNoticeBeans==null?0:mNoticeBeans.size();
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
		ViewHolder viewHolder=null;
		if(null==convertView){
			viewHolder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.notice_item, null);
			viewHolder.tv_notice_content=(TextView) convertView.findViewById(R.id.noticeitem_tv_content);
			viewHolder.tv_noticeSendTime=(TextView) convertView.findViewById(R.id.noticeitem_tv_time);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		NoticeBean notice=mNoticeBeans.get(position);
		if(null!=notice){
			viewHolder.tv_notice_content.setText(notice.getContent());
			viewHolder.tv_noticeSendTime.setText(TimeUtil.getTimeDisplay(notice.getCreateDate(), mContext));
		}
		
		return convertView;
	}

	
	static class ViewHolder{
		TextView tv_notice_content;//消息内容
		TextView tv_noticeSendTime;//消息发送时间
	}
}
