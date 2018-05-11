package com.liao310.www.activity.mian4.personcenter.setting;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.version.Back;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PostHttpInfoUtils;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.utils.Util;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangePassWordActivity extends BaseActivity {
	private ChangePassWordActivity _this;
	private ImageView back;
	private TextView title;

	private EditText passwordold_tx;
	private EditText passwordnew_tx;
	private EditText passwordnew2_tx;
	
	private TextView sure;

	private boolean isTasking = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);

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
		title.setText("修改密码");

		passwordold_tx = (EditText) findViewById(R.id.passwordold_tx);
		passwordnew_tx = (EditText) findViewById(R.id.passwordnew_tx);
		passwordnew2_tx = (EditText) findViewById(R.id.passwordnew2_tx);

		
		sure = (TextView) findViewById(R.id.sure);
		sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String passwordold = passwordold_tx.getText().toString().trim();
				final String passwordnew = passwordnew_tx.getText().toString().trim();
				final String passwordnew2 = passwordnew2_tx.getText().toString().trim();
				
				if (TextUtils.isEmpty(passwordold)) {
					ToastUtils.showShort(_this, "旧密码为空");
					passwordold_tx.requestFocus();
					return;
				}
				if (TextUtils.isEmpty(passwordnew)) {
					ToastUtils.showShort(_this, "新密码为空");
					passwordnew_tx.requestFocus();
					return;
				}
				if (TextUtils.isEmpty(passwordnew2)) {
					ToastUtils.showShort(_this, "确认密码为空");
					passwordnew2_tx.requestFocus();
					return;
				}
				if (!passwordnew.equals(passwordnew2)) {
					ToastUtils.showShort(_this, "新密码和确认密码不一致");
					passwordnew_tx.requestFocus();
					return;
				}
				if ((!TextUtils.isEmpty(passwordnew))
						&& Util.textNameTemp1(passwordnew)) {
					ToastUtils.showShort(_this, "密码不能全为数字");
					passwordnew_tx.requestFocus();
					return;
				}
				if ((!TextUtils.isEmpty(passwordnew))
						&& Util.isAllEnglish(passwordnew)) {
					ToastUtils.showShort(_this, "密码不能全为字母");
					passwordnew_tx.requestFocus();
					return;
				}	
				if (!Util.isPasswordValid_OnlyNumAndLeter(passwordnew)||passwordnew.length()<6 || passwordnew.length()>15) {
					ToastUtils.showShort(_this, "密码为6-15位字母和数字组成的密码");
					passwordnew_tx.requestFocus();
					return;
				}
				if(isTasking) {
					return;
				}
				isTasking = true;
				ServiceLogin.getInstance().postChangePassword(_this, passwordold,
						passwordnew,passwordnew2,
						new CallBack<Back>() {
					@Override
					public void onSuccess(Back s) {
						ToastUtils.showShort(_this, "成功修改密码");
						isTasking = false;
						finish();
					}

					@Override
					public void onFailure(ErrorMsg errorMsg) {
						PostHttpInfoUtils.doPostFail(_this, errorMsg,"失败");
						isTasking = false;
					}
				});
			}
		});
	}

}
