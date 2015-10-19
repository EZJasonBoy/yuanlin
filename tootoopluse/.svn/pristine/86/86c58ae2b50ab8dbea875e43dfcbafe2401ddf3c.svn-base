package com.ninetowns.tootoopluse.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.UpLoadFileBean;
import com.ninetowns.tootoopluse.fragment.UpLoadViewDialogFragment;
import com.ninetowns.tootoopluse.helper.ImageHttpMultipartPostHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.helper.UploadPicPopup;
import com.ninetowns.tootoopluse.helper.UploadPicPopup.CameraAndLocalListener;
import com.ninetowns.tootoopluse.util.AlbumUtil;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
/**
 * 店铺认证
 * @author huangchao
 *
 */
public class ShopCertificationActivity extends BaseActivity {
	
	/**个人身份证正面**/
	private ImageView per_IDCard_front_iv;
	/**个人身份证反面**/
	private ImageView per_IDCard_reverse_iv;
	/**企业营业执照**/
	private ImageView shop_businessLicence_iv;
	/**食物流通许可证**/
	private ImageView shop_foodCirculationPermits_iv;
	/**食物生产许可证**/
	private ImageView shop_foodProductionLicence_iv;
	/**食物卫生许可证**/
	private ImageView shop_foodHygieneLicence_iv;
	/**企业身份证正面**/
	private ImageView shop_IDCard_front_iv;
	/**企业身份证反面**/
	private ImageView shop_IDCard_reverse_iv;
	
	private ImageView finalImageView;
	
	private String finalPicPath = "";
	//判断是本地图片还是相机
	private String uploadType = "";
	
	private UpLoadViewDialogFragment fragment;
	
	private Map<Integer, UpLoadFileBean> per_upMap = new HashMap<Integer, UpLoadFileBean>();
	
	private Map<Integer, UpLoadFileBean> shop_upMap = new HashMap<Integer, UpLoadFileBean>();
	
	
	public Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				List<UpLoadFileBean> list = (List<UpLoadFileBean>) msg.obj;
				if(msg.arg1 == 6 || msg.arg1 == 7){
					per_upMap.put(msg.arg1, list.get(0));
				} else {
					shop_upMap.put(msg.arg1, list.get(0));
				}
				
				break;
			}
		};
		
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.shop_certification);
		init();
		
	}

	/**
	 * 初始化控件
	 */
	private void init(){
		Intent intent = getIntent();
		String comeFrom = intent.getStringExtra("comeFrom");
		
		//返回
		LinearLayout two_or_one_btn_head_back = (LinearLayout)findViewById(R.id.two_or_one_btn_head_back);
		if(comeFrom == null){
			two_or_one_btn_head_back.setVisibility(View.INVISIBLE);
		} else {
			two_or_one_btn_head_back.setVisibility(View.VISIBLE);
			two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.shop_cert_title);
		if(comeFrom == null){
			RelativeLayout two_or_one_btn_head_second_layout = (RelativeLayout)findViewById(R.id.two_or_one_btn_head_second_layout);
			two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//跳过
					Intent intent = new Intent(ShopCertificationActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				}
			});
			
			TextView two_or_one_btn_head_second_tv = (TextView)findViewById(R.id.two_or_one_btn_head_second_tv);
			two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
			two_or_one_btn_head_second_tv.setText(R.string.shop_cert_jump);
		}
		
		
		/**企业提交审核**/
		TextView shop_cert_submit_verify = (TextView)findViewById(R.id.shop_cert_submit_verify);
		shop_cert_submit_verify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(shop_upMap.size() < 6){
					ComponentUtil.showToast(ShopCertificationActivity.this, getResources().getString(R.string.cert_not_full_up));
				} else {
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_USERID, SharedPreferenceHelper.getLoginUserId(getApplicationContext()));
					//0表示个人，1表示企业
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_VERIFY_TYPE, "1");
					
					for(int key: shop_upMap.keySet()){
						if(key == 0){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_BUSINESS_LICENCE, shop_upMap.get(key).getThumbFileUrl());
						} else if(key == 1){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_FOOD_CIRCULATION_PERMITS, shop_upMap.get(key).getThumbFileUrl());
						} else if(key == 2){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_FOOD_PRODUCTION_LICENCE, shop_upMap.get(key).getThumbFileUrl());
						} else if(key == 3){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_FOOD_HYGIENE_LICENCE, shop_upMap.get(key).getThumbFileUrl());
						} else if(key == 4){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_IDCARD_FRONT, shop_upMap.get(key).getThumbFileUrl());
						} else if(key == 5){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_IDCARD_REVERSE, shop_upMap.get(key).getThumbFileUrl());
						}
						
					}
					
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SHOP_CERT_URL, requestParamsNet, new RequestCallBack<String>() {
						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							// TODO Auto-generated method stub
							String verifyStr = new String(responseInfo.result);
							try {
								JSONObject verifyJson = new JSONObject(verifyStr);
								if(verifyJson.has("Status")){
									String status = verifyJson.getString("Status");
									if(status.equals("1")){
										//认证成功
										ComponentUtil.showToast(ShopCertificationActivity.this, getResources().getString(R.string.shop_cert_success));
										
										Intent intent = new Intent(ShopCertificationActivity.this, HomeActivity.class);
										startActivity(intent);
										finish();
										
									} else {
										ComponentUtil.showToast(ShopCertificationActivity.this, getResources().getString(R.string.cert_fail));
									}
									
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub
						}
					});
					
				}
			}
		});
		
		
		/**个人提交审核**/
		TextView per_submit_verify = (TextView)findViewById(R.id.per_submit_verify);
		per_submit_verify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(per_upMap.size() < 2){
					ComponentUtil.showToast(ShopCertificationActivity.this, getResources().getString(R.string.cert_not_full_up));
				} else {
					RequestParamsNet requestParamsNet = new RequestParamsNet();
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_USERID, SharedPreferenceHelper.getLoginUserId(getApplicationContext()));
					//0表示个人，1表示企业
					requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_VERIFY_TYPE, "0");
					
					for(int key: per_upMap.keySet()){
						if(key == 6){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_IDCARD_FRONT, per_upMap.get(key).getThumbFileUrl());
						}else if(key == 7){
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.SHOP_CERT_IDCARD_REVERSE, per_upMap.get(key).getThumbFileUrl());
						}
						
					}
					
					CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.SHOP_CERT_URL, requestParamsNet, new RequestCallBack<String>() {
								@Override
								public void onSuccess(ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									String verifyStr = new String(responseInfo.result);
									try {
										JSONObject verifyJson = new JSONObject(verifyStr);
										if(verifyJson.has("Status")){
											String status = verifyJson.getString("Status");
											if(status.equals("1")){
												//认证成功
												Intent intent = new Intent(ShopCertificationActivity.this, HomeActivity.class);
												startActivity(intent);
												finish();
											} else {
												ComponentUtil.showToast(ShopCertificationActivity.this, getResources().getString(R.string.cert_fail));
											}
											
										}
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
								}

								@Override
								public void onFailure(HttpException error, String msg) {
									// TODO Auto-generated method stub
								}
							});
					
					
				}
				
			}
		});
//		//由于不提供个人认证，所以店铺认证一直展开状态		
//		//企业认证父布局
//		LinearLayout shop_cert_shop_group_layout = (LinearLayout)findViewById(R.id.shop_cert_shop_group_layout);
//		//企业认证子布局
		final LinearLayout shop_cert_shop_child_layout = (LinearLayout)findViewById(R.id.shop_cert_shop_child_layout);
		shop_cert_shop_child_layout.setVisibility(View.VISIBLE);
//		
//		shop_cert_shop_group_layout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//显示还是隐藏子布局
//				if(shop_cert_shop_child_layout.getVisibility() == View.GONE){
//					shop_cert_shop_child_layout.setVisibility(View.VISIBLE);
//				} else {
//					shop_cert_shop_child_layout.setVisibility(View.GONE);
//				}
//				
//			}
//		});
		
		//个人认证父布局
		LinearLayout shop_cert_per_group_layout = (LinearLayout)findViewById(R.id.shop_cert_per_group_layout);
		//暂时不提供个人认证
		shop_cert_per_group_layout.setVisibility(View.GONE);
		
		//个人认证子布局
		final LinearLayout shop_cert_per_child_layout = (LinearLayout)findViewById(R.id.shop_cert_per_child_layout);
		//默认情况下隐藏
		shop_cert_per_child_layout.setVisibility(View.GONE);
		shop_cert_per_group_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(shop_cert_per_child_layout.getVisibility() == View.GONE){
					shop_cert_per_child_layout.setVisibility(View.VISIBLE);
				} else {
					shop_cert_per_child_layout.setVisibility(View.GONE);
				}
				
			}
		});
		
		TextView shop_businessLicence_upload_tv = (TextView)findViewById(R.id.shop_businessLicence_upload_tv);
		shop_businessLicence_iv = (ImageView)findViewById(R.id.shop_businessLicence_iv);
		shop_businessLicence_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showUploadView(0);
			}
		});
		
		TextView shop_foodCirculationPermits_upload_tv = (TextView)findViewById(R.id.shop_foodCirculationPermits_upload_tv);
		shop_foodCirculationPermits_iv = (ImageView)findViewById(R.id.shop_foodCirculationPermits_iv);
		shop_foodCirculationPermits_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showUploadView(1);
			}
		});
		
		TextView shop_foodProductionLicence_upload_tv = (TextView)findViewById(R.id.shop_foodProductionLicence_upload_tv);
		shop_foodProductionLicence_iv = (ImageView)findViewById(R.id.shop_foodProductionLicence_iv);
		shop_foodProductionLicence_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showUploadView(2);
			}
		});
		
		TextView shop_foodHygieneLicence_upload_tv = (TextView)findViewById(R.id.shop_foodHygieneLicence_upload_tv);
		shop_foodHygieneLicence_iv = (ImageView)findViewById(R.id.shop_foodHygieneLicence_iv);
		shop_foodHygieneLicence_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showUploadView(3);
			}
		});
		
		TextView shop_IDCard_front_upload_tv = (TextView)findViewById(R.id.shop_IDCard_front_upload_tv);
		shop_IDCard_front_iv = (ImageView)findViewById(R.id.shop_IDCard_front_iv);
		shop_IDCard_front_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showUploadView(4);
			}
		});
		
		TextView shop_IDCard_reverse_upload_tv = (TextView)findViewById(R.id.shop_IDCard_reverse_upload_tv);
		shop_IDCard_reverse_iv = (ImageView)findViewById(R.id.shop_IDCard_reverse_iv);
		shop_IDCard_reverse_upload_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showUploadView(5);
			}
		});
		
		
		per_IDCard_front_iv = (ImageView)findViewById(R.id.per_IDCard_front_iv);
		per_IDCard_front_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showUploadView(6);
			}
		});
		
		per_IDCard_reverse_iv = (ImageView)findViewById(R.id.per_IDCard_reverse_iv);
		per_IDCard_reverse_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showUploadView(7);
			}
		});
	}
	
	
	/**
	 * 显示上传底部样式
	 */
	public void showUploadView(final int requestCode){
		WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		int screen_width = wm.getDefaultDisplay().getWidth();
		//屏幕高度
		int screen_height = wm.getDefaultDisplay().getHeight();
		UploadPicPopup uploadPicPopup = new UploadPicPopup(this, screen_width, screen_height);
		uploadPicPopup.setCameraAndLocalListener(new CameraAndLocalListener() {
			
			@Override
			public void onLocal() {
				//本地图片
				uploadType = "local";
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
		        intent.setType("image/*");  
		        intent.putExtra("return-data", true);  
		        startActivityForResult(intent, requestCode);  
				
			}
			
			@Override
			public void onCamera() {
				//拍照
				uploadType = "camera";
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri imageUri = Uri.fromFile(new File(ImageUtil.getTempPhotoPath()));
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra(ImageColumns.ORIENTATION, 0);
				startActivityForResult(intent, requestCode);
				
			}
		});
		
		
	}
	
	
	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		
        if(arg1 == Activity.RESULT_OK){
        	
        	if(arg2 != null && uploadType.equals("local")){
        		String picPath = AlbumUtil.getPicPath(this, arg2);
                cutImage(arg0, picPath);
        	} else {
        		cutImage(arg0, "");
        	}
        	
        	
		}
	}
	
	/**
	 * 
	 * @Title: PhotoCameraActivity.java
	 * @Description: 防止某些手机拍照倒立
	 * @author wuyulong
	 * @date 2014-12-19 下午12:40:37
	 * @param
	 * @return void
	 */
	private void cutImage(final int iv_mark, String picPath) {
		ImageLoader.getInstance().clearMemoryCache();
		ImageLoader.getInstance().clearDiskCache();
		if(iv_mark == 0){
			finalImageView = shop_businessLicence_iv;
		} else if(iv_mark == 1){
			finalImageView = shop_foodCirculationPermits_iv;
		} else if(iv_mark == 2){
			finalImageView = shop_foodProductionLicence_iv;
		} else if(iv_mark == 3){
			finalImageView = shop_foodHygieneLicence_iv;
		} else if(iv_mark == 4){
			finalImageView = shop_IDCard_front_iv;
		} else if(iv_mark == 5){
			finalImageView = shop_IDCard_reverse_iv;
		} else if(iv_mark == 6){
			finalImageView = per_IDCard_front_iv;
		} else if(iv_mark == 7){
			finalImageView = per_IDCard_reverse_iv;
		}
		if(picPath.equals("")){
			finalPicPath = ImageUtil.getTempPhotoPath();
		} else {
			finalPicPath = picPath;
		}
		ImageLoader.getInstance().displayImage("file://" + finalPicPath, finalImageView,
				new SimpleImageLoadingListener() {

					private Bitmap bmp;

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						int angle = ImageUtil.readPictureDegree(finalPicPath);
						if (angle == 0) {
							bmp = arg2;
						} else {
							// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
							Matrix m = new Matrix();
							int width = arg2.getWidth();
							int height = arg2.getHeight();
							m.setRotate(angle); // 旋转angle度
							bmp = Bitmap.createBitmap(arg2, 0, 0, width,
									height, m, true);// 从新生成图片
						}
						if (bmp != null) {
							finalImageView.setImageBitmap(bmp);
							
							upCameraPic(bmp, iv_mark);
							
						}

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
					}

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}
				});
	}
	
	
	/**
	 * 上传照相机拍到的照片
	 * @param iv_mark
	 */
	private void upCameraPic(Bitmap bmp, int iv_mark){
		
		final String upFileName = ImageUtil.makePhotoName(new Date());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(upFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		List<File> listFile = new ArrayList<File>();
		File upFile1 = new File(upFileName);
		listFile.add(upFile1);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TootooeNetApiUrlHelper.UPLOAD_FILE_TYPE, TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE_PHOTO);
        map.put(TootooeNetApiUrlHelper.UPLOAD_FILE_APPLICATIONID, TootooeNetApiUrlHelper.APPLICATIONID_PARAM);
        String useridlocal = SharedPreferenceHelper.getLoginUserId(getApplicationContext());
        map.put(TootooeNetApiUrlHelper.UPLOAD_FILE_USERID, useridlocal);
        
        ImageHttpMultipartPostHelper uploadImages = new ImageHttpMultipartPostHelper(this,
                CommonUtil.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_FIRLE), listFile,
                map, handler, iv_mark);
        uploadImages.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

	}
}
