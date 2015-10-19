package com.ninetowns.tootooplus.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VideoRecorderBean {
    private VideoRecorderPushBean videoinfo;
    private String state;
    private VideoRecorderPushBean  videoRecorderBean;
    public VideoRecorderBean(String json){
        getData(json);
        
    }
    private void getData(String json) {
        JSONArray JARR;
        VideoRecorderPushBean videoRecorderBean=new VideoRecorderPushBean();
        try {
            JARR = new JSONArray(json);
            JSONObject jobjDevid = (JSONObject) JARR.get(0);
            String mRecordId = jobjDevid
                    .getString("RecordId");
            String mRtmp = jobjDevid.getString("Rtmp");
            String mHls = jobjDevid.getString("Hls");
            String mLiveImgPath = jobjDevid
                    .getString("LiveImgPath");
            String mMP4Path = jobjDevid
                    .getString("MP4Path");
            videoRecorderBean.setHls(mHls);
            videoRecorderBean.setLiveImagePath(mLiveImgPath);
            videoRecorderBean.setMp4Url(mMP4Path);
            videoRecorderBean.setRtmpUrl(mRtmp);
            videoRecorderBean.setRecorderId(mRecordId);
            setVideoRecorderBean(videoRecorderBean);
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        
    }
 
    
    
   public VideoRecorderPushBean getVideoRecorderBean() {
        return videoRecorderBean;
    }


    public void setVideoRecorderBean(VideoRecorderPushBean videoRecorderBean) {
        this.videoRecorderBean = videoRecorderBean;
    }



}
