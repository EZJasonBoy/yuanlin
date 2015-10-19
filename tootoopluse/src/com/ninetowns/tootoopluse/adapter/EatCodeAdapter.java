package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.R.interpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GridViewGroupBean;

public class EatCodeAdapter extends BaseAdapter {

	private List<GridViewGroupBean> mEatCodeList;

	private LayoutInflater mInflater;

	public EatCodeAdapter(List<GridViewGroupBean> bean) {

		this.mEatCodeList = bean;
		setInflater();
	}

	private void setInflater() {
		mInflater = LayoutInflater.from(TootooPlusEApplication.getAppContext());

	}

	@Override
	public int getCount() {
		return mEatCodeList.size();
	}

	@Override
	public Object getItem(int position) {
		return mEatCodeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder mViewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.eatcode_item, parent,
					false);
			mViewHolder = new ViewHolder();
			ViewUtils.inject(mViewHolder, convertView);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.mEatCodeTextView.setText(mEatCodeList.get(position)
				.getEatCode());
		return convertView;
	}

	public class ViewHolder {
		@ViewInject(R.id.eatcode)
		TextView mEatCodeTextView;
	}

	public void notify(int code, GridViewGroupBean bean) {

		if (mEatCodeList != null) {
			if (code == 0)
				mEatCodeList.add(bean);
			else if (code == 1) {
				if (mEatCodeList.contains(bean))
					mEatCodeList.remove(bean);
			}
		}
		notifyDataSetChanged();
	}

	public List<GridViewGroupBean> getEatCodeList() {
		return this.mEatCodeList;
	}

}
