package com.liao310.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
public class Dialog_Hint extends Dialog {
    //控件
    TextView mTitle;//主题
    TextView mContent;//内容
    //上下文
    Context mContext;
    String title,content;
    View.OnClickListener mListener;
    String type;
    //隐藏判断
    boolean isShow=true;

    public Dialog_Hint(Context context) {
        super(context);
        mContext=context;
    }
    public Dialog_Hint(Context context, String title,String content) {
        super(context, 0);
        mContext=context;
        this.title=title;
        this.content=content;
    }
    public Dialog_Hint(Context context, String title,String content, View.OnClickListener itemsOnClick) {
        super(context, 0);
        mContext=context;
        this.title=title;
        this.content=content;
        mListener=itemsOnClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.hint_dialog);
        this.setCancelable(true);// flase设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mTitle= findViewById(R.id.Hint_Dialog_Title);
        mContent=  findViewById(R.id.Hint_Dialog_Content);
        mTitle.setText(title);
        mContent.setText(content);
    }

    /**
     * 事件监听
     */
    private void initListen() {

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
}
