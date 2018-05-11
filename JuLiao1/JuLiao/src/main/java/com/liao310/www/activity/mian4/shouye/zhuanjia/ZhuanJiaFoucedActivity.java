package com.liao310.www.activity.mian4.shouye.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.adapter.MyFragmentPagerAdapter;
import com.liao310.www.base.BaseActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuanJiaFoucedActivity extends BaseActivity{
	private ZhuanJiaFoucedActivity _this;
	private ImageView back;
	private TextView title;

	private TextView wenzhang,zhuanjia,ivBottomLine;
	private ViewPager mPager;//对应的fragment
	private int currIndex = 0;
	private int position_one;
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
	private WenZhangFragment wzFragment;
	private ZhuanJiaFrament zjFragment;
	Bundle savedInstanceState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		setContentView(R.layout.zhuanjiafouced);
		_this = this;
		initView();
		initdata();
	}
	private void initView() {
		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_this.finish();
			}
		});
		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("关注专家");
	}
	private void initdata() {
		wenzhang = (TextView) findViewById(R.id.wenzhang);
		zhuanjia = (TextView) findViewById(R.id.zhuanjia);

		wenzhang.setOnClickListener(new MyOnClickListener(0));
		zhuanjia.setOnClickListener(new MyOnClickListener(1));
		InitWidth();
		mPager = (ViewPager) findViewById(R.id.pager);
		setFragment();//设置订单数据
	}
	private void InitWidth() {
		ivBottomLine = (TextView)findViewById(R.id.jingxuan_tab_line);
		DisplayMetrics dm = new DisplayMetrics();
		_this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;//屏幕宽度
		position_one = (int) (screenW /2.0);
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;
		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};
	private void setFragment(){
		if(savedInstanceState != null){
			wzFragment = (WenZhangFragment) getSupportFragmentManager().getFragment(
					savedInstanceState, "wzFragment");
			if(wzFragment == null){
				wzFragment = new WenZhangFragment();
			}
			
			zjFragment = (ZhuanJiaFrament) getSupportFragmentManager().getFragment(
					savedInstanceState, "zjFragment");
			if(zjFragment == null){
				zjFragment = new ZhuanJiaFrament();
			}

		}else{
			wzFragment = new WenZhangFragment();
			zjFragment = new ZhuanJiaFrament();
		}

		fragments.add(wzFragment);
		fragments.add(zjFragment);

		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			setBase(arg0);
		}	
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	public void setBase(int arg0){
		Animation animation = null;
		switch (arg0) {
		case 0:
			if (currIndex == 1) {
				animation = new TranslateAnimation(position_one, 0, 0, 0);
				zhuanjia.setTextColor(_this.getResources().getColor(R.color.textlightgrey));
			}   
			wenzhang.setTextColor(_this.getResources().getColor(R.color.textgreen));
			break;
		case 1:
			if (currIndex == 0) {
				animation = new TranslateAnimation(0, position_one, 0, 0);
				wenzhang.setTextColor(_this.getResources().getColor(R.color.textlightgrey));
			}
			zhuanjia.setTextColor(_this.getResources().getColor(R.color.textgreen));
			break;
		}

		currIndex = arg0;
		if(animation!=null){
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);
		}
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if (wzFragment != null
				&&wzFragment.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "wzFragment", wzFragment);
		}
		
		if (zjFragment != null
				&&zjFragment.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "zjFragment", zjFragment);
		}
	}
}
