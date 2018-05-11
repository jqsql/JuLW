package com.liao310.www.activity.mian4.personcenter;

import java.util.Map;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.WebActivity;
import com.liao310.www.activity.mian4.personcenter.account.AccountDetailActivity;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.domain.pay.Pay;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public class ReChargeActivity  extends BaseActivity implements OnClickListener {
	private static final int SDK_PAY_FLAG = 1;
	ReChargeActivity _this;
	private ImageView back;
	private TextView title,accountDetail;
	private View value1,value2,value3,value4,value5,value6;
	private TextView value1_0,value2_0,value3_0,value4_0,value5_0,value6_0;
	private TextView value1_1,value2_1,value3_1,value4_1,value5_1,value6_1;
	private TextView value1_2,value2_2,value3_2,value4_2,value5_2,value6_2;
	private View zfb,wx;
	private ImageView zfbIm,wxIm;
	private TextView notice;
	private TextView sure;
	private int money = 8,moneyType = 1,payway = 1;//0支付宝，1微信

	String str1 = "金币主要用于购买比赛分析文章，金币购买后不可提现，不可退款，如有问题请咨询";
	String str2 = "在线客服";
	String str3 = "或拨打";
	String str4 = "客服热线";
	String str5 = "。";
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: 
				@SuppressWarnings("unchecked") 
				Map<String, String> payResult = (Map<String, String>) msg.obj;
				String resultStatus = payResult.get("resultStatus");				
				String memo = payResult.get("memo");				
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(_this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(_this, "支付失败,"+memo, Toast.LENGTH_SHORT).show();
				}
				break;
			}

		}
	};
	private String orderId;
	IWXAPI api;
	boolean toPay = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recharge);
		api = WXAPIFactory.createWXAPI(this, null);
		api.registerApp(ConstantsBase.APPIP_WEIXIN);
		_this = this;
		initView();
	}
	private void initView() {
		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(this);

		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("充值");
		accountDetail = (TextView) findViewById(R.id.account_detail);
		accountDetail.setVisibility(View.VISIBLE);
		accountDetail.setOnClickListener(this);

		value1 = findViewById(R.id.tv_value1);
		value1_0 = (TextView) findViewById(R.id.tv_value1_0);
		value1_1 = (TextView) findViewById(R.id.tv_value1_1);
		value1_2 = (TextView) findViewById(R.id.tv_value1_2);
		value1.setOnClickListener(this);

		value2 = findViewById(R.id.tv_value2);
		value2_0 = (TextView) findViewById(R.id.tv_value2_0);
		value2_1 = (TextView) findViewById(R.id.tv_value2_1);
		value2_2 = (TextView) findViewById(R.id.tv_value2_2);
		value2.setOnClickListener(this);

		value3 = findViewById(R.id.tv_value3);
		value3_0 = (TextView) findViewById(R.id.tv_value3_0);
		value3_1 = (TextView) findViewById(R.id.tv_value3_1);
		value3_2 = (TextView) findViewById(R.id.tv_value3_2);
		value3.setOnClickListener(this);

		value4 = findViewById(R.id.tv_value4);
		value4_0 = (TextView) findViewById(R.id.tv_value4_0);
		value4_1 = (TextView) findViewById(R.id.tv_value4_1);
		value4_2 = (TextView) findViewById(R.id.tv_value4_2);
		value4.setOnClickListener(this);

		value5 = findViewById(R.id.tv_value5);
		value5_0 = (TextView) findViewById(R.id.tv_value5_0);
		value5_1 = (TextView) findViewById(R.id.tv_value5_1);
		value5_2 = (TextView) findViewById(R.id.tv_value5_2);
		value5.setOnClickListener(this);

		value6 = findViewById(R.id.tv_value6);
		value6_0 = (TextView) findViewById(R.id.tv_value6_0);
		value6_1 = (TextView) findViewById(R.id.tv_value6_1);
		value6_2 = (TextView) findViewById(R.id.tv_value6_2);
		value6.setOnClickListener(this);

		zfb = findViewById(R.id.zfb);
		zfb.setOnClickListener(this);
		wx = findViewById(R.id.wx);
		wx.setOnClickListener(this);
		zfbIm = (ImageView) findViewById(R.id.choosezfb);
		wxIm = (ImageView) findViewById(R.id.choosewx);

		sure = (TextView) findViewById(R.id.btn_pay);
		sure.setOnClickListener(this);


		notice = (TextView) findViewById(R.id.notice);

		SpannableStringBuilder spannableString = new SpannableStringBuilder();
		spannableString.append(str1);
		spannableString.append(str2);
		spannableString.append(str3);
		spannableString.append(str4);
		spannableString.append(str5);
		//文字颜色
		ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.textgreen));
		spannableString.setSpan(colorSpan,str1.length(),str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		spannableString.setSpan(colorSpan,str1.length()+str2.length()+str3.length(),str1.length()+str2.length()+str3.length()+str4.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		//点击事件
		Clickable clickableSpanOnLine = new Clickable(new View.OnClickListener() {  
			@Override  
			public void onClick(View v) {  
				//在这里添加点击事件  
				Intent intent = new Intent(_this,WebActivity.class);
				intent.putExtra("url", "http://tb.53kf.com/code/client/10167449/1");
				intent.putExtra("titleStr", "在线客服");
				startActivity(intent);

			}  
		});		
		spannableString.setSpan(clickableSpanOnLine,str1.length(),str1.length()+str2.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		//点击事件
		Clickable clickableSpanPhone = new Clickable(new View.OnClickListener() {  
			@Override
			public void onClick(View view) {
				//检查权限
				if (ContextCompat.checkSelfPermission(ReChargeActivity.this, "android.permission.CALL_PHONE") == PackageManager.PERMISSION_GRANTED) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					Uri data = Uri.parse("tel:18962196074");
					intent.setData(data);
					startActivity(intent);
				} else {
					// 请求权限
					ActivityCompat.requestPermissions(ReChargeActivity.this, new String[]{"android.permission.CALL_PHONE"}, 1000);
				}
			}
		});
		spannableString.setSpan(clickableSpanPhone,str1.length()+str2.length()+str3.length(),str1.length()+str2.length()+str3.length()+str4.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
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
			startActivity(new Intent(_this,AccountDetailActivity.class));
			break;
		case R.id.tv_value1:
			resetImgs(1);
			break;
		case R.id.tv_value2:
			resetImgs(2);
			break;
		case R.id.tv_value3:
			resetImgs(3);
			break;
		case R.id.tv_value4:
			resetImgs(4);
			break;
		case R.id.tv_value5:
			resetImgs(5);
			break;
		case R.id.tv_value6:
			resetImgs(6);
			break;
		case R.id.zfb:
			zfbIm.setImageResource(R.drawable.useaccount);
			wxIm.setImageResource(R.drawable.useaccountno);
			payway = 0;
			break;
		case R.id.wx:
			zfbIm.setImageResource(R.drawable.useaccountno);
			wxIm.setImageResource(R.drawable.useaccount);
			payway = 1;
			break;
		case R.id.btn_pay:
			if(payway == 0) {
				toPay();
			}if(payway == 1) {
				toWXPay() ;
			}
			break;
		}
	}
	public void toPay() {
		ServicePay.getInstance().reCharge(_this,money+"",payway,
				new CallBack<PayBack>() {
			@Override
			public void onSuccess(PayBack t) {
				// TODO 自动生成的方法存根
				if(t.getData()!=null) {
					orderId = t.getData().getOrder_no();
					if(payway == 0) {
						toZFBPay() ;
					}if(payway == 1) {
						toWXPay() ;
					}
				}

			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);				
			}
		});
	}
	public void toZFBPay() {
		
	}
	public void toWXPay() {
		ServicePay.getInstance().WeiXinReCharge(_this,money+"",moneyType+"",
				new CallBack<PayBack>() {
			@Override
			public void onSuccess(PayBack t) {
				// TODO 自动生成的方法存根
				try{
					toWXPay(t.getData());
				}catch(Exception e){
					ToastUtils.showShort(_this, e.getMessage());
				}

			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);				
			}
		});
	}
	public void toWXPay(Pay pay) {
		try{
			PayReq req = new PayReq();
			req.appId			= pay.getAppid();
			req.partnerId		= pay.getPartnerid();
			req.prepayId		= pay.getPrepayid();
			req.nonceStr		= pay.getNoncestr();
			req.timeStamp		= pay.getTimestamp();
			req.packageValue	= "Sign=WXPay";
			req.sign			= pay.getSign();
			req.extData			= "app data";
			api.sendReq(req);
		}catch(Exception e){
			Toast.makeText(_this, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	protected void resetImgs(int rank) {
		int right1 = value1.getPaddingRight();  
		int top1 = value1.getPaddingTop();  
		int bottom1 = value1.getPaddingBottom();  
		int left1 = value1.getPaddingLeft();


		value1.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value1.setPadding(left1,top1,right1,bottom1); 
		value1_0.setTextColor(getResources().getColor(R.color.textgreen));
		value1_1.setTextColor(getResources().getColor(R.color.textgreen));
		value1_2.setTextColor(getResources().getColor(R.color.textgreen));

		value2.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value2.setPadding(left1,top1,right1,bottom1); 
		value2_0.setTextColor(getResources().getColor(R.color.textgreen));
		value2_1.setTextColor(getResources().getColor(R.color.textgreen));
		value2_2.setTextColor(getResources().getColor(R.color.textgreen));

		value3.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value3.setPadding(left1,top1,right1,bottom1); 
		value3_0.setTextColor(getResources().getColor(R.color.textgreen));
		value3_1.setTextColor(getResources().getColor(R.color.textgreen));
		value3_2.setTextColor(getResources().getColor(R.color.textgreen));

		value4.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value4.setPadding(left1,top1,right1,bottom1); 
		value4_0.setTextColor(getResources().getColor(R.color.textgreen));
		value4_1.setTextColor(getResources().getColor(R.color.textgreen));
		value4_2.setTextColor(getResources().getColor(R.color.textgreen));

		value5.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value5.setPadding(left1,top1,right1,bottom1); 
		value5_0.setTextColor(getResources().getColor(R.color.textgreen));
		value5_1.setTextColor(getResources().getColor(R.color.textgreen));
		value5_2.setTextColor(getResources().getColor(R.color.textgreen));

		value6.setBackgroundResource(R.drawable.cornerfulllinefull_greenline_whitefull);
		value6.setPadding(left1,top1,right1,bottom1); ;
		value6_0.setTextColor(getResources().getColor(R.color.textgreen));
		value6_1.setTextColor(getResources().getColor(R.color.textgreen));
		value6_2.setTextColor(getResources().getColor(R.color.textgreen));
		switch (rank) {	
		case 1:
			value1.setBackgroundResource(R.drawable.cornerfullgreen);
			value1.setPadding(left1,top1,right1,bottom1); 
			value1_0.setTextColor(getResources().getColor(R.color.white));
			value1_1.setTextColor(getResources().getColor(R.color.white));
			value1_2.setTextColor(getResources().getColor(R.color.white));
			money = 8;
			moneyType = 1;
			break;
		case 2:
			value2.setBackgroundResource(R.drawable.cornerfullgreen);
			value2.setPadding(left1,top1,right1,bottom1); 
			value2_0.setTextColor(getResources().getColor(R.color.white));
			value2_1.setTextColor(getResources().getColor(R.color.white));
			value2_2.setTextColor(getResources().getColor(R.color.white));
			money = 18;
			moneyType = 2;
			break;
		case 3: 
			value3.setBackgroundResource(R.drawable.cornerfullgreen);
			value3.setPadding(left1,top1,right1,bottom1);
			value3_0.setTextColor(getResources().getColor(R.color.white));
			value3_1.setTextColor(getResources().getColor(R.color.white));
			value3_2.setTextColor(getResources().getColor(R.color.white));
			money = 28;
			moneyType = 3;
			break;
		case 4:
			value4.setBackgroundResource(R.drawable.cornerfullgreen);
			value4.setPadding(left1,top1,right1,bottom1); 
			value4_0.setTextColor(getResources().getColor(R.color.white));
			value4_1.setTextColor(getResources().getColor(R.color.white));
			value4_2.setTextColor(getResources().getColor(R.color.white));
			money = 58;
			moneyType = 4;
			break;
		case 5:
			value5.setBackgroundResource(R.drawable.cornerfullgreen);
			value5.setPadding(left1,top1,right1,bottom1); 
			value5_0.setTextColor(getResources().getColor(R.color.white));
			value5_1.setTextColor(getResources().getColor(R.color.white));
			value5_2.setTextColor(getResources().getColor(R.color.white));
			money = 88;
			moneyType = 5;
			break;
		case 6:
			value6.setBackgroundResource(R.drawable.cornerfullgreen);
			value6.setPadding(left1,top1,right1,bottom1); 
			value6_0.setTextColor(getResources().getColor(R.color.white));
			value6_1.setTextColor(getResources().getColor(R.color.white));
			value6_2.setTextColor(getResources().getColor(R.color.white));
			money = 188;
			moneyType = 13;
			break;
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
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(toPay) {
			toPay = false;
			refreshInfo();
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		toPay = true;
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
