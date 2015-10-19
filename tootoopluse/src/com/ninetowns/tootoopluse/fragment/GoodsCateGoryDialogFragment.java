package com.ninetowns.tootoopluse.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GoodsScreenMainBean;
import com.ninetowns.tootoopluse.bean.GoodsScreenSubBean;
import com.ninetowns.tootoopluse.fragment.CategoryExspandListViewDialogFragment.OnGroupDataListener;
import com.ninetowns.tootoopluse.helper.CategaryHelper;
import com.ninetowns.tootoopluse.helper.ExpandTabViewHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
 * @ClassName: GoodsTypeDialogFragment
 * @Description: 商品分类筛选
 * @author wuyulong
 * @date 2015-1-27 下午4:30:26
 * 
 */
public class GoodsCateGoryDialogFragment extends BaseFragmentDialog implements
		View.OnClickListener, OnCheckedChangeListener,OnGroupDataListener {
	private View goodsTypeView;
	@ViewInject(R.id.expand_tab_helper)
	private ExpandTabViewHelper mCateGory;// 全部分类
	@ViewInject(R.id.rg_tab_user)
	private RadioGroup mRgUser;// 个人，商家
	@ViewInject(R.id.rb_tab_seller)
	private RadioButton mRbtnSeller;// 商家
	@ViewInject(R.id.rb_tab_mine)
	private RadioButton mRbtnMine;
	@ViewInject(R.id.rg_story)
	private RadioGroup mRgStory;// 最新、最热、推荐
	@ViewInject(R.id.rb_tab_new)
	private RadioButton mRbtnNew;
	@ViewInject(R.id.rb_tab_hot)
	private RadioButton mRbtnHot;
	@ViewInject(R.id.rb_tab_recommend)
	private RadioButton mRbtnRecommend;
	@ViewInject(R.id.ll_cancel)
	private LinearLayout mTVcancel;// 取消
	@ViewInject(R.id.ll_sure)
	private LinearLayout mTVSure;// 确认
	@ViewInject(R.id.ll_category_dismiss)
	private LinearLayout mLlDissmiss;
	private OnCategoryGoods onCategoryGoodsListener;
	private String cateGoryMainId = "";//主分类的id
	private String cateGorySubId = "";//子分类的id
	private String publisher="1";//发布者身份（1商家，2个人）
	private String sort="1";//排序方式（1最新，2最热，3推荐）
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private List<GoodsScreenMainBean> listGoodsScreenMain = new ArrayList<GoodsScreenMainBean>();// 存储主分类列表

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		goodsTypeView = inflater.inflate(
				R.layout.goods_category_dialogfragment, container);
		ViewUtils.inject(this, goodsTypeView);
		setOnViewClickListener();
		setOnCheckListener();
		return goodsTypeView;
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		setRequestScreenGoodsData();
	}
	/******* 设置选择器监听事件 **********/
	private void setOnCheckListener() {
		mRbtnSeller.setChecked(true);
		mRbtnNew.setChecked(true);
		mRgUser.setOnCheckedChangeListener(this);
		mRgStory.setOnCheckedChangeListener(this);
		
	}

	private void setOnViewClickListener() {
		mCateGory.setOnClickListener(this);
		mTVSure.setOnClickListener(this);
		mTVcancel.setOnClickListener(this);
		mLlDissmiss.setOnClickListener(this);
	}

	/**
	 * 
	 * @ClassName: OnScreenGoods
	 * @Description: 筛选故事接口
	 * @author wuyulong
	 * @date 2015-1-27 下午4:52:39
	 * 
	 */
	public interface OnCategoryGoods {
		public void onPushGoodsListener(View push, String publisher,String sort,String cateGoryIdMain,
				String cateGoryIdSub);

	}

	/**
	 * 
	 * @Title: setOnScreenGoods
	 * @Description: 注册筛选监听器
	 * @param 传人一个监听器
	 * @return
	 * @throws
	 */
	public void setOnCateGoryGoods(OnCategoryGoods onCategoryGoodsListener) {
		this.onCategoryGoodsListener = onCategoryGoodsListener;
	}
	/**
	 * 
	 * @Title: setRequestScreenGoodsData
	 * @Description: 获取商品筛选数据的接口
	 * @param
	 * @return
	 * @throws
	 */
	private void setRequestScreenGoodsData() {

		// StoryId：故事Id (必填)
		// PageType：故事页类型：1,文字，2图片，3视频 (必填)
		// PageContent：故事页文字
		// PageImgThumb：故事页缩略图
		// PageImg：故事页图片或直播录播封面图地址
		// PageVideoUrl：故事页录播视频地址
		// RecordId：录制Id
		// PageDesc：故事页描述

		// 创建封面图
	
		if ((NetworkUtil.isNetworkAvaliable(TootooPlusEApplication
				.getAppContext()))) {
			// 显示进度
			showProgressDialog(getActivity());
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.GOODS_CATEGORY,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialog(getActivity());
							String strObj = responseInfo.result;
							try {
								JSONObject jsonObject = new JSONObject(strObj);
								if (jsonObject.has("Status")) {
									String strStatus = jsonObject
											.getString("Status");
									if (strStatus.equals("1")) {
									} else {
										return;
									}

								}
								if (jsonObject.has("Data")) {
									String strArr = jsonObject
											.getString("Data");
									JSONObject jsonObjectMain = new JSONObject(
											strArr);
									if (jsonObjectMain.has("CategoryMain")) {
										String strJsonObjectMain = jsonObjectMain
												.getString("CategoryMain");
										JSONArray jsonArray = new JSONArray(
												strJsonObjectMain);
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject jsonObjectCategoryMain = (JSONObject) jsonArray
													.get(i);
											String cateGoryId = jsonObjectCategoryMain
													.getString("CategoryId");
											String categoryName = jsonObjectCategoryMain
													.getString("CategoryName");
											String categorySub = jsonObjectCategoryMain
													.getString("CategorySub");
											String imageUrl = jsonObjectCategoryMain
													.getString("ImgUrl");
											GoodsScreenMainBean goodsScreenMainBean = new GoodsScreenMainBean();
											goodsScreenMainBean.setImgUrl(imageUrl);
											goodsScreenMainBean
													.setCategoryId(cateGoryId);
											goodsScreenMainBean
													.setCategoryName(categoryName);
											goodsScreenMainBean
													.setCategorySub(categorySub);
											listGoodsScreenMain
													.add(goodsScreenMainBean);

										}
										
									}

								}
								
								createCatagary();
								
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						/** 
						* @Title: createCatagary 
						* @Description: TODO
						* @param  
						* @return   
						* @throws 
						*/
						private void createCatagary() {
							final CategaryHelper viewHelpter = new CategaryHelper(getActivity(), listGoodsScreenMain);
							mViewArray.add(viewHelpter);
							ArrayList<String> mTextArray = new ArrayList<String>();
							mTextArray.add("全部分类");
							mCateGory.setValue(mTextArray, mViewArray);
							mCateGory.setTitle(viewHelpter.getShowText(), 1);
							viewHelpter.setOnSelectListener(new CategaryHelper.OnSelectListener() {

								@Override
								public void getValue(
										String showText,
										ArrayList<GoodsScreenMainBean> groups,
										int firstPosition,
										GoodsScreenMainBean secondCategary) {
									
									if(groups!=null){
										cateGoryMainId=groups.get(firstPosition).getCategoryId();
										cateGorySubId=secondCategary.getCategoryId();
										LogUtil.systemlogError("categoryidMain", cateGoryMainId);
										LogUtil.systemlogError("categoryidSub", cateGorySubId);
									}
									onRefresh(viewHelpter,showText);
									
								}});
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialog(getActivity());
							 if(isAdded()){
								 createCatagary();
								 ComponentUtil.showToast(
											TootooPlusEApplication.getAppContext(),
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
							   
							 }
							
						}
					});

		} else {
			ComponentUtil.showToast(
					TootooPlusEApplication.getAppContext(),
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.cv_all_category:
//			showcategoryDialogFragment();
//			break;
		case R.id.ll_sure:
			if(onCategoryGoodsListener!=null){
				onCategoryGoodsListener.onPushGoodsListener(v, publisher, sort, cateGoryMainId, cateGorySubId);
			}
			dismiss();
			break;
		case R.id.ll_cancel:
			dismiss();
			break;
		case R.id.ll_category_dismiss:
			dismiss();
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: showcategoryDialogFragment
	 * @Description: 分类列表
	 * @param
	 * @return
	 * @throws
	 */
	private void showcategoryDialogFragment() {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		CategoryExspandListViewDialogFragment fragment = new CategoryExspandListViewDialogFragment();
		fragment.setOnGroupDataListener(this);
		if (fragmentManager != null) {
			// 屏幕较小，以全屏形式显示
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			// 指定一个过渡动画
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.addToBackStack(null);
			 transaction.attach(fragment);
			fragment.show(fragmentManager, "dialog");
			transaction.commitAllowingStateLoss();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_tab_seller:
			publisher="1";//商家
			break;
		case R.id.rb_tab_mine:
			publisher="2";//个人
			break;
		case R.id.rb_tab_new:
			sort="1";
			break;
		case R.id.rb_tab_hot:
			sort="2";
			break;
		case R.id.rb_tab_recommend:
			sort="3";
			break;
		

		default:
			break;
		}

	}
private void onRefresh(View view, String showText) {
		
	mCateGory.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !mCateGory.getTitle(position).equals(showText)) {
			mCateGory.setTitle(showText, position);
		}

	}

private int getPositon(View tView) {
	for (int i = 0; i < mViewArray.size(); i++) {
		if (mViewArray.get(i) == tView) {
			return i;
		}
	}
	return -1;
}
	@Override
	public void onGroupListener(GoodsScreenSubBean goodsScreenSub,
			GoodsScreenMainBean goosMainBean) {
//		String mainName=goosMainBean.getCategoryName();
//		String subName=goodsScreenSub.getCategoryName();
//		cateGoryMainId=goosMainBean.getCategoryId();
//		cateGorySubId=goodsScreenSub.getCategoryId();
//		StringBuilder stringBuild=new StringBuilder();
//		stringBuild.append(mainName).append("-").append(subName);
//		mCateGory.setTextColor(getResources().getColor(R.color.red));
//		mCateGory.setText(stringBuild.toString());
	}

}
