package com.ninetowns.tootooplus.helper;

import java.util.Comparator;

import com.ninetowns.tootooplus.bean.FansBean;


public class FansPinyinComparator implements Comparator<FansBean> {

	@Override
	public int compare(FansBean o1, FansBean o2) {
		if (o1.getFans_initial().equals("@")
				|| o2.getFans_initial().equals("#")) {
			return -1;
		} else if (o1.getFans_initial().equals("#")
				|| o2.getFans_initial().equals("@")) {
			return 1;
		} else {
			return o1.getFans_initial().compareTo(o2.getFans_initial());
		}
	}

}
