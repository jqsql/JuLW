package com.liao310.www.net;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.version.Back;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;

public class ServiceLogin  extends ServiceABase {
	private static ServiceLogin instance;

	public static ServiceLogin getInstance() {
		if (null == instance) {
			instance = new ServiceLogin();
		}
		return instance;
	}
	//找回密码_验证码
	public  void getYanZhengMa(Context context,String mobile,int type,//1注册，2找回密码
			final CallBack<Back> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/user/sendsms/mobile/"+mobile+"/type/"+type;
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoAction(url),
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
						Back mResult = gson.fromJson(
								result,Back.class);
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
	//找回密码_确认
	public  void postPassword(Context context,String mobile,String code,String newPassword,
			final CallBack<Back> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/user/findpwd";

		Map<String, String> ParamMap = new LinkedHashMap<String, String>();
		ParamMap.put("mobile", mobile);
		ParamMap.put("verification_code", code);
		ParamMap.put("password", newPassword);

		BaseHttps.getInstance().postHttpRequest(context,GetCommonParam(url,ParamMap),
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
						Back mResult = gson.fromJson(
								result,Back.class);
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
	//注册
	public  void postRegister(Context context,String mobile,String verification_code,String password,
			String device,String invite_no,
			final CallBack<RegisterBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/user/reg";

		Map<String, String> ParamMap = new LinkedHashMap<String, String>();
		ParamMap.put("mobile", mobile);
		ParamMap.put("verification_code", verification_code);
		ParamMap.put("password", password);
		ParamMap.put("equi_no", device);
		ParamMap.put("parent", invite_no);

		BaseHttps.getInstance().postHttpRequest(context,GetCommonParam(url,ParamMap),
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
						RegisterBack mResult = gson.fromJson(
								result,RegisterBack.class);
						if (mResult.getData() != null) {									
							MyDbUtils.saveUser(mResult.getData());
						}
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
	//登录
	public void postLogin(Context context,String mobile,String password,
			String equi_no,String device,
			final CallBack<RegisterBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/user/login";

		Map<String, String> ParamMap = new LinkedHashMap<String, String>();
		ParamMap.put("mobile", mobile);
		ParamMap.put("password", password);
		ParamMap.put("equi_no", equi_no);
		ParamMap.put("device", device);

		BaseHttps.getInstance().postHttpRequest(context,GetCommonParam(url,ParamMap),
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
						RegisterBack mResult = gson.fromJson(
								result,RegisterBack.class);	
						if (mResult.getData() != null) {									
							MyDbUtils.saveUser(mResult.getData());
						}					
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
	//修改密码
	public  void postChangePassword(Context context,String mobile,String code,String newPassword,
			final CallBack<Back> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/user/changepwd";
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		Map<String, String> ParamMap = new LinkedHashMap<String, String>();
		ParamMap.put("old_pwd", mobile);
		ParamMap.put("new_pwd", code);
		ParamMap.put("second_pwd", newPassword);
		
		BaseHttps.getInstance().postHttpRequest(context,GetCommonParam(url,token,ParamMap),
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
						Back mResult = gson.fromJson(
								result,Back.class);
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

	public void getInfo(Context context,final CallBack<RegisterBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}	
		String url =  ConstantsBase.IP+"index.php/user/mystatus";
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
						RegisterBack mResult = gson.fromJson(
								result,RegisterBack.class);	
						if (mResult.getData() != null) {									
							MyDbUtils.saveUser(mResult.getData());
						}					
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