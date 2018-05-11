package com.liao310.www.activity.mian4;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends BaseActivity {
	private ImageView back;
	private TextView title;
	private WebView mWebView = null;
	private Dialog mDialog;
	String url = null,titleStr="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webactivity);
		url = getIntent().getStringExtra("url");
		titleStr = getIntent().getStringExtra("titleStr");
		initView();
	}

	private void initView() {
		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebActivity.this.finish();
			}
		});

		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText(titleStr);

		mWebView = (WebView) findViewById(R.id.wv);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
		// 缓存模式
		mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		mWebView.getSettings().setAllowFileAccess(true);
		// 开启 database storage API 功能 //启用数据库
		mWebView.getSettings().setDatabaseEnabled(true);
		//mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
		String cacheDirPath = getFilesDir().getAbsolutePath() + "/webviewCache";
		// 设置数据库缓存路径
		mWebView.getSettings().setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		mWebView.getSettings().setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		mWebView.getSettings().setAppCacheEnabled(true);
		String dir = this.getApplicationContext()
				.getDir("database", Context.MODE_PRIVATE).getPath();
		// 设置定位的数据库路径
		mWebView.getSettings().setGeolocationDatabasePath(dir);
		// 最重要的方法，一定要设置，这就是出不来的主要原因
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.loadUrl(url);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{ //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			// 动画加载
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {					
					dismissDialog();
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		showDialog();
	}
	public void showDialog() {
		if (!this.isFinishing()) {
			if (mDialog == null)
				mDialog = makeDialog(this,true);
			if (!mDialog.isShowing())
				mDialog.show();
		}
	}
	public void dismissDialog() {
		if (!this.isFinishing() && mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}

	}
	/**
	 * 带菊花的对话框 *
	 */
	public Dialog makeDialog(final Context context, boolean cancelable) {
		final Dialog loadingDialog = ProgressDialog.show(context, "",
				"正在加载，请稍候...");
		loadingDialog.setCancelable(cancelable);
		loadingDialog
		.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialogInterface) {
				try {
					if (loadingDialog != null
							&& loadingDialog.isShowing()
							&& !((Activity) context).isFinishing()) {
						loadingDialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		loadingDialog
		.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialogInterface) {
				try {
					if (loadingDialog != null
							&& loadingDialog.isShowing()
							&& !((Activity) context).isFinishing()) {
						loadingDialog.dismiss();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return loadingDialog;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
