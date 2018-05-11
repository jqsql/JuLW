package com.liao310.www;

import com.liao310.www.activity.mian4.match.MatchMainNewFragment;
import com.liao310.www.activity.mian4.personcenter.MainPersonalCenterFragment;
import com.liao310.www.activity.mian4.shouye.MainHomeFragment;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaMainFragment;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaMainNewFragment;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.utils.StatusBarColor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements OnClickListener{
	private View shouye,zhuanjia,bisai,wode;
	private ImageView shouye_im,zhuanjia_im,bisai_im,wode_im;
	private TextView shouye_tv,zhuanjia_tv,bisai_tv,wode_tv;

	private MainHomeFragment mTab01;
	private ZhuanJiaMainNewFragment mTab02;
	private MatchMainNewFragment mTab03;
	private MainPersonalCenterFragment mTab04;

	TextView share,message;
	EditText messageEd;
	private long firstTime = 0;
	FragmentManager fm;
	Bundle savedInstanceState;
	TextView notice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		fm = getSupportFragmentManager();
		setContentView(R.layout.activity_main);
		initView();
		resetImgs(1);
	}
	private void initView() {

		notice = (TextView) findViewById(R.id.notice);
		notice.setOnClickListener(this);
		
		shouye = findViewById(R.id.shouye);
		shouye.setOnClickListener(this);
		zhuanjia = findViewById(R.id.zhuanjia);
		zhuanjia.setOnClickListener(this);
		bisai = findViewById(R.id.bisai);
		bisai.setOnClickListener(this);
		wode = findViewById(R.id.wode);
		wode.setOnClickListener(this);

		shouye_im = (ImageView) findViewById(R.id.shouye_im);
		zhuanjia_im = (ImageView) findViewById(R.id.zhuanjia_im);
		bisai_im = (ImageView) findViewById(R.id.bisai_im);
		wode_im = (ImageView) findViewById(R.id.wode_im);

		shouye_tv= (TextView) findViewById(R.id.shouye_tv);
		zhuanjia_tv = (TextView) findViewById(R.id.zhuanjia_tv);
		bisai_tv = (TextView) findViewById(R.id.bisai_tv);
		wode_tv = (TextView) findViewById(R.id.wode_tv);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.notice:
			String rid = JPushInterface.getRegistrationID(getApplicationContext());
			if (!rid.isEmpty()) {
				notice.setText("RegistrationID:" + rid);
			} else {
				Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.shouye:
			resetImgs(1);
			break;
		case R.id.zhuanjia:
			resetImgs(2);
			break;
		case R.id.bisai:
			resetImgs(3);
			break;
		case R.id.wode:
			resetImgs(4);
			break;
		}
	}

	protected void resetImgs(int rank) {
		shouye_im.setImageResource(R.drawable.shouye02);
		zhuanjia_im.setImageResource(R.drawable.zhuanjia02);
		bisai_im.setImageResource(R.drawable.leitai02);
		wode_im.setImageResource(R.drawable.wode02);
		shouye_tv.setTextColor(getResources().getColor(R.color.white));
		zhuanjia_tv.setTextColor(getResources().getColor(R.color.white));
		bisai_tv.setTextColor(getResources().getColor(R.color.white));
		wode_tv.setTextColor(getResources().getColor(R.color.white));
		switch (rank) {	
		case 1:
			shouye_im.setImageResource(R.drawable.shouye01);
			shouye_tv.setTextColor(getResources().getColor(R.color.textgreen));
			break;
		case 2:
			zhuanjia_im.setImageResource(R.drawable.zhuanjia01);
			zhuanjia_tv.setTextColor(getResources().getColor(R.color.textgreen));
			break;
		case 3:
			bisai_im.setImageResource(R.drawable.leitai01);
			bisai_tv.setTextColor(getResources().getColor(R.color.textgreen));
			break;
		case 4:
			wode_im.setImageResource(R.drawable.wode01);
			wode_tv.setTextColor(getResources().getColor(R.color.textgreen));
			break;
		}
		setSelect(rank);
	}
	protected void setSelect(int i) {
		FragmentTransaction	transaction = fm.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 1:
			StatusBarColor.setStatusBar(this);//修改状态栏颜色
			if (mTab01 == null) {
				if(savedInstanceState != null){
					mTab01 = (MainHomeFragment) fm.findFragmentByTag("MainHomeFragment");  
					if (mTab01 == null) {
						mTab01 = new MainHomeFragment();
						transaction.add(R.id.id_content, mTab01,"MainHomeFragment");											
					}
				}else{
					mTab01 = new MainHomeFragment();
					transaction.add(R.id.id_content, mTab01,"MainHomeFragment");
				}
			} else {
				transaction.show(mTab01);
			}

			break;
		case 2:
			StatusBarColor.setStatusBarColor(this, R.color.APPColor);//修改状态栏颜色
			if (mTab02 == null) {
				if(savedInstanceState != null){
					mTab02 = (ZhuanJiaMainNewFragment) fm.findFragmentByTag("ZhuanJiaMainNewFragment");
					if (mTab02 == null) {
						mTab02 = new ZhuanJiaMainNewFragment();
						transaction.add(R.id.id_content, mTab02,"ZhuanJiaMainFragment");
					}
				}else{
					mTab02 = new ZhuanJiaMainNewFragment();
					transaction.add(R.id.id_content, mTab02,"ZhuanJiaMainNewFragment");
				}
			} else {
				transaction.show(mTab02);
			}
			break;
		case 3:
			StatusBarColor.setStatusBarColor(this, R.color.APPColor);//修改状态栏颜色
			if (mTab03 == null) {
				if(savedInstanceState != null){
					mTab03 = (MatchMainNewFragment) fm.findFragmentByTag("MatchMainNewFragment");
					if (mTab03 == null) {
						mTab03 = new MatchMainNewFragment();
						transaction.add(R.id.id_content, mTab03,"MatchMainNewFragment");
					}
				}else{
					mTab03 = new MatchMainNewFragment();
					transaction.add(R.id.id_content, mTab03,"MatchMainNewFragment");
				}
			} else {
				transaction.show(mTab03);
			}
			break;
		case 4:
			StatusBarColor.setStatusBarColor(this, R.color.APPColor);//修改状态栏颜色
			if (mTab04 == null) {
				if(savedInstanceState != null){
					mTab04 = (MainPersonalCenterFragment) fm.findFragmentByTag("MainPersonalCenterFragment");  
					if (mTab04 == null) {
						mTab04 = new MainPersonalCenterFragment();
						transaction.add(R.id.id_content, mTab04,"MainPersonalCenterFragment");											
					}
				}else{
					mTab04 = new MainPersonalCenterFragment();
					transaction.add(R.id.id_content, mTab04,"MainPersonalCenterFragment");
				}
			} else {
				transaction.show(mTab04);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}
	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (mTab01 != null) {
			transaction.hide(mTab01);
		}else{
			if(savedInstanceState != null){
				mTab01 = (MainHomeFragment) fm.findFragmentByTag("MainHomeFragment");  
				if (mTab01 != null) {
					transaction.hide(mTab01);											
				}
			}
		}
		if (mTab02 != null) {
			transaction.hide(mTab02);
		}else{
			if(savedInstanceState != null){
				mTab02 = (ZhuanJiaMainNewFragment) fm.findFragmentByTag("ZhuanJiaMainNewFragment");
				if (mTab02 != null) {					
					transaction.hide(mTab02);											
				}
			}
		}
		if (mTab03 != null) {
			transaction.hide(mTab03);
		}else{
			if(savedInstanceState != null){
				mTab03 = (MatchMainNewFragment) fm.findFragmentByTag("MatchMainNewFragment");
				if (mTab03 != null) {
					transaction.hide(mTab03);											
				}
			}
		}
		if (mTab04 != null) {
			transaction.hide(mTab04);
		}else{
			if(savedInstanceState != null){
				mTab04 = (MainPersonalCenterFragment) fm.findFragmentByTag("MainPersonalCenterFragment");  
				if (mTab04 != null) {
					transaction.hide(mTab04);											
				}
			}
		}
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO 自动生成的方法存根
		super.onNewIntent(intent);
		int nowPage = intent.getIntExtra("nowPage",1);
		resetImgs(nowPage);

	}

	// 返回键退出
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else { // 两次按键小于2秒时，退出应用
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid);
				System.exit(0);
			}
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if (mTab01 != null
				&&mTab01.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "MainHomeFragment", mTab01);
		}
		if (mTab02 != null
				&&mTab02.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "ZhuanJiaMainFragment", mTab02);
		}

		if (mTab03 != null
				&&mTab03.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "MatchMainFragment", mTab03);
		}
		if (mTab04 != null
				&&mTab04.isVisible()) {
			getSupportFragmentManager().putFragment(savedInstanceState, "MainPersonalCenterFragment", mTab04);
		}
	}
}
