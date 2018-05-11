package com.liao310.www.activity.login;

import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.version.Back;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PostHttpInfoUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.utils.Util;
import com.liao310.www.widget.MyCountTime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

/**
 * A login screen that offers login via username/password and via Google+ sign
 * in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************ In order for Google+ sign in
 * to work with your app, you must first go to:
 * https://developers.google.com/+/mobile
 * /android/getting-started#step_1_enable_the_google_api and follow the steps in
 * "Step 1" to create an OAuth 2.0 client for your package.
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private ImageView back;
	private TextView title;
	private EditText telephone_tx;
	private EditText password_tx;
	private EditText yanzhengma_tx;
	private EditText invite_tx;
	private MyCountTime mMyCountTime;
	private TextView verif;
	private TextView register;
	private RegisterActivity _this;

	private View mProgressView;
	private boolean isTasking = false;
	private boolean isGetting = false;
	private String codeTelephone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		_this = this;

		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(this);

		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("注    册");

		telephone_tx = (EditText) findViewById(R.id.telephone_tx);
		password_tx = (EditText) findViewById(R.id.password_tx);
		yanzhengma_tx = (EditText) findViewById(R.id.yanzhengma_tx);
		invite_tx = (EditText) findViewById(R.id.invite_tx);

		verif = (TextView) findViewById(R.id.btn_verif);
		verif.setOnClickListener(this);
		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(this);

		mProgressView = findViewById(R.id.login_progress);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
			.alpha(show ? 1 : 0)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mProgressView.setVisibility(show ? View.VISIBLE
							: View.GONE);
				}
			});
		} else {
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			_this.finish();
			break;
		case R.id.btn_verif:
			if(isGetting) {
				return;
			}
			verif.setEnabled(false);
			final String mobile = telephone_tx.getText().toString();

			if (TextUtils.isEmpty(mobile)) {
				ToastUtils.showShort(_this, "手机号码不能为空");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			} else if (!Util.isMobileValid(mobile)) {
				ToastUtils.showShort(_this, "手机号格式不正确");
				telephone_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}
			codeTelephone = mobile;
			isGetting = true;
			ServiceLogin.getInstance().getYanZhengMa(_this,codeTelephone,1,
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
			break;
		case R.id.register:
			if (isTasking) {
				return;
			}
			// Store values at the time of the login attempt.
			final String mobile1 = telephone_tx.getText().toString();
			final String password = password_tx.getText().toString();
			final String code = yanzhengma_tx.getText().toString();
			final String invite = invite_tx.getText().toString();

			if (TextUtils.isEmpty(mobile1)) {
				ToastUtils.showShort(_this, "手机号码不能为空");
				telephone_tx.requestFocus();
				return;
			}
			if (!Util.isMobileValid(mobile1)) {
				ToastUtils.showShort(_this, "手机号格式不正确");
				telephone_tx.requestFocus();
				return;
			}
			if (TextUtils.isEmpty(password)) {
				ToastUtils.showShort(_this, "密码不能为空");
				password_tx.requestFocus();
				return;
			}
			if ((!TextUtils.isEmpty(password)) && Util.textNameTemp1(password)) {
				ToastUtils.showShort(_this, "密码不能全为数字");
				password_tx.requestFocus();
				return;
			}
			if ((!TextUtils.isEmpty(password)) && Util.isAllEnglish(password)) {
				ToastUtils.showShort(_this, "密码不能全为字母");
				password_tx.requestFocus();
				return;
			}
			if (!Util.isPasswordValid_OnlyNumAndLeter(password)||password.length()<6 || password.length()>15) {
				ToastUtils.showShort(_this, "密码为6-15位字母和数字组成的密码");
				password_tx.requestFocus();
				verif.setEnabled(true);
				return;
			}
			if (TextUtils.isEmpty(code)) {
				ToastUtils.showShort(_this, "验证码不能为空");
				yanzhengma_tx.requestFocus();
				return;
			}
			showProgress(true);
			isTasking = true;

			ServiceLogin.getInstance().postRegister(_this,mobile1,code,password,getIMEI(),invite,
					new CallBack<RegisterBack>() {
				@Override
				public void onSuccess(RegisterBack back) {
					ToastUtils.showShort(_this, "注册成功！");
					PreferenceUtil.putBoolean(_this, "hasLogin", true);
					showProgress(false);
					isTasking = false;
					Intent intent = new Intent(_this,MainActivity.class);
					EventBus.getDefault().post("updateUserInfo");
					if(back.getData().getNew_sign() == 1) {
						EventBus.getDefault().post("showRegisterPacket");
					}else {
						EventBus.getDefault().post("showRegisterPacketNo");
					}
					
					intent.putExtra("nowPage", 4);// 标记
					startActivity(intent);
					finish();
				}

				@Override
				public void onFailure(ErrorMsg errorMsg) {
					PostHttpInfoUtils.doPostFail(_this, errorMsg,"注册失败");
					showProgress(false);
					isTasking = false;
				}
			});

		default:
			break;
		}
	}
}