package com.liao310.www.activity.mian4.personcenter.account;

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

public class AccountDetailActivity  extends BaseActivity{
	private AccountDetailActivity _this;
	private ImageView back;
	private TextView title;

	private TextView xiaofei,chongzhi,ivBottomLine;
	private ViewPager mPager;//对应的fragment
	private int currIndex = 0;
	private int position_one;
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
	private AccountFragment xfFragment;
	private AccountFragment czFragment;
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
		title.setText("账户明细");
	}
	private void initdata() {
		xiaofei = (TextView) findViewById(R.id.wenzhang);
		xiaofei.setText("消费");
		chongzhi = (TextView) findViewById(R.id.zhuanjia);
		chongzhi.setText("充值");

		xiaofei.setOnClickListener(new MyOnClickListener(0));
		chongzhi.setOnClickListener(new MyOnClickListener(1));
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
	}
	private void setFragment(){
		if(savedInstanceState != null){
			xfFragment = (AccountFragment) getSupportFragmentManager().getFragment(
					savedInstanceState, "xfFragment");
			if(xfFragment == null){
				xfFragment = new AccountFragment();
			}
			
			czFragment = (AccountFragment) getSupportFragmentManager().getFragment(
					savedInstanceState, "czFragment");
			if(czFragment == null){
				czFragment = new AccountFragment();
			}

		}else{
			xfFragment = new AccountFragment();
			czFragment = new AccountFragment();
		}

		xfFragment.from = "costmore";
		czFragment.from = "recharge";
		fragments.add(xfFragment);
		fragments.add(czFragment);

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
				chongzhi.setTextColor(_this.getResources().getColor(R.color.white));
			}   
			xiaofei.setTextColor(_this.getResources().getColor(R.color.textgreen));
			break;
		case 1:
			if (currIndex == 0) {
				animation = new TranslateAnimation(0, position_one, 0, 0);
				xiaofei.setTextColor(_this.getResources().getColor(R.color.white));
			}
			chongzhi.setTextColor(_this.getResources().getColor(R.color.textgreen));
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
		if (xfFragment != null
				&&xfFragment.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "xfFragment", xfFragment);
		}
		
		if (czFragment != null
				&&czFragment.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "czFragment", czFragment);
		}
	}
}