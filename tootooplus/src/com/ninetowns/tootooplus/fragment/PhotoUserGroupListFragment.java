package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ActivityDetailActivity;
import com.ninetowns.tootooplus.adapter.GridViewGroupPhotoAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.GridViewGroupBean;
import com.ninetowns.tootooplus.fragment.FirstGuideLookGroupCountDialog.InOnClickListenerDismiss;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.PhotoGroupParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.fragment.PageListFragment;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase;
import com.ninetowns.ui.widget.refreshable.PullToRefreshBase.Mode;
import com.ninetowns.ui.widget.refreshable.RefreshableGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * 
 * @ClassName: PhotoUserDialogFragment
 * @Description: 白吃活动参与组团用户列表
 * @author wuyulong
 * @date 2015-2-5 下午4:20:56
 * 
 */
public class PhotoUserGroupListFragment extends
		PageListFragment<GridView, List<GridViewGroupBean>, PhotoGroupParser>
		implements View.OnClickListener, InOnClickListenerDismiss {
	private View mWishFragmentView;
	@ViewInject(R.id.gv_photo)
	private RefreshableGridView mWishRefreshGridView;// 刷新的GridView
	private GridView mGridView;
	private int totalPage;// 总页数
	private List<GridViewGroupBean> wishList = new ArrayList<GridViewGroupBean>();// 当前数据列表集合
	private View gridViewPhoto;
	private View viewDissMiss;
	@ViewInject(R.id.ll_group_view)
	private LinearLayout mDissMiss;
	@ViewInject(R.id.tv_group_count)
	private TextView mTVGroupCount;// 报名的组数
	private String activityId;
	private String countGroup;
	private FragmentTransaction beginTransation;

	public PhotoUserGroupListFragment(View viewDissMiss, String activityid,
			String countGroup, FragmentTransaction beginTransation) {
		this.viewDissMiss = viewDissMiss;
		this.activityId = activityid;
		this.countGroup = countGroup;
		this.beginTransation = beginTransation;

	}

	@Override
	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		gridViewPhoto = inflater.inflate(R.layout.gridview_group_photo, null);
		if (getActivity() != null) {

			boolean isFirst = SharedPreferenceHelper
					.getFirstGuideLookGoGroupCount(getActivity());
			if (isFirst) {
				FirstGuideLookGroupCountDialog dialog=(FirstGuideLookGroupCountDialog) CommonUtil.showFirstGuideDialog(getActivity(),
						ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GOGROUPCOUNT);
				dialog.setInOnClickListenerDismiss(this);
			}

		}
		ViewUtils.inject(this, gridViewPhoto);
		mTVGroupCount.setText(countGroup + " )");
		setViewListener();
		return gridViewPhoto;
	}

	/**
	 * 
	 * @Title: setViewListener
	 * @Description: 注册点击事件
	 * @param
	 * @return void
	 * @throws
	 */
	private void setViewListener() {
		mDissMiss.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected PullToRefreshBase<GridView> initRefreshIdView() {
		mGridView = mWishRefreshGridView.getRefreshableView();
		mWishRefreshGridView.setMode(Mode.DISABLED);
		PauseOnScrollListener listener = new PauseOnScrollListener(
				ImageLoader.getInstance(), true, true);
		mWishRefreshGridView.setOnScrollListener(listener);
		return mWishRefreshGridView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		loadData();
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 
	 * @Title: loadData
	 * @Description: 加载数据
	 * @param
	 * @return
	 * @throws
	 */
	public void loadData() {
		super.onLoadData(true, true, false);
	}

	@Override
	public PhotoGroupParser setParser(String str) {
		PhotoGroupParser wishParser = new PhotoGroupParser(str);
		totalPage = wishParser.getTotalPage();
		return wishParser;

	}

	@Override
	public RequestParamsNet getApiParmars() {
		// CategoryId：分类筛选
		RequestParamsNet requestPar = new RequestParamsNet();
		String userid = SharedPreferenceHelper
				.getLoginUserId(TootooPlusApplication.getAppContext());
		requestPar.setmStrHttpApi(TootooeNetApiUrlHelper.GROUP_MAN);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.ACTIVITYID,
				activityId);
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE,
				String.valueOf(currentpage));
		requestPar.addQueryStringParameter(TootooeNetApiUrlHelper.PAGE_SIZE,
				String.valueOf(TootooeNetApiUrlHelper.PAGESIZE_DRAFT_PHOTO));// 默认每页6条

		return requestPar;
	}

	@Override
	public int getTotalPage() {
		return totalPage;
	}

	@Override
	public void getPageListParserResult(List<GridViewGroupBean> parserResult) {
		if (super.currentpage == 1) {
			this.wishList.clear();
		}
		wishList.addAll(parserResult);
		if (wishList.size() > 0) {
			GridViewGroupPhotoAdapter wishAdatper = new GridViewGroupPhotoAdapter(
					getActivity(), wishList);
			mGridView.setAdapter(wishAdatper);
			if (super.currentpage != 1) {
				mGridView.setSelection(this.wishList.size() / 4 + 1);
			}
			wishAdatper.notifyDataSetChanged();
		} else {
			ComponentUtil.showToast(TootooPlusApplication.getAppContext(),
					"没有数据");
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_group_view:
			viewDissMiss.setVisibility(View.GONE);
			if (onListenerSwitch != null) {
				onListenerSwitch.onListenerSwitch(true);
			}
			break;

		}

	}

	private OnListenerOfSwitch onListenerSwitch;

	public interface OnListenerOfSwitch {
		public void onListenerSwitch(boolean isClose);

	}

	public void setOnListenerOfSwitch(OnListenerOfSwitch onListenerSwitch) {
		this.onListenerSwitch = onListenerSwitch;
	}

	@Override
	public void OnClickListenerDismiss(boolean isDissMiss) {
		if(isDissMiss){

			boolean isFirst = SharedPreferenceHelper
					.getFirstGuideClickGroupCount(getActivity());
			if (isFirst) {
				FirstGuideClickGroupCountDialog firstGuide = (FirstGuideClickGroupCountDialog) CommonUtil
						.showFirstGuideDialog(
								getActivity(),
								ConstantsTooTooEHelper.FIRST_GUIDE_CLICK_GOGROUPCOUNT);
			}
		}
	}

}
