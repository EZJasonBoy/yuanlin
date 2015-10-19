package com.ninetowns.tootooplus.util;

import com.ninetowns.library.util.StringUtils;

public class MSGTypeUtils implements INetConstanst{
	
	public static int convertIntType(String type){
		if (MSGTYPE_TEXT.equalsIgnoreCase(type)) {
			return 0;
		} else if (MSGTYPE_IMG.equalsIgnoreCase(type)) {
			return 1;
		} else if (MSGTYPE_AUDIO.equalsIgnoreCase(type)) {
			return 2;
		} else if (MSGTYPE_VIDEO.equalsIgnoreCase(type)) {
			return 3;
		}
		return 0;
	}
	public static int convertIntPrivateLetterType(String type){
		if(!StringUtils.isEmpty(type)){
			return Integer.valueOf(type);
		}
		return 0;
	}

}
