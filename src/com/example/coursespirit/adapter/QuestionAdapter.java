package com.example.coursespirit.adapter;

import java.util.List;

import com.example.coursespirit.R;
import com.example.coursespirit.db.Question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class QuestionAdapter extends ArrayAdapter<Question> {
	private int resourceId;
	public QuestionAdapter(Context context, int textViewResourceId, List<Question> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Question question = getItem(position); // 获取当前项的Fruit实例
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView questionTitle = (TextView) view.findViewById(R.id.question_title);
		TextView questionContent = (TextView) view.findViewById(R.id.question_content);
		TextView questionAsker = (TextView) view.findViewById(R.id.question_asker);
		questionTitle.setText(question.getQuestionTitle());
		questionContent.setText(question.getQuestionContent());
		questionAsker.setText("作者：" + question.getQuestionAsker());
		return view;
	}
}