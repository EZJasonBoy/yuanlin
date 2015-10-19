package com.ninetowns.tootoopluse.activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.library.helper.AppManager;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.GroupMemberSortAdapter;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.GroupMember;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootoopluse.helper.PingYinUtil;
import com.ninetowns.tootoopluse.helper.PinyinComparator;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.helper.SideBar;
import com.ninetowns.tootoopluse.helper.SideBar.OnTouchingLetterChangedListener;
import com.ninetowns.tootoopluse.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootoopluse.parser.GroupMemberParser;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.Activity.BaseActivity;

/**
 * @ClassName: AllMembersListActivity
 * @Description: 群组成员列表
 * @author zhou
 * @date 2015-3-3 下午2:05:35
 * 
 */

@ContentView(R.layout.activity_allmemberlist)
public class AllMembersListActivity extends BaseActivity implements INetConstanst {

	/**
	 * @Fields mBackButton :返回按钮
	 */
	@ViewInject(R.id.setting_iv_back)
	private ImageView mBackButton;

	/**
	 * @Fields mTextDialog :显示点击 的首字母
	 */
	@ViewInject(R.id.groupmember_textdialog)
	private TextView mTextDialog;
	/**
	 * @Fields mSideBar : a-z布局
	 */
	@ViewInject(R.id.groupmember_sidebar)
	private SideBar mSideBar;

	@ViewInject(R.id.groupmember_listview)
	private ListView mSortListView;

	private List<GroupMember> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	private GroupMemberSortAdapter mAdapter;

	@OnClick(R.id.setting_iv_back)
	public void onBackButtonClick(View v) {
		AppManager.getAppManager().finishActivity(this);
	}

	/**
	 * @Fields mTitleText : 标题文字
	 */
	@ViewInject(R.id.commontitlebar_tv_title)
	private TextView mTitleText;
	
	private String groupId="1";
	
	private String group_type = "";
	
	@ViewInject(R.id.commontitlebar_second_tv)
	private TextView commontitlebar_second_tv;
	
	
	@ViewInject(R.id.two_or_one_btn_head_second_layout)
	private RelativeLayout exit_group_layout;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		groupId = getIntent().getStringExtra("groupid");
		
		group_type = getIntent().getStringExtra("group_type");
		
		initViews();

		
	}

	private void initViews() {
		mTitleText.setText(getResources().getString(R.string.groupmember));
		
		mSideBar.setTextView(mTextDialog);

		// 设置右侧触摸监听
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = mAdapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mSortListView.setSelection(position);
				}

			}
		});


		loadDataFromServer();

	}

	/** 
	* @Title: loadDataFromServer 
	* @Description: 从服务器获取数据
	* @param     设定文件 
	* @return void    返回类型 
	*/
	private void loadDataFromServer() {
		showProgressDialog(this);
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.addQueryStringParameter("GroupId", groupId);
		requestParamsNet.addQueryStringParameter("PageSize", "4000");
		requestParamsNet.addQueryStringParameter("Page", "1");
		CommonUtil.xUtilsGetSend(GET_GROUPMEMBER_LIST, requestParamsNet, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				closeProgressDialog(AllMembersListActivity.this);
				GroupMemberParser parser=new GroupMemberParser();
				
				SourceDateList = initialFbList(parser.getParseResult(responseInfo.result));
				String adminUserId = "";
				for(int i = 0; i < SourceDateList.size(); i++){
					if(SourceDateList.get(i).getIsAdmin().equals("1")){
						adminUserId = SourceDateList.get(i).getUserId();
					}
				}
				if(group_type.equals("-1") && !adminUserId.equals(SharedPreferenceHelper.getLoginUserId(AllMembersListActivity.this))){
					commontitlebar_second_tv.setText(R.string.all_mem_exit_group);
					commontitlebar_second_tv.setVisibility(View.VISIBLE);
					exit_group_layout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//退出群
							RequestParamsNet requestParamsNet = new RequestParamsNet();
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ALL_MEMBER_EXIT_GROUP_GROUPID, groupId);
							requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.ALL_MEMBER_EXIT_GROUP_USERID, SharedPreferenceHelper.getLoginUserId(AllMembersListActivity.this));
							CommonUtil.xUtilsGetSend(TootooeNetApiUrlHelper.ALL_MEMBER_EXIT_GROUP_URL,
									requestParamsNet, new RequestCallBack<String>() {

										@Override
										public void onSuccess(ResponseInfo<String> responseInfo) {
											// TODO Auto-generated method stub
											closeProgressDialog(AllMembersListActivity.this);
											String loginStr = new String(responseInfo.result);
											try {
												JSONObject jsonObj = new JSONObject(loginStr);
												if(jsonObj.has("Status")){
													if(jsonObj.getString("Status").equals("1")){
														//退群成功
														ComponentUtil.showToast(AllMembersListActivity.this, getResources().getString(R.string.all_mem_exit_group_success));
														
														Bundle bundle = new Bundle();
														Intent intent = new Intent(
																TootooPlusEApplication.getAppContext(),
																HomeActivity.class);
														intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
														bundle.putInt("tab_index", 1);
														intent.putExtra(ConstantsTooTooEHelper.BUNDLE, bundle);
														startActivity(intent);
													} else {
														//退群失败
														ComponentUtil.showToast(AllMembersListActivity.this, getResources().getString(R.string.all_mem_exit_group_fail));
													}
												}
												
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											

										}

										@Override
										public void onFailure(HttpException error,
												String msg) {
											// TODO Auto-generated method stub
											closeProgressDialog(AllMembersListActivity.this);
											ComponentUtil.showToast(AllMembersListActivity.this, getResources().getString(R.string.errcode_network_response_timeout));
										}
									});
						}
					});
				}
				pinyinComparator = new PinyinComparator();

				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList, pinyinComparator);
				mAdapter = new GroupMemberSortAdapter(AllMembersListActivity.this, SourceDateList);
				mSortListView.setAdapter(mAdapter);
				
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				closeProgressDialog(AllMembersListActivity.this);
				UIUtils.showCenterToast(AllMembersListActivity.this, msg);
			}
		});
	}

	/**
	 * 把列表的首字母获取并转为大写
	 * @param fbList
	 * @return
	 */
	private List<GroupMember> initialFbList(List<GroupMember> fbList){
		List<GroupMember> initialBeans = new ArrayList<GroupMember>();
		for(int i = 0; i < fbList.size(); i++){
			//汉字转换成拼音
			String pinyin = PingYinUtil.getPingYin(fbList.get(i).getUserName());
			if(group_type.equals("-1") && (!"1".equals(fbList.get(i).getIsAdmin()) || !"100".equals(fbList.get(i).getUserGrade()))){
				//"-1"表示为心愿群
				if(!"".equals(pinyin)){
					//第一个字母并转化为大写
					String sortString = pinyin.substring(0, 1).toUpperCase();
					// 正则表达式，判断首字母是否是英文字母
					if(sortString.matches("[A-Z]")){
						fbList.get(i).setSortLetters(sortString);
					}else{
						fbList.get(i).setSortLetters("#");
					}
				} else {
					fbList.get(i).setSortLetters("#");
				}
			} else {
				//为了把团长和商家单独提出来
				if("1".equals(fbList.get(i).getIsAdmin()) || "1".equals(fbList.get(i).getBusinessStatus())){
					fbList.get(i).setSortLetters("@");
				} else {
					if(!"".equals(pinyin)){
						//第一个字母并转化为大写
						String sortString = pinyin.substring(0, 1).toUpperCase();
						// 正则表达式，判断首字母是否是英文字母
						if(sortString.matches("[A-Z]")){
							fbList.get(i).setSortLetters(sortString);
						}else{
							fbList.get(i).setSortLetters("#");
						}
					} else {
						fbList.get(i).setSortLetters("#");
					}
				}
			}
			
			
			initialBeans.add(fbList.get(i));
		}
		return initialBeans;
					
	}


}
