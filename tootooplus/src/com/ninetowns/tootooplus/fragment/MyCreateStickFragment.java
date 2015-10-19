package com.ninetowns.tootooplus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.tootooplus.R;
import com.ninetowns.ui.fragment.GroupFragment;

/**
 * 
 * @ClassName: MyCreateStickFragment
 * @Description:我创建未通过和已通过附着界面
 * @author wuyulong
 * @date 2015-4-16 下午2:16:52
 * 
 */
public class MyCreateStickFragment extends GroupFragment implements
		OnCheckedChangeListener {
	private View myActCommentFreeFragment;
	@ViewInject(R.id.rg_tab_wis)
	private RadioGroup mRGWishTab;

	@ViewInject(R.id.rb_wis_tab_passed)
	private RadioButton mRbtnPassed;//已通过

	@ViewInject(R.id.rb_tab_wis_no_passed)
	private RadioButton mRbtnNoPassed;// 未通过

	@ViewInject(R.id.view_line_passed)
	private View mLinePassed;
	@ViewInject(R.id.view_line_nopassed)
	private View mLineNoPassed;
	
	@ViewInject(R.id.rl_free_comment)
	private FrameLayout mFreeComment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myActCommentFreeFragment = inflater.inflate(
				R.layout.my_cre_stick_fragment, null);
		ViewUtils.inject(this, myActCommentFreeFragment);
		mRGWishTab.setOnCheckedChangeListener(this);
		return myActCommentFreeFragment;
	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rb_wis_tab_passed);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		Class<? extends Fragment> calzz = null;
		switch (fragmentId) {
		case R.id.rb_tab_wis_no_passed:
			calzz = NoPassedWishFragment.class;
			break;
		case R.id.rb_wis_tab_passed:
			calzz = PassedWishFragment.class;
			break;
	
		default:
			throw new IllegalArgumentException();
		}
		return calzz;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.rl_free_comment;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab(checkedId);

	}

	/**
	 * @Title: switchTab
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void switchTab(int checkedId) {
		switch (checkedId) {
		case R.id.rb_tab_wis_no_passed:
			
			mLinePassed.setVisibility(View.INVISIBLE);
			mLineNoPassed.setVisibility(View.VISIBLE);

			
			break;
		case R.id.rb_wis_tab_passed:

			mLinePassed.setVisibility(View.VISIBLE);

			mLineNoPassed.setVisibility(View.INVISIBLE);

			break;
	

		default:

			break;
		}
		switchPrimaryFragment(checkedId);
	}

	@Override
	public AbsParser setParser(String str) {
		// 不作处理
		return null;
	}

	@Override
	public void getParserResult(Object parserResult) {
		// 不作处理

	}

	@Override
	public RequestParamsNet getApiParmars() {
		// 不作处理
		return null;
	}

}
