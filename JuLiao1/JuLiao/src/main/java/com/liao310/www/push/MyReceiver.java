package com.liao310.www.push;

import com.liao310.www.utils.ToastUtils;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	boolean isFirst=true;
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			if(bundle!=null){
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
				if(!isFirst && !isNotificationEnabled(context)){
					//toSetting(context);
                    ToastUtils.showShort(context, "您的通知栏权限未打开，请前往设置！");
				}
                if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
    				//---ToastUtils.showShortCenter(context, "Title : " + title + "\n" + "Content : " + content);
    				/*Intent i = new Intent(context, TestActivity.class);
    				i.putExtras(bundle);
    				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
    				context.startActivity(i);*/
    			}
				isFirst=false;
            }
			
		} catch (Exception e){
			e.printStackTrace();
		}

	}


	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private boolean isNotificationEnabled(Context context) {
		String CHECK_OP_NO_THROW = "checkOpNoThrow";
		String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

		AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
		ApplicationInfo appInfo = context.getApplicationInfo();
		String pkg = context.getApplicationContext().getPackageName();
		int uid = appInfo.uid;

		Class appOpsClass = null;
  		/* Context.APP_OPS_MANAGER */
		try {
			appOpsClass = Class.forName(AppOpsManager.class.getName());
			Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
					String.class);
			Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

			int value = (Integer) opPostNotificationValue.get(Integer.class);
			return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void toSetting(Context context) {
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= 9) {
			localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
		} else if (Build.VERSION.SDK_INT <= 8) {
			localIntent.setAction(Intent.ACTION_VIEW);
			localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
			localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
		}
		context.startActivity(localIntent);
	}
}
