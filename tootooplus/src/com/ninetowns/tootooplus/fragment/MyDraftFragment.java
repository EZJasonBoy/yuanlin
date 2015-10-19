package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.WishDetailActivity;
import com.ninetowns.tootooplus.adapter.DraftAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.WishBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.MyWishParser;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * 
 * @ClassName: MyDraftFragment
 * @Description: 我的草稿
 * @author wuyulong
 * @date 2015-1-28 上午11:33:52
 * 
 */
public class MyDraftFragment extends
		PageListFragment<ListView, List<WishBean>, MyWishParser> implements
		View.OnClickListener ,OnItemClickListener{
	private View mWishFragmentView;
	private ListView mWishRefreshListView;// 刷新的listView
	private int totalPage;// 总页数
	private List<WishBean> wishList = new ArrayList<WishBean>();// 当前数据列表集合
	@ViewInject(R.id.ibtn_left)
	private ImageButton mIbtnSelect;// 返回
	@ViewInject(R.id.ibtn_right)
	private ImageButton mIBtnDraft;// 草稿
	@ViewInject(R.id.tv_title)
	private TextView mTvTitle;
	@ViewInject(R.id.rl_arcmenu)
	private RelativeLayout mRLarcMenu;

	@SuppressLint("InflateParams")
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mWishFragmentView = inflater.inflate(R.layout.my_wish_fragment, null);
		ViewUtils.inject(this, mWishFragmentView); // 注入view和事件
		mRLarcMenu.setVisibility(View.GONE);
		mTvTitle.setText("草稿");
		mIBtnDraft.setVisibility(View.GONE);
		setViewListener();
		return mWishFragmentView;
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
		mIBtnDraft.setOnClickListener(this);
		mIbtnSelect.setOnClickListener(this);
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView refresh = (RefreshableListView) mWishFragmentView
				.findViewById(R.id.refresh_home_page_list);
		mWishRefreshListView = refresh.getRefreshableView();
		refresh.setMode(Mode.DISABLED);
		PauseOnScrollListener listener = new PauseOnScrollListener(
				ImageLoader.getInstance(), true, true);
		refresh.setOnScrollListener(listener);
		mWishRefreshListView.setFastScrollEnabled(false);
		mWishRefreshListView.setOnItemClickListener(this);
		return refresh;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		loadData();
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 
	 * @Title: loadData
	 * @Description: 加载数据
	 * @param
	 * @return
	 * @throws
	 */
	public void loadData() {
		super.onLoadData(true, true, false);
	}

	@Override
	public MyWishParser setParser(String str) {
		MyWishParser wishParser = new MyWishParser(str);
		totalPage = wishParser.getTotalPage();
		return wishParser;

	}

	@Override
	public RequestParamsNet getApiParmars() {
		// CategoryId：分类筛选
		RequestParamsNet requestPar = new RequestParamsNet();
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		requestPar.setmStrHttpApi(TootooeNetApiUrlHelper.MY_WISH_LIST);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID,
				userid);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.TYPE,"4");//草稿
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));// 默认每页6条

		return requestPar;
	}

	@Override
	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public void getPageListParserResult(List<WishBean> parserResult) {
		if (super.currentpage == 1) {
			this.wishList.clear();
		}
		if(parserResult!=null){

			wishList.addAll(parserResult);
			if (wishList.size() > 0) {
				DraftAdapter wishAdatper = new DraftAdapter(getActivity(),wishList);
				mWishRefreshListView.setAdapter(wishAdatper);
				if (super.currentpage != 1) {
					mWishRefreshListView.setSelection(this.wishList.size() -parserResult.size() + 1);
				}
				wishAdatper.notifyDataSetChanged();
			} else {
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
						"没有数据");
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibtn_left:// 返回
			getActivity().finish();

			break;
		case R.id.ibtn_right:// 草稿

			break;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position!=-1){
			WishBean selectionItem = wishList.get(position-1);
			Intent intentToDetail=new Intent(getActivity(),WishDetailActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString(ConstantsTooTooEHelper.USERID, selectionItem.getUserId());
			bundle.putString(ConstantsTooTooEHelper.STORYID, selectionItem.getStoryId());
			intentToDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intentToDetail);
		}
		
	}


	





}