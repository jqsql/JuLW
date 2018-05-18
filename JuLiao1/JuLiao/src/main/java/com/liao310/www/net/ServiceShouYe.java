package com.liao310.www.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.CaiPiaoApplication;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.shouye.LunBoTuListBack;
import com.liao310.www.domain.shouye.LunBoTuListNewBack;
import com.liao310.www.domain.shouye.NoticeBack;
import com.liao310.www.domain.shouye.ZJRecommendBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.AttentionList;
import com.liao310.www.domain.zhuanjia.AttentionListBack;
import com.liao310.www.domain.zhuanjia.ZhuanJiaListBack;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;
import android.util.Log;

public class ServiceShouYe  extends ServiceABase {
	private static ServiceShouYe instance;

	public static ServiceShouYe getInstance() {
		if (null == instance) {
			instance = new ServiceShouYe();
		}
		return instance;
	}

	//首页轮播图
	public  void gettLunBoTuList(Context context,
			final CallBack<LunBoTuListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/article/sliders";
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
						LunBoTuListBack mResult = gson.fromJson(
								result,LunBoTuListBack.class);
						if(mResult!=null){
							MyDbUtils.saveLunBoTuList(mResult.getData());
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
	//首页轮播图
	public  void gettLunBoTuListNew(Context context,
								 final CallBack<LunBoTuListNewBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"article/sliders2";
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
								LunBoTuListNewBack mResult = gson.fromJson(
										result,LunBoTuListNewBack.class);
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
	//首页通知
	public  void getNoticeList(Context context,
			final CallBack<NoticeBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/expert/broadcast2";
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
						NoticeBack mResult = gson.fromJson(
								result,NoticeBack.class);
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
	//首页文章
	public  void getJingCaiGuoGuanList(Context context,int page,int type,
			final CallBack<ArticleListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/article/articles2/p/"+page+"/type/"+type;
		Log.e("首页文章列表",""+url);
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoAction(url),
				new BaseHttpsCallback<String>() {  

			@Override  
			public void onSuccess(String result) {
				Log.e("首页文章列表",""+result);
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
	//关注的专家文章
	public  void getFocueWenZhangList(Context context,int page,
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
		String url =  ConstantsBase.IP+"index.php/user/followarticle/p/"+page;
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
	} //vip文章
	public  void getVipList(Context context,int page,
			final CallBack<ArticleListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/article/vipart/p/"+page;
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
	//免费专区
	public  void getFreeList(Context context,int page,
			final CallBack<ArticleListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/article/freeart/p/"+page;
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
				}catch (Exception e) {
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

	//获取关注列表
	public  void getZJAttentionList(Context context,final CallBack<AttentionListBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
        String token = "";
        User user = MyDbUtils.getCurrentUser();
        if(user!=null) {
            token = user.getToken();
        }
		String url =  ConstantsBase.IP+"index.php/user/followExpert2";
		Log.e("获取关注列表token",""+token);
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoActionHead(url, token),
				new BaseHttpsCallback<String>() {

					@Override
					public void onSuccess(String result) {
						Log.e("获取关注列表",""+result);
						int errMsg;
						String msg = null;
						try {
							JSONObject jsonObject = new JSONObject(result);
							errMsg = jsonObject.getInt("errno");
							msg = jsonObject.getString("msg");
							if ( errMsg == 0&&"success".equals(msg)) {
								Gson gson = new Gson();
								AttentionListBack mResult = gson.fromJson(
										result,AttentionListBack.class);
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
	//获取专家推荐列表
	public  void getZJRecommend(Context context,final CallBack<ZJRecommendBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"expert/retag";
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoAction(url),
				new BaseHttpsCallback<String>() {

					@Override
					public void onSuccess(String result) {
						Log.e("获取专家推荐列表",""+result);
						int errMsg;
						String msg = null;
						try {
							JSONObject jsonObject = new JSONObject(result);
							errMsg = jsonObject.getInt("errno");
							msg = jsonObject.getString("msg");
							if ( errMsg == 0&&"success".equals(msg)) {
								Gson gson = new Gson();
								ZJRecommendBack mResult = gson.fromJson(
										result,ZJRecommendBack.class);
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
