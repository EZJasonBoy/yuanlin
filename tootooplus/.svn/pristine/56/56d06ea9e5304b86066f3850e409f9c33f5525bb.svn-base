package com.ninetowns.tootooplus.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.FollowFansActivity;
import com.ninetowns.tootooplus.activity.PrivateLetterChat;
import com.ninetowns.tootooplus.bean.MineBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.MineParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PerHomeFragment extends BaseFragment<MineBean, MineParser> {
	/** 用户名称 **/
	private TextView two_or_one_btn_head_title;

	/** 封面图 **/
	private ImageView per_home_head_cover_iv;
	/** 头像 **/
	private ImageView per_home_head_photo;
	/** vip等级 **/
	private ImageView per_home_head_vip;
	/** 个人昵称 **/
	private TextView per_home_head_name;
	/** 关注数 **/
	private TextView per_home_head_follow_count;
	/** 粉丝数 **/
	private TextView per_home_head_fans_count;
	/** 私信 **/
	private LinearLayout per_home_head_letter_layout;
	/** 关注点击布局 **/
	private LinearLayout per_home_head_follow_layout;
	/** 关注状态 **/
	private TextView per_home_head_follow_state;
	/** 图标展示的关注状态 **/
	private ImageView per_home_head_fol_state_iv;

	private MineBean mineBean;

	// 所看信息的userId
	private String userId = "";

	/** 第一个tab点击布局 **/
	private RelativeLayout per_home_act_layout;

	/** 第二个tab点击布局 **/
	private RelativeLayout per_home_store_wish_layout;

	/** 显示第一个tab文字 **/
	private TextView per_home_act_or_story;

	/** 第一个tab下的线 **/
	private View per_home_act_story_view;

	/** 显示第二个tab文字 **/
	private TextView per_home_store_or_custom_wish;
	/** 第二个tab下的线 **/
	private View per_home_store_or_custom_wish_view;

	/** 用来加载Fragment的FrameLayout布局 **/
	private FrameLayout per_home_cont_layout;

	// 手机屏幕宽度
	private int screen_width = 0;

	private View home_fragment_view;

	private LinearLayout per_home_change_layout;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		userId = getActivity().getIntent().getStringExtra("userId");

		super.onLoadData(true, false, false);

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		// 屏幕宽度
		screen_width = wm.getDefaultDisplay().getWidth();

		home_fragment_view = inflater.inflate(R.layout.personal_home_fragment,
				null);

		per_home_change_layout = (LinearLayout) home_fragment_view
				.findViewById(R.id.per_home_change_layout);

		per_home_cont_layout = (FrameLayout) home_fragment_view
				.findViewById(R.id.per_home_cont_layout);

		LinearLayout two_or_one_btn_head_back = (LinearLayout) home_fragment_view
				.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();

			}
		});

		// 用户名称
		two_or_one_btn_head_title = (TextView) home_fragment_view
				.findViewById(R.id.two_or_one_btn_head_title);

		per_home_act_or_story = (TextView) home_fragment_view
				.findViewById(R.id.per_home_act_or_story);
		per_home_store_or_custom_wish = (TextView) home_fragment_view
				.findViewById(R.id.per_home_store_or_custom_wish);
		per_home_act_layout = (RelativeLayout) home_fragment_view
				.findViewById(R.id.per_home_act_layout);

		per_home_store_wish_layout = (RelativeLayout) home_fragment_view
				.findViewById(R.id.per_home_store_wish_layout);

		per_home_act_story_view = home_fragment_view
				.findViewById(R.id.per_home_act_story_view);

		per_home_store_or_custom_wish_view = home_fragment_view
				.findViewById(R.id.per_home_store_or_custom_wish_view);

		per_home_change_layout.setVisibility(View.GONE);

		return home_fragment_view;
	}

	@Override
	public MineParser setParser(String str) {
		// TODO Auto-generated method stub
		return new MineParser(str);
	}

	@Override
	public void getParserResult(MineBean parserResult) {
		if (parserResult != null) {
			// 第一个headView
			View head_view_top = LayoutInflater.from(getActivity()).inflate(
					R.layout.per_home_lv_head_top, null);
			head_view_top.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//因为小米机器点击header的时候会触发OnItemClick事件
					
				}
			});
			RelativeLayout per_home_head_cover_layout = (RelativeLayout) head_view_top
					.findViewById(R.id.per_home_head_cover_layout);
			LinearLayout.LayoutParams lLayoutParams = (LinearLayout.LayoutParams) per_home_head_cover_layout
					.getLayoutParams();
			lLayoutParams.width = screen_width;
			lLayoutParams.height = screen_width * 2 / 3;
			per_home_head_cover_layout.setLayoutParams(lLayoutParams);

			ImageView per_home_lv_head_back = (ImageView) head_view_top
					.findViewById(R.id.per_home_lv_head_back);
			per_home_lv_head_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}
			});

			per_home_head_cover_iv = (ImageView) head_view_top
					.findViewById(R.id.per_home_head_cover_iv);
		

			per_home_head_photo = (ImageView) head_view_top
					.findViewById(R.id.per_home_head_photo);

			per_home_head_vip = (ImageView) head_view_top
					.findViewById(R.id.per_home_head_vip);

			per_home_head_name = (TextView) head_view_top
					.findViewById(R.id.per_home_head_name);

			per_home_head_follow_count = (TextView) head_view_top
					.findViewById(R.id.per_home_head_follow_count);

			per_home_head_fans_count = (TextView) head_view_top
					.findViewById(R.id.per_home_head_fans_count);

			// 第二个headView
			View head_view_next = LayoutInflater.from(getActivity()).inflate(
					R.layout.per_home_lv_head_next, null);
			head_view_next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//防止点击header 走onItemClick
					
				}
			});
			per_home_head_letter_layout = (LinearLayout) head_view_next
					.findViewById(R.id.per_home_head_letter_layout);

			per_home_head_follow_layout = (LinearLayout) head_view_next
					.findViewById(R.id.per_home_head_follow_layout);

			per_home_head_follow_state = (TextView) head_view_next
					.findViewById(R.id.per_home_head_follow_state);

			per_home_head_fol_state_iv = (ImageView) head_view_next
					.findViewById(R.id.per_home_head_fol_state_iv);

			mineBean = parserResult;

			ImageLoader.getInstance().displayImage(
					mineBean.getMine_CoverImage(), per_home_head_cover_iv,
					CommonUtil.MINE_COVER_OPTIONS);

			ImageLoader.getInstance().displayImage(mineBean.getMine_logoUrl(),
					per_home_head_photo, CommonUtil.OPTIONS_BIG_HEADPHOTO);

			if (!TextUtils.isEmpty(mineBean.getMine_userName())) {
				per_home_head_name.setText(mineBean.getMine_userName());
				two_or_one_btn_head_title.setText(mineBean.getMine_userName());
			} else {
				per_home_head_name.setText("");
				two_or_one_btn_head_title.setText("");
			}

			if (!TextUtils.isEmpty(mineBean.getMine_Attend())) {
				per_home_head_follow_count.setText(mineBean.getMine_Attend());
			} else {
				per_home_head_follow_count.setText("");
			}
			per_home_head_follow_count
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 跳转到关注列表
							Intent follow_intent = new Intent(getActivity(),
									FollowFansActivity.class);
							follow_intent.putExtra("follow_or_fans", "follow");
							follow_intent.putExtra("other_userId", userId);
							startActivity(follow_intent);
						}
					});

			if (!TextUtils.isEmpty(mineBean.getMine_Fans())) {
				per_home_head_fans_count.setText(mineBean.getMine_Fans());
			} else {
				per_home_head_fans_count.setText("");
			}
			per_home_head_fans_count.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 跳转到粉丝列表
					Intent follow_intent = new Intent(getActivity(),
							FollowFansActivity.class);
					follow_intent.putExtra("follow_or_fans", "fans");
					follow_intent.putExtra("other_userId", userId);
					startActivity(follow_intent);

				}
			});

			if (mineBean.getMine_Relation().equals("0")) {
				per_home_head_fol_state_iv
						.setImageResource(R.drawable.icon_per_home_cacel_follow);
				per_home_head_follow_state
						.setText(R.string.per_home_cancel_fol);
			} else if (mineBean.getMine_Relation().equals("1")) {
				per_home_head_fol_state_iv
						.setImageResource(R.drawable.icon_per_home_follow_each);
				per_home_head_follow_state.setText(R.string.per_home_fol_each);
			} else if (mineBean.getMine_Relation().equals("2")) {
				per_home_head_fol_state_iv
						.setImageResource(R.drawable.icon_per_home_add_follow);
				per_home_head_follow_state.setText(R.string.per_home_add_fol);
			}

			if (!TextUtils.isEmpty(mineBean.getMine_userGrade())) {
				CommonUtil.showVip(per_home_head_vip,
						mineBean.getMine_userGrade());
			} else {
				per_home_head_vip.setVisibility(View.GONE);
			}

			per_home_head_letter_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 发送私信
							Intent letter_intent = new Intent(getActivity(),
									PrivateLetterChat.class);
							letter_intent.putExtra("receiveuserid", userId);
							startActivity(letter_intent);

						}
					});

			per_home_head_follow_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mineBean.getMine_Relation().equals("1")
									|| mineBean.getMine_Relation().equals("0")) {
								// 调取消关注接口
								RequestParamsNet requestParamsNet = new RequestParamsNet();
								requestParamsNet
										.addQueryStringParameter(
												TootooeNetApiUrlHelper.CANCEL_FOLLOW_USERID,
												SharedPreferenceHelper
														.getLoginUserId(getActivity()));
								requestParamsNet
										.addQueryStringParameter(
												TootooeNetApiUrlHelper.CANCEL_FOLLOW_FOLLOWID,
												userId);
								CommonUtil
										.xUtilsGetSend(
												TootooeNetApiUrlHelper.CANCEL_FOLLOW_URL,
												requestParamsNet,
												new RequestCallBack<String>() {

													@Override
													public void onSuccess(
															ResponseInfo<String> responseInfo) {
														String cancelStr = new String(
																responseInfo.result);
														try {
															JSONObject jsonObj = new JSONObject(
																	cancelStr);
															if (jsonObj
																	.has("Status")) {
																String cancelStatus = jsonObj
																		.getString("Status");
																if (cancelStatus
																		.equals("1")) {
																	ComponentUtil
																			.showToast(
																					getActivity(),
																					getResources()
																							.getString(
																									R.string.per_home_cancel_fol_success));
																	per_home_head_follow_state
																			.setText(R.string.per_home_add_fol);
																	per_home_head_fol_state_iv
																			.setImageResource(R.drawable.icon_per_home_add_follow);
																	mineBean.setMine_Relation("2");

																	if (Integer
																			.parseInt(per_home_head_follow_count
																					.getText()
																					.toString()) > 0) {
																		per_home_head_follow_count
																				.setText(String
																						.valueOf(Integer
																								.parseInt(per_home_head_follow_count
																										.getText()
																										.toString()) - 1));
																	}

																} else {
																	ComponentUtil
																			.showToast(
																					getActivity(),
																					getResources()
																							.getString(
																									R.string.per_home_cancel_fol_fail));
																}

															}

														} catch (JSONException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}

													}

													@Override
													public void onFailure(
															HttpException error,
															String msg) {
														ComponentUtil
																.showToast(
																		getActivity(),
																		getResources()
																				.getString(
																						R.string.per_home_cancel_fol_fail));
													}

												});

							} else if (mineBean.getMine_Relation().equals("2")) {
								// 调添加关注接口
								RequestParamsNet requestParamsNet = new RequestParamsNet();
								requestParamsNet
										.addQueryStringParameter(
												TootooeNetApiUrlHelper.ADD_FOLLOW_USERID,
												SharedPreferenceHelper
														.getLoginUserId(getActivity()));
								requestParamsNet
										.addQueryStringParameter(
												TootooeNetApiUrlHelper.ADD_FOLLOW_FOLLOWID,
												userId);
								CommonUtil.xUtilsGetSend(
										TootooeNetApiUrlHelper.ADD_FOLLOW_URL,
										requestParamsNet,
										new RequestCallBack<String>() {

											@Override
											public void onSuccess(
													ResponseInfo<String> responseInfo) {
												String addStr = new String(
														responseInfo.result);
												try {
													JSONObject jsonObj = new JSONObject(
															addStr);
													if (jsonObj.has("Status")) {
														String addStatus = jsonObj
																.getString("Status");
														if (addStatus
																.equals("1")) {

															if (jsonObj
																	.has("Relation")) {
																String relation = jsonObj
																		.getString("Relation");
																ComponentUtil
																		.showToast(
																				getActivity(),
																				getResources()
																						.getString(
																								R.string.per_home_add_fol_success));
																if (relation
																		.equals("0")) {
																	per_home_head_follow_state
																			.setText(R.string.per_home_cancel_fol);
																	per_home_head_fol_state_iv
																			.setImageResource(R.drawable.icon_per_home_cacel_follow);
																} else if (relation
																		.equals("1")) {
																	per_home_head_follow_state
																			.setText(R.string.per_home_fol_each);
																	per_home_head_fol_state_iv
																			.setImageResource(R.drawable.icon_per_home_follow_each);
																}
																mineBean.setMine_Relation(relation);
																per_home_head_follow_count
																		.setText(String
																				.valueOf(Integer
																						.parseInt(per_home_head_follow_count
																								.getText()
																								.toString()) + 1));
															}

														} else {
															ComponentUtil
																	.showToast(
																			getActivity(),
																			getResources()
																					.getString(
																							R.string.per_home_add_fol_fail));
														}

													}

												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}
											}

											@Override
											public void onFailure(
													HttpException error,
													String msg) {

											}

										});
							}
						}
					});

			customOrStore(mineBean.getMine_businessStatus(),
					per_home_act_or_story, per_home_store_or_custom_wish,
					per_home_act_story_view,
					per_home_store_or_custom_wish_view,
					R.id.per_home_cont_layout, per_home_act_layout,
					per_home_store_wish_layout, head_view_top, head_view_next,
					per_home_change_layout);

		}
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.MINE_URL);
		// 所获取该用户信息的userId
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.MINE_USERID, userId);
		// 登录者userId
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.MINE_MY_USERID,
				SharedPreferenceHelper.getLoginUserId(getActivity()));
		return requestParamsNet;
	}

	private void customOrStore(String busStatus, TextView tv_tab1,
			TextView tv_tab2, final View v_tab1, final View v_tab2,
			final int fragment_layout_id, RelativeLayout layout_tab1,
			RelativeLayout layout_tab2, View per_home_head_top,
			View per_home_head_next, LinearLayout per_home_change_layout) {

		TextView head_view_two_act_or_story = (TextView) per_home_head_next
				.findViewById(R.id.head_view_two_act_or_story);
		TextView head_view_two_store_or_custom_wish = (TextView) per_home_head_next
				.findViewById(R.id.head_view_two_store_or_custom_wish);
		RelativeLayout head_view_two_act_layout = (RelativeLayout) per_home_head_next
				.findViewById(R.id.head_view_two_act_layout);

		RelativeLayout head_view_two_store_wish_layout = (RelativeLayout) per_home_head_next
				.findViewById(R.id.head_view_two_store_wish_layout);

		final View head_view_two_act_story_view = per_home_head_next
				.findViewById(R.id.head_view_two_act_story_view);

		final View head_view_two_store_or_custom_wish_view = per_home_head_next
				.findViewById(R.id.head_view_two_store_or_custom_wish_view);

		final FragmentManager fragmentManager = getFragmentManager();

		final RemarkStoryFragment actFragment = new RemarkStoryFragment(userId,
				per_home_head_top, per_home_head_next, per_home_change_layout);
		final HomePageFragment homeFragment = new HomePageFragment(userId,
				per_home_head_top, per_home_head_next, per_home_change_layout);
		final PersonWishFragment perWishFragment = new PersonWishFragment(
				userId, per_home_head_top, per_home_head_next,
				per_home_change_layout);
		if (busStatus.equals("0")) {
			head_view_two_act_or_story
					.setText(R.string.per_home_custom_story_hint);
			head_view_two_store_or_custom_wish
					.setText(R.string.per_home_custom_wish_hint);
			head_view_two_act_story_view.setVisibility(View.VISIBLE);
			head_view_two_store_or_custom_wish_view.setVisibility(View.GONE);
			tv_tab1.setText(R.string.per_home_custom_story_hint);
			tv_tab2.setText(R.string.per_home_custom_wish_hint);
			v_tab1.setVisibility(View.VISIBLE);
			v_tab2.setVisibility(View.GONE);

			FragmentTransaction actTransaction = fragmentManager
					.beginTransaction();
			actTransaction.add(fragment_layout_id, actFragment);
			actTransaction.add(fragment_layout_id, perWishFragment);
			actTransaction.commit();

			layout_tab1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v_tab1.setVisibility(View.VISIBLE);
					v_tab2.setVisibility(View.GONE);
					head_view_two_act_story_view.setVisibility(View.VISIBLE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.GONE);
//					FragmentTransaction actTransaction = fragmentManager
//							.beginTransaction();
//					actTransaction.replace(fragment_layout_id, actFragment);
//					actTransaction.commit();
					switchContent(perWishFragment, actFragment, fragmentManager, fragment_layout_id);
				}
			});
			// 跟layout_tab1处理事件一模一样
			head_view_two_act_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v_tab1.setVisibility(View.VISIBLE);
					v_tab2.setVisibility(View.GONE);
					head_view_two_act_story_view.setVisibility(View.VISIBLE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.GONE);
//					FragmentTransaction actTransaction = fragmentManager
//							.beginTransaction();
//					actTransaction.replace(fragment_layout_id, actFragment);
//					actTransaction.commit();
					switchContent(perWishFragment, actFragment, fragmentManager, fragment_layout_id);
				}
			});

			layout_tab2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v_tab1.setVisibility(View.GONE);
					v_tab2.setVisibility(View.VISIBLE);

					head_view_two_act_story_view.setVisibility(View.GONE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.VISIBLE);

//					FragmentTransaction wishTransaction = fragmentManager
//							.beginTransaction();
//					wishTransaction
//							.replace(fragment_layout_id, perWishFragment);
//					wishTransaction.commit();
					switchContent(actFragment, perWishFragment, fragmentManager, fragment_layout_id);

				}
			});

			head_view_two_store_wish_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							v_tab1.setVisibility(View.GONE);
							v_tab2.setVisibility(View.VISIBLE);

							head_view_two_act_story_view
									.setVisibility(View.GONE);
							head_view_two_store_or_custom_wish_view
									.setVisibility(View.VISIBLE);

//							FragmentTransaction wishTransaction = fragmentManager
//									.beginTransaction();
//							wishTransaction.replace(fragment_layout_id,
//									perWishFragment);
//							wishTransaction.commit();
							switchContent(actFragment, perWishFragment, fragmentManager, fragment_layout_id);
						}
					});

		} else if (busStatus.equals("1")) {

			tv_tab1.setText(R.string.per_home_act_hint);
			tv_tab2.setText(R.string.per_home_store_wish_hint);
			v_tab1.setVisibility(View.VISIBLE);
			v_tab2.setVisibility(View.GONE);

			head_view_two_act_or_story.setText(R.string.per_home_act_hint);
			head_view_two_store_or_custom_wish
					.setText(R.string.per_home_store_wish_hint);
			head_view_two_act_story_view.setVisibility(View.VISIBLE);
			head_view_two_store_or_custom_wish_view.setVisibility(View.GONE);

			FragmentTransaction homeTransaction = fragmentManager
					.beginTransaction();
			homeTransaction.add(fragment_layout_id, homeFragment);
			homeTransaction.add(fragment_layout_id, perWishFragment);
			homeTransaction.commit();

			layout_tab1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					v_tab1.setVisibility(View.VISIBLE);
					v_tab2.setVisibility(View.GONE);

					head_view_two_act_story_view.setVisibility(View.VISIBLE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.GONE);

//					FragmentTransaction homeTransaction = fragmentManager
//							.beginTransaction();
//					homeTransaction.replace(fragment_layout_id, homeFragment);
//					homeTransaction.commit();
					switchContent(perWishFragment, homeFragment, fragmentManager, fragment_layout_id);

				}
			});

			head_view_two_act_layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v_tab1.setVisibility(View.VISIBLE);
					v_tab2.setVisibility(View.GONE);

					head_view_two_act_story_view.setVisibility(View.VISIBLE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.GONE);

//					FragmentTransaction homeTransaction = fragmentManager
//							.beginTransaction();
//					homeTransaction.replace(fragment_layout_id, homeFragment);
//					homeTransaction.commit();
					switchContent(perWishFragment, homeFragment, fragmentManager, fragment_layout_id);
				}
			});

			layout_tab2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					v_tab1.setVisibility(View.GONE);
					v_tab2.setVisibility(View.VISIBLE);
					head_view_two_act_story_view.setVisibility(View.GONE);
					head_view_two_store_or_custom_wish_view
							.setVisibility(View.VISIBLE);

//					FragmentTransaction wishTransaction = fragmentManager
//							.beginTransaction();
//					wishTransaction
//							.replace(fragment_layout_id, perWishFragment);
//					wishTransaction.commit();
					switchContent(homeFragment, perWishFragment, fragmentManager, fragment_layout_id);

				}
			});

			head_view_two_store_wish_layout
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							v_tab1.setVisibility(View.GONE);
							v_tab2.setVisibility(View.VISIBLE);
							head_view_two_act_story_view
									.setVisibility(View.GONE);
							head_view_two_store_or_custom_wish_view
									.setVisibility(View.VISIBLE);
//
//							FragmentTransaction wishTransaction = fragmentManager
//									.beginTransaction();
//							wishTransaction.replace(fragment_layout_id,
//									perWishFragment);
//							wishTransaction.commit();
							switchContent(homeFragment, perWishFragment, fragmentManager, fragment_layout_id);

						}
					});

		}

	}

	public void switchContent(Fragment from, Fragment to,
			FragmentManager mFragmentManager, int fragment_id) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (!to.isAdded()) { // 先判断是否被add过
			transaction.hide(from).add(fragment_id, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
		} else {
			transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
		}
	}
}
