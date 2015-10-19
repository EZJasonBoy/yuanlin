package com.ninetowns.tootooplus.fragment;

import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.PhoneListMineActivity;
import com.ninetowns.tootooplus.activity.PhoneNameListActivity;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ContactListBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;

public class UpdateDelMineAddFragment extends BaseFragment<Object, AbsParser>
		implements View.OnClickListener {

	@ViewInject(R.id.et_username)
	private EditText mETusername;
	@ViewInject(R.id.et_mobile)
	private EditText mMobile;

	@ViewInject(R.id.ibtn_left)
	private ImageButton IbtnLeft;

	@ViewInject(R.id.tv_title)
	private TextView mTitle;
	@ViewInject(R.id.ibtn_right)
	private ImageButton ibtnRight;
	private boolean isUpdate;

	// 从编辑页面传过来的
	private ContactListBean updateAddressBean = null;
	private View updateView;
	private boolean isFooter;
	private String act_id;
	private String group_id;
	private String constantid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		updateView = inflater.inflate(R.layout.update_del_fragment, null);
		getSerBean();
		ViewUtils.inject(this, updateView);
		ibtnRight.setImageResource(R.drawable.btn_keep);
		ibtnRight.setOnClickListener(this);
		IbtnLeft.setOnClickListener(this);
		justAddOrUpdate();

		return updateView;

	}

	/**
	 * @Title: justAddOrUpdate
	 * @Description: 判断是添加还是iugai
	 * @param
	 * @return
	 * @throws
	 */
	private void justAddOrUpdate() {
		if (updateAddressBean != null) {// 修改
			isUpdate = true;
			String name = updateAddressBean.getName();
			String phonenumb = updateAddressBean.getPhone();
			if (!TextUtils.isEmpty(name)) {
				mETusername.setText(name);
			}
			if (!TextUtils.isEmpty(phonenumb)) {
				mMobile.setText(phonenumb);
			}
			mTitle.setText(R.string.update_infotitle);
		} else {// 添加
			mTitle.setText(R.string.add_infotitle);
			isUpdate = false;
		}
	}

	/**
	 * @Title: getSerBean
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void getSerBean() {
		if (isAdded()) {
			if (getActivity() != null) {
				Bundle bundle = getActivity().getIntent().getBundleExtra(
						ConstantsTooTooEHelper.BUNDLE);
				if (bundle != null) {
					isUpdate = bundle
							.getBoolean(ConstantsTooTooEHelper.isUpdate);
					
					try {
						act_id=bundle.getString(TootooeNetApiUrlHelper.ACTIVITYID);
						group_id=bundle.getString("group_id");
						constantid=bundle.getString("constantid");
					
						isFooter = bundle.getBoolean("isFooter");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isUpdate) {
						

						updateAddressBean = (ContactListBean) bundle
								.getSerializable(ConstantsTooTooEHelper.UPDATEDEL_INFO);
					}

				}

			}

		}
	}

	@Override
	public void getParserResult(Object parserResult) {

	}

	@Override
	public RequestParamsNet getApiParmars() {
		return null;
	}

	@Override
	public AbsParser setParser(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_left:
			if (getActivity() != null) {
				getActivity().finish();
			}
			break;
		case R.id.ibtn_right:
			String mobileStr=mMobile.getText().toString();
			String etusername=mETusername.getText().toString();
			if(!TextUtils.isEmpty(etusername)){
				
			}else{
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(), "请输入姓名");
				return ;
			}
			if(!TextUtils.isEmpty(mobileStr)){
				if(CommonUtil.isValidMobiNumber(mobileStr)){
					
				}else{
					ComponentUtil.showToast(TootooPlusApplication.getAppContext(), "手机号码输入错误");
					return ;
				}
				
			}else{
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(), "请输入手机号码");
				return;
			}
			saveData(isUpdate);
			break;

		}

	}

	/**
	 * 
	 * @Title: saveData
	 * @Description: 保存数据
	 * @param
	 * @return
	 * @throws
	 */
	private void saveData(final boolean isUpdate) {

		// 新增
		showProgressDialog();

		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(ConstantsTooTooEHelper.USERID,
				SharedPreferenceHelper.getLoginUserId(getActivity()));
		String mobile = mMobile.getText().toString();
		String username = mETusername.getText().toString();
		requestParamsNet.addQueryStringParameter(
				ConstantsTooTooEHelper.RealName, username);
		requestParamsNet.addQueryStringParameter(
				ConstantsTooTooEHelper.PhoneNumber, mobile);
		if (isUpdate) {
			requestParamsNet.addQueryStringParameter(
					ConstantsTooTooEHelper.CONTACTID,
					updateAddressBean.getContactId());
		}
		CommonUtil.xUtilsGetSend(
				isUpdate ? TootooeNetApiUrlHelper.UPDATE_INOF_PHONE
						: TootooeNetApiUrlHelper.ADD_INOF_PHONE,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						closeProgressDialogFragment();
						String jsonStr = new String(responseInfo.result);
						try {
							JSONObject json = new JSONObject(jsonStr);
							String add_status = "";
							if (json.has("Status")) {
								add_status = json.getString("Status");
							}

							if (add_status.equals("1")) {// 成功
								if (isUpdate) {
									ComponentUtil
											.showToast(
													getActivity(),
													getResources()
															.getString(
																	R.string.phone_update_success));
								} else {
									ComponentUtil
											.showToast(
													getActivity(),
													getResources()
															.getString(
																	R.string.phone_add_success));
								}
								if (isFooter) {
									Intent address_intent = new Intent(
											getActivity(),
											PhoneNameListActivity.class);
									
								
									address_intent.putExtra(TootooeNetApiUrlHelper.ACTIVITYID, act_id);
									address_intent.putExtra("group_id", group_id);
									address_intent.putExtra("constantid", constantid);
									address_intent
											.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(address_intent);
									getActivity().finish();

								} else {
									Intent address_intent = new Intent(
											getActivity(),
											PhoneListMineActivity.class);
									address_intent
											.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(address_intent);
									getActivity().finish();

								}

							} else if (add_status.equals("1381")) {
								ComponentUtil.showToast(
										getActivity(),
										getResources().getString(
												R.string.delivery_add_fail));
							} else if (add_status.equals("1380")) {
								// ComponentUtil.showToast(getActivity(),
								// getResources().getString(R.string.delivery_add_fail));
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						closeProgressDialogFragment();
						ComponentUtil
								.showToast(
										getActivity(),
										getResources()
												.getString(
														R.string.errcode_network_response_timeout));
					}
				});

	}

}
