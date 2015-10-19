package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.CreateActiveFirstStepActivity;
import com.ninetowns.tootooplus.activity.HomeActivity;
import com.ninetowns.tootooplus.activity.MyActivityApplyActivity;
import com.ninetowns.tootooplus.activity.PersonalHomeActivity;
import com.ninetowns.tootooplus.activity.SearchActivity;
import com.ninetowns.tootooplus.adapter.AuditAdapter;
import com.ninetowns.tootooplus.adapter.HomePageAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.fragment.HistoryFragmentDialog.OnSearchListener;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.HomePageParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/**
 * 
 * @ClassName: PendingFragment
 * @Description: 待审核
 * @author wuyulong
 * @date 2015-3-13 上午10:18:16
 * 
 */
public class PendingFragment extends
		PageListFragment<ListView, List<HomePageBean>, HomePageParser>
		implements OnSearchListener {
	private View mHomePageFragmentView;
	private ListView mHomePageRefreshListView;
	private int totalPage;// 总页数
	@ViewInject(R.id.ibtn_left)
	private ImageButton mIbtnSelect;// 筛选
	@ViewInject(R.id.ibtn_right)
	private ImageButton mIbtnCreateStory;// 创建故事心愿
	@ViewInject(R.id.tv_title)
	private TextView mTvTitle;
	private List<HomePageBean> listHomePageBean = new ArrayList<HomePageBean>();
	private String userid;
	@ViewInject(R.id.title_in)
	private LinearLayout mInTitle;
	@ViewInject(R.id.ll_middle)
	private LinearLayout mMiddleSearch;
	private boolean isMyActivityApply;
	private boolean isPersonalHomeActivity;
	private boolean isHomeActivity;
	private String status = "1";// 1待审核2通过-1未通过
	private HomePageAdapter homePageAdatper;
	private AuditAdapter ediPageAdatper;
	private View per_home_head_top;
	private View per_home_head_next;

	private LinearLayout per_home_change_layout;
	private String searchActName;

	public PendingFragment(String userid, View per_home_head_top,
			View per_home_head_next, LinearLayout per_home_change_layout) {
		this.userid = userid;
		this.per_home_head_top = per_home_head_top;

		this.per_home_head_next = per_home_head_next;

		this.per_home_change_layout = per_home_change_layout;
	}

	public PendingFragment() {
	}

	@OnClick(R.id.ll_middle)
	public void setOnClickSearch(View v) {
		// skipToSearchWish();
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

	@SuppressLint("NewApi")
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mHomePageFragmentView = inflater.inflate(R.layout.home_page_fragment,
				null);

		isMyActivityApply = getActivity() instanceof MyActivityApplyActivity;
		isPersonalHomeActivity = getActivity() instanceof PersonalHomeActivity;
		isHomeActivity = getActivity() instanceof HomeActivity;
		ViewUtils.inject(this, mHomePageFragmentView); // 注入view和事件
		if (isMyActivityApply) {
			MyActivityApplyActivity myActivityApply = (MyActivityApplyActivity) getActivity();
			mMiddleSearch.setVisibility(View.GONE);
			mInTitle.setVisibility(View.GONE);
		} else if (isPersonalHomeActivity) {
			mMiddleSearch.setVisibility(View.GONE);
			mInTitle.setVisibility(View.GONE);
		} else {
			
			if (Build.VERSION.SDK_INT >= 16) {

				mMiddleSearch.setBackground(null);

			} else {

				mMiddleSearch.setBackgroundDrawable(null);
			}
			
			
			mTvTitle.setText("白吃活动");
		}
		mIbtnSelect.setVisibility(View.INVISIBLE);
		return mHomePageFragmentView;
	}

	@OnClick(R.id.ibtn_right)
	public void createActivity(View view) {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.VERIFY_PERMISSIONS_USERID,
				SharedPreferenceHelper.getLoginUserId(getActivity()));
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.VERIFY_PERMISSIONS_TYPE, "1");
		CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.VERIFY_PERMISSIONS_URL,
				requestParamsNet, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String createStr = new String(responseInfo.result);
						try {
							JSONObject createObj = new JSONObject(createStr);
							if (createObj.has("Status")) {
								String status = createObj.getString("Status");
								if (status.equals("1")) {
									Intent intent = new Intent(getActivity(),
											CreateActiveFirstStepActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
								} else if (status.equals("4401")) {
									ComponentUtil
											.showToast(
													getActivity(),
													getResources()
															.getString(
																	R.string.create_act_verify_no_through));
								} else if (status.equals("4402")) {
									ComponentUtil
											.showToast(
													getActivity(),
													getResources()
															.getString(
																	R.string.create_act_verify_no_wish));
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

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
		requestPar
				.setmStrHttpApi(TootooeNetApiUrlHelper.ACTIVITYS_MY_FREE_ACTIVITYSE);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID,
				myUserid);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));// 默认每页6条
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.STATUS,
				status);
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
		listHomePageBean.addAll(parserResult);
		if (listHomePageBean.size() > 0) {
			
			ediPageAdatper = new AuditAdapter(getActivity(),
					listHomePageBean);
			mHomePageRefreshListView.setAdapter(ediPageAdatper);
			ediPageAdatper.notifyDataSetChanged();
			if (super.currentpage != 1) {
				mHomePageRefreshListView.setSelection(this.listHomePageBean
						.size() / 2 + 1);
			}

		} else {
			ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
					"没有数据");
		}

	}

	@Override
	public void OnSearchListener(String name) {
		searchActName = name;
		super.onLoadData(true, true, true);

	}

}