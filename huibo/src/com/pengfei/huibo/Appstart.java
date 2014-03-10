package com.pengfei.huibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.pengfei.huibo.model.UserData;
import com.pengfei.huibo.utilities.*;


public class Appstart extends Activity {

	/*
	 * (non-Javadoc)创建界面的回调方法
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		
		new Handler().postDelayed(
				new Runnable() {
					@Override
					public void run() {
						UserData userdata = UserDataUtil.readUserData(getApplicationContext());// 得到用户数据
						
						if (NetworkUtil.getNetworkState(Appstart.this) != NetworkUtil.NONE) {// 有网络
							String localToken = userdata.getToken();
							String localExpiresIn = userdata.getExpirestime();
//							System.out.println("token = " + localToken);
							
							Intent intent = null;
							
							if (UserDataUtil.isTokenValid(localToken, localExpiresIn)) {// token还有效
								if (userdata.isFirstrun()) {// 第一次运行，进入欢迎界面
									intent = new Intent(Appstart.this, Whatsnew.class);
								} else {// 不是第一次运行，进入主界面
									intent = new Intent(Appstart.this, MainHuibo.class);
								}
							} 
							else 
							{// 无效，重新授权登陆
								intent = new Intent(Appstart.this, Welcome.class);
							}
							
							startActivity(intent);
							Appstart.this.finish();
							
						} 
						else 
						{// 没有网络
							ToastUtil.showShortToast(Appstart.this, "网络不可用哟");
						}
					}
				}, 2000);
	}
}