package com.ninetowns.tootoopluse.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.interpolator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.EatCodeAdapter;
import com.ninetowns.tootoopluse.adapter.JoinMemberAdapter;
import com.ninetowns.tootoopluse.adapter.JoinMemberAdapter.OnNotifyChangeData;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GridViewGroupBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.MyJoinMemGroupParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;
import com.ninetowns.ui.widget.listview.multicolumn.HorizontalListView;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

public class MyTempJoinMemberFragment extends
		BaseFragment<List<List<GridViewGroupBean>>, MyJoinMemGroupParser>
		implements View.OnClickListener, OnItemClickListener,
		OnNotifyChangeData {

	@ViewInject(R.id.tv_rem_info)
	private TextView mTvInfo;
	@ViewInject(R.id.tv_comit)
	private TextView mTVComit;
	@ViewInject(R.id.et_left)
	private HorizontalListView mHorizontalListView;
	// @ViewInject(R.id.gl_rember)
	// private GridView mGV;
	@ViewInject(R.id.gl_rember)
	private RefreshableListView mRefreshableListView;
	@ViewInject(R.id.ibtn_left)
	private ImageButton ibtnLeft;
	@ViewInject(R.id.tv_title)
	private TextView mTVTitle;
	@ViewInject(R.id.ibtn_right)
	private ImageButton ibtnRight;
	private View remberCount;
	private String groupid;
	private String activityId;
	private List<GridViewGroupBean> localResult;
	private String itemEateCode;
	private String username;
	private boolean isUsered;

	private JoinMemberAdapter adapter;

	private EatCodeAdapter mEatCodeAdapter;

	private List<GridViewGroupBean> mEatCodeList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		groupid = getActivity().getIntent().getStringExtra(
				ConstantsTooTooEHelper.GROUP_ID);
		activityId = getActivity().getIntent().getStringExtra(
				ConstantsTooTooEHelper.ACTIVITYID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		remberCount = inflater.inflate(R.layout.mytempjonimemberact, null);
		ViewUtils.inject(this, remberCount);
		mTVComit.setOnClickListener(this);
		ibtnLeft.setOnClickListener(this);
		ibtnRight.setVisibility(View.INVISIBLE);
		mTVTitle.setText(R.string.already_title_group);
		mRefreshableListView.setOnItemClickListener(mOnItemClickListener);

		return remberCount;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadData();
	}

	private void loadData() {
		super.onLoadData(true, true, true);
	}

	@Override
	public MyJoinMemGroupParser setParser(String str) {
		MyJoinMemGroupParser parser = new MyJoinMemGroupParser(str);
		String info = parser.getInfo();
		if (!TextUtils.isEmpty(info)) {
			mTvInfo.setText(info);
		}
		return parser;
	}

	private List<HashMap<String, List<GridViewGroupBean>>> mGroupList;

	@Override
	public void getParserResult(List<List<GridViewGroupBean>> parserResult) {

		mEatCodeList = new ArrayList<GridViewGroupBean>();
		// localResult = parserResult;
		mGroupList = new ArrayList<HashMap<String, List<GridViewGroupBean>>>();
		// 父数据
		List<GridViewGroupBean> list = new ArrayList<GridViewGroupBean>();
		// 子数据
		// List<List<GridViewGroupBean>> mChildList = new
		// ArrayList<List<GridViewGroupBean>>();

		if (parserResult != null && parserResult.size() > 0) {
			for (int i = 0; i < parserResult.size(); i++) {
				List<GridViewGroupBean> mChildList = new ArrayList<GridViewGroupBean>();
				HashMap<String, List<GridViewGroupBean>> map = new HashMap<String, List<GridViewGroupBean>>();
				for (int j = 0; j < parserResult.get(i).size(); j++) {

					GridViewGroupBean bean = parserResult.get(i).get(j);

					if (bean.getIsAdmin().equals("1")) {
						// 添加团长
						HashMap<String, List<GridViewGroupBean>> leader = new HashMap<String, List<GridViewGroupBean>>();
						List<GridViewGroupBean> mRootList = new ArrayList<GridViewGroupBean>();
						mRootList.add(bean);
						leader.put("IsAdmin", mRootList);
						mGroupList.add(leader);
					} else {
						mChildList.add(bean);
					}
				}
				map.put("IsAdmin", mChildList);
				mGroupList.add(map);
				// mChildList.add(parserResult.get(i));
			}
			adapter = new JoinMemberAdapter(getActivity(), mGroupList);
			mRefreshableListView.setAdapter(adapter);
			mEatCodeAdapter = new EatCodeAdapter(mEatCodeList);
			mHorizontalListView.setAdapter(mEatCodeAdapter);
			adapter.setOnNotifyChangeData(this);
		}

	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet par = new RequestParamsNet();
		par.setmStrHttpApi(TootooeNetApiUrlHelper.MY_FREE_GROUP_URL_MEMBER);
		if (!TextUtils.isEmpty(groupid)) {
			par.addQueryStringParameter(
					TootooeNetApiUrlHelper.ALL_MEMBER_EXIT_GROUP_GROUPID,
					groupid);
		}
		if (!TextUtils.isEmpty(activityId)) {
			par.addQueryStringParameter(
					TootooeNetApiUrlHelper.MY_FREE_GROUP_URL_MEMBER_ACT,
					activityId);
		}

		return par;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_comit:
			if (isUsered) {
				ComponentUtil.showToast(TootooPlusEApplication.getAppContext(),
						"该用户已签到");
			} else {

				final String mEatCodeString = appendStringEatCode(mEatCodeAdapter
						.getEatCodeList());
				if (!TextUtils.isEmpty(mEatCodeString)) {

					TooSureCancelDialog tooDialog = new TooSureCancelDialog(
							getActivity());
					tooDialog.setDialogTitle(R.string.rainbo_hint);
					StringBuilder strbu = new StringBuilder();
					strbu.append("确定使用")
							.append(mEatCodeAdapter.getEatCodeList().get(0)
									.getUserName()).append("等")
							.append(mEatCodeAdapter.getEatCodeList().size())
							.append("人的白吃码吗？");

					tooDialog.setDialogContent(strbu.toString());
					tooDialog.setTooDialogCallBack(new TooDialogCallBack() {

						@Override
						public void onTooDialogSure() {
							mEatCodeAdapter.getEatCodeList().clear();
							mEatCodeAdapter.notifyDataSetChanged();
//							for (int i = 0; i < mGroupList.size(); i++) {
//								HashMap<String, List<GridViewGroupBean>> map = mGroupList
//										.get(i);
//								List<GridViewGroupBean> list = map
//										.get("IsAdmin");
//								for (int j = 0; j < list.size(); j++) {
//									if (list.get(j).getIsUsed().equals("2")) {
//										list.get(j).setUserId("0");
//									}
//								}
//							}
//							adapter.notifyDataSetChanged();
							shiyongNetData(mEatCodeString);
						}

						@Override
						public void onTooDialogCancel() {

						}
					});
					tooDialog.show();

				} else {
					ComponentUtil.showToast(
							TootooPlusEApplication.getAppContext(), "请选择白吃码");
				}

			}

			break;
		case R.id.ibtn_left:
			if (isAdded()) {
				getActivity().finish();
			}

			break;

		default:
			break;
		}

	}

	/**
	 * 
	 * @Title: shiyongNetData
	 * @Description: 使用优先码
	 * @param
	 * @return
	 * @throws
	 */
	private void shiyongNetData(String baichima) {

		if ((NetworkUtil.isNetworkAvaliable(TootooPlusEApplication
				.getAppContext()))) {
			// 显示进度
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ALL_MEMBER_EXIT_GROUP_GROUPID,
					groupid);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITYID, activityId);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.EatCode, baichima);// userid
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.MY_USERDEAT,
					requestParamsNet, new RequestCallBack<String>() {

						private String status;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String strreault = responseInfo.result;
							if (!TextUtils.isEmpty(strreault)) {
								try {
									JSONObject obj = new JSONObject(strreault);
									if (obj.has("Status")) {
										String status = obj.getString("Status");
										if (status.equals("1")) {
											// mETLeft.setText("");
											loadData();
											// <string
											// name="qiandaosuccess">签到成功！</string>
											// <string
											// name="request_error">请求失败!</string>
											// <string
											// name="no_user">无此用户!</string>
											// <string
											// name="the_user_isget">该用户已签到!</string>
											if (isAdded()) {
												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.qiandaosuccess));
											}

										} else if (status.equals("3103")) {

											if (isAdded()) {
												List<String> list = new ArrayList<String>();
												JSONArray array = obj
														.getJSONArray("Users");
												StringBuilder strbu = new StringBuilder();
												// for(int i=0;i<
												// array.length();i++){
												// strbu.append("").append(mEatCodeAdapter.getEatCodeList().get(0).getUserName()).append("\n\t");
												// list.add(array.getString(i));
												// }

												strbu.append("");
												for (int i = 0; i < array
														.length(); i++) {
													strbu.append(
															array.getString(i))
															.append(",");
												}
												strbu.deleteCharAt(strbu
														.toString()
														.lastIndexOf(","));
												strbu.append("\n\t\t\t\t使用白吃码失败！");

												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.qiandaosuccess));

												TooSureCancelDialog tooDialog = new TooSureCancelDialog(
														getActivity());
												tooDialog
														.setDialogTitle(R.string.rainbo_hint);

												tooDialog
														.setDialogContent(strbu
																.toString());
												tooDialog
														.setTooDialogCallBack(new TooDialogCallBack() {

															@Override
															public void onTooDialogSure() {

															}

															@Override
															public void onTooDialogCancel() {

															}
														});
												tooDialog.show();
											}
										} else if (status.equals("3101")) {
											if (isAdded()) {
												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.no_user));
											}
										} else if (status.equals("3102")) {
											//

											if (isAdded()) {
												JSONArray array = obj
														.getJSONArray("Users");
												StringBuilder strbu = new StringBuilder();
												strbu.append("");
												for (int i = 0; i < array
														.length(); i++) {
													strbu.append(
															array.getString(i))
															.append(",");
												}
												strbu.deleteCharAt(strbu
														.toString()
														.lastIndexOf(","));
												strbu.append("\n\t\t\t\t用户己经签到!");

												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.qiandaosuccess));

												final TooSureCancelDialog tooDialog = new TooSureCancelDialog(
														getActivity());
												tooDialog
														.setDialogTitle(R.string.rainbo_hint);

												tooDialog
														.setDialogContent(strbu
																.toString());
												tooDialog
														.setTooDialogCallBack(new TooDialogCallBack() {

															@Override
															public void onTooDialogSure() {
																tooDialog
																		.dismiss();
															}

															@Override
															public void onTooDialogCancel() {

															}
														});
												tooDialog.show();
												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.the_user_isget));
											}
										} else if (status.equals("3100")) {
											if (isAdded()) {
												ComponentUtil.showToast(
														TootooPlusEApplication
																.getAppContext(),
														getResources()
																.getString(
																		R.string.error_endter_num));
											}
										}

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil.showToast(
									TootooPlusEApplication.getAppContext(),
									getResources()
											.getString(
													R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					TootooPlusEApplication.getAppContext(),
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		itemEateCode = "";
		username = "";
		if (localResult != null && position != -1 && localResult.size() > 0) {
			GridViewGroupBean item = localResult.get(position);
			itemEateCode = item.getEatCode();
			String status = item.getIsUsed();
			if (!TextUtils.isEmpty(status)) {
				if (status.equals("1")) {
					isUsered = true;
					// 成功
				} else {// 0未成功
					isUsered = false;
				}
			} else {
				isUsered = false;
			}
			username = item.getUserName();
			// mETLeft.setText(itemEateCode);
		}

	}

	/**
	 * 点击成员
	 */
	public OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (!adapter.getCmp().get(position - 1).get(0)) {
				GridViewGroupBean bean = adapter.getmList().get(position - 1)
						.get("IsAdmin").get(0);
				if (adapter.getMultiGroupListMap().containsKey(position - 1)) {
					Map<Integer, Boolean> map = adapter.getMultiGroupListMap()
							.get(position - 1);
					if (map.containsKey(0)) {
						boolean isUsed = map.get(0);
						if (isUsed) {
							mEatCodeAdapter.notify(1, bean);
							map.put(0, false);
							bean.setIsUsed("0");
						} else {
							mEatCodeAdapter.notify(0, bean);
							map.put(0, true);
							bean.setIsUsed("2");
						}
					}
				}
				adapter.notifyDataSetChanged();
			} else {
				ComponentUtil.showToast(TootooPlusEApplication.getAppContext(),
						"该用户已签到");
			}
		}
	};

	@Override
	public void notifyChange(int code, GridViewGroupBean bean) {
		if (bean != null) {
			mEatCodeAdapter.notify(code, bean);
		}
	}

	/**
	 * @Filed appendStringEatCode
	 * @Depend 拼接上传的白吃码(可以进行多人)
	 * @param list
	 *            传入进来的eatcode人员
	 * @return 拼接之后的上传字符串
	 */

	private String appendStringEatCode(List<GridViewGroupBean> list) {

		StringBuffer mEatCodeBuffer = new StringBuffer();

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				mEatCodeBuffer.append(list.get(i).getEatCode()).append(",");
			}
			mEatCodeBuffer.deleteCharAt(mEatCodeBuffer.toString().lastIndexOf(
					","));
			return mEatCodeBuffer.toString();
		}
		return "";
	}

}
