package com.ninetowns.ui.fragment;


import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

/**
 * 
 * @Title: FragmentGroup.java
 * @Description: 这个类是为二级菜单的tab页 而封装的，
 *               主要封装了创建fragment的方法以及切换fragment方法和FragmentGroupActivity实现创建fragment的方式一样
 * @author wuyulong
 * @date 2015-1-7 下午5:33:33
 * @version V1.0
 */
public abstract class GroupFragment
        extends BaseFragment {
    protected static final int INVALID_FRAGMENT_ID = -1;
    private static final String SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG = "primary_fragment_tag";// 缓存界面的key标签
    private static final String SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG = "secondary_fragment_tag";// 缓存界面的key标签

    private String mCurrentPrimaryFragmentTag;
    protected Fragment mCurrentPrimaryFragment;

    private String mCurrentSecondaryFragmentTag;
    private Fragment mCurrentSecondaryFragment;

    private int mCurrentPrimaryFragmentId = INVALID_FRAGMENT_ID;
    private int mCurrentSecondaryFragmentId = INVALID_FRAGMENT_ID;

    protected FragmentManager mFragmentManager;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 
     * @Title: FragmentGroup.java
     * @Description: 初始化第一个界面
     * @author wuyulong
     * @date 2015-1-7 下午5:37:03
     * @param
     * @return void
     */
    protected abstract void initPrimaryFragment();

    /**
     * 
     * @Title: FragmentGroup.java
     * @Description: 根据 fragmentId来切换不同的fragment
     * @author wuyulong
     * @date 2015-1-7 下午5:37:32
     * @param
     * @return Class<? extends Fragment> 返回的类型是Fragment的子类，利用反射技术
     */
    protected abstract Class<? extends Fragment> getPrimaryFragmentClass(
            int fragmentId);

    /**
     * 向要跳转的fragment传递参数bundle，然后在跳到的fragment中用getArguments().getString(key)(
     * 如果传递的是字符串)获取。
     * 
     * @param fragmentId
     * @return
     */
    protected abstract Bundle getPrimaryFragmentArguments(int fragmentId);

    protected abstract int getPrimaryFragmentStubId();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mFragmentManager = getChildFragmentManager();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 
     * @Title: FragmentGroup.java
     * @Description: 获得管理fragment的事物类
     * @author wuyulong
     * @date 2015-1-7 下午5:48:25
     * @param enterFragmentId
     *            exitFragmentId 扩展用 （比如说回退栈）
     * @return FragmentTransaction
     */
    protected FragmentTransaction beginPrimaryFragmentTransaction(
            int enterFragmentId, int exitFragmentId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        return ft;
    }

    /**
     * 
     * @Title: FragmentGroup.java
     * @Description: 是public形式的，子类可以覆盖 覆盖之后会自动生成父类的抽象方法
     * @author wuyulong
     * @date 2015-1-7 下午5:38:40
     * @param
     * @return void
     */
    public void switchPrimaryFragment(int fragmentId) {
        // 获得子类的Fragment
        Class<? extends Fragment> clz = getPrimaryFragmentClass(fragmentId);
        // 得到子类fragment的类名
        mCurrentPrimaryFragmentTag = clz.getName();
        // mFragmentManager 可以通过制定的tag得到子类的fragment也可以通过
        // mFragmentManager.findFragmentById(arg0)方式得到
        Fragment fragment = mFragmentManager
                .findFragmentByTag(mCurrentPrimaryFragmentTag);
        FragmentTransaction ft = beginPrimaryFragmentTransaction(fragmentId,
                mCurrentPrimaryFragmentId);
        // 把刚刚进入fragmentId 也就是切换最新的fragmentid赋值给mCurrentPrimaryFragmentId
        // 让它成为最新的id
        mCurrentPrimaryFragmentId = fragmentId;
        if (mCurrentPrimaryFragment != null) {// 如果当前的fragment
                                              // mCurrentPrimaryFragment不为null
                                              // 那么删除当前的fragment
            ft.detach(mCurrentPrimaryFragment);
        }
        if (fragment == null) {// 如果当前的fragment是null
            // 实例化一个fragment
            fragment = Fragment.instantiate(getActivity(), clz.getName());
            Bundle args = getPrimaryFragmentArguments(fragmentId);
            fragment.setArguments(args);
            // 重新替换新的fragment
            ft.replace(getPrimaryFragmentStubId(), fragment,
                    mCurrentPrimaryFragmentTag);
        } else {// 如果不为null 直接附在上面
            ft.attach(fragment);
        }
        // 把新的fragment 赋值给当前的fragment
        mCurrentPrimaryFragment = fragment;
        // 事物提交
        ft.commit();
    }

    protected FragmentTransaction beginSecondaryFragmentTransaction(
            int enterFragmentId, int exitFragmentId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        return ft;
    }

    /**
     * 
     * @Title: FragmentGroup.java
     * @Description: 
     *               这个是扩展的方法，如果后期开发中遇到繁琐的界面，比如类似于京东主界面，我们可以用分模块的方式将界面分成一个一个的界面（碎片
     *               ）
     * @author wuyulong
     * @date 2015-1-7 下午5:59:56
     * @param
     * @return void
     */
    public void switchSecondaryFragment(int fragmentId) {
        Class<? extends Fragment> clz = getSecondaryFragmentClass(fragmentId);
        if (clz == null) {
            return;
        }
        mCurrentSecondaryFragmentTag = clz.getName();
        Fragment fragment = mFragmentManager
                .findFragmentByTag(mCurrentSecondaryFragmentTag);
        FragmentTransaction ft = beginSecondaryFragmentTransaction(fragmentId,
                mCurrentSecondaryFragmentId);
        mCurrentSecondaryFragmentId = fragmentId;
        if (mCurrentSecondaryFragment != null) {
            ft.detach(mCurrentSecondaryFragment);
        }
        if (fragment == null) {
            fragment = Fragment.instantiate(getActivity(), clz.getName());
            Bundle args = getSecondaryFragmentArguments(fragmentId);
            fragment.setArguments(args);
            ft.replace(getSecondaryFragmentStubId(), fragment,
                    mCurrentSecondaryFragmentTag);
        } else {
            ft.attach(fragment);
        }
        mCurrentSecondaryFragment = fragment;
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG,
                mCurrentPrimaryFragmentTag);
        outState.putString(SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG,
                mCurrentSecondaryFragmentTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String primaryFragmentTag = savedInstanceState
                    .getString(SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(primaryFragmentTag)) {
                mCurrentPrimaryFragmentTag = primaryFragmentTag;
                mCurrentPrimaryFragment = mFragmentManager
                        .findFragmentByTag(mCurrentPrimaryFragmentTag);
            }
            String secondaryFragmentTag = savedInstanceState
                    .getString(SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(secondaryFragmentTag)) {
                mCurrentSecondaryFragmentTag = secondaryFragmentTag;
                mCurrentSecondaryFragment = mFragmentManager
                        .findFragmentByTag(mCurrentSecondaryFragmentTag);
            }
        }
        if (mCurrentPrimaryFragment == null) {
            initPrimaryFragment();
        }
        if (mCurrentSecondaryFragment == null) {
            initSecondaryFragment();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mCurrentPrimaryFragment != null) {
            mFragmentManager.beginTransaction().remove(mCurrentPrimaryFragment)
                    .commitAllowingStateLoss();
            mCurrentPrimaryFragment = null;
        }
        if (mCurrentSecondaryFragment != null) {
            mFragmentManager.beginTransaction()
                    .remove(mCurrentSecondaryFragment)
                    .commitAllowingStateLoss();
            mCurrentSecondaryFragment = null;
        }
        super.onDestroyView();
    }

    protected void initSecondaryFragment() {
    }

    protected Class<? extends Fragment> getSecondaryFragmentClass(int fragmentId) {
        return null;
    }

    protected Bundle getSecondaryFragmentArguments(int fragmentId) {
        return null;
    }

    protected int getSecondaryFragmentStubId() {
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
	public AbsParser setParser(String str) {
		return null;
	}

	@Override
	public void getParserResult(Object parserResult) {
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		return null;
	}
}
