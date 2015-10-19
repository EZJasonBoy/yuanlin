package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.application.TootooPlusEApplication;
import com.ninetowns.tootoopluse.bean.MyConstanctBean;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

public class MyConstanctAdapter extends BaseAdapter {
	private List<MyConstanctBean> constanctList;
	private Context context;

	public MyConstanctAdapter(List<MyConstanctBean> constanctList,Context context) {
		this.constanctList = constanctList;
		this.context=context;
	}

	@Override
	public int getCount() {
		return constanctList.size();
	}

	@Override
	public Object getItem(int position) {
		return constanctList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyConstanctBean itemBean = constanctList.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(
					TootooPlusEApplication.getAppContext()).inflate(
					R.layout.item_constanct, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String count = itemBean.getMemberCount();
		String name = itemBean.getName();
		String phone = itemBean.getPhone();
		
		if (!TextUtils.isEmpty(name)) {
			if(!TextUtils.isEmpty(count)){
//				 realName+ "  (" + count + "份)"
				viewHolder.mCtName.setText(name+ "  (" + count + "人)");
			}else{
				viewHolder.mCtName.setText(name);
			}
			

		}
		if (!TextUtils.isEmpty(phone)) {

			Drawable phoneDrawable = context.getResources().getDrawable(R.drawable.icon_phone);
			viewHolder.mCtPhone.setCompoundDrawablesWithIntrinsicBounds(phoneDrawable, null, null, null);
			viewHolder.mCtPhone.setText(phone);	
		
			
		}else{
			viewHolder.mCtPhone.setText("");	
		}
		viewHolder.mCtPhone.setTag(phone);
		viewHolder.mCtPhone.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String number=(String) v.getTag();
					if(!TextUtils.isEmpty(number)){
						Intent intent = new Intent(Intent.ACTION_DIAL);
						Uri data = Uri.parse("tel:" + number);
						intent.setData(data);
						context.startActivity(intent);
					}
				
				}
			});
		StringBuilder strbuild=new StringBuilder();
		strbuild.append(name).append("  (" + count + "人)").append("　　").append(phone);
		viewHolder.mRlTag.setTag(strbuild);
		viewHolder.mRlTag.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				StringBuilder stb=(StringBuilder) v.getTag();
				final String content=stb.toString();
				if(!TextUtils.isEmpty(content)){
					TooSureCancelDialog tooDialog = new TooSureCancelDialog(
							context);
					tooDialog.setDialogTitle(R.string.rainbo_hint);
					tooDialog
							.setDialogContent(R.string.comit_content);
					tooDialog.setTooDialogCallBack(new TooDialogCallBack() {

						@SuppressWarnings("deprecation")
						@Override
						public void onTooDialogSure() {
						
								CommonUtil.copyContent(content,context);
							
						}

						

						@Override
						public void onTooDialogCancel() {

						}
					});
					tooDialog.show();
				}
				
			
				
				return false;
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		@ViewInject(R.id.ct_name)
		public TextView mCtName;

		@ViewInject(R.id.ct_phone)
		public CheckedTextView mCtPhone;
		@ViewInject(R.id.rl_item_tag)
		public RelativeLayout mRlTag;

		

	}

}
