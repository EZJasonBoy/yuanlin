package com.ninetowns.tootoopluse.helper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.mime.content.StringBody;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.fragment.UpLoadViewDialogFragment;

public class MyChildrenUpLoader extends MyUpLoadAsyncTast {

	private Bundle mBundle;
	private HashMap<String, String> map;
	private boolean isEditextView;
	private boolean isCreateView;
	private boolean isDraftView;
	private boolean isConvertView;
	private boolean isRecommendView;
	private boolean isRecommendConvertView;
	private File mFile;

	public MyChildrenUpLoader(Context context, String url, File file,
			HashMap<String, String> map, Handler handler,
			UpLoadViewDialogFragment upDialog, Bundle bundle,ProgressDialog pb) {
		super(context, url, file, map, handler, upDialog, bundle,pb);
		this.mBundle = bundle;
		this.map = map;
		this.mFile = file;
		isEditextView = bundle.getBoolean(ConstantsTooTooEHelper.isEditextView);
		isCreateView = bundle.getBoolean(ConstantsTooTooEHelper.isCreateView);
		isDraftView = bundle.getBoolean(ConstantsTooTooEHelper.isDraftView);
		isConvertView = bundle.getBoolean(ConstantsTooTooEHelper.isConvertView);
		isRecommendView = bundle
				.getBoolean(ConstantsTooTooEHelper.isRecommendView);
		isRecommendConvertView = bundle
				.getBoolean(ConstantsTooTooEHelper.isConvertRecommendView);
	}

	@Override
	public void setParamPart(CustomMultiPartEntityHelper multipartContent,
			int count) {
		// 偷粮换柱 count 上传第几个
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					if (entry.getKey().equals(
							ConstantsTooTooEHelper.ScenarioType)) {
						if (isRecommendConvertView) {
							if (count == 1) {// 如果是第一个 那么是封面图
								multipartContent.addPart(entry.getKey(),
										new StringBody("2"));
								LogUtil.systemlogInfo("isRecommendView封面图"
										+ ConstantsTooTooEHelper.ScenarioType,
										"2");
							} else {// 3是拖拽页
								multipartContent.addPart(entry.getKey(),
										new StringBody("3"));
								LogUtil.systemlogInfo("isRecommendView拖拽图"
										+ ConstantsTooTooEHelper.ScenarioType,
										"3");
							}

						} else if (isCreateView) {
							multipartContent.addPart(entry.getKey(),
									new StringBody("3"));
							LogUtil.systemlogInfo("isCreateView拖拽图"
									+ ConstantsTooTooEHelper.ScenarioType, "3");
						} else if (isConvertView) {
							if (count == 1) {// 如果是第一个 那么是封面图
								multipartContent.addPart(entry.getKey(),
										new StringBody("2"));
								LogUtil.systemlogInfo("isRecommendView封面图"
										+ ConstantsTooTooEHelper.ScenarioType,
										"2");
							} else {// 3是拖拽页
								multipartContent.addPart(entry.getKey(),
										new StringBody("3"));
								LogUtil.systemlogInfo("isRecommendView拖拽图"
										+ ConstantsTooTooEHelper.ScenarioType,
										"3");
							}

						} else if (isEditextView) {

							multipartContent.addPart(entry.getKey(),
									new StringBody("3"));
							LogUtil.systemlogInfo("isCreateView拖拽图"
									+ ConstantsTooTooEHelper.ScenarioType, "3");

						}
					} else {
						multipartContent.addPart(entry.getKey(),
								new StringBody(entry.getValue()));
					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
