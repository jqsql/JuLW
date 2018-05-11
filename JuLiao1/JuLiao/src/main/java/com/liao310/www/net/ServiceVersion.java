package com.liao310.www.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.version.Version;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;
import android.text.TextUtils;

public class ServiceVersion extends ServiceABase{
	private static ServiceVersion instance;

	public static ServiceVersion getInstance() {
		if (null == instance) {
			instance = new ServiceVersion();
		}
		return instance;
	}

	/**
	 * 首页版本号101接口  获取版本信息
	 */
	public void PostGetVersionDatas(Context context,final CallBack<Version> callBack) {
		if(!NetWorkUtil.isNetworkAvailable(context)){
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return ;
		}
		String url =  ConstantsBase.IP+"index.php/user/chkversion/platform/android";
		
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParamNoAction(url),
				new BaseHttpsCallback<String>() {  
			@Override  
			public void onSuccess(String result) {
				if (!TextUtils.isEmpty(result)) {
					try {
						int errno;
						String errMsg;
						try {
							JSONObject jsonObject = new JSONObject(result);
							errno = jsonObject.getInt("errno");
							if (errno==0) {
								Gson gson = new Gson();
								Version mResult = gson.fromJson(
										result,Version.class);
								callBack.onSuccess(mResult);
							} else {
								errMsg = jsonObject.getString("errorMsg");
								callBack.onFailure(new ErrorMsg("-1", getWrongBack(errMsg)));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							callBack.onFailure(new ErrorMsg("-1", getWrongBack(e.getMessage())));
						}
						return;
					} catch (Exception e) {
						e.printStackTrace();
						callBack.onFailure(new ErrorMsg("-1",getWrongBack(e.getMessage())));
					}
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

	/**
	 * 预加载图106接口
	 */
	/*public void PostGetAdsData(Context context,
			final CallBack<PreLoadPageData> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return ;
		}
		String wAction = "106";//106接口数据
		BaseHttps.getInstance().getHttpRequest(context,GetCommonParam(HOMEIP_APP,wAction,null),
				new BaseHttpsCallback<String>() {  
			@Override  
			public void onSuccess(String result) {
				if (!TextUtils.isEmpty(result)) {
					try {
						// 获取某些信息
						PreLoadPageData preLoadPageData = new PreLoadPageData();

						JSONObject jsonObject2 = new JSONObject(result);

						JSONArray jsonArray = jsonObject2
								.getJSONArray("indexIconList");

						for (int i = 0; i < jsonArray.length(); i++) {

							PreLoadPage preLoadPage = new PreLoadPage(); //

							JSONObject jsonObjectSon = (JSONObject) jsonArray
									.opt(i);

							preLoadPage.setIcon(jsonObjectSon.getString("icon"));
							preLoadPage.setType(jsonObjectSon.getInt("type"));
							preLoadPage.setUrl(jsonObjectSon.getString("url"));
							preLoadPageData.getPreLoadPages().add(preLoadPage);

						}						
						if (preLoadPageData != null) {									
							MyDbUtils.savePreLoadPageData(preLoadPageData);
						}
						
						callBack.onSuccess(preLoadPageData);
						return;
					} catch (Exception e) {
						e.printStackTrace();
						callBack.onFailure(new ErrorMsg("-1",getWrongBack(e.getMessage())));
					}
				}
			}
			@Override  
			public void onError(int code, String message) {  
				callBack.onFailure(new ErrorMsg("-1", getWrongBack(message)));
			}  
			@Override  
			public void onFinished() {  }  
		});

	}*/
}
