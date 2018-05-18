package com.liao310.www.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.ArticalJCBack;
import com.liao310.www.domain.shouye.ArticleDetailBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.FavouriteBack;
import com.liao310.www.domain.zhuanjia.FouceBack;
import com.liao310.www.domain.zhuanjia.ZhuanJiaDetailBackBack;
import com.liao310.www.domain.zhuanjia.ZhuanJiaListBack;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;
import android.util.Log;

public class ServiceZhuanJia   extends ServiceABase {
	private static ServiceZhuanJia instance;

	public static ServiceZhuanJia getInstance() {
		if (null == instance) {
			instance = new ServiceZhuanJia();
		}
		return instance;
	}
	//专家列表
	public  void getZhuanJia(Context context,int titleType,int type,int page,
			final CallBack<ZhuanJiaListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/expert/expertlist2/p/"+page
		+"/pl/10/titletype/"+titleType+"/type/"+type;
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
						ZhuanJiaListBack mResult = gson.fromJson(
								result,ZhuanJiaListBack.class);
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
	public  void getRecommendDetail(Context context,String rid,
			final CallBack<ArticleDetailBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/expert/detail/rid/"+rid;
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
						ArticleDetailBack mResult = gson.fromJson(
								result,ArticleDetailBack.class);
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
	//关注
	public  void getFouce(Context context,String uid,int i,
			final CallBack<FouceBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/expert/follow/uid/"+uid+"/type/"+i;
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
					if ( errMsg == 0) { 
						Gson gson = new Gson();
						FouceBack mResult = gson.fromJson(
								result,FouceBack.class);
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
	//关注专家
	public  void getZhuanJiaFouced(Context context,String type,int page,
			final CallBack<ZhuanJiaListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/expert/expertlistmore/p/"+page+"/type/"+type;
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
						ZhuanJiaListBack mResult = gson.fromJson(
								result,ZhuanJiaListBack.class);
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
	//专家详情
	public  void getZhuanJiaDetail(Context context,String rid,int page,
			final CallBack<ZhuanJiaDetailBackBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		//Log.e("专家竞猜id=",""+rid);
		String url = ConstantsBase.IP+"index.php/expert/index/uid/"+rid+"/p/"+page;
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
						ZhuanJiaDetailBackBack mResult = gson.fromJson(
								result,ZhuanJiaDetailBackBack.class);
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
	//获取竞猜结果
	public  void getJC1Result(Context context,String rid,
			final CallBack<ArticalJCBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/article/checkjcai/rid/"+rid;
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url,token),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {
				Log.e("获取竞猜列表点击",""+result);
				int errMsg;
				String msg = null;
				try {
					JSONObject jsonObject = new JSONObject(result);
					errMsg = jsonObject.getInt("errno");
					msg = jsonObject.getString("msg");
					if ( errMsg == 0&&"success".equals(msg)) { 
						Gson gson = new Gson();
						ArticalJCBack mResult = gson.fromJson(
								result,ArticalJCBack.class);
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
	public  void setFavourite(Context context,String rid,int type,//1加收藏，否则减收藏
			final CallBack<FavouriteBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/user/favorite/rid/"+rid+"/type/"+type;
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
						FavouriteBack mResult = gson.fromJson(
								result,FavouriteBack.class);
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

/*
	*/
/**
	 * 专家排行榜
	 *//*

	public  void getExpertList(Context context,int titletype,int type,int p,int pl,
							  final CallBack<ZhuanJiaListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String token = "";
		User user = MyDbUtils.getCurrentUser();
		if(user!=null) {
			token = user.getToken();
		}
		String url =  ConstantsBase.IP+"index.php/expert/expertlist2/p/"+p+"/pl/"+pl+"/titletype/"+titletype+"/type/"+type;
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
								ZhuanJiaListBack mResult = gson.fromJson(
										result,ZhuanJiaListBack.class);
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
*/

}
