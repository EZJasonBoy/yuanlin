package com.ninetowns.tootooplus.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.CreateActSecondFragmentAdapter;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.bean.ActivityStructure;
import com.ninetowns.tootooplus.bean.CreateActivitySecondBean;
import com.ninetowns.tootooplus.bean.PageBean;
import com.ninetowns.tootooplus.bean.SecondStepStoryBean;
import com.ninetowns.tootooplus.bean.StoryDetailListBean;
import com.ninetowns.tootooplus.fragment.CreateSecondActivityFragment;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.CreateSecondActivityParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.cooldraganddrop.SpanVariableGridView.LayoutParams;

/**
 * 
 * @ClassName: CreateActSecondStepActivity
 * @Description: 创建活动第二步
 * @author wuyulong
 * @date 2015-2-9 下午1:24:08
 * 
 */
public class CreateActSecondStepActivity extends BaseActivity implements
		OnPageChangeListener, OnClickListener {
	private Bundle bundle;
	@ViewInject(R.id.ll_left)
	private LinearLayout mLLAbove;
	@ViewInject(R.id.ct_left)
	private CheckedTextView mCTAbove;
	@ViewInject(R.id.ll_middle)
	private LinearLayout mLLMiddle;
	@ViewInject(R.id.tv_title)
	private TextView mTvMiddle;
	@ViewInject(R.id.ll_right)
	private LinearLayout mLLRight;
	@ViewInject(R.id.ct_right)
	private CheckedTextView mCTRight;
	@ViewInject(R.id.second_viewpager)
	private ViewPager mViewPager;
	private String storyid;
	private SecondStepStoryBean secondStepStoryBean;
	private List<CreateSecondActivityFragment> listFragment = new ArrayList<CreateSecondActivityFragment>();
	public int currentPosition = 0;
	private String activityName;
	private List<CreateActivitySecondBean> wishPage;
	private List<StoryDetailListBean> currentList;
	private String activityId;
	private String storyIdArray="";
	private boolean isEdiTextAct;
	private String activityNameYuan="";
	private boolean isAgainPush;
	private boolean isEditextSecond;
	private StoryDetailListBean oldBean;
	private int itemIndex=0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity_second_activity);
		getType();
		ViewUtils.inject(this);
		mLLRight.setOnClickListener(this);
		if(isEdiTextAct){//编辑
			mTvMiddle.setText("编辑白吃活动   ");
			mCTAbove.setVisibility(View.INVISIBLE);
			editextActivity();
		}else{//创建
			mTvMiddle.setText("创建白吃活动   ");
			mCTAbove.setText("上一步");
			mCTAbove.setVisibility(View.VISIBLE);
			createData();
			
		}
		mCTRight.setText("下一步");

	}

	/**
	 * 
	 * @Title: createData
	 * @Description: 获取创建第二步的数据
	 * @param
	 * @return
	 * @throws
	 */
	private void createData() {
		if (secondStepStoryBean != null) {
			setAdapterData(secondStepStoryBean);

		} else {
			if ((NetworkUtil.isNetworkAvaliable(this))) {
				// 显示进度
				showProgressDialog(CreateActSecondStepActivity.this);
				RequestParamsNet requestParamsNet = new RequestParamsNet();
				requestParamsNet.addQueryStringParameter(
						TootooeNetApiUrlHelper.STORYID, storyid);
				String userid = SharedPreferenceHelper
						.getLoginUserId(TootooPlusApplication.getAppContext());
				requestParamsNet.addQueryStringParameter(
						TootooeNetApiUrlHelper.USER_ID, userid);
				CommonUtil.xUtilsPostSend(
						TootooeNetApiUrlHelper.ACTIVITY_CREATE_PRE,
						requestParamsNet, new RequestCallBack<String>() {

							private String status;

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								closeProgressDialog(CreateActSecondStepActivity.this);
								String jsonStr = new String(responseInfo.result);
								CreateSecondActivityParser secondParser = new CreateSecondActivityParser(
										jsonStr);
								SecondStepStoryBean resultData = secondParser
										.getParseResult(jsonStr);
								setAdapterData(resultData);
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								closeProgressDialog(CreateActSecondStepActivity.this);
								ComponentUtil
										.showToast(
												CreateActSecondStepActivity.this,
												getResources()
														.getString(
																R.string.errcode_network_response_timeout));
							}
						});

			} else {
				ComponentUtil.showToast(
						this,
						this.getResources().getString(
								R.string.errcode_network_response_timeout));
			}
		}

	}

	private void setAdapterData(SecondStepStoryBean resultData) {
		this.secondStepStoryBean=resultData;
		wishPage = resultData.getStoryList();
		if (wishPage != null && wishPage.size() > 0) {
			for (int i = 0; i < wishPage.size(); i++) {
				CreateActivitySecondBean viewpagerbean = wishPage.get(i);
				CreateSecondActivityFragment createSecActFragment = new CreateSecondActivityFragment(
						resultData, viewpagerbean, i);
				listFragment.add(createSecActFragment);
			}

		}
		CreateActSecondFragmentAdapter createActAdapter = new CreateActSecondFragmentAdapter(
				getSupportFragmentManager(), listFragment);
		mViewPager.setAdapter(createActAdapter);
		mViewPager.setOnPageChangeListener(CreateActSecondStepActivity.this);
		mViewPager.setCurrentItem(currentPosition);
		createActAdapter.notifyDataSetChanged();
		getActivityName();
	}

	/**
	 * 
	 * @Title: getType
	 * @Description: bundle 参数
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	private void getType() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		storyid = bundle.getString(ConstantsTooTooEHelper.STORYID);
		activityId=bundle.getString(ConstantsTooTooEHelper.ACTIVITYID);
		isEdiTextAct=bundle.getBoolean("isEditextAct");
		isAgainPush=bundle.getBoolean("isAgainPush");
		isEditextSecond=bundle.getBoolean("isEditextSecond");
		secondStepStoryBean = (SecondStepStoryBean) bundle
				.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
		if(secondStepStoryBean!=null){//编辑的时候
			 storyIdArray=secondStepStoryBean.getStoryIdArray();
			if(!TextUtils.isEmpty(storyIdArray)){
				isEdiTextAct=true;
				
			}else{
				isEdiTextAct=false;
			}
		}
		currentPosition = bundle.getInt("currentPosition");

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
//		getActivityName();
		

		if (listFragment.size() > 0) {
			CreateSecondActivityFragment currentFragment = listFragment
					.get(currentPosition);
			if (currentFragment.mActivityName != null) {
				activityName = currentFragment.mActivityName.getText()
						.toString();// 上一个activityname
			}

		}
	
		currentPosition = arg0;
		
		
		
		if (listFragment.size() > 0) {
			CreateSecondActivityFragment currentFragment = listFragment
					.get(currentPosition);
			if (currentFragment.mActivityName != null) {
				if(!TextUtils.isEmpty(activityName)){
					currentFragment.mActivityName.setText(activityName);
				}
				activityName = currentFragment.mActivityName.getText()
						.toString();// 上一个activityname
			}

		}
		System.out.println("当前位置" + arg0);

	}
/**
 * 
* @Title: getActivityName 
* @Description: 获取当前的activityName
* @param  
* @return   
* @throws
 */
	private void getActivityName() {
		if (listFragment.size() > 0) {
			CreateSecondActivityFragment currentFragment = listFragment
					.get(currentPosition);
			if (currentFragment.mActivityName != null) {
				activityName = currentFragment.mActivityName.getText()
						.toString();// 上一个activityname
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_right:
			getActivityName();
			if(!TextUtils.isEmpty(activityName)){
				clickTheNext();
			}else{
				ComponentUtil.showToast(this, "您还没有填写活动标题");
			}
			
			break;

		default:
			break;
		}

	}

	/** 
	* @Title: clickTheNext 
	* @Description: 点击下一步
	* @param  
	* @return   
	* @throws 
	*/
	private void clickTheNext() {
		int centerPalyX = CommonUtil.getWidth(TootooPlusApplication
				.getAppContext()) / 2;
		if (listFragment.size() > 0) {
			CreateSecondActivityFragment currentFragment = listFragment
					.get(currentPosition);
			if (currentFragment.wishDetailAdapter != null) {
				currentList = currentFragment.wishDetailAdapter.mListDragBean;// 上一个activityname
			}

		}
		if (currentList != null) {
			wishPage.get(currentPosition).setWishDetailBean(currentList);
		}
		final List<ActivityStructure> activityStructureList = new ArrayList<ActivityStructure>();
		// 点击下一步
		if (wishPage != null && wishPage.size() > 0) {
			for (int i = 0; i < wishPage.size(); i++) {
				CreateActivitySecondBean viewpagerbean = wishPage.get(i);
				CreateSecondActivityFragment currentFragment = listFragment
						.get(i);
				
			
				String storyId = viewpagerbean.getConvertBean()
						.getStoryId();
				List<StoryDetailListBean> listRespn = viewpagerbean
						.getWishDetailBean();
				List<PageBean> pageList = new ArrayList<PageBean>();
				int datasize = listRespn.size();
				if (datasize > 0) {//
					// 集合转化json 保存
					int datatsize = listRespn.size();
					for (int i1 = 0; i1 < datatsize; i1++) {
						if(currentFragment.mCoolDragAndrDropView!=null){
							View positionView = currentFragment.mCoolDragAndrDropView.getChildAt(i1);	
							if (positionView != null) {
								final LayoutParams lp = (LayoutParams) positionView
										.getLayoutParams();
								int rowX = lp.row;
								StoryDetailListBean trbean = listRespn.get(i1);
								PageBean pageBean = new PageBean();
//								pageBean.setItemIndex(rowX);
//								trbean.setItemIndex(rowX);
								
								
								int location=trbean.getLocation();
								int elementType=trbean.getElementType();
								if(oldBean!=null){//如果不是第一条那么第一条如果是长方形
									int oldElement=oldBean.getElementType();
									int oldLocation=oldBean.getLocation();
									if(oldElement==1||oldElement==0){//长方形
										itemIndex=itemIndex+1;
										pageBean.setItemIndex(itemIndex);//追加到后边
										trbean.setItemIndex(itemIndex);
									}else {//上一条数据是正方形
										if(oldLocation==1){
											if(elementType==2){//当前的是否是2？ 如果是
												pageBean.setItemIndex(itemIndex);//追加到后边
												trbean.setItemIndex(itemIndex);
											}else if(elementType==0){//当前的是长方形
												itemIndex=itemIndex+1;
												pageBean.setItemIndex(itemIndex);//追加到后边
												trbean.setItemIndex(itemIndex);
												trbean.setElementType(2);
											}else{
												itemIndex=itemIndex+1;
												pageBean.setItemIndex(itemIndex);//追加到后边
												trbean.setItemIndex(itemIndex);
											}
										}else{//第二个位置
											itemIndex=itemIndex+1;
											pageBean.setItemIndex(itemIndex);//追加到后边
											trbean.setItemIndex(itemIndex);
										}
										
										
									}
									
								}else{//是第一条数据
									if(elementType==1){//长方形
										pageBean.setItemIndex(itemIndex);
										trbean.setItemIndex(itemIndex);
									}else {
										pageBean.setItemIndex(itemIndex);
										trbean.setItemIndex(itemIndex);
									}
									
								}
									
								oldBean=trbean;//把当前的复制给老的
								
								LogUtil.systemlogError("我发布时候的row1", trbean.getItemIndex()
										+ "");
								
								
								
								
								Rect rect = CommonUtil.getRect(positionView);
								int centerViewX = rect.centerX();
								if (centerViewX < centerPalyX) {
									trbean.setLocation(1);
									pageBean.setLocation(1);

								} else if (centerViewX == centerPalyX) {
									trbean.setLocation(1);
									pageBean.setLocation(1);
								} else if (centerViewX > centerPalyX) {
									trbean.setLocation(2);
									pageBean.setLocation(2);
								} else {
									LogUtil.systemlogError("拖动的时候centerViewX=", centerViewX
											+ "");
								}
							String pageid = trbean.getPageId();
							pageBean.setPageId(pageid);
							pageBean.setElementType(trbean.getElementType());
							pageBean.setTemplate("1");
							trbean.setTemplate("1");
							listRespn.add(trbean);
							pageList.add(pageBean);
						}
						}
							
					
					
				}} else {
					// json 是“”

				}
				ActivityStructure activityStructure = new ActivityStructure();
				activityStructure.setPageList(pageList);
				if(storyId!=null){
					LogUtil.systemlogError("保存的时候storyid", storyId);
				}else{
					LogUtil.systemlogError("保存的时候storyid", "null");
				}
				activityStructure.setStoryId(storyId);
				activityStructureList.add(activityStructure);

			}

			// gson.toJson(src, typeOfSrc, writer)
			Gson gson = new Gson();
			String strGson = gson.toJson(activityStructureList);
			LogUtil.systemlogInfo("多个故事的数据结构", strGson);

			savestoryPageSory(strGson);
		}else{
			skipToThirdAct();
		}

	}

	/**
	 * 
	 * @Title: savestoryPageSory
	 * @Description:保存结构
	 * @param
	 * @return
	 * @throws
	 */
	public void savestoryPageSory(String storyList) {

		if ((NetworkUtil.isNetworkAvaliable(this))) {
			// 显示进度
			RequestParamsNet requestParamsNet = new RequestParamsNet();

			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.StoryList, storyList);// 2是图片3是视频
			CommonUtil.xUtilsPostSend(TootooeNetApiUrlHelper.PAGE_SORT,
					requestParamsNet, new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String strRespone = responseInfo.result;
							if (!TextUtils.isEmpty(strRespone)) {
								try {
									JSONObject jsobj = new JSONObject(
											strRespone);
									if (jsobj.has("Status")) {
										String status = jsobj
												.getString("Status");
										if (status.equals("1")) {
											// 下一步 发布结构
											skipToThirdAct();
										}

									}

								} catch (JSONException e) {
									LogUtil.error("CreateStoryFragment",
											e.toString());
									e.printStackTrace();
								}
							}

						}

						

						@Override
						public void onFailure(HttpException error, String msg) {
							ComponentUtil
									.showToast(
											CreateActSecondStepActivity.this,
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(
					this,
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}

	}
	/**
	 * 
	* @Title: editextActivity 
	* @Description: 编辑活动
	* @param  
	* @return   
	* @throws
	 */
	public void editextActivity(){

		if (secondStepStoryBean != null) {
			setAdapterData(secondStepStoryBean);

		} else {
			if ((NetworkUtil.isNetworkAvaliable(this))) {
				// 显示进度
				showProgressDialog(this);
				RequestParamsNet requestParamsNet = new RequestParamsNet();
				if(!isAgainPush){
					if(!isEditextSecond){
						requestParamsNet.addQueryStringParameter(
								TootooeNetApiUrlHelper.STORYID, storyid);
						
					}
					
				}else{
					requestParamsNet.addQueryStringParameter(
							TootooeNetApiUrlHelper.AGAIN_PUSH, "1");
				}
				
				String userid = SharedPreferenceHelper
						.getLoginUserId(TootooPlusApplication.getAppContext());
				requestParamsNet.addQueryStringParameter(
						TootooeNetApiUrlHelper.USER_ID, userid);
				requestParamsNet.addQueryStringParameter(
						TootooeNetApiUrlHelper.ACTIVITYID, activityId);
				CommonUtil.xUtilsPostSend(
						TootooeNetApiUrlHelper.ACTIVITY_STORY_UPDATEPRE,
						requestParamsNet, new RequestCallBack<String>() {

							private String status;
							
							

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								closeProgressDialog(CreateActSecondStepActivity.this);
								String jsonStr = new String(responseInfo.result);
								CreateSecondActivityParser secondParser = new CreateSecondActivityParser(
										jsonStr);
								SecondStepStoryBean resultData = secondParser
										.getParseResult(jsonStr);
								if(resultData!=null){
									activityId=resultData.getActivityId();
									storyIdArray=resultData.getStoryIdArray();
									activityNameYuan=resultData.getActivityName();
								}
								setAdapterData(resultData);
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								closeProgressDialog(CreateActSecondStepActivity.this);
								ComponentUtil
										.showToast(
												CreateActSecondStepActivity.this,
												getResources()
														.getString(
																R.string.errcode_network_response_timeout));
							}
						});

			} else {
				ComponentUtil.showToast(
						this,
						this.getResources().getString(
								R.string.errcode_network_response_timeout));
			}
		}

	
		
		
		
	}
	/**
	 * 
	* @Title: skipToThirdAct 
	* @Description: 跳转到第三步
	* @param  
	* @return   
	* @throws
	 */
	private void skipToThirdAct() {
		if(isEdiTextAct){//跳转到第三步的编辑
			
			Intent mEdiThird=new Intent(this,UpdateActThirdStepActivity.class);
			Bundle bundle=new Bundle();
		
			bundle.putString(ConstantsTooTooEHelper.STORYID, storyIdArray);
//			LogUtil.systemlogInfo("跳到第三部", storyIdArray);
			bundle.putString(ConstantsTooTooEHelper.ACTIVITYID, activityId);
			getActivityName();
			if(!TextUtils.isEmpty(activityName)){
				bundle.putString(ConstantsTooTooEHelper.ACTIVITY_NAME, activityName);
			}else{
				bundle.putString(ConstantsTooTooEHelper.ACTIVITY_NAME, activityNameYuan);
			}
			if(isAgainPush){
				bundle.putBoolean("isAgainPush", true);
			}else{
				bundle.putBoolean("isAgainPush", false);
			}
			mEdiThird.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mEdiThird.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
			
			startActivity(mEdiThird);
		}else{
//			LogUtil.debug("跳到非编辑", storyIdArray);
			Intent intent3 = new Intent(
					CreateActSecondStepActivity.this,
					CreateActThirdStepActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(
					ConstantsTooTooEHelper.ACTIVITYID,
					secondStepStoryBean
							.getActivityId());
			getActivityName();
			bundle.putString(
					ConstantsTooTooEHelper.ACTIVITY_NAME,
					activityName);
			// intent3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.putExtra(
					ConstantsTooTooEHelper.BUNDLE,
					bundle);
			startActivity(intent3);
		}
		
	}
}
