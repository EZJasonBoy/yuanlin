package com.ninetowns.tootoopluse.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.AuditAdapter;
import com.ninetowns.tootoopluse.adapter.EditextTepAdapter;
import com.ninetowns.tootoopluse.adapter.HomePageAdapter;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.ActivityHistoryBean;
import com.ninetowns.tootoopluse.bean.HomePageBean;
import com.ninetowns.tootoopluse.fragment.HistoryFragmentDialog.OnSearchListener;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.HomePageParser;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/**
 * 
 * @ClassName: SearchHomePageFragment
 * @Description: 搜索过滤活动
 * @author wuyulong
 * @date 2015-3-13 上午10:18:16
 * 
 */
public class SearchHomePageFragment extends
		PageListFragment<ListView, List<HomePageBean>, HomePageParser>
		implements OnSearchListener, OnClickListener {
	private View mHomePageFragmentView;
	private ListView mHomePageRefreshListView;
	private int totalPage;// 总页数

	private List<HomePageBean> listHomePageBean = new ArrayList<HomePageBean>();
	private String userid;
	@ViewInject(R.id.ll_se_right)
	private LinearLayout mLLSearch;// 搜索
	@ViewInject(R.id.et_search)
	private EditText mEditext;// 搜索输入框
	@ViewInject(R.id.ibtn_se_left)
	private ImageButton mLLBack;// 返回
	private String status = "2";// 1待审核2通过-1未通过
	private HomePageAdapter homePageAdatper;
	private EditextTepAdapter ediPageAdatper;
	@ViewInject(R.id.fl_history)
	private FrameLayout mFLHistory;
	private LinearLayout per_home_change_layout;
	private String oldStatus = "";
	private String searchActName;
	private HistoryFragmentDialog fragment;
	private RefreshableListView refresh;

	public SearchHomePageFragment(String userid, View per_home_head_top,
			View per_home_head_next, LinearLayout per_home_change_layout) {
		this.userid = userid;

		this.per_home_change_layout = per_home_change_layout;
	}

	public SearchHomePageFragment() {
	}

	/**
	 * 
	 * @Title: setViewListener
	 * @Description: 注册点击事件
	 * @param
	 * @return void
	 * @throws
	 */
	private void setViewListener() {
		mLLSearch.setOnClickListener(this);
		mLLBack.setOnClickListener(this);
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mHomePageFragmentView = inflater.inflate(
				R.layout.search_home_page_fragment, null);
		ViewUtils.inject(this, mHomePageFragmentView); // 注入view和事件
		setViewListener();
		return mHomePageFragmentView;
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		refresh = (RefreshableListView) mHomePageFragmentView
				.findViewById(R.id.refresh_home_page_list);
		refresh.setRefreshing(false);

		mHomePageRefreshListView = refresh.getRefreshableView();
		refresh.setMode(Mode.BOTH);
		mHomePageRefreshListView.setFastScrollEnabled(false);
		return refresh;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// super.onLoadData(true, true, false);
		showHisToryDialog();
		super.onActivityCreated(savedInstanceState);
	}

	private void showHisToryDialog() {

		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		fragment = new HistoryFragmentDialog("isActivity", mFLHistory, refresh);
		fragment.setOnSearchListener(this);
		if (fragmentManager != null) {
			// 屏幕较小，以全屏形式显示
			// 指定一个过渡动画
			transaction.replace(R.id.fl_history, fragment);
			transaction.commitAllowingStateLoss();
		}

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
				.getLoginUserId(TootooPlusEApplication.getAppContext());
		requestPar
				.setmStrHttpApi(TootooeNetApiUrlHelper.ACTIVITYS_MY_FREE_ACTIVITYSE);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID,
				myUserid);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		if (!TextUtils.isEmpty(searchActName)) {
			requestPar.addQueryStringParameter(
					TootooeNetApiUrlHelper.ACTIVITY_NAME, searchActName);// 分类筛选id
		}
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
				mHomePageRefreshListView.setSelection(this.listHomePageBean
						.size() / 2 + 1);
			}

		} else {
			ComponentUtil.showToast(TootooPlusEApplication.getAppContext(),
					"没有数据");
		}

	}

	@Override
	public void OnSearchListener(String name) {
		searchActName = name;
		if(null!=fragment){
			mFLHistory.setVisibility(View.GONE);
			refresh.setVisibility(View.VISIBLE);
		}else{
			mFLHistory.setVisibility(View.VISIBLE);
			refresh.setVisibility(View.GONE);
		}
		super.onLoadData(true, true, true);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ll_se_right:
			// 创建一个数据库
			createSearch();
			break;
		case R.id.ibtn_se_left:
			getActivity().finish();
			break;

		default:
			break;
		}

	}

	/**
	 * @Title: createSearch
	 * @Description: 搜索 、存储、
	 * @param
	 * @return
	 * @throws
	 */
	private void createSearch() {
		String strName = mEditext.getText().toString();
		searchHistory(strName);

	}

	private void searchHistory(String strName) {
		if (!TextUtils.isEmpty(strName)) {
			DbUtils dbUtils = DbUtils.create(getActivity());

			ActivityHistoryBean history = new ActivityHistoryBean();
			history.setHistoryName(strName);
			try {
				ActivityHistoryBean searHistoryBean = dbUtils.findFirst(Selector.from(
						ActivityHistoryBean.class).where("historyName", "=", strName));
				if (searHistoryBean != null) {
					dbUtils.delete(searHistoryBean);
					dbUtils.save(history);
				} else {
					dbUtils.save(history);
				}

			} catch (DbException e) {
				e.printStackTrace();
			}
			OnSearchListener(strName);

		} else {
			ComponentUtil.showToast(getActivity(), "请输入搜索内容");
		}
	}

}
