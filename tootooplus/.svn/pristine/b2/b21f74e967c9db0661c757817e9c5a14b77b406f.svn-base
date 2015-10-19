package com.ninetowns.tootooplus.fragment;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.activity.AddressListActivity;
import com.ninetowns.tootooplus.activity.FaceToFaceGroupEnterActivity;
import com.ninetowns.tootooplus.activity.MyFreeGroupActivity;
import com.ninetowns.tootooplus.activity.PhoneNameListActivity;
import com.ninetowns.tootooplus.bean.GroupDetailBean;
import com.ninetowns.tootooplus.bean.JoinMemberBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.JoinMemberParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.ShareUtils;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JoinMemberFragment extends
		PageListFragment<GridView, List<JoinMemberBean>, JoinMemberParser> {

	private String group_id = "";

	private String act_id = "";

	private String act_name = "";
	/** 线上还是线下 **/
	private String act_type = "";
	// "0"为未报名; "1"为已报名，待审核; "-1"已报名，但审核失败; "2"为被选中
	private String group_status = "";

	private String group_countParticipant = "";
	// 活动是否已经结束, "0"表示未结束, "1"表示已结束
	private String act_isEnd = "";

	/** 是否有删除和报名组团的权限， "0"表示没权限, "1"表示有权限 **/
	private String authority = "0";

	private TextView join_member_join_tv;

	private RefreshableGridView join_member_gv;
	/** 底部布局，用来是否显示 **/
	private LinearLayout join_member_bottom_layout;

	private List<JoinMemberBean> joinMemList;

	private TextView two_or_one_btn_head_second_tv;

	private RelativeLayout two_or_one_btn_head_second_layout;

	private LinearLayout join_mem_num_layout;

	private TextView join_member_num_tv;

	private String eatCode;
	/**
	 * 用来显示优先码
	 */
	private LinearLayout mLLYouXianma;

	private com.ninetowns.ui.widget.text.MarqueeTextView mCTyouxianma;

	private com.ninetowns.ui.widget.text.MarqueeTextView ct_time;

	private com.ninetowns.ui.widget.text.MarqueeTextView ct_adress;

	private String strAdress;

	private String strTime;

	private int titleTop;

	private int titleTopFace;

	private String source;

	private String activityCode;

	private CheckedTextView mCTEnterNum;
	private GroupDetailBean groupDeataBean;

	private RelativeLayout mRLNum;

	private RelativeLayout llnotice;

	private int titlenoticetop;

	private String minGroupNumber;

	private String minPeopleNumber;

	private Integer minGroupM;

	private Integer minPeoMem;

	private int mTotalPage;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		super.onLoadData(true, true, true);
	}

	@Override
	public JoinMemberParser setParser(String str) {
		JoinMemberParser joinMemParser = new JoinMemberParser(str);
		String delStatus = joinMemParser.getStatus();
		mTotalPage = joinMemParser.getTotalPage();
		if (!TextUtils.isEmpty(delStatus) && delStatus.equals("3402")) {
			if (isAdded()) {
				ComponentUtil.showToast(getActivity(), getActivity()
						.getResources().getString(R.string.del_user));
				/*
				 * if(joinMemList!=null&&joinMemList.size()>0){ for (int i = 0;
				 * i < joinMemList.size(); i++) { JoinMemberBean item =
				 * joinMemList.get(i); item.getUserId()
				 * 
				 * } }
				 */
			}

		} else {

			authority = joinMemParser.getAuthority();
			minGroupNumber = joinMemParser.getMinGroupNumber();
			minPeopleNumber = joinMemParser.getMinPeopleNumber();

			eatCode = joinMemParser.getEatCode();// 不为null就是大白痴 报名成功的 线下的
			strAdress = joinMemParser.getActivityAddress();
			strTime = joinMemParser.getActivityTime();
			source = joinMemParser.getSource();
			activityCode = joinMemParser.getActivityCode();
			groupDeataBean = joinMemParser.getGroupDetailBean();
			if (!TextUtils.isEmpty(activityCode)) {
				mCTEnterNum.setText(activityCode);
			}
			RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) join_member_gv
					.getLayoutParams();

			RelativeLayout.LayoutParams layoutParamsNotice = (android.widget.RelativeLayout.LayoutParams) llnotice
					.getLayoutParams();

			if (groupDeataBean != null) {
				group_id = groupDeataBean.getGroupId();
				act_id = groupDeataBean.getActivityId();
				act_name = groupDeataBean.getActivityName();
				act_type = groupDeataBean.getOnLineStatus();
				group_status = groupDeataBean.getStatus();
				group_countParticipant = groupDeataBean.getCountParticipant();
				act_isEnd = groupDeataBean.getIsEnd();
			}
			if (!TextUtils.isEmpty(source)) {
				if (source.equals("2")) {// 面对面
					mRLNum.setVisibility(View.VISIBLE);
					layoutParams.topMargin = titleTopFace;
					layoutParamsNotice.topMargin = titleTopFace
							- titlenoticetop;
				} else {
					mRLNum.setVisibility(View.GONE);
					layoutParams.topMargin = titleTop;
					layoutParamsNotice.topMargin = titleTop - titlenoticetop;
					if (!TextUtils.isEmpty(group_status)
							&& group_status.equals("0")
							&& (act_isEnd == null || !act_isEnd.equals("1"))) {
						two_or_one_btn_head_second_tv
								.setVisibility(View.VISIBLE);

					}
				}
				join_member_gv.setLayoutParams(layoutParams);
				llnotice.setLayoutParams(layoutParamsNotice);
			}
			two_or_one_btn_head_second_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 分享
							ShareUtils.showShare(getActivity(), act_id,
									group_id, act_name);

						}
					});
		}
		return joinMemParser;
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet
				.setmStrHttpApi(TootooeNetApiUrlHelper.JOIN_GROUP_MEMEBER_URL);
		if (!TextUtils.isEmpty(group_id)) {
			requestParamsNet
					.addQueryStringParameter(
							TootooeNetApiUrlHelper.JOIN_GROUP_MEMEBER_GROUPID,
							group_id);
		}

		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.JOIN_GROUP_MEMEBER_USERID,
				SharedPreferenceHelper.getLoginUserId(getActivity()));

		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.REMARK_STORY_PAGESIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE));
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.REMARK_STORY_PAGE,
				String.valueOf(currentpage));
		return requestParamsNet;
	}

	class JoinMemGvAdapter extends BaseAdapter {

		private Context context;

		private List<JoinMemberBean> list;

		public JoinMemGvAdapter(Context context, List<JoinMemberBean> list) {

			this.context = context;

			this.list = list;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list != null && list.size() > 0) {
				return list.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.join_mem_gv_item, null);

				vh.join_mem_item_head_photo = (ImageView) convertView
						.findViewById(R.id.join_mem_item_head_photo);

				vh.join_mem_item_delete = (ImageView) convertView
						.findViewById(R.id.join_mem_item_delete);

				vh.join_mem_item_user = (TextView) convertView
						.findViewById(R.id.join_mem_item_user);

				vh.join_mem_item_is_admin = (ImageView) convertView
						.findViewById(R.id.join_mem_item_is_admin);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			ImageLoader.getInstance().displayImage(
					list.get(position).getLogoUrl(),
					vh.join_mem_item_head_photo, CommonUtil.OPTIONS_HEADPHOTO);

			if (!TextUtils.isEmpty(list.get(position).getUserName())) {
				vh.join_mem_item_user.setText(list.get(position).getUserName());
			} else {
				vh.join_mem_item_user.setText("");
			}

			// 如果活动已经结束，那么就不能删除
			if (act_isEnd != null && act_isEnd.equals("1")) {
				vh.join_mem_item_delete.setVisibility(View.GONE);
			} else {
				if (group_status.equals("0") && authority.equals("1")) {
					if (!TextUtils.isEmpty(list.get(position).getIsAdmin())
							&& list.get(position).getIsAdmin().equals("1")) {
						vh.join_mem_item_delete.setVisibility(View.GONE);
					} else {
						vh.join_mem_item_delete.setVisibility(View.VISIBLE);
					}
				} else {
					vh.join_mem_item_delete.setVisibility(View.GONE);
				}

			}

			// 如果是团长显示团长标记，如果不是团长不显示
			if (!TextUtils.isEmpty(list.get(position).getIsAdmin())
					&& list.get(position).getIsAdmin().equals("1")) {
				vh.join_mem_item_is_admin.setVisibility(View.VISIBLE);
			} else {
				vh.join_mem_item_is_admin.setVisibility(View.GONE);
			}

			vh.join_mem_item_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除
					showProgressDialog();
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.DEL_GROUP_MEMBER_GROUPID,
							list.get(position).getGroupId());
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.DEL_GROUP_MEMBER_USERID,
							list.get(position).getUserId());
					CommonUtil.xUtilsGetSend(
							TootooeNetApiUrlHelper.DEL_GROUP_MEMBER_URL,
							requestParamsNet, new RequestCallBack<String>() {

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									closeProgressDialogFragment();
									String jsonStr = new String(
											responseInfo.result);

									try {
										JSONObject jsonObj = new JSONObject(
												jsonStr);
										String register_status = "";
										if (jsonObj.has("Status")) {
											register_status = jsonObj
													.getString("Status");
										}

										if (register_status.equals("1")) {
											list.remove(position);
											notifyDataSetChanged();
											if (isAdded())
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.join_mem_delete_success));
										} else if (register_status
												.equals("3904")) {
											if (isAdded())
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.join_mem_delete_fail));
										}

									} catch (Exception e) {
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									// TODO Auto-generated method stub
									closeProgressDialogFragment();
									if (isAdded())
										ComponentUtil
												.showToast(
														getActivity(),
														getResources()
																.getString(
																		R.string.errcode_network_response_timeout));
								}
							});

				}
			});
			return convertView;
		}

		class ViewHolder {

			ImageView join_mem_item_head_photo;

			ImageView join_mem_item_delete;

			TextView join_mem_item_user;

			ImageView join_mem_item_is_admin;
		}

	}

	private void yxmPop() {
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		// 屏幕宽度
		int screen_width = wm.getDefaultDisplay().getWidth();
		// 屏幕高度
		int screen_height = wm.getDefaultDisplay().getHeight();
		final PopupWindow popupWindow = new PopupWindow(getActivity());

		if (!popupWindow.isShowing()) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.yxm_pop_layout, null);

			popupWindow.setContentView(view);

			// 窗口的宽带和高度根据情况定义
			popupWindow.setWidth(screen_width);
			popupWindow.setHeight(screen_height);

			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));
			popupWindow.setOutsideTouchable(false);

			popupWindow.showAtLocation(LayoutInflater.from(getActivity())
					.inflate(R.layout.join_member_fragment, null),
			// 位置可以按要求定义
					Gravity.NO_GRAVITY, 0, 0);
			LinearLayout yxm_pop_all_layout = (LinearLayout) view
					.findViewById(R.id.yxm_pop_all_layout);
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) yxm_pop_all_layout
					.getLayoutParams();
			linearParams.width = screen_width * 3 / 4;
			linearParams.height = screen_height / 3;
			yxm_pop_all_layout.setLayoutParams(linearParams);

			// 用优先码
			final CheckBox use_yxm_pop_cb = (CheckBox) view
					.findViewById(R.id.use_yxm_pop_cb);
			// 默认使用优先码
			use_yxm_pop_cb.setChecked(true);

			final CheckBox unuse_yxm_pop_cb = (CheckBox) view
					.findViewById(R.id.unuse_yxm_pop_cb);

			LinearLayout use_yxm_pop_layout = (LinearLayout) view
					.findViewById(R.id.use_yxm_pop_layout);
			use_yxm_pop_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!use_yxm_pop_cb.isChecked()) {
						use_yxm_pop_cb.setChecked(true);
						unuse_yxm_pop_cb.setChecked(false);
					}

				}
			});
			LinearLayout unuse_yxm_pop_layout = (LinearLayout) view
					.findViewById(R.id.unuse_yxm_pop_layout);
			unuse_yxm_pop_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!unuse_yxm_pop_cb.isChecked()) {
						unuse_yxm_pop_cb.setChecked(true);
						use_yxm_pop_cb.setChecked(false);
					}

				}
			});

			final EditText use_yxm_pop_et = (EditText) view
					.findViewById(R.id.use_yxm_pop_et);

			LinearLayout yxm_pop_submit_layout = (LinearLayout) view
					.findViewById(R.id.yxm_pop_submit_layout);
			yxm_pop_submit_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 线下
					showProgressDialog();
					// 组团报名
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_USERID,
									SharedPreferenceHelper
											.getLoginUserId(getActivity()));
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_GROUPID,
									group_id);
					requestParamsNet
							.addQueryStringParameter(
									TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_ACTIVITYID,
									act_id);
					if (use_yxm_pop_cb.isChecked()) {
						if (use_yxm_pop_et.getText().toString().trim()
								.equals("")) {
							if (isAdded())
								ComponentUtil.showToast(
										getActivity(),
										getResources().getString(
												R.string.use_yxm_tv_no_empty));
							closeProgressDialogFragment();
							return;
						} else {
							requestParamsNet
									.addQueryStringParameter(
											TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_PRIORITY_CODE,
											use_yxm_pop_et.getText().toString()
													.trim());
						}
					}
					CommonUtil.xUtilsGetSend(
							TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_URL,
							requestParamsNet, new RequestCallBack<String>() {

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									closeProgressDialogFragment();
									String jsonStr = new String(
											responseInfo.result);

									try {
										JSONObject jsonObj = new JSONObject(
												jsonStr);
										String register_status = "";
										if (jsonObj.has("Status")) {
											register_status = jsonObj
													.getString("Status");
										}

										if (register_status.equals("1")) {
											// 跳转到活动详情
											Intent act_intent = new Intent(
													getActivity(),
													ActivityDetailActivity.class);
											Bundle bundle = new Bundle();
											bundle.putString(
													TootooeNetApiUrlHelper.ACTIVITYID,
													act_id);
											bundle.putString("currentPosition",
													"0");
											act_intent
													.putExtra(
															TootooeNetApiUrlHelper.BUNDLE,
															bundle);
											act_intent
													.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											startActivity(act_intent);
											AppManager
													.getAppManager()
													.finishActivity(
															MyFreeGroupActivity.class);
											getActivity().finish();

										} else if (register_status
												.equals("3104")) {
											if (isAdded())
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.join_enter_group_yxm_fail));
										} else if (register_status
												.equals("3105")) {
											if (isAdded())
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.join_enter_group_less_ten));
										} else {
											if (isAdded())
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.join_enter_group_fail));
										}

									} catch (Exception e) {
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									closeProgressDialogFragment();
									if (isAdded())
										ComponentUtil
												.showToast(
														getActivity(),
														getResources()
																.getString(
																		R.string.errcode_network_response_timeout));
								}
							});
					popupWindow.dismiss();
				}
			});

			LinearLayout yxm_pop_cancel_layout = (LinearLayout) view
					.findViewById(R.id.yxm_pop_cancel_layout);
			yxm_pop_cancel_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
				}
			});
		}
	}

	@Override
	protected PullToRefreshBase<GridView> initRefreshIdView() {
		return join_member_gv;
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		Intent intent = getActivity().getIntent();
		if (!(getActivity() instanceof FaceToFaceGroupEnterActivity)) {
			group_id = intent.getStringExtra("group_id");
			act_id = intent.getStringExtra("act_id");
			act_name = intent.getStringExtra("act_name");
			act_type = intent.getStringExtra("act_type");
			group_status = intent.getStringExtra("group_status");
			group_countParticipant = intent
					.getStringExtra("group_countParticipant");
			act_isEnd = intent.getStringExtra("act_isEnd");

		}

		View view = inflater.inflate(R.layout.join_member_fragment, null);
		mLLYouXianma = (LinearLayout) view.findViewById(R.id.ll_youxianma);
		mCTEnterNum = (CheckedTextView) view.findViewById(R.id.ct_enter_num);
		mRLNum = (RelativeLayout) view.findViewById(R.id.rl_face_num);

		mCTyouxianma = (com.ninetowns.ui.widget.text.MarqueeTextView) view
				.findViewById(R.id.ct_youxianma);

		ct_time = (com.ninetowns.ui.widget.text.MarqueeTextView) view
				.findViewById(R.id.ct_time);
		ct_adress = (com.ninetowns.ui.widget.text.MarqueeTextView) view
				.findViewById(R.id.ct_adress);

		LinearLayout two_or_one_btn_head_back = (LinearLayout) view
				.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		TextView two_or_one_btn_head_title = (TextView) view
				.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.join_member_title);

		two_or_one_btn_head_second_tv = (TextView) view
				.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setText(R.string.invite_code_share_title);

		// 用于来做点击的
		two_or_one_btn_head_second_layout = (RelativeLayout) view
				.findViewById(R.id.two_or_one_btn_head_second_layout);

		join_member_join_tv = (TextView) view
				.findViewById(R.id.join_member_join_tv);
		llnotice = (RelativeLayout) view.findViewById(R.id.ll_notice);
		if (isAdded()) {
			titlenoticetop = (int) getResources().getDimension(
					R.dimen.notice_face_refresh);// 提示的高度

			titleTop = (int) getResources().getDimension(
					R.dimen.title_bar_height);

			titleTopFace = (int) getResources().getDimension(R.dimen.face_top);
		}

		titleTop = titleTop + titlenoticetop;
		titleTopFace = titleTop + titleTopFace;
		join_member_gv = (RefreshableGridView) view
				.findViewById(R.id.join_mem_gv);
		// layoutpar.topMargin=
		join_member_bottom_layout = (LinearLayout) view
				.findViewById(R.id.join_mem_bottom_layout);

		join_mem_num_layout = (LinearLayout) view
				.findViewById(R.id.join_mem_num_layout);

		join_member_num_tv = (TextView) view
				.findViewById(R.id.join_member_num_tv);

		return view;

	}

	@Override
	public int getTotalPage() {
		return mTotalPage;
	}

	@Override
	public void getPageListParserResult(List<JoinMemberBean> parserResult) {

		if (parserResult != null && parserResult.size() > 0) {
			if (isAdded()) {
				boolean isFirst = SharedPreferenceHelper
						.getFirstGroup(getActivity());
				if (isFirst) {
					// 如果是第一次
				} else {// 如果不是第一次那么设置为第一次
					SharedPreferenceHelper.setIsFirstGroup(getActivity());
				}
			}
			if (currentpage == 1) {
				joinMemList = parserResult;
			} else {
				joinMemList.addAll(parserResult);
;			}
			if (!TextUtils.isEmpty(eatCode)) {
				if (!TextUtils.isEmpty(strAdress)) {
					ct_adress.setText(strAdress);

				}
				if (!TextUtils.isEmpty(strTime)) {
					ct_time.setText(strTime);
				}

				mLLYouXianma.setVisibility(View.VISIBLE);
				join_mem_num_layout.setVisibility(View.GONE);
				join_member_bottom_layout.setVisibility(View.GONE);
				mCTyouxianma.setText(eatCode);
				setGoneOfNoitceNum();
			} else {
				mLLYouXianma.setVisibility(View.GONE);
				if (group_status.equals("0")) {
					join_member_bottom_layout.setVisibility(View.VISIBLE);

					if (act_isEnd != null && act_isEnd.equals("1")) {// 已经结束
						join_member_join_tv.setText(R.string.join_act_is_end);
						join_mem_num_layout.setVisibility(View.GONE);
						setGoneOfNoitceNum();
					} else {
						if (!TextUtils.isEmpty(authority)
								&& authority.equals("1")) {
							boolean isGetMembersGroup = SharedPreferenceHelper
									.getFirstGetMembersGroup(getActivity());
							if (isGetMembersGroup) {
								CommonUtil
										.showFirstGuideDialog(
												getActivity(),
												ConstantsTooTooEHelper.FIRST_GUIDE_GET_MEMBERS_GROUP);
							}

							join_member_join_tv
									.setText(R.string.join_enter_hint);
							join_member_join_tv
									.setBackgroundResource(R.drawable.btn_join_enter);
							join_member_join_tv
									.setOnClickListener(new OnClickListener() {

										private Integer minGroupMember;
										private Integer minPeoMember;

										@Override
										public void onClick(View v) {
											if (TextUtils
													.isEmpty(group_countParticipant)
													|| minGroupNumber
															.equals("null")) {
												return;
											}
											if (TextUtils
													.isEmpty(minGroupNumber)
													|| minGroupNumber
															.equals("null")) {
												return;
											} else {
												minGroupMember = Integer
														.valueOf(minGroupNumber);
											}
											if (TextUtils
													.isEmpty(minPeopleNumber)
													|| minGroupNumber
															.equals("null")) {
												return;
											} else {
												minPeoMember = Integer
														.valueOf(minPeopleNumber);
											}
											int max_num = Integer
													.parseInt(group_countParticipant)
													/ minGroupMember;// 判断的人数minGroupNumber
											if (joinMemList != null
													&& joinMemList.size() >= minPeoMember
													&& max_num
															- joinMemList
																	.size() >= 0) {// minPeopleNumber
												if (act_type.equals("0")) {
													// 线上
													Intent address_intent = new Intent(
															getActivity(),
															AddressListActivity.class);
													address_intent
															.putExtra(
																	TootooeNetApiUrlHelper.ACTIVITYID,
																	act_id);
													address_intent.putExtra(
															"group_id",
															group_id);
													startActivity(address_intent);

												} else {
													Intent address_intent = new Intent(
															getActivity(),
															PhoneNameListActivity.class);
													address_intent
															.putExtra(
																	TootooeNetApiUrlHelper.ACTIVITYID,
																	act_id);
													address_intent.putExtra(
															"group_id",
															group_id);
													startActivity(address_intent);
													// 线下
													// yxmPop();
												}
											} else if (max_num
													- joinMemList.size() < 0) {
												if (isAdded())
													ComponentUtil
															.showToast(
																	getActivity(),
																	getResources()
																			.getString(
																					R.string.most_man));
											} else {
												StringBuilder sbuild = new StringBuilder();
												sbuild.append("亲，需要努力哦，")
														.append(minPeopleNumber)
														.append("人以上可组团报名哦");
												if (isAdded()) {
													ComponentUtil.showToast(
															getActivity(),
															sbuild.toString());
												}
											}

										}
									});
						} else {
							join_member_join_tv
									.setText(R.string.join_is_not_submit);
						}

						join_mem_num_layout.setVisibility(View.VISIBLE);
						if (TextUtils.isEmpty(group_countParticipant)
								|| minGroupNumber.equals("null")) {
							return;
						}
						if (TextUtils.isEmpty(minGroupNumber)
								|| minGroupNumber.equals("null")) {
							return;
						} else {
							minGroupM = Integer.valueOf(minGroupNumber);
						}
						if (TextUtils.isEmpty(minPeopleNumber)
								|| minGroupNumber.equals("null")) {
							return;
						} else {
							minPeoMem = Integer.valueOf(minPeopleNumber);
						}
						int max_num = Integer.parseInt(group_countParticipant)
								/ minGroupM;// 判断的人数
						if (max_num - joinMemList.size() > 0) {
							join_member_num_tv.setText(minPeoMem
									+ getResources().getString(
											R.string.join_mem_max_hint)
									+ (max_num - joinMemList.size()) + "人");
						} else if (max_num - joinMemList.size() == 0) {
							join_member_num_tv.setText(getResources()
									.getString(R.string.join_mem_full_hint));
						} else {
							join_member_num_tv.setText(getResources()
									.getString(R.string.join_mem_full_more)
									+ max_num
									+ getResources().getString(
											R.string.man_count));
						}
					}

				} else if (group_status.equals("1")) {// 组团已经提交 去掉提示
					join_member_bottom_layout.setVisibility(View.VISIBLE);
					join_member_join_tv
							.setText(R.string.join_submit_wait_audit);
					join_mem_num_layout.setVisibility(View.GONE);
					setGoneOfNoitceNum();
				} else if (group_status.equals("-1")) {
					join_member_bottom_layout.setVisibility(View.VISIBLE);
					join_member_join_tv
							.setText(R.string.join_submit_audit_fail);
					join_mem_num_layout.setVisibility(View.GONE);
					setGoneOfNoitceNum();
				} else if (group_status.equals("2")) {// 小白吃被选中
					join_member_bottom_layout.setVisibility(View.GONE);
					join_mem_num_layout.setVisibility(View.GONE);
					setGoneOfNoitceNum();
				}

			}
			join_member_gv.setAdapter(new JoinMemGvAdapter(getActivity(),
					joinMemList));

		} else {
			join_member_gv.setAdapter(null);
		}

	}

	/**
	 * @Title: setGoneOfNoitceNum
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void setGoneOfNoitceNum() {
		RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) join_member_gv
				.getLayoutParams();
		if (isAdded()) {
			layoutParams.topMargin = (int) getResources().getDimension(
					R.dimen.title_bar_height);
		}

		join_member_gv.setLayoutParams(layoutParams);
		mRLNum.setVisibility(View.GONE);
		llnotice.setVisibility(View.GONE);
	}

}
