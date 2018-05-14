package com.liao310.www.activity.mian4.personcenter;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.utils.ToastUtils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class KeFuActivity extends BaseActivity {
	private ImageView back;
	private TextView title;
	private View qq, wx, phone, hezuo, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kefu);
		initView();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				KeFuActivity.this.finish();
			}
		});
		title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("专属客服");

		qq = findViewById(R.id.qq);
		wx = findViewById(R.id.wx);
		phone = findViewById(R.id.phone);
		hezuo = findViewById(R.id.hezuo);
		email = findViewById(R.id.email);
		qq.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkApkExist(KeFuActivity.this, "com.tencent.mobileqq")) {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + 2058048532 + "&version=1")));
				} else {
					ToastUtils.showShort(KeFuActivity.this, "本机未安装QQ应用");
				}
			}
		});
		wx.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("weixin://"));
				startActivity(intent);
			}
		});
		phone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				Uri data = Uri.parse("tel:0512-62716848");
				intent.setData(data);
				if (ActivityCompat.checkSelfPermission(KeFuActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					return;
				}
				startActivity(intent);
			}
		});
		hezuo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				Uri data = Uri.parse("tel:18962196074");
				intent.setData(data);
				if (ActivityCompat.checkSelfPermission(KeFuActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					return;
				}
				startActivity(intent);
			}
		});
		email.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse ("mailto: vip@310liao.cn");   
				Intent intent = new Intent (Intent.ACTION_SENDTO, uri);    
				startActivity(intent);  
			}
		});
	}	
}
