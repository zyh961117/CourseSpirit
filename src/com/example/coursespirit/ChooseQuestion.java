package com.example.coursespirit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.coursespirit.adapter.CourseAdapter;
import com.example.coursespirit.adapter.QuestionAdapter;
import com.example.coursespirit.db.Course;
import com.example.coursespirit.db.Question;
import com.example.coursespirit.db.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ChooseQuestion extends Activity implements OnClickListener {
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private TextView titleText;
	private UserBean user;
	private Course course;
	private Button addBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_question);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		extra = intent.getSerializableExtra("course"); 
		course = (Course) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
		showCourse();
		showQuestion();
	}
	
	private void initView() {
		addBtn = (Button) this.findViewById(R.id.id_add_question_btn);
		addBtn.setOnClickListener(this);
	}
	
	private void showCourse() {
		TextView courseName = (TextView) findViewById(R.id.title_course);
		courseName.setText(course.getCourseName());
	}
	
	private void showQuestion() {
		BmobQuery<Question> query = new BmobQuery<Question>();
        //按照时间降序
        query.order("-createdAt");
        //查询该课程下的问题
        query.addWhereEqualTo("courseId", course.getObjectId()); 
        query.setLimit(50);
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<Question>() {
        	ArrayList<Question> questionList=new ArrayList<Question>();
        	
            @Override
            public void onSuccess(List<Question> objectId) {
                //将结果显示在列表中
            	 for (Question question : objectId) {
            		 questionList.add(question);
            	 }
            	 QuestionAdapter adapter = new QuestionAdapter(ChooseQuestion.this, R.layout.question_item, questionList);
    			 ListView listView = (ListView) findViewById(R.id.list_question);
    			 listView.setAdapter(adapter);
    			 listView.setOnItemClickListener(new OnItemClickListener() {
					 @Override
					 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						 Question question = questionList.get(position);
						 Intent intent = new Intent(ChooseQuestion.this, ChooseAnswer.class);
						 intent.putExtra("user", user);
						 intent.putExtra("course",course);
						 intent.putExtra("question", question);
						 startActivity(intent);
					 }
    			});
            }

            @Override
            public void onError(int code, String arg0) {
            	Toast.makeText(ChooseQuestion.this, "暂无问题", Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_add_question_btn:
			Intent intent = new Intent(ChooseQuestion.this, AskQuestion.class);
			intent.putExtra("user", user);
			intent.putExtra("course",course);
			startActivity(intent);
			break;
		}
	}
	
}
