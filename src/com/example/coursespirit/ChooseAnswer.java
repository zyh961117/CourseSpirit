package com.example.coursespirit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.coursespirit.adapter.AnswerAdapter;
import com.example.coursespirit.adapter.CourseAdapter;
import com.example.coursespirit.adapter.QuestionAdapter;
import com.example.coursespirit.db.Answer;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ChooseAnswer extends Activity{
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private UserBean user;
	private Course course;
	private Question question;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_answer);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		extra = intent.getSerializableExtra("course"); 
		course = (Course) extra;
		extra = intent.getSerializableExtra("question"); 
		question = (Question) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		showAnswer();
	}
	
	private void showAnswer() {
		BmobQuery<Answer> query = new BmobQuery<Answer>();
        //按照时间降序
        query.order("-createdAt");
        query.addWhereEqualTo("questionId", question.getObjectId()); 
        query.setLimit(50);
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<Answer>() {
        	ArrayList<Answer> answerList=new ArrayList<Answer>();
        	
            @Override
            public void onSuccess(List<Answer> objectId) {
                //将结果显示在列表中
            	 for (Answer answer : objectId) {
            		 answerList.add(answer);
            	 }
            	 AnswerAdapter adapter = new AnswerAdapter(ChooseAnswer.this, R.layout.answer_item, answerList);
    			 ListView listView = (ListView) findViewById(R.id.list_answer);
    			 listView.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String arg0) {
            	Toast.makeText(ChooseAnswer.this, "暂无回答", Toast.LENGTH_SHORT).show();
            }
        });
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.answer, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.answer_question:
			Intent intent = new Intent(ChooseAnswer.this, AnswerQuestion.class);
			intent.putExtra("user", user);
			intent.putExtra("course",course);
			intent.putExtra("question", question);
			startActivity(intent);
			break;
		case R.id.refresh:
			Toast.makeText(this, "已刷新", Toast.LENGTH_SHORT).show();
			showAnswer();
			break;
		default:
		}
	return true;
	}
}
