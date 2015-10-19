package com.ninetowns.tootooplus.fragment;

import java.io.File;
import java.util.Timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.camera.CameraView;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.UpLoadFileBean;
import com.ninetowns.tootooplus.bean.VideoRecorderBean;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.ProgressiveDialog;
/**
 * 
* @Title: VideoStoryCreateFragment.java  
* @Description: 创建商品视频类型的界面 
* @author wuyulong
* @date 2015-1-8 上午11:22:11  
* @version V1.0
 */
public class VideoStoryCreateFragment extends Fragment implements View.OnClickListener {
    private static final String tag = "VideoStoryCreateFragment";
    private View view1;
    private String deviceId;
    private Bundle bundle;
    private String storyid;
    private String title;
    private String type;
    private String userid;
    private String goodid;
    public TimeCount timeOut;
    private ImageView iv_back, iv_sound, iv_recorder, iv_recorder_change;
    private TextView tv_recorder_time;
    private CameraView camview;
    private VideoRecorderBean videoRecorder;
    private static final int RECORD_TIME_NOTIFICE_TWENTY_FIVE = 1000 * 55;
    private static final int RECORD_TIME_NOTIFICE_TWENTY_SEVEN = 1000 * 57;
    private static final int RECORD_TIME_NOTIFICE_TWENTY_NINE = 1000 * 59;
    private static final int RECORD_TIME_ALL_NOTIFICE_SIXTY = 1000 * 60;
    private static final int RECORD_TIME_MIDDLE = 1000;
    int cameraPosition = 1;// 0代表前置摄像头，1代表后置摄像头
    private boolean isMute = true;
    boolean isStartRecording = true;
    private String videoId;
    private String desContent;
    private AudioManager audioManager;
    private boolean isRecorderingView = true;
    public static final int HANDLER_REQUEST_GETRTMP = 1;
    public static final int HANDLER_REQUEST_ERROR = 2;
    private String UpdateStoryId;
    private boolean isdraftEditory;
    private String pageidEdit;
    private boolean isEditorView;
    private RelativeLayout relaCamera;
    private LayoutParams layoutParam;
    private boolean isMiddleEditor;
    private LinearLayout llbelow;
    private boolean isCreateView;
    private String mp4url;

    @SuppressWarnings("static-access")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.create_video, null);
        audioManager = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        bundle = this.getActivity().getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
        if(null!=bundle){
        	  getType(bundle);
        }
        initView();
        initListener();
        return view1;
    }
    /**
     * 
    * @Title: VideoStoryCreateFragment.java  
    * @Description: 判断是否是编辑 
    * @author wuyulong
    * @date 2014-12-18 下午1:02:45  
    * @param 
    * @return void
     */
    protected void operateData(UpLoadFileBean bean) {
    }
  

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 开始录制
     * @author wuyulong
     * @date 2014-8-19 下午4:33:23
     * @param
     * @return void
     */
    private void startRecord() {
        if (CommonUtil.ExistSDCard()) {
            long availableStore = CommonUtil.getAvailableStore(getActivity()) / 1024 / 1024;// M
            if (availableStore != -1) {
                if (availableStore > 50 && availableStore < 100) {// 大于50兆并且小于100兆
                    timeOut = new TimeCount(RECORD_TIME_ALL_NOTIFICE_SIXTY, RECORD_TIME_MIDDLE);
                    // timeOut.start();
                    // 设置
                    PublishTask pushTask = new PublishTask(timeOut);
                    pushTask.execute();
                    ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_hundred));
                } else if (availableStore <= 50) {// 小于50M
                    ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_fifty));
                } else if (availableStore >= 100) {
                    timeOut = new TimeCount(RECORD_TIME_ALL_NOTIFICE_SIXTY, RECORD_TIME_MIDDLE);
                    // timeOut.start();
                    // 设置
                    PublishTask pushTask = new PublishTask(timeOut);
                    pushTask.execute();
                }
            }
        } else {
            ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_sdcard));
        }

        // isRecorderingView = false;

    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 修改视频
     * @author wuyulong
     * @date 2014-8-20 下午5:46:56
     * @param
     * @return void
     */
    private String PageId;
	private ProgressiveDialog progressDialog;
    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 创建故事id
     * @author wuyulong
     * @date 2014-8-19 下午4:32:01
     * @param
     * @return void
     */

    private void initListener() {
        iv_recorder_change.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_sound.setOnClickListener(this);
        iv_recorder.setOnClickListener(this);

    }

    private void initView() {
        int width = this.getActivity().getWindowManager().getDefaultDisplay().getWidth();
        relaCamera = (RelativeLayout) view1.findViewById(R.id.rl_camera);
        llbelow = (LinearLayout) view1.findViewById(R.id.rl_below);
        // camview = (CameraView) view1.findViewById(R.id.camview);
        camview = new CameraView(getActivity(), null);
        camview.setActivity(getActivity());
        int videoHeight = width * 4 / 3;
        int belowHeight = CommonUtil.getHeight(getActivity()) - videoHeight;
        // 保证正方形
        // 设置cameraview的布局 采集比率为4:3;
        layoutParam = new RelativeLayout.LayoutParams(width, videoHeight);
        LinearLayout.LayoutParams belowParam = new LinearLayout.LayoutParams(width, belowHeight);
        llbelow.setLayoutParams(belowParam);
        relaCamera.addView(camview, layoutParam);
        tv_recorder_time = (TextView) view1.findViewById(R.id.tv_recorder_time);// 录制的时间
        iv_recorder_change = (ImageView) view1.findViewById(R.id.iv_recorder_change);// 切换按钮
        iv_back = (ImageView) view1.findViewById(R.id.iv_back);// 回退
        iv_sound = (ImageView) view1.findViewById(R.id.iv_sound);// 静音
        iv_recorder = (ImageView) view1.findViewById(R.id.iv_recorder);// 录制
    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 展示是否显示正在录制界面
     * @author wuyulong
     * @date 2014-8-19 上午9:09:51
     * @param
     * @return void
     */
    private void setRecorderWayView(boolean isRecorderingView) {
        if (camview.isFailed == true) {
            this.getActivity().finish();
            return;
        }
        if (isRecorderingView) {// 显示 开始录制
            tv_recorder_time.setVisibility(View.VISIBLE);
            iv_recorder_change.setVisibility(View.INVISIBLE);
            iv_recorder.setImageResource(R.drawable.btn_mv_stateb);
            startRecord();
        } else {
            stopRecord();
            tv_recorder_time.setVisibility(View.INVISIBLE);
            iv_recorder_change.setVisibility(View.VISIBLE);
            iv_recorder.setImageResource(R.drawable.btn_mv_statea);
        }
        this.isRecorderingView = !isRecorderingView;

    }

    /**
     * 停止采集
     */
    private  void stopRecord() {

        if (camview != null && timeOut != null) {
            mp4url = camview.mp4Path;
            camview.stopPublish();
            timeOut.cancel();
            if (mp4url == null)
                return;
        } else {
            return;
        }
        
        if (ImageUtil.getVideoThumbnail(mp4url, 170, 170, MediaStore.Images.Thumbnails.MICRO_KIND) != null) {
        	
            skipToUpload(mp4url);
        } else {
            // 删除sdcard 不能播放的视频
            File file = new File(mp4url);
            if (file.exists()) {
                file.delete();
            }
            ComponentUtil.showToast(getActivity(), "亲！您点的太快了，还没有生成此视频！");
        }

    }

    public void skipToUpload(String mp4url) {
    	
    }

    // 发布视频线程
    public class PublishTask extends AsyncTask<Void, Void, Void> {
        private TimeCount timeOut;

        public PublishTask(TimeCount timeOut) {
            this.timeOut = timeOut;
        }

        @Override
        protected Void doInBackground(Void... params) {
            timeOut.start();
            try {
				camview.startPublish();
			} catch (Exception e) {
				e.printStackTrace();
				 ComponentUtil.showToast(getActivity(), "您还没有打开视频录制、录音权限！");
			}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    /**
     * 
     * @Title: StoryDetailItemTypeActivity.java
     * @Description: 根据跳转类型跳到相应的fragemnt中
     * @author wuyulong
     * @date 2014-8-1 下午4:51:02
     * @param
     * @return void
     */
    public void getType(Bundle bundle) {
        try {
            storyid = bundle.getString("storyid");
            title = bundle.getString("title");
            type = bundle.getString("PageType");

            userid = bundle.getString("userid");
            goodid = bundle.getString("goodid");

            UpdateStoryId = bundle.getString("UpdateStoryId");

            isdraftEditory = bundle.getBoolean("isDraftOrStory");
            isEditorView = bundle.getBoolean("isEditorView");
            isMiddleEditor = bundle.getBoolean("isMiddleEditor");
            pageidEdit = bundle.getString("pageid");
            isCreateView = bundle.getBoolean("isCreateView");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 切换前后摄像头
     * @author wuyulong
     * @date 2014-8-19 上午10:02:24
     * @param
     * @return void
     */
    private void setChangeBeforeAfter() {
        Intent intent = new Intent();
        intent.setAction("changecamera");
        intent.putExtra("cameraPosition", cameraPosition);
        this.getActivity().sendBroadcast(intent);
        if (cameraPosition == 1) {
            cameraPosition = 0;
        } else {
            cameraPosition = 1;
        }

    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 设置是否静音
     * @author wuyulong
     * @date 2014-8-19 上午9:28:59
     * @param
     * @return void
     */
    private void setChangeAudioVolum(boolean isMute) {
        if (isMute) {
            iv_sound.setImageResource(R.drawable.btn_mv_sound_no);
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        } else {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            iv_sound.setImageResource(R.drawable.btn_mv_sound);
        }
        this.isMute = !isMute;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.iv_recorder_change:
            setChangeBeforeAfter();
            break;
        case R.id.iv_back:
            justSaveContent();
            break;
        case R.id.iv_sound:
            setChangeAudioVolum(isMute);
            break;
        case R.id.iv_recorder:
        	if(camview.mCamera!=null){
        		setRecorderWayView(isRecorderingView);
        	}else{
        		ComponentUtil.showToast(getActivity(), "您没有权限拍摄视频，请到相应的手机设置并允许此应用程序拍照的权限。");
        	}
            
            break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(camview!=null){
        	camview.unRegisterReceiver(this.getActivity());
        }
        
    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 是否继续拍摄
     * @author wuyulong
     * @date 2014-9-17 上午11:13:31
     * @param
     * @return void
     */
    private void justSaveContent() {
        if (camview != null && camview.pcmSource != null && camview.pcmSource.isRecording) {
            final AlertDialog.Builder builder = new Builder(getActivity());
            builder.setTitle("提示");
            // if(camview!=null&&camview.pcmSource!=null&&camview.pcmSource.isRecording){
            builder.setMessage("退出将结束拍摄，确定退出？");
            // }else{
            // builder.setMessage("是否继续拍摄？");
            // }

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // if(camview!=null&&camview.pcmSource!=null&&camview.pcmSource.isRecording){
                    stopRecord();
                    // }else{
                    // builder.create().dismiss();
                    // }

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
                    // getActivity().finish();
                }
            });
            builder.show();
        } else {
        	getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        camview.stopPublish();
        if (timeOut != null) {
            timeOut.cancel();
        }
    }

    class TimeCount1 extends Timer {

    }

    /**
     * 
     * @Title: VideoStoryCreateFragment.java
     * @Description: 记录录制时间
     * @author wuyulong
     * @date 2014-8-22 上午9:20:26
     * @version V1.0
     */
    class TimeCount extends CountDownTimer {
        int timedur = 0;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 总时长 和依次的时间间隔
        }

        int count = 1;

        @Override
        public void onTick(long millisUntilFinished) {// 计时的过程
            long time = millisUntilFinished / 1000;
            timedur = timedur + 1000;
            if (CommonUtil.ExistSDCard()) {
                long availableStore = CommonUtil.getAvailableStore(getActivity()) / 1024 / 1024;
                if (availableStore != -1) {
                    if (availableStore > 50 && availableStore < 100) {// 大于50兆并且小于100兆
                        if (count == 1) {
                            ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_hundred));
                        }
                        count = 0;
                    } else if (availableStore <= 50) {// 小于50M
                        if (timeOut != null) {
                            timeOut.cancel();
                            timeOut = null;
                        }

                        ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_fifty));
                        return;
                    } else if (availableStore >= 100) {
//                        ComponentUtil.showToast(getActivity(), availableStore + "");
                    }
                }
            } else {
                ComponentUtil.showToast(getActivity(), getResources().getString(R.string.no_have_sdcard));
            }
            String timeAlready = CommonUtil.formatDurationTime(timedur);
            tv_recorder_time.setText(timeAlready);
            // 剩余时间=总时间-time;
            // time=总时间-剩余时间
            // 剩余时间=（RECORD_TIME_ALL_NOTIFICE_THIRTY-过去的时间（RECORD_TIME_NOTIFICE_TWENTY_FIVE））/1000
            long twentyFiveTime = (RECORD_TIME_ALL_NOTIFICE_SIXTY - RECORD_TIME_NOTIFICE_TWENTY_FIVE) / 1000;
            long twentySevenTime = (RECORD_TIME_ALL_NOTIFICE_SIXTY - RECORD_TIME_NOTIFICE_TWENTY_SEVEN) / 1000;
            long twentyNineTime = (RECORD_TIME_ALL_NOTIFICE_SIXTY - RECORD_TIME_NOTIFICE_TWENTY_NINE) / 1000;
            if (time == twentyFiveTime) {// 如果还剩5
                ComponentUtil.showToast(getActivity(), getResources().getString(R.string.you_have_already_five_min));
            } else if (time == twentySevenTime) {
                ComponentUtil.showToast(getActivity(), getResources().getString(R.string.you_have_already_three_min));
            } else if (time == twentyNineTime) {
                ComponentUtil.showToast(getActivity(), getResources().getString(R.string.you_have_already_one_min));
            }

        }

        @Override
        public void onFinish() {// 计时完毕的时候触发的
            stopRecord();
        }

    }
    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 显示dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:26
     * @param
     * @return void
     */
    public void showProgressDialog(Context context) {
        if ((!((Activity) context).isFinishing()) && (progressDialog == null)) {
            progressDialog = new ProgressiveDialog(context);
        }
        if (progressDialog != null) {
            progressDialog.setMessage(R.string.loading);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

    }

    /**
     * 
     * @Title: ComponentUtil.java
     * @Description: 取消dialog
     * @author wuyulong
     * @date 2014-7-14 下午4:23:48
     * @param
     * @return void
     */
    public void closeProgressDialog(Context context) {
        if (progressDialog != null) {
            progressDialog.isShowing();
            progressDialog.dismiss();
        }
    }
}
