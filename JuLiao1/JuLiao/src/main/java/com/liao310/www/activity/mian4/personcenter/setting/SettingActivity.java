package com.liao310.www.activity.mian4.personcenter.setting;


import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.PersonalItemView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;
public class SettingActivity extends BaseActivity  implements OnClickListener{
	public static final int LOGIN = 0;
	private Dialog dialog;
	private ImageView back;
	private TextView title;
	private SettingActivity _this;
	private PersonalItemView changePassword,aboutUs,nowversion;
	private Button btn_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		_this = this;
		initView();
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
		title.setText("设    置");
		changePassword = (PersonalItemView) findViewById(R.id.changepassword);
		changePassword.setOnClickListener(this);

		aboutUs = (PersonalItemView) findViewById(R.id.aboutus);
		aboutUs.setOnClickListener(this);
		nowversion = (PersonalItemView) findViewById(R.id.nowversion);
		nowversion.setInfoValue(getPackageInfo().versionName);
		nowversion.setImageIn(View.GONE);

		btn_exit =  findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);

	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {	
		case R.id.changepassword:
			if (!PreferenceUtil.getBoolean(_this,"hasLogin")) {
				Intent intentLogin = new Intent(_this, LoginActivity.class);
				intentLogin.putExtra("requestName", "intentLogin");
				startActivityForResult(intentLogin,LOGIN);
			}else {
				startActivity(new Intent(_this, ChangePassWordActivity.class));
			}	
			break;
		case R.id.aboutus:
			startActivity(new Intent(_this, AboutUsActivity.class));
			break;
		case R.id.btn_exit:
			dialog("温馨提示","您确认退出登录吗？","取消",true,"确定","");
			break;
		}
	}

	public void Clear(){
		MyDbUtils.removeLoginAll();
		PreferenceUtil.clearAll(this);
	}
	public void dialogToDo() {
		ToastUtils.showShort(_this, "退出登录！");
		Clear();
		Intent intent = new Intent(_this,MainActivity.class);
		intent.putExtra("nowPage", 4);// 标记
		startActivity(intent);
		EventBus.getDefault().post("updateUserInfo");
		EventBus.getDefault().post("showRegisterPacketNo");
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case LOGIN:	
				startActivity(new Intent(_this, ChangePassWordActivity.class));
				break;	
			default:
				break;
			}
		}
	}
}  