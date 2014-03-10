package com.pengfei.huibo;

import weibo4j.Comments;
import weibo4j.model.WeiboException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.pengfei.huibo.model.UserData;
import com.pengfei.huibo.utilities.ConstantUtil;
import com.pengfei.huibo.utilities.ToastUtil;

//微博评论界面

public class HomeWeiboComment extends Activity {

	/** 评论的输入文本框 */
	private EditText et_comment_content;

	/** 评论内容的字数 */
	private TextView tv_comment_counter;

	/** 是否同时评论给原微博 */
	private CheckBox cb_comment_option;

	/** 发送微博按钮 */
	private Button btn_comment_send;

	/** 评论的内容 */
	private String commentContent;

	/** 是否评论给原微博 */
	private boolean isCommentOri = false;

	/** 用户数据 */
	private UserData userData;

	/** 要评论的微博ID */
	private String statusid;

	/*
	 * (non-Javadoc)创建界面的回调方法
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// good for input
		View mainview = getLayoutInflater().inflate(R.layout.home_weibo_comment, null);
		setContentView(mainview);
		statusid = getIntent().getStringExtra(ConstantUtil.STATUS_ID);
		userData = MainHuibo.userData;

		et_comment_content = (EditText) mainview.findViewById(R.id.et_comment_content);
		tv_comment_counter = (TextView) mainview.findViewById(R.id.tv_comment_counter);
		cb_comment_option = (CheckBox) mainview.findViewById(R.id.cb_comment_option);
		btn_comment_send = (Button) mainview.findViewById(R.id.btn_comment_send);

		et_comment_content.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				commentContent = et_comment_content.getText().toString();
				int len = commentContent.length();
				if (len <= ConstantUtil.WEIBO_MAX_LENGTH) {
					tv_comment_counter.setTextColor(Color.BLACK);// back to normal
					len = ConstantUtil.WEIBO_MAX_LENGTH - len;
					if (!btn_comment_send.isEnabled()) {
						btn_comment_send.setEnabled(true);
					}
				} else {
					len = len - ConstantUtil.WEIBO_MAX_LENGTH;
					tv_comment_counter.setTextColor(Color.RED);// mention
					if (btn_comment_send.isEnabled()) {
						btn_comment_send.setEnabled(false);
					}
				}
				tv_comment_counter.setText(String.valueOf(len));
			}
		});

		cb_comment_option.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isCommentOri = isChecked;
			}
		});

	}

	/**
	 * 返回微博首页
	 * 
	 * @param v
	 *            View组件
	 */
	public void backToMainWeiboHome(View v) {
		finish();
	}

	/**
	 * 发送微博评论
	 * 
	 * @param v
	 *            View组件
	 */
	public void sendWeiboComment(View v) {
		if (null == commentContent || commentContent.equalsIgnoreCase("")) {
			ToastUtil.showShortToast(getApplicationContext(), "亲，说点什么吧！");
			return;
		}
		new AsyncTask<Void, Void, Void>() {// AsyncTask:call from UI thread
			protected Void doInBackground(Void... params) {
				Comments cm = new Comments();
				cm.client.setToken(userData.getToken());
				try {
					Integer comment_ori = isCommentOri ? 1 : 0;
					cm.createComment(commentContent, statusid, comment_ori);
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				ToastUtil.showShortToast(getApplicationContext(), "评论成功");
			}
		}.execute();
		finish();
	}

	/**
	 * 删除评论
	 * 
	 * @param v
	 *            View组件
	 */
	public void deleteWeiboComment(View v) {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("删除确认").setMessage("您确定要删除这条评论吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						et_comment_content.setText("");
					}
				}).setNegativeButton("取消", null).create();
		dialog.show();
	}
}