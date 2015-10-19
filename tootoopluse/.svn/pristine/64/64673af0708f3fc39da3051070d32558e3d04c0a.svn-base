package com.ninetowns.tootoopluse.helper;

import java.util.Comparator;

import com.ninetowns.tootoopluse.bean.FollowBean;


public class FollPinyinComparator implements Comparator<FollowBean> {

	@Override
	public int compare(FollowBean o1, FollowBean o2) {
		if (o1.getFol_initial().equals("@")
				|| o2.getFol_initial().equals("#")) {
			return -1;
		} else if (o1.getFol_initial().equals("#")
				|| o2.getFol_initial().equals("@")) {
			return 1;
		} else {
			return o1.getFol_initial().compareTo(o2.getFol_initial());
		}
	}

}
