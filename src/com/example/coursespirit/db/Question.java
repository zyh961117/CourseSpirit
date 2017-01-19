package com.example.coursespirit.db;

import cn.bmob.v3.BmobObject;

public class Question extends BmobObject{
	private static final long serialVersionUID = 1L;
	private String questionTitle;
	private String questionContent;
	private String questionAsker;
	private String courseId;
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getQuestionAsker() {
		return questionAsker;
	}
	public void setQuestionAsker(String questionAsker) {
		this.questionAsker = questionAsker;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Override
	public String toString() {
		return questionTitle + "    from:" + questionAsker;
	}
}