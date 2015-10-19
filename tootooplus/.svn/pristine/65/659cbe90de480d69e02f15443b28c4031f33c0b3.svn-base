package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.InvitationListAdapter;
import com.ninetowns.tootooplus.bean.MyInvitationBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.parser.MyInvitationParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.ShareUtils;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.MyGridView;

public class MyInvitationFragment extends
		BaseFragment<List<MyInvitationBean>, MyInvitationParser> implements
		INetConstanst, OnClickListener {
	private MyInvitationParser parser;
	/**
	 * @Fields mIWasInvitedView :我被邀请试图
	 */
	private View mIWasInvitedView;

	/**
	 * @Fields mMyInvitationCodeTextView : 我的邀请码 用来分享
	 */
	private TextView mMyInvitationCodeTextView, mIWillInvitate;
	/**
	 * @Fields mInfoTextView : 邀请信息
	 */
	private TextView mInfoTextView;
	private Activity mActivity;

	private MyGridView mGridView;
	// private GridView mGridView;

	private InvitationListAdapter mAdapter;

	/**
	 * @Fields mGetVertifyCode :获取验证码按钮
	 */
	private TextView mGetVertifyCode;
	/**
	 * @Fields mCommit :提交按钮
	 * */
	private TextView mCommit;

	/**
	 * @Fields mInvitationCodeEditText :邀请码
	 */
	private EditText mInvitationCodeEditText;
	/**
	 * @Fields mMobileNumEditText : 手机号
	 */
	private EditText mMobileNumEditText;
	/**
	 * @Fields mVertifyCodeEditText :验证码
	 */
	private EditText mVertifyCodeEditText;

	private Resources resources;

	private String userId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myInvitationView = inflater.inflate(
				R.layout.fragment_myinvitation, null);
		mActivity = getActivity();
		resources = getResources();
		userId = SharedPreferenceHelper.getLoginUserId(mActivity);
		initViews(myInvitationView);
		return myInvitationView;
	}

	private void initViews(View myInvitationView) {
		mIWasInvitedView = myInvitationView.findViewById(R.id.ll_iwasinvitde);
		mMyInvitationCodeTextView = (TextView) myInvitationView
				.findViewById(R.id.myinvitation_tv_myinvitation);
		mIWillInvitate = (TextView) myInvitationView
				.findViewById(R.id.myinvitation_tv_iwillinvite);
		mInfoTextView = (TextView) myInvitationView
				.findViewById(R.id.myinvitation_tv_info);
		// mGridView = (GridView) myInvitationView
		// .findViewById(R.id.myinvitation_gridview);
		mGridView = (MyGridView) myInvitationView
				.findViewById(R.id.myinvitation_gridview);
		mGetVertifyCode = (TextView) myInvitationView
				.findViewById(R.id.myinvitation_tv_vertifycode);
		mCommit = (TextView) myInvitationView
				.findViewById(R.id.myinvitation_tv_commit);
		mInvitationCodeEditText = (EditText) myInvitationView
				.findViewById(R.id.myivitation_et_invitationcode);
		mMobileNumEditText = (EditText) myInvitationView
				.findViewById(R.id.myivitation_et_mobilenum);
		mVertifyCodeEditText = (EditText) myInvitationView
				.findViewById(R.id.myivitation_et_vertifycode);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
		setListener();
	}

	private void setListener() {
		mGetVertifyCode.setOnClickListener(this);
		mCommit.setOnClickListener(this);
		mIWillInvitate.setOnClickListener(this);
	}

	@Override
	public MyInvitationParser setParser(String str) {
		parser = new MyInvitationParser(str);
		return parser;
	}

	@Override
	public void getParserResult(List<MyInvitationBean> myInvitationBeans) {
		myInvitationBeans = myInvitationBeans == null ? new ArrayList<MyInvitationBean>()
				: myInvitationBeans;
		// if (null != myInvitationBeans && myInvitationBeans.size() > 0) {
		refreshAdpater(myInvitationBeans);
		// }
		if (null != parser) {
			String invitationCode = parser.getInvitationCode();
			if (hasNotIInvited(invitationCode)) {
				UIUtils.setViewVisible(mIWasInvitedView);
			}
			mMyInvitationCodeTextView.setText(SharedPreferenceHelper
					.getLoginUserId(mActivity));
			mInfoTextView.setText(parser.getInvitationInfo());
		}

	}

	private void refreshAdpater(List<MyInvitationBean> myInvitationBeans) {
		if (null == mAdapter) {
			mAdapter = new InvitationListAdapter(mActivity, myInvitationBeans);
			mGridView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * @Title: wasIInvited
	 * @Description: 我是否没有被邀请过
	 * @param @param invitationCode
	 * @param @return true 邀请过 false 未被邀请过
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean hasNotIInvited(String invitationCode) {
		return "0".equals(invitationCode);
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(MY_INVATATION);
		requestParamsNet.addQueryStringParameter(USER_ID, userId);
		return requestParamsNet;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myinvitation_tv_vertifycode:// 发送验证码
			getVertifyCode(mMobileNumEditText.getText());

			break;
		case R.id.myinvitation_tv_commit:// 提交
			commitInvitation();
			break;
		case R.id.myinvitation_tv_iwillinvite:// 我要邀请
			iWillInvitate();
			break;

		}

	}

	/**
	 * @Title: iWillInvitate
	 * @Description: 我要邀请
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void iWillInvitate() {
		if (StringUtils.isEmpty(mMyInvitationCodeTextView.getText())) {
			return;
		}
		ShareUtils.showShareText(mActivity, mMyInvitationCodeTextView.getText()
				+ "");
	}

	/**
	 * @Title: commitInvitation
	 * @Description: 提交邀请
	 * @param 设定文件
	 * @return void 返回类型
	 */
	private void commitInvitation() {
		vertifyInvitationCode(mInvitationCodeEditText.getText());
		vertifyMobileNum(mMobileNumEditText.getText());
		vertifyCodeIsValid(mVertifyCodeEditText.getText());
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(USER_ID, userId);
		requestParamsNet.addQueryStringParameter(PHONE_NUM,
				mMobileNumEditText.getText() + "");
		requestParamsNet.addQueryStringParameter(VERTIFY_CODE,
				mVertifyCodeEditText.getText() + "");
		requestParamsNet.addQueryStringParameter(INVITATION_CODE,
				mInvitationCodeEditText.getText() + "");
		showProgressDialog();
		CommonUtil.xUtilsGetSend(COMMIT_INVITATION, requestParamsNet,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						closeProgressDialogFragment();
						String result = responseInfo.result;
						if (!StringUtils.isEmpty(result)) {
							try {
								JSONObject object = new JSONObject(result);
								if (null != object && object.has("Status")
										&& 1 == object.getInt("Status")) {
									UIUtils.showCenterToast(mActivity, "提交成功");
								} else {
									UIUtils.showCenterToast(mActivity, "提交失败");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						closeProgressDialogFragment();
					}
				});

	}

	/**
	 * @Title: vertifyInvitationCode
	 * @Description: 验证邀请码
	 * @param @param text 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private boolean vertifyInvitationCode(Editable text) {
		if (!StringUtils.isEmpty(text)) {
			return true;
		} else {
			UIUtils.showCenterToast(mActivity,
					resources.getString(R.string.invitationcode_cannot_benull));
			return false;
		}

	}

	/**
	 * @Title: vertifyInvitationCode
	 * @Description: 验证码是否有效
	 * @param @param text 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private boolean vertifyCodeIsValid(Editable text) {
		if (!StringUtils.isEmpty(text)) {
			return true;
		} else {
			UIUtils.showCenterToast(mActivity,
					resources.getString(R.string.vertifycode_cannot_benull));
			return false;
		}

	}

	/**
	 * @param editable
	 * @Title: getVertifyCode
	 * @Description:获取验证码
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void getVertifyCode(Editable editable) {

		if (vertifyMobileNum(editable)) {
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(USER_ID, userId);
			requestParamsNet.addQueryStringParameter(PHONE_NUM, editable + "");
			showProgressDialog();
			CommonUtil.xUtilsGetSend(GET_VETTIFYCODE, requestParamsNet,
					new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							try {
								String result = responseInfo.result;
								JSONObject object = new JSONObject(result);
								if (null != object && object.has("Status")) {
									int status = object.getInt("Status");
//									if (1003 == status) {
//										UIUtils.showCenterToast(mActivity,
//												"该手机号已经被邀请过");
//									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							closeProgressDialogFragment();
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							UIUtils.showCenterToast(mActivity, "验证码请求失败！请重新请求");
							closeProgressDialogFragment();
						}
					});
		}

	}

	/**
	 * @param editable
	 * @Title: vertifyMobileNum
	 * @Description: 验证手机号码是否正确
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean vertifyMobileNum(Editable editable) {
		if (StringUtils.isEmpty(editable)) {
			UIUtils.showCenterToast(mActivity,
					resources.getString(R.string.mobilenum_cannot_benull));
			return false;
		}
		if (!CommonUtil.isValidMobiNumber(editable.toString())) {
			UIUtils.showCenterToast(mActivity,
					resources.getString(R.string.mobilenum_isnot_correctformat));
			return false;
		}
		return true;
	}

}
