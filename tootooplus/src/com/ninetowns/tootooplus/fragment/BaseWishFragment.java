package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.WishAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.WishBean;
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
 * @ClassName: BaseWishFragment
 * @Description: 心愿基类（我的心愿，通过、未通过、我要白吃、草稿）
 * @author wuyulong
 * @date 2015-1-28 上午11:33:52
 * 
 */
public abstract class BaseWishFragment extends
		PageListFragment<ListView, List<WishBean>, MyWishParser> implements
		OnItemClickListener {
	private View mWishFragmentView;
	private ListView mWishRefreshListView;// 刷新的listView
	private int totalPage;// 总页数
	private List<WishBean> wishList = new ArrayList<WishBean>();// 当前数据列表集合

	@SuppressLint("InflateParams")
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mWishFragmentView = inflater.inflate(R.layout.base_wish_fragment, null);
		ViewUtils.inject(this, mWishFragmentView); // 注入view和事件
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
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));// 默认每页6条
		setDifferentParam(requestPar);
		return requestPar;
	}

	@Override
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 
	 * @Title: setDifferentParam
	 * @Description: 设置不同的参数
	 * @param
	 * @return
	 * @throws
	 */
	protected abstract void setDifferentParam(RequestParamsNet requestParamNet);

	/**
	 * 
	 * @Title: onItemClickToSkip
	 * @Description: 条目点击事件
	 * @param
	 * @return
	 * @throws
	 */
	protected abstract void onItemClickToSkip(View view, int position,
			List<WishBean> parserData);

	@Override
	public void getPageListParserResult(List<WishBean> parserResult) {
		if (super.currentpage == 1) {
			this.wishList.clear();
		}
		if(parserResult!=null){

			wishList.addAll(parserResult);
			int moreSize=parserResult.size();
			if (wishList.size() > 0) {
				WishAdapter wishAdatper = new WishAdapter(getActivity(), wishList);
				mWishRefreshListView.setAdapter(wishAdatper);
				if (super.currentpage != 1) {
					mWishRefreshListView.setSelection(this.wishList.size()-moreSize + 1);
				}
				wishAdatper.notifyDataSetChanged();
			} else {
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
						"没有数据");
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if(wishList!=null&&wishList.size()>0){
			onItemClickToSkip(view, position, wishList);
		}else{
			LogUtil.error("数据异常", "parserResult=null");
		}
	/*	if (position != -1) {
			WishBean selectionItem = wishList.get(position - 1);
			Intent intentToDetail = new Intent(getActivity(),
					WishDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(ConstantsTooTooEHelper.USERID,
					selectionItem.getUserId());
			bundle.putString(ConstantsTooTooEHelper.STORYID,
					selectionItem.getStoryId());
			intentToDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intentToDetail);
		}
*/
	}

}
