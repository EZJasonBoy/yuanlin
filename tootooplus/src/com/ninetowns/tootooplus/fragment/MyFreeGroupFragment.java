package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.activity.JoinMemberActivity;
import com.ninetowns.tootooplus.bean.FreeGroupBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.MyFreeGroupParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyFreeGroupFragment extends PageListFragment<ListView, List<FreeGroupBean>, MyFreeGroupParser> {

	// 总页数
	private int totalPage;
	
	private RefreshableListView free_group_lv;
	

	private List<FreeGroupBean> freeGroupList = new ArrayList<FreeGroupBean>();
	
	private FreeGroupLvAdapter freeGroupLvAdapter = null;
	
	private String group_type = "";
	public MyFreeGroupFragment() {
		// TODO Auto-generated constructor stub
	}
	public MyFreeGroupFragment(String group_type){
		this.group_type = group_type;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onLoadData(true, true, false);
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.my_free_group_fragment, null);
		
		free_group_lv = (RefreshableListView)view.findViewById(R.id.free_group_lv);
		
		return view;
	}
	
	@Override
	public void getPageListParserResult(List<FreeGroupBean> parserResult) {
		// TODO Auto-generated method stub
		if(super.currentpage == 1){
			freeGroupList.clear();
		}
		freeGroupList.addAll(parserResult);
		
		freeGroupLvAdapter = new FreeGroupLvAdapter(getActivity(), freeGroupList);
		
		free_group_lv.setAdapter(freeGroupLvAdapter);
		
		if(super.currentpage != 1){
			free_group_lv.getRefreshableView().setSelection(freeGroupList.size());
		}
		
		
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		// TODO Auto-generated method stub
		return free_group_lv;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public MyFreeGroupParser setParser(String str) {
		// TODO Auto-generated method stub
		MyFreeGroupParser myFreeGroupParser = new MyFreeGroupParser(str);

		totalPage = myFreeGroupParser.getTotalPage();
		
		return myFreeGroupParser;
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.MY_FREE_GROUP_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.MY_FREE_GROUP_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.MY_FREE_GROUP_PAGE_SIZE, String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.MY_FREE_GROUP_PAGE, String.valueOf(currentpage));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.MY_FREE_GROUP_TYPE, group_type);
		return requestParamsNet;
	}

	
	class FreeGroupLvAdapter extends BaseAdapter{
		
		private Context context;
		
		private List<FreeGroupBean> list;
		
		public FreeGroupLvAdapter(Context context, List<FreeGroupBean> list){
			
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
				convertView = LayoutInflater.from(context).inflate(R.layout.free_group_lv_item, null);
				vh.free_group_item_layout = (LinearLayout)convertView.findViewById(R.id.free_group_item_layout);
				vh.free_group_item_iv = (ImageView)convertView.findViewById(R.id.free_group_item_iv);
				vh.free_group_item_tv = (TextView)convertView.findViewById(R.id.free_group_item_tv);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
			
			ImageLoader.getInstance().displayImage(list.get(position).getFree_group_coverThumb(), vh.free_group_item_iv, CommonUtil.OPTIONS_ALBUM);
			if(list.get(position) != null){
				vh.free_group_item_tv.setText(list.get(position).getFree_group_actName() + "(" + list.get(position).getFree_group_mumberCount() + ")");
			} else {
				vh.free_group_item_tv.setText("");
			}
			
			vh.free_group_item_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//已招募团员
					Intent member_intent = new Intent(getActivity(), JoinMemberActivity.class);
					member_intent.putExtra("group_id", list.get(position).getFree_group_id());
					member_intent.putExtra("act_id", list.get(position).getFree_group_actId());
					member_intent.putExtra("act_name", list.get(position).getFree_group_actName());
					//"0"为未报名; "1"为已报名，待审核; "-1"已报名，但审核失败; "2"为被选中
					member_intent.putExtra("group_status", list.get(position).getFree_group_status());
					member_intent.putExtra("act_type", list.get(position).getFreee_group_OnLineStatus());
					member_intent.putExtra("group_countParticipant", list.get(position).getFree_group_countParticipant());
					member_intent.putExtra("act_isEnd", list.get(position).getFree_group_isEnd());
					
					startActivity(member_intent);
					
				}
			});
			
			vh.free_group_item_iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent act_intent = new Intent(getActivity(), ActivityDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString(TootooeNetApiUrlHelper.ACTIVITYID, list.get(position).getFree_group_actId());
					bundle.putString("currentPosition", "0");
					act_intent.putExtra(TootooeNetApiUrlHelper.BUNDLE, bundle);
					startActivity(act_intent);
				}
			});
			
			return convertView;
		}
		
		
		class ViewHolder{
			ImageView free_group_item_iv;
			
			TextView free_group_item_tv;
			
			LinearLayout free_group_item_layout;
		}
	}
}
