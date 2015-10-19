package com.ninetowns.tootooplus.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.parser.AbsParser;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.NoticeAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.NoticeBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.parser.NoticeParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.ParserUitils;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/** 
* @ClassName: NoticeFragment 
* @Description: 通知碎片
* @author zhou
* @date 2015-1-22 下午4:55:56 
*  
*/
public class NoticeFragment extends PageListFragment<ListView, List<NoticeBean>, NoticeParser> implements INetConstanst{

	View mNoticeView;
	private Activity mContext;
	private ListView mNoticeRefreshListView;

	private NoticeAdapter mNoticeAdapter;
	private int totalPage;
	private List<NoticeBean> mNoticeBeans;
	@Override
	public void getPageListParserResult(List<NoticeBean> parserResult) {
		mNoticeBeans=parserResult==null?new ArrayList<NoticeBean>():parserResult;
//		if (super.currentpage == 1) {
//			this.messageBeans.clear();
//		}
		mNoticeAdapter = new NoticeAdapter(mContext, mNoticeBeans);
		mNoticeRefreshListView.setAdapter(mNoticeAdapter);
		if (super.currentpage != 1) {
			mNoticeRefreshListView.setSelection(this.mNoticeBeans.size() / 2 + 1);
		}
		mNoticeAdapter.notifyDataSetChanged();
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView refresh = (RefreshableListView) mNoticeView
				.findViewById(R.id.refresh_listview);
		mNoticeRefreshListView = refresh.getRefreshableView();
		
		mNoticeRefreshListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					 final int position, long id) {
				final String NoticeId=mNoticeBeans.get(position-1).getNoticeId();
				UIUtils.showConfirmDialog(mContext,  "删除消息", "是否删除该条消息", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						RequestParamsNet requestPar = new RequestParamsNet();
						requestPar.addQueryStringParameter("UserId", userId);
						requestPar.addQueryStringParameter("NoticeId",NoticeId );
						CommonUtil.xUtilsGetSend(DELETE_SINGLE_NOTICE, requestPar, new RequestCallBack<String>() {
							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								String result=responseInfo.result;
								if(!StringUtils.isEmpty(result)){
									try {
										JSONObject object=new JSONObject(result);
										if(null!=object&&object.has("Status")){
											int Status=object.getInt("Status");
											if(1==Status){
												UIUtils.showCenterToast(mContext, "删除成功");	
												mNoticeBeans.remove(position-1);
												mNoticeAdapter.notifyDataSetChanged();
											}
											UIUtils.showCenterToast(mContext, "status=="+Status);
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							@Override
							public void onFailure(HttpException error, String msg) {
								UIUtils.showCenterToast(mContext, "删除失败"+msg);
								
							}
						});
						
					}
				} );
				return false;
			}
		});
		return refresh;
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mNoticeView=inflater.inflate(R.layout.notice_fragment, null);
		ViewUtils.inject(this, mNoticeView);
		mContext=getActivity();
		return mNoticeView;
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
	public AbsParser setParser(String str) {
		NoticeParser messageParser = new NoticeParser(str);
		return messageParser;
	}
	private String userId;
	@Override
	public RequestParamsNet getApiParmars() {
		userId = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.setmStrHttpApi(NOTICE_LIST_URL);
		requestPar.addQueryStringParameter("UserId", userId);
		requestPar.addQueryStringParameter("PageSize", PAGE_SIZE+ "");
		requestPar.addQueryStringParameter("Page", super.currentpage + "");
		return requestPar;
	}
	
	
	public void clearAll(){
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.addQueryStringParameter("UserId",userId);
		CommonUtil.xUtilsGetSend(CLEAR_ALL_NOTICE, requestPar, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				if(ParserUitils.isSuccess(responseInfo)){
					UIUtils.showCenterToast(mContext, "成功清空");
					if(null!=mNoticeBeans){
						mNoticeBeans.clear();
						mNoticeAdapter.notifyDataSetChanged();
					}
				}
			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});
	}
}