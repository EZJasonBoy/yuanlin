package com.ninetowns.tootooplus.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.UpLoadFileBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.AlbumUtil;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PhotoSelectActivity extends BaseActivity {
	
	private Bitmap photoBmp;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.photo_select);
		
		//返回按钮
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.album_photo_title);
		
		//保存按钮
		TextView two_or_one_btn_head_second_tv = (TextView)findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
		two_or_one_btn_head_second_tv.setText(R.string.rainbo_sure);
		//为了让点击范围变大，所以点击外部的布局
		RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//开始上传头像 
				fileUpload();
			}
			
		});
		ImageView photo_select_iv = (ImageView)findViewById(R.id.photo_select_iv);
		//从相机页面过来传该参数
		Bundle bundle = getIntent().getBundleExtra("bundle");
		
		//从相册选择过来传该参数
		String photo_id = getIntent().getStringExtra("photo_id");
		if(photo_id != null){
			
			String photo_select_path = AlbumUtil.photoSelectInfo(this, photo_id);
			
			ImageLoader.getInstance().displayImage("file://" + photo_select_path , photo_select_iv, new ImageLoadingListener() {

                @Override
                public void onLoadingCancelled(String arg0, View arg1) {
                }

                @Override
                public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                    photoBmp = ImageUtil.cutSquareBmp(arg2);
                    ImageView imageView = (ImageView)arg1;
                    imageView.setImageBitmap(photoBmp);
                }

                @Override
                public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                }

                @Override
                public void onLoadingStarted(String arg0, View arg1) {
                }});
			
			
		} else {
			//获取相机返回的数据，并转换为图片格式
			String picPath = bundle.getString("picPath");
			Bitmap bmp = ImageUtil.getBitmapFromPath(picPath, this);
			photoBmp = ImageUtil.cutSquareBmp(bmp);
			photo_select_iv.setImageBitmap(photoBmp);
		}
		
	}
	
	
	/**
	 * 上传图片
	 */
	 private void fileUpload() {
        

		// StoryId：故事Id (必填)
		// PageType：故事页类型：1,文字，2图片，3视频 (必填)
		// PageContent：故事页文字
		// PageImgThumb：故事页缩略图
		// PageImg：故事页图片或直播录播封面图地址
		// PageVideoUrl：故事页录播视频地址
		// RecordId：录制Id
		// PageDesc：故事页描述

		// 创建封面图

		if ((NetworkUtil.isNetworkAvaliable(this))) {
			// 显示进度
			showProgressDialog(PhotoSelectActivity.this);
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.USERID, SharedPreferenceHelper.getLoginUserId(this));
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.APPLICATIONID, (TootooeNetApiUrlHelper.APPLICATIONID_PARAM));//2是图片3是视频
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE, TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE_PHOTO);
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.UPLOAD_FIRLE_WIDTH, "");
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.UPLOAD_FIRLE_HEIGHT, "");
			requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.UPLOAD_FIRLE_FLAG, TootooeNetApiUrlHelper.UPLOAD_FIRLE_FLAG_DEBUG);
			   try {
		        	String upFileName = ImageUtil.makePhotoName(new Date());
		        	FileOutputStream out = new FileOutputStream(upFileName);
		        	photoBmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		            File upFile = new File(upFileName);
		            requestParamsNet.addBodyParameter(TootooeNetApiUrlHelper.UPLOAD_FIRLE_FILE1, upFile);
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			
			
			CommonUtil.xUtilsPostSend(
					TootooeNetApiUrlHelper.UPLOAD_FIRLE,
					requestParamsNet, new RequestCallBack<String>() {
						
						

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String jsonStr =responseInfo.result;
							if(jsonStr!=null){
								  try {
					                    JSONObject jsobj = new JSONObject(jsonStr);
					                    if (jsobj.has("Status")) {
					                        String status = jsobj.getString("Status");
					                        if (status.equals("1")) {
					                            if (jsobj.has("Data")) {
					                            	UpLoadFileBean upLoad = new UpLoadFileBean();
					                                String datastr = jsobj.getString("Data");
					                                if (!TextUtils.isEmpty(datastr)) {
					                                    JSONObject objData = new JSONObject(datastr);
					                                    if (objData.has("FileUrl")) {
					                                        upLoad.setFileUrl(objData.getString("FileUrl"));
					                                    }
					                                    if (objData.has("ThumbFileUrl")) {
					                                        upLoad.setThumbFileUrl(objData.getString("ThumbFileUrl"));
					                                    }
					                                    
					                                    changeLogoUrl(upLoad.getThumbFileUrl());
					                                    
					                                } else {
					                                    ComponentUtil.showToast(PhotoSelectActivity.this, getResources().getString(R.string.upload_error));
					                                }
					                            }

					                        } else if(status.equals("1002")){
					                            ComponentUtil.showToast(PhotoSelectActivity.this, getResources().getString(R.string.upload_error_overbig));
					                        }else if(status.equals("1003")){
					                            ComponentUtil.showToast(PhotoSelectActivity.this, getResources().getString(R.string.upload_error_type));
					                        }

					                    }

					                } catch (JSONException e) {
					                    e.printStackTrace();
					                }
							}
							LogUtil.systemlogInfo("PhotoSelectActivity++++上传头像+++++>", jsonStr);
							              

						}

						@Override
						public void onFailure(HttpException error, String msg) {
		                	closeProgressDialog(PhotoSelectActivity.this);
		                	

		        }
					});

		} else {
			closeProgressDialog(PhotoSelectActivity.this);
			ComponentUtil.showToast(
					this,
					this.getResources().getString(
							R.string.errcode_network_response_timeout));
		}


  }
	 
	 /**
	  * 修改头像
	  * @param logo_url
	  */
	 private void changeLogoUrl(String logo_url){

	        

			// StoryId：故事Id (必填)
			// PageType：故事页类型：1,文字，2图片，3视频 (必填)
			// PageContent：故事页文字
			// PageImgThumb：故事页缩略图
			// PageImg：故事页图片或直播录播封面图地址
			// PageVideoUrl：故事页录播视频地址
			// RecordId：录制Id
			// PageDesc：故事页描述

			// 创建封面图

			if ((NetworkUtil.isNetworkAvaliable(this))) {
				// 显示进度
				showProgressDialog(PhotoSelectActivity.this);
				RequestParamsNet requestParamsNet = new RequestParamsNet();
				requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_LOGO_USERID, SharedPreferenceHelper.getLoginUserId(PhotoSelectActivity.this));
				requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_LOGO_LOGOURL, logo_url);
				
				CommonUtil.xUtilsPostSend(
						TootooeNetApiUrlHelper.CHANGE_LOGO_URL,
						requestParamsNet, new RequestCallBack<String>() {
							
							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								String jsonStr =responseInfo.result;
								if(jsonStr!=null){
									try {
										JSONObject jsonObj = new JSONObject(jsonStr);
										String change_status = "";
										if(jsonObj.has("Status")){
											change_status = jsonObj.getString("Status");
										}
										
										String bg_logo_url = "";
										if(jsonObj.has("Data")){
											JSONObject jsonData = jsonObj.getJSONObject("Data");
											if(jsonData.has("LogoUrl")){
												bg_logo_url = jsonData.getString("LogoUrl");
											}
										}
										
										if(change_status.equals("1")){
											ComponentUtil.showToast(PhotoSelectActivity.this, getResources().getString(R.string.upload_success));
					                      	//上传成功的处理
											SharedPreferenceHelper.changeLoginLogoUrl(bg_logo_url, PhotoSelectActivity.this);
					                      	//跳到上一级页面
//											Intent info_intent = new Intent(PhotoSelectActivity.this, PersonalInfoActivity.class);
//											info_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//											startActivity(info_intent);
//											finish();
											
										} else {
											ComponentUtil.showToast(PhotoSelectActivity.this, getResources().getString(R.string.upload_error));
										}
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								              

							}

							@Override
							public void onFailure(HttpException error, String msg) {
			                	closeProgressDialog(PhotoSelectActivity.this);
			                	

			        }
						});

			} else {
				closeProgressDialog(PhotoSelectActivity.this);
				ComponentUtil.showToast(
						this,
						this.getResources().getString(
								R.string.errcode_network_response_timeout));
			}


	  
		 
		 
		 
	 
  }


}