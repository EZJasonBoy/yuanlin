package com.ninetowns.tootooplus.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.adapter.FansLvAdapter;
import com.ninetowns.tootooplus.bean.FansBean;
import com.ninetowns.tootooplus.helper.FansPinyinComparator;
import com.ninetowns.tootooplus.helper.PingYinUtil;
import com.ninetowns.tootooplus.helper.SharedPreferenceHelper;
import com.ninetowns.tootooplus.helper.SideBar;
import com.ninetowns.tootooplus.helper.SideBar.OnTouchingLetterChangedListener;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.parser.FansParser;
import com.ninetowns.ui.fragment.BaseFragment;

public class FansFragment extends BaseFragment<List<FansBean>, FansParser> {
	
	private ListView follow_lv;
	
	private TextView follow_dialog;
	
	private SideBar sideBar;
	
	private String other_userId = "";
	
	private List<FansBean> fansList = new ArrayList<FansBean>();
	
	private FansLvAdapter fansLvAdapter;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
    	View view = inflater.inflate(R.layout.follow_fragment, null);
    	
    	follow_lv = (ListView)view.findViewById(R.id.follow_lv);
    	
    	follow_dialog = (TextView)view.findViewById(R.id.follow_dialog);
    	
    	sideBar = (SideBar)view.findViewById(R.id.sideBar);
    	
    	return view;
	 }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		
		//跳转到关注列表得传递该参数
		other_userId = getActivity().getIntent().getStringExtra("other_userId");
		
        super.onLoadData(true, false, false);
        super.onActivityCreated(savedInstanceState);
    }

	@Override
	public FansParser setParser(String str) {
		// TODO Auto-generated method stub
		return new FansParser(str);
	}

	@Override
	public void getParserResult(List<FansBean> parserResult) {
		// TODO Auto-generated method stub
		if(parserResult != null){
			fansList = initialFbList(parserResult);
			
			FansPinyinComparator pinyinComparator = new FansPinyinComparator();
			// 根据a-z进行排序源数据
			Collections.sort(fansList, pinyinComparator);
			
			
			fansLvAdapter = new FansLvAdapter(getActivity(), fansList, other_userId);
			follow_lv.setAdapter(fansLvAdapter);
			
			
			sideBar.setTextView(follow_dialog);
			//设置右侧触摸监听
			sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
				
				@Override
				public void onTouchingLetterChanged(String s) {
					//该字母首次出现的位置
					int position = fansLvAdapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						follow_lv.setSelection(position);
					}
					
				}
			});
			
			
		}
		
		
	}

	@Override
	public RequestParamsNet getApiParmars() {
		// TODO Auto-generated method stub
		RequestParamsNet requestParamsNet = new RequestParamsNet();
		requestParamsNet.setmStrHttpApi(TootooeNetApiUrlHelper.FANS_LIST_URL);
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.FANS_LIST_USERID, SharedPreferenceHelper.getLoginUserId(getActivity()));
		requestParamsNet.addQueryStringParameter(TootooeNetApiUrlHelper.FANS_LIST_OTHER_USERID, other_userId);
		return requestParamsNet;
	}
	
	
	/**
	 * 把列表的首字母获取并转为大写
	 * @param fbList
	 * @return
	 */
	private List<FansBean> initialFbList(List<FansBean> fbList){
		List<FansBean> initialBeans = new ArrayList<FansBean>();
		for(int i = 0; i < fbList.size(); i++){
			
			//汉字转换成拼音
			String pinyin = PingYinUtil.getPingYin(fbList.get(i).getFans_userName());
			
			if(!"".equals(pinyin)){
				//第一个字母并转化为大写
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// 正则表达式，判断首字母是否是英文字母
				if(sortString.matches("[A-Z]")){
					fbList.get(i).setFans_initial(sortString);
				}else{
					fbList.get(i).setFans_initial("#");
				}
				
			} else {
				fbList.get(i).setFans_initial("#");
			}
			
			initialBeans.add(fbList.get(i));
		}
		return initialBeans;
					
	}

}
