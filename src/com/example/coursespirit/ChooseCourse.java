package com.example.coursespirit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.coursespirit.adapter.CourseAdapter;
import com.example.coursespirit.db.Course;
import com.example.coursespirit.db.UserBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ChooseCourse extends Activity implements OnClickListener {
	
	private static final String BMOB_APPLICATION_ID = "08a83f2371f73387e6ff9ee27097c9ec";
	private UserBean user;
	private Button addBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_course);
		Intent intent = getIntent();
		Serializable extra = intent.getSerializableExtra("user"); 
		user = (UserBean) extra;
		Bmob.initialize(this, BMOB_APPLICATION_ID);
		initView();
		showCourse();
	}
	
	private void initView() {
		addBtn = (Button) this.findViewById(R.id.id_add_course_btn);
		addBtn.setOnClickListener(this);
	}

	
	private void showCourse() {
		BmobQuery<Course> query = new BmobQuery<Course>();
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(this, new FindListener<Course>() {
        	ArrayList<Course> courseList=new ArrayList<Course>();
        	
            @Override
            public void onSuccess(List<Course> objectId) {
                //将结果显示在列表中
            	 for (Course course : objectId) {
            		 courseList.add(course);
            	 }
            	 CourseAdapter adapter = new CourseAdapter(ChooseCourse.this, R.layout.course_item, courseList);
    			 ListView listView = (ListView) findViewById(R.id.list_course);
    			 listView.setAdapter(adapter);
    			 listView.setOnItemClickListener(new OnItemClickListener() {
					 @Override
					 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						 Course course = courseList.get(position);
						 Intent intent = new Intent(ChooseCourse.this, ChooseQuestion.class);
						 intent.putExtra("user", user);
						 intent.putExtra("course",course);
						 startActivity(intent);
					 }
    			});
            }

            @Override
            public void onError(int code, String arg0) {
            	Toast.makeText(ChooseCourse.this, "暂无课程", Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_add_course_btn:
			Intent intent = new Intent(ChooseCourse.this, CreateCourse.class);
			intent.putExtra("user", user);
			startActivity(intent);
			break;
		}
	}
}
