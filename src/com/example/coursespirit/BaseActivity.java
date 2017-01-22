package com.example.coursespirit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BaseActivity extends FragmentActivity{

	private FragmentTabHost mTabHost;
	private RadioGroup m_radioGroup;
	String tabs[] = {"Tab1","Tab2","Tab3"};
	Class cls[] = {Tab1.class,Tab2.class,Tab3.class};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment_tabs);

		init();
	}

	private void init()
	{
		mTabHost = (FragmentTabHost)this.findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setVisibility(View.GONE);
		for(int i=0;i<tabs.length;i++){
//			View tabView = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
			mTabHost.addTab(mTabHost.newTabSpec(tabs[i]).setIndicator(tabs[i]),cls[i], null);
		}
		m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		m_radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.RadioButton0:

					mTabHost.setCurrentTabByTag(tabs[0]);
					break;
				case R.id.RadioButton1:
					mTabHost.setCurrentTabByTag(tabs[1]);
					break;
				case R.id.RadioButton2:
					mTabHost.setCurrentTabByTag(tabs[2]);
					break;
				}
			}
		});

		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
	}


}
