package com.liao310.www.activity.mian4.personcenter.setting;



import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity 
{
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		title= (TextView) findViewById(R.id.tv_head_title);
		title.setText("关于我们");
		findViewById(R.id.iv_back).setOnClickListener(new  View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutUsActivity.this.finish();
			}
		});
	}
}
