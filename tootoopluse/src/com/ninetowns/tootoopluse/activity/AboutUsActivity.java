package com.ninetowns.tootoopluse.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.ui.Activity.BaseActivity;

/** 
* @ClassName: AboutUsActivity 
* @Description: 关于我们页面
* @author zhou
* @date 2015-3-23 上午10:49:12 
*  
*/
public class AboutUsActivity extends BaseActivity {
	
	/**
	 * @Fields mIVBack :返回按钮
	 */
	private ImageView mIVBack;

	/**
	 * @Fields mTVTitle : 标题
	 */
	private TextView mTVTitle;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.about_us);
		LinearLayout mLLBack = (LinearLayout) findViewById(R.id.two_or_one_btn_head_back);
		mLLBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		TextView mTVTitle = (TextView) findViewById(R.id.two_or_one_btn_head_title);
		mTVTitle.setText(R.string.about_us_title);
	}

}
