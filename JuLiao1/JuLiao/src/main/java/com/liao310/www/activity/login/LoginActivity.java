package com.liao310.www.activity.login;

import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PostHttpInfoUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {
	private ImageView back;
	private TextView title;
	private TextView findpassword,register;
	private EditText telephone_tx;
	private EditText password_tx;
	private TextView sign;
	private LoginActivity _this;
	private boolean isTasking = false;

	private String requestName;
	private int from;

	private View mProgressView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		_this = this;
		requestName=getIntent().getStringExtra("requestName");
		from=getIntent().getIntExtra("from",1);

		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_this.finish();
			}
		});
		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("登     录");

		telephone_tx = (EditText) findViewById(R.id.telephone_tx);
		password_tx = (EditText) findViewById(R.id.password_tx);

		sign = (TextView) findViewById(R.id.sign);
		sign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		findpassword=(TextView) findViewById(R.id.findpassword);
		findpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(_this, FindLoginPwdActivity.class);
				startActivity(intent);
			}
		});
		register=(TextView) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(_this, RegisterActivity.class);
				startActivity(intent);

			}
		});
		mProgressView = findViewById(R.id.login_progress);
	}
	public void attemptLogin() {
		if (isTasking) {// 任务在运行中不再登入
			return;
		}
		final String telephone = telephone_tx.getText().toString();
		final String password = password_tx.getText().toString();
		if (TextUtils.isEmpty(telephone)) {
			ToastUtils.showShort(_this, "亲，账户名不能为空哟！");
			telephone_tx.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			ToastUtils.showShort(_this, "亲，密码不能为空哟！");
			password_tx.requestFocus();
			return;
		} 
		showProgress(true);
		isTasking = true;
		ServiceLogin.getInstance().postLogin(_this,telephone,password,getIMEI(),"",
				new CallBack<RegisterBack>() {
			@Override
			public void onSuccess(RegisterBack user) {
				ToastUtils.showShort(_this, "登录成功！");
				PreferenceUtil.putBoolean(_this, "hasLogin", true);
				if(user.getData().getNew_sign() == 1) {
					EventBus.getDefault().post("showRegisterPacket");
				}else {
					EventBus.getDefault().post("showRegisterPacketNo");
				}
				showProgress(false);
				isTasking = false;// 任务标记访问结束后
				// 存放你已经登入的信息
				if (!TextUtils.isEmpty(requestName)) {
					Intent intent=new Intent();
					setResult(RESULT_OK, intent);
				}else{
					Intent intent = new Intent(_this,MainActivity.class);
					EventBus.getDefault().post("updateUserInfo");
					intent.putExtra("nowPage", from);// 标记
					startActivity(intent);
				}
				finish();
			}
			@Override
			public void onFailure(ErrorMsg errorMsg) {
				PostHttpInfoUtils.doPostFail(_this, errorMsg, "登陆失败");		
				showProgress(false);
				isTasking = false;
			}
		});
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
	// 返回键退出
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			/*if (TextUtils.isEmpty(requestName)) {
				Intent intent=new Intent(_this, MainActivity.class);					
				intent.putExtra("hasLogin", "cancel");
				startActivity(intent);	

			}*/
			break;
		}
		return super.onKeyUp(keyCode, event);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			
			
		}
	} 
}
