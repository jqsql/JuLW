package com.liao310.www.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.personal.AccountBack;
import com.liao310.www.domain.personal.SendCardListBackBack;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;

public class ServicePersonal  extends ServiceABase {
	private static ServicePersonal instance;

	public static ServicePersonal getInstance() {
		if (null == instance) {
			instance = new ServicePersonal();
		}
		return instance;
	}
	//我的赠送卡
	public  void getMySend(Context context,
			final CallBack<SendCardListBackBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/user/mycard";
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url,token),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {  
				int errMsg;
				String msg = null;
				try {
					JSONObject jsonObject = new JSONObject(result);
					errMsg = jsonObject.getInt("errno");
					msg = jsonObject.getString("msg");
					if ( errMsg == 0&&"success".equals(msg)) { 
						Gson gson = new Gson();
						SendCardListBackBack mResult = gson.fromJson(
								result,SendCardListBackBack.class);
						callBack.onSuccess(mResult);
					} else {
						callBack.onFailure(new ErrorMsg("-1", getWrongBack(msg)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callBack.onFailure(new ErrorMsg("-1", getWrongBack(e.getMessage())));
				}
			}  
			@Override  
			public void onError(int code, String message) {                                
				callBack.onFailure(new ErrorMsg("-1", getWrongBack(message)));
			}  
			@Override  
			public void onFinished() {  }  
		});  
	} 
	//我的收藏
	public  void getMyFavourite(Context context,int page,
			final CallBack<ArticleListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
 		String url =  ConstantsBase.IP+"index.php/user/myfavorite/p/"+page;
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url,token),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {  
				int errMsg;
				String msg = null;
				try {
					JSONObject jsonObject = new JSONObject(result);
					errMsg = jsonObject.getInt("errno");
					msg = jsonObject.getString("msg");
					if ( errMsg == 0&&"success".equals(msg)) { 
						Gson gson = new Gson();
						ArticleListBack mResult = gson.fromJson(
								result,ArticleListBack.class);
						callBack.onSuccess(mResult);
					} else {
						callBack.onFailure(new ErrorMsg("-1", getWrongBack(msg)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callBack.onFailure(new ErrorMsg("-1", getWrongBack(e.getMessage())));
				}
			}  
			@Override  
			public void onError(int code, String message) {                                
				callBack.onFailure(new ErrorMsg("-1", getWrongBack(message)));
			}  
			@Override  
			public void onFinished() {  }  
		});  
	}
	//我的购买
	public  void getMyBuy(Context context,int page,
			final CallBack<ArticleListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/user/mybought/p/"+page;
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url,token),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {  
				int errMsg;
				String msg = null;
				try {
					JSONObject jsonObject = new JSONObject(result);
					errMsg = jsonObject.getInt("errno");
					msg = jsonObject.getString("msg");
					if ( errMsg == 0&&"success".equals(msg)) { 
						Gson gson = new Gson();
						ArticleListBack mResult = gson.fromJson(
								result,ArticleListBack.class);
						callBack.onSuccess(mResult);
					} else {
						callBack.onFailure(new ErrorMsg("-1", getWrongBack(msg)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callBack.onFailure(new ErrorMsg("-1", getWrongBack(e.getMessage())));
				}
			}  
			@Override  
			public void onError(int code, String message) {                                
				callBack.onFailure(new ErrorMsg("-1", getWrongBack(message)));
			}  
			@Override  
			public void onFinished() {  }  
		});  
	}
	//我的账户明细
	public  void getMyAccount(Context context,String from,int page,
			final CallBack<AccountBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url = null;
		if("recharge".equals(from)) {
			url =  ConstantsBase.IP+"index.php/user/rechargemore/p/"+page;
		}else {
			url =  ConstantsBase.IP+"index.php/user/costmore/p/"+page;
		}
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url,token),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {  
				int errMsg;
				String msg = null;
				try {
					JSONObject jsonObject = new JSONObject(result);
					errMsg = jsonObject.getInt("errno");
					msg = jsonObject.getString("msg");
					if ( errMsg == 0&&"success".equals(msg)) { 
						Gson gson = new Gson();
						AccountBack mResult = gson.fromJson(
								result,AccountBack.class);
						callBack.onSuccess(mResult);
					} else {
						callBack.onFailure(new ErrorMsg("-1", getWrongBack(msg)));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callBack.onFailure(new ErrorMsg("-1", getWrongBack(e.getMessage())));
				}
			}  
			@Override  
			public void onError(int code, String message) {                                
				callBack.onFailure(new ErrorMsg("-1", getWrongBack(message)));
			}  
			@Override  
			public void onFinished() {  }  
		});  
	} 
	
}
