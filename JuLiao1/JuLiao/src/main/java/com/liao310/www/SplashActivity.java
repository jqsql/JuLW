package com.liao310.www;

import java.io.File;
import java.security.Permission;

import org.xutils.x;

import com.liao310.www.base.BaseActivity;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.shouye.LunBoTuListBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.version.Version;
import com.liao310.www.domain.version.VersionDetail;
import com.liao310.www.net.ServiceShouYe;
import com.liao310.www.net.ServiceVersion;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.AppUpgradeUtils.AppUpgradeManager;
import com.liao310.www.utils.DirsUtil;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.SilentDownHelp;
import com.liao310.www.utils.StatusBarColor;
import com.liao310.www.utils.ToastUtils;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	LinearLayout mLinearLayout;
	VersionDetail mVersion;
	/*private Handler mHandler = new Handler();// 延时间操作确保网络数据访问chenggon

	AlertDialog  alertDialog;
	TextView  updatesize ;
	ProgressBar pgsBar;
	String update ;
	String VersionCode;*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		if (!isTaskRoot()) {
			finish();
			return;
		}
		setContentView(R.layout.activity_splash);
		StatusBarColor.setStatusBar(this);//修改状态栏颜色
		mLinearLayout=findViewById(R.id.progressLayout);
		x.view().inject(this);
		getVersionDatas();
	}
	private void getVersionDatas() {
		// 获取版本信息保存
		ServiceVersion.getInstance().PostGetVersionDatas(this,new CallBack<Version>() {
			@Override
			public void onSuccess(Version verison) {
				if(verison!=null){
					// 拿到版本号后后纯在share中逻辑放在获取网络数据
					String appversion = verison.getDetail().getVersion();
					int updateState = verison.getDetail().getIsForcedUpdate();
					mVersion=verison.getDetail();
					try{
						if(isNeedDownLoadVersion(appversion)){
							dialog(updateState);
						}else{
							getDatas();
						}
					}catch(Exception e){
						e.printStackTrace();
						getDatas();
					}
				}else{
					getDatas();
				}
			}
			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO Auto-generated method stub
				getDatas();
			}
		});
	}
	private void getDatas() {
		 initPage();
	}

	//跳转
	public void initPage() {
		startActivity(new Intent(SplashActivity.this, MainActivity.class));
		finish();
	}

	public void dialog(int updateState){
		if(updateState==0) {//不强制更新
			AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
			builder.setMessage("发现新版本" + mVersion.getVersion() + ",是否立即更新!");
			builder.setTitle("提示");
			builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialog, int which) {
					//检查权限
					if ((ContextCompat.checkSelfPermission(SplashActivity.this,"android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)
					&& (ContextCompat.checkSelfPermission(SplashActivity.this,"android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED)) {
						AppUpgradeManager.getInstance(SplashActivity.this, mVersion).startDown();
						mLinearLayout.setVisibility(View.VISIBLE);
					} else {
						// 请求权限
						ActivityCompat.requestPermissions(SplashActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
								"android.permission.READ_PHONE_STATE"}, 1000);
					}
				}

			});

			builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {

				@Override

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					getDatas();
				}
			});
			builder.create().show();
		}else {//强制更新
			AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
			builder.setMessage("发现新版本" + mVersion.getVersion() + ",请立即更新!");
			builder.setTitle("提示");
			builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(final DialogInterface dialog, int which) {
					//检查权限
					if ((ContextCompat.checkSelfPermission(SplashActivity.this,"android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)
							&& (ContextCompat.checkSelfPermission(SplashActivity.this,"android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED)) {
						AppUpgradeManager.getInstance(SplashActivity.this, mVersion).startDown();
						mLinearLayout.setVisibility(View.VISIBLE);
					} else {
						// 请求权限
						ActivityCompat.requestPermissions(SplashActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
								"android.permission.READ_PHONE_STATE"}, 1000);
					}

				}

			});

			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					finish();//退出应用
				}

			});
			builder.setCancelable(false);
			builder.create().show();
		}
	}

	/**
	 * 将版本数字化比较,是否进行更新
	 */
	private boolean isNeedDownLoadVersion(String onLineStr) {
		String localStr=getPackageInfo().versionName;
		String[] str_OnLine = onLineStr.split("\\.");
		String[] str_Local = localStr.split("\\.");
		if (str_OnLine != null && str_Local != null) {
			if (str_OnLine.length >= 3 && str_Local.length >= 3) {
				if (Integer.parseInt(str_OnLine[0]) > Integer.parseInt(str_Local[0]))
					return true;
				else if (Integer.parseInt(str_OnLine[1]) > Integer.parseInt(str_Local[1]))
					return true;
				else if (Integer.parseInt(str_OnLine[2]) > Integer.parseInt(str_Local[2]))
					return true;
			}
		}
		return false;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			if (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != PackageManager.PERMISSION_GRANTED) {
				return;
			}
			AppUpgradeManager.getInstance(SplashActivity.this, mVersion).startDown();
			mLinearLayout.setVisibility(View.VISIBLE);
		}
	}
}
