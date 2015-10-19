package com.ninetowns.tootooplus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.fragment.ChatRoomRecordFragment;
import com.ninetowns.tootooplus.util.INetConstanst;
import com.ninetowns.ui.Activity.FragmentGroupActivity;

/**
 * 
 * @ClassName: CreateWishActivity
 * @Description:录制视频
 * @author wuyulong
 * @date 2015-1-23 下午4:14:03
 * 
 */
public class ChatRoomRecordVideoActivity extends FragmentGroupActivity implements INetConstanst {
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_wish_activity);
//		ReceiverManager.getIntance().registerReceiver(this, finishRecordReceiver, FINISH_RECORDVIDEO_ACTION);
	}


	@Override
	protected void initPrimaryFragment() {
		switchPrimaryFragment(R.id.fragment_stub);

	}

	@Override
	protected Class<? extends Fragment> getPrimaryFragmentClass(int fragmentId) {
		Class<? extends Fragment> clazz = null;
		// 根据不同的type显示是创建界面 还是 推荐创建界面 还是编辑界面

		clazz = ChatRoomRecordFragment.class;// 录制视频

		return clazz;
	}

	@Override
	protected Bundle getPrimaryFragmentArguments(int fragmentId) {
		return null;
	}

	@Override
	protected int getPrimaryFragmentStubId() {
		return R.id.fragment_stub;
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		ReceiverManager.getIntance().unRegisterReceiver(finishRecordReceiver);
	}
	
//	private BroadcastReceiver finishRecordReceiver=new BroadcastReceiver() {
//		
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			ChatRoomRecordVideoActivity.this.finish();
//		}
//	};

}
