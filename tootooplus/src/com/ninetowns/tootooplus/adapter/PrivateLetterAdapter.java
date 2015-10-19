package com.ninetowns.tootooplus.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.activity.PrivateLetterChat;
import com.ninetowns.tootooplus.bean.PrivateLetterBean;
import com.ninetowns.tootooplus.photoview.CircleImageView;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.tootooplus.viewbader.BadgeView;
import com.nostra13.universalimageloader.core.ImageLoader;

/** 
* @ClassName: PrivateLetterAdapter 
* @Description:私信适配器
* @author zhou
* @date 2015-3-31 下午1:51:52 
*  
*/
public class PrivateLetterAdapter extends BaseAdapter {

	
	
	private List<PrivateLetterBean> mPrivateLetters;
	private Activity mContext;

	public PrivateLetterAdapter(Activity mContext,
			List<PrivateLetterBean> mPrivateLetterBeans) {
		this.mContext=mContext;
		this.mPrivateLetters=mPrivateLetterBeans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPrivateLetters==null?0:mPrivateLetters.size();
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
		final ViewHolder viewHolder;
		if(null==convertView){
			viewHolder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.privateletter_item, null);
			viewHolder.iv_headPhoto=(CircleImageView) convertView.findViewById(R.id.privateletteritem_iv_headphoto);
			viewHolder.tv_nickName=(TextView) convertView.findViewById(R.id.privateletteritem_tv_nickname);
			viewHolder.badge=new BadgeView(mContext, viewHolder.iv_headPhoto);
			viewHolder.privateletteritem_icon_vip = (ImageView)convertView.findViewById(R.id.privateletteritem_icon_vip);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		final PrivateLetterBean privateLetter=mPrivateLetters.get(position);
		if(null!=privateLetter){
			if(!StringUtils.isEmpty(privateLetter.LogoUrl))
			ImageLoader.getInstance().displayImage(privateLetter.LogoUrl, viewHolder.iv_headPhoto,CommonUtil.OPTIONS_HEADPHOTO_NOROUND);
			viewHolder.tv_nickName.setText(privateLetter.UserName);
			if(privateLetter.read>0){
				viewHolder.badge.setText(privateLetter.read+"");
				viewHolder.badge.show();
			}
			CommonUtil.showVip(viewHolder.privateletteritem_icon_vip, privateLetter.UserGrade);
			viewHolder.iv_headPhoto.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					viewHolder.badge.hide();
//					UIUtils.setViewGone(viewHolder.badge);
					Intent intent=new Intent(mContext,PrivateLetterChat.class);
					intent.putExtra("receiveuserid",privateLetter.UserId);
					mContext.startActivity(intent);
					viewHolder.badge.hide();
				}
			});
			
		}
		return convertView;
	}

	
	 
	static class ViewHolder{
		CircleImageView iv_headPhoto;//头像
		TextView tv_nickName;//昵称
//		TextView tv_msgSendTime;//消息发送时间
		BadgeView badge;
		
		ImageView privateletteritem_icon_vip;
		
	}
}
