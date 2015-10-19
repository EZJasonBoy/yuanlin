package com.ninetowns.tootoopluse.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.ninetowns.tootoopluse.bean.GridViewGroupBean;

public class JoinMultiMemberAdapter extends MyGroupSuccessAdapter {

	private Map<Integer, Map<Integer, Boolean>> mMultiMemberMap;

	public JoinMultiMemberAdapter(Activity activity,
			List<GridViewGroupBean> parserResult) {
		super(activity, parserResult);
	}

	public JoinMultiMemberAdapter(int position, Activity activity,
			List<GridViewGroupBean> parserResult) {
		this(activity, parserResult);

		mMultiMemberMap = new HashMap<Integer, Map<Integer, Boolean>>();
		if (parserResult != null || !parserResult.isEmpty()) {
			Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
			for (int i = 0; i < parserResult.size(); i++) {
				if (parserResult.get(i).getIsUsed().equals("1")) {
					map.put(i, true);
				} else {
					map.put(i, false);
				}
			}
			mMultiMemberMap.put(position, map);
		}
	}

	public Map<Integer, Map<Integer, Boolean>> getMultiMemberMap() {
		return mMultiMemberMap;
	}

	public GridViewGroupBean updateNotifyAdapter(int group, int position) {
		GridViewGroupBean bean = getParserResult().get(position);
		if (this.mMultiMemberMap.containsKey(group)) {
			Map<Integer, Boolean> map = mMultiMemberMap.get(group);
			if (map.containsKey(position)) {
				boolean isUsed = map.get(position);
				if (isUsed) {
					map.put(position, false);
					bean.setIsUsed("0");
				} else {
					map.put(position, true);
					bean.setIsUsed("2");
				}
			}
		}
		// notifyDataSetChanged();
		return bean;
	}

}
