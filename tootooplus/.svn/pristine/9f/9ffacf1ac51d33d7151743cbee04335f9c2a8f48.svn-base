package com.ninetowns.tootooplus.helper;

import java.util.List;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.PopupWindow;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.CityPostAresExpandAdapter;
import com.ninetowns.tootooplus.adapter.HomePageAdapter.HomePageAdatperHolder;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.HomePageBean;
import com.ninetowns.tootooplus.bean.PostAresBean;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.widget.expandableview.EdgeEffectExpandableListView;

/**
 * 
 * @ClassName: HomePagerCityHelper
 * @Description: 下拉地址的帮助类，显示popwindown
 * @author wuyulong
 * @date 2015-2-7 下午2:59:10
 * 
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class HomePagerCityHelper implements OnChildClickListener {
	private HomePageAdatperHolder homePageAdatperHolder;
	private HomePageBean homePageBean;
	private PopupWindow popWindowsub;
	private View postAresCity;
	private int position;
	private OnClickListenerItem onClickListener;
	private EdgeEffectExpandableListView listViewPostAResCity;
	private List<PostAresBean> postSresList;

	public HomePagerCityHelper(HomePageAdatperHolder homePageAdatperHolder,
			HomePageBean homePageBean, int position) {
		this.homePageAdatperHolder = homePageAdatperHolder;
		this.homePageBean = homePageBean;
		this.position = position;
		setPopwindowPostCitys();
	}

	/**
	 * 
	 * @Title: setPopwindowPostCitys
	 * @Description: 显示配送地址
	 * @param
	 * @return
	 * @throws
	 */
	public void setPopwindowPostCitys() {
		if (popWindowsub == null) {
			postAresCity = LayoutInflater.from(
					TootooPlusApplication.getAppContext()).inflate(
					R.layout.category_exspand_view, null);
			postAresCity.findViewById(R.id.rl_title_city).setVisibility(
					View.GONE);
			listViewPostAResCity = (EdgeEffectExpandableListView) postAresCity
					.findViewById(R.id.expandablelistview);
			listViewPostAResCity.setOnChildClickListener(this);
			listViewPostAResCity
					.setOnGroupExpandListener(new OnGroupExpandListener() {

						@Override
						public void onGroupExpand(int groupPosition) {

							for (int i = 0; i < postSresList.size(); i++) {
								if (groupPosition != i) {
									listViewPostAResCity.collapseGroup(i);
								}
							}
							if (onClickListener != null) {
								onClickListener
										.onListenerItemPosition(groupPosition);
							}

						}
					});

			int width = CommonUtil.getWidth(TootooPlusApplication
					.getAppContext());
			int height = (int) (width * 0.75);
			popWindowsub = new PopupWindow(
					postAresCity,
					CommonUtil.getWidth(TootooPlusApplication.getAppContext()),
					height);
			popWindowsub.setContentView(postAresCity);
			// 使其聚集
			popWindowsub.setFocusable(true);
			// 设置允许在外点击消失
			popWindowsub.setOutsideTouchable(true);
			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popWindowsub.setBackgroundDrawable(new BitmapDrawable());
			popWindowsub.setAnimationStyle(R.style.win_ani_grow_from);
			Rect rect = CommonUtil.getRect(homePageAdatperHolder.mCTUpLineCity);
			int centerx = rect.centerX() - popWindowsub.getWidth() / 2;
			int centery = rect.centerY() + 15;
			initExpandsAdapter();
			popWindowsub.showAtLocation(homePageAdatperHolder.mCTUpLineCity,
					Gravity.NO_GRAVITY, centerx, centery);
		}

	}

	/**
	 * 
	 * @Title: initExpandsAdapter
	 * @Description: 初始化可扩展的adapter
	 */
	private void initExpandsAdapter() {
		postSresList = homePageBean.getPostAresList();
		if (postSresList != null && postSresList.size() > 0) {
			CityPostAresExpandAdapter cityExpandAdapter = new CityPostAresExpandAdapter(
					postSresList, position);
			listViewPostAResCity.setAdapter(cityExpandAdapter);
			int width = CommonUtil.getWidth(TootooPlusApplication
					.getAppContext());

			if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
				listViewPostAResCity.setIndicatorBounds(width - 40, width - 10);
			} else {
				listViewPostAResCity.setIndicatorBoundsRelative(width - 40,
						width - 10);
			}

			setOnClickItemListener(cityExpandAdapter);
			cityExpandAdapter.notifyDataSetChanged();
		} else {
			ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
					"没有数据");
		}

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public interface OnClickListenerItem {
		public void onListenerItemPosition(int position);
	}

	public void setOnClickItemListener(OnClickListenerItem listener) {
		this.onClickListener = listener;

	}
}
