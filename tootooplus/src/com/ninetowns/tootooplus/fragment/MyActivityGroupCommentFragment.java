package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.BigOrSmallEateAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.HomePageParser;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
/**
 * 
* @ClassName: MyActivityGroupCommentFragment 
* @Description: 组团线上、组团线下基类
* @author wuyulong
* @date 2015-4-16 下午5:08:43 
*
 */
public abstract class MyActivityGroupCommentFragment extends
		PageListFragment<ListView,List<HomePageBean>, HomePageParser> implements OnItemClickListener{

	private int totalPage;
	private ListView mWishRefreshListView;
	private View mFreeFragmentView;
	private List<HomePageBean> parserResult=new ArrayList<HomePageBean>();
	private BigOrSmallEateAdapter myActAdapter;

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView refresh = (RefreshableListView) mFreeFragmentView
				.findViewById(R.id.refresh_group_comment);
		refresh.setRefreshing(true);
		mWishRefreshListView = refresh.getRefreshableView();
		refresh.setMode(Mode.BOTH);
		PauseOnScrollListener listener = new PauseOnScrollListener(
				ImageLoader.getInstance(), true, true);
		refresh.setOnScrollListener(listener);
		mWishRefreshListView.setFastScrollEnabled(false);
		mWishRefreshListView.setOnItemClickListener(this);
		return refresh;
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mFreeFragmentView=inflater.inflate(R.layout.my_base_activity_free_comment_fragment, null);
		return mFreeFragmentView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onLoadData(true, true, true);
	}

	@Override
	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public HomePageParser setParser(String str) {
		HomePageParser freeCommentParser = new HomePageParser(str);
		totalPage = freeCommentParser.getTotalPage();
		return freeCommentParser;
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamNet = new RequestParamsNet();
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		requestParamNet.setmStrHttpApi(TootooeNetApiUrlHelper.MY_ACT_FREE_COMMENT);
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ID,
				userid);
		requestParamNet.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		requestParamNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));//
		setDifferentParam(requestParamNet);
		return requestParamNet;
	}

	/**
	 * 
	 * @Title: setDiffientParam
	 * @Description: 设置不同的参数，请求不同的数据
	 * @param
	 * @return
	 * @throws
	 */
	public abstract void setDifferentParam(RequestParamsNet requestParamNet);

	@Override
	public void getPageListParserResult(List<HomePageBean> parserData) {

		if (super.currentpage == 1) {
			this.parserResult.clear();
		}
		if(parserData!=null){
			int moreSize=parserData.size();
			parserResult.addAll(parserData);
			
			if (parserResult.size() > 0) {
				myActAdapter = new BigOrSmallEateAdapter(getActivity(),
						parserResult);
				mWishRefreshListView.setAdapter(myActAdapter);
				
				
				if (super.currentpage != 1) {
					mWishRefreshListView.setSelection(this.parserResult.size() -moreSize + 1);
				}
				myActAdapter.notifyDataSetChanged();
			} else {
				ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
						"没有要筛选的数据");
			}
		}

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(parserResult!=null&&parserResult.size()>0){
			onItemClickToSkip(view, position, parserResult);
		}else{
			LogUtil.error("数据异常", "parserResult=null");
		}
		
	}
	/**
	 * 
	* @Title: onItemClickToSkip 
	* @Description: 点击条目跳转数据
	* @param  
	* @return   
	* @throws
	 */
	public abstract void onItemClickToSkip(View view, int position,List<HomePageBean> parserData);
}