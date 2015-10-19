package com.ninetowns.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetowns.ui.R;

public class GpsDialog extends Dialog  {
	private OnDialogOpera dialog;
	private TextView mTitle;
	private TextView mContent;
	private TextView mHelp;
	private TextView mSure;
	private TextView mCancel;
	private int screen_width;

	public GpsDialog(Context context) {
		this(context, R.style.too_dialog_style);
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		screen_width = wm.getDefaultDisplay().getWidth();
		addDialogView(R.layout.gps_dialog);
	}

	public GpsDialog(Context context, int theme) {
		super(context, theme);
		setCanceledOnTouchOutside(false);
		
	}

	public void addDialogView(int layout_id) {
		setContentView(layout_id);
		
		LinearLayout viewLayout = (LinearLayout) findViewById(R.id.ll_layout);
		//设置dialog的宽高
		LinearLayout.LayoutParams lLayoutParams = (LinearLayout.LayoutParams)viewLayout.getLayoutParams();
		lLayoutParams.width = screen_width * 4/5;
		lLayoutParams.height = lLayoutParams.width *3/4;
		viewLayout.setLayoutParams(lLayoutParams);
		LinearLayout viewCancel = (LinearLayout) findViewById(R.id.ll_dialog_cancle);
		LinearLayout viewSure = (LinearLayout) findViewById(R.id.ll_dialog_sure);
		
		mTitle=(TextView)findViewById(R.id.tv_dialog_title);
		mContent=(TextView)findViewById(R.id.tv_dialog_content);
		mHelp=(TextView)findViewById(R.id.tv_dialog_help);
		mCancel=(TextView)findViewById(R.id.tv_dialog_cancel);
		mSure=(TextView)findViewById(R.id.tv_dialog_sure_tv);

		mHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if(dialog!=null){
					dialog.skipHelp(GpsDialog.this);
				}
				
			
				
			}
		});



		viewCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(dialog!=null){
					dismiss();
					dialog.cancelDialog(GpsDialog.this);
				}
				
			}
		});
		viewSure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(dialog!=null){
					dismiss();
					dialog.skipOpera(GpsDialog.this);
				}
			}
		});
	}

	public interface OnDialogOpera {
		public void cancelDialog(GpsDialog gpsDialog);

		public void skipOpera(GpsDialog gpsDialog);
		public void skipHelp(GpsDialog gpsDialog);

	}

	public void setOnDialogOpera(OnDialogOpera dialog) {
		this.dialog = dialog;
	}

	/**设置标题**/
	public void setDialogTitle(String dialog_title){
		mTitle.setText(dialog_title);
	}
	
	
	/**设置内容**/
	public void setDialogContent(String dialog_content){
		mContent.setText(dialog_content);
	}
	
	/**设置确定按钮文字, 如果不设置默认为"确定"**/
	public void setDialogSureBtnName(String dialog_sure_btn_name){
		mSure.setText(dialog_sure_btn_name);
	}
	
	/**设置取消按钮文字, 如果不设置默认为"取消"**/
	public void setDialogCancelBtnName(String dialog_cancel_btn_name){
		mCancel.setText(dialog_cancel_btn_name);
	}
	
	/**设置取消按钮文字, 如果不设置默认为"取消"**/
	public void setDialogHelpName(String help){
		mHelp.setText(help);
	}
	
	
}
