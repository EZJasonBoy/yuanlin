package com.ninetowns.tootoopluse.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.ninetowns.library.helper.BaseSharedPreferenceHelper;
import com.ninetowns.tootoopluse.bean.CityBean;
import com.ninetowns.tootoopluse.bean.LoginBean;

public class SharedPreferenceHelper extends BaseSharedPreferenceHelper{
	
	/**
	 * 保存用户信息
	 */
	public static void saveLoginMsg(Context context, String login_source, String login_relationId, String login_Id,
			String login_name, String login_logoUrl,String login_publishStory, String login_business, String login_medal, String login_tCurrency, 
			String login_isLOHAS, String login_userGrade, String login_coverImage){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.putString("login_source", login_source).putString("login_relationId", login_relationId).putString("login_Id", login_Id).putString("login_name", login_name)
		.putString("login_logoUrl", login_logoUrl).putString("login_publishStory", login_publishStory).putString("login_business", login_business)
		.putString("login_medal", login_medal).putString("login_tCurrency", login_tCurrency).putString("login_isLOHAS", login_isLOHAS)
		.putString("login_userGrade", login_userGrade).putString("login_coverImage", login_coverImage).commit();
		
	}
	
	/**
	 * 获取登录用户的信息
	 * @param context
	 * @return
	 */
	public static LoginBean getLoginMsg(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		LoginBean loginBean = null;
		if(!sharePrefs.getString("login_Id", "").equals("")){
			loginBean = new LoginBean();
			loginBean.setLogin_source(sharePrefs.getString("login_source", ""));
			loginBean.setLogin_relationId(sharePrefs.getString("login_relationId", ""));
			loginBean.setLogin_Id(sharePrefs.getString("login_Id", ""));
			loginBean.setLogin_name(sharePrefs.getString("login_name", ""));
			loginBean.setLogin_logoUrl(sharePrefs.getString("login_logoUrl", ""));
			loginBean.setLogin_publishStory(sharePrefs.getString("login_publishStory", ""));
			loginBean.setLogin_businessStatus(sharePrefs.getString("login_business", ""));
			loginBean.setLogin_medal(sharePrefs.getString("login_medal", ""));
			loginBean.setLogin_tCurrency(sharePrefs.getString("login_tCurrency", ""));
			loginBean.setLogin_isLOHAS(sharePrefs.getString("login_isLOHAS", ""));
			loginBean.setLogin_userGrade(sharePrefs.getString("login_userGrade", ""));
			loginBean.setLogin_coverImage(sharePrefs.getString("login_coverImage", ""));
		}
		
		return loginBean;
	}
	
	/**
	 * 获取登录用户的用户id
	 * @param context
	 * @return
	 */
	public static String getLoginUserId(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		String loginUserId = "";
		if(!sharePrefs.getString("login_Id", "").equals("")){
			loginUserId = sharePrefs.getString("login_Id", "");
		}
		
		return loginUserId;
	}
	
	
	/**
	 * 获取登录用户的用户名
	 * @param context
	 * @return
	 */
	public static String getLoginUserName(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		String loginUserName = "";
		if(!sharePrefs.getString("login_name", "").equals("")){
			loginUserName = sharePrefs.getString("login_name", "");
		}
		
		return loginUserName;
	}
	
	/**
	 * 修改本地存储的用户昵称
	 * @param nick_name
	 * @param context
	 */
	public static void changeLoginName(String nick_name, Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if(loginBean != null){
			Editor editor = sharePrefs.edit();
			editor.putString("login_name", nick_name).commit();
		}
	}
	
	
	/**
	 * 修改本地存储的用户的头像地址
	 * @param logo_url
	 * @param context
	 */
	public static void changeLoginLogoUrl(String logo_url, Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if(loginBean != null){
			Editor editor = sharePrefs.edit();
			editor.putString("login_logoUrl", logo_url).commit();
		}
		
	}
	
	
	/**
	 * 修改本地存储的用户的封面图地址
	 * @param login_coverImage
	 * @param context
	 */
	public static void changeLoginCoverUrl(String login_coverImage, Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if(loginBean != null){
			Editor editor = sharePrefs.edit();
			editor.putString("login_coverImage", login_coverImage).commit();
		}
		
	}
	
	/**
	 * 清除登录用户的信息
	 * @param context
	 */
	public static void clearLoginMsg(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS,Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.clear().commit();
	}
	
	
	/**
	 * @author huangchao
	 * 获取手机唯一标示(先是获取androidId, 然后是diviceId, 再是随机UUID)，如果是随机获取得UUID，保存到SharedPreferences文件中
	 */
	public static String phoneUniqueId(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		
		String phone_unique_id = sharePrefs.getString("phone_unique_id", "");
		
		if(!phone_unique_id.equals("")){
			return phone_unique_id;
		} else {
			String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			//在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
			if(android_id != null && !android_id.equals("9774d56d682e549c")){
				return android_id;
			} else {
				String device_id = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE )).getDeviceId();
				if(device_id != null){
					return device_id;
				} else {
					 String uu_id = UUID.randomUUID().toString();
					 sharePrefs.edit().putString("phone_unique_id", uu_id).commit();
					 return uu_id;
				}
				
			}
		}
		
	}
	
	/**
	 * 保存发货地址
	 * @param context
	 * @param sendArea
	 */
	public static void saveSendCity(Context context, String sendArea, String sendAreaId){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS, Context.MODE_PRIVATE);
		sharePrefs.edit().putString("sendArea", sendArea).putString("sendAreaId", sendAreaId).commit();
	}
	
	/**
	 * 获取发货地址
	 * @param context
	 * @return
	 */
	public static CityBean getSendCity(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS, Context.MODE_PRIVATE);
		CityBean cityBean = new CityBean();
		cityBean.setCity_name(sharePrefs.getString("sendArea", ""));
		cityBean.setCity_id(sharePrefs.getString("sendAreaId", ""));
		return cityBean;
	}
	
	/**
	 * 保存到达地址
	 * @param context
	 * @param arriveArea
	 */
	public static void saveArriveCity(Context context, String arriveArea, String arriveAreaId){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS, Context.MODE_PRIVATE);
		sharePrefs.edit().putString("arriveArea", arriveArea).putString("arriveAreaId", arriveAreaId).commit();
	}
	
	/**
	 * 获取到达地址
	 * @param context
	 * @return
	 */
	public static CityBean getArriveCity(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS, Context.MODE_PRIVATE);
		CityBean cityBean = new CityBean();
		cityBean.setCity_name(sharePrefs.getString("arriveArea", ""));
		cityBean.setCity_id(sharePrefs.getString("arriveAreaId", ""));
		return cityBean;
	}
	
	
	/**
	 * 清除发货和到达地址
	 * @param context
	 */
	public static void clearArea(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.clear().commit();
	}
	
	/**
	 * 保存白吃类型和最少份数
	 * @param context
	 * @param typeList
	 */
	public static void saveLineType(Context context, List<String> typeList, String free_count){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.ACT_TYPE_SHARE_PREFS, Context.MODE_PRIVATE);
		sharePrefs.edit().putString("actType0", typeList.get(0)).putString("actType1", typeList.get(1)).putString("free_count", free_count).commit();
	}
	
	/**
	 * 获取白吃活动类型
	 * @param context
	 * @return
	 */
	public static List<String> getLineType(Context context){
		List<String> typeList = new ArrayList<String>();
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.ACT_TYPE_SHARE_PREFS, Context.MODE_PRIVATE);
		typeList.add(sharePrefs.getString("actType0", ""));
		typeList.add(sharePrefs.getString("actType1", ""));
		return typeList;
	}
	
	/**
	 * 获取创建最少份数
	 */
	public static String getFreeCount(Context context){
		SharedPreferences sharePrefs = context.getSharedPreferences(ConstantsTooTooEHelper.ACT_TYPE_SHARE_PREFS, Context.MODE_PRIVATE);
		return sharePrefs.getString("free_count", "");
	}
	
	/**
	 * @return 
	 * 
	 * @Title: getFirstGuideCreateWish
	 * @Description: 获得创建心愿的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideCreateWish(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_WISH, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideCreateWish
	 * @Description: 设置创建心愿的引导页为已经点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideCreateWish(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_WISH,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideCreateActivity
	 * @Description: 获得创建活动的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideCreateActivity(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_ACTIVITY, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideCreateActivity
	 * @Description: 设置创建活动的引导页已点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideCreateActivity(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_ACTIVITY,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideCreateComment
	 * @Description: 获得查看网络地址引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideLookLink(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_LINK, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideLookLink
	 * @Description: 设置引导链接已点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideLookLink(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_LINK,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideCreateViewPager
	 * @Description: 获得图片显示的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideCreateViewPager(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_VIEWPAGER, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideCreateViewPager
	 * @Description: 设置获得图片引导页为点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideCreateViewPager(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_VIEWPAGER, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideDrop
	 * @Description: 获得拖拽引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideDrop(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_DROP, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideDrop
	 * @Description: 设置可以拖拽已点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideDrop(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_DROP,
						false).commit();
	}
	/**
	 * 
	 * @Title: getFirstGuideQD
	 * @Description: 获得签到引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideQD(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_QD, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideDrop
	 * @Description: 设置QD已点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideQD(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_QD,
						false).commit();
	}

	
}