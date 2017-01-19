package com.example.coursespirit.adapter;

import java.util.List;

import com.example.coursespirit.R;
import com.example.coursespirit.db.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CourseAdapter extends ArrayAdapter<Course> {
	private int resourceId;
	public CourseAdapter(Context context, int textViewResourceId, List<Course> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Course course = getItem(position); // 获取当前项的Fruit实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView courseName = (TextView) view.findViewById(R.id.course_name);
		TextView teacherName = (TextView) view.findViewById(R.id.teacher_name);
		courseName.setText(course.getCourseName());
		teacherName.setText("老师：" + course.getTeacherId());
		return view;
	}
}