package com.ninetowns.tootoopluse.fragment;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.UpLoadFileBean;
import com.ninetowns.tootoopluse.helper.HttpChMultipartPostHelper;
import com.ninetowns.tootoopluse.helper.ImageChHttpMultipartPostHelpter;
import com.ninetowns.tootoopluse.helper.ImageHttpMultipartPostHelper;
import com.ninetowns.tootoopluse.helper.MyChildrenUpLoader;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.BaseFragmentDialog;

/**
 * 
 * @Title: UpLoadViewDialogFragment.java
 * @Description: 上传的dialog 防止后期修改dialog 进度条
 * @author wuyulong
 * @date 2015-1-8 上午10:55:24
 * @version V1.0
 */
public class UpLoadViewDialogFragment extends BaseFragmentDialog {
	public List<File> listFile;
	private Handler handler;
	private HashMap<String, String> map;
	private String type = "";
	private File imageThumb;
	private File upfile;
	private Bundle bundle;
	private String scenarioType;
	public static final int ADD_FILE_LIST=1120;
	public static final int SEND_FILE=1121;
	private List<UpLoadFileBean> mFilelist = new ArrayList<UpLoadFileBean>();
	/********* 记录上传之后的数据 **********/
	private Handler mUpLoadAddFileListHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ADD_FILE_LIST:
				UpLoadFileBean item = (UpLoadFileBean) msg.obj;
				int whatcount=msg.arg1;
				int shengyuPagecount=msg.arg2;
				if(item!=null){
					mFilelist.add(item);
				}else{
					ComponentUtil.showToast(TootooPlusEApplication.getAppContext(), "第"+whatcount+"张图片上传失败");
				}
				
				if(shengyuPagecount==0){
					Message msgsend=Message.obtain();
					msgsend.what=SEND_FILE;
					mUpLoadAddFileListHandler.sendMessage(msgsend);
				}else{
					if(listFile!=null&&listFile.size()>0){
						uploaderProgress(listFile.get(whatcount), whatcount,shengyuPagecount);
				
				}
				}
			
				break;
			case SEND_FILE:
				  if (mFilelist != null && mFilelist.size() > 0) {
			            Message msgupload = Message.obtain();
			            msgupload.what = TootooeNetApiUrlHelper.UPLOAD_VIDEO;
			            msgupload.obj = mFilelist;
			            msgupload.arg1 = 0;
			            handler.sendMessage(msgupload);
			            if(pd!=null){
			            	pd.dismiss();
			            }
			        } else {
			            LogUtil.systemlogError("list是null", "获取的list数据是null");
			        }
				break;

			default:
				break;
			}

		};

	};
	private ImageView mIVupload;
	private ProgressBar mPdupload;
	private ProgressDialog pd;

	public UpLoadViewDialogFragment() {
		// TODO Auto-generated constructor stub
	}

	public UpLoadViewDialogFragment(List<File> listFile, Handler handler,
			HashMap<String, String> map, Bundle bundle) {
		this.listFile = listFile;
		this.handler = handler;
		this.map = map;
		this.bundle = bundle;
		type = map.get(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE);
		scenarioType = map.get(TootooeNetApiUrlHelper.ScenarioType);
	}

	public UpLoadViewDialogFragment(List<File> listFile, Handler handler,
			HashMap<String, String> map) {
		this.listFile = listFile;
		this.handler = handler;
		this.map = map;
		type = map.get(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE);
		scenarioType = map.get(TootooeNetApiUrlHelper.ScenarioType);
	}

	public UpLoadViewDialogFragment(File imageThumb, File upfile,
			HashMap<String, String> map, Handler hander) {
		this.map = map;
		this.handler = hander;
		type = map.get(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE);
		this.imageThumb = imageThumb;
		scenarioType = map.get(TootooeNetApiUrlHelper.ScenarioType);
		this.upfile = upfile;
	}

	public UpLoadViewDialogFragment(File imageThumb, File upfile,
			HashMap<String, String> map, Handler hander, Bundle bundle) {
		this.map = map;
		this.handler = hander;
		this.bundle = bundle;
		type = map.get(TootooeNetApiUrlHelper.UPLOAD_FIRLE_TYPE);
		this.imageThumb = imageThumb;
		scenarioType = map.get(TootooeNetApiUrlHelper.ScenarioType);
		this.upfile = upfile;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.upload_view, null);
		
		mIVupload=(ImageView)view.findViewById(R.id.iv_uploadImage);
		mPdupload=(ProgressBar)view.findViewById(R.id.pd_gress);
if(isAdded()){
	pd = new ProgressDialog(getActivity());
	pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    pd.setCancelable(false);
}
        
    
		
		return view;

	}@Override
	public void onActivityCreated(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onActivityCreated(arg0);
		upload();
	}

	/**
	 * 
	 * @Title: UpLoadViewDialogFragment.java
	 * @Description: 上传图片
	 * @author wuyulong
	 * @date 2015-1-4 下午3:32:23
	 * @param
	 * @return void
	 */
	public void upload() {
		if (!TextUtils.isEmpty(scenarioType) && scenarioType.equals("4")) {
			ImageHttpMultipartPostHelper multipartPost = new ImageHttpMultipartPostHelper(
					getActivity(),
					CommonUtil
							.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FIRLE),
					listFile, map, handler, this);
			multipartPost.execute();
		} else {
			if (type.equals("3")) {
				HttpChMultipartPostHelper uploadVieo = new HttpChMultipartPostHelper(
						getActivity(),
						CommonUtil
								.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FIRLE),
						imageThumb, upfile, map, handler, this, bundle);
				uploadVieo.execute();
			} else {
				if(listFile!=null&&listFile.size()>0){
					int count =listFile.size();
					
//						uploaderProgress(listFile.get(0), 0,count);
					uploader1();
						
				
				}
//				uploader1();
			}
		}

	}
	/**
	 * 
	* @Title: uploaderProgress 
	* @Description: TODO
	* @param  i 第几个 countpage  还剩几个
	* @return   
	* @throws
	 */
	private void uploaderProgress(File file,int i,int countpage){
		if(isAdded()){
			MyChildrenUpLoader mChildUpladerAsy=new MyChildrenUpLoader(
					getActivity(),
					CommonUtil
							.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FIRLE),
							file, map, mUpLoadAddFileListHandler, this, bundle,pd);
			mChildUpladerAsy.execute(i,countpage);
		}
		
	}

	/** 
	* @Title: uploader1 
	* @Description: TODO
	* @param  
	* @return   
	* @throws 
	*/
	private void uploader1() {
		ImageChHttpMultipartPostHelpter uploadImages = new ImageChHttpMultipartPostHelpter(
				getActivity(),
				CommonUtil
						.appInterfaceUrl(TootooeNetApiUrlHelper.UPLOAD_RECDOMMMEND_FIRLE),
				listFile, map, handler, this, bundle);
		// uploadImages.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
		uploadImages
				.executeOnExecutor(new SerialExecutor(uploadImages));
	}

	private static class SerialExecutor implements Executor {
		private ImageHttpMultipartPostHelper uploadImages;

		@SuppressWarnings("unused")
		public SerialExecutor(ImageHttpMultipartPostHelper uploadImages) {
			// TODO Auto-generated constructor stub
			this.uploadImages = uploadImages;
		}

		final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
		Runnable mActive;

		public synchronized void execute(final Runnable r) {
			mTasks.offer(new Runnable() {
				public void run() {
					try {
						r.run();
					} finally {
						scheduleNext();
					}
				}
			});
			if (mActive == null) {
				scheduleNext();
			}
		}

		@SuppressWarnings("static-access")
		protected synchronized void scheduleNext() {
			if ((mActive = mTasks.poll()) != null) {
				uploadImages.execute(mActive);
			}
		}
	}
}
