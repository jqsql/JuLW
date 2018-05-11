package com.liao310.www.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.domain.match.MatchDetailBackBack;
import com.liao310.www.domain.match.MatchListBackBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import android.content.Context;

public class ServiceMatch   extends ServiceABase {
	private static ServiceMatch instance;

	public static ServiceMatch getInstance() {
		if (null == instance) {
			instance = new ServiceMatch();
		}
		return instance;
	}
	public  void getMatch(Context context,int search_type,String recom_type,int time_type,
			int page,final CallBack<MatchListBackBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url = ConstantsBase.IP+"index.php/match/index/recom_type/"
				+recom_type+"/time_type/"+time_type+"/search_type/"+search_type+"/p/"+page;
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
						MatchListBackBack mResult = gson.fromJson(
								result,MatchListBackBack.class);
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
	public  void getMatchDetail(Context context,String aid,int page,int cid,
			final CallBack<MatchDetailBackBack> callBack) {
		if (!NetWorkUtil.isNetworkAvailable(context)) {
			callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
			return;
		}
		String url =  ConstantsBase.IP+"index.php/match/matchvs/aid/"
				+aid+"/p/"+page+"/cid/"+cid;
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
						MatchDetailBackBack mResult = gson.fromJson(
								result,MatchDetailBackBack.class);
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
