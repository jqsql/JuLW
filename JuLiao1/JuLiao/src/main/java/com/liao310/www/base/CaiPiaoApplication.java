package com.liao310.www.base;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.xutils.DbManager;
import org.xutils.x;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class CaiPiaoApplication extends Application {
	// 用于存放倒计时时间
	public static Map<String, Long> map;
	private static List<Activity> activitys = new LinkedList<Activity>();
	private List<Service> services = new LinkedList<Service>();

	private static Context mContext;
	private static DbManager.DaoConfig daoConfig;
	public  static DbManager.DaoConfig getConfig() {
		return daoConfig;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG);
		Log.d("彩票Application", "彩票application预加载init");
		daoConfig =new DbManager.DaoConfig().setDbName("caipiao.db");//数据库更新操作

	}
	public static boolean isAppAlive(Context context, String packageName){
		ActivityManager activityManager =
				(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processInfos
		= activityManager.getRunningAppProcesses();
		for(int i = 0; i < processInfos.size(); i++){
			if(processInfos.get(i).processName.equals(packageName)){
				return true;
			}
		}
		return false;
	}

	public static Context getmContext() {
		return mContext;
	}
	public static Activity getFirstContext() {
		if(activitys==null || activitys.size()==0)
			return null;
		return activitys.get(0);
	}

	public static void setmContext(Context mContext) {
		CaiPiaoApplication.mContext = mContext;
	}

	public void addActivity(Activity activity) {
		activitys.add(activity);
	}

	public void removeActivity(Activity activity) {
		activitys.remove(activity);
	}

	public void addService(Service service) {
		services.add(service);
	}

	public void removeService(Service service) {
		services.remove(service);
	}

	public void closeApplication() {
		closeActivitys();
		closeServices();
	}

	private void closeActivitys() {
		ListIterator<Activity> iterator = activitys.listIterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity != null) {
				activity.finish();
			}
		}
	}

	private void closeServices() {
		ListIterator<Service> iterator = services.listIterator();
		while (iterator.hasNext()) {
			Service service = iterator.next();
			if (service != null) {
				stopService(new Intent(this, service.getClass()));
			}
		}
	}

}
