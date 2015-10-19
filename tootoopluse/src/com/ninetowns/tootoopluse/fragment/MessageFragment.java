package com.ninetowns.tootoopluse.fragment;

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
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.MessageAdapter;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.MessageBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.parser.MessageParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.util.ParserUitils;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;

/**
 * @ClassName: MessageFragment
 * @Description: 消息碎片
 * @author zhou
 * @date 2015-1-22 下午4:55:27
 * 
 */
public class MessageFragment extends
		PageListFragment<ListView, List<MessageBean>, MessageParser>implements INetConstanst {

	private static final int PageSize = 10;
	private static final int currenPage = 1;
	private View mMessageView;
	private ListView mMessageRefreshListView;

	private Activity mContext;
	private MessageAdapter mMessageAdapter;
	private int totalPage;
	private List<MessageBean> messageBeans;

	@Override
	public void getPageListParserResult(List<MessageBean> parserResult) {
		messageBeans=parserResult==null?new ArrayList<MessageBean>():parserResult;
//		if (super.currentpage == 1) {
//			this.messageBeans.clear();
//		}
		mMessageAdapter = new MessageAdapter(mContext, messageBeans);
		mMessageRefreshListView.setAdapter(mMessageAdapter);
		if (super.currentpage != 1) {
			mMessageRefreshListView.setSelection(this.messageBeans.size() / 2 + 1);
		}
		mMessageAdapter.notifyDataSetChanged();
	} 

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		RefreshableListView refresh = (RefreshableListView) mMessageView
				.findViewById(R.id.refresh_msg_list);
		mMessageRefreshListView = refresh.getRefreshableView();
		
		mMessageRefreshListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					 final int position, long id) {
				
				final String msgId=messageBeans.get(position-1).getMessageId();
				UIUtils.showConfirmDialog(mContext,  "删除消息", "是否删除该条消息", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						RequestParamsNet requestPar = new RequestParamsNet();
						requestPar.addQueryStringParameter("UserId", userId);
						requestPar.addQueryStringParameter("MessageId",msgId );
						CommonUtil.xUtilsGetSend(DELETE_SINGLE_MESSAGE, requestPar, new RequestCallBack<String>() {
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
												messageBeans.remove(position-1);
												mMessageAdapter.notifyDataSetChanged();
											}
											UIUtils.showCenterToast(mContext, "status=="+Status);
										}
									} catch (JSONException e) {
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		mMessageView = inflater.inflate(R.layout.msg_fragment, null);
		mContext = getActivity();
		ViewUtils.inject(this, mMessageView);
		return mMessageView;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return totalPage;
	}
	@Override
	public AbsParser setParser(String str) {
		MessageParser messageParser = new MessageParser(str);
		totalPage=messageParser.getTotalPage();
		return messageParser;
	}
	
	private String userId;
	@Override
	public RequestParamsNet getApiParmars() {
		userId = SharedPreferenceHelper
				.getLoginUserId(TootooPlusEApplication.getAppContext());
		
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.setmStrHttpApi("message/MessageList.htm?");
		requestPar.addQueryStringParameter("UserId", userId);
		requestPar.addQueryStringParameter("PageSize", PageSize + "");
		requestPar.addQueryStringParameter("Page", currenPage + "");
		return requestPar;
	}
	
	/** 
	* @Title: clearAll 
	* @Description: 	清空
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void clearAll(){
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.addQueryStringParameter("UserId",userId);
		CommonUtil.xUtilsGetSend(CLEAR_ALL_MESSAGE, requestPar, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				if(ParserUitils.isSuccess(responseInfo)){
					UIUtils.showCenterToast(mContext, "成功清空");
					
					if(null!=messageBeans){
						messageBeans.clear();
						mMessageAdapter.notifyDataSetChanged();
					}
				}
			}
			@Override
			public void onFailure(HttpException error, String msg) {
			}
		});
	}

}
