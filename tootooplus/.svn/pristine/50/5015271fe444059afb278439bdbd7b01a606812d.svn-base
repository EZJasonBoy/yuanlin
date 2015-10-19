package com.ninetowns.tootooplus.fragment;

/**
 * 
 * @ClassName: WishShaiXuanDialog
 * @Description: 故事筛选
 * @author wuyulong
 * @date 2015-4-28 下午1:31:58
 * 
 */
public class WishShaiXuanDialog extends BaseShaiXuanDialog {

	@Override
	public int getViewType() {
		return WISH;
	}

	@Override
	public String getListenerPar(int type) {
		String strPar="";
		switch (type) {
		case ALL:
			strPar = "0";// 全部
			break;
		case NEW:
			strPar = "1";// 最新
			break;
		case HOT:
			strPar = "2";// 最热
			break;
		case RECOMMEND:
			strPar = "3";// 推荐
			break;

		}
		return strPar;
	}

}
