package com.liao310.www.net.https;

import java.io.IOException;
import java.util.Map;

import org.xutils.http.RequestParams;

import com.liao310.www.domain.version.ErrorMsg;

import android.text.TextUtils;
import android.util.Log;

public class ServiceABase {
	public interface CallBack<T> {
		void onSuccess(T t);

		void onFailure(ErrorMsg errorMessage);

	}
	public String getWrongBack(String wrongMessage){
		Log.e("WrongMessage===",wrongMessage);
		if(!TextUtils.isEmpty(wrongMessage)){
			if(wrongMessage.contains("timed out") || wrongMessage.contains("after 10000ms")){
				wrongMessage = "链接超时";
			}
			if(wrongMessage.contains("refused")){
				wrongMessage = "链接被拒绝，可能是服务器未开启";
			}
			if("Endofinputatcharacter0of".equals(wrongMessage.trim().replace(" ", ""))){
				wrongMessage = "暂无数据";
			}
			if("Not Found".equals(wrongMessage)){
				wrongMessage = "服务器开差了";
			}
			if("Endofinputatcharacter0of".equals(wrongMessage.trim().replace(" ", ""))){
				wrongMessage = "暂无数据";
			}
		}
		return wrongMessage;
	}
	
	public RequestParams GetCommonParamNoAction(String url) {
		RequestParams ParamMapCom = new RequestParams(url);
		return ParamMapCom;
	}
	public RequestParams GetCommonParamNoActionHead(String url,String head) {
		RequestParams ParamMapCom = new RequestParams(url); 
		ParamMapCom.addHeader("token",head);
		return ParamMapCom;
	} 
	public RequestParams GetCommonParam(String url,
			Map<String, String> ParamMap) { 
		RequestParams params=new RequestParams(url);  
		try {  
			params.setRequestBody(new UrlEncodedParamsBody(ParamMap,"utf-8"));  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return params;
	}
	public RequestParams GetCommonParam(String url,String head,
			Map<String, String> ParamMap) { 
		RequestParams params=new RequestParams(url);  
		params.addHeader("token",head);
		try {  
			params.setRequestBody(new UrlEncodedParamsBody(ParamMap,"utf-8"));  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
		return params;
	}
}
