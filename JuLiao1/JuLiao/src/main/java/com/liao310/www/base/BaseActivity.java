package com.liao310.www.base;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.shouye.ArticalJCBack;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleDetailBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.ServiceZhuanJia;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.StatusBarColor;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.Dialog_Pay;
import com.liao310.www.widget.Dialog_ProgressBar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

@SuppressLint("NewApi")
public class BaseActivity extends FragmentActivity {
    private IntentFilter mFilter = new IntentFilter();
    AlertDialog alertDialog;
    String imei = "";//设备号
    Dialog_Pay dialog_pay;
    Dialog_ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((CaiPiaoApplication) getApplication()).addActivity(this);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        StatusBarColor.setStatusBarColor(this, R.color.APPColor);//修改状态栏颜色
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((CaiPiaoApplication) getApplication()).removeActivity(this);
    }

    /**
     * 开启新的activity并且关闭自己
     *
     * @param cls 新的activity的字节码
     */
    public void startActivityAndFinishSelf(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * 开启新的activity不关闭自己
     *
     * @param cls 新的activity的字节码
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public String getIMEI() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == PackageManager.PERMISSION_GRANTED) {
            //获取设备号
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } else {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 1000);
        }
        return imei;
    }

    public static PackageInfo getPackageInfo() {
        PackageManager manager = CaiPiaoApplication.getmContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(CaiPiaoApplication.getmContext().getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return info;
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void refreshInfo() {
        ServiceLogin.getInstance().getInfo(this,
                new CallBack<RegisterBack>() {
                    @Override
                    public void onSuccess(RegisterBack t) {
                        // TODO 自动生成的方法存根
                        EventBus.getDefault().post("updateUserInfo");
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                    }
                });
    }

    public void toGetJC1Result(String id, final String price, final String event) {
        ServiceZhuanJia.getInstance().getJC1Result(this, id,
                new CallBack<ArticalJCBack>() {
                    @Override
                    public void onSuccess(ArticalJCBack t) {
                        // TODO 自动生成的方法存根
                        if (t != null) {
                            if ("未购买".equals(t.getData())) {
                                //dialog("竞猜详情","待支付"+art.getPrice()+"金币","取消",true,"去支付",event);
                                dialog("竞猜详情", price + "", "取消", true, "去支付", event);
                            } else {
                                dialog("竞猜详情", "推荐：" + t.getData(), "", false, "确定", event);
                            }
                        }
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(BaseActivity.this, errorMessage.msg);
                    }
                });
    }

    public void dialog(String titleStr, String contentStr, String cancleStr, boolean cancleShow, String sureStr, final String event) {
        if ("去支付".equals(sureStr)) {
            dialog_pay = new Dialog_Pay(this, contentStr, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_pay.dismiss();
                    switch (view.getId()) {
                        case R.id.ToPay_ToPay:
                            if (TextUtils.isEmpty(event)) {
                                dialogToDo();
                            } else {
                                EventBus.getDefault().post("dialogToDo");
                            }
                            break;
                        case R.id.ToPay_ReadCard:
                            if (TextUtils.isEmpty(event)) {
                                toPayForCard();
                            } else {
                                EventBus.getDefault().post("toPayForCard");
                            }
                            break;

                    }
                }
            });
            dialog_pay.show();
        } else {

            if (alertDialog == null) {
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setCancelable(false);
            }
            alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setContentView(R.layout.dialog);
            //去黑角
            window.setBackgroundDrawable(new BitmapDrawable());
            TextView title = (TextView) window.findViewById(R.id.title);
            TextView content = (TextView) window.findViewById(R.id.content);
            TextView cancle = (TextView) window.findViewById(R.id.cancle);
            View line = window.findViewById(R.id.line);
            TextView sure = (TextView) window.findViewById(R.id.sure);
            title.setText(titleStr);
            content.setText(contentStr);
            sure.setText(sureStr);
            if (cancleShow) {
                cancle.setText(cancleStr);
                cancle.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        alertDialog.cancel();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        alertDialog.cancel();
                        if (TextUtils.isEmpty(event)) {
                            dialogToDo();
                        } else {
                            EventBus.getDefault().post("dialogToDo");
                        }
                    }
                });
            } else {
                cancle.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        alertDialog.cancel();
                    }
                });
            }
        }
    }

    public void getArticledetail(final String rid, final String price, final String event) {
        ServiceZhuanJia.getInstance().getRecommendDetail(this, rid,
                new CallBack<ArticleDetailBack>() {
                    @Override
                    public void onSuccess(ArticleDetailBack t) {
                        // TODO 自动生成的方法存根
                        if (t.getData() != null) {
                            if (t.getData().getArticledetail() != null && t.getData().getArticledetail().isShow()) {
                                Intent intent = new Intent(BaseActivity.this, ArticleDetailActivity.class);
                                intent.putExtra("rid", rid);
                                startActivity(intent);
                            } else {
                                if (!PreferenceUtil.getBoolean(BaseActivity.this, "hasLogin")) {
                                    Intent intentLogin = new Intent(BaseActivity.this, LoginActivity.class);
                                    startActivity(intentLogin);
                                } else {
                                    dialog("", price, "取消", true, "去支付", event);
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(BaseActivity.this, errorMessage.msg);
                    }
                });
    }

    public void dialogToDo() {
    }

    public void toPayForCard() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            //获取设备号
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        }
    }

    public void showProgress() {
        if (mProgressBar == null) {
            mProgressBar = new Dialog_ProgressBar(this);
        }
        if (!mProgressBar.isShowing())
            mProgressBar.show();
    }

    public void dismissProgress() {
        if (mProgressBar == null) {
            return;
        }
        if (mProgressBar.isShowing())
            mProgressBar.dismiss();
    }
}
