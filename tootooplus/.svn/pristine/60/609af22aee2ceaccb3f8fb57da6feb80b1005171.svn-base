package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.InternetBrowserActivity;
import com.ninetowns.tootooplus.adapter.CategoryGoodsSubAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ConVertBean;
import com.ninetowns.tootooplus.bean.GoodsScreenMainBean;
import com.ninetowns.tootooplus.bean.GoodsScreenSubBean;
import com.ninetowns.tootooplus.helper.CategaryHelper;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.ExpandTabViewHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
 * @ClassName: GoodsTypeDialogFragment
 * @Description: 商品分类列表
 * @author wuyulong
 * @date 2015-1-27 下午4:30:26
 * 
 */
public class GoodsTypeDialogFragment extends BaseFragmentDialog implements
		View.OnClickListener {
	private View goodsTypeView;
	private List<GoodsScreenMainBean> listGoodsScreenMain = new ArrayList<GoodsScreenMainBean>();// 存储主分类列表
	private List<GoodsScreenSubBean> listGoodsScreenSub = new ArrayList<GoodsScreenSubBean>();
	private OnScreenGoods onScreenGoodsListener;
	@ViewInject(R.id.ll_push)
	private LinearLayout mLLPush;// 发布
	// @ViewInject(R.id.ll_main)
	// private LinearLayout mLLMian;// 主分类
	private ArrayList<View> mViewArray = new ArrayList<View>();
	@ViewInject(R.id.expand_tab)
	private ExpandTabViewHelper mExpandTab;// 主分类
	private PopupWindow popWindow;
	@ViewInject(R.id.ll_dismiss)
	private LinearLayout ll_dismiss;
	@ViewInject(R.id.et_getshoppingurl)
	private EditText mEtShoppingUrl;
	@ViewInject(R.id.iv_look_internet)
	private ImageView mIVInternet;
	private ListView listViewMain;
	private PopupWindow popWindowsub;
	private ListView listViewSub;
	private View categorysub;
	private View categorymain;
	private String categoryidSub="";
	private String categoryidMain="";

	private CategoryGoodsSubAdapter categoryGoodsAdapter;
	private String shoppingUrl="";

	public GoodsTypeDialogFragment() {
	}

	private ConVertBean convertBean;

	public GoodsTypeDialogFragment(ConVertBean convertBean) {
		this.convertBean = convertBean;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		goodsTypeView = inflater.inflate(R.layout.goods_type_dialogfragment,
				container);
		ViewUtils.inject(this, goodsTypeView);
		setOnViewClickListener();
		justIsHaveShoppingOrCateId();
		return goodsTypeView;
	}

	/** 
	* @Title: justIsHaveShopping 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void justIsHaveShoppingOrCateId() {
		if (convertBean != null) {
			shoppingUrl = convertBean.getShoppingUrl();
			if (!TextUtils.isEmpty(shoppingUrl)) {
				mEtShoppingUrl.setText(shoppingUrl);
			}
			categoryidMain=convertBean.getCategoryParentId();
			categoryidSub=convertBean.getCategoryId();
		}
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		setRequestScreenGoodsData();
	}

	/**
	 * 
	 * @Title: skipToInternet
	 * @Description: 跳转到网络
	 * @param
	 * @return
	 * @throws
	 */
	@OnClick(R.id.iv_look_internet)
	public void skipToInternet(View v) {
		String shopUrl = mEtShoppingUrl.getText().toString();
		if (!TextUtils.isEmpty(shopUrl)) {
			Intent intent = InternetBrowserActivity.buildIntent(getActivity(),
					shopUrl, "", mEtShoppingUrl);
			startActivity(intent);
		}

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

		if ((NetworkUtil.isNetworkAvaliable(TootooPlusApplication
				.getAppContext()))) {
			// 显示进度
			showProgressDialog(getActivity());
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(ConstantsTooTooEHelper.TYPE, ConstantsTooTooEHelper.CATEGROY_TYPE);
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.GOODS_CATEGORY,
					requestParamsNet, new RequestCallBack<String>() {

						private String cateGoryName = "";
						private int subPostion = 0;
						private int mainPostion;

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
											goodsScreenMainBean
													.setImgUrl(imageUrl);
											goodsScreenMainBean
													.setCategoryId(cateGoryId);
											goodsScreenMainBean
													.setCategoryName(categoryName);
											goodsScreenMainBean
													.setCategorySub(categorySub);
											listGoodsScreenMain
													.add(goodsScreenMainBean);

										}
										final CategaryHelper viewHelpter = new CategaryHelper(
												getActivity(),
												listGoodsScreenMain);
										mViewArray.add(viewHelpter);
										ArrayList<String> mTextArray = new ArrayList<String>();
										if (convertBean != null) {
											// convertBean.getCategoryParentId();
											if (listGoodsScreenMain != null) {
												for (int i = 0; i < listGoodsScreenMain
														.size(); i++) {
													GoodsScreenMainBean main = listGoodsScreenMain
															.get(i);

													if (main.getCategoryId()
															.equals(convertBean
																	.getCategoryParentId())) {
														LinkedList<GoodsScreenMainBean> sub = getChildrenCateGoryData(main);
														if (sub != null) {
															for (int j = 0; j < sub
																	.size(); j++) {
																GoodsScreenMainBean subdata = sub
																		.get(j);
																if (subdata
																		.getCategoryId()
																		.equals(convertBean
																				.getCategoryId())) {
																	cateGoryName = subdata
																			.getCategoryName();
																	subPostion = j;
																	break;
																}

															}
														}
														mainPostion = i;
														break;
													}

												}
											}

										}
										if (!TextUtils.isEmpty(cateGoryName)) {
											mTextArray.add(cateGoryName);
											mExpandTab.setValue(mTextArray,
													mViewArray);
											if (0 != mainPostion) {
												mExpandTab.setTitle(viewHelpter
														.getShowText(),
														mainPostion + 1);
											} else {
												mExpandTab.setTitle(viewHelpter
														.getShowText(), 1);
											}

										} else {
											mTextArray.add("请选择");
											mExpandTab.setValue(mTextArray,
													mViewArray);
											mExpandTab.setTitle(
													viewHelpter.getShowText(),
													1);
										}

										viewHelpter
												.setOnSelectListener(new CategaryHelper.OnSelectListener() {

													@Override
													public void getValue(
															String showText,
															ArrayList<GoodsScreenMainBean> groups,
															int firstPosition,
															GoodsScreenMainBean secondCategary) {

														if (groups != null) {
															categoryidMain = groups
																	.get(firstPosition)
																	.getCategoryId();
															categoryidSub = secondCategary
																	.getCategoryId();
															LogUtil.systemlogError(
																	"categoryidMain",
																	categoryidMain);
															LogUtil.systemlogError(
																	"categoryidSub",
																	categoryidSub);
														}
														onRefresh(viewHelpter,
																showText);

													}
												});
									}

								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialog(getActivity());
							ComponentUtil.showToast(
									TootooPlusApplication.getAppContext(),
									getResources()
											.getString(
													R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					TootooPlusApplication.getAppContext(),
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}

	private LinkedList<GoodsScreenMainBean> getChildrenCateGoryData(
			GoodsScreenMainBean iterable_element) {
		LinkedList<GoodsScreenMainBean> tItem = new LinkedList<GoodsScreenMainBean>();
		String strJson = iterable_element.getCategorySub();

		JSONArray jsonArr;
		try {
			jsonArr = new JSONArray(strJson);
			for (int i = 0; i < jsonArr.length(); i++) {
				GoodsScreenMainBean subBean = new GoodsScreenMainBean();
				JSONObject obj = (JSONObject) jsonArr.get(i);
				if (obj.has("CategoryId")) {
					String subCatagoryId = obj.getString("CategoryId");
					subBean.setCategoryId(subCatagoryId);
				}
				if (obj.has("CategoryName")) {
					String subCatagoryName = obj.getString("CategoryName");
					subBean.setCategoryName(subCatagoryName);
				}
				tItem.add(subBean);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tItem;
	}

	private void setOnViewClickListener() {
		mLLPush.setOnClickListener(this);
		// mLLub.setOnClickListener(this);
		ll_dismiss.setOnClickListener(this);
	}

	/**
	 * 
	 * @ClassName: OnScreenGoods
	 * @Description: 筛选故事接口
	 * @author wuyulong
	 * @date 2015-1-27 下午4:52:39
	 * 
	 */
	public interface OnScreenGoods {
		public void onPushGoodsListener(View push, String cateGoryIdMain,
				String cateGoryIdSub, String shoppingUrl);

	}

	/**
	 * 
	 * @Title: setOnScreenGoods
	 * @Description: 注册筛选监听器
	 * @param
	 * @return
	 * @throws
	 */
	public void setOnScreenGoods(OnScreenGoods onScreenGoodsListener) {
		this.onScreenGoodsListener = onScreenGoodsListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_push:
			shoppingUrl = mEtShoppingUrl.getText().toString();
			if (!TextUtils.isEmpty(categoryidMain)
					&& !TextUtils.isEmpty(categoryidSub)) {
				if (listGoodsScreenMain != null
						&& listGoodsScreenMain.size() > 0) {
					if (onScreenGoodsListener != null) {
						onScreenGoodsListener.onPushGoodsListener(v,
								categoryidMain, categoryidSub, shoppingUrl);
					}
				} else {
					ComponentUtil.showToast(getActivity(), "您还没有选择商品分类");
				}
			} else {
				ComponentUtil.showToast(getActivity(), "您还没有选择商品分类");
			}

			break;
		case R.id.ll_dismiss:
			dismiss();
			break;

		default:
			break;
		}
	}

	private void onRefresh(View view, String showText) {

		mExpandTab.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !mExpandTab.getTitle(position).equals(showText)) {
			mExpandTab.setTitle(showText, position);
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
	public void onDestroy() {
		super.onDestroy();

	}
}
