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
 * @ClassName: MyActOnLineCommentFragment
 * @Description: 线上 已经参加、未点评、已经点评 附着界面
 * @author wuyulong
 * @date 2015-4-16 下午2:16:52
 * 
 */
public class MyActOnLineCommentFragment extends GroupFragment implements
		OnCheckedChangeListener {
	private View myActCommentFreeFragment;
	@ViewInject(R.id.rg_tab_act_comment)
	private RadioGroup mRGActComment;
	
	@ViewInject(R.id.rb_act_tab_join)
	private RadioButton mRbtnJoin;//已经参加
	
	@ViewInject(R.id.rb_tab_act_no_comment)
	private RadioButton mRbtnNoComment;// 未点评
	
	@ViewInject(R.id.rb_tab_act_commented)
	private RadioButton mRbtnCommented;// 已经点评
	@ViewInject(R.id.view_line_join)
	private View mLineJoin;
	@ViewInject(R.id.view_line_nocomment)
	private View mLineNoComment;
	@ViewInject(R.id.view_line_commented)
	private View mLineCommented;
	@ViewInject(R.id.rl_free_comment)
	private FrameLayout mFreeComment;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myActCommentFreeFragment = inflater.inflate(
				R.layout.my_activity_comment_free_fragment, null);
		ViewUtils.inject(this, myActCommentFreeFragment);
		mRGActComment.setOnCheckedChangeListener(this);
		return myActCommentFreeFragment;
	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rb_tab_act_no_comment);
	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		Class<? extends Fragment> calzz = null;
		switch (fragmentId) {
		case R.id.rb_act_tab_join:
			calzz = JoinOnLineActFragment.class;
			break;
		case R.id.rb_tab_act_no_comment:
			calzz = NoCommentOnLineCommentFragment.class;
			break;
		case R.id.rb_tab_act_commented:
			calzz = CommentedOnLineCommentFragment.class;


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
		case R.id.rb_act_tab_join:
			mLineJoin.setVisibility(View.VISIBLE);

			mLineNoComment.setVisibility(View.INVISIBLE);
			mLineCommented.setVisibility(View.INVISIBLE);
			break;
		case R.id.rb_tab_act_no_comment:
			mLineNoComment.setVisibility(View.VISIBLE);

			mLineJoin.setVisibility(View.INVISIBLE);
			mLineCommented.setVisibility(View.INVISIBLE);

			break;
		case R.id.rb_tab_act_commented:

			mLineCommented.setVisibility(View.VISIBLE);

			mLineJoin.setVisibility(View.INVISIBLE);
			mLineNoComment.setVisibility(View.INVISIBLE);

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
