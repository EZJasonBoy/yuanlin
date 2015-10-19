package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.MyFreeCommentDraftPassedFragment;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 
* @ClassName: MyDraftFreeCommentActivity 
* @Description: 我的点评草稿
* @author wuyulong
* @date 2015-2-4 下午5:46:11 
*
 */
public class MyDraftFreeCommentActivity extends FragmentGroupActivity {
	private Bundle bundle;
//	ll_left
//	tv_title
//	ibtn_right
	@ViewInject(R.id.ibtn_left)
	private ImageButton mIbtnLeft;
	@ViewInject(R.id.tv_title)
	private TextView mTvTitle;
	@ViewInject(R.id.ibtn_right)
	private ImageButton mIbtnRight;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_comment_draft_activity);
		getType();
		ViewUtils.inject(this);
		mIbtnRight.setVisibility(View.INVISIBLE);
		mTvTitle.setText("草稿");
		
	}
	@OnClick(R.id.ibtn_left)
	public void dismissView(View v){
		finish();
	}
	/**
	 * 
	* @Title: getType 
	* @Description: bundle 参数
	* @param    
	* @return void    返回类型 
	* @throws
	 */
private void getType(){
	  bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
	
}
	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.fragment_stub);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
        Class<? extends Fragment> clazz = null;
        //根据不同的type显示是创建界面 还是 推荐创建界面 还是编辑界面
        	clazz=MyFreeCommentDraftPassedFragment.class;

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

}