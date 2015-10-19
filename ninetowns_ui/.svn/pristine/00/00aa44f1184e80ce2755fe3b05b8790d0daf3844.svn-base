package com.ninetowns.ui.Activity;


import com.ninetowns.library.helper.AppManager;
import com.ninetowns.ui.R;
import com.ninetowns.ui.widget.dialog.ProgressiveDialog;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 
 * @Title: BaseActivity.java
 * @Description: 所有沱沱项目Activity的基类
 * @author wuyulong
 * @date 2015-1-15 上午10:19:22
 * @version V1.0
 */
public abstract class BaseActivity extends FragmentActivity {
    private long exitTime = 0;// 退出时间的标记
    private ProgressiveDialog progressDialog;// 请求网络的进度条
    public static final String EXIT_ACTION = "exit_action";// 退出所有的activity的action

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);	
 		try{
 			if(finishBroadcastReceiver != null){
     			unregisterReceiver(finishBroadcastReceiver);
     			finishBroadcastReceiver=null;
     		}
 		} catch(Exception e){
 			e.printStackTrace();
 		}
 		
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_ACTION);
        registerReceiver(finishBroadcastReceiver, filter);
      
        //友盟开始
      	MobclickAgent.onResume(this);
    }
    
    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 显示dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:26
     * @param
     * @return void
     */
    public void showProgressDialog(Context context) {
        if ((!((Activity) context).isFinishing()) && (progressDialog == null)) {
            progressDialog = new ProgressiveDialog(context);
        }
        if (progressDialog != null) {
            progressDialog.setMessage(R.string.loading);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

    }


    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 取消dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:48
     * @param
     * @return void
     */
    public void closeProgressDialog(Context context) {
        if (progressDialog != null&&(!((Activity) context).isFinishing())) {
            progressDialog.isShowing();
            progressDialog.dismiss();
        }
    }

    /**
     * 
     * @Title: BaseActivity.java
     * @Description: 点击两次退出
     * @author wuyulong
     * @date 2015-1-15 上午10:17:21
     * @param
     * @return void
     */
    public void exitApp(Context context) {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(context.getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
           exitApp();
        }
    }

    private BroadcastReceiver finishBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (EXIT_ACTION.equals(action)) {
                	exitApp();
                }
            }
        }
    };
    
    /** 
    * @Title: exitApp 
    * @Description: TODO 退出app
    * @param     设定文件 
    * @return void    返回类型 
    * @throws 
    */
    private void exitApp() {
		AppManager.getAppManager().AppExit(getApplicationContext());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		//友盟结束
		MobclickAgent.onPause(this);
	}

}
