package com.example.coursespirit;

import java.io.Serializable;

import com.example.coursespirit.db.Course;
import com.example.coursespirit.db.Question;
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
import cn.bmob.v3.listener.SaveListener;

public class AskQuestion extends Activity implements OnClickListener {
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private Button askQuestionBtn;
	private EditText questionTitleEt, questionContentEt;
	private UserBean user;
	private Course course;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ask_question);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		extra = intent.getSerializableExtra("course"); 
		course = (Course) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
	}
	
	private void initView() {
		askQuestionBtn = (Button) this.findViewById(R.id.id_ask_question_btn);
		questionTitleEt = (EditText) this.findViewById(R.id.id_question_title_et);
		questionContentEt = (EditText) this.findViewById(R.id.id_question_content_et);
		askQuestionBtn.setOnClickListener(this);
	}
	private void create() {
		String questionTitle = questionTitleEt.getText().toString();
		String questionContent = questionContentEt.getText().toString();
		if (questionTitle.isEmpty() || questionContent.isEmpty()) {
			ToastUtils.toast(this, "标题或内容不能为空!");
			return;
		}
		ToastUtils.toast(this, user.getUserName());
		Question question = new Question();
		question.setQuestionTitle(questionTitle);
		question.setQuestionContent(questionContent);
		question.setQuestionAsker(user.getUserName());
		question.setCourseId(course.getObjectId());
		question.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				ToastUtils.toast(AskQuestion.this, "提问成功");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ToastUtils.toast(AskQuestion.this, arg0 + "," + arg1 + "提问失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_ask_question_btn:
			create();
			break;
		}
	}
}
