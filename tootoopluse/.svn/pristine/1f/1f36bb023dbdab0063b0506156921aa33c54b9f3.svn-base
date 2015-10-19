package com.ninetowns.tootoopluse.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.AlbumPhotoGvAdapter;
import com.ninetowns.tootoopluse.adapter.GridViewApdapter;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.AlbumPhotoBean;
import com.ninetowns.tootoopluse.bean.UpLoadFileBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.ImageHttpMultipartPostHelper;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.AlbumUtil;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.Activity.BaseActivity;

/****** 选择照片 ******/
public class AlbumPhotoActivity extends BaseActivity implements
        View.OnClickListener {

    private String storyId;

    private String upload_type;

    private String userid;

    private boolean isDraftOrStory;

    private String pageid;

    private String PageType;

    private String goodid;

    private boolean isEditorView;

    private String UpdateStoryId;

    private boolean isMiddleEditor;

    private boolean isEditextView;
    private boolean isCreateView;
    private boolean isDraftView;
    private boolean isConvertView;
    private boolean isRecommendView;

    private String title;

    private TextView comit;

    private View comitLayout;

    // 多选存放图片的map 用linkedhashmap 来
    private LinkedHashMap<Integer, AlbumPhotoBean> selectMap = new LinkedHashMap<Integer, AlbumPhotoBean>();

    private GridViewApdapter moreGridViewAdapter;

    private List<AlbumPhotoBean> albumPhotoBeans;
/*****************处理上传图片的handler*************/
    private Handler handler = new Handler() {
        private UpLoadFileBean bean;
        List<UpLoadFileBean> list;

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case 1://如果上传图片之后返回的消息是1
                list = (List<UpLoadFileBean>) msg.obj;
                StringBuilder build = new StringBuilder();
                StringBuilder build2 = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    bean = list.get(i);
                    String imagefial = bean.getThumbFileUrl();
                    String imageurl = bean.getFileUrl();
                    if (i != list.size() - 1) {
                        build.append(imagefial).append("@@");
                        build2.append(imageurl).append("@@");
                    } else {
                        build.append(imagefial);
                        build2.append(imageurl);
                    }
                }
                postData(build.toString(), build2.toString());//首先先创建封面图  得到storyid
                break;
            }

        };

    };

    /**
     * 
     * @Title: AlbumPhotoActivity.java
     * @Description: 子类实现
     * @author wuyulong
     * @date 2014-12-15 下午2:32:44
     * @param
     * @return void
     */
    public void postData(String thumbFileUrl, String imageUrl) {
    	
    	

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
			showProgressDialog(AlbumPhotoActivity.this);
			RequestParamsNet requestParamsNet = new RequestParamsNet();
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_STORYID, storyId);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_PAGE_TYPE, "2");//2是图片3是视频
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_THUMBIMAGE, thumbFileUrl);
			requestParamsNet.addQueryStringParameter(
					TootooeNetApiUrlHelper.STORYCREATE_PAGE_IMAGE, imageUrl);
			CommonUtil.xUtilsPostSend(
					TootooeNetApiUrlHelper.STORYCREATE_PAGE_API,
					requestParamsNet, new RequestCallBack<String>() {

						private String PageId;

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							String strResult=responseInfo.result;
                            if (strResult != null) {
                                try {
                                    JSONObject jsobj = new JSONObject(strResult);
                                    if (jsobj.has("Status")) {
                                        jsobj.getString("Status");

                                    }
                                    if (jsobj.has("Data")) {
                                        String st = jsobj.getString("Data");
                                        JSONObject storyjsonobj = new JSONObject(
                                                st);
                                        if (storyjsonobj.has("PageId")) {
                                            PageId = storyjsonobj
                                                    .getString("PageId");
                                            if (!TextUtils.isEmpty(PageId)) {
                                                skipActivity();
                                            } else {
                                                LogUtil.error(
                                                        "TextContentStoryCreateFragment",
                                                        "pageid是null");
                                            }

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
							closeProgressDialog(AlbumPhotoActivity.this);
							ComponentUtil
									.showToast(
											AlbumPhotoActivity.this,
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
     * @Title: AlbumPhotoActivity.java
     * @Description: 子类实现
     * @author wuyulong
     * @date 2014-12-15 下午12:37:43
     * @param
     * @return GridViewApdapter
     */
    public GridViewApdapter initAdapter(Context context,
            List<AlbumPhotoBean> list, LinkedHashMap<Integer, AlbumPhotoBean> selectMap) {
        return null;
    }

    /**
     * 
     * @Title: AlbumPhotoActivity.java
     * @Description: 子类中实现
     * @author wuyulong
     * @date 2014-12-15 下午12:48:22
     * @param
     * @return void
     */
    public void skipActivity() {
    	
}
    private Bundle bundle;
    private boolean isRecommendConvertView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.album_photo);
        getType();
        try {
        	  bundle = this.getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
              isEditextView = bundle.getBoolean(ConstantsTooTooEHelper.isEditextView);
              isCreateView = bundle.getBoolean(TootooeNetApiUrlHelper.isCreateView);
              isDraftView = bundle.getBoolean(TootooeNetApiUrlHelper.isDraftView);
              isConvertView = bundle.getBoolean(TootooeNetApiUrlHelper.isConvertView);
              isRecommendView = bundle.getBoolean(TootooeNetApiUrlHelper.isRecommendView);
              isRecommendConvertView = bundle.getBoolean(TootooeNetApiUrlHelper.isConvertRecommendView);
			
		} catch (Exception e) {
		}
      
        if(isEditextView){
            
        }else if(isCreateView){
            createStoryId();
        }else if(isDraftView){
            
        }else if(isConvertView){
            createStoryId();
        }else if(isRecommendView){
            createStoryId();
            
        }else if(isRecommendConvertView){
            createStoryId();
        }
        
        setVisibleCareraOrSele();
        // 返回按钮
        LinearLayout two_or_one_btn_head_back = (LinearLayout) findViewById(R.id.two_or_one_btn_head_back);
        two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 标题
        TextView two_or_one_btn_head_title = (TextView) findViewById(R.id.two_or_one_btn_head_title);
        two_or_one_btn_head_title.setText(R.string.album_photo_title);
        comit = (TextView) findViewById(R.id.two_or_one_btn_head_second_tv);
        if (!TextUtils.isEmpty(upload_type)) {
            if (upload_type.equals("change_head")) {// 上传头像
                comit.setVisibility(View.INVISIBLE);
            } else if (upload_type.equals("post_story")) {
                comit.setVisibility(View.VISIBLE);
                comitLayout = findViewById(R.id.two_or_one_btn_head_second_layout);
                comitLayout.setOnClickListener(this);
                // 默认情况下显示
                comit.setText(getResources().getString(
                        R.string.mobile_forget_pwd_success_btn)
                        + "(0/" + ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO + ")");
            }
        } else {
            comit.setVisibility(View.VISIBLE);
            comitLayout = findViewById(R.id.two_or_one_btn_head_second_layout);
            comitLayout.setOnClickListener(this);
            // 默认情况下显示
            comit.setText(getResources().getString(
                    R.string.mobile_forget_pwd_success_btn)
                    + "(0/" + ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO + ")");
        }

        GridView album_photo_gv = (GridView) findViewById(R.id.album_photo_gv);
        String folder_name = getIntent().getStringExtra("folder_name");
        if (!TextUtils.isEmpty(folder_name)) {
            albumPhotoBeans = AlbumUtil.albumPhotoInfo(this, folder_name);
            LogUtil.systemlogInfo("AlbumPhotoActivity+++>", albumPhotoBeans);
        }

        if (!TextUtils.isEmpty(upload_type)) {
            if (upload_type.equals("change_head")) {// 上传头像
                album_photo_gv.setAdapter(new AlbumPhotoGvAdapter(this,
                        albumPhotoBeans));
            } else if (upload_type.equals("post_story")) {
                moreGridViewAdapter = new GridViewApdapter(this,
                        albumPhotoBeans, selectMap);
                album_photo_gv.setAdapter(moreGridViewAdapter);
            }
        } else {
            moreGridViewAdapter = initAdapter(this, albumPhotoBeans, selectMap);// 子类必须实现
                   if(moreGridViewAdapter!=null) {
                       album_photo_gv.setAdapter(moreGridViewAdapter);
                   }                                                            // 暂时先酱紫
          
        }
        album_photo_gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                onItemSelectPhoto(position, comit);

            }

        });

    }
/**
 * 
* @Title: AlbumPhotoActivity.java  
* @Description: 子类去实现
* @author wuyulong
* @date 2014-12-16 下午3:08:57  
* @param 
* @return void
 */
    public void setVisibleCareraOrSele() {
        
        
    }


    public void onItemSelectPhoto(int position, TextView comit) {
        if (upload_type.equals("change_head")) {
            Intent select_intent = new Intent(AlbumPhotoActivity.this,
                    PhotoSelectActivity.class);
            select_intent.putExtra("photo_id", String.valueOf(albumPhotoBeans
                    .get(position).getAlbum_photo_id()));
            startActivity(select_intent);
        } else if (upload_type.equals("post_story")) {
            if (moreGridViewAdapter.selectMap.get(position) != null) {
                moreGridViewAdapter.selectMap.remove(position);
            } else {
                // 最多选5张
                if (moreGridViewAdapter.selectMap.size() < Integer
                        .parseInt(ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO)) {
                    moreGridViewAdapter.selectMap.put(position,
                            albumPhotoBeans.get(position));
                } else {
                    ComponentUtil.showToast(
                            AlbumPhotoActivity.this,
                            getResources().getString(
                                    R.string.pass_max_more_update_photo));
                }
            }
            comit.setText(getResources().getString(
                    R.string.mobile_forget_pwd_success_btn)
                    + "("
                    + moreGridViewAdapter.selectMap.size()
                    + "/"
                    + ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO + ")");
//            moreGridViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 
     * @Title: AlbumPhotoActivity.java
     * @Description: 子类可复写
     * @author wuyulong
     * @date 2014-12-15 上午11:29:58
     * @param
     * @return void
     */
    public void getType() {
        try {
            storyId = this.getIntent().getExtras().getString("storyid");
            upload_type = getIntent().getStringExtra("upload_type");
            userid = this.getIntent().getExtras().getString("userid");
            isDraftOrStory = this.getIntent().getExtras()
                    .getBoolean("isDraftOrStory");
            pageid = this.getIntent().getExtras().getString("pageid");
            PageType = this.getIntent().getExtras().getString("PageType");
            goodid = this.getIntent().getExtras().getString("goodid");
            isEditorView = this.getIntent().getExtras()
                    .getBoolean("isEditorView");
            UpdateStoryId = this.getIntent().getExtras()
                    .getString("UpdateStoryId");
            isMiddleEditor = getIntent().getExtras().getBoolean(
                    "isMiddleEditor");
            isCreateView = getIntent().getExtras().getBoolean("isCreateView");
            title = getIntent().getExtras().getString("title");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.two_or_one_btn_head_second_layout:
            List<String> temp_imgs = new ArrayList<String>();
            for (int i = 0; i < albumPhotoBeans.size(); i++) {
                if (moreGridViewAdapter.selectMap.get(i) != null) {
                    temp_imgs.add(moreGridViewAdapter.selectMap.get(i)
                            .getAlbum_photo_path());
                }
            }
            LogUtil.systemlogInfo("++++hc+++selectPhoto++++>", temp_imgs);
            uploadImage(temp_imgs);
            Toast.makeText(getApplication(), "选择了" + temp_imgs.size() + "张图片",
                    Toast.LENGTH_LONG).show();
            break;
        default:
            break;
        }
    }
/**
 * 
* @Title: AlbumPhotoActivity.java  
* @Description: 上传图片 
* @author wuyulong
* @date 2015-1-8 上午9:58:54  
* @param 
* @return void
 */
    private void uploadImage(List<String> temp_imgs) {
        List<File> listFile = new ArrayList<File>();
        for (int i = 0; i < temp_imgs.size(); i++) {
            File upFile = new File(temp_imgs.get(i));
            listFile.add(upFile);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE,
                TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE_PHOTO);//
        map.put(TootooeNetApiUrlHelper.UPLOAD_FIRLE_FLAG,
                TootooeNetApiUrlHelper.UPLOAD_FIRLE_FLAG_DEBUG);
        map.put(TootooeNetApiUrlHelper.APPLICATIONID, (TootooeNetApiUrlHelper.APPLICATIONID_PARAM));
        String useridlocal = SharedPreferenceHelper.getLoginUserId(TootooPlusEApplication.getAppContext());
        if (!TextUtils.isEmpty(useridlocal)) {
            map.put(TootooeNetApiUrlHelper.USERID, useridlocal);
        } else {
            ComponentUtil.showToast(getApplicationContext(), "未登陆");
        }
        ImageHttpMultipartPostHelper uploadImages = new ImageHttpMultipartPostHelper(this,
                CommonUtil.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_FIRLE), listFile,
                map, handler);
        uploadImages.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /**
     * 
     * @Title: AlbumPhotoActivity.java
     * @Description: 得到创建故事的id ，子类实现
     * @author wuyulong
     * @date 2014-12-15 下午2:24:54
     * @param
     * @return void
     */
    public void createStoryId() {
        
    }

}
