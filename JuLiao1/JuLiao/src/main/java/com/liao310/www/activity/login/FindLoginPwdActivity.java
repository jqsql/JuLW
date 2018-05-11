package com.liao310.www.activity.login;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.version.Back;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PostHttpInfoUtils;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.utils.Util;
import com.liao310.www.widget.MyCountTime;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FindLoginPwdActivity extends BaseActivity implements
OnClickListener {

	private FindLoginPwdActivity _this;
	private ImageView back;
	private TextView title;

	private EditText telephone_tx;
	private EditText yanzheng_tx;
	private EditText passwordnew_tx;
	private TextView verif;
	private MyCountTime mMyCountTime;
	private TextView sign;

	private String codeTelephone;
	private boolean isTasking = false;
	private boolean isGetting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_pwd);

		_this = this;

		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_this.finish();
			}
		});
		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("找回密码");

		telephone_tx = (EditText) findViewById(R.id.telephone_tx);
		yanzheng_tx = (EditText) findViewById(R.id.yanzhengma_tx);
		passwordnew_tx = (EditText) findViewById(R.id.passwordnew_tx);

		verif = (TextView) findViewById(R.id.btn_verif);
		verif.setOnClickListener(this);
		sign = (TextView) findViewById(R.id.sign);
		sign.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == verif) {
			if(isGetting) {
				return;
			}
			String mobilebase = telephone_tx.getText().toString().trim();
			verif.setEnabled(false);
			if (TextUtils.isEmpty(mobilebase)) {
				ToastUtils.showShort(_this, "手机号码不能为空");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}

			if (!Util.isMobileValid(mobilebase)) {
				ToastUtils.showShort(_this, "手机号格式不正确");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}
			codeTelephone = mobilebase;
			isGetting = true;
			ServiceLogin.getInstance().getYanZhengMa(_this,codeTelephone,2,
					new CallBack<Back>() {
				@Override
				public void onSuccess(Back back) {
					mMyCountTime = new MyCountTime(_this, 90000,
							1000, verif, "重新获取", "", null, false);
					mMyCountTime.start();
					isGetting = false;
					verif.setEnabled(true);
				}

				@Override
				public void onFailure(ErrorMsg errorMsg) {
					PostHttpInfoUtils.doPostFail(_this, errorMsg,"获取失败");
					isGetting = false;
					verif.setEnabled(true);
				}
			});

		}
		if (v == sign) {
			String telephone = telephone_tx.getText().toString().trim();
			String code = yanzheng_tx.getText().toString().trim();
			String newPassword = passwordnew_tx.getText().toString().trim();
			if (TextUtils.isEmpty(telephone)) {
				ToastUtils.showShort(_this, "手机号码不能为空");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}
			if (TextUtils.isEmpty(code)) {
				ToastUtils.showShort(_this, "验证码不能为空");
				yanzheng_tx.requestFocus();
				return;
			}
			if (TextUtils.isEmpty(newPassword)) {
				ToastUtils.showShort(_this, "新密码为空");
				passwordnew_tx.requestFocus();
				return;
			}
			if ((!TextUtils.isEmpty(newPassword))
					&& Util.textNameTemp1(newPassword)) {
				ToastUtils.showShort(_this, "密码不能全为数字");
				passwordnew_tx.requestFocus();
				return;
			}
			if ((!TextUtils.isEmpty(newPassword))
					&& Util.isAllEnglish(newPassword)) {
				ToastUtils.showShort(_this, "密码不能全为字母");
				passwordnew_tx.requestFocus();
				return;
			}
			if (!Util.isPasswordValid_OnlyNumAndLeter(newPassword)||newPassword.length()<6 || newPassword.length()>15) {
				ToastUtils.showShort(_this, "密码为6-15位字母和数字组成的密码");
				passwordnew_tx.requestFocus();
				return;
			}
			if (!telephone.equals(codeTelephone)) {
				ToastUtils.showShort(_this, "手机号与获取验证码时输入的手机号不一致");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}
			if(isTasking) {
				return;
			}
			isTasking = true;
			ServiceLogin.getInstance().postPassword(_this, telephone,
					code,newPassword,
					new CallBack<Back>() {
				@Override
				public void onSuccess(Back s) {
					ToastUtils.showShort(_this, "成功找回密码");
					isTasking = false;
					finish();
				}

				@Override
				public void onFailure(ErrorMsg errorMsg) {
					PostHttpInfoUtils.doPostFail(_this, errorMsg,"失败");
					isTasking = false;
					verif.setEnabled(true);

				}
			});
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something...
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
