package com.ninetowns.tootoopluse.fragment;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.MyTextwatcherUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.CreateActSecondStepActivity;
import com.ninetowns.tootoopluse.activity.CreateWishActivity;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.CreateActivitySecondBean;
import com.ninetowns.tootoopluse.bean.PhotoSelectOrConvertBean;
import com.ninetowns.tootoopluse.bean.SecondStepStoryBean;
import com.ninetowns.tootoopluse.bean.StoryDetailListBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;

/**
 * 
 * @Title: TextRecommendContentStoryCreateFragment.java
 * @Description: 创建推荐故事文字类型的界面
 * @author wuyulong
 * @date 2015-1-8 上午11:21:41
 * @version V1.0
 */
public class TextRecommendContentStoryCreateFragment extends
		TextContentStoryCreateFragment implements View.OnClickListener,
		TextContentStoryCreateFragment.EditextSizeInterface {
	private Bundle bundle;
	private boolean isEditextView;
	private boolean isCreateView;
	private boolean isDraftView;
	private boolean isRecommendView;
	private boolean isRecommendConvertView;
	private PhotoSelectOrConvertBean isCreateViewPhotoConvertBean;
	private String storyid;
	private String content;
	private List<StoryDetailListBean> isCreateListBean;
	private String isTitle;
	private String UpdateStoryId;
	private boolean isActivitySecondView;
	private int currentPosition;
	private SecondStepStoryBean secondStepStoryBean;
	private CreateActivitySecondBean storyBean;
	private HashMap<String, Integer> mHashMap;

	@Override
	public void getType() {
		bundle = this.getActivity().getIntent()
				.getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		isEditextView = bundle.getBoolean(ConstantsTooTooEHelper.isEditextView);
		isCreateView = bundle.getBoolean(ConstantsTooTooEHelper.isCreateView);
		isDraftView = bundle.getBoolean(ConstantsTooTooEHelper.isDraftView);
		isRecommendView = bundle
				.getBoolean(ConstantsTooTooEHelper.isRecommendView);
		isRecommendConvertView = bundle
				.getBoolean(ConstantsTooTooEHelper.isConvertRecommendView);

		storyid = bundle.getString("storyid");
		isTitle = bundle.getString("isTitle");
		if (isEditextView || isRecommendView) {
			UpdateStoryId = bundle.getString("UpdateStoryId");
			isCreateListBean = isCreateViewPhotoConvertBean.getListBean();
			isCreateViewPhotoConvertBean = (PhotoSelectOrConvertBean) bundle
					.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
			mHashMap = isCreateViewPhotoConvertBean.getmHashMap();
		} else if (isCreateView) {
			UpdateStoryId = bundle.getString("UpdateStoryId");
			if (!TextUtils.isEmpty(UpdateStoryId) && UpdateStoryId.equals("-1")) {// 故事第二步
				isActivitySecondView = true;
				currentPosition = bundle.getInt("currentPosition");
				secondStepStoryBean = (SecondStepStoryBean) bundle
						.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
				mHashMap=secondStepStoryBean.getmHashMap();
				storyBean = secondStepStoryBean.getStoryList().get(
						currentPosition);
				isCreateListBean = storyBean.getWishDetailBean();
			} else {
				isCreateViewPhotoConvertBean = (PhotoSelectOrConvertBean) bundle
						.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
				mHashMap = isCreateViewPhotoConvertBean.getmHashMap();
				storyid = bundle.getString("storyid");
				UpdateStoryId = bundle.getString("UpdateStoryId");
				isCreateListBean = isCreateViewPhotoConvertBean.getListBean();
			}
		} else {
			isCreateListBean = isCreateViewPhotoConvertBean.getListBean();
			isCreateViewPhotoConvertBean = (PhotoSelectOrConvertBean) bundle
					.getSerializable(ConstantsTooTooEHelper.BundleResopnse);
			mHashMap = isCreateViewPhotoConvertBean.getmHashMap();
		}
		this.setOnEditextChangeTextSizeListener(this);

	}

	public void setSize(EditText let, TextView tv) {
		if(!TextUtils.isEmpty(isTitle)){
			if(isTitle.equals(ConstantsTooTooEHelper.ONE_TITLE)){

				let.addTextChangedListener(new MyTextwatcherUtil(
						this.getActivity(), let, tv, 20));// 
				let.setHint(TootooPlusEApplication.getAppContext().getResources()
						.getString(R.string.editor_text_num_twenty));
			
			}else if(isTitle.equals(ConstantsTooTooEHelper.SECOND_TITLE)){

				let.addTextChangedListener(new MyTextwatcherUtil(
						this.getActivity(), let, tv, ConstantsTooTooEHelper.TWELVE));// 最多输入12个字符
				let.setHint(TootooPlusEApplication.getAppContext().getResources()
						.getString(R.string.editor_text_num_twlever));
			}else if(isTitle.equals(ConstantsTooTooEHelper.GAOLIANG)){


				let.addTextChangedListener(new MyTextwatcherUtil(
						this.getActivity(), let, tv, ConstantsTooTooEHelper.TWO_HANDRED));// 最多输入200个字符
				let.setHint(TootooPlusEApplication.getAppContext().getResources()
						.getString(R.string.editor_text_num_twohandrand));
			
			
			}else if(isTitle.equals(ConstantsTooTooEHelper.CONTENT)){

				let.addTextChangedListener(new MyTextwatcherUtil(
						this.getActivity(), let, tv, ConstantsTooTooEHelper.FIVE_HANDRED));// 最多输入500个字符
				let.setHint(TootooPlusEApplication.getAppContext().getResources()
						.getString(R.string.editor_text_num_fivehandrand));
			
			}
				
		}
		

	}

	@Override
	public RequestParamsNet initParam() {
		RequestParamsNet params = new RequestParamsNet();
		params.addQueryStringParameter(
				TootooeNetApiUrlHelper.STORYCREATE_STORYID, storyid);
		params.addQueryStringParameter(
				TootooeNetApiUrlHelper.STORYCREATE_PAGE_TYPE, "1");
		params.addQueryStringParameter(
				TootooeNetApiUrlHelper.STORYCREATE_PAGE_CONTENT, editorContent);

		if(!TextUtils.isEmpty(isTitle)){
			if(isTitle.equals(ConstantsTooTooEHelper.ONE_TITLE)){
				params.addQueryStringParameter(TootooeNetApiUrlHelper.FontSize,ConstantsTooTooEHelper.ONE_TITLE);
			
			}else if(isTitle.equals(ConstantsTooTooEHelper.SECOND_TITLE)){
				params.addQueryStringParameter(TootooeNetApiUrlHelper.FontSize,ConstantsTooTooEHelper.SECOND_TITLE);
			}else if(isTitle.equals(ConstantsTooTooEHelper.GAOLIANG)){
				params.addQueryStringParameter(TootooeNetApiUrlHelper.FontSize,ConstantsTooTooEHelper.GAOLIANG);
			}else if(isTitle.equals(ConstantsTooTooEHelper.CONTENT)){	
				params.addQueryStringParameter(TootooeNetApiUrlHelper.FontSize, ConstantsTooTooEHelper.CONTENT);
				}
				
		}else{
			params.addQueryStringParameter(TootooeNetApiUrlHelper.FontSize, ConstantsTooTooEHelper.CONTENT);
		}
		

	
		
		return params;
	}

	@Override
	public void saveText(String content) {
		this.content = content;
		postData();

	}

	private int index = 0;
	private int indey=0;
	private int itemIndewx;
	private StoryDetailListBean heightItem;

	@Override
	public void getPageId(JSONObject storyjsonobj) throws JSONException {// 此方法是点击保存之后上传完数据之后的回调
		if (storyjsonobj != null) {
			String pageid = storyjsonobj.getString("PageId");
			StoryDetailListBean storyDetailListBean = new StoryDetailListBean();

			if(!TextUtils.isEmpty(isTitle)){
				if(isTitle.equals(ConstantsTooTooEHelper.ONE_TITLE)){
					storyDetailListBean.setFontSize(Integer.valueOf(ConstantsTooTooEHelper.ONE_TITLE));
				
				}else if(isTitle.equals(ConstantsTooTooEHelper.SECOND_TITLE)){
					storyDetailListBean.setFontSize(Integer.valueOf(ConstantsTooTooEHelper.SECOND_TITLE));
				}else if(isTitle.equals(ConstantsTooTooEHelper.GAOLIANG)){
					storyDetailListBean.setFontSize(Integer.valueOf(ConstantsTooTooEHelper.GAOLIANG));
				}else if(isTitle.equals(ConstantsTooTooEHelper.CONTENT)){
					storyDetailListBean.setFontSize(Integer.valueOf(ConstantsTooTooEHelper.CONTENT));
				}
					
			}else{
				storyDetailListBean.setFontSize(Integer.valueOf(ConstantsTooTooEHelper.CONTENT));
			}
			storyDetailListBean.setPageType("1");
			storyDetailListBean.setPageId(pageid);
			storyDetailListBean.setPageContent(content);
			if (mHashMap != null) {
				int instertPosition = mHashMap
						.get(ConstantsTooTooEHelper.POSITION_KEY);
				if (instertPosition != ConstantsTooTooEHelper.NO_ITEM_CLICK_INSERT_CODE) {// 要插入某个位置的下面
					insertPosition(storyDetailListBean, instertPosition);
				} else {// 插入到底部
					insertBottom(storyDetailListBean);
				}

			} else {
				insertBottom(storyDetailListBean);
			}
//			insertBottom(storyDetailListBean);
			LogUtil.systemlogInfo("PageId", pageid);
			if (isActivitySecondView) {
				storyBean.setWishDetailBean(isCreateListBean);
			} else {
				isCreateViewPhotoConvertBean.setListBean(isCreateListBean);
			}

			if (isEditextView || isRecommendView) {
				Intent intent = new Intent(this.getActivity(),
						CreateWishActivity.class);
				bundle.putSerializable(ConstantsTooTooEHelper.BundleResopnse,
						isCreateViewPhotoConvertBean);
				bundle.putString("UpdateStoryId", UpdateStoryId);
				ConstantsTooTooEHelper.putView(
						ConstantsTooTooEHelper.isRecommendView, bundle);
				bundle.putString("storyid", storyid);
				intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				if (isActivitySecondView) {
					Intent intent = new Intent(
							TootooPlusEApplication.getAppContext(),
							CreateActSecondStepActivity.class);
					bundle.putSerializable(
							ConstantsTooTooEHelper.BundleResopnse,
							secondStepStoryBean);
					bundle.putString("UpdateStoryId", UpdateStoryId);
					bundle.putString("storyid", storyid);
					bundle.putInt("currentPosition", currentPosition);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					Intent intent = new Intent(this.getActivity(),
							CreateWishActivity.class);
					bundle.putSerializable(
							ConstantsTooTooEHelper.BundleResopnse,
							isCreateViewPhotoConvertBean);
					ConstantsTooTooEHelper.putView(
							ConstantsTooTooEHelper.isConvertRecommendView,
							bundle);
					bundle.putString("storyid", storyid);
					intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}

			}

			// dd
			if (editext != null) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editext.getWindowToken(), 0);
			}

		}

	}

	/** 
	* @Title: insertBottom 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void insertBottom(StoryDetailListBean storyDetailListBean) {
		int listSize = isCreateListBean.size();

		for (int i = 0; i < listSize; i++) {
			if (isCreateListBean.get(i).getItemIndex() >= isCreateListBean
					.get(indey).getItemIndex()) {
				indey = i;
			}
		}
		if (listSize > 0) {
			heightItem = isCreateListBean.get(indey);
			itemIndewx = heightItem.getItemIndex();
		}
		int itemIndexQ = itemIndewx;
		if (listSize == 0) {
			storyDetailListBean.setLocation(1);
			storyDetailListBean.setItemIndex(0);
			storyDetailListBean.setElementType(1);
		} else {
			if (heightItem != null) {
				storyDetailListBean.setElementType(1);
				storyDetailListBean.setLocation(1);
				storyDetailListBean.setItemIndex(itemIndexQ + 1);
			}

		}

		isCreateListBean.add(storyDetailListBean);
	}
	private void insertPosition(StoryDetailListBean storyDetailListBean,
			int position) {
		if(isCreateListBean==null){
			return;
		}
		int listSize = isCreateListBean.size();
		StoryDetailListBean item = isCreateListBean.get(position);
		itemIndewx = item.getItemIndex();
		int itemIndexQ = itemIndewx;
		if (listSize == 0) {
			storyDetailListBean.setLocation(1);
			storyDetailListBean.setItemIndex(0);
			storyDetailListBean.setElementType(1);
		} else {
				storyDetailListBean.setElementType(1);
				storyDetailListBean.setLocation(1);
				storyDetailListBean.setItemIndex(itemIndexQ + 1);

		}
		isCreateListBean.add(position+1, storyDetailListBean);
	}
	@Override
	public void backview() {
		getActivity().finish();
	}

	private EditText editext;

	@Override
	public void OnEditextChangeTextSizeListener(final EditText editext) {
		this.editext = editext;
		
		if(!TextUtils.isEmpty(isTitle)){
			if(isTitle.equals(ConstantsTooTooEHelper.ONE_TITLE)){
				editext.setTextSize(CommonUtil.convertPxToDp(getActivity(),
						(int) this.getResources().getDimension(R.dimen.h0)));// 标题
			
			}else if(isTitle.equals(ConstantsTooTooEHelper.SECOND_TITLE)){
				editext.setTextSize(CommonUtil.convertPxToDp(getActivity(),
						(int) this.getResources().getDimension(R.dimen.h1))); // 副标题
			}else if(isTitle.equals(ConstantsTooTooEHelper.GAOLIANG)){
				editext.setTextSize(CommonUtil.convertPxToDp(getActivity(),
						(int) this.getResources().getDimension(R.dimen.h4))); // 正文
			}else if(isTitle.equals(ConstantsTooTooEHelper.CONTENT)){
				editext.setTextSize(CommonUtil.convertPxToDp(getActivity(),
						(int) this.getResources().getDimension(R.dimen.h4))); // 正文
			}
				
		}else{
			editext.setTextSize(CommonUtil.convertPxToDp(getActivity(),
					(int) this.getResources().getDimension(R.dimen.h4))); // 正文
		}
		editext.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				onFocusChangeAction(editext.isFocused());
			}

			public void onFocusChangeAction(boolean hasFocus) {
				final boolean isFocus = hasFocus;
				(new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() {
						InputMethodManager imm = (InputMethodManager) editext
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						if (isFocus) {
							imm.toggleSoftInput(0,
									InputMethodManager.HIDE_NOT_ALWAYS);
						} else {
							imm.hideSoftInputFromWindow(
									editext.getWindowToken(), 0);
						}
					}
				}, 100);
			}
		});
	}

}
