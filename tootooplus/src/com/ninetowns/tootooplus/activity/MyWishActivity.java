package com.ninetowns.tootooplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.fragment.MyCreateStickFragment;
import com.ninetowns.tootooplus.fragment.MyWantFreeFragment;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: MyWishActivity
 * @Description: 我的心愿  我创建  我要白吃
 * @author wuyulong
 * @date 2015-2-4 下午5:46:11
 * 
 */
public class MyWishActivity extends FragmentGroupActivity implements
		OnCheckedChangeListener {
	private Bundle bundle;

	@ViewInject(R.id.iv_left)
	private ImageButton mIVBack;// 返回上一级
	@ViewInject(R.id.rg_my_wis)
	private RadioGroup mRGMyWis;
	@ViewInject(R.id.rbtn_my_cre)
	private RadioButton mRbtnCre;// 我创建
	@ViewInject(R.id.rbtn_my_free)
	private RadioButton mRbtnMyFree;// 我要白吃
	@ViewInject(R.id.fragment_stub)
	private FrameLayout mFlContainer;
	@ViewInject(R.id.iv_right)
	private ImageButton mIVRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_wish_activity);
		getType();
		ViewUtils.inject(this);
		mRGMyWis.setOnCheckedChangeListener(this);

	}

	@OnClick(R.id.iv_left)
	public void backReturn(View view) {
		finish();

	}

	/**
	 * 
	 * @Title: getType
	 * @Description: bundle 参数
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void getType() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);

	}

	@Override
	protected void initPrimaryFragment() {
		switchTab(R.id.rbtn_my_cre);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		// 根据不同TAB

		Class<? extends Fragment> clazz = null;
		switch (fragmentId) {
		case R.id.rbtn_my_cre:// 我创建
			clazz = MyCreateStickFragment.class;
			break;
		case R.id.rbtn_my_free:// 我要白吃
			clazz = MyWantFreeFragment.class;
			break;

		default:
			throw new IllegalArgumentException();
		}

		return clazz;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab(checkedId);

	}

	/**
	 * 
	 * @Title: skipToDraftAct
	 * @Description: 跳转到草稿界面
	 * @param
	 * @return
	 * @throws
	 */
	@OnClick(R.id.iv_right)
	private void skipToDraftAct(View v) {
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		if (!TextUtils.isEmpty(userid)) {
			Intent intent = new Intent(this, MyDraftWishActivity.class);
			// Bundle bundle=new Bundle();
			// ConstantsTooTooEHelper.putView(
			// ConstantsTooTooEHelper.isDraftView, bundle);
			// intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intent);

		} else {
			ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
					"没有登陆");
		}
	}

	/**
	 * 
	 * @Title: switchTab
	 * @Description: 切换tab页
	 * @param
	 * @return
	 * @throws
	 */
	private void switchTab(int checkedId) {
		switch (checkedId) {
		case R.id.rbtn_my_cre:
			mRGMyWis.setBackgroundResource(R.drawable.applay_yes);

			break;
		case R.id.rbtn_my_free:
			mRGMyWis.setBackgroundResource(R.drawable.applay_no);
			break;

		}
		switchPrimaryFragment(checkedId);
	}

}