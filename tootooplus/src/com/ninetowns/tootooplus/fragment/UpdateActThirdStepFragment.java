package com.ninetowns.tootooplus.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.HomeActivity;
import com.ninetowns.tootooplus.activity.MyActivityApplyActivity;
import com.ninetowns.tootooplus.activity.SelectCityActivity;
import com.ninetowns.tootooplus.adapter.CreateActTypeLvAdapter;
import com.ninetowns.tootooplus.bean.CityBean;
import com.ninetowns.tootooplus.bean.ProvinceCityBean;
import com.ninetowns.tootooplus.bean.UpdateActThirdBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.CreateActThirdChangeView;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.UpdateThirdParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.BaseFragment;
import com.ninetowns.ui.widget.JudgeDate;
import com.ninetowns.ui.widget.ScreenInfo;
import com.ninetowns.ui.widget.WheelMain;
import com.ninetowns.ui.widget.dialog.TooSelectTimeDialog;
import com.ninetowns.ui.widget.dialog.TooSelectTimeDialog.TooTimeDialogCallBack;

public class UpdateActThirdStepFragment extends BaseFragment<UpdateActThirdBean, UpdateThirdParser> implements OnGetGeoCoderResultListener{
	
	private String act_id = "";
	
	//区分是再次发布还是修改
	private boolean isAgainPush = false;
	
	private String act_name= "";
	
	private String act_story_id = "";
	
	private String act_line_type = "";
	
	//用来注册点击选择组团方式
	LinearLayout create_act_third_step_line_type_layout;
	
	private ImageView create_act_third_arrow;
	
	private TextView create_act_third_line_type;
	
	/**变化的布局**/
	private CreateActThirdChangeView create_act_third_change_view;
	
	private UpdateActThirdBean updateActBean = null;
	
	//配送发货地点击
	private LinearLayout create_act_online_change_send_layout;
	//配送发货地
	private TextView create_act_online_change_send_area;
	
	//配送到地址点击
	private LinearLayout create_act_online_change_arrive_layout;
	//配送到地址
	private TextView create_act_online_change_arrive_area;
	
	
	//配送方式点击
	private LinearLayout create_act_third_step_send_type_layout;
	
	//配送方式
	private TextView create_act_online_change_send_type;
	
	//配送方式 "0"商家负责, "1"沱沱配送
	private String send_type = "0";
	
	//配送方式下拉按钮
	private ImageView create_act_online_change_send_arrow;
	
	//其他配送方式布局
	private LinearLayout create_act_online_change_other_send_type_layout;
	
	private EditText create_act_online_change_other_send_type_et;
	
	/**报名开始时间**/
	private TextView create_act_third_enter_start_time;
	
	/**报名结束时间**/
	private TextView create_act_third_enter_end_time;
	
	/**活动人数**/
	private EditText create_act_third_person_num;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**活动开始时间**/
	private TextView create_act_outline_change_start_time;
	/**活动结束时间**/
	private TextView create_act_outline_change_end_time;
	/**填写地址**/
	private EditText create_act_outline_change_address;
	/**地图**/
	private MapView create_act_outline_change_bmapView;
	
	BaiduMap mBaiduMap = null;
	// 搜索模块，也可去掉地图模块独立使用
	private GeoCoder mSearch = null; 
	
	private EditText create_act_outline_change_store_name;
	
	private TextView create_act_outline_change_search;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		
		Intent intent = getActivity().getIntent();
	    Bundle bundle = intent.getExtras().getBundle(ConstantsTooTooEHelper.BUNDLE);
	    act_id = bundle.getString(ConstantsTooTooEHelper.ACTIVITYID);
	    act_name = bundle.getString(ConstantsTooTooEHelper.ACTIVITY_NAME);
	    act_story_id = bundle.getString(ConstantsTooTooEHelper.STORYID);
	    isAgainPush = bundle.getBoolean("isAgainPush");
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		 
		 View view = inflater.inflate(R.layout.create_act_third_step, null);
		 
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
		two_or_one_btn_head_title.setText(R.string.update_act_title);
				
		RelativeLayout create_act_third_step_submit_layout = (RelativeLayout)view.findViewById(R.id.create_act_third_step_submit_layout);
		create_act_third_step_submit_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RequestParamsNet requestParamsNet = new RequestParamsNet();
				//提交
				if(act_line_type.equals("0")){
					//线上组团
					if(create_act_third_person_num.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.create_act_per_num_empty));
					} else if(Integer.parseInt(create_act_third_person_num.getText().toString().trim()) < 100){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.create_act_per_num_less_100));
					} else if(create_act_third_enter_start_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_start_time_required));
					} else if(create_act_third_enter_end_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_end_time_required));
					} else if(create_act_third_enter_start_time.getText().toString().trim().compareTo(create_act_third_enter_end_time.getText().toString().trim()) > 0){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_start_more_end_time));
					} else if(create_act_online_change_send_area.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.send_area_required));
					} else if(create_act_online_change_arrive_area.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.area_area_required));
					} else {
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_ACTID, act_id);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_TYPE, act_line_type);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_COUNT_PARTICIPANT, create_act_third_person_num.getText().toString().trim());	
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_START, CommonUtil.dateToTimeStamp(create_act_third_enter_start_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_END, CommonUtil.dateToTimeStamp(create_act_third_enter_end_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_LOCATION, SharedPreferenceHelper.getSendCity(getActivity()).getCity_name());
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_AREAID, SharedPreferenceHelper.getArriveCity(getActivity()).getCity_id());
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DISPATCHTYPE, send_type);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_EXPRESS_NAME, create_act_online_change_other_send_type_et.getText().toString().trim());
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_ACT_NAME, act_name);
						if(act_story_id != null){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_STORYID, act_story_id);
						}
					}
					
				} else {
					//线下组团
					if(create_act_third_person_num.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.create_act_per_num_empty));
					} else if(Integer.parseInt(create_act_third_person_num.getText().toString().trim()) < 100){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.create_act_per_num_less_100));
					} else if(create_act_third_enter_start_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_start_time_required));
					} else if(create_act_third_enter_end_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_end_time_required));
					} else if(create_act_third_enter_start_time.getText().toString().trim().compareTo(create_act_third_enter_end_time.getText().toString().trim()) > 0){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.enter_start_more_end_time));
					} else if(create_act_outline_change_start_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.start_time_required));
					} else if(create_act_outline_change_end_time.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.end_time_required));
					} else if(create_act_outline_change_start_time.getText().toString().trim().compareTo(create_act_outline_change_end_time.getText().toString().trim()) > 0){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.start_more_end_time));
					} else if(create_act_outline_change_store_name.getText().toString().trim().equals("")){
						ComponentUtil.showToast(getActivity(), getResources().getString(R.string.create_act_store_name_required));
					} else {
						//未判断地址和百度地图所获地址哪个可以为空的判断
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_ACTID, act_id);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_TYPE, act_line_type);
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_COUNT_PARTICIPANT, create_act_third_person_num.getText().toString().trim());	
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_START, CommonUtil.dateToTimeStamp(create_act_third_enter_start_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_REGISTER_END, CommonUtil.dateToTimeStamp(create_act_third_enter_end_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_START, CommonUtil.dateToTimeStamp(create_act_outline_change_start_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_DATE_END, CommonUtil.dateToTimeStamp(create_act_outline_change_end_time.getText().toString().trim()));
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_PLACE, create_act_outline_change_address.getText().toString().trim());
						//百度地图地址暂时写的空
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_PLACE_MAP, "");
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_SUPPLIERNAME, create_act_outline_change_store_name.getText().toString().trim());
						requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CREATE_ACT_THIRD_STEP_SUBMIT_ACT_NAME, act_name);
						if(act_story_id != null){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_STORYID, act_story_id);
						}
					}
				}
				
				CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_URL, requestParamsNet, new RequestCallBack<String>(){

					@Override
					public void onSuccess(
							ResponseInfo<String> responseInfo) {
						String addStr = new String(responseInfo.result);
						try {
							JSONObject jsonObj = new JSONObject(addStr);
							if(jsonObj.has("Status")){
								String addStatus = jsonObj.getString("Status");
								if(addStatus.equals("1")){
									ComponentUtil.showToast(getActivity(), getResources().getString(R.string.update_act_success));
									AppManager.getAppManager().finishOtherActivity(HomeActivity.class);
									Intent myActApply = new Intent(getActivity(), MyActivityApplyActivity.class);
									startActivity(myActApply);
									
								}
								
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(HttpException error,
							String msg) {
						
					}
					
				});
				
			}
		});
		TextView two_or_one_btn_head_second_tv = (TextView)view.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_tv.setText(R.string.change_pwd_submit);
			
		//用来注册点击选择组团方式
		create_act_third_step_line_type_layout = (LinearLayout)view.findViewById(R.id.create_act_third_step_line_type_layout);
		//显示组团方式
		create_act_third_line_type = (TextView)view.findViewById(R.id.create_act_third_line_type);
		create_act_third_arrow = (ImageView)view.findViewById(R.id.create_act_third_arrow);
		
		create_act_third_change_view = (CreateActThirdChangeView)view.findViewById(R.id.create_act_third_change_view);
		
		create_act_third_enter_start_time = (TextView)view.findViewById(R.id.create_act_third_enter_start_time);
		create_act_third_enter_end_time = (TextView)view.findViewById(R.id.create_act_third_enter_end_time);
		create_act_third_person_num = (EditText)view.findViewById(R.id.create_act_third_person_num);
		
		 return view;
	 }
	

	@Override
	public UpdateThirdParser setParser(String str) {
		// TODO Auto-generated method stub
		return new UpdateThirdParser(str);
	}

	@Override
	public void getParserResult(UpdateActThirdBean parserResult) {
		// TODO Auto-generated method stub
		if(parserResult != null){
			updateActBean = parserResult;
			
			//组装两种组团方式
			final List<String> act_online_list = SharedPreferenceHelper.getLineType(getActivity());
			
			if(!TextUtils.isEmpty(updateActBean.getUp_actId())){
				act_id = updateActBean.getUp_actId();
			}
			
			if(!TextUtils.isEmpty(updateActBean.getUp_type()) && updateActBean.getUp_type().equals("1")){
				act_line_type = "1";
				create_act_third_change_view.initView(act_line_type);
				create_act_third_line_type.setText(act_online_list.get(1));
				//线下
				outlineLayout();
			} else {
				act_line_type = "0";
				create_act_third_change_view.initView(act_line_type);
				create_act_third_line_type.setText(act_online_list.get(0));
				//线上
				onlineLayout();
			}
			
			
			create_act_third_step_line_type_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//选择组团方式
					selectLineType(v, act_online_list);
				}
			});
			
			
			if(!TextUtils.isEmpty(updateActBean.getUp_dateRegisterStart())){
				create_act_third_enter_start_time.setText(updateActBean.getUp_dateRegisterStart());
			} else {
				create_act_third_enter_start_time.setText("");
			}
			
			create_act_third_enter_start_time.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectTime(create_act_third_enter_start_time);
				}
			});
			
			
			if(!TextUtils.isEmpty(updateActBean.getUp_dateRegisterEnd())){
				create_act_third_enter_end_time.setText(updateActBean.getUp_dateRegisterEnd());
			} else {
				create_act_third_enter_end_time.setText("");
			}
			create_act_third_enter_end_time.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectTime(create_act_third_enter_end_time);
				}
			});
			
			if(!TextUtils.isEmpty(updateActBean.getUp_countParticipant())){
				create_act_third_person_num.setText(updateActBean.getUp_countParticipant());
			} else {
				create_act_third_person_num.setText("");
			}
		}
		
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_PRE_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_PRE_ACT_ID, act_id);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_PRE_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		if(isAgainPush){
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITY_INFO_UPDATE_PRE_AGAIN_RELEASED, "1");
		}
		
		return requestParamsNet;
	}
	
	
	/**
	 * 选择组团方式
	 * @param popupWindow
	 * @param view
	 * @param list
	 */
	private void selectLineType(final View view, final List<String> list){
		final PopupWindow popupWindow = new PopupWindow(getActivity());
		View pw_view = LayoutInflater.from(getActivity()).inflate(R.layout.create_act_wish_pw, null);
		
		create_act_third_arrow.setImageResource(R.drawable.icon_arrow_up);
		
		popupWindow.setContentView(pw_view);
		//窗口的宽带和高度根据情况定义
		popupWindow.setWidth(view.getWidth());
		/**购物车每个item布局**/
        View pw_item_view = LayoutInflater.from(getActivity()).inflate(R.layout.create_act_down_item, null);
        LinearLayout create_act_down_item_layout = (LinearLayout)pw_item_view.findViewById(R.id.create_act_down_item_layout);
        LinearLayout.LayoutParams pw_item_layoutParams = (LinearLayout.LayoutParams)create_act_down_item_layout.getLayoutParams();
        int pw_item_height = pw_item_layoutParams.height;
		
        ListView create_act_wish_pw_lv = (ListView)pw_view.findViewById(R.id.create_act_wish_pw_lv);
        
		popupWindow.setHeight(pw_item_height * list.size() + create_act_wish_pw_lv.getDividerHeight() * (list.size() - 1));
		
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				create_act_third_arrow.setImageResource(R.drawable.icon_arrow_down);
			}
		});
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_corners_shape_drawable));
		
//		//窗口进入和退出的效果,如果不设置默认有进入和退出效果
//		popupWindow.setAnimationStyle(R.style.win_ani_right_top_menu);
		int[] location = new int[2];

	    view.getLocationOnScreen(location);
		//位置可以按要求定义
		popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] + view.getHeight() + 2);
		
		create_act_wish_pw_lv.setAdapter(new CreateActTypeLvAdapter(getActivity(), list));
		create_act_wish_pw_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				create_act_third_line_type.setText(list.get(arg2));
				act_line_type = String.valueOf(arg2);
				popupWindow.dismiss();
				
				create_act_third_change_view.initView(act_line_type);
				if(act_line_type.equals("0")){
					//线上
					onlineLayout();
					
				} else {
					//线下
					outlineLayout();
				}
				
			}
		});
	}
	
	
	/**
	 * 线上活动变化
	 */
	private void onlineLayout(){
		create_act_online_change_send_layout = (LinearLayout)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_send_layout);
		create_act_online_change_send_area = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_send_area);
		
		create_act_online_change_arrive_layout = (LinearLayout)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_arrive_layout);
		create_act_online_change_arrive_area = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_arrive_area);
		
		create_act_third_step_send_type_layout = (LinearLayout)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_third_step_send_type_layout);
		create_act_online_change_send_type = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_send_type);
		create_act_online_change_send_arrow = (ImageView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_send_arrow);
		create_act_online_change_other_send_type_layout = (LinearLayout)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_other_send_type_layout);
		
		create_act_online_change_other_send_type_et = (EditText)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_online_change_other_send_type_et);
		
		if(StringUtils.isEmpty(SharedPreferenceHelper.getSendCity(getActivity()).getCity_name())){
			if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_location())){
				create_act_online_change_send_area.setText(updateActBean.getUp_location());
				//保存发货地址数据
				SharedPreferenceHelper.saveSendCity(getActivity(), updateActBean.getUp_location(), "");
				
			} else {
				create_act_online_change_send_area.setText("");
			}
			
		} else {
			create_act_online_change_send_area.setText(SharedPreferenceHelper.getSendCity(getActivity()).getCity_name());
		}
		
		create_act_online_change_send_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到选择城市(单选)
				Intent single_intent = new Intent(getActivity(), SelectCityActivity.class);
				single_intent.putExtra("select_type", "single");
				startActivity(single_intent);
			}
		});
		
		if(StringUtils.isEmpty(SharedPreferenceHelper.getArriveCity(getActivity()).getCity_name())){
			
			List<CityBean> cityBeans = new ArrayList<CityBean>();
			
			if(updateActBean != null && updateActBean.getProvinceList() != null && updateActBean.getProvinceList().size() > 0){
				if(updateActBean.getProvinceList().get(0).getProCity_pro().equals("全国")){
					CityBean cityAllBean = new CityBean();
					cityAllBean.setCity_id("0");
					cityAllBean.setCity_name("全国");
					cityBeans.add(cityAllBean);
				} else {
					for(int i = 0; i < updateActBean.getProvinceList().size(); i++){
						ProvinceCityBean cityBean = updateActBean.getProvinceList().get(i);
						for(int j = 0; j < cityBean.getCityBeanList().size(); j++){
							cityBeans.add(cityBean.getCityBeanList().get(j));
						}
					}
				}
				
				
				StringBuffer sb_name = new StringBuffer();
				StringBuffer sb_id = new StringBuffer();
				for(int i = 0; i < cityBeans.size(); i++){
					if(i == cityBeans.size() - 1){
						sb_name.append(cityBeans.get(i).getCity_name());
						sb_id.append(cityBeans.get(i).getCity_id());
					} else {
						sb_name.append(cityBeans.get(i).getCity_name()).append(",");
						sb_id.append(cityBeans.get(i).getCity_id()).append(",");
					}
				}
				
				SharedPreferenceHelper.saveArriveCity(getActivity(), sb_name.toString(), sb_id.toString());
				
				create_act_online_change_arrive_area.setText(sb_name.toString());
			} else {
				create_act_online_change_arrive_area.setText("");
			}
			
		} else {
			create_act_online_change_arrive_area.setText(SharedPreferenceHelper.getArriveCity(getActivity()).getCity_name());
		}
		create_act_online_change_arrive_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到选择城市(多选)
				Intent mult_intent = new Intent(getActivity(), SelectCityActivity.class);
				startActivity(mult_intent);
			}
		});
		
		
		if(create_act_online_change_send_area.getText().toString().trim().equals(getResources().getString(R.string.bj)) 
				&& create_act_online_change_arrive_area.getText().toString().trim().equals(getResources().getString(R.string.bj))){
			if(updateActBean != null && updateActBean.getUp_dispatchingType().equals("1")){
				create_act_online_change_send_type.setText(R.string.create_act_send_tootoo);
				send_type = "1";
				create_act_online_change_send_arrow.setImageResource(R.drawable.icon_arrow_down);
				create_act_online_change_other_send_type_layout.setVisibility(View.GONE);
				create_act_third_step_send_type_layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//选择配送方式
						List<String> sendTypeList = new ArrayList<String>();
						sendTypeList.add(getResources().getString(R.string.create_act_send_store));
						sendTypeList.add(getResources().getString(R.string.create_act_send_tootoo));
						selectSendType(v, sendTypeList);
					}
				});
				
			} else {
				
				create_act_online_change_send_type.setText(R.string.create_act_send_store);
				send_type = "0";
				create_act_online_change_send_arrow.setImageResource(R.drawable.icon_arrow_down);
				create_act_online_change_other_send_type_layout.setVisibility(View.VISIBLE);
				if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_expressName())){
					create_act_online_change_other_send_type_et.setText(updateActBean.getUp_expressName());
				} else {
					create_act_online_change_other_send_type_et.setText("");
				}
				
			}
			
			
		} else {
			create_act_online_change_send_type.setText(R.string.create_act_send_store);
			send_type = "0";
			create_act_online_change_send_arrow.setImageResource(R.drawable.icon_arrow_down);
			create_act_online_change_other_send_type_layout.setVisibility(View.VISIBLE);
			
			if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_expressName())){
				create_act_online_change_other_send_type_et.setText(updateActBean.getUp_expressName());
			} else {
				create_act_online_change_other_send_type_et.setText("");
			}
		}
		
	}
	
	
	
	/**
	 * 选择配送方式
	 * @param view
	 * @param list
	 */
	private void selectSendType(final View view, final List<String> list){
		final PopupWindow popupWindow = new PopupWindow(getActivity());
		View pw_view = LayoutInflater.from(getActivity()).inflate(R.layout.create_act_wish_pw, null);
		
		create_act_online_change_send_arrow.setImageResource(R.drawable.icon_arrow_up);
		
		popupWindow.setContentView(pw_view);
		//窗口的宽带和高度根据情况定义
		popupWindow.setWidth(view.getWidth());
		/**购物车每个item布局**/
        View pw_item_view = LayoutInflater.from(getActivity()).inflate(R.layout.create_act_down_item, null);
        LinearLayout create_act_down_item_layout = (LinearLayout)pw_item_view.findViewById(R.id.create_act_down_item_layout);
        LinearLayout.LayoutParams pw_item_layoutParams = (LinearLayout.LayoutParams)create_act_down_item_layout.getLayoutParams();
        int pw_item_height = pw_item_layoutParams.height;
        
        ListView create_act_wish_pw_lv = (ListView)pw_view.findViewById(R.id.create_act_wish_pw_lv);
		
		popupWindow.setHeight(pw_item_height * list.size() + create_act_wish_pw_lv.getDividerHeight() * (list.size() - 1));
		
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				create_act_online_change_send_arrow.setImageResource(R.drawable.icon_arrow_down);
			}
		});
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_corners_shape_drawable));
		
//		//窗口进入和退出的效果,如果不设置默认有进入和退出效果
//		popupWindow.setAnimationStyle(R.style.win_ani_right_top_menu);
		int[] location = new int[2];

	    view.getLocationOnScreen(location);
		//位置可以按要求定义
		popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] + view.getHeight() + 2);
		
		create_act_wish_pw_lv.setAdapter(new CreateActTypeLvAdapter(getActivity(), list));
		create_act_wish_pw_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				popupWindow.dismiss();
				create_act_online_change_send_type.setText(list.get(arg2));
				send_type = String.valueOf(arg2);
				
				updateActBean.setUp_dispatchingType(send_type);
				
				if(create_act_online_change_send_type.getText().toString().trim().equals(getResources().getString(R.string.create_act_send_tootoo))){
					create_act_online_change_other_send_type_layout.setVisibility(View.GONE);
				} else {
					create_act_online_change_other_send_type_layout.setVisibility(View.VISIBLE);
				}
				
				
			}
		});
	}

	
	/**
	 * 设置时间
	 * @param time_tv
	 */
	private void selectTime(final TextView time_tv){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View timepickerview = inflater.inflate(R.layout.time_picker, null);
		ScreenInfo screenInfo = new ScreenInfo(getActivity());
		final WheelMain wheelMain = new WheelMain(timepickerview, true);
		wheelMain.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		String time = time_tv.getText().toString();
		if (JudgeDate.isDate(time, "yyyy-MM-dd HH:mm")) {
			try {
				calendar.setTime(dateFormat.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int h = calendar.getTime().getHours();
		int m = calendar.getTime().getMinutes();
		wheelMain.initDateTimePicker(year, month, day, h, m);
		TooSelectTimeDialog selectTimeDialog = new TooSelectTimeDialog(getActivity());
		selectTimeDialog.setDialogTitle(R.string.rainbo_hint);
		selectTimeDialog.setDialogView(timepickerview);
		selectTimeDialog.setTooTimeDialogCallBack(new TooTimeDialogCallBack() {
			
			@Override
			public void onTooTimeDialogSure() {
				time_tv.setText(wheelMain.getTime());
			}
			
			@Override
			public void onTooTimeDialogCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		selectTimeDialog.show();
	}
	
	
	/**
	 * 线下活动变化
	 */
	private void outlineLayout(){
		create_act_outline_change_start_time = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_start_time);
		if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_dateStart())){
			create_act_outline_change_start_time.setText(updateActBean.getUp_dateStart());
		} else {
			create_act_outline_change_start_time.setText("");
		}
		
		create_act_outline_change_start_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTime(create_act_outline_change_start_time);
			}
		});
		
		create_act_outline_change_end_time = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_end_time);
		if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_dateEnd())){
			create_act_outline_change_end_time.setText(updateActBean.getUp_dateEnd());
		} else {
			create_act_outline_change_end_time.setText("");
		}
		
		create_act_outline_change_end_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTime(create_act_outline_change_end_time);
			}
		});
		
		create_act_outline_change_store_name = (EditText)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_store_name);
		if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_supplierName())){
			create_act_outline_change_store_name.setText(updateActBean.getUp_supplierName());
		} else {
			create_act_outline_change_store_name.setText("");
		}
		
		//填写地址
		create_act_outline_change_address = (EditText)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_address);
		if(updateActBean != null && !TextUtils.isEmpty(updateActBean.getUp_place())){
			create_act_outline_change_address.setText(updateActBean.getUp_place());
		} else {
			create_act_outline_change_address.setText("");
		}
		
		
		create_act_outline_change_search = (TextView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_search);
		
		create_act_outline_change_bmapView = (MapView)create_act_third_change_view.getChildAt(0).findViewById(R.id.create_act_outline_change_bmapView);
		mBaiduMap = create_act_outline_change_bmapView.getMap();
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		if(!create_act_outline_change_address.getText().toString().trim().equals("") && create_act_outline_change_address.getText().toString().trim().length() > 3){
			mSearch.geocode(new GeoCodeOption().city(
					create_act_outline_change_address.getText().toString().trim().substring(0, 3)).address(
							create_act_outline_change_address.getText().toString().trim().substring(3)));
		}
		
		//搜索
		create_act_outline_change_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Geo搜索
				if(!create_act_outline_change_address.getText().toString().equals("") && create_act_outline_change_address.getText().toString().trim().length() > 3){
				mSearch.geocode(new GeoCodeOption().city(
						create_act_outline_change_address.getText().toString().substring(0, 3)).address(
								create_act_outline_change_address.getText().toString().substring(3)));
				}
			}
		});
	}
	
	
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(getActivity(), result.getAddress(),
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		SharedPreferenceHelper.clearArea(getActivity());
		
		if(create_act_outline_change_bmapView != null){
			create_act_outline_change_bmapView.onDestroy();
		}
		
		if(mSearch != null){
			mSearch.destroy();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		onlineLayout();
		
		if(create_act_outline_change_bmapView != null){
			create_act_outline_change_bmapView.onResume();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		if(create_act_outline_change_bmapView != null){
			create_act_outline_change_bmapView.onPause();
		}
	}
}
