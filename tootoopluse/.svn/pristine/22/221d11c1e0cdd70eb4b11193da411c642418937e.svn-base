package com.ninetowns.tootoopluse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.BaseChatFragment;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
@ContentView(R.layout.activity_privateletterchat)
public class PrivateLetterChat extends FragmentGroupActivity implements INetConstanst {
	@ViewInject(R.id.commontitlebar_tv_title)
	private TextView mTitleText;
//	@ViewInject(R.id.setting_iv_back)
//	private ImageView mIVBack;
	
	
	@OnClick(R.id.setting_iv_back)
	public void onBackBtnClick(View v){
		AppManager.getAppManager().finishActivity(this);
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ViewUtils.inject(this);
		initViews();
		
//		loadDataFromServer();
	}
	private String userId;
	private String receiverUserId;
	private void initViews() {
		LogUtils.e("initViews............");
		mTitleText.setText("私信");
		userId = SharedPreferenceHelper.getLoginUserId(this);
		receiverUserId=getIntent().getStringExtra("receiveuserid");
	}

	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.fragment_stub);
	}
	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		return BaseChatFragment.class;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		bundle.putString("isprivateletter", "isprivateletter");
		bundle.putString("receiveuserid", receiverUserId);
		return bundle;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		BaseChatFragment.getBaseChatFragmentInstance().onActivityResult(requestCode, resultCode, data);
	}

}
