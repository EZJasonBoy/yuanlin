package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.AddressListActivity;
import com.ninetowns.tootooplus.adapter.AddAddressSpinnerAdapter;
import com.ninetowns.tootooplus.bean.GoodsAddressBean;
import com.ninetowns.tootooplus.bean.GoodsAreaBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.GoodsAreaParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;

public class AddressAddFragment extends BaseFragment<GoodsAreaBean, GoodsAreaParser> {

	private View view;
	
	private EditText delivery_add_name_et, delivery_add_mobile_et, delivery_add_details, delivery_add_zip_code;
	
	private Spinner delivery_add_province, delivery_add_city, delivery_add_district;
	
	private String add_spin_province_id = "", add_spin_city_id = "", add_spin_district_id = "";
	
	private String act_id = "";
	
	private String group_id = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		act_id = getActivity().getIntent().getStringExtra("act_id");
		group_id = getActivity().getIntent().getStringExtra("group_id");
		
		super.onLoadData(false, false, true);
		
		view = inflater.inflate(R.layout.address_add_fragment, null);
		
		
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
		two_or_one_btn_head_title.setText(R.string.delivery_address_add_title);
		
		
		delivery_add_name_et = (EditText)view.findViewById(R.id.delivery_add_name_et);
		delivery_add_mobile_et = (EditText)view.findViewById(R.id.delivery_add_mobile_et);
		delivery_add_details = (EditText)view.findViewById(R.id.delivery_add_details);
		delivery_add_zip_code = (EditText)view.findViewById(R.id.delivery_add_zip_code);
		
		delivery_add_province = (Spinner)view.findViewById(R.id.delivery_add_province);
		delivery_add_city = (Spinner)view.findViewById(R.id.delivery_add_city);
		delivery_add_district = (Spinner)view.findViewById(R.id.delivery_add_district);
		
		//保存按钮
		ImageView two_or_one_btn_head_second_btn = (ImageView)view.findViewById(R.id.two_or_one_btn_head_second_btn);
		two_or_one_btn_head_second_btn.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_btn.setImageResource(R.drawable.btn_keep);
		//为了让点击范围变大，所以点击外部的布局
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)view.findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String de_add_name = delivery_add_name_et.getText().toString().trim();
				String de_add_mobile = delivery_add_mobile_et.getText().toString().trim();
				String de_add_details = delivery_add_details.getText().toString().trim();
				String de_add_zip_code = delivery_add_zip_code.getText().toString().trim();
				
				if(de_add_name == null || "".equals(de_add_name)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_name_no_empty));
				} else if(de_add_mobile == null || "".equals(de_add_mobile)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_mobile_no_empty));
				} else if(de_add_details == null || "".equals(de_add_details)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_details_no_empty));
				} else if(de_add_zip_code == null || "".equals(de_add_zip_code)){ 
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_zip_cop_no_empty));
				} else if(!CommonUtil.isValidRealName(de_add_name)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_real_chinese_english_name));
				} else if(!CommonUtil.isValidRealNameLength(de_add_name)){	
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_real_name_length));
				} else if(!CommonUtil.isValidMobiNumber(de_add_mobile)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.mobile_num_format_error));
				} else if(!CommonUtil.isValidDetailAdressLength(de_add_details)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_details_length));
				} else if(!CommonUtil.isValidPostCode(de_add_zip_code)){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_post_code_format));
				} else {
					//新增
					showProgressDialog();
					
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
					
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_REALNAME, de_add_name);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PHONENUMBER, de_add_mobile);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DETAILS, de_add_details);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_POSTCODE, delivery_add_zip_code.getText().toString().trim());
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_PROVINCEID, add_spin_province_id);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_CITYID, add_spin_city_id);
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_DISTRICTID, add_spin_district_id);
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.USER_ADD_ADDRESS_URL, requestParamsNet, new RequestCallBack<String>() {
						
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							closeProgressDialogFragment();
							String jsonStr = new String(responseInfo.result);
							try {
								JSONObject json = new JSONObject(jsonStr);
								String add_status = "";
								String address_id = "";
								if(json.has("Status")){
									add_status = json.getString("Status");
								}
								if(json.has("Data")){
									JSONObject jsonData = json.getJSONObject("Data");
									if(jsonData.has("AddressId")){
										address_id = jsonData.getString("AddressId");
									}
								}
								
								if(add_status.equals("1")){
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_success));
									Intent address_intent = new Intent(getActivity(), AddressListActivity.class);
									address_intent.putExtra(TootooeNetApiUrlHelper.ACTIVITYID, act_id);
									address_intent.putExtra("address_id", address_id);
									address_intent.putExtra("group_id", group_id);
									address_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(address_intent);
									getActivity().finish();
									
								} else if(add_status.equals("1381")){
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.delivery_add_fail));
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
						
						
					
				}
			}
		});
		
		return view;
	}
	
	
	@Override
	public void getParserResult(GoodsAreaBean parserResult) {
		// TODO Auto-generated method stub
		if(parserResult != null){

			final GoodsAreaBean areaBean = parserResult;
			AddAddressSpinnerAdapter province_spin_adapter = new AddAddressSpinnerAdapter(getActivity(), areaBean.getArea_provinces());
			
			delivery_add_province.setAdapter(province_spin_adapter);
			
			delivery_add_province.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					GoodsAddressBean provinceBean = areaBean.getArea_provinces().get(position);
					add_spin_province_id = provinceBean.getGoods_address_id();
					//过滤以后的城市
					final List<GoodsAddressBean> cityBeans = new ArrayList<GoodsAddressBean>();
					//过滤出父id是该省id的列表
					for(int i = 0; i < areaBean.getArea_citys().size(); i++){
						if(areaBean.getArea_citys().get(i).getGoods_address_parent_id().equals(add_spin_province_id)){
							cityBeans.add(areaBean.getArea_citys().get(i));
						}
					}
					
					
					//如果省下面没有市,就不会执行delivery_add_city.setOnItemSelectedListener(), 所以要把市和区的适配器设为null
					if(cityBeans == null || cityBeans.size() == 0){
						delivery_add_city.setAdapter(null);
						delivery_add_district.setAdapter(null);
						add_spin_city_id = "";
						add_spin_district_id = "";
						
					} else {
						AddAddressSpinnerAdapter city_spin_adapter = new AddAddressSpinnerAdapter(getActivity(), cityBeans);
						delivery_add_city.setAdapter(city_spin_adapter);
					}
					
					delivery_add_city.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							GoodsAddressBean cityBean = cityBeans.get(position);
							add_spin_city_id = cityBean.getGoods_address_id();
							
							//过滤以后的区
							final List<GoodsAddressBean> districtBeans = new ArrayList<GoodsAddressBean>();

							//过滤出父id是该城市id的列表
							for(int j = 0; j < areaBean.getArea_districts().size(); j++){
								if(areaBean.getArea_districts().get(j).getGoods_address_parent_id().equals(add_spin_city_id)){
									districtBeans.add(areaBean.getArea_districts().get(j));
								}
							}
							
							if(districtBeans == null || districtBeans.size() == 0){
								delivery_add_district.setAdapter(null);
								add_spin_district_id = "";
							} else {
								AddAddressSpinnerAdapter district_spin_adapter = new AddAddressSpinnerAdapter(getActivity(), districtBeans);
								delivery_add_district.setAdapter(district_spin_adapter);
							}
							
							
							delivery_add_district.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> parent,
										View view, int position, long id) {
									GoodsAddressBean districtBean = districtBeans.get(position);
									add_spin_district_id = districtBean.getGoods_address_id();
								}

								@Override
								public void onNothingSelected(AdapterView<?> parent) {
									// TODO Auto-generated method stub
									
								}
								
							});
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
					});
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
		
			
			
		}
	}
	
	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.ADDRESS_GOODS_AREA_LIST_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ADDRESS_GOODS_AREA_LIST_ACTID, act_id);
		return requestParamsNet;
	}
	
	@Override
	public GoodsAreaParser setParser(String str) {
		// TODO Auto-generated method stub
		return new GoodsAreaParser(str);
	}

}