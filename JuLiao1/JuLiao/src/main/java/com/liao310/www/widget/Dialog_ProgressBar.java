package com.liao310.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.liao310.www.R;


/**
 * 描述：自定义dialog
 * 作者：贾强胜
 * 时间：2018.5.10
 */
public class Dialog_ProgressBar extends Dialog {

    public Dialog_ProgressBar(Context context) {
        super(context,R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.progressbar_dialog);
        this.setCancelable(false);// flase设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {

    }

    /**
     * 事件监听
     */
    private void initListen() {

    }
}
