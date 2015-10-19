package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;

/**
 * @ClassName: HasPresentedPriorityAdapter
 * @Description: 已赠送的优先码
 * @author zhou
 * @date 2015-4-23 上午9:52:56
 * 
 */
public class HasPresentedPriorityAdapter extends BasePriorityCodeAdapter {

	public HasPresentedPriorityAdapter(Activity mContext,
			List<PriorityCodeBean> mPriorityCodeBeans) {
		super(mContext, mPriorityCodeBeans);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = View.inflate(mContext,
					R.layout.item_haspresented_prioritycode, null);
		}
		TextView tv_priortyCode = ViewHolder.get(convertView,
				R.id.haspresented_prioritycode_text);
		TextView tv_nickname = ViewHolder.get(convertView,
				R.id.haspresented_prioritycode_nickname_text);
		TextView tv_time = ViewHolder.get(convertView,
				R.id.haspresented_prioritycode_time_text);

		final PriorityCodeBean priorityCodeBean = mPriorityCodeBeans
				.get(position);
		if (null != priorityCodeBean) {
			tv_priortyCode.setText(priorityCodeBean.PriorityCodeId);
			tv_nickname.setText(priorityCodeBean.UserNameUsed
					+ priorityCodeBean.UserIdUsed);
			tv_time.setText(priorityCodeBean.DateUsed);
		}
		return convertView;
	}

}
