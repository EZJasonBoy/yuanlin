package com.ninetowns.tootoopluse.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.util.ParserUitils;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.Activity.BaseActivity;

/** 
* @ClassName: FeedBackActivity 
* @Description: 意见反馈
* @author zhou
* @date 2015-3-23 上午10:52:32 
*  
*/
public class FeedBackActivity extends BaseActivity implements INetConstanst{
	
	/** 
	* @Fields mIVBack :返回按钮
	*/ 
	private ImageView mIVBack;
	
	/** 
	* @Fields mTVTitle : 标题 
	*/ 
	private TextView mTVTitle;
	
	/** 
	* @Fields mEditText : 文本编辑框
	*/ 
	private EditText mEditText;
	
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_feedback);
		mIVBack=(ImageView) findViewById(R.id.setting_iv_back);
		mTVTitle= (TextView) findViewById(R.id.commontitlebar_tv_title);
		mEditText= (EditText) findViewById(R.id.feedback_edit);
		mContext=this;
		mIVBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			   FeedBackActivity.this.finish();
			}
		});
		mTVTitle.setText("意见反馈");
		mEditText.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(!StringUtils.isEmpty(mEditText.getText())){
					sendMessage(mEditText.getText());
				}
				return true;
			}
			
		});
	}
	
	/**
	 * @param editable  
	* @Title: sendMessage 
	* @Description:发送意见反馈信息
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void sendMessage(Editable editable) {
		RequestParamsNet params= new  RequestParamsNet();
		params.addQueryStringParameter("UserId", SharedPreferenceHelper.getLoginUserId(this));
		params.addQueryStringParameter("Message", editable.toString());
		CommonUtil.xUtilsGetSend(SEND_FEEDBACK, params, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if(ParserUitils.isSuccess(responseInfo)){
					UIUtils.showCenterToast(mContext, "发送反馈成功");
					mEditText.setText("");
				}else{
					UIUtils.showCenterToast(mContext, "发送反馈失败");
				}
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				UIUtils.showCenterToast(mContext, "发送反馈失败");
			}
		});
	}

}
