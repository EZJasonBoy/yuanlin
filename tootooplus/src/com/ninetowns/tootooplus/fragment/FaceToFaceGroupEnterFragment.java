package com.ninetowns.tootooplus.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.activity.JoinMemberActivity;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;

@SuppressWarnings("rawtypes")
public class FaceToFaceGroupEnterFragment extends
		BaseFragment<Object, AbsParser> implements View.OnClickListener {

	private View wantGoGroup;
	@ViewInject(R.id.two_or_one_btn_head_back)
	private LinearLayout mLLFinish;

	@ViewInject(R.id.two_or_one_btn_head_title)
	private TextView mTVTtile;

	@ViewInject(R.id.ctv_one)
	private CheckedTextView mCTVOne;
	@ViewInject(R.id.ctv_two)
	private CheckedTextView mCTVTwo;
	@ViewInject(R.id.ctv_three)
	private CheckedTextView mCTVThree;
	@ViewInject(R.id.ctv_four)
	private CheckedTextView mCTVFour;
	@ViewInject(R.id.ctv_five)
	private CheckedTextView mCTVFive;
	@ViewInject(R.id.ctv_six)
	private CheckedTextView mCTVSix;
	@ViewInject(R.id.ctv_seven)
	private CheckedTextView mCTVSeven;
	@ViewInject(R.id.ctv_eight)
	private CheckedTextView mCTVEight;
	@ViewInject(R.id.ctv_nine)
	private CheckedTextView mCTVNine;
	@ViewInject(R.id.ctv_zero)
	private CheckedTextView mCTVZero;

	@ViewInject(R.id.enter_first)
	private CheckedTextView mCTVEnterFirst;
	@ViewInject(R.id.enter_second)
	private CheckedTextView mCTVEnterSecond;
	@ViewInject(R.id.enter_third)
	private CheckedTextView mCTVEnterThird;
	@ViewInject(R.id.enter_fourth)
	private CheckedTextView mCTVEnterFourth;

	@ViewInject(R.id.ll_back_clean)
	private LinearLayout mLLBackClean;// 回退清除
	private Drawable mEnterTextBg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		wantGoGroup = inflater.inflate(R.layout.want_go_group, null);
		ViewUtils.inject(this, wantGoGroup);
		if (isAdded()) {
			mEnterTextBg = getResources().getDrawable(
					R.drawable.icon_enter_text_bg);
			mTVTtile.setText(getResources().getString(
					R.string.want_to_go_group_facetoface));
		}
		setOnClickListenerAll();
		return wantGoGroup;
	}

	private void setOnClickListenerAll() {
		mLLFinish.setOnClickListener(this);
		mCTVOne.setOnClickListener(this);
		mCTVTwo.setOnClickListener(this);
		mCTVThree.setOnClickListener(this);
		mCTVFour.setOnClickListener(this);
		mCTVFive.setOnClickListener(this);
		mCTVSix.setOnClickListener(this);
		mCTVSeven.setOnClickListener(this);
		mCTVEight.setOnClickListener(this);
		mCTVNine.setOnClickListener(this);
		mCTVZero.setOnClickListener(this);
		mLLBackClean.setOnClickListener(this);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.two_or_one_btn_head_back:// finish
			if (isAdded()) {
				getActivity().finish();
			}
			break;
		case R.id.ctv_one:// 1
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_two:// 2
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_three:// 3
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_four:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_five:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_six:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_seven:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_eight:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_nine:
			setTextContent((CheckedTextView) v);
			break;
		case R.id.ctv_zero:
			setTextContent((CheckedTextView) v);
			break;

		case R.id.ll_back_clean:
			cleanTextContent();
			break;

		}

	}

	/**
	 * 
	 * @Title: setTextContent
	 * @Description: 设置输入的字
	 * @param
	 * @return
	 * @throws
	 */
	private void setTextContent(CheckedTextView v) {
		String number = v.getText().toString();
		// icon_enter_text_bg
		String first = mCTVEnterFirst.getText().toString();
		String second = mCTVEnterSecond.getText().toString();
		String three = mCTVEnterThird.getText().toString();
		String fourth = mCTVEnterFourth.getText().toString();
		if (TextUtils.isEmpty(first)) {
			mCTVEnterFirst.setText(number);
			mCTVEnterFirst.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
		} else if (TextUtils.isEmpty(second)) {
			mCTVEnterSecond.setText(number);
			mCTVEnterSecond.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
		} else if (TextUtils.isEmpty(three)) {
			mCTVEnterThird.setText(number);
			mCTVEnterThird.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
		} else if (TextUtils.isEmpty(fourth)) {
			mCTVEnterFourth.setText(number);
			mCTVEnterFourth.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
			StringBuilder strBuild = new StringBuilder();// 获得输入的数字
			strBuild.append(mCTVEnterFirst.getText().toString())
					.append(mCTVEnterSecond.getText().toString())
					.append(mCTVEnterThird.getText().toString())
					.append(mCTVEnterFourth.getText().toString());
			if (!TextUtils.isEmpty(strBuild.toString())) {
				enterFinishGetServerStatus(strBuild.toString());
			} else {
				return;
			}

		}

	}

	/**
	 * 
	 * @Title: enterFinishGetServerStatus
	 * @Description: 输入完成请求网络
	 * @param
	 * @return
	 * @throws
	 */
	private void enterFinishGetServerStatus(String string) {

		if ((NetworkUtil.isNetworkAvaliable(TootooPlusApplication
				.getAppContext()))) {
			String loginUserId = SharedPreferenceHelper
					.getLoginUserId(TootooPlusApplication.getAppContext());
			// 显示进度
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.USER_ID, loginUserId);

			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITY_CODE, string);

			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.FACETOFACE,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;
						private String activityId;
						private String groupId;
						private String groupStatus;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject jsobj = new JSONObject(jsonStr);
								if (jsobj.has("Status")) {
									status = jsobj.getString("Status");
									if (!status.equals("1")) {
										if(status.equals("1301")){
											ComponentUtil.showToast(getActivity(),
													"参团码不存在或已过期");
											return;
										}else {
											groupStatus=status;
										}
									

									}

								}
								if (jsobj.has("Data")) {
									String strJson = jsobj.getString("Data");
									JSONObject jobj = new JSONObject(strJson);
									if (jobj.has("Group")) {
										String strJonGroup = jobj
												.getString("Group");
										JSONObject jsonGroup = new JSONObject(
												strJonGroup);
										if (jsonGroup.has("ActivityId")) {
											activityId = jsonGroup
													.getString("ActivityId");

										}
										if (jsonGroup.has("GroupId")) {
											groupId = jsonGroup
													.getString("GroupId");

										}

									}

								}
								
								if (jsobj.has("First")) {
									String first = jsobj.getString("First");
									if (first.equals("0")) {// 已参团是0跳转到
										skipAlreadyGroup(activityId, groupId,groupStatus);
									} else if (first.equals("1")) {// 未参团跳转到活动详情
										skipToActDetailFaceToFace(activityId,
												groupId,groupStatus);

									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
						}
					});

		} else {
		}

	}

	protected void skipToActDetailFaceToFace(String activityId, String groupId,String groupStatus) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), ActivityDetailActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString(ConstantsTooTooEHelper.ACTIVITYID, activityId);
		bundle.putString("group_status", groupStatus);
		bundle.putString("groupid", groupId);
		bundle.putString("activity", "FaceToFaceGroupEnterActivity");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(intent);

	}

	protected void skipAlreadyGroup(String activityId, String groupId,String groupStatus) {
		Intent intent = new Intent(getActivity(), JoinMemberActivity.class);
		intent.putExtra("group_id", groupId);
		intent.putExtra("act_id", activityId);
		if(!TextUtils.isEmpty(groupStatus)){
			intent.putExtra("group_status", groupStatus);
		}else{
			intent.putExtra("group_status", "");
		}
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}

	/**
	 * 
	 * @Title: cleanTextContent
	 * @Description: 清除输入的字
	 * @param
	 * @return
	 * @throws
	 */
	private void cleanTextContent() {
		String first = mCTVEnterFirst.getText().toString();
		String second = mCTVEnterSecond.getText().toString();
		String three = mCTVEnterThird.getText().toString();
		String fourth = mCTVEnterFourth.getText().toString();
		if (!TextUtils.isEmpty(fourth)) {
			mCTVEnterFourth.setText(null);
			mCTVEnterFourth.setCompoundDrawablesWithIntrinsicBounds(
					mEnterTextBg, null, null, null);
		} else if (!TextUtils.isEmpty(three)) {
			mCTVEnterThird.setText(null);
			mCTVEnterThird.setCompoundDrawablesWithIntrinsicBounds(
					mEnterTextBg, null, null, null);
		} else if (!TextUtils.isEmpty(second)) {
			mCTVEnterSecond.setText(null);
			mCTVEnterSecond.setCompoundDrawablesWithIntrinsicBounds(
					mEnterTextBg, null, null, null);
		} else if (!TextUtils.isEmpty(first)) {
			mCTVEnterFirst.setText(null);
			mCTVEnterFirst.setCompoundDrawablesWithIntrinsicBounds(
					mEnterTextBg, null, null, null);

		}
	}

}
