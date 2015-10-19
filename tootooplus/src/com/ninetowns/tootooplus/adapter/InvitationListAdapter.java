package com.ninetowns.tootooplus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.bean.MyInvitationBean;
import com.ninetowns.tootooplus.photoview.CircleImageView;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @ClassName: InvitationListAdapter
 * @Description: 邀请列表适配器
 * @author zhou
 * @date 2015-4-20 下午5:01:07
 * 
 */
public class InvitationListAdapter extends BaseAdapter {

	private Activity mContext;

	public InvitationListAdapter(Activity mContext,List<MyInvitationBean> myInvitationBeans) {
		this.mContext = mContext;
		this.myInvitationBeans = myInvitationBeans == null ? new ArrayList<MyInvitationBean>()
				: myInvitationBeans;
	}

	private List<MyInvitationBean> myInvitationBeans;

//	public void setData(List<MyInvitationBean> myInvitationBeans) {
//		this.myInvitationBeans = myInvitationBeans == null ? new ArrayList<MyInvitationBean>()
//				: myInvitationBeans;
//		notifyDataSetInvalidated();
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.myInvitationBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (null == convertView) {
			convertView = View.inflate(mContext, R.layout.item_invitation,
					null);
			viewHolder=new ViewHolder();
			viewHolder.civ_headPhotoImageView=(CircleImageView) convertView.findViewById(R.id.item_invitation_civ_headphoto);
			viewHolder.tv_nickName=(TextView) convertView.findViewById(R.id.item_invitation_tv_nickname);
			viewHolder.iv_vipLevel=(ImageView) convertView.findViewById(R.id.item_invitation_icon_viplevel);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final MyInvitationBean myInvitationBean=myInvitationBeans.get(position);
		if(null!=myInvitationBean){
			ImageLoader.getInstance().displayImage(myInvitationBean.LogoUrl, viewHolder.civ_headPhotoImageView, CommonUtil.OPTIONS_HEADPHOTO_NOROUND);
			viewHolder.tv_nickName.setText(myInvitationBean.UserName);
		}
		return convertView;
	}

	static class ViewHolder {
		CircleImageView civ_headPhotoImageView;// 头像
		TextView tv_nickName;// 用户名
		ImageView iv_vipLevel;// 用户vip等级
	}

}
