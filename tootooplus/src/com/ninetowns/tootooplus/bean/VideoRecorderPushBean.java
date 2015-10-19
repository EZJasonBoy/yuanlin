package com.ninetowns.tootooplus.bean;

import java.io.Serializable;

public class VideoRecorderPushBean implements Serializable{
	
	public String getDesContent() {
		return desContent;
	}
	public void setDesContent(String desContent) {
		this.desContent = desContent;
	}
	private String desContent;
    private String recorderId;
    private String rtmpUrl;
    private String hls;//网页
    private String liveImagePath;//直播封面
    private String mp4Url;
    public String getRecorderId() {
        return recorderId;
    }
    public void setRecorderId(String recorderId) {
        this.recorderId = recorderId;
    }
    public String getRtmpUrl() {
        return rtmpUrl;
    }
    public VideoRecorderPushBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoRecorderPushBean(String desContent, String recorderId,
			String rtmpUrl, String hls, String liveImagePath, String mp4Url) {
		super();
		this.desContent = desContent;
		this.recorderId = recorderId;
		this.rtmpUrl = rtmpUrl;
		this.hls = hls;
		this.liveImagePath = liveImagePath;
		this.mp4Url = mp4Url;
	}
	@Override
	public String toString() {
		return "VideoRecorderPushBean [desContent=" + desContent
				+ ", recorderId=" + recorderId + ", rtmpUrl=" + rtmpUrl
				+ ", hls=" + hls + ", liveImagePath=" + liveImagePath
				+ ", mp4Url=" + mp4Url + "]";
	}
	public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }
    public String getHls() {
        return hls;
    }
    public void setHls(String hls) {
        this.hls = hls;
    }
    public String getLiveImagePath() {
        return liveImagePath;
    }
    public void setLiveImagePath(String liveImagePath) {
        this.liveImagePath = liveImagePath;
    }
    public String getMp4Url() {
        return mp4Url;
    }
    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }


}
