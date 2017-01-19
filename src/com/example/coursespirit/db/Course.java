package com.example.coursespirit.db;

import cn.bmob.v3.BmobObject;

public class Course extends BmobObject{
	
	private static final long serialVersionUID = 1L;
	private String courseName;
	private String teacherId;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
}
