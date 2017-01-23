package com.example.coursespirit;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.coursespirit.db.UserBean;
import com.example.coursespirit.util.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {

	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private Button registerBtn, loginBtn;
	private EditText loginEt, passwordEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		/**
		 * 初始化BmobSDK
		 */
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
	}

	/**
	 * 
	 * 初始化控件
	 */
	private void initView() {
		registerBtn = (Button) this.findViewById(R.id.id_register_btn);
		loginBtn = (Button) this.findViewById(R.id.id_login_btn);
		loginEt = (EditText) this.findViewById(R.id.id_login_name_et);
		passwordEt = (EditText) this.findViewById(R.id.id_password_et);

		registerBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);

	}

	/**
	 * 注册
	 */
	

	/**
	 * 登陆
	 */
	private void login() {
		String loginId = loginEt.getText().toString();
		String password = passwordEt.getText().toString();
		if (loginId.isEmpty() || password.isEmpty()) {
			ToastUtils.toast(this, "密码或账号不能为空!");
			return;
		}

		BmobQuery<UserBean> userQuery = new BmobQuery<UserBean>();

		//查询条件 
		userQuery.addWhereEqualTo("loginId", loginId);
		userQuery.addWhereEqualTo("password", password);

		userQuery.findObjects(this, new FindListener<UserBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				ToastUtils.toast(Login.this, arg0 + "," + arg1 + " 登陆失败");
			}

			@Override
			public void onSuccess(List<UserBean> userList) {
				if (userList != null && userList.size() > 0){
					ToastUtils.toast(Login.this, "登陆成功");
					Intent intent = new Intent(Login.this, MainActivity.class);
					intent.putExtra("user", userList.get(0));
					startActivity(intent);
					finish();
				}
				else {
					ToastUtils.toast(Login.this, " 登陆失败");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_register_btn:
			Intent intent = new Intent(Login.this, Register.class);
			startActivity(intent);
			break;
		case R.id.id_login_btn:
			login();
			break;
		}
	}

}
