package com.ninetowns.tootooplus.fragment;

import java.util.List;

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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.AddressMineAddActivity;
import com.ninetowns.tootooplus.bean.AddressListBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.AddressListParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

/**
 * 个人中心跳过来的个人所有地址
 * @author huangchao
 *
 */
public class AddressListMineFragment extends BaseFragment<List<AddressListBean>, AddressListParser> {
	
	private ListView address_fragment_lv;
	
	private List<AddressListBean> addressList;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.address_list_fragment, null);
    	//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)view.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)view.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.address_list_title);
    	
		
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)view.findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//新增收货地址
				Intent add_intent = new Intent(getActivity(), AddressMineAddActivity.class);
				startActivity(add_intent);
				
			}
		});
		
		TextView two_or_one_btn_head_second_tv = (TextView)view.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_tv.setText(R.string.address_list_add_btn);
		
		address_fragment_lv = (ListView)view.findViewById(R.id.address_fragment_lv);
		
    	return view;
	 }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }

	@Override
	public AddressListParser setParser(String str) {
		// TODO Auto-generated method stub
		return new AddressListParser(str);
	}

	@Override
	public void getParserResult(List<AddressListBean> parserResult) {
		// TODO Auto-generated method stub
		if(parserResult != null){
			addressList = parserResult;
			
			address_fragment_lv.setAdapter(new AddLvAdapter(getActivity(), addressList));
			
		}
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.ADDRESS_LIST_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADDRESS_LIST_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		return requestParamsNet;
	}
	
	//适配器
	class AddLvAdapter extends BaseAdapter{
		
		private Context context;
		private List<AddressListBean> list;
		
		public AddLvAdapter(Context context, List<AddressListBean> list){
			
			this.context = context;
			
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list != null && list.size() > 0){
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.address_lv_item, null);
				
				vh.address_lv_item_all_layout = (LinearLayout)convertView.findViewById(R.id.address_lv_item_all_layout);
				
				vh.address_lv_item_name = (TextView)convertView.findViewById(R.id.address_lv_item_name);
				
				vh.address_lv_item_phone = (TextView)convertView.findViewById(R.id.address_lv_item_phone);
				
				vh.address_lv_item_details = (TextView)convertView.findViewById(R.id.address_lv_item_details);
				
				vh.address_lv_item_update = (ImageView)convertView.findViewById(R.id.address_lv_item_update);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
			
			if(!TextUtils.isEmpty(list.get(position).getAdd_realName())){
				vh.address_lv_item_name.setText(list.get(position).getAdd_realName());
			} else {
				vh.address_lv_item_name.setText("");
			}
			
			
			if(!TextUtils.isEmpty(list.get(position).getAdd_phoneNumber())){
				vh.address_lv_item_phone.setText(list.get(position).getAdd_phoneNumber());
			} else {
				vh.address_lv_item_phone.setText("");
			}
			
			if(list.get(position).getAdd_provinceName().equals(list.get(position).getAdd_cityName())){
				//直辖市的情况
				vh.address_lv_item_details.setText(list.get(position).getAdd_cityName() + getResources().getString(R.string.address_item_city) + 
						list.get(position).getAdd_districtName() + list.get(position).getAdd_detailedAddress() + 
						"    " + list.get(position).getAdd_postcode());
			} else {
				vh.address_lv_item_details.setText(list.get(position).getAdd_provinceName() + getResources().getString(R.string.address_item_province) + 
						list.get(position).getAdd_cityName() + getResources().getString(R.string.address_item_city) + list.get(position).getAdd_districtName() + 
						list.get(position).getAdd_detailedAddress() + "    " + list.get(position).getAdd_postcode());
			}
			
			
			vh.address_lv_item_update.setVisibility(View.VISIBLE);
			vh.address_lv_item_update.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//修改地址
					Intent update_intent = new Intent(getActivity(), AddressMineAddActivity.class);
					update_intent.putExtra("addressListBean", list.get(position));
					startActivity(update_intent);
				}
			});
			
			//长按删除
			vh.address_lv_item_all_layout.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					
					TooSureCancelDialog tooDialog = new TooSureCancelDialog(getActivity());
					tooDialog.setDialogTitle(R.string.delete_dialog_title);
					tooDialog.setDialogContent(R.string.delete_dialog_content);
					tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
						
						@Override
						public void onTooDialogSure() {
							showProgressDialog();
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADDRESS_LIST_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_DELETE_ADDRESS_ADDRESSID, list.get(position).getAdd_addressId());
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.USER_DELETE_ADDRESS_URL, requestParamsNet, new RequestCallBack<String>() {
								
								@Override
								public void onSuccess(ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									closeProgressDialogFragment();
											
									try {
										JSONObject obj=new JSONObject(new String(responseInfo.result));
										String status = "";
										if(obj.has("Status")){
											status=	obj.getString("Status");
											if(status.equals("1")){
												list.remove(position);
												notifyDataSetChanged();
												ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delete_address_success));
											} else {
												ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delete_address_fail));
											}
										}
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
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
							
							
						}
						
						@Override
						public void onTooDialogCancel() {
							
						}
					});
					tooDialog.show();
					
					
					return false;
				}
			});
			
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout address_lv_item_all_layout;
			
			TextView address_lv_item_name;
			
			TextView address_lv_item_phone;
			
			TextView address_lv_item_details;
			
			ImageView address_lv_item_update;
			
		}
		
	}
	
}
