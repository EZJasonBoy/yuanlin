package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.ParentGroupChatFragmentPagerAdapter;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.widget.ScrollControlViewPager;

/**
 * @ClassName: ParentGroupChatFragment
 * @Description:聊天 群组列表 的 父窗体
 * @author zhou
 * @date 2015-4-9 下午5:04:59
 */
public class ParentGroupChatFragment extends Fragment  implements INetConstanst, OnClickListener {
	

	@ViewInject(R.id.setting_iv_back)
	private ImageView mBackImageView;

	/**
	 * @Fields currIndex : 当前fragment的position
	 */
	private int mCurrIndex;
	private ParentGroupChatFragmentPagerAdapter mPageAdapter;

	/**
	 * @Fields mTitleTextView : 标题栏 text
	 */
	@ViewInject(R.id.commontitlebar_tv_title)
	private TextView mTitleTextView;

	private Activity mContext;
	private View mParentGroupChatView;
	@ViewInject(R.id.chat_vPager)
	private ScrollControlViewPager mCustomViewPager;
	@ViewInject(R.id.chat_acitivity_tv_groupone)
	private TextView mGroupTextView;
	@ViewInject(R.id.chat_acitivity_tv_grouptwo)
	private TextView mWishTextView;
	@ViewInject(R.id.chat_acitivity_tv_groupthree)
	private TextView mThreeTextView;
	
	private Resources resources;

	private ArrayList<GroupChatFragment> mFragmentsList;

	private GroupChatFragment mGroupChatFragment;

	private int mOffset;
	@ViewInject(R.id.iv_bottom_line)
	private ImageView mBottomLine;

	private int mPositionOne;

	private Bundle bundle;

	private int secondTabindex=0;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	bundle = getActivity().getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
	if (bundle != null){
		try {
			secondTabindex=bundle.getInt("second_tab_index");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		resources=getResources();
		mParentGroupChatView = inflater.inflate(R.layout.chat_activity, null);
		ViewUtils.inject(this, mParentGroupChatView);
		initViews();
		return mParentGroupChatView;
	}

	private void initViews() {
		UIUtils.setViewGone(mBackImageView);
		UIUtils.setViewGone(mThreeTextView);
		mTitleTextView.setText(resources.getString(R.string.group_chat));
		mGroupTextView.setText(resources.getString(R.string.activity_group));
		mWishTextView.setText(resources.getString(R.string.wish_group));
		initTabWidth();
		initViewPager();
		setListener();
	}

	private void setListener() {
		mGroupTextView.setOnClickListener(this);
		mWishTextView.setOnClickListener(this);
	}

	private void initTabWidth() {
		int screenW = UIUtils.getScreenWidth(mContext);
		mOffset = (int) (screenW / 2.0);
		int height = UIUtils.dip2px(mContext, 3);
		mBottomLine.setLayoutParams(new LinearLayout.LayoutParams(mOffset,
				height));
		mPositionOne = (int) (screenW / 2.0);
	}

	private void initViewPager() {
		mFragmentsList = new ArrayList<GroupChatFragment>();
		for (int i = 0; i <GROUP_CHAT_TAB_SIZE; i++) {
			Bundle bundle = new Bundle();
			bundle.putInt(GROUP_CHAT_TAB_POSITION, i);
			mGroupChatFragment=new GroupChatFragment();
			mGroupChatFragment.setArguments(bundle);
			mFragmentsList.add(mGroupChatFragment);
		}
		mPageAdapter = new ParentGroupChatFragmentPagerAdapter(this.getChildFragmentManager(),mFragmentsList);
		mCustomViewPager.setAdapter(mPageAdapter);
		mCustomViewPager.setOnPageChangeListener(mPagerListener);
		// 设置预加载的数量 可以缓存
		mCustomViewPager.setOffscreenPageLimit(1);
		mCustomViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		mCustomViewPager.setCurrentItem(secondTabindex);
		
	}
	
	/**
	 * @param tabUnSelectedTextColor 
	 * @param tabSelectedTextColor  
	* @Title: changeTabOneTextColor 
	* @Description: 设置第一个 tab被选中 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void changeTabOneTextColor(int tabSelectedTextColor, int tabUnSelectedTextColor) {
		mGroupTextView.setTextColor(tabSelectedTextColor);
		mWishTextView.setTextColor(tabUnSelectedTextColor);
	}
	/** 
	 * @Title: changeTabTwoTextColor 
	 * @Description: 设置第二 个 tab被选中 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	private void changeTabTwoTextColor(int tabSelectedTextColor, int tabUnSelectedTextColor) {
		mGroupTextView.setTextColor(tabUnSelectedTextColor);
		mWishTextView.setTextColor(tabSelectedTextColor);
	}
	
	private ViewPager.SimpleOnPageChangeListener mPagerListener = new ViewPager.SimpleOnPageChangeListener() {
		Animation animation = null;
		@Override
		public void onPageSelected(int position) {
			int tabSelectedTextColor=resources.getColor(R.color.tab_selected_textcolor);
			int tabUnSelectedTextColor=resources.getColor(R.color.tab_unSelected_textcolor);
			switch (position) {
			case 0://
			changeTabOneTextColor(tabSelectedTextColor,tabUnSelectedTextColor);
			animation = new TranslateAnimation(mPositionOne, 0, 0, 0);
				break;
			case 1://
				changeTabTwoTextColor(tabSelectedTextColor,tabUnSelectedTextColor);
				animation = new TranslateAnimation(0,mPositionOne, 0, 0);
				break;
			}
			mCurrIndex = position;
			animation.setFillAfter(true);
			animation.setDuration(300);
			mBottomLine.startAnimation(animation);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chat_acitivity_tv_grouptwo://wishgroup
			mCurrIndex=1;
			
			break;
		case R.id.chat_acitivity_tv_groupone://acitivitygroup
			mCurrIndex=0;
			break;

		}
		mCustomViewPager.setCurrentItem(mCurrIndex);
	}
}
