package com.liao310.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.domain.login.RegisterBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLogin;
import com.liao310.www.net.https.ServiceABase;

import de.greenrobot.event.EventBus;


/**
 * 描述：支付弹出框
 * 作者：贾强胜
 */
public class Dialog_Pay extends Dialog implements View.OnClickListener {
    //控件
    TextView mToPayGold;//需要支付的金额
    TextView mHavingGold;//有的金额
    TextView mAddGpld;//加金币
    TextView mReadCard;//阅读卡
    TextView mCancel;//取消
    TextView mPay;//支付
    //上下文
    Context mContext;
    String toPay, havingGold;
    View.OnClickListener mListener;

    public Dialog_Pay(Context context, String toPay, View.OnClickListener itemsOnClick) {
        super(context, 0);
        mContext = context;
        this.toPay = toPay;
        mListener = itemsOnClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.pay_dialog);
        this.setCancelable(false);// flase设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();
        getHavingGold();
    }

    /**
     * 初始化
     */
    private void initView() {
        mToPayGold = findViewById(R.id.ToPay_ToPayGold);
        mHavingGold = findViewById(R.id.ToPay_HavingGold);
        mAddGpld = findViewById(R.id.ToPay_AddGold);
        mReadCard = findViewById(R.id.ToPay_ReadCard);
        mCancel = findViewById(R.id.ToPay_Cancel);
        mPay = findViewById(R.id.ToPay_ToPay);
        mToPayGold.setText("查看: "+toPay+"金币");

    }

    /**
     * 事件监听
     */
    private void initListen() {
        mCancel.setOnClickListener(this);
        mPay.setOnClickListener(this);
        mReadCard.setOnClickListener(this);
        mAddGpld.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ToPay_AddGold:
                //mListener.onClick(view);
                /*Intent intent=new Intent(mContext, PayActivity.class);
                mContext.startActivity(intent);*/
                break;
            case R.id.ToPay_ReadCard:
                //mListener.onClick(view);
                break;
            case R.id.ToPay_ToPay:
                if(Double.parseDouble(toPay)>Double.parseDouble(havingGold)){
                    final Dialog_Hint dialog_hint=new Dialog_Hint(mContext,"余额不足",false);
                    dialog_hint.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog_hint.dismiss();
                        }
                    },1000);
                }else {
                    mListener.onClick(view);
                }
                break;
            case R.id.ToPay_Cancel:
                dismiss();
                break;
        }
    }

    private void getHavingGold(){
        ServiceLogin.getInstance().getInfo(mContext,
            new ServiceABase.CallBack<RegisterBack>() {
                @Override
                public void onSuccess(RegisterBack t) {
                    // TODO 自动生成的方法存根
                    if(t.getData()!=null) {
                        havingGold = t.getData().getMoney();
                        mHavingGold.setText(havingGold+"金币");
                    }
                }
                @Override
                public void onFailure(ErrorMsg errorMessage) {
                    // TODO 自动生成的方法存根
                }
            });}

}
