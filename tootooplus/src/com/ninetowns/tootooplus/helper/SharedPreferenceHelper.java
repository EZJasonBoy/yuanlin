package com.ninetowns.tootooplus.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.ninetowns.library.helper.BaseSharedPreferenceHelper;
import com.ninetowns.tootooplus.bean.CityBean;
import com.ninetowns.tootooplus.bean.LoginBean;
import com.ninetowns.tootooplus.bean.UserLocBean;

public class SharedPreferenceHelper extends BaseSharedPreferenceHelper {

	/**
	 * 保存用户信息
	 */
	public static void saveLoginMsg(Context context, String login_source,
			String login_relationId, String login_Id, String login_name,
			String login_logoUrl, String login_publishStory,
			String login_business, String login_medal, String login_tCurrency,
			String login_isLOHAS, String login_userGrade,
			String login_coverImage) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.putString("login_source", login_source)
				.putString("login_relationId", login_relationId)
				.putString("login_Id", login_Id)
				.putString("login_name", login_name)
				.putString("login_logoUrl", login_logoUrl)
				.putString("login_publishStory", login_publishStory)
				.putString("login_business", login_business)
				.putString("login_medal", login_medal)
				.putString("login_tCurrency", login_tCurrency)
				.putString("login_isLOHAS", login_isLOHAS)
				.putString("login_userGrade", login_userGrade)
				.putString("login_coverImage", login_coverImage).commit();

	}

	/**
	 * 获取登录用户的信息
	 * 
	 * @param context
	 * @return
	 */
	public static LoginBean getLoginMsg(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		LoginBean loginBean = null;
		if (!sharePrefs.getString("login_Id", "").equals("")) {
			loginBean = new LoginBean();
			loginBean.setLogin_source(sharePrefs.getString("login_source", ""));
			loginBean.setLogin_relationId(sharePrefs.getString(
					"login_relationId", ""));
			loginBean.setLogin_Id(sharePrefs.getString("login_Id", ""));
			loginBean.setLogin_name(sharePrefs.getString("login_name", ""));
			loginBean.setLogin_logoUrl(sharePrefs
					.getString("login_logoUrl", ""));
			loginBean.setLogin_publishStory(sharePrefs.getString(
					"login_publishStory", ""));
			loginBean.setLogin_businessStatus(sharePrefs.getString(
					"login_business", ""));
			loginBean.setLogin_medal(sharePrefs.getString("login_medal", ""));
			loginBean.setLogin_tCurrency(sharePrefs.getString(
					"login_tCurrency", ""));
			loginBean.setLogin_isLOHAS(sharePrefs
					.getString("login_isLOHAS", ""));
			loginBean.setLogin_userGrade(sharePrefs.getString(
					"login_userGrade", ""));
			loginBean.setLogin_coverImage(sharePrefs.getString(
					"login_coverImage", ""));
		}

		return loginBean;
	}

	/**
	 * 获取登录用户的用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getLoginUserId(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		String loginUserId = "";
		if (!sharePrefs.getString("login_Id", "").equals("")) {
			loginUserId = sharePrefs.getString("login_Id", "");
		}

		return loginUserId;
	}

	/**
	 * 获取登录用户的用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getLoginUserName(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		String loginUserName = "";
		if (!sharePrefs.getString("login_name", "").equals("")) {
			loginUserName = sharePrefs.getString("login_name", "");
		}

		return loginUserName;
	}

	/**
	 * 修改本地存储的用户昵称
	 * 
	 * @param nick_name
	 * @param context
	 */
	public static void changeLoginName(String nick_name, Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if (loginBean != null) {
			Editor editor = sharePrefs.edit();
			editor.putString("login_name", nick_name).commit();
		}
	}

	/**
	 * 修改本地存储的用户的头像地址
	 * 
	 * @param logo_url
	 * @param context
	 */
	public static void changeLoginLogoUrl(String logo_url, Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if (loginBean != null) {
			Editor editor = sharePrefs.edit();
			editor.putString("login_logoUrl", logo_url).commit();
		}

	}

	/**
	 * 修改本地存储的用户的封面图地址
	 * 
	 * @param login_coverImage
	 * @param context
	 */
	public static void changeLoginCoverUrl(String login_coverImage,
			Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		LoginBean loginBean = getLoginMsg(context);
		if (loginBean != null) {
			Editor editor = sharePrefs.edit();
			editor.putString("login_coverImage", login_coverImage).commit();
		}

	}

	/**
	 * 清除登录用户的信息
	 * 
	 * @param context
	 */
	public static void clearLoginMsg(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.clear().commit();
	}

	/**
	 * @author huangchao 获取手机唯一标示(先是获取androidId, 然后是diviceId,
	 *         再是随机UUID)，如果是随机获取得UUID，保存到SharedPreferences文件中
	 */
	public static String phoneUniqueId(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_SHARE_PREFS, Context.MODE_PRIVATE);

		String phone_unique_id = sharePrefs.getString("phone_unique_id", "");

		if (!phone_unique_id.equals("")) {
			return phone_unique_id;
		} else {
			String android_id = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
			// 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
			if (android_id != null && !android_id.equals("9774d56d682e549c")) {
				return android_id;
			} else {
				String device_id = ((TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE))
						.getDeviceId();
				if (device_id != null) {
					return device_id;
				} else {
					String uu_id = UUID.randomUUID().toString();
					sharePrefs.edit().putString("phone_unique_id", uu_id)
							.commit();
					return uu_id;
				}

			}
		}

	}

	/**
	 * 保存发货地址
	 * 
	 * @param context
	 * @param sendArea
	 */
	public static void saveSendCity(Context context, String sendArea,
			String sendAreaId) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,
				Context.MODE_PRIVATE);
		sharePrefs.edit().putString("sendArea", sendArea)
				.putString("sendAreaId", sendAreaId).commit();
	}

	/**
	 * 获取发货地址
	 * 
	 * @param context
	 * @return
	 */
	public static CityBean getSendCity(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,
				Context.MODE_PRIVATE);
		CityBean cityBean = new CityBean();
		cityBean.setCity_name(sharePrefs.getString("sendArea", ""));
		cityBean.setCity_id(sharePrefs.getString("sendAreaId", ""));
		return cityBean;
	}

	/**
	 * 保存到达地址
	 * 
	 * @param context
	 * @param arriveArea
	 */
	public static void saveArriveCity(Context context, String arriveArea,
			String arriveAreaId) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,
				Context.MODE_PRIVATE);
		sharePrefs.edit().putString("arriveArea", arriveArea)
				.putString("arriveAreaId", arriveAreaId).commit();
	}

	/**
	 * 获取到达地址
	 * 
	 * @param context
	 * @return
	 */
	public static CityBean getArriveCity(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,
				Context.MODE_PRIVATE);
		CityBean cityBean = new CityBean();
		cityBean.setCity_name(sharePrefs.getString("arriveArea", ""));
		cityBean.setCity_id(sharePrefs.getString("arriveAreaId", ""));
		return cityBean;
	}

	/**
	 * 清除发货和到达地址
	 * 
	 * @param context
	 */
	public static void clearArea(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_AREA_SHARE_PREFS,
				Context.MODE_PRIVATE);
		Editor editor = sharePrefs.edit();
		editor.clear().commit();
	}

	/**
	 * 保存白吃类型
	 * 
	 * @param context
	 * @param typeList
	 */
	public static void saveLineType(Context context, List<String> typeList) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.ACT_TYPE_SHARE_PREFS,
				Context.MODE_PRIVATE);
		sharePrefs.edit().putString("actType0", typeList.get(0))
				.putString("actType1", typeList.get(1)).commit();
	}

	/**
	 * 获取白吃活动类型
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getLineType(Context context) {
		List<String> typeList = new ArrayList<String>();
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.ACT_TYPE_SHARE_PREFS,
				Context.MODE_PRIVATE);
		typeList.add(sharePrefs.getString("actType0", ""));
		typeList.add(sharePrefs.getString("actType1", ""));
		return typeList;
	}

	/**
	 * 保存用户经纬度和所在城市
	 * 
	 * @param context
	 * @param typeList
	 */
	public static void saveUserLocation(Context context, String locCity,
			String locLatitude, String locLongitude) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_LOCATION_SHARE_PREFS,
				Context.MODE_PRIVATE);
		sharePrefs.edit().putString("locCity", locCity)
				.putString("locLatitude", locLatitude)
				.putString("locLongitude", locLongitude).commit();
	}

	/**
	 * 获取用户经纬度和所在城市
	 * 
	 * @param context
	 * @return
	 */
	public static UserLocBean getUserLocation(Context context) {
		SharedPreferences sharePrefs = context.getSharedPreferences(
				ConstantsTooTooEHelper.USER_LOCATION_SHARE_PREFS,
				Context.MODE_PRIVATE);
		UserLocBean userLocBean = new UserLocBean();
		userLocBean.setLocCity(sharePrefs.getString("locCity", ""));
		userLocBean.setLocLatitude(sharePrefs.getString("locLatitude", ""));
		userLocBean.setLocLongitude(sharePrefs.getString("locLongitude", ""));

		return userLocBean;
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
	 * @Description: 获得点评的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideCreateComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_COMMENT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideCreateComment
	 * @Description: 设置点评引导页已点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideCreateComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_CREATE_COMMENT,
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
	 * @Title: getFirstGuideWriteComment
	 * @Description: 获得写评论引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideWriteComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_WRITE_COMMENT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideWriteComment
	 * @Description: 设置写评论
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideWriteComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_WRITE_COMMENT,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideWriteComment
	 * @Description: 提交组团报名引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideComitComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_COMMIT_REGISTER, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideComitComment
	 * @Description: 设置提交组图引导页为点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideComitComment(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_COMMIT_REGISTER,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideGoToGroup
	 * @Description: 获得去组团大白吃报名的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideGoToGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_REGISTER, true);// 默认是true

		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideComitComment
	 * @Description: 设置去组团大白吃报名引导页为点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideGoToGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_REGISTER, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideGoToGroup
	 * @Description: 获得去组团小白吃报名的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideSmallGoToGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_SMALL_REGISTER, true);// 默认是true

		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideComitComment
	 * @Description: 设置去组团小白吃报名引导页为点击过
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideSmallGoToGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_SMALL_REGISTER,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideGoToGroup
	 * @Description: 查看组团的信息
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideLookGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GROUPINFO, true);// 默认是true

		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideComitComment
	 * @Description: 设置查看组团信息
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideLookGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GROUPINFO,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideLookInfo
	 * @Description: 查看i白吃活动信息
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideLookInfo(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_INFO, true);// 默认是true

		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideLookInfo
	 * @Description: 设置查看白吃活动信息
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideLookInfo(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_INFO, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideLookNumberGroup
	 * @Description: 查看招募组团的成员信息
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideLookNumberGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_ALREADY_GROUP, true);// 默认是true

		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideLookNumberGroup
	 * @Description: 设置查看招募组团成员的信息
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideLookNumberGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(
						ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_ALREADY_GROUP,
						false).commit();
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
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_DROP, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideActChat
	 * @Description: 活动群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideActChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_ACT_CHAT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideActChat
	 * @Description: 设置活动群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideActChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_ACT_CHAT, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideWisChat
	 * @Description: 心愿群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideWisChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_WIS_CHAT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideWisChat
	 * @Description: 设置心愿群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideWisChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_WIS_CHAT, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideBaiChiChat
	 * @Description: 心愿群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideBaiChiChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_BAICHI_CHAT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideBaiChiChat
	 * @Description: 设置白吃团群聊
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideBaiChiChat(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_BAICHI_CHAT,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideLookGoGroup
	 * @Description: 显示组团人数
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideLookGoGroupCount(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GOGROUPCOUNT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideLookGroupCount
	 * @Description: 显示组团人数
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideLookGroupCount(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(
						ConstantsTooTooEHelper.FIRST_GUIDE_LOOK_GOGROUPCOUNT,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideClickGroupCount
	 * @Description: 点击显示组团人数
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideClickGroupCount(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_CLICK_GOGROUPCOUNT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideClickGroupCount
	 * @Description: 点击显示组团人数
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideClickGroupCount(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(
						ConstantsTooTooEHelper.FIRST_GUIDE_CLICK_GOGROUPCOUNT,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGetMembersGroup
	 * @Description:完成组团的信息提交组团的提示
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGetMembersGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_GET_MEMBERS_GROUP, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setNoFirstGuideGetMembersGroup
	 * @Description: :完成组团的信息提交组团的提示
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideGetMembersGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(
						ConstantsTooTooEHelper.FIRST_GUIDE_GET_MEMBERS_GROUP,
						false).commit();
	}

	/**
	 * 
	 * @Title: getFirstGuideFaceToFace
	 * @Description: 面对面提示
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGuideFaceToFace(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: getFirstGuideFaceToFace
	 * @Description: 面对面提示设置
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideGuideFaceToFace(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE,
						false).commit();
	}

	/**
	 * 
	 * @Title: setNoFirstGuideFaceToFaceDetailAct
	 * @Description: 设置活动详情中的面对面邀请
	 * @param
	 * @return
	 * @throws
	 */
	public static void setNoFirstGuideFaceToFaceDetailAct(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(
						ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE_DETAIL,
						false).commit();
	}

	public static boolean getFirstGuideFaceToFaceDetailAct(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_FACE_TO_FACE_DETAIL, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setIsFirstGroup
	 * @Description: 设置第一次组团
	 * @param
	 * @return
	 * @throws
	 */
	public static void setIsFirstGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GROUP, true).commit();
	}

	/**
	 * 
	 * @Title: getFirstGroup
	 * @Description: 判断是否是第一次组团
	 * @param
	 * @return
	 * @throws
	 */
	public static boolean getFirstGroup(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GROUP, false);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setIsFirstShowShare
	 * @Description: 设置显示分享的引导页
	 * @param
	 * @return
	 * @throws
	 */
	public static void setIsFirstShowShare(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_SHARE, false)
				.commit();
	}

	public static boolean getFirstShowShare(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_SHARE, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setIsFirstDianzan
	 * @Description: 点赞获得白吃计划
	 * @param
	 * @return
	 * @throws
	 */
	public static void setIsFirstDianzan(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish.edit()
				.putBoolean(ConstantsTooTooEHelper.FIRST_GUIDE_DIANZAN, false)
				.commit();
	}

	/**
	 * 
	 * @Title: getFirstDianzan
	 * @Description: 点赞
	 * @param
	 * @return
	 * @throws
	 */

	public static boolean getFirstDianzan(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.FIRST_GUIDE_DIANZAN, true);// 默认是true
		return isFirst;
	}
	
	
	public static boolean getFirstGuideGoBuyAct(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		boolean isFirst = sharFirstGuideWish.getBoolean(
				ConstantsTooTooEHelper.First_GUIDE_GO_BUY_ACT, true);// 默认是true
		return isFirst;
	}

	/**
	 * 
	 * @Title: setFirstGuideGoBuyAct
	 * @Description: 提示购买后升级
	 * @param
	 * @return
	 * @throws
	 */
	public static void setFirstGuideGoBuyAct(Context context) {
		SharedPreferences sharFirstGuideWish = context.getSharedPreferences(
				ConstantsTooTooEHelper.FIRST_GUIDE, Context.MODE_PRIVATE);
		sharFirstGuideWish
				.edit()
				.putBoolean(ConstantsTooTooEHelper.First_GUIDE_GO_BUY_ACT,
						false).commit();
	}

}
