package com.ninetowns.tootooplus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.BrowserInternetFragment;
import com.ninetowns.tootooplus.fragment.NinetownsBrowserFragment;
import com.ninetowns.tootooplus.util.BrowserTitleUpdateUtil;

public class InternetBrowserActivity extends FragmentActivity implements
		BrowserTitleUpdateUtil {
	public static final String BROWSER_TITLE_TEXT = "Browser_Title_Text";
	private static EditText mEtShoppingUr;
	private NinetownsBrowserFragment fragment;
	@ViewInject(R.id.ibtn_left)
	private ImageButton ibtnBack;
	@ViewInject(R.id.et_url)
	private EditText mEditextUrl;
	private String mEditextContent;
	@ViewInject(R.id.tv_go)
	private TextView mTvGo;
	@ViewInject(R.id.rl_notice)
	private RelativeLayout mRlNotice;
	private String urlcontent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.browser);
		ViewUtils.inject(this);
		String url = getIntent().getStringExtra(NinetownsBrowserFragment.EXTRA_BROWSER_ADDRESS);
		if(!TextUtils.isEmpty(url)){
			setDisplayFragment(url);
		}else{
			mEditextUrl.setText("www.baidu.com");
		}
	
	}

	@OnClick(R.id.tv_go)
	public void goInternet(View view) {
		mRlNotice.setVisibility(View.GONE);
		setDisplayFragment(null);
	}

	/**
	 * 
	 * @Title: setDisplayFragment
	 * @Description: 展示网络内容
	 * @param
	 * @return
	 * @throws
	 */
	private void setDisplayFragment(String url) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		if(!TextUtils.isEmpty(url)){
			mEditextContent=url;
			fragment = new BrowserInternetFragment();
		}else{
			mEditextContent = mEditextUrl.getText().toString();
			fragment = new NinetownsBrowserFragment();
		}
		if (mEditextContent.length() > 5) {
			String spit = mEditextContent.substring(0, 5);
			if (spit.contains("http")) {
				urlcontent = mEditextContent;
			} else {
				urlcontent = "http://" + mEditextContent;
			}
		}

		fragment.setBrowserTitleUpdate(this);
		Bundle bundle = new Bundle();
		bundle.putString(NinetownsBrowserFragment.EXTRA_BROWSER_ADDRESS,
				urlcontent);
		fragment.setArguments(bundle);
		ft.add(R.id.content, fragment);
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
	}

	@OnClick(R.id.ibtn_left)
	public void backView(View view) {
		finish();

	}

	@Override
	public void updateTitle(String title, String url) {
		mEditextUrl.setText(url);
		if(mEtShoppingUr!=null){
			mEtShoppingUr.setText(url);
		}
	}

	@Override
	public void onBackPressed() {
		if (fragment != null) {
			if (!fragment.onBackPressed()) {
				super.onBackPressed();
			}
		}else{
			finish();
		}

	}

	public static Intent buildIntent(Context context, String url, String title,EditText mEtShoppingUrl) {
		mEtShoppingUr=mEtShoppingUrl;
		Intent intent = new Intent(context, InternetBrowserActivity.class);
		intent.putExtra(NinetownsBrowserFragment.EXTRA_BROWSER_ADDRESS, url);
		if (!TextUtils.isEmpty(title)) {
			intent.putExtra(InternetBrowserActivity.BROWSER_TITLE_TEXT, title);
		}
		return intent;
	}
}