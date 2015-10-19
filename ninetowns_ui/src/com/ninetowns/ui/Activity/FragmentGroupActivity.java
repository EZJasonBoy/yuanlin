package com.ninetowns.ui.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Window;

public abstract class FragmentGroupActivity extends BaseActivity {
    protected static final int INVALID_FRAGMENT_ID = -1;
    private static final String SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG = "primary_fragment_tag";
    private static final String SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG = "secondary_fragment_tag";
    private String mCurrentPrimaryFragmentTag;
    protected Fragment mCurrentPrimaryFragment;

    private String mCurrentSecondaryFragmentTag;
    protected Fragment mCurrrentSecondaryFragment;

    private int mCurrentPrimaryFragmentId = INVALID_FRAGMENT_ID;
    private int mCurrentSecondaryFragmentId = INVALID_FRAGMENT_ID;

    protected FragmentManager mFragmentManager;
    private SharedPreferences shar;

    /**
     * 这个函数会调用片段的生命周期onActivityCreated() 先初始化可见主要片段
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

    /**
     * 
     * @Title: FragmentGroupActivity.java
     * @Description: 判断是否是相同的fragment
     * @author wuyulong
     * @date 2014-11-2 上午9:45:32
     * @param
     * @return boolean
     */
    protected boolean isSameFragment(int fragmentId) {

        return false;
    };

    /**
     * 得到主要片段的容器ViewGroup资源id
     * 
     * @return
     */
    protected abstract int getPrimaryFragmentStubId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
    }

    /**
     * 他是在onStart()----onRestoreInstanceState()执行之后作最后的初始化
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPrimaryFragmentTag = savedInstanceState
                    .getString(SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(mCurrentPrimaryFragmentTag)) {
                mCurrentPrimaryFragment = mFragmentManager
                        .findFragmentByTag(mCurrentPrimaryFragmentTag);
            }
            mCurrentSecondaryFragmentTag = savedInstanceState
                    .getString(SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG);
            if (!TextUtils.isEmpty(mCurrentSecondaryFragmentTag)) {
                mCurrrentSecondaryFragment = mFragmentManager
                        .findFragmentByTag(mCurrentSecondaryFragmentTag);
            }
        }
        if (mCurrentPrimaryFragment == null) {
            initPrimaryFragment();
        }
        if (mCurrrentSecondaryFragment == null) {
            initSecondaryFragment();// 防止 以后扩展
        }
        super.onPostCreate(savedInstanceState);
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
        Class<? extends Fragment> clz = getPrimaryFragmentClass(fragmentId);
        mCurrentPrimaryFragmentTag = clz.getName();
        Fragment fragment = mFragmentManager
                .findFragmentByTag(mCurrentPrimaryFragmentTag);
        FragmentTransaction ft = beginPrimaryFragmentTransaction(fragmentId,
                mCurrentPrimaryFragmentId);
        mCurrentPrimaryFragmentId = fragmentId;
        if (mCurrentPrimaryFragment != null) {
            ft.detach(mCurrentPrimaryFragment);
        }
        if (fragment == null) {
            fragment = instantiateFragment(fragmentId, clz, ft);
        } else {
            if (isSameFragment(fragmentId)) {// 如果是共用了一个fragment那么每次重新实例化
                fragment = null;
                fragment = instantiateFragment(fragmentId, clz, ft);
            } else {
                ft.attach(fragment);
            }

        }
        mCurrentPrimaryFragment = fragment;
        ft.commitAllowingStateLoss();
    }

    /**
     * 
     * @Title: FragmentGroupActivity.java
     * @Description: 创建fragment
     * @author wuyulong
     * @date 2015-1-8 上午10:11:24
     * @param
     * @return Fragment
     */
    private Fragment instantiateFragment(int fragmentId,
            Class<? extends Fragment> clz, FragmentTransaction ft) {
        Fragment fragment;
        fragment = Fragment.instantiate(this, clz.getName());
        Bundle args = getPrimaryFragmentArguments(fragmentId);
        fragment.setArguments(args);
        ft.replace(getPrimaryFragmentStubId(), fragment,
                mCurrentPrimaryFragmentTag);
        return fragment;
    }

    protected FragmentTransaction beginSecondaryFragmentTransaction(
            int enterFragmentId, int exitFragmentId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        return ft;
    }

    public void switchSecondaryFragment(int fragmentId) {
        Class<? extends Fragment> clz = getSecondaryFragmentClass(fragmentId);
        if (clz == null) {
            return;
        }
        mCurrentSecondaryFragmentTag = clz.getName();// 得到fragment的名字
        Fragment fragment = mFragmentManager
                .findFragmentByTag(mCurrentSecondaryFragmentTag);
        FragmentTransaction ft = beginSecondaryFragmentTransaction(fragmentId,
                mCurrentSecondaryFragmentId);
        mCurrentSecondaryFragmentId = fragmentId;
        if (mCurrrentSecondaryFragment != null) {
            ft.detach(mCurrrentSecondaryFragment);
        }
        if (fragment == null) {
            fragment = Fragment.instantiate(this, clz.getName());
            Bundle args = getSecondaryFragmentArguments(fragmentId);
            fragment.setArguments(args);
            ft.replace(getSecondaryFragmentStubId(), fragment,
                    mCurrentSecondaryFragmentTag);
        } else {
            ft.attach(fragment);
        }
        mCurrrentSecondaryFragment = fragment;
        ft.commit();
    }

    /**
     * on
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVE_INSTANCE_STATE_PRIMARY_FRAGMENT_TAG,
                mCurrentPrimaryFragmentTag);// 将当前的fragment保存起来
        outState.putString(SAVE_INSTANCE_STATE_SECONDARY_FRAGMENT_TAG,
                mCurrentSecondaryFragmentTag);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (mCurrentPrimaryFragment != null) {
            mFragmentManager.beginTransaction().remove(mCurrentPrimaryFragment)
                    .commitAllowingStateLoss();
            mCurrentPrimaryFragment = null;
        }
        if (mCurrrentSecondaryFragment != null) {
            mFragmentManager.beginTransaction()
                    .remove(mCurrrentSecondaryFragment)
                    .commitAllowingStateLoss();
            mCurrrentSecondaryFragment = null;
        }
        super.onDestroy();
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
}
