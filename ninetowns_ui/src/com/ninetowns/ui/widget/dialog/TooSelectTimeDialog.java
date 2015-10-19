package com.ninetowns.ui.widget.dialog;


import com.ninetowns.ui.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TooSelectTimeDialog extends Dialog {
	
	private int screen_width = 0;
	
	/**弹出框标题**/
	private TextView too_dialog_title;
	/**弹出框确定按钮**/
	private TextView too_dialog_sure_tv;
	/**弹出框取消按钮**/
	private TextView too_dialog_cancel_tv;
	
	private LinearLayout too_select_time_dialog_content_layout;
	
	public interface TooTimeDialogCallBack {
		
		public void onTooTimeDialogSure();
		
		public void onTooTimeDialogCancel();
	}
	
	private TooTimeDialogCallBack tooTimeDialogCallBack;

	public TooSelectTimeDialog(Context context, int theme) {
		super(context, theme);
		//按返回键可以取消
//		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

	public TooSelectTimeDialog(Context context) {
		this(context, R.style.too_dialog_style);
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		screen_width = wm.getDefaultDisplay().getWidth();
		
		addDialogView(R.layout.too_select_time_dialog_suer_cancel);
		
	}

	
	private void addDialogView(int layout_id){
		//必须加上这么一句加载布局的语句
		super.setContentView(layout_id);
		
		LinearLayout too_dialog_layout = (LinearLayout)findViewById(R.id.too_select_time_dialog_layout);
		//设置dialog的宽高
		LinearLayout.LayoutParams lLayoutParams = (LinearLayout.LayoutParams)too_dialog_layout.getLayoutParams();
		lLayoutParams.width = screen_width * 9 / 10;
		lLayoutParams.height = lLayoutParams.width * 3 / 4;
		too_dialog_layout.setLayoutParams(lLayoutParams);
		
		//确定
		LinearLayout too_dialog_sure_layout = (LinearLayout)findViewById(R.id.too_select_time_dialog_sure_layout);
		too_dialog_sure_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(tooTimeDialogCallBack != null){
					tooTimeDialogCallBack.onTooTimeDialogSure();
				}
			}

		});
		
		//取消
		LinearLayout too_dialog_cancel_layout = (LinearLayout)findViewById(R.id.too_select_time_dialog_cancel_layout);
		too_dialog_cancel_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				if(tooTimeDialogCallBack != null){
					tooTimeDialogCallBack.onTooTimeDialogCancel();
				}
			}
		});
		
		too_dialog_title = (TextView)findViewById(R.id.too_select_time_dialog_title);
		
		too_dialog_sure_tv = (TextView)findViewById(R.id.too_select_time_dialog_sure_tv);
		
		too_dialog_cancel_tv = (TextView)findViewById(R.id.too_select_time_dialog_cancel_tv);
		
		too_select_time_dialog_content_layout = (LinearLayout)findViewById(R.id.too_select_time_dialog_content_layout);
	}
	
	/**设置标题**/
	public void setDialogTitle(String dialog_title){
		too_dialog_title.setText(dialog_title);
	}
	
	/**设置标题**/
	public void setDialogTitle(int dialog_title_id){
		too_dialog_title.setText(dialog_title_id);
	}
	
	public void setDialogView(View view){
		too_select_time_dialog_content_layout.removeAllViews();
		too_select_time_dialog_content_layout.addView(view);
	}
	
	/**设置确定按钮文字, 如果不设置默认为"确定"**/
	public void setDialogSureBtnName(String dialog_sure_btn_name){
		too_dialog_sure_tv.setText(dialog_sure_btn_name);
	}
	
	/**设置确定按钮文字, 如果不设置默认为"确定"**/
	public void setDialogSureBtnName(int dialog_sure_btn_name_id){
		too_dialog_sure_tv.setText(dialog_sure_btn_name_id);
	}
	
	/**设置取消按钮文字, 如果不设置默认为"取消"**/
	public void setDialogCancelBtnName(String dialog_cancel_btn_name){
		too_dialog_cancel_tv.setText(dialog_cancel_btn_name);
	}
	
	/**设置取消按钮文字, 如果不设置默认为"取消"**/
	public void setDialogCancelBtnName(int dialog_cancel_btn_name_id){
		too_dialog_cancel_tv.setText(dialog_cancel_btn_name_id);
	}
	
	//设置事件接口
	public void setTooTimeDialogCallBack(TooTimeDialogCallBack tooTimeDialogCallBack){
		this.tooTimeDialogCallBack = tooTimeDialogCallBack;
	}
	
}
