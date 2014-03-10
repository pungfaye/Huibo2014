package com.pengfei.huibo.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.pengfei.huibo.model.UserData;

//用户数据工具类
public class UserDataUtil {

	/** 用户ID */
	public static final String USERID = "userid";

	/** access token */
	public static final String TOKEN = "token";

	/** token过期时间 */
	public static final String EXPIRESTIME = "expirestime";

	/** 用户的头像URL */
	public static final String PROFILE = "profileimage";

	/** 用户的昵称 */
	public static final String NICKNAME = "nickname";

	/** 是否关注作者微博 */
	public static final String FOLLOW = "follow";

	/** 是否开启声音效果 */
	public static final String SOUND = "sound";

	/** 是否是第一次运行 */
	public static final String FIRSTRUN = "firstrun";

	/**
	 * 检查accessToken是否有效
	 * 
	 * @param accessToken
	 *            accessToken
	 * @param time
	 *            失效时间
	 * @return 如果有效返回true
	 */
	public static boolean isTokenValid(String accessToken, String time) {
		long expirestime = Long.parseLong(time);
		return (!TextUtils.isEmpty(accessToken) && (expirestime == 0 || (System.currentTimeMillis() < expirestime)));
	}

	/**
	 * 更新userData信息
	 * 
	 * @param context
	 *            Context
	 * @param userData
	 *            userData信息
	 */
	public static void updateUserData(Context context, UserData userData) {
		
		//打开SharedPreferences
		SharedPreferences.Editor editor = context.getSharedPreferences(ConstantUtil.HUIBO, Context.MODE_PRIVATE).edit();
		
		//通过SharedPreferences.Editor接口的putXxx方法保存key-value对。其中Xxx表示不同的数据类型。
		editor.putString(USERID, userData.getUserid());
		editor.putString(TOKEN, userData.getToken());
		editor.putString(EXPIRESTIME, userData.getExpirestime());
		editor.putString(NICKNAME, userData.getNickname());
		editor.putString(PROFILE, userData.getProfileimage());
		editor.putBoolean(FOLLOW, userData.isFollowauthor());
		editor.putBoolean(SOUND, userData.isSoundPlay());
		editor.putBoolean(FIRSTRUN, userData.isFirstrun());
		
		//通过SharedPreferences.Editor接口的commit方法保存key-value对。commit方法相当于数据库事务中的提交（commit）操作。
		editor.commit();
	}

	/**
	 * 删除userData
	 * 
	 * @param context
	 *            Context
	 */
	public static void clearUserData(Context context) {
		SharedPreferences.Editor editor = context.getSharedPreferences(ConstantUtil.HUIBO, Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 读取userData
	 * 
	 * @param context
	 *            Context
	 * @return 用户信息
	 */
	public static UserData readUserData(Context context) {
		SharedPreferences pref = context.getSharedPreferences(ConstantUtil.HUIBO, Context.MODE_PRIVATE);
//		return new UserData(pref.getString(TOKEN, ""), pref.getString(EXPIRESTIME, "0"), pref.getString(USERID, ""));
		
		return new UserData(pref.getString(TOKEN, ""), pref.getString(EXPIRESTIME, "0"), pref.getString(USERID, ""));
	}
}
