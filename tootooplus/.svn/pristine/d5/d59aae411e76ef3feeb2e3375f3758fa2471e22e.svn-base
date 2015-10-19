package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.activity.WishDetailActivity;
import com.ninetowns.tootooplus.bean.RemarkStoryBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.RemarkStoryParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.WrapRatingBar;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.RefreshableListView;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 点评故事
 * @author huangchao
 *
 */
public class RemarkStoryFragment extends PageListFragment<ListView, List<RemarkStoryBean>, RemarkStoryParser> implements OnItemClickListener {
	
	private RefreshableListView remark_story_lv;
	// 总页数
	private int totalPage;
	
	private String userId = "";
	
	private RemarkLvAdapter remarkLvAdapter;
	
	private List<RemarkStoryBean> remarkStoryList = new ArrayList<RemarkStoryBean>();
	
	private View per_home_head_top;
	
	private View per_home_head_next;
	
	private LinearLayout per_home_change_layout;
	public RemarkStoryFragment() {
		// TODO Auto-generated constructor stub
	}
	public RemarkStoryFragment(String userId, View per_home_head_top, View per_home_head_next, LinearLayout per_home_change_layout){
		this.userId = userId;
		
		this.per_home_head_top = per_home_head_top;
		
		this.per_home_head_next = per_home_head_next;
		
		this.per_home_change_layout = per_home_change_layout;
	}
	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.remark_story_fragment, null);
		
		remark_story_lv = (RefreshableListView)view.findViewById(R.id.remark_story_lv);
		remark_story_lv.setOnItemClickListener(this);
		if(per_home_head_top!=null){
			setHeaderView();
		}
		
		return view;
	}
	/** 
	* @Title: setHeaderView 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void setHeaderView() {
		remark_story_lv.getRefreshableView().addHeaderView(per_home_head_top);
		remark_story_lv.getRefreshableView().addHeaderView(per_home_head_next);
		
		remark_story_lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem > 1){
					per_home_change_layout.setVisibility(View.VISIBLE);
				} else if(firstVisibleItem < 4){
					per_home_change_layout.setVisibility(View.GONE);
				}
				
			}
		});
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onLoadData(true, true, false);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void getPageListParserResult(List<RemarkStoryBean> parserResult) {
		// TODO Auto-generated method stub
			
		if(super.currentpage == 1){
			remarkStoryList.clear();
			if(parserResult == null || parserResult.size() == 0){
				remark_story_lv.setAdapter(null);
				return;
			}
		}
		if(parserResult!=null){

			remarkStoryList.addAll(parserResult);
			
			setFreeAdapter(remark_story_lv,remarkStoryList);
			
			
			if(super.currentpage != 1){
				remark_story_lv.getRefreshableView().setSelection(remarkStoryList.size()-parserResult.size()+1);
			}
		}
			
	}
	/** 
	* @Title: setFreeAdapter 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	protected void setFreeAdapter(RefreshableListView remark_story_lv,List<RemarkStoryBean> parserResult) {
		remarkLvAdapter = new RemarkLvAdapter(getActivity(), parserResult);
		
		remark_story_lv.setAdapter(remarkLvAdapter);
	}

	@Override
	protected PullToRefreshBase<ListView> initRefreshIdView() {
		// TODO Auto-generated method stub
		
		return remark_story_lv;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public RemarkStoryParser setParser(String str) {
		
		RemarkStoryParser remarkStoryParser = new RemarkStoryParser(str);
		
		totalPage = remarkStoryParser.getTotalPage();
		
		return remarkStoryParser;
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.REMARK_STORY_URL);
		if(!TextUtils.isEmpty(userId)){
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REMARK_STORY_USERID, userId);
		}
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REMARK_STORY_PAGESIZE, String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.REMARK_STORY_PAGE, String.valueOf(currentpage));
		setDiffientPar(requestParamsNet);
		return requestParamsNet;
	}
	/**
	 * 
	* @Title: setDiffientPar 
	* @Description: 设置不同的参数,子类可以覆盖
	* @param  
	* @return   
	* @throws
	 */
	protected void  setDiffientPar(RequestParamsNet requestParamsNet){
		
	}
	//适配器
	class RemarkLvAdapter extends BaseAdapter{
		
		private Context context;
		
		private List<RemarkStoryBean> list;
		
		public RemarkLvAdapter(Context context, List<RemarkStoryBean> list){
			
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
			RemarkStoryBean item = list.get(position);
			ViewHolder vh = null;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.remark_story_lv_item, null);
				
				vh.remark_story_item_user_logo = (ImageView)convertView.findViewById(R.id.remark_story_item_user_logo);
				vh.remark_story_item_name = (TextView)convertView.findViewById(R.id.remark_story_item_name);
				vh.remark_story_item_vip = (ImageView)convertView.findViewById(R.id.remark_story_item_vip);
				vh.remark_story_item_like = (TextView)convertView.findViewById(R.id.remark_story_item_like);
				vh.remark_story_item_cover = (ImageView)convertView.findViewById(R.id.remark_story_item_cover);
				vh.remark_story_item_story_name = (TextView)convertView.findViewById(R.id.remark_story_item_story_name);
				vh.remark_story_item_ratbar = (WrapRatingBar)convertView.findViewById(R.id.remark_story_item_ratbar);
				vh.remark_story_item_act_name = (TextView)convertView.findViewById(R.id.remark_story_item_act_name);
				
				vh.remark_story_item_layout = (LinearLayout)convertView.findViewById(R.id.remark_story_item_layout);
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
			
			if(!TextUtils.isEmpty(item.getRemark_logoUrl())){
				ImageLoader.getInstance().displayImage(item.getRemark_logoUrl(), vh.remark_story_item_user_logo, CommonUtil.OPTIONS_HEADPHOTO);
			}
			
			if(!TextUtils.isEmpty(item.getRemark_userName())){
				vh.remark_story_item_name.setText(item.getRemark_userName());
			} else {
				vh.remark_story_item_name.setText("");
			}
			
			
			if(!TextUtils.isEmpty(item.getRemark_userGrade())){
				CommonUtil.showVip(vh.remark_story_item_vip, item.getRemark_userGrade());
			} else {
				vh.remark_story_item_vip.setVisibility(View.GONE);
			}
			
			if(!TextUtils.isEmpty(item.getRemark_countLike())){
				vh.remark_story_item_like.setText(item.getRemark_countLike());
			} else {
				vh.remark_story_item_like.setText("");
			}
			
			RelativeLayout.LayoutParams coverParams = (RelativeLayout.LayoutParams)vh.remark_story_item_cover.getLayoutParams();
			WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
			//屏幕宽度
			int screen_width_show = wm.getDefaultDisplay().getWidth();
			coverParams.width = screen_width_show;
			coverParams.height = screen_width_show * 2 / 3;
			vh.remark_story_item_cover.setLayoutParams(coverParams);
			
			if(!TextUtils.isEmpty(item.getRemark_coverThumb())){
				ImageLoader.getInstance().displayImage(item.getRemark_coverThumb(), vh.remark_story_item_cover, CommonUtil.OPTIONS_ALBUM_DETAIL);
			}
			
			if(!TextUtils.isEmpty(item.getRemark_storyName())){
				vh.remark_story_item_story_name.setText(item.getRemark_storyName());
			} else {
				vh.remark_story_item_story_name.setText("");
			}
			
			if(!TextUtils.isEmpty(item.getRemark_countRecommend())){
				vh.remark_story_item_ratbar.setRating(Float.parseFloat(item.getRemark_countRecommend()));
			}
			
			if(!TextUtils.isEmpty(item.getRemark_actName())){
				vh.remark_story_item_act_name.setText("#" + item.getRemark_actName() + "#");
			} else {
				vh.remark_story_item_act_name.setText("");
			}
			vh.remark_story_item_act_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					Intent intent = new Intent(context, ActivityDetailActivity.class);
					bundle.putString("activity", ConstantsTooTooEHelper.OPEN_COMMENT_LIST);
					bundle.putString(TootooeNetApiUrlHelper.STORYID,list.get(position).getWishStoryId());
					bundle.putString(ConstantsTooTooEHelper.USERID, list.get(position).getRemark_userId());
					bundle.putString(ConstantsTooTooEHelper.ACTIVITYID, list.get(position).getRemark_actId());
					bundle.putInt("currentPosition", 0);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					context.startActivity(intent);

				}
			});
			
			vh.remark_story_item_layout.setTag(item);
			
			return convertView;
		}
		
		class ViewHolder{
			//用户头像
			ImageView remark_story_item_user_logo;
			//用户昵称
			TextView remark_story_item_name;
			//vip等级
			ImageView remark_story_item_vip;
			//喜欢数
			TextView remark_story_item_like;
			//故事封面图
			ImageView remark_story_item_cover;
			//故事名称
			TextView remark_story_item_story_name;
			//推荐购买指数
			WrapRatingBar remark_story_item_ratbar;
			//活动标题
			TextView remark_story_item_act_name;
			
			LinearLayout remark_story_item_layout;
		}
		
	}
	/**
	 * 
	* @Title: setOnItemClickToDetail 
	* @Description: 条目点击事件
	* @param  
	* @return   
	* @throws
	 */
	protected void setOnItemClickToDetail(RemarkStoryBean vTag){
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {	
		if(remarkStoryList!=null&&remarkStoryList.size()>0){
				onItemClickToSkip(view, position, remarkStoryList);
			}else{
				LogUtil.error("数据异常", "parserResult=null");
			}
			
	}
	/**
	 * 
	* @Title: onItemClickToSkip 
	* @Description: 条目点击事件
	* @param  
	* @return   
	* @throws
	 */
	protected void onItemClickToSkip(View view, int position,
			List<RemarkStoryBean> remarkStoryList2) {

		if (position != -1&&position-3>=0) {
			RemarkStoryBean vTag = remarkStoryList2.get(position - 3);
			Intent intentToDetail = new Intent(getActivity(),
					WishDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(ConstantsTooTooEHelper.USERID,
					vTag.getRemark_userId());
			bundle.putString(ConstantsTooTooEHelper.STORYID,
					vTag.getRemark_storyId());
			intentToDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intentToDetail.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			startActivity(intentToDetail);
		}
	
	}
	
}
