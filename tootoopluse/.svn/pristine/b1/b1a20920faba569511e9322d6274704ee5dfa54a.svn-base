package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.fragment.MyConstanctFragment;
import com.ninetowns.ui.Activity.FragmentGroupActivity;
/**
 * 
* @ClassName: MyConstanctActivity 
* @Description: 我的联系方式
* @author wuyulong
* @date 2015-6-17 下午4:32:23 
*
 */
public class MyConstanctActivity  extends FragmentGroupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.only_fragment_layout);
		
		ViewUtils.inject(this);
		
	}

	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.only_fragment_frame);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
        Class<? extends Fragment> clazz = null;
        //根据不同的type显示是创建界面 还是 推荐创建界面 还是编辑界面
        	clazz=MyConstanctFragment.class;

        return clazz;
    }

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.only_fragment_frame;
	}

}