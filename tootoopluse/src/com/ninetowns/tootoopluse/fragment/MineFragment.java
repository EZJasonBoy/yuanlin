package com.ninetowns.tootoopluse.fragment;


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
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.activity.ChangePwdActivity;
import com.ninetowns.tootoopluse.activity.MyActivityApplyActivity;
import com.ninetowns.tootoopluse.activity.MyLookDataActivity;
import com.ninetowns.tootoopluse.activity.MyMessageActivity;
import com.ninetowns.tootoopluse.activity.MyWishActivity;
import com.ninetowns.tootoopluse.activity.PersonInfoActivity;
import com.ninetowns.tootoopluse.activity.SettingActivity;
import com.ninetowns.tootoopluse.activity.ShopCertificationActivity;
import com.ninetowns.tootoopluse.bean.MineBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.MineParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
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
	/**商家id**/
	private TextView mine_store_id;
	
	private RelativeLayout two_or_one_btn_head_second_layout;
	
	private TextView two_or_one_btn_head_second_tv;
	
	private int screen_width = 0;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.mine_fragment, null);
    	
    	LinearLayout two_or_one_btn_head_back = (LinearLayout)view.findViewById(R.id.two_or_one_btn_head_back);
    	two_or_one_btn_head_back.setVisibility(View.INVISIBLE);
    	
    	WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		screen_width = wm.getDefaultDisplay().getWidth();
		
		//标题
		TextView two_or_one_btn_head_title = (TextView)view.findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mine_title);
		
		two_or_one_btn_head_second_layout = (RelativeLayout)view.findViewById(R.id.two_or_one_btn_head_second_layout);
		two_or_one_btn_head_second_tv = (TextView)view.findViewById(R.id.two_or_one_btn_head_second_tv);
		two_or_one_btn_head_second_tv.setText(R.string.mine_store_sure);
		two_or_one_btn_head_second_tv.setTextSize(12);
		
        
    	person_center_photo = (ImageView)view.findViewById(R.id.person_center_photo);
    	person_center_name = (TextView)view.findViewById(R.id.person_center_name);
    	person_center_vip = (ImageView)view.findViewById(R.id.person_center_vip);
    	mine_store_id = (TextView)view.findViewById(R.id.mine_store_id);
    	mine_per_info_cover_iv = (ImageView)view.findViewById(R.id.mine_per_info_cover_iv);
    	
    	RelativeLayout mine_per_info_layout = (RelativeLayout)view.findViewById(R.id.mine_per_info_layout);
    	LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)mine_per_info_layout.getLayoutParams();
    	layoutParams.width = screen_width;
    	layoutParams.height = screen_width * 2 / 3;
    	mine_per_info_layout.setLayoutParams(layoutParams);
    	
    	LinearLayout mine_act_apply_layout = (LinearLayout)view.findViewById(R.id.mine_act_apply_layout);
    	LinearLayout mine_msg_layout = (LinearLayout)view.findViewById(R.id.mine_msg_layout);
    	LinearLayout mine_sj_layout = (LinearLayout)view.findViewById(R.id.mine_sj_layout);
    	LinearLayout mine_recommend_layout = (LinearLayout)view.findViewById(R.id.mine_recommend_layout);
    	LinearLayout mine_change_pwd_layout = (LinearLayout)view.findViewById(R.id.mine_change_pwd_layout);
    	LinearLayout mine_set_layout = (LinearLayout)view.findViewById(R.id.mine_set_layout);
    	mine_per_info_layout.setOnClickListener(this);
    	mine_act_apply_layout.setOnClickListener(this);
    	mine_sj_layout.setOnClickListener(this);
    	mine_msg_layout.setOnClickListener(this);
    	mine_recommend_layout.setOnClickListener(this);
    	mine_change_pwd_layout.setOnClickListener(this);
    	mine_set_layout.setOnClickListener(this);
    	
    	
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
    		CommonUtil.showVip(person_center_vip, parserResult.getMine_userGrade());
    		
    		if(!TextUtils.isEmpty(parserResult.getMine_userId())){
    			mine_store_id.setText("ID:" + parserResult.getMine_userId());
    		} else {
    			mine_store_id.setText("ID:");
    		}
    		
    		if(!TextUtils.isEmpty(parserResult.getMine_submitStatus()) && parserResult.getMine_submitStatus().equals("1")){
    			two_or_one_btn_head_second_tv.setVisibility(View.GONE);
    		} else {
    			two_or_one_btn_head_second_tv.setVisibility(View.VISIBLE);
    			//店铺认证
    			two_or_one_btn_head_second_layout.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					Intent cert_intent = new Intent(getActivity(), ShopCertificationActivity.class);
    					cert_intent.putExtra("comeFrom", "mineFragment");
    					startActivity(cert_intent);
    					
    				}
    			});
    		}
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
		case R.id.mine_per_info_layout:
			//个人信息
			Intent perInfo = new Intent(getActivity(), PersonInfoActivity.class);
			startActivity(perInfo);
			
			break;
		case R.id.mine_act_apply_layout:
			//我的白吃活动申请
			Intent myActApply=new Intent(getActivity(),MyActivityApplyActivity.class);
			myActApply.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(myActApply);
			
			break;
		case R.id.mine_sj_layout:
			//我的数据
			Intent data_intent = new Intent(getActivity(),MyLookDataActivity.class);
			startActivity(data_intent);
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
		case R.id.mine_change_pwd_layout:
			//修改密码
			Intent intent = new Intent(getActivity(), ChangePwdActivity.class);
			startActivity(intent);
	
			break;
		case R.id.mine_set_layout:
			//设置
			Intent setting = new Intent(getActivity(), SettingActivity.class);
			startActivity(setting);
			
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