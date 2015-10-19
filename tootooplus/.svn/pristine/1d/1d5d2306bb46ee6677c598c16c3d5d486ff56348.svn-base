package com.ninetowns.tootooplus.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;

import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.activity.JoinMemberActivity;
import com.ninetowns.tootooplus.activity.MyFreeGroupActivity;
import com.ninetowns.tootooplus.bean.AddressListBean;
import com.ninetowns.tootooplus.bean.ContactListBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PhoneNameListFragment extends PhoneListMineFragment {
	private Map<Integer, ContactListBean> selectMap = new HashMap<Integer, ContactListBean>();
	private String contaId;
	private String act_id;
	private String group_id;
	@Override
	public void clickRight() {
		//确定
		if(adapter2!=null){
			Map<Integer, ContactListBean> adapterMap = adapter2.selectMap;
			ContactListBean addressListBean = null;
			for(Integer key : adapterMap.keySet()){
				addressListBean = adapterMap.get(key);
			}
			if(addressListBean!=null){
				yxmPop(addressListBean.getContactId());
			}
			
		}
	
	}
	@Override
	public void getBundle() {
		try {
			act_id = getActivity().getIntent().getStringExtra(TootooeNetApiUrlHelper.ACTIVITYID);
			group_id = getActivity().getIntent().getStringExtra("group_id");
			constantid=getActivity().getIntent().getStringExtra("constantid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void setRightTitle(TextView tv) {
		tv.setText(R.string.commit);
		
	}
	@Override
	public void setFotterView(ListView mListView) {
		View foot_view = LayoutInflater.from(getActivity()).inflate(
				R.layout.order_add_adress_layout, null);
		LinearLayout order_add_layout = (LinearLayout) foot_view
				.findViewById(R.id.order_add_layout);
		TextView mTvAddPhone = (TextView)foot_view
		.findViewById(R.id.tv_addphone);
		mTvAddPhone.setText("新增联系方式");
		
		order_add_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				skipAdd(true);
			}
		});
		mListView.addFooterView(foot_view);
	}
	@Override
	public void setBundle(Bundle bundle) {
		bundle.putString(TootooeNetApiUrlHelper.ACTIVITYID, act_id);
		bundle.putString("group_id", group_id);
		bundle.putString("constantid", constantid);
		
		
		
	}
	AddLvAdapter adapter2;
	private ListView listview;
	private List<ContactListBean> addressList;
	private String constantid;
	@Override
	protected void createAdapter(AddLvAdapter adapter,
			List<ContactListBean> addressList, ListView v) {
		this.addressList=addressList;
		this.adapter2=adapter;
		this.listview=v;
		if(constantid!= null){
			for(int i = 0; i < addressList.size(); i++){
				if(addressList.get(i).getContactId().equals(constantid)){
					selectMap.put(i, addressList.get(i));
				}
			}
		} else {
			constantid=addressList.get(0).getContactId();
			selectMap.put(0, addressList.get(0));
		}
		adapter.setMapData(selectMap);
		listview.setAdapter(adapter);
		
		
	}
	@Override
	protected void onItemClickData(View view, int position) {
		if(addressList!=null&&addressList.size()>0){
			selectMap.clear();
			selectMap.put(position, addressList.get(position));
			adapter2.notifyDataSetChanged();
		}
		
	}
	
	private void yxmPop(final String constid){
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		int screen_width = wm.getDefaultDisplay().getWidth();
		//屏幕高度
		int screen_height = wm.getDefaultDisplay().getHeight();
		final PopupWindow popupWindow = new PopupWindow(getActivity());
		
		if(!popupWindow.isShowing()){
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.yxm_pop_layout, null);
			
			popupWindow.setContentView(view);

			//窗口的宽带和高度根据情况定义
			popupWindow.setWidth(screen_width);
			popupWindow.setHeight(screen_height);
			
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));
			
			popupWindow.showAtLocation(LayoutInflater.from(getActivity()).inflate(R.layout.join_member_fragment, null), 
											//位置可以按要求定义
											Gravity.NO_GRAVITY, 0, 0);
			LinearLayout yxm_pop_all_layout = (LinearLayout)view.findViewById(R.id.yxm_pop_all_layout);
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)yxm_pop_all_layout.getLayoutParams();
			linearParams.width = screen_width * 3/4;
			linearParams.height = screen_height / 3;
			yxm_pop_all_layout.setLayoutParams(linearParams);
			
			//用优先码
			final CheckBox use_yxm_pop_cb = (CheckBox)view.findViewById(R.id.use_yxm_pop_cb);
			//默认使用优先码
			use_yxm_pop_cb.setChecked(true);
			
			final CheckBox unuse_yxm_pop_cb = (CheckBox)view.findViewById(R.id.unuse_yxm_pop_cb);
			
			LinearLayout use_yxm_pop_layout = (LinearLayout)view.findViewById(R.id.use_yxm_pop_layout);
			use_yxm_pop_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!use_yxm_pop_cb.isChecked()){
						use_yxm_pop_cb.setChecked(true);
						unuse_yxm_pop_cb.setChecked(false);
					}
					
				}
			});
			LinearLayout unuse_yxm_pop_layout = (LinearLayout)view.findViewById(R.id.unuse_yxm_pop_layout);
			unuse_yxm_pop_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!unuse_yxm_pop_cb.isChecked()){
						unuse_yxm_pop_cb.setChecked(true);
						use_yxm_pop_cb.setChecked(false);
					}
					
				}
			});
			
			final EditText use_yxm_pop_et = (EditText)view.findViewById(R.id.use_yxm_pop_et);
			
			LinearLayout yxm_pop_submit_layout = (LinearLayout)view.findViewById(R.id.yxm_pop_submit_layout);
			yxm_pop_submit_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//线下
					showProgressDialog();
					//组团报名
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_GROUPID, group_id);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_ACTIVITYID, act_id);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_ADDRESS_ID, "0");
					requestParamsNet.addQueryStringParameter(ConstantsTooTooEHelper.CONTACTID,constid);
					if(use_yxm_pop_cb.isChecked()){
						if(use_yxm_pop_et.getText().toString().trim().equals("")){
							ComponentUtil.showToast(getActivity(), getResources().getString(R.string.use_yxm_tv_no_empty));
							closeProgressDialogFragment();
							return;
						} else {
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_PRIORITY_CODE, use_yxm_pop_et.getText().toString().trim());
						}
						
					}
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.JOIN_MEM_SUBMIT_GROUP_URL, requestParamsNet, new RequestCallBack<String>() {
						
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialogFragment();
							String jsonStr = new String(responseInfo.result);
							
							try {
								JSONObject jsonObj = new JSONObject(jsonStr);
								String register_status = "";
								if(jsonObj.has("Status")){
									register_status = jsonObj.getString("Status");
								}
								
								if(register_status.equals("1")){
									//跳转到活动详情
									Intent act_intent = new Intent(getActivity(), ActivityDetailActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString(TootooeNetApiUrlHelper.ACTIVITYID, act_id);
									bundle.putString("currentPosition", "0");
									act_intent.putExtra(TootooeNetApiUrlHelper.BUNDLE, bundle);
									act_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(act_intent);
									AppManager.getAppManager().finishActivity(MyFreeGroupActivity.class);
									AppManager.getAppManager().finishActivity(JoinMemberActivity.class);
									getActivity().finish();
									
								} else if(register_status.equals("3104")){
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.join_enter_group_yxm_fail));
								} else if(register_status.equals("3105")){
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.join_enter_group_less_ten));
								} else {
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.join_enter_group_fail));
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
							closeProgressDialogFragment();
							ComponentUtil.showToast(getActivity(), getResources().getString(R.string.errcode_network_response_timeout));
						}
					});
					popupWindow.dismiss();	
				}
			});
			
			LinearLayout yxm_pop_cancel_layout = (LinearLayout)view.findViewById(R.id.yxm_pop_cancel_layout);
			yxm_pop_cancel_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
				}
			});
		}
	}
}
