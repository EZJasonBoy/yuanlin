package com.ninetowns.tootoopluse.helper;

import java.util.Comparator;

import com.ninetowns.tootoopluse.bean.GroupMember;



/** 
* @ClassName: PinyinComparator 
* @Description:  
* @author zhou
* @date 2015-3-3 下午4:08:19 
*  
*/
public class PinyinComparator implements Comparator<GroupMember> {

	public int compare(GroupMember o1, GroupMember o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
