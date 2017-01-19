package com.example.coursespirit.db;

import cn.bmob.v3.BmobObject;

public class Answer extends BmobObject{
	private String answerContent;
	private String answerAnswerer;
	private String questionId;
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public String getAnswerAnswerer() {
		return answerAnswerer;
	}
	public void setAnswerAnswerer(String answerAnswerer) {
		this.answerAnswerer = answerAnswerer;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}