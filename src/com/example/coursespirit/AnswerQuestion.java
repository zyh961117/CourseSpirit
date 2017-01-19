package com.example.coursespirit;

import java.io.Serializable;

import com.example.coursespirit.db.Answer;
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

public class AnswerQuestion extends Activity implements OnClickListener {
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private Button answerQuestionBtn;
	private EditText answerContentEt;
	private UserBean user;
	private Course course;
	private Question question;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_question);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		extra = intent.getSerializableExtra("course"); 
		course = (Course) extra;
		extra = intent.getSerializableExtra("question"); 
		question = (Question) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
	}
	
	private void initView() {
		answerQuestionBtn = (Button) this.findViewById(R.id.id_answer_question_btn);
		answerContentEt = (EditText) this.findViewById(R.id.id_answer_content_et);
		answerQuestionBtn.setOnClickListener(this);
	}
	private void create() {
		String answerContent = answerContentEt.getText().toString();
		if (answerContent.isEmpty()) {
			ToastUtils.toast(this, "内容不能为空!");
			return;
		}

		Answer answer = new Answer();
		answer.setAnswerContent(answerContent);
		answer.setAnswerAnswerer(user.getUserName());
		answer.setQuestionId(question.getObjectId());
		answer.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				ToastUtils.toast(AnswerQuestion.this, "发表成功");
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ToastUtils.toast(AnswerQuestion.this, arg0 + "," + arg1 + "发表失败");
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_answer_question_btn:
			create();
			break;
		}
	}
}
