package com.ninetowns.tootooplus.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.AddressListMineActivity;
import com.ninetowns.tootooplus.activity.FaceToFaceGroupEnterActivity;
import com.ninetowns.tootooplus.activity.MyActivityCommentActivity;
import com.ninetowns.tootooplus.activity.MyCollectionActivity;
import com.ninetowns.tootooplus.activity.MyFreeCommentActivity;
import com.ninetowns.tootooplus.activity.MyFreeGroupActivity;
import com.ninetowns.tootooplus.activity.MyInvitationActivity;
import com.ninetowns.tootooplus.activity.MyMessageActivity;
import com.ninetowns.tootooplus.activity.MyPriorityCodeActivity;
import com.ninetowns.tootooplus.activity.MyWishActivity;
import com.ninetowns.tootooplus.activity.PersonInfoActivity;
import com.ninetowns.tootooplus.activity.PhoneListMineActivity;
import com.ninetowns.tootooplus.activity.SettingActivity;
import com.ninetowns.tootooplus.bean.MineBean;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.MineParser;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.util.UIUtils;
import com.ninetowns.ui.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 点击"我的"进入该页面，即个人中心页面
 * @author huangchao
 *
 */
public class MineFragment extends BaseFragment<MineBean, MineParser> implements OnClickListener{
	
	/**封面图**/
	private ImageView mine_per_info_cover_iv;
	/**头像**/
	private ImageView person_center_photo;
	/**昵称**/
	private TextView person_center_name;
	/**vip等级**/
	private ImageView person_center_vip;
	
	private int screen_width = 0;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.mine_fragment, null);
    	
    	LinearLayout two_or_one_btn_head_back = (LinearLayout)view.findViewById(R.id.two_or_one_btn_head_back);
    	two_or_one_btn_head_back.setVisibility(View.INVISIBLE);
    	
    	LinearLayout ll_rootlayout = (LinearLayout)view.findViewById(R.id.ll_rootlayout);
    	ll_rootlayout.setBackgroundColor(getResources().getColor(R.color.white_trans));
    	
    	WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		screen_width = wm.getDefaultDisplay().getWidth();
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)view.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mine_title);
		
    	person_center_photo = (ImageView)view.findViewById(R.id.person_center_photo);
    	person_center_name = (TextView)view.findViewById(R.id.person_center_name);
    	person_center_vip = (ImageView)view.findViewById(R.id.person_center_vip);
    	mine_per_info_cover_iv = (ImageView)view.findViewById(R.id.mine_per_info_cover_iv);
    	
    	RelativeLayout mine_per_info_layout = (RelativeLayout)view.findViewById(R.id.mine_per_info_layout);
    	LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)mine_per_info_layout.getLayoutParams();
    	layoutParams.width = screen_width;
    	layoutParams.height = screen_width * 4 / 5;
    	mine_per_info_layout.setLayoutParams(layoutParams);
    	
    	//确定头像父布局正好在分割线上
    	RelativeLayout per_center_photo_layout = (RelativeLayout)view.findViewById(R.id.per_center_photo_layout);
    	RelativeLayout.LayoutParams photo_layoutParams = (RelativeLayout.LayoutParams)per_center_photo_layout.getLayoutParams();
    	float y = layoutParams.height * 19 / 30 - photo_layoutParams.height / 2 + mine_per_info_layout.getY();
    	per_center_photo_layout.setY(y);
    	LinearLayout facetoface = (LinearLayout)view.findViewById(R.id.mine_face_to_face);
    	LinearLayout mine_jump_per_info_layout = (LinearLayout)view.findViewById(R.id.mine_jump_per_info_layout);
    	LinearLayout mine_act_apply_layout_group = (LinearLayout)view.findViewById(R.id.mine_act_apply_layout_group);
    
    	
    	LinearLayout mine_act_apply_layout = (LinearLayout)view.findViewById(R.id.mine_act_apply_layout);
    	LinearLayout mine_msg_layout = (LinearLayout)view.findViewById(R.id.mine_msg_layout);
    	LinearLayout mine_recommend_layout = (LinearLayout)view.findViewById(R.id.mine_recommend_layout);
    	LinearLayout mine_set_layout = (LinearLayout)view.findViewById(R.id.mine_set_layout);
    	
    	LinearLayout mine_invite_layout = (LinearLayout)view.findViewById(R.id.mine_invite_layout);
    	LinearLayout mine_collect_layout = (LinearLayout)view.findViewById(R.id.mine_collect_layout);
    	LinearLayout mine_dp_layout = (LinearLayout)view.findViewById(R.id.mine_dp_layout);
    	LinearLayout mine_address_layout = (LinearLayout)view.findViewById(R.id.mine_address_layout);
    	LinearLayout mine_yxm_layout = (LinearLayout)view.findViewById(R.id.mine_yxm_layout);
    	LinearLayout mine_yxm_layout_phone = (LinearLayout)view.findViewById(R.id.mine_yxm_layout_phone);
    	
    	facetoface.setOnClickListener(this);
    	mine_act_apply_layout.setOnClickListener(this);
    	mine_act_apply_layout_group.setOnClickListener(this);
    	mine_msg_layout.setOnClickListener(this);
    	mine_recommend_layout.setOnClickListener(this);
    	mine_set_layout.setOnClickListener(this);
    	
    	mine_invite_layout.setOnClickListener(this);
    	mine_collect_layout.setOnClickListener(this);
    	mine_dp_layout.setOnClickListener(this);
    	mine_address_layout.setOnClickListener(this);
    	mine_yxm_layout.setOnClickListener(this);
    	
    	mine_jump_per_info_layout.setOnClickListener(this);
    	mine_yxm_layout_phone.setOnClickListener(this);
    	
        return view;
    }
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public MineParser setParser(String str) {
    	
        return new MineParser(str);
     
    }

    @Override
    public void getParserResult(MineBean parserResult) {
    	if(parserResult != null){
    		ImageLoader.getInstance().displayImage(parserResult.getMine_logoUrl(), person_center_photo, CommonUtil.OPTIONS_BIG_HEADPHOTO);
    		ImageLoader.getInstance().displayImage(parserResult.getMine_CoverImage(), mine_per_info_cover_iv, CommonUtil.MINE_COVER_OPTIONS);
    		
    		if(!TextUtils.isEmpty(parserResult.getMine_userName())){
    			person_center_name.setText(parserResult.getMine_userName());
    		} else {
    			person_center_name.setText("");
    		}
    		
    		//用户vip
    		CommonUtil.showCenterVip(person_center_vip, parserResult.getMine_userGrade());
    		
    	}
    }

    @Override
    public RequestParamsNet getApiParmars() {
    	
    	RequestParamsNet requestParamsNet = new RequestParamsNet();
    	requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.MINE_URL);
    	requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.MINE_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
        return requestParamsNet;
    }


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.mine_face_to_face://面对面参团 
			Intent intent =new Intent(getActivity(),FaceToFaceGroupEnterActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mine_jump_per_info_layout:
			//个人信息
			Intent perInfo = new Intent(getActivity(), PersonInfoActivity.class);
			startActivity(perInfo);
			
			break;
		case R.id.mine_act_apply_layout_group:
			// 我的白吃团
			Intent free_intent = new Intent(getActivity(),
					MyFreeGroupActivity.class);
			startActivity(free_intent);
			break;
		case R.id.mine_act_apply_layout:
			//我的白吃活动
			Intent myActApply=new Intent(getActivity(),MyActivityCommentActivity.class);
			myActApply.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(myActApply);
			
			break;
		case R.id.mine_msg_layout:
			//我的消息
			Intent myMessage = new Intent(getActivity(), MyMessageActivity.class);
			startActivity(myMessage);
			break;
		case R.id.mine_recommend_layout:
			//我的推荐
			Intent myWishActivity=new Intent(getActivity(),MyWishActivity.class);
			myWishActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(myWishActivity);
	
			break;
		case R.id.mine_set_layout:
			//设置
			Intent setting = new Intent(getActivity(), SettingActivity.class);
			startActivity(setting);
			
			break;
			
		case R.id.mine_invite_layout:
			//我的邀请
			
			UIUtils.startActivity(getActivity(), "", MyInvitationActivity.class);
			break;
			
		case R.id.mine_collect_layout:
			//我的收藏
			UIUtils.startActivity(getActivity(), "", MyCollectionActivity.class);
			
			
			break;
			
		case R.id.mine_dp_layout://我的点评
			Intent intentFree=new Intent(getActivity(),MyFreeCommentActivity.class);
			intentFree.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentFree);
		
			break;
			
		case R.id.mine_address_layout:
			//我的收货地址
			Intent address_intent = new Intent(getActivity(), AddressListMineActivity.class);
			startActivity(address_intent);
			break;
			
			
		case R.id.mine_yxm_layout:
			//我的优先码
			UIUtils.startActivity(getActivity(), "", MyPriorityCodeActivity.class);
			break;
		case R.id.mine_yxm_layout_phone:
			//我的联系方式
			Intent phone = new Intent(getActivity(), PhoneListMineActivity.class);
			startActivity(phone);
			break;
		
			
		}
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!"".equals(SharedPreferenceHelper.getLoginUserName(getActivity()))){
			person_center_name.setText(SharedPreferenceHelper.getLoginUserName(getActivity()));
		}
		
		if(SharedPreferenceHelper.getLoginMsg(getActivity()) != null && !"".equals(SharedPreferenceHelper.getLoginMsg(getActivity()).getLogin_logoUrl())){
			ImageLoader.getInstance().displayImage(SharedPreferenceHelper.getLoginMsg(getActivity()).getLogin_logoUrl(), person_center_photo, CommonUtil.OPTIONS_BIG_HEADPHOTO);
		}
		
		if(SharedPreferenceHelper.getLoginMsg(getActivity()) != null && !"".equals(SharedPreferenceHelper.getLoginMsg(getActivity()).getLogin_coverImage())){
			ImageLoader.getInstance().displayImage(SharedPreferenceHelper.getLoginMsg(getActivity()).getLogin_coverImage(), mine_per_info_cover_iv, CommonUtil.MINE_COVER_OPTIONS);
		}
	}
	

}