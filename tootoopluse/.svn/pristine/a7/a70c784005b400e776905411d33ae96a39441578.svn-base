package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.MessageBean;
import com.ninetowns.tootoopluse.helper.SharedPreferenceHelper;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.ninetowns.tootoopluse.util.INetConstanst;
import com.ninetowns.tootoopluse.util.TimeUtil;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/** 
* @ClassName: MessageAdapter 
* @Description:我的消息适配器
* @author zhou
* @date 2015-3-31 下午1:50:42 
*  
*/
public class MessageAdapter extends BaseAdapter implements INetConstanst{

	private Activity mContext;
	private List<MessageBean> messageBeans;

	public MessageAdapter(Activity mContext, List<MessageBean> parserResult) {
		this.mContext=mContext;
		this.messageBeans=parserResult;
	}

	@Override
	public int getCount() {
		return messageBeans==null?0:messageBeans.size();
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
	ViewHolder viewHolder=null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(null==convertView){
			viewHolder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.mymsg_item, null);
			viewHolder.iv_headPhoto=(ImageView) convertView.findViewById(R.id.mymsgitem_iv_headphoto);
			viewHolder.tv_nickName=(TextView) convertView.findViewById(R.id.mymsgitem_tv_nickname);
			viewHolder.tv_msgContent=(TextView) convertView.findViewById(R.id.mymsgitem_tv_msgcontent);
			viewHolder.tv_msgSendTime=(TextView) convertView.findViewById(R.id.mymsgitem_tv_msgsendtime);
			viewHolder.btn_agree= (TextView) convertView.findViewById(R.id.mymsgitem_btn_agree);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final MessageBean messageBean=messageBeans.get(position);
		if(null!=messageBean){
			ImageLoader.getInstance().displayImage(messageBean.getLogoUrl(), viewHolder.iv_headPhoto, CommonUtil.OPTIONS_HEADPHOTO);
			viewHolder.tv_nickName.setText(messageBean.getUserName());
			viewHolder.tv_msgContent.setText(messageBean.getContent());
			if(displayAgreeButto(messageBean)){
				UIUtils.setViewVisible(viewHolder.btn_agree);
			}else{
				UIUtils.setViewGone(viewHolder.btn_agree);
			}
			viewHolder.tv_msgSendTime.setText(TimeUtil.getTimeDisplay(messageBean.getCreateDate(), mContext));
			
			viewHolder.btn_agree.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
			System.out.println("+++++++++++++++messageBean+++++++++++>" + messageBean);
					agreeActivity(messageBean,viewHolder.btn_agree);
				}

				
			});
		}
		
		return convertView;
	}

	/** 
	* @Title: displayAgreeButto 
	* @Description: 是否显示同意按钮
	* @param @param messageBean
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	private boolean displayAgreeButto(MessageBean messageBean) {
		return "1".equals(messageBean.getAgree());
	}

	
	
	/** 
	* @param messageBean 
	 * @param btn_agree 
	 * @Title: agreeActivity 
	* @Description: 同意一块组团活动
	* @param     设定文件 
	* @return void    返回类型 
	*/
	private void agreeActivity(final MessageBean messageBean, final TextView btn_agree) {
		RequestParamsNet requestParamsNet =new RequestParamsNet();
		requestParamsNet.addQueryStringParameter("UserId",SharedPreferenceHelper.getLoginUserId(mContext));
		requestParamsNet.addQueryStringParameter("MessageId",messageBean.getMessageId());
		requestParamsNet.addQueryStringParameter("Agree",messageBean.getAgree());
		CommonUtil.xUtilsGetSend(AGREE_ACTIVITY, requestParamsNet, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				try {
					JSONObject obj = new JSONObject(responseInfo.result);
					if(obj.has("Status")){
						if(obj.getString("Status").equals("1")){
							UIUtils.setViewGone(btn_agree);
							messageBean.setAgree("0");
							notifyDataSetChanged();
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				ComponentUtil
				.showToast(
						mContext,
						mContext.getResources()
								.getString(
										R.string.errcode_network_response_timeout));
			}
		});
		
	}
	static class ViewHolder{
		ImageView iv_headPhoto;//头像
		TextView tv_msgContent;//消息内容
		TextView tv_nickName;//昵称
		TextView tv_msgSendTime;//消息发送时间
		TextView btn_agree;//消息发送时间
	}
}
