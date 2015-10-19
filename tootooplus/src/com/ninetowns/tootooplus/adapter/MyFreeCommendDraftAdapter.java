package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.ninetowns.tootooplus.bean.RemarkStoryBean;
/**
 * 
* @ClassName: MyFreeCommendDraftAdapter 
* @Description: 我的点评草稿
* @author wuyulong
* @date 2015-4-23 下午2:46:37 
*
 */
public class MyFreeCommendDraftAdapter extends MyFreeCommentAdapter{

	public MyFreeCommendDraftAdapter(Context context,
			List<RemarkStoryBean> parserResult) {
		super(context, parserResult);
	}
	@Override
	public void setVisibleDel(CommentHolder commentHolder) {
		commentHolder.mIVDelOrone.setVisibility(View.VISIBLE);
	}
}
