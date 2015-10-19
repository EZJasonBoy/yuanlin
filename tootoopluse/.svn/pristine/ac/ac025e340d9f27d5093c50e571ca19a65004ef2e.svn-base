package com.ninetowns.tootoopluse.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.HistoryAdapter;
import com.ninetowns.tootoopluse.bean.ActivityHistoryBean;
import com.ninetowns.tootoopluse.bean.HistoryBean;
/**
 * 
* @ClassName: HistoryFragmentDialog 
* @Description: 历史记录
* @author wuyulong
* @date 2015-4-11 上午11:06:25 
*
 */
public class HistoryFragmentDialog extends Fragment implements
		OnClickListener,OnItemClickListener {
	private View historyView;
	@ViewInject(R.id.history_list)
	private ListView mListView;// 列表
	
	@ViewInject(R.id.ll_history_view)
	private LinearLayout mLLHishtoryView;// 历史记录界面
	@ViewInject(R.id.ct_clear_history)
	private CheckedTextView mCTClearHistory;
	private String type;
	private HistoryAdapter historyAdapter;
	private List<HistoryBean> listHistory;
	private View mFLHistory;
	private View mRefresh;
	private List<ActivityHistoryBean> activityHistory;


	public HistoryFragmentDialog() {
	}

	public HistoryFragmentDialog(String type,View mFLHistory,View mRefresh) {
		this.type = type;
		this.mFLHistory=mFLHistory;
		this.mRefresh=mRefresh;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		historyView = inflater.inflate(R.layout.search_or_history, null);
		ViewUtils.inject(this, historyView);
		justWishOrActivityOrComment();
		setOnViewClickListener();
		return historyView;
	}

	/**
	 * 
	 * @Title: searchHistoryData
	 * @Description: 搜索是否有数据，
	 * @param
	 * @return
	 * @throws
	 */
	private void searchWishHistoryData() {
		DbUtils db = DbUtils.create(getActivity());
		if (db != null) {
			try {
				 listHistory = db.findAll(Selector
						.from(HistoryBean.class));
				if (listHistory != null && listHistory.size() > 0) {
					mLLHishtoryView.setVisibility(View.VISIBLE);
					historyAdapter = new HistoryAdapter(getActivity(),
							listHistory);
					mListView.setAdapter(historyAdapter);
					historyAdapter.notifyDataSetChanged();
					mRefresh.setVisibility(View.GONE);
					mFLHistory.setVisibility(View.VISIBLE);
				} else {
					mRefresh.setVisibility(View.VISIBLE);
					mFLHistory.setVisibility(View.GONE);
					mLLHishtoryView.setVisibility(View.INVISIBLE);
				}
			} catch (DbException e) {
				LogUtil.error("db", "数据库出现异常");
				e.printStackTrace();
			}
		} else {
			mLLHishtoryView.setVisibility(View.INVISIBLE);
			mRefresh.setVisibility(View.VISIBLE);
			mFLHistory.setVisibility(View.GONE);
			mLLHishtoryView.setVisibility(View.INVISIBLE);
			LogUtil.error("db", "=null");
		}

	}
	/**
	 * 
	* @Title: searchActivityHistoryData 
	* @Description: 搜索活动
	* @param  
	* @return   
	* @throws
	 */
	private void searchActivityHistoryData() {
		DbUtils db = DbUtils.create(getActivity());
		if (db != null) {
			try {
				 activityHistory = db.findAll(Selector
						.from(ActivityHistoryBean.class));
				if (activityHistory != null && activityHistory.size() > 0) {
					mLLHishtoryView.setVisibility(View.VISIBLE);
					historyAdapter = new HistoryAdapter(getActivity(),
							activityHistory);
					mListView.setAdapter(historyAdapter);
					historyAdapter.notifyDataSetChanged();
					mRefresh.setVisibility(View.GONE);
					mFLHistory.setVisibility(View.VISIBLE);
				} else {
					mRefresh.setVisibility(View.VISIBLE);
					mFLHistory.setVisibility(View.GONE);
					mLLHishtoryView.setVisibility(View.INVISIBLE);
				}
			} catch (DbException e) {
				LogUtil.error("db", "数据库出现异常");
				e.printStackTrace();
			}
		} else {
			mLLHishtoryView.setVisibility(View.INVISIBLE);
			mRefresh.setVisibility(View.VISIBLE);
			mFLHistory.setVisibility(View.GONE);
			mLLHishtoryView.setVisibility(View.INVISIBLE);
			LogUtil.error("db", "=null");
		}

	}
	private void setOnViewClickListener() {
	
		mCTClearHistory.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	/**
	 * 
	 * @Title: justWishOrActivityOrComment
	 * @Description: 判断搜索类型
	 * @param
	 * @return
	 * @throws
	 */
	private void justWishOrActivityOrComment() {
		if (!TextUtils.isEmpty(type)) {
			if (type.equals("isActivity")) {
				searchActivityHistoryData();
			} else if (type.equals("isWish")) {
				searchWishHistoryData();
			} else if (type.equals("isComment")) {

			} else {
				LogUtil.error("您传的类型错误", "type错误");
			}

		} else {
			LogUtil.error("您还没有传类型", "type=null");
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	
		case R.id.ct_clear_history:

			if (!TextUtils.isEmpty(type)) {
				if (type.equals("isActivity")) {
					clearActivityHistory();
				} else if (type.equals("isWish")) {
					clearWishHistory();
				} else if (type.equals("isComment")) {
					clearCommentHistory();
				} else {
					LogUtil.error("您传的类型错误", "type错误");
				}

			} else {
				LogUtil.error("您还没有传类型", "type=null");
			}

		
			
			
			break;

		default:
			break;
		}

	}

	/** 
	* @Title: clearWishHistory 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void clearWishHistory() {
		DbUtils db=DbUtils.create(getActivity());
		if(db!=null){
//				=db.deleteAll(Selector.from(HistoryBean.class));
//				 listHistory = db.findAll(Selector
//							.from(HistoryBean.class));
			if(listHistory!=null){
				try {
					db.deleteAll(listHistory);
					 listHistory = db.findAll(Selector
								.from(HistoryBean.class));
				} catch (DbException e) {
					e.printStackTrace();
					LogUtil.error("删除失败", "");
				}
				if(listHistory!=null&&listHistory.size()>0){
					mLLHishtoryView.setVisibility(View.VISIBLE);
				}else{
					mLLHishtoryView.setVisibility(View.INVISIBLE);
				}
			
			}else{
				LogUtil.error("没有历史记录", "");
			}
			
			
		}else{
			
		}
	}
	private void clearActivityHistory() {
		DbUtils db=DbUtils.create(getActivity());
		if(db!=null){
//				=db.deleteAll(Selector.from(HistoryBean.class));
//				 listHistory = db.findAll(Selector
//							.from(HistoryBean.class));
			if(activityHistory!=null){
				try {
					db.deleteAll(activityHistory);
					activityHistory = db.findAll(Selector
								.from(HistoryBean.class));
				} catch (DbException e) {
					e.printStackTrace();
					LogUtil.error("删除失败", "");
				}
				if(activityHistory!=null&&activityHistory.size()>0){
					mLLHishtoryView.setVisibility(View.VISIBLE);
				}else{
					mLLHishtoryView.setVisibility(View.INVISIBLE);
				}
			
			}else{
				LogUtil.error("没有历史记录", "");
			}
			
			
		}else{
			
		}
	}
	private void clearCommentHistory() {
		DbUtils db=DbUtils.create(getActivity());
		if(db!=null){
//				=db.deleteAll(Selector.from(HistoryBean.class));
//				 listHistory = db.findAll(Selector
//							.from(HistoryBean.class));
			if(activityHistory!=null){
				try {
					db.deleteAll(activityHistory);
					activityHistory = db.findAll(Selector
								.from(HistoryBean.class));
				} catch (DbException e) {
					e.printStackTrace();
					LogUtil.error("删除失败", "");
				}
				if(activityHistory!=null&&activityHistory.size()>0){
					mLLHishtoryView.setVisibility(View.VISIBLE);
				}else{
					mLLHishtoryView.setVisibility(View.INVISIBLE);
				}
			
			}else{
				LogUtil.error("没有历史记录", "");
			}
			
			
		}else{
			
		}
	}

	/** 
	* @Title: searchHistory 
	* @Description: 搜索活动历史记录
	* @param  
	* @return   
	* @throws 
	*/
	private void searchActHistory(String strName) {
		if (!TextUtils.isEmpty(strName)) {
			DbUtils dbUtils = DbUtils.create(getActivity());
		
			HistoryBean history = new HistoryBean();
			history.setHistoryName(strName);
			try {
				ActivityHistoryBean searHistoryBean = dbUtils.findFirst(Selector.from(ActivityHistoryBean.class).where("historyName","=",strName));
				if(searHistoryBean!=null){
					dbUtils.delete(searHistoryBean);
					dbUtils.save(history);
				}else{
					dbUtils.save(history);
				}
				
				
				
			} catch (DbException e) {
				e.printStackTrace();
			}

			if (onSearchListener != null) {
				onSearchListener.OnSearchListener(strName);
			}
			mFLHistory.setVisibility(View.GONE);

		} else {
			ComponentUtil.showToast(getActivity(), "请输入搜索内容");
		}
	}
	private void searchHistory(String strName) {
		if (!TextUtils.isEmpty(strName)) {
			DbUtils dbUtils = DbUtils.create(getActivity());
		
			HistoryBean history = new HistoryBean();
			history.setHistoryName(strName);
			try {
				HistoryBean searHistoryBean = dbUtils.findFirst(Selector.from(HistoryBean.class).where("historyName","=",strName));
				if(searHistoryBean!=null){
					dbUtils.delete(searHistoryBean);
					dbUtils.save(history);
				}else{
					dbUtils.save(history);
				}
				
				
				
			} catch (DbException e) {
				e.printStackTrace();
			}

			if (onSearchListener != null) {
				onSearchListener.OnSearchListener(strName);
			}
			mFLHistory.setVisibility(View.GONE);

		} else {
			ComponentUtil.showToast(getActivity(), "请输入搜索内容");
		}
	}
	/**
	 * 
	 * @ClassName: OnSearchListener
	 * @Description: 搜索监听
	 * @author wuyulong
	 * @date 2015-4-11 上午10:45:37
	 * 
	 */
	public interface OnSearchListener {
		public void OnSearchListener(String name);

	}

	public void setOnSearchListener(OnSearchListener onSearchListener) {
		this.onSearchListener = onSearchListener;
	}

	private OnSearchListener onSearchListener;


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!TextUtils.isEmpty(type)) {
			if (type.equals("isActivity")) {
				if(activityHistory!=null&&activityHistory.size()>0){
					HistoryBean item = activityHistory.get(position);
					String storyName=item.getHistoryName();
					if(!TextUtils.isEmpty(storyName)){
						searchActHistory(storyName);
					}
					
				}
			} else if (type.equals("isWish")) {
				if(listHistory!=null&&listHistory.size()>0){
					HistoryBean item = listHistory.get(position);
					String storyName=item.getHistoryName();
					if(!TextUtils.isEmpty(storyName)){
						searchHistory(storyName);
					}
					
				}
			} else if (type.equals("isComment")) {

			} else {
				LogUtil.error("您传的类型错误", "type错误");
			}

		} else {
			LogUtil.error("您还没有传类型", "type=null");
		}
		
			
		
	}

}
