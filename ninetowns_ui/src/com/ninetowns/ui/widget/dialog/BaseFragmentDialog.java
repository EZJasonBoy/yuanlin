package com.ninetowns.ui.widget.dialog;

import com.ninetowns.ui.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 
* @Title: BaseDialogFragment.java  
* @Description: 所有dialogFrament的基类 
* @author wuyulong
* @date 2015-1-8 上午11:08:41  
* @version V1.0
 */
public class BaseFragmentDialog extends DialogFragment{

    private ProgressiveDialog progressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Trans_NoTitleBar_Fullscreen);
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
        if (progressDialog != null) {
            progressDialog.isShowing();
            progressDialog.dismiss();
        }
    }
}
