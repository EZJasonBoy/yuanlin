package com.ninetowns.tootoopluse.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.ActivityDetailBean;
import com.ninetowns.tootoopluse.bean.ActivityInfoBean;
import com.ninetowns.ui.widget.text.MarqueeTextView;
/**
 * 
* @ClassName: DownLineActivityFragment 
* @Description: 线下显示活动信息
* @author wuyulong
* @date 2015-3-13 上午11:22:16 
*
 */
public class DownLineActivityFragment extends Fragment  implements
OnGetGeoCoderResultListener{
	@ViewInject(R.id.ct_type_name)
	private CheckedTextView mCTType;//类型
	@ViewInject(R.id.ct_categray_name)
	private CheckedTextView mCTCateGory;//分类
	@ViewInject(R.id.ct_man_count)
	private CheckedTextView mCTCount;//人数
	@ViewInject(R.id.ct_business)
	private CheckedTextView mCTBuiness;//商人
	@ViewInject(R.id.ct_location)
	private CheckedTextView mCTLocation;//地点、

	@ViewInject(R.id.ct_sign_start_time)
	private MarqueeTextView mCTSignStartTime;//报名开始时间
	@ViewInject(R.id.ct_sign_end_time)
	private MarqueeTextView mCTSignEndTime;//报名结束时间
	
	@ViewInject(R.id.ct_activity_startime)
	private MarqueeTextView mCTACTStartTime;//活动开始时间
	@ViewInject(R.id.ct_activity_edntime)
	private MarqueeTextView mCTActEndTime;//活动结束时间
	@ViewInject(R.id.ll_bottom_dismiss)
	private LinearLayout mDismiss;//
	@ViewInject(R.id.bmapView)
	private MapView mMapView;//地图view图层
	
	
	private View myDownLineView;
	private ActivityDetailBean resultData;//活动详情的数据结构
	private View mActivityInfoDown;
	private BaiduMap mBaiduMap;
	private GeoCoder mSearch;
	public DownLineActivityFragment(ActivityDetailBean resultData,View mActivityInfoDown) {
		this.resultData=resultData;
		this.mActivityInfoDown=mActivityInfoDown;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myDownLineView=inflater.inflate(R.layout.downline_activity_fragment, null);
		ViewUtils.inject(this, myDownLineView);
		mBaiduMap = mMapView.getMap();

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		setData();
		return myDownLineView;
	}
	/**
	 * 
	* @Title: setData 
	* @Description: 设置数据
	* @param  
	* @return   
	* @throws
	 */
	private void setData() {
		ActivityInfoBean mActivityInfoBean = resultData.getActivityInfoBean();
		String category=mActivityInfoBean.getCategory();
		String type=mActivityInfoBean.getType();
		String countParticipant=mActivityInfoBean.getCountParticipant();
		String dateStart=mActivityInfoBean.getDateStart();
		String dateEnd=mActivityInfoBean.getDateEnd();
		String registerStart=mActivityInfoBean.getDateRegisterStart();
		String registerEnd=mActivityInfoBean.getDateRegisterEnd();
		String palaceMap=mActivityInfoBean.getPlaceMap();//活动地点来自地图
		String palce=mActivityInfoBean.getPlace();//活动地点
		String supplierName=mActivityInfoBean.getSupplierName();//商家
		
		mCTType.setText(type);
//		mCTCateGory.setTag(category);
		mCTCateGory.setText(category);
		mCTCount.setText(countParticipant);
		mCTBuiness.setText(supplierName);
		mCTSignStartTime.setText("报名开始:"+registerStart);
		mCTSignEndTime.setText("报名结束:"+registerEnd);
		mCTACTStartTime.setText("活动开始:"+dateStart);
		mCTActEndTime.setText("活动结束:"+dateEnd);
		mCTLocation.setText(palce);
//		if(!TextUtils.isEmpty(palaceMap)){
//			mSearch.geocode(new GeoCodeOption().city(palaceMap).address(palaceMap));
//		}else{
			try {
				if(!TextUtils.isEmpty(palce)){
					if(palce.length()>3){
						String city=palce.substring(0, 3);
						String address=palce.substring(3);
						mSearch.geocode(new GeoCodeOption().city(city).address(address));
					}
				
}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
	}
	@OnClick(R.id.ll_bottom_dismiss)
	public void dimissView(View v){
		mActivityInfoDown.setVisibility(View.GONE);
	}
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ComponentUtil.showToast(getActivity(), "抱歉，未能找到结果");
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
	}
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ComponentUtil.showToast(getActivity(), "抱歉，未能找到结果");
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));

	}
	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}
}
