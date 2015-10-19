package com.ninetowns.tootooplus.fragment;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.UpdateAddMineAddActivity;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ContactListBean;
import com.ninetowns.tootooplus.fragment.PhoneListMineFragment.AddLvAdapter;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.ContactListParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

/**
 * 联系方式
 * 
 */
public class PhoneListMineFragment extends
		BaseFragment<List<ContactListBean>, ContactListParser> {

	private ListView address_fragment_lv;

	private List<ContactListBean> addressList;

	private AddLvAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.phone_list_fragment, null);
		getBundle();
		// 返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout) view
				.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});

		// 标题
		TextView two_or_one_btn_head_title = (TextView) view
				.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.comit_sb);

		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout) view
				.findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 新增收货地址
						clickRight();

					}

				});
		
		TextView two_or_one_btn_head_second_tv = (TextView) view
				.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		setRightTitle(two_or_one_btn_head_second_tv);
		address_fragment_lv = (ListView) view
				.findViewById(R.id.address_fragment_lv);
		setFotterView(address_fragment_lv);
//		address_fragment_lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				onItemClickData(view, position);
//			}
//
//			
//		});
		return view;
	}

	public void getBundle() {

	}



	public void setFotterView(ListView mListView) {

	}

	public void setRightTitle(TextView tv) {
		tv.setText(R.string.address_list_add_btn);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public ContactListParser setParser(String str) {
		// TODO Auto-generated method stub
		return new ContactListParser(str);
	}

	/**
	 * @Title: clickRight
	 * @Description: TODO
	 * @param
	 * @return
	 * @throws
	 */
	public void clickRight() {
		skipAdd(false);
	}

	/**
	 * @Title: skipAdd
	 * @Description: 新增联系方式
	 * @param
	 * @return
	 * @throws
	 */
	public void skipAdd(boolean isFooter) {
		Intent add_intent = new Intent(getActivity(),
				UpdateAddMineAddActivity.class);
		Bundle bundle = new Bundle();
		setBundle(bundle);
		bundle.putBoolean(ConstantsTooTooEHelper.isUpdate, false);
		bundle.putBoolean("isFooter", isFooter);
		add_intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
		startActivity(add_intent);
	}
	public void setBundle(Bundle bundle){
		
	}

	@Override
	public void getParserResult(List<ContactListBean> parserResult) {
		// TODO Auto-generated method stub
		if (parserResult != null && parserResult.size() > 0) {
			addressList = parserResult;
			adapter=new AddLvAdapter(getActivity(),
					addressList);
			createAdapter(adapter,addressList,address_fragment_lv);

		} else {
			address_fragment_lv.setAdapter(null);
			ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
					"暂无联系方式");
		}
	
	}

	
	protected void createAdapter(AddLvAdapter adapter,List<ContactListBean> addressList,ListView v) {
		address_fragment_lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	protected void onItemClickData(View view, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.CONTACTLIST);
		requestParamsNet.addQueryStringParameter(
				TootooeNetApiUrlHelper.ADDRESS_LIST_USERID,
				SharedPreferenceHelper.getLoginUserId(getActivity()));
		return requestParamsNet;
	}

	// 适配器
	class AddLvAdapter extends BaseAdapter {
		 public Map<Integer, ContactListBean> selectMap;

		private Context context;
		private List<ContactListBean> list;
		public void setMapData(Map<Integer, ContactListBean> selectMap){
			this.selectMap=selectMap;
			
		}

		public AddLvAdapter(Context context, List<ContactListBean> list) {

			this.context = context;

			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list != null && list.size() > 0) {
				return list.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ContactListBean itemBean = list.get(position);
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.phone_lv_item, null);
				ViewUtils.inject(vh, convertView);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			String username = itemBean.getName();
			String phone = itemBean.getPhone();

			if (!TextUtils.isEmpty(username)) {
				vh.phone_username.setText(username);

			}
			if (!TextUtils.isEmpty(phone)) {
				vh.phone.setText(phone);
			}
			vh.phone_lv_item_update.setTag(itemBean);
			vh.phone_lv_item_update
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							ContactListBean itemBean = (ContactListBean) v
									.getTag();
							Intent intent = new Intent(TootooPlusApplication
									.getAppContext(),
									UpdateAddMineAddActivity.class);
							Bundle bundle = new Bundle();
							bundle.putBoolean(ConstantsTooTooEHelper.isUpdate,
									true);
							bundle.putSerializable(
									ConstantsTooTooEHelper.UPDATEDEL_INFO,
									itemBean);
							intent.putExtra(ConstantsTooTooEHelper.BUNDLE,
									bundle);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
					});
			vh.phone_lv_item_all_layout
					.setOnLongClickListener(new MyOnLongListner(position, this,
							itemBean, list));
			vh.phone_lv_item_all_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onItemClickData(v, position);
				}
			});
			
		
			if(selectMap!=null){
				if(selectMap.get(position)!=null){
					vh.phone_lv_item_select.setVisibility(View.VISIBLE);
					vh.phone_lv_item_update.setVisibility(View.INVISIBLE);
				}else{
					vh.phone_lv_item_select.setVisibility(View.INVISIBLE);
					vh.phone_lv_item_update.setVisibility(View.INVISIBLE);
				}
			
			}else{
				vh.phone_lv_item_select.setVisibility(View.INVISIBLE);
				vh.phone_lv_item_update.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

		class ViewHolder {
			@ViewInject(R.id.rl_layout)
			RelativeLayout phone_lv_item_all_layout;
			@ViewInject(R.id.ct_phone)
			TextView phone;
			@ViewInject(R.id.ct_username)
			TextView phone_username;
			@ViewInject(R.id.phone_lv_item_update)
			ImageView phone_lv_item_update;
			@ViewInject(R.id.phone_lv_item_select)
			ImageView phone_lv_item_select;
			

		}

	}

	class MyOnLongListner implements View.OnLongClickListener {
		ContactListBean itemBean;
		List<ContactListBean> list;
		AddLvAdapter addlvAdapter;
		int position;

		public MyOnLongListner(int position, AddLvAdapter addlvAdapter,
				ContactListBean itemBean, List<ContactListBean> list) {
			this.itemBean = itemBean;
			this.list = list;
			this.itemBean = itemBean;
			this.addlvAdapter = addlvAdapter;
			this.position = position;

		}

		@Override
		public boolean onLongClick(View v) {

			TooSureCancelDialog tooDialog = new TooSureCancelDialog(
					getActivity());
			tooDialog.setDialogTitle(R.string.delete_dialog_title);
			tooDialog.setDialogContent(R.string.delete_dialog_content);
			tooDialog.setTooDialogCallBack(new TooDialogCallBack() {

				@Override
				public void onTooDialogSure() {
					showProgressDialog();
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.USER_ID,
							SharedPreferenceHelper
									.getLoginUserId(getActivity()));
					requestParamsNet.addQueryStringParameter(
							ConstantsTooTooEHelper.CONTACTID,
							itemBean.getContactId());
					CommonUtil.xUtilsGetSend(
							TootooeNetApiUrlHelper.DELETE_INOF_PHONE,
							requestParamsNet, new RequestCallBack<String>() {

								@Override
								public void onSuccess(
										ResponseInfo<String> responseInfo) {
									closeProgressDialogFragment();

									try {
										JSONObject obj = new JSONObject(
												new String(responseInfo.result));
										String status = "";
										if (obj.has("Status")) {
											status = obj.getString("Status");
											if (status.equals("1")) {
												list.remove(position);
												addlvAdapter
														.notifyDataSetChanged();
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.delete_phone_success));
											} else {
												ComponentUtil
														.showToast(
																getActivity(),
																getResources()
																		.getString(
																				R.string.delete_phone_fail));
											}
										}

									} catch (JSONException e) {
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									// TODO Auto-generated method stub
									closeProgressDialogFragment();
									ComponentUtil
											.showToast(
													getActivity(),
													getResources()
															.getString(
																	R.string.errcode_network_response_timeout));
								}
							});

				}

				@Override
				public void onTooDialogCancel() {

				}
			});
			tooDialog.show();

			return false;

		}

	}
}
