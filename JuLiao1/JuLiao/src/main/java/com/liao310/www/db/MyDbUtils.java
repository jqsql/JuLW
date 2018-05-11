package com.liao310.www.db;

import android.util.Log;

import java.util.ArrayList;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.ex.DbException;

import com.liao310.www.base.CaiPiaoApplication;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.LunBoTu;
import com.liao310.www.domain.shouye.LunBoTuList;

public class MyDbUtils {
	private MyDbUtils() {}

	public static void removeAll() {
		removeLoginAll();
		removeAllCurrentLunBoTu();//轮播
	}
	public static void removeLoginAll() {
		removeAllUser();
	}
	/*****************HomeAdsInfo.class*********************/
	// 保存轮播图的信息
	public static void saveLunBoTuList(LunBoTuList lunbo) {

		DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
		ArrayList<LunBoTu> lunboInfos = null;
		try {
			lunboInfos = lunbo.getSlides();
			manager.dropTable(LunBoTu.class); // 删除以前的 保存现在的数据
			manager.saveBindingId(lunboInfos);
		} catch (DbException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	// 获取轮播图的信息
	public static LunBoTuList getLunBoTuList() {

		DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
		LunBoTuList lunboData = new LunBoTuList();
		ArrayList<LunBoTu> lunboInfos = null;
		try {
			lunboInfos = (ArrayList<LunBoTu>) manager.findAll(LunBoTu.class);
			lunboData.setSlides(lunboInfos);
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
		return lunboData;

	}
	public static void removeAllCurrentLunBoTu() {
		DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
		try {  
			manager.dropTable(LunBoTu.class);
		} catch (DbException e) {  
			e.printStackTrace();  
		}
	}
	
	/*****************User.class*********************/
	public static User getCurrentUser() {
		DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
		User user = null;
		try {
			user = manager.findFirst(User.class);
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	public static void saveUser(User user) {
		User currentUser = getCurrentUser();
		if (user != null) {
			try {
				DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
				if (currentUser != null) {
					manager.dropTable(User.class);
				}
				manager.save(user);
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void removeAllUser() {
		if (getCurrentUser()!=null) {
			DbManager manager = x.getDb(CaiPiaoApplication.getConfig());
			try {  
				manager.dropTable(User.class);	
			} catch (DbException e) {  
				e.printStackTrace();  
			}
		}
	}
}
