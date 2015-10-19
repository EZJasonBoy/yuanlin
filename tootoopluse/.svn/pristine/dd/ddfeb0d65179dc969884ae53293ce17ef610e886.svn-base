package com.ninetowns.tootoopluse.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.CityBean;
import com.ninetowns.tootoopluse.bean.ProvinceCityBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.SelectCityParser;
import com.ninetowns.ui.fragment.BaseFragment;

public class SelectCityFragment extends BaseFragment<List<ProvinceCityBean>, SelectCityParser> {
	
	private ExpandableListView select_city_ex_lv;
	
	private List<ProvinceCityBean> proCityList = new ArrayList<ProvinceCityBean>();
	
	private String select_type = "";
	
	private SelectAreaLvAdapter selectAreaLvAdapter;
	
	private Map<Integer, Map<Integer, CityBean>> selectMap = new HashMap<Integer, Map<Integer, CityBean>>();
	
	//全选checkbox
	private CheckBox select_all_city_cb;
	
	//选中所有人的那个CheckBox点击的次数
	private int clickTime = 0;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		 
		View view = inflater.inflate(R.layout.select_city_fragment, null);
		
		select_type = getActivity().getIntent().getStringExtra("select_type");
		//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)view.findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		
		TextView two_or_one_btn_head_title = (TextView)view.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.select_city_title);
		
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)view.findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//确定
				Map<Integer, Map<Integer, CityBean>> map = selectAreaLvAdapter.totalMap;
				List<CityBean> selectList = new ArrayList<CityBean>();
				for(int i = 0; i < selectAreaLvAdapter.getGroupCount(); i++){
					if(map.get(i) != null){
						for(int j = 0; j < selectAreaLvAdapter.getChildrenCount(i); j++){
							if(map.get(i).get(j) != null){
LogUtil.systemlogInfo("++++selectCityFragment++++++map.get(i).get(j)+++", map.get(i).get(j));
								
								selectList.add(map.get(i).get(j));
							}
						}
					}
				}
				
				
				if(selectList.size() == 0){
					ComponentUtil.showToast(getActivity(), getResources().getString(R.string.select_city_empty));
				} else {
					if(select_type != null){
						SharedPreferenceHelper.saveSendCity(getActivity(), selectList.get(0).getCity_name(), selectList.get(0).getCity_id());
					} else {
						if(select_all_city_cb.isChecked()){
							SharedPreferenceHelper.saveArriveCity(getActivity(), "全国", "0");
						} else {
							StringBuffer sb_name = new StringBuffer();
							StringBuffer sb_id = new StringBuffer();
							for(int i = 0; i < selectList.size(); i++){
								if(i == selectList.size() - 1){
									sb_name.append(selectList.get(i).getCity_name());
									sb_id.append(selectList.get(i).getCity_id());
								} else {
									sb_name.append(selectList.get(i).getCity_name()).append(",");
									sb_id.append(selectList.get(i).getCity_id()).append(",");
								}
							}
							
							SharedPreferenceHelper.saveArriveCity(getActivity(), sb_name.toString(), sb_id.toString());
						}
						
					}
				}
				
				getActivity().finish();
				
			}
		});
		
		TextView two_or_one_btn_head_second_tv = (TextView)view.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_tv.setText(R.string.rainbo_sure);
		
		select_city_ex_lv = (ExpandableListView)view.findViewById(R.id.select_city_ex_lv);
		
		return view;
	 }

	@Override
	public SelectCityParser setParser(String str) {
		// TODO Auto-generated method stub
		return new SelectCityParser(str);
	}

	@Override
	public void getParserResult(List<ProvinceCityBean> parserResult) {
		// TODO Auto-generated method stub
		if(parserResult != null){
			
			View head_view = LayoutInflater.from(getActivity()).inflate(R.layout.select_city_group, null);
			
			select_all_city_cb = (CheckBox)head_view.findViewById(R.id.select_city_group_cb);
			
			TextView select_city_group_cont = (TextView)head_view.findViewById(R.id.select_city_group_cont);
			select_city_group_cont.setText(parserResult.get(0).getProCity_pro());
			
			//去除第一个"全国"
			for(int i = 1; i < parserResult.size(); i++){
				proCityList.add(parserResult.get(i));
			}
			
			select_all_city_cb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					clickTime++;
					if(clickTime % 2 == 1){
						for(int i = 0; i < proCityList.size(); i++){
							Map<Integer, CityBean> childMap = new HashMap<Integer, CityBean>();
							for(int j = 0; j < proCityList.get(i).getCityBeanList().size(); j++){
								childMap.put(j, proCityList.get(i).getCityBeanList().get(j));
							}
							selectMap.put(i, childMap);
						}
						
					} else {
						selectMap.clear();
					}
					
					selectAreaLvAdapter.notifyDataSetChanged();
				}
			});
			
			ImageView select_city_arraw = (ImageView)head_view.findViewById(R.id.select_city_arraw);
			select_city_arraw.setVisibility(View.GONE);
			
			if(select_type == null){
				select_city_ex_lv.addHeaderView(head_view);
			}
			
			if(select_type != null){
				CityBean select_cityBean = SharedPreferenceHelper.getSendCity(getActivity());
				for(int i = 0; i < proCityList.size(); i++){
					for(int j = 0; j < proCityList.get(i).getCityBeanList().size(); j++){
						if(proCityList.get(i).getCityBeanList().get(j).getCity_id().equals(select_cityBean.getCity_id())){
							Map<Integer, CityBean> child_cityMap = new HashMap<Integer, CityBean>();
							child_cityMap.put(j, proCityList.get(i).getCityBeanList().get(j));
							selectMap.put(i, child_cityMap);
						}
					}
				}
				
			} else {
				CityBean select_cityBean = SharedPreferenceHelper.getArriveCity(getActivity());
				String select_cityId = select_cityBean.getCity_id();
				String[] select_ids = select_cityId.split(",");
				if(select_ids != null && select_ids.length > 0){
					//全国
					if(select_ids[0].equals("0")){
						for(int i = 0; i < proCityList.size(); i++){
							Map<Integer, CityBean> childMap = new HashMap<Integer, CityBean>();
							for(int j = 0; j < proCityList.get(i).getCityBeanList().size(); j++){
								childMap.put(j, proCityList.get(i).getCityBeanList().get(j));
							}
							selectMap.put(i, childMap);
						}
						//如果进入是全国的时候这个Checkbox要被选中
						clickTime = 1;
						select_all_city_cb.setChecked(true);
					} else {
						//只是部分被选中
						for(int i = 0; i < proCityList.size(); i++){
							Map<Integer, CityBean> childMap = new HashMap<Integer, CityBean>();
							for(int j = 0; j < proCityList.get(i).getCityBeanList().size(); j++){
								String cityId = proCityList.get(i).getCityBeanList().get(j).getCity_id();
								for(int x = 0; x < select_ids.length; x++){
									if(select_ids[x].equals(cityId)){
										childMap.put(j, proCityList.get(i).getCityBeanList().get(j));
									}
								}
							}
							if(childMap.size() > 0){
								selectMap.put(i, childMap);
							}
							
						}
						
						
					}
				}
				
			}
			
			selectAreaLvAdapter = new SelectAreaLvAdapter(getActivity(), proCityList, select_type, selectMap);
			select_city_ex_lv.setAdapter(selectAreaLvAdapter);
			
		}
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.SELECT_AREA_LIST_URL);
		return requestParamsNet;
	}
	
	
	//适配器
	class SelectAreaLvAdapter extends BaseExpandableListAdapter {
		
		private List<ProvinceCityBean> proCityList;
		
		private Context context;
		
		private String select_type;
		
		public Map<Integer, Map<Integer, CityBean>> totalMap;
		
		public SelectAreaLvAdapter(Context context, List<ProvinceCityBean> proCityList, String select_type, Map<Integer, Map<Integer, CityBean>> totalMap){
			
			this.context = context;
			
			this.proCityList = proCityList;
			
			this.select_type = select_type;
			
			this.totalMap = totalMap;
		}
		

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.select_city_child, null);
			}
			
			CheckBox select_city_child_cb = (CheckBox)convertView.findViewById(R.id.select_city_child_cb);
			
			TextView select_city_child_cont = (TextView)convertView.findViewById(R.id.select_city_child_cont);
			
			if(proCityList.get(groupPosition).getCityBeanList() != null && proCityList.get(groupPosition).getCityBeanList().size() > 0){
				select_city_child_cont.setText(proCityList.get(groupPosition).getCityBeanList().get(childPosition).getCity_name());
			}
			
			if(totalMap.get(groupPosition) != null && totalMap.get(groupPosition).get(childPosition) != null){
				select_city_child_cb.setChecked(true);
			} else {
				select_city_child_cb.setChecked(false);
			}
			
			select_city_child_cb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(select_type != null && select_type.equals("single")){
						totalMap.clear();
						Map<Integer, CityBean> cityMap = new HashMap<Integer, CityBean>();
						cityMap.put(childPosition, proCityList.get(groupPosition).getCityBeanList().get(childPosition));
						totalMap.put(groupPosition, cityMap);
						notifyDataSetChanged();
						
					} else {
						//多选
						if(totalMap.get(groupPosition) != null && totalMap.get(groupPosition).get(childPosition) != null){
							totalMap.get(groupPosition).remove(childPosition);
						} else {
							if(totalMap.get(groupPosition) != null){
								totalMap.get(groupPosition).put(childPosition, proCityList.get(groupPosition).getCityBeanList().get(childPosition));
							} else {
								Map<Integer, CityBean> cityMap = new HashMap<Integer, CityBean>();
								cityMap.put(childPosition, proCityList.get(groupPosition).getCityBeanList().get(childPosition));
								totalMap.put(groupPosition, cityMap);
							}
							
							
						}
						
						if(totalMap.size() == getGroupCount()){
							for(int i = 0; i < getGroupCount(); i++){
								if(totalMap.get(i).size() == getChildrenCount(i)){
									clickTime = 1;
									select_all_city_cb.setChecked(true);
								} else {
									clickTime = 0;
									select_all_city_cb.setChecked(false);
								}
							}
						} else {
							clickTime = 0;
							select_all_city_cb.setChecked(false);
						}
						
						notifyDataSetChanged();
					}
					
				}
			});
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			if(proCityList.get(groupPosition).getCityBeanList() != null && proCityList.get(groupPosition).getCityBeanList().size() > 0){
				return proCityList.get(groupPosition).getCityBeanList().size();
			} else {
				return 0;
			}
			
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			if(proCityList != null && proCityList.size() > 0){
				return proCityList.size();
			} else {
				return 0;
			}
			
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.select_city_group, null);
			} 
			
			final CheckBox select_city_group_cb = (CheckBox)convertView.findViewById(R.id.select_city_group_cb);
			
			TextView select_city_group_cont = (TextView)convertView.findViewById(R.id.select_city_group_cont);
			
			ImageView select_city_arraw = (ImageView)convertView.findViewById(R.id.select_city_arraw);
			if(isExpanded){
				select_city_arraw.setImageResource(R.drawable.down_press);
			} else {
				select_city_arraw.setImageResource(R.drawable.icon_arrow);
			}
			
			if(!TextUtils.isEmpty(proCityList.get(groupPosition).getProCity_pro())){
				select_city_group_cont.setText(proCityList.get(groupPosition).getProCity_pro());
			} else {
				select_city_group_cont.setText("");
			}
			
			if(totalMap.get(groupPosition) != null && 
					getChildrenCount(groupPosition) == totalMap.get(groupPosition).size()){
				select_city_group_cb.setChecked(true);
			} else {
				select_city_group_cb.setChecked(false);
			}
			
			select_city_group_cb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(select_type == null){
						//如果是多选
						if(totalMap.get(groupPosition) != null){
							if(totalMap.get(groupPosition).size() == getChildrenCount(groupPosition)){
								totalMap.remove(groupPosition);
							} else {
								totalMap.remove(groupPosition);
								Map<Integer, CityBean> cityMap = new HashMap<Integer, CityBean>();
								for(int i = 0; i < getChildrenCount(groupPosition); i++){
									cityMap.put(i, proCityList.get(groupPosition).getCityBeanList().get(i));
								}
								totalMap.put(groupPosition, cityMap);
							}
						} else {
							totalMap.remove(groupPosition);
							Map<Integer, CityBean> cityMap = new HashMap<Integer, CityBean>();
							for(int i = 0; i < getChildrenCount(groupPosition); i++){
								cityMap.put(i, proCityList.get(groupPosition).getCityBeanList().get(i));
							}
							totalMap.put(groupPosition, cityMap);
							
						}
						
						if(totalMap.size() == getGroupCount()){
							for(int i = 0; i < getGroupCount(); i++){
								if(totalMap.get(i).size() == getChildrenCount(i)){
									clickTime = 1;
									select_all_city_cb.setChecked(true);
								} else {
									clickTime = 0;
									select_all_city_cb.setChecked(false);
								}
							}
						} else {
							clickTime = 0;
							select_all_city_cb.setChecked(false);
						}
						notifyDataSetChanged();
					} else {
						notifyDataSetChanged();
					}
				}
			});
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}


}
