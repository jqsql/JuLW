package com.liao310.www.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.LeiTai.RankingListBack;
import com.liao310.www.domain.LeiTai.ZhuanJBuyListBack;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.shouye.LunBoTuListBack;
import com.liao310.www.domain.shouye.NoticeBack;
import com.liao310.www.domain.shouye.ZJRecommendBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.AttentionListBack;
import com.liao310.www.net.https.BaseHttps;
import com.liao310.www.net.https.BaseHttpsCallback;
import com.liao310.www.net.https.ServiceABase;
import com.liao310.www.utils.NetWorkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceLeiTai extends ServiceABase {
    private static ServiceLeiTai instance;

    public static ServiceLeiTai getInstance() {
        if (null == instance) {
            instance = new ServiceLeiTai();
        }
        return instance;
    }

    //获取擂台排行、关注列表
    public void getPHRanking(Context context, String type, int pager, final CallBack<RankingListBack> callBack) {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
            return;
        }

        String token = "";
        User user = MyDbUtils.getCurrentUser();
        if (user != null) {
            token = user.getToken();
        }

        String url = ConstantsBase.IP + "Ring/ring/type/" + type + "/p/" + pager;
        BaseHttps.getInstance().getHttpRequest(context, GetCommonParamNoActionHead(url, token),
                new BaseHttpsCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        int errMsg;
                        String msg = null;
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            errMsg = jsonObject.getInt("errno");
                            msg = jsonObject.getString("msg");
                            if (errMsg == 0 && "success".equals(msg)) {
                                Gson gson = new Gson();
                                RankingListBack mResult = gson.fromJson(
                                        result, RankingListBack.class);
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
                    public void onFinished() {
                    }
                });
    }
    //获取擂台已购列表
    public void getPHBuyRanking(Context context, int pager, final CallBack<ZhuanJBuyListBack> callBack) {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            callBack.onFailure(new ErrorMsg("-1", "当前网络信号较差，请检查网络设置"));
            return;
        }

        String token = "";
        User user = MyDbUtils.getCurrentUser();
        if (user != null) {
            token = user.getToken();
        }

        String url = ConstantsBase.IP + "Ring/ring/type/buy/p/" + pager;
        BaseHttps.getInstance().getHttpRequest(context, GetCommonParamNoActionHead(url, token),
                new BaseHttpsCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        Log.e("获取擂台已购列表", "" + result);
                        int errMsg;
                        String msg = null;
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            errMsg = jsonObject.getInt("errno");
                            msg = jsonObject.getString("msg");
                            if (errMsg == 0 && "success".equals(msg)) {
                                Gson gson = new Gson();
                                ZhuanJBuyListBack mResult = gson.fromJson(
                                        result, ZhuanJBuyListBack.class);
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
                    public void onFinished() {
                    }
                });
    }
}
