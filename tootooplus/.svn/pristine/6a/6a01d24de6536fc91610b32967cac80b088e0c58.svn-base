package com.ninetowns.tootooplus.fragment;


import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.FileFolderUtils;
import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.tootooplus.R;

public class VersionDownDialogFragment extends DialogFragment {
	
	private ProgressBar version_dialog_pb;
	private TextView version_dialog_size, version_dialog_percent;
	
	private String down_url = "";
	
	private String from = "";
	
	private HttpHandler downhttp = null;
	public VersionDownDialogFragment() {
		// TODO Auto-generated constructor stub
	}
	public VersionDownDialogFragment(String down_url, String from){
		this.down_url = down_url;
		this.from = from;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//透明
		setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Trans_NoTitleBar_Fullscreen);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.version_dialog_fragment, null);
		WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		int screen_width = wm.getDefaultDisplay().getWidth();
		LinearLayout version_dialog_layout = (LinearLayout)view.findViewById(R.id.version_dialog_layout);
		LinearLayout.LayoutParams lLayoutParams = (LinearLayout.LayoutParams)version_dialog_layout.getLayoutParams();
		lLayoutParams.width = screen_width * 3 / 4;
		version_dialog_layout.setLayoutParams(lLayoutParams);
		
		version_dialog_pb = (ProgressBar)view.findViewById(R.id.version_dialog_pb);
		
		version_dialog_size = (TextView)view.findViewById(R.id.version_dialog_size);
		
		version_dialog_percent = (TextView)view.findViewById(R.id.version_dialog_percent);
		
		HttpUtils http = new HttpUtils();
		File dirs = new File(ImageUtil.TOO_INSTALL);
		//判断文件夹是否存在, 不存在就创建
		if(!dirs.exists() || !dirs.isDirectory()){    
			dirs.mkdirs();
		}
		
		//如果文件夹中已经有tootooplus.apk, 就先删除
		File appFile = new File(ImageUtil.TOO_INSTALL + "/tootooplus.apk");
		if(appFile.exists()){
			appFile.delete();
		}
		downhttp = http.download(down_url, ImageUtil.TOO_INSTALL + "/tootooplus.apk",
		    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
		    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
		    new RequestCallBack<File>() {

		        @Override
		        public void onStart() {
		        	
		        }

		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		        	//总大小
		    		version_dialog_pb.setMax((int)total);
		    		//已下载大小
		    		version_dialog_pb.setProgress((int)current);
		    		version_dialog_size.setText(FileFolderUtils.getAppSize((int)current) + "/" + FileFolderUtils.getAppSize((int)total));
		    		
		    		version_dialog_percent.setText(FileFolderUtils.getNotiPercent((int)current, (int)total));
		        }

		        @Override
		        public void onSuccess(ResponseInfo<File> responseInfo) {
		        	FileFolderUtils fileUtils = new FileFolderUtils();
		        	//安装app
		        	fileUtils.installApp(getActivity(), ImageUtil.TOO_INSTALL + "/tootooplus.apk");
		        	
		        }


		        @Override
		        public void onFailure(HttpException error, String msg) {
		        	ComponentUtil.showToast(getActivity(), msg);
		        }
		});
	
		
		return view;
		
	}
	
    
    @Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	 @Override
	public void onDestroy() {
        super.onDestroy();
        downhttp.cancel();
        if(from.equals("start")){
        	getActivity().finish();
        } else if(from.equals("settings")){
        	dismiss();
        }
       
    }
}
