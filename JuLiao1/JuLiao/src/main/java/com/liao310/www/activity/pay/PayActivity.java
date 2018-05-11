package com.liao310.www.activity.pay;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.WebActivity;
import com.liao310.www.activity.mian4.personcenter.ReChargeActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class PayActivity extends BaseActivity implements OnClickListener {
	PayActivity _this;
	private ImageView back;
	private TextView title, recharge, name, money, yue;
	private View ye, zfb, wx;
	private TextView notice;
	private int payway = 0;//0支付宝，1微信

	String str1 = "金币主要用于购买比赛分析文章，金币购买后不可提现，不可退款，如有问题请咨询";
	String str2 = "在线客服";
	String str3 = "或拨打";
	String str4 = "客服热线";
	String str5 = "。";
	private String rid, nameStr, prize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		rid = getIntent().getStringExtra("rid");
		nameStr = getIntent().getStringExtra("name");
		prize = getIntent().getStringExtra("prize");
		_this = this;
		EventBus.getDefault().register(_this);
		initView();
		refreshInfo();
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(this);

		title = (TextView) findViewById(R.id.tv_head_title);
		title.setText("购买");
		recharge = (TextView) findViewById(R.id.account_detail);
		recharge.setText("充值");
		recharge.setVisibility(View.VISIBLE);
		recharge.setOnClickListener(this);
		name = (TextView) findViewById(R.id.name);
		name.setText(nameStr + "的推荐");
		money = (TextView) findViewById(R.id.money);
		money.setText(prize);

		ye = findViewById(R.id.ye);
		ye.setOnClickListener(this);
		yue = (TextView) findViewById(R.id.yue);
		zfb = findViewById(R.id.zfb);
		zfb.setOnClickListener(this);
		wx = findViewById(R.id.wx);
		wx.setOnClickListener(this);

		notice = (TextView) findViewById(R.id.notice);

		SpannableStringBuilder spannableString = new SpannableStringBuilder();
		spannableString.append(str1);
		spannableString.append(str2);
		spannableString.append(str3);
		spannableString.append(str4);
		spannableString.append(str5);
		//文字颜色
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.textgreen));
		spannableString.setSpan(colorSpan, str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		spannableString.setSpan(colorSpan, str1.length() + str2.length() + str3.length(), str1.length() + str2.length() + str3.length() + str4.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		//点击事件
		Clickable clickableSpanOnLine = new Clickable(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//在这里添加点击事件  
				Intent intent = new Intent(_this, WebActivity.class);
				intent.putExtra("url", "http://tb.53kf.com/code/client/10167449/1");
				intent.putExtra("titleStr", "在线客服");
				startActivity(intent);

			}
		});
		spannableString.setSpan(clickableSpanOnLine, str1.length(), str1.length() + str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		//点击事件
		Clickable clickableSpanPhone = new Clickable(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//检查权限
				if (ContextCompat.checkSelfPermission(PayActivity.this, "android.permission.CALL_PHONE") == PackageManager.PERMISSION_GRANTED) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					Uri data = Uri.parse("tel:18962196074");
					intent.setData(data);
					startActivity(intent);
				} else {
					// 请求权限
					ActivityCompat.requestPermissions(PayActivity.this, new String[]{"android.permission.CALL_PHONE"}, 1000);
				}

			}
		});
		spannableString.setSpan(clickableSpanPhone, str1.length() + str2.length() + str3.length(), str1.length() + str2.length() + str3.length() + str4.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		notice.setText(spannableString);
		notice.setMovementMethod(LinkMovementMethod.getInstance());
		notice.setHighlightColor(getResources().getColor(R.color.touming));//去掉点击后的背景颜色为透明  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.iv_back:
				_this.finish();
				break;
			case R.id.account_detail:
				startActivity(new Intent(_this, ReChargeActivity.class));
				break;
			case R.id.ye:
				payway = 1;
				toPay();
				break;
			case R.id.wx:
				payway = 2;
				break;
			case R.id.zfb:
				payway = 3;
				break;
		}
	}

	public void toPay() {
		dialog("余额支付", "您确认使用余额支付" + prize + "吗？", "取消", true, "确认", "");
	}

	public void toShow(RegisterBack t) {
		if (t.getData() != null &&
				t.getData().getMoney() != null) {
			yue.setText("（余额：" + t.getData().getMoney() + "金币）");
		}
	}


	class Clickable extends ClickableSpan implements View.OnClickListener {
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener mListener) {
			this.mListener = mListener;
		}

		@Override
		public void onClick(View v) {
			mListener.onClick(v);
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(ds.linkColor);
			ds.setUnderlineText(false);    //去除超链接的下划线  
		}
	}

	private void updateUserInfo() {
		User user = MyDbUtils.getCurrentUser();
		if (user != null && PreferenceUtil.getBoolean(_this, "hasLogin")) {
			yue.setText("（余额：" + user.getMoney() + "金币）");
		} else {
			yue.setText("（余额：0金币）");
		}
	}

	public void onEventMainThread(String event) {
		if (!TextUtils.isEmpty(event)) {
			if ("updateUserInfo".equals(event)) {
				updateUserInfo();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void dialogToDo() {
		ServicePay.getInstance().pay(_this, rid, 0, payway,
				new CallBack<PayBack>() {
					@Override
					public void onSuccess(PayBack t) {
						// TODO 自动生成的方法存根
						ToastUtils.showShort(_this, "购买成功");
						if (t.getData() != null &&
								!TextUtils.isEmpty(t.getData().getMoney())) {
							User user = MyDbUtils.getCurrentUser();
							user.setMoney(t.getData().getMoney());
							MyDbUtils.saveUser(user);
						}
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
						finish();
					}

					@Override
					public void onFailure(ErrorMsg errorMessage) {
						// TODO 自动生成的方法存根
						ToastUtils.showShort(_this, errorMessage.msg);
					}
				});
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			Uri data = Uri.parse("tel:18962196074");
			intent.setData(data);
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			startActivity(intent);
		}
	}
}
