package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;

/**
 * @ClassName: HasUsedPriorityAdapter
 * @Description: 已使用的优先码
 * @author zhou
 * @date 2015-4-23 上午9:52:56
 * 
 */
public class HasUsedPriorityAdapter extends BasePriorityCodeAdapter {

	public HasUsedPriorityAdapter(Activity mContext,
			List<PriorityCodeBean> mPriorityCodeBeans) {
		super(mContext, mPriorityCodeBeans);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = View.inflate(mContext,
					R.layout.item_hasused_prioritycode, null);
		}
		TextView tv_priortyCode = ViewHolder.get(convertView,
				R.id.hasused_prioritycode_text);
		TextView tv_usetime = ViewHolder.get(convertView,
				R.id.hasused_prioritycode_usetime_text);

		final PriorityCodeBean priorityCodeBean = mPriorityCodeBeans
				.get(position);
		if (null != priorityCodeBean) {
			tv_priortyCode.setText(priorityCodeBean.PriorityCodeId);
			tv_usetime.setText(mContext.getResources().getString(R.string.prioritycode_usedtime)+priorityCodeBean.DateUsed);
		}
		return convertView;
	}

}
