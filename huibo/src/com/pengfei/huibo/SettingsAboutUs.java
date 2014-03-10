package com.pengfei.huibo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

//关于作者

public class SettingsAboutUs extends Activity {

	/*
	 * (non-Javadoc)创建界面的回调方法
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_aboutus);
	}

	/**
	 * 界面返回
	 * 
	 * @param v
	 *            View组件
	 */
	public void btn_back(View v) {
		finish();
	}
}