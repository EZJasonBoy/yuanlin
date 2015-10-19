package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.PriorityCodeBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.parser.PriorityCodeParser;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;
/** 
* @Description: 基类优先码
* @author zhou
* @date 2015-4-22 下午4:12:30 
*  
*/
public abstract class  BasePriorityCodeFragment extends PageListFragment<ListView, List<PriorityCodeBean>, PriorityCodeParser> implements INetConstanst{
	
	View mNotUsedPriorityCodeView ;
	private Activity mContext;
	private ListView mPNotUsedPriorityCodeRefreshListView;

	private BaseAdapter mNotUsedPriorityCodeAdapter;
	private int totalPage;
	private List<PriorityCodeBean> mNotUserPriorityCodeBeans;

	@Override
	public void getPageListParserResult(List<PriorityCodeBean> parserResult) {
		mNotUserPriorityCodeBeans=parserResult==null?new ArrayList<PriorityCodeBean>():parserResult;
//		if (super.currentpage == 1) {
//			this.messageBeans.clear();
//		}
		if (super.currentpage != 1) {
			mPNotUsedPriorityCodeRefreshListView.setSelection(this.mNotUserPriorityCodeBeans.size() / 2 + 1);
		}
		refreshAdapter();
	}

	private void refreshAdapter() {
//		if(null==mNotUsedPriorityCodeAdapter){
			mNotUsedPriorityCodeAdapter=getAdapter(mNotUserPriorityCodeBeans);
//			mNotUsedPriorityCodeAdapter = new NotUsedPriorityAdapter(mContext, mNotUserPriorityCodeBeans);
			mPNotUsedPriorityCodeRefreshListView.setAdapter(mNotUsedPriorityCodeAdapter);
//		}else {
//			mNotUsedPriorityCodeAdapter.notifyDataSetChanged();
//		}
	}

	/**
	 * @param mNotUserPriorityCodeBeans  
	* @Title: getAdapter 
	* @Description: 子类中实现该方法 设置适配器
	* @param @return    设定文件 
	* @return BaseAdapter    返回类型 
	* @throws 
	*/
	protected abstract BaseAdapter getAdapter(List<PriorityCodeBean> mNotUserPriorityCodeBeans);

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView  refresh = (RefreshableListView) mNotUsedPriorityCodeView 
				.findViewById(R.id.refresh_listview);
		mPNotUsedPriorityCodeRefreshListView =(ListView) refresh.getRefreshableView();
		mPNotUsedPriorityCodeRefreshListView.addHeaderView(View.inflate(mContext, R.layout.base_prioritycode_headview, null));
		// PauseOnScrollListener listener = new
		// PauseOnScrollListener(ImageLoader.getInstance(), true, true);
		// mRefresh.setOnScrollListener(listener);
		return refresh;
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mNotUsedPriorityCodeView =inflater.inflate(R.layout.notice_fragment, null);
		
//		ViewUtils.inject(this, mPrivateLetterView);
		mContext=getActivity();
		return mNotUsedPriorityCodeView ;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public PriorityCodeParser setParser(String str) {
		PriorityCodeParser priorityCodeParser = new PriorityCodeParser(str);
		totalPage=priorityCodeParser.getTotalPage();
		return priorityCodeParser;
	}

	private String userid;
	@Override
	public RequestParamsNet getApiParmars() {
		 userid = SharedPreferenceHelper
				.getLoginUserId(mContext);
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.setmStrHttpApi(PRIORITYCODE_LIST_URL);
		requestPar.addQueryStringParameter("UserId", userid);
		requestPar.addQueryStringParameter("Type", getType());
		requestPar.addQueryStringParameter("PageSize", PAGE_SIZE + "");
		requestPar.addQueryStringParameter("Page", super.currentpage + "");
		return requestPar;
	}

	/** 
	* @Title: getType 
	* @Description: 要来区分优先码的类型 在子类中实现
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	protected abstract String getType(); 


}