package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.FaceToFaceGroupEnterActivity;
import com.ninetowns.tootooplus.activity.HomeActivity;
import com.ninetowns.tootooplus.activity.MyActivityApplyActivity;
import com.ninetowns.tootooplus.activity.MyCollectionActivity;
import com.ninetowns.tootooplus.activity.MyFreeGroupActivity;
import com.ninetowns.tootooplus.activity.PersonalHomeActivity;
import com.ninetowns.tootooplus.activity.SearchActivity;
import com.ninetowns.tootooplus.adapter.AuditAdapter;
import com.ninetowns.tootooplus.adapter.EditextTepAdapter;
import com.ninetowns.tootooplus.adapter.HomePageAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.bean.NoticeSuccess;
import com.ninetowns.tootooplus.fragment.BaseShaiXuanDialog.OnSelectedListener;
import com.ninetowns.tootooplus.fragment.FirstGuideFaceToFace.OnFaceToFaceDialogStatus;
import com.ninetowns.tootooplus.fragment.HistoryFragmentDialog.OnSearchListener;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.HomePageParser;
import com.ninetowns.tootooplus.parser.MyNoticeParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/**
 * 
 * @ClassName: HomePageFragment
 * @Description: 活动页显示
 * @author wuyulong
 * @date 2015-3-13 上午10:18:16
 * 
 */
@SuppressLint("ValidFragment")
public class HomePageFragment extends
		PageListFragment<ListView, List<HomePageBean>, HomePageParser>
		implements OnSearchListener, INetConstanst, OnSelectedListener,OnFaceToFaceDialogStatus {
	private View mHomePageFragmentView;
	private ListView mHomePageRefreshListView;
	private int totalPage;// 总页数
	@ViewInject(R.id.ibtn_left)
	private ImageButton mIbtnSelect;// 筛选
	@ViewInject(R.id.ibtn_right)
	private ImageButton mIbtnCreateStory;// 创建故事心愿
	@ViewInject(R.id.tv_title)
	private CheckedTextView mTvTitle;
	private List<HomePageBean> listHomePageBean = new ArrayList<HomePageBean>();
	private String userid;
	@ViewInject(R.id.title_in)
	private LinearLayout mInTitle;
	@ViewInject(R.id.ll_middle)
	private LinearLayout mMiddleSearch;
	private boolean isMyActivityApply;
	private boolean isPersonalHomeActivity;
	private boolean isHomeActivity;
	@ViewInject(R.id.ct_notice)
	private CheckedTextView mCTTextView;
	@ViewInject(R.id.rl_top)
	private RelativeLayout mRLTop;
	/**
	 * @Fields isMyCollectionActivity : 是否 为 我的收藏页面
	 */
	private boolean isMyCollectionActivity;
	private String status = "2";// 1待审核2通过-1未通过
	private HomePageAdapter homePageAdatper;
	private EditextTepAdapter ediPageAdatper;
	private View per_home_head_top;
	private View per_home_head_next;

	private LinearLayout per_home_change_layout;
	// private String oldStatus = "";
	private String searchActName;
	private ActivityShaiXuanDialog fragment;
	private String viewType = "0";
	private NoticeSuccessDialog fragmentNotice;

	public HomePageFragment(String userid, View per_home_head_top,
			View per_home_head_next, LinearLayout per_home_change_layout) {
		this.userid = userid;
		this.per_home_head_top = per_home_head_top;

		this.per_home_head_next = per_home_head_next;

		this.per_home_change_layout = per_home_change_layout;
	}

	/**
	 * 
	 * @Title: getData
	 * @Description: 获取数据
	 * @param
	 * @return
	 * @throws
	 */
	private void getData() {

		RequestParamsNet requestParamsNet = new RequestParamsNet();
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USERID,
				userid);
		CommonUtil.xUtilsPostSend(
				TootooeNetApiUrlHelper.ACTIVITY_SUCCESS_NOTIFY,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String jsonStr = new String(responseInfo.result);
						MyNoticeParser mNoticePar = new MyNoticeParser(jsonStr);
						List<NoticeSuccess> listResult = mNoticePar
								.getParseResult(jsonStr);
						if (listResult != null && listResult.size() > 0) {
							mRLTop.setVisibility(View.VISIBLE);
							// showNoticeDialog(listResult);
						} else {
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ComponentUtil.showToast(
								TootooPlusApplication.getAppContext(),
								getResources()
										.getString(
												R.string.errcode_network_response_timeout));
					}
				});
	}

	public HomePageFragment() {
	}

	@OnClick(R.id.ll_middle)
	public void setOnClickSearch(View v) {
		if (isHomeActivity) {
			skipToSearchWish();
		}

	}

	@OnClick(R.id.ibtn_left)
	public void setSelect(View v) {
		if (isHomeActivity) {
			Intent intent =new Intent(getActivity(),FaceToFaceGroupEnterActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			//
//			showSelectedDiallog();//隐藏过滤
		}

	}

	/**
	 * 
	 * @Title: showNoticeDialog
	 * @Description: 显示提示
	 * @param
	 * @return
	 * @throws
	 */
	private void showNoticeDialog(List<NoticeSuccess> listNotice) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (fragmentNotice != null) {
			if (fragmentNotice.isVisible()) {
				fragmentNotice.dismiss();
			} else {
				fragmentNotice.show(fragmentManager, "dialog");
			}

		} else {
			fragmentNotice = new NoticeSuccessDialog(listNotice);
			if (fragmentManager != null) {
				// 屏幕较小，以全屏形式显示
				// 指定一个过渡动画
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(null);
				transaction.attach(fragmentNotice);
				fragmentNotice.show(fragmentManager, "dialog");
				transaction.commitAllowingStateLoss();
			}

		}
	}

	/**
	 * @Title: showSelectedDiallog
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	private void showSelectedDiallog() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (fragment != null) {
			if (fragment.isVisible()) {
				fragment.dismiss();
			} else {
				fragment.show(fragmentManager, "dialog");
			}

		} else {
			fragment = new ActivityShaiXuanDialog();
			fragment.setOnSelectedListener(this);
			if (fragmentManager != null) {
				// 屏幕较小，以全屏形式显示

				// 指定一个过渡动画
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.addToBackStack(null);
				transaction.attach(fragment);
				fragment.show(fragmentManager, "dialog");
				transaction.commitAllowingStateLoss();
			}

		}
	}

	/**
	 * 
	 * @Title: showHisToryDialog
	 * @Description: 展示dialog
	 * @param
	 * @return
	 * @throws
	 */
	private void skipToSearchWish() {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchType", "isActivity");
		startActivity(intent);

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mHomePageFragmentView = inflater.inflate(R.layout.home_page_fragment,
				null);
		isMyActivityApply = getActivity() instanceof MyActivityApplyActivity;
		isPersonalHomeActivity = getActivity() instanceof PersonalHomeActivity;
		isHomeActivity = getActivity() instanceof HomeActivity;
		isMyCollectionActivity = getActivity() instanceof MyCollectionActivity;
		ViewUtils.inject(this, mHomePageFragmentView); // 注入view和事件

		if (isMyActivityApply) {
			MyActivityApplyActivity myActivityApply = (MyActivityApplyActivity) getActivity();
			status = myActivityApply.status;
			// oldStatus = this.status;// 把当前的记录起来
			mMiddleSearch.setVisibility(View.GONE);
			mInTitle.setVisibility(View.GONE);
			mIbtnSelect.setVisibility(View.INVISIBLE);
		} else if (isPersonalHomeActivity) {
			mMiddleSearch.setVisibility(View.GONE);
			mInTitle.setVisibility(View.GONE);
			mIbtnSelect.setVisibility(View.INVISIBLE);
		} else if (isMyCollectionActivity) {
			// mMiddleSearch.setVisibility(View.GONE);
			mTvTitle.setText("我的收藏");
			UIUtils.setViewVisible(mIbtnSelect);
			UIUtils.setViewGone(mIbtnCreateStory);
			mIbtnSelect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();

				}
			});
		} else {
			getData();
			mIbtnCreateStory.setImageResource(R.drawable.icon_my_group);
			mIbtnSelect.setVisibility(View.VISIBLE);// 把筛选按钮隐藏掉了
			mIbtnSelect.setImageResource(R.drawable.icon_facetoface_group);
			if(isAdded()){
				if (Build.VERSION.SDK_INT >= 16) {

					mMiddleSearch.setBackground(getResources().getDrawable(
							R.drawable.round_gray_backgroud));

				} else {

					mMiddleSearch.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.round_gray_backgroud));
				}
			}
				
		

			mTvTitle.setTextSize(UIUtils.px2Sp(TootooPlusApplication
					.getAppContext(),
					getResources().getDimension(R.dimen.search_text)));
			mTvTitle.setText("搜索");
			mTvTitle.setTextColor(getResources().getColor(
					R.color.btn_gray_color));
			mTvTitle.setCompoundDrawablesWithIntrinsicBounds(getResources()
					.getDrawable(R.drawable.icon_search), null, null, null);
			boolean isFirst = SharedPreferenceHelper.getFirstGuideFaceToFace(getActivity());
			if(isFirst){//如果第一次
				FirstGuideFaceToFace faceToFace = (FirstGuideFaceToFace) CommonUtil.showFirstGuideDialog(getActivity(), ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE);
				faceToFace.setOnDialogStatus(this);
			}
		}
		return mHomePageFragmentView;
	}

	@OnClick(R.id.rl_top)
	public void setTop(View v) {
		Intent free_intent = new Intent(getActivity(),
				MyFreeGroupActivity.class);
		free_intent.putExtra("group_tab",
				String.valueOf(ConstantsTooTooEHelper.TAB_THREE));
		free_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(free_intent);
		mRLTop.setVisibility(View.GONE);
	}

	@OnClick(R.id.ibtn_right)
	public void createActivity(View view) {
		// 我的白吃团
		Intent free_intent = new Intent(getActivity(),
				MyFreeGroupActivity.class);
		startActivity(free_intent);
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView refresh = (RefreshableListView) mHomePageFragmentView
				.findViewById(R.id.refresh_home_page_list);
		mHomePageRefreshListView = refresh.getRefreshableView();
		refresh.setMode(Mode.BOTH);
		mHomePageRefreshListView.setFastScrollEnabled(false);
		if (isPersonalHomeActivity) {
			mHomePageRefreshListView.addHeaderView(per_home_head_top);
			mHomePageRefreshListView.addHeaderView(per_home_head_next);

			mHomePageRefreshListView
					.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view,
								int scrollState) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onScroll(AbsListView view,
								int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {
							if (firstVisibleItem > 1) {
								per_home_change_layout
										.setVisibility(View.VISIBLE);
							} else if (firstVisibleItem < 4) {
								per_home_change_layout.setVisibility(View.GONE);
							}

						}
					});
		}
		return refresh;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onLoadData(true, true, false);
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public HomePageParser setParser(String str) {
		HomePageParser homePageParser = new HomePageParser(str);
		totalPage = homePageParser.getTotalPage();
		return homePageParser;

	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestPar = new RequestParamsNet();
		String myUserid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());

		if (isMyCollectionActivity) {
			requestPar.setmStrHttpApi(MY_COLLECTION_API);
			requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID,
					myUserid);
			requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
					String.valueOf(currentpage));
		} else {

			if (isPersonalHomeActivity) {
				requestPar
						.setmStrHttpApi(TootooeNetApiUrlHelper.INDEX_MY_FREE_ACTIVITY);
			} else {
				requestPar
						.setmStrHttpApi(TootooeNetApiUrlHelper.ACTIVITYS_MY_FREE_ACTIVITYSE);
			}
			if (isPersonalHomeActivity) {
				requestPar.addQueryStringParameter(
						TootooeNetApiUrlHelper.USER_ID, userid);
			} else {
				requestPar.addQueryStringParameter(
						TootooeNetApiUrlHelper.USER_ID, myUserid);
			}
			requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
					String.valueOf(currentpage));
			if (!TextUtils.isEmpty(searchActName)) {
				requestPar.addQueryStringParameter(
						TootooeNetApiUrlHelper.ACTIVITY_NAME, searchActName);// 分类筛选id
				searchActName = null;// 重置null
			}
			requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE,
					viewType);// 分类筛选id
			requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS,
					status);
		}

		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));
		// 审核状态
		// 1待审核2审核通过-1审核不通过，这里传值2
		// (必填)
		return requestPar;
	}

	@Override
	public int getTotalPage() {
		return totalPage;
	}



	@Override
	public void getPageListParserResult(List<HomePageBean> parserResult) {

		if (super.currentpage == 1) {
			this.listHomePageBean.clear();
			if (parserResult == null || parserResult.size() == 0) {
				mHomePageRefreshListView.setAdapter(null);
				return;
			}
		}
		if (parserResult != null&&parserResult.size()>0) {
			int moreSize = parserResult.size();
			listHomePageBean.addAll(parserResult);
			if (listHomePageBean.size() > 0) {
				if (status.equals("-1")) {// 审核未通过
					ediPageAdatper = new EditextTepAdapter(getActivity(),
							listHomePageBean);
					mHomePageRefreshListView.setAdapter(ediPageAdatper);
					ediPageAdatper.notifyDataSetChanged();
				} else if (status.equals("1")) {// 待审核
					homePageAdatper = new AuditAdapter(getActivity(),
							listHomePageBean);
					mHomePageRefreshListView.setAdapter(homePageAdatper);
					homePageAdatper.notifyDataSetChanged();
				} else {
					// 审核通过
					homePageAdatper = new HomePageAdapter(getActivity(),
							listHomePageBean);
					mHomePageRefreshListView.setAdapter(homePageAdatper);
					homePageAdatper.notifyDataSetChanged();

				}

				if (super.currentpage != 1) {
					mHomePageRefreshListView.setSelection(listHomePageBean
							.size() - moreSize + 1);
				}

			} else {
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
						"没有数据");
			}
		}

	}

	@Override
	public void OnSearchListener(String name) {
		searchActName = name;
		super.onLoadData(true, true, true);

	}

	@Override
	public void OnSelectedListenerPar(String type) {
		viewType = type;
		currentpage = 1;
		super.onLoadData(true, true, true);

	}

	@Override
	public void onDialogStatusListener(boolean isDismiss) {
		if(isDismiss){
			boolean isFirst = SharedPreferenceHelper.getFirstGuideLookGroup(getActivity());
			if(isFirst){//如果第一次
				CommonUtil.showFirstGuideDialog(getActivity(), ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GROUPINFO);
			}
		}
		
		
	}

}
