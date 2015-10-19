package com.ninetowns.tootooplus.fragment;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.ChangeNickActivity;
import com.ninetowns.tootooplus.activity.ChangePwdActivity;
import com.ninetowns.tootooplus.activity.FollowFansActivity;
import com.ninetowns.tootooplus.adapter.HelpInfoAdapter;
import com.ninetowns.tootooplus.bean.PersonInfoDetailBean;
import com.ninetowns.tootooplus.bean.PersonInfoDetailBean.HelpInfo;
import com.ninetowns.tootooplus.bean.PersonInfoDetailBean.UserInfo;
import com.ninetowns.tootooplus.bean.UpLoadFileBean;
import com.ninetowns.tootooplus.helper.ImageHttpMultipartPostHelper;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.helper.UploadPicPopup;
import com.ninetowns.tootooplus.helper.UploadPicPopup.CameraAndLocalListener;
import com.ninetowns.tootooplus.parser.PersonInfoParser;
import com.ninetowns.tootooplus.photoview.PerInfoVipView;
import com.ninetowns.tootooplus.util.AlbumUtil;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/** 
* @ClassName: PersonInfoFragment 
* @Description:个人中心详情页面
* @author zhou
* @date 2015-4-17 上午11:22:02 
*  
*/
public class PersonInfoDetailFragment extends BaseFragment<PersonInfoDetailBean, PersonInfoParser> implements INetConstanst{
	private HelpInfoAdapter mHelpAdapter;
	//判断是本地图片还是相机
	private String uploadType = "";
	
	private ImageView finalImageView;
	
	private DisplayImageOptions finalDisplayImageOptions = null;
	
	@ViewInject(R.id.person_info_llexpandlv)
	private com.ninetowns.ui.widget.LinearLayoutExpandListView mLLExpandListView;
	/** 
	* @Fields mHeadphotoImageView : 头像
	*/ 
	@ViewInject(R.id.per_home_head_photo)
	private ImageView mHeadphotoImageView;
	
	@ViewInject(R.id.per_info_all_trans_layout)
	private LinearLayout per_info_all_trans_layout;

	
	/** 
	* @Title: modifyHeadphoto 
	* @Description: 修改头像
	* @param @param v    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@OnClick(R.id.per_home_head_photo)
	public void modifyHeadphoto(View v){
		showUploadView(6, per_info_all_trans_layout);
	}
	
	/** 
	* @Fields mHeadLayoutBackgoudImageView : 头部背景图片
	*/ 
	@ViewInject(R.id.per_home_head_cover_iv)
	private ImageView mHeadLayoutBackgoudImageView;
	/** 
	* @Fields mIconVip : vip等级的 图标
	*/ 
	@ViewInject(R.id.per_home_head_vip)
	private ImageView mIconVip;
	
	/** 
	* @Fields mNickNameTextView : 昵称
	*/ 
	@ViewInject(R.id.per_home_head_name)
	private TextView mNickNameTextView;
	
	/** 
	* @Fields mFansCount : 粉丝数量
	*/ 
	@ViewInject(R.id.per_home_head_fans_count)
	private TextView mFansCountTextView;
	
	/** 
	* @Title: enterFans 
	* @Description: 进入粉丝关注页面
	* @param @param v    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@OnClick(R.id.per_home_head_fans_count)
	public void enterFans(View v){
		Intent follow_intent = new Intent(mActivity, FollowFansActivity.class);
		follow_intent.putExtra("follow_or_fans", "fans");
		follow_intent.putExtra("other_userId", userId);
		startActivity(follow_intent);
	}
	
	/** 
	 * @Title: enterFans 
	 * @Description: 进入粉丝关注页面
	 * @param @param v    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	@OnClick(R.id.per_home_head_fans_count)
	public void enterFocus(View v){
		Intent follow_intent = new Intent(getActivity(), FollowFansActivity.class);
		follow_intent.putExtra("follow_or_fans", "follow");
		follow_intent.putExtra("other_userId", userId);
		startActivity(follow_intent);
	}
	
	
	@ViewInject(R.id.person_info_tv_modifypwd)
	private TextView person_infoModifypwd;
	
	@OnClick(R.id.person_info_tv_modifypwd)
	public void onModifyPwdClick(View view){
		UIUtils.startActivity(mActivity, "", ChangePwdActivity.class);
	}
	
	@ViewInject(R.id.framlayout_modifynick)
	private LinearLayout mModifyNickFrameLayout;
	@OnClick(R.id.framlayout_modifynick)
	public void onModifyNickNameClick(View v){
		Intent nick_intent = new Intent(getActivity(), ChangeNickActivity.class);
		nick_intent.putExtra("nick", mNickNameTextView.getText());
		startActivity(nick_intent);
	}
	/** 
	 * @Fields mMyFocusCount : 关注 的 数量
	 */ 
	@ViewInject(R.id.per_home_head_follow_count)
	private TextView mMyFocusCountTextView;
	
	@ViewInject(R.id.person_info_vip_layout)
	private PerInfoVipView mVipView;
	@ViewInject(R.id.person_info_tv_distancetouplevel)
	private TextView mDistanceToUpLevel;
	/** 
	* @Fields mLevelCount : vip点数
	*/ 
	@ViewInject(R.id.person_info_tv_levelcount)
	private TextView mLevelCount;
	/** 
	* @Title: onBackButtonClick 
	* @Description: 点击返回按钮 
	* @param @param v    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@OnClick(R.id.per_home_lv_head_back_layout)
	public void onBackButtonClick(View v){
		if(null!=mActivity){
			mActivity.finish();
		}
	}
	
	private Activity mActivity;
	private Resources  resources;
	private String userId;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mActivity=getActivity();
		resources=getResources();
		userId=SharedPreferenceHelper.getLoginUserId(mActivity);
		  super.onLoadData(true, false, false);
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 View mPersonInfoView=inflater.inflate(R.layout.person_info_fragment, null);
		 ViewUtils.inject(this, mPersonInfoView); // 注入view和事件
		 UIUtils.setViewVisible(mModifyNickFrameLayout);

		 //切换封面图
		 mHeadLayoutBackgoudImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				showUploadView(7, per_info_all_trans_layout);
			}
		});
		 
		
		 
		 return mPersonInfoView;
	}
	
	@Override
	public PersonInfoParser setParser(String str) {
		return new PersonInfoParser(str);
	}

	@Override
	public void getParserResult(PersonInfoDetailBean personInfoDetailBean) {
		if(personInfoDetailBean==null){
			 throw new IllegalArgumentException("personInfoDetailBean不能 为null");//抛出不合法的异常
		}
		final UserInfo userInfo=personInfoDetailBean.UserInfo;
		final List<HelpInfo>helpInfos =personInfoDetailBean.HelpInfoList;
		if(null!=userInfo){
			ImageLoader.getInstance().displayImage(userInfo.CoverImage, mHeadLayoutBackgoudImageView, CommonUtil.MINE_COVER_OPTIONS);
			ImageLoader.getInstance().displayImage(userInfo.LogoUrl, mHeadphotoImageView, CommonUtil.OPTIONS_BIG_HEADPHOTO);
			
			
			if(userInfo.Source != null && userInfo.Source.equals("phone")){
				person_infoModifypwd.setVisibility(View.VISIBLE);
			} else {
				person_infoModifypwd.setVisibility(View.GONE);
			}
			
			mNickNameTextView.setText(userInfo.UserName);
			mFansCountTextView.setText(userInfo.Fans);
			mMyFocusCountTextView.setText(userInfo.Attend);
			
			mVipView.setGradePercent(userInfo.GradePercent);
			//用户vip
    		CommonUtil.showCenterVip(mIconVip, userInfo.UserGrade);
    		
			if(!StringUtils.isEmpty(userInfo.TCurrencyDistance)){
				mDistanceToUpLevel.setText(String.format(resources.getString(R.string.distancetouplevel), Integer.valueOf(userInfo.TCurrencyDistance)));
			}else{
				setDefaultDistanceToUpLevel();
			}
			if(!TextUtils.isEmpty(userInfo.TCurrency)){
				mLevelCount.setText(String.format(resources.getString(R.string.levelcount),Integer.valueOf(userInfo.TCurrency) ));
			}else{
				setDefaultLevelCount();
			}
		}else{
			setDefaultLevelCount();
			setDefaultDistanceToUpLevel();
		}
		if(null!=helpInfos&&helpInfos.size()>0){
			refreshAdaper(helpInfos);
		}
	}
	private void setDefaultDistanceToUpLevel() {
		mLevelCount.setText(String.format(resources.getString(R.string.distancetouplevel),0));
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
        	
        	if(data != null && uploadType.equals("local")){
        		String picPath  = "";
				if (android.os.Build.MANUFACTURER.equals("Xiaomi")){
        			//如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
        			picPath = data.getData().getPath();
        		}else{
        			picPath = AlbumUtil.getPicPath(getActivity(), data);
        		}
                cutImage(requestCode, picPath);
        	} else {
        		cutImage(requestCode, ImageUtil.getTempPhotoPath());
        	}
		}
		
	}
	
	
	private void cutImage(final int iv_mark, final String picPath) {
		ImageLoader.getInstance().clearMemoryCache();
		ImageLoader.getInstance().clearDiskCache();
		if(iv_mark == 6){
			finalImageView = mHeadphotoImageView;
			finalDisplayImageOptions = CommonUtil.OPTIONS_BIG_HEADPHOTO;
		} else if(iv_mark == 7){
			finalImageView = mHeadLayoutBackgoudImageView;
			finalDisplayImageOptions = CommonUtil.MINE_COVER_OPTIONS;
		}
		
		ImageLoader.getInstance().displayImage("file://" + picPath, finalImageView, finalDisplayImageOptions,
				new SimpleImageLoadingListener() {

					private Bitmap bmp;

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						int angle = ImageUtil.readPictureDegree(picPath);
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
        String useridlocal = SharedPreferenceHelper.getLoginUserId(getActivity());
        map.put(TootooeNetApiUrlHelper.UPLOAD_FILE_USERID, useridlocal);
        
        if(iv_mark == 6){
        	map.put(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FILE_SCENARIOTYPE, "5");
        } else if(iv_mark == 7){
        	map.put(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FILE_SCENARIOTYPE, "1");
        }
        ImageHttpMultipartPostHelper uploadImages = null;
        
        uploadImages = new ImageHttpMultipartPostHelper(getActivity(),
                CommonUtil.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FIRLE), listFile,
                map, handler, iv_mark);
        
        uploadImages.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

	}
	
	public Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				List<UpLoadFileBean> list = (List<UpLoadFileBean>) msg.obj;
				if(msg.arg1 == 6){
					//修改头像处理
					changeUserLogo(list.get(0).getTailorSquareImg());
					
				} else if(msg.arg1 == 7){
					//修改封面图处理
					changeCoverImage(list.get(0).getListCoverImg());
				}

				break;
			}
		};
		
	};
	
	
	/**
	 * 修改封面图
	 */
	private void changeCoverImage(final String coverUrl){
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_COVER_IMAGE_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_COVER_IMAGE_COVER, coverUrl);
		CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CHANGE_COVER_IMAGE_URL, requestParamsNet, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String verifyStr = new String(responseInfo.result);
				try {
					JSONObject verifyJson = new JSONObject(verifyStr);
					if(verifyJson.has("Status")){
						String status = verifyJson.getString("Status");
						if(status.equals("1")){
							
							ImageLoader.getInstance().displayImage(coverUrl, mHeadLayoutBackgoudImageView, CommonUtil.MINE_COVER_OPTIONS);
							SharedPreferenceHelper.changeLoginCoverUrl(coverUrl, getActivity());
							ComponentUtil.showToast(getActivity(), getResources().getString(R.string.change_cover_success));
						} else {
							ComponentUtil.showToast(getActivity(), getResources().getString(R.string.change_cover_fail));
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

	/**
	 * 修改头像
	 */
	private void changeUserLogo(final String logoUrl){
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_LOGO_USERID, userId);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.CHANGE_LOGO_LOGOURL, logoUrl);
		CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.CHANGE_LOGO_URL, requestParamsNet, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String verifyStr = new String(responseInfo.result);
				try {
					JSONObject verifyJson = new JSONObject(verifyStr);
					if(verifyJson.has("Status")){
						String status = verifyJson.getString("Status");
						if(status.equals("1")){
							
							ImageLoader.getInstance().displayImage(logoUrl, mHeadphotoImageView, CommonUtil.OPTIONS_BIG_HEADPHOTO);
							//修改存到本地的数据
							SharedPreferenceHelper.changeLoginLogoUrl(logoUrl, mActivity);
							
							ComponentUtil.showToast(mActivity, resources.getString(R.string.change_logo_success));
						} else {
							ComponentUtil.showToast(mActivity,resources.getString(R.string.change_logo_fail));
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
	
	

	/** 
	* @Title: refreshAdaper 
	* @Description:刷新数据
	* @param @param helpInfos    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void refreshAdaper(List<HelpInfo> helpInfos) {
		if(null==mHelpAdapter){
			mHelpAdapter = new HelpInfoAdapter(mActivity, helpInfos);
			mLLExpandListView.setLayoutAdapter(mHelpAdapter);
		}else{
			mHelpAdapter.notifyDataSetChanged();
		}
		
		
	}
	private void setDefaultLevelCount() {
		mLevelCount.setText(String.format(resources.getString(R.string.levelcount),0));
	}

	@Override
	public RequestParamsNet getApiParmars() {
		RequestParamsNet requestParamsNet=new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(GET_USERINFO_DETAIL);
		requestParamsNet.addQueryStringParameter(USER_ID, userId);
		return requestParamsNet;
	}
	/**
	 * 显示上传底部样式
	 */
	public void showUploadView(final int requestCode, LinearLayout all_trans_layout){
		WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		int screen_width_show = wm.getDefaultDisplay().getWidth();
		//屏幕高度
		int screen_height = wm.getDefaultDisplay().getHeight();
		
		UploadPicPopup uploadPicPopup = new UploadPicPopup(mActivity, screen_width_show, screen_height, all_trans_layout);
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!"".equals(SharedPreferenceHelper.getLoginUserName(getActivity()))){
			mNickNameTextView.setText(SharedPreferenceHelper.getLoginUserName(getActivity()));
		}
		
	}
	
}
