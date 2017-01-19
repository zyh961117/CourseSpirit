package com.example.coursespirit;

import java.io.Serializable;
import java.util.List;

import com.example.coursespirit.db.Course;
import com.example.coursespirit.db.UserBean;
import com.example.coursespirit.util.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateCourse extends Activity implements OnClickListener {
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private Button createCourseBtn;
	private EditText courseNameEt;
	private UserBean user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_course);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
	}
	
	/**
	 * 
	 * 初始化控件
	 */
	private void initView() {
		createCourseBtn = (Button) this.findViewById(R.id.id_create_course_btn);
		courseNameEt = (EditText) this.findViewById(R.id.id_course_name_et);
		createCourseBtn.setOnClickListener(this);
	}

	/**
	 * 创建课程
	 */
	private void create() {
		String courseName = courseNameEt.getText().toString();
		if (courseName.isEmpty()) {
			ToastUtils.toast(this, "课程名不能为空!");
			return;
		}

		Course course = new Course();
		course.setCourseName(courseName);
		course.setTeacherId(user.getUserName());
		/**
		 * 保存数据到Bmob服务器
		 */
		course.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				ToastUtils.toast(CreateCourse.this, "创建成功");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ToastUtils.toast(CreateCourse.this, arg0 + "," + arg1 + "创建失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_create_course_btn:
			create();
			break;
		}
	}
}
