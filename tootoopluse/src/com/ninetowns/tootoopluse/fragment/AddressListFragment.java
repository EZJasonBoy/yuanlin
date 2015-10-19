package com.ninetowns.tootoopluse.fragment;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.SettingActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.AddressListBean;
import com.ninetowns.tootoopluse.fragment.AddressListFragment.AddressLvAdapter;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.AddressListParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

public class AddressListFragment extends BaseFragment<List<AddressListBean>, AddressListParser> {
	
	private ListView address_fragment_lv;
	
	private String act_id = "";
	public Map<Integer, AddressListBean> map;

	private View mCopy;

	private AddressLvAdapter adressAdapter;
	private List<AddressListBean> localData;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.address_list_fragment, null);
    	TextView mTvCopy = (TextView)view.findViewById(R.id.two_or_one_btn_head_second_tv);
    	mTvCopy.setVisibility(View.VISIBLE);
    	mTvCopy.setText(R.string.copy_all);
    	mTvCopy.setTextSize(UIUtils.px2Sp(
				TootooPlusEApplication.getAppContext(),
				(int) TootooPlusEApplication.getAppContext()
						.getResources().getDimension(R.dimen.h3)));
    	mCopy=view.findViewById(R.id.two_or_one_btn_head_second_layout);
    	mCopy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(localData!=null&&localData.size()>0){
					StringBuilder strbuild=new StringBuilder();
					for (AddressListBean iterable_element : localData) {
						String realName=iterable_element.getAddress_realName();
						String count=iterable_element.getAddress_memberCount();
						String phoneNumber=iterable_element.getAddress_phoneNumber();
						String addressDetail=iterable_element.getAddress_detail();
						String poastcode=iterable_element.getAddress_postcode();
						strbuild.append(realName).append("  (" + count + "份)").append("　　").append(phoneNumber).append(addressDetail+"　　"+poastcode);
					}
					if(getActivity()!=null){
						CommonUtil.copyContent(strbuild.toString(),getActivity());
						ComponentUtil.showToast(getActivity(), "复制成功");
					}
					
				}
				
			}
		});
    	
    	//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)view.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)view.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.address_list_title);
    	
		
		address_fragment_lv = (ListView)view.findViewById(R.id.address_fragment_lv);
		
    	
    	return view;
	 }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		
		act_id = getActivity().getIntent().getStringExtra(TootooeNetApiUrlHelper.ACTIVITYID);
		
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }


	@Override
	public AddressListParser setParser(String str) {
		return new AddressListParser(str);
	}

	@Override
	public void getParserResult(List<AddressListBean> parserResult) {
		if(localData!=null&&localData.size()>0){
			localData.clear();
		}
		if(parserResult != null&&parserResult.size()>0){
			localData=parserResult;
			adressAdapter=new AddressLvAdapter(getActivity(), parserResult);
			address_fragment_lv.setAdapter(adressAdapter);
			adressAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.ADDRESS_LIST_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADDRESS_LIST_ACT_ID, act_id);
		
		return requestParamsNet;
	}
	
	
	class AddressLvAdapter extends BaseAdapter{
		
		private Activity context;
		
		private List<AddressListBean> list;
		
		public AddressLvAdapter(Activity context, List<AddressListBean> list){
			this.context = context;
			
			this.list = list;
		}

		@Override
		public int getCount() {
			if(list != null && list.size() > 0){
				return list.size();
			} else {
				return 0;
			}
			
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.address_lv_item, null);
				vh.address_item_name = (TextView)convertView.findViewById(R.id.address_item_name);
				vh.address_item_phone_num = (CheckedTextView)convertView.findViewById(R.id.address_item_phone_num);
				vh.address_item_details = (TextView)convertView.findViewById(R.id.address_item_details);
				vh.mItemLayout=(LinearLayout)convertView.findViewById(R.id.address_item_layout);
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
			String count=list.get(position).getAddress_memberCount();
			String realName=list.get(position).getAddress_realName();
			if(!TextUtils.isEmpty(count)){
				vh.address_item_name.setText( realName+ "  (" + count + "份)");
			} else {
				vh.address_item_name.setText(realName + "  (0份)");
			}
			String phoneNumber=list.get(position).getAddress_phoneNumber();
			if(!TextUtils.isEmpty(phoneNumber)){
				Drawable phone = getResources().getDrawable(R.drawable.icon_phone);
				vh.address_item_phone_num.setCompoundDrawablesWithIntrinsicBounds(phone, null, null, null);
				vh.address_item_phone_num.setText(phoneNumber);	
			} else {
				vh.address_item_phone_num.setText("");	
			}
			String poastcode=list.get(position).getAddress_postcode();
			String addressDetail=list.get(position).getAddress_detail();
			if(!TextUtils.isEmpty(addressDetail)){
				if(!TextUtils.isEmpty(poastcode)){
					vh.address_item_details.setText(addressDetail+"　　"+poastcode);
				}else{
					vh.address_item_details.setText(addressDetail);
				}
				
			} else {
				vh.address_item_details.setText("");
			}
			vh.address_item_phone_num.setTag(phoneNumber);
			vh.address_item_phone_num.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String number=(String) v.getTag();
					if(!TextUtils.isEmpty(number)){
						Intent intent = new Intent(Intent.ACTION_DIAL);
						Uri data = Uri.parse("tel:" + number);
						intent.setData(data);
						startActivity(intent);
					}
				
				}
			});
			StringBuilder strbuild=new StringBuilder();
			strbuild.append(realName).append("  (" + count + "份)").append("　　").append(phoneNumber).append(addressDetail+"　　"+poastcode);
			vh.mItemLayout.setTag(strbuild);
			vh.mItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					StringBuilder stb=(StringBuilder) v.getTag();
					final String content=stb.toString();
					if(!TextUtils.isEmpty(content)){
						TooSureCancelDialog tooDialog = new TooSureCancelDialog(
								context);
						tooDialog.setDialogTitle(R.string.rainbo_hint);
						tooDialog
								.setDialogContent(R.string.comit_content);
						tooDialog.setTooDialogCallBack(new TooDialogCallBack() {

							@SuppressWarnings("deprecation")
							@Override
							public void onTooDialogSure() {
							
									CommonUtil.copyContent(content,context);
								
							}

							

							@Override
							public void onTooDialogCancel() {

							}
						});
						tooDialog.show();
					}
					
				
					
					return false;
				}
			});
			return convertView;
		}
		
		class ViewHolder{
			TextView address_item_name,address_item_details;
			CheckedTextView  address_item_phone_num;
			LinearLayout mItemLayout;
		}
	}
	
}
