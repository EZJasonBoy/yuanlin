package com.ninetowns.tootoopluse.fragment;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.ChatGroupAdapter;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GroupChatBean;
import com.ninetowns.tootoopluse.bean.GroupChatList;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.parser.GroupChatParser;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.util.ReceiverManager;
import com.ninetowns.ui.fragment.BaseFragment;

/**
 * @ClassName: GroupChatFragment
 * @Description: 我的活动群
 * @author zhou
 * @date 2015-3-26 下午1:52:14
 * 
 */
public class GroupChatFragment extends
		BaseFragment<List<GroupChatList>, GroupChatParser> implements
		INetConstanst {
	private ReceiverMessageBroadcastReceiver mReceiverMessageBroadcast;

	private ChatGroupAdapter chatGroupAdapter;
	private List<GroupChatList> groupChatLists;
	private Activity mContext;
	@ViewInject(R.id.groupchat_listview)
	private ListView mChatGroupListView;

	
	private String urlApi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.groupchat_fragment, null);
		ViewUtils.inject(this, view); // 注入view和事件
		mContext = getActivity();
		Bundle bundle = getArguments();
		if (null != bundle) {
			if (bundle.containsKey(GROUP_CHAT_TAB_POSITION)) {
				int  position=bundle.getInt(GROUP_CHAT_TAB_POSITION);
				switch (position) {
				case 0:
					urlApi=ACTIVITY_CHATGROUP_LIST_URL;
					break;
				case 1:
					urlApi=WISH_CHATGROUP_LIST_URL;
					break;
				}
			} 
		}
		initBroadcast();
		return view;
	}

	private void initBroadcast() {
		mReceiverMessageBroadcast = new ReceiverMessageBroadcastReceiver();
		ReceiverManager.getIntance().registerReceiver(mContext,
				mReceiverMessageBroadcast, RECEIVE_MESSAGE_ACTION);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public GroupChatParser setParser(String str) {
		GroupChatParser groupChatParser = new GroupChatParser(str);
		return groupChatParser;

	}

	@Override
	public RequestParamsNet getApiParmars() {
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusEApplication.getAppContext());
		RequestParamsNet requestPar = new RequestParamsNet();
		requestPar.setmStrHttpApi(urlApi);
		requestPar.addQueryStringParameter("UserId", userid);
		requestPar.addQueryStringParameter("PageSize", 1000 + "");
		requestPar.addQueryStringParameter("Page", "1");
		return requestPar;
	}

	@Override
	public void getParserResult(List<GroupChatList> parserResult) {

		groupChatLists = parserResult;
		chatGroupAdapter = new ChatGroupAdapter(mContext, parserResult, urlApi);
		mChatGroupListView.setAdapter(chatGroupAdapter);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ReceiverManager.getIntance().unRegisterReceiver(
				mReceiverMessageBroadcast);
	}

	private class ReceiverMessageBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (null != intent) {
				Bundle bundle = intent.getExtras();
				if (null != bundle) {
					for (GroupChatList groupChatList : groupChatLists) {
						for (GroupChatBean groupChatBean : groupChatList
								.getGroupChatBeans()) {
							if (bundle.getString("groupid").equals(
									groupChatBean.getGroupId())) {
								groupChatList.MsgCount++;
								chatGroupAdapter.notifyDataSetChanged();
							}
						}
					}

				}
			}

		}
	}

}
