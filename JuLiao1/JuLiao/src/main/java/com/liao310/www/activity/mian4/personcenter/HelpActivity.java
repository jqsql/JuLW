package com.liao310.www.activity.mian4.personcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;

public class HelpActivity extends BaseActivity {
    private ImageView back;
    private TextView title;
    private WebView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mContent = findViewById(R.id.Help_Content);
        back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        title = findViewById(R.id.tv_head_title);
        title.setText("用户帮助");


        mContent.loadUrl("file:///android_asset/userHelp.htm");
        //不使用Android默认浏览器打开Web，就在App内部打开Web

        mContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //支持App内部javascript交互
        mContent.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        mContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mContent.getSettings().setLoadWithOverviewMode(true);
        //设置可以支持缩放
        mContent.getSettings().setSupportZoom(false);
        //扩大比例的缩放
        mContent.getSettings().setUseWideViewPort(false);
        //设置是否出现缩放工具
        mContent.getSettings().setBuiltInZoomControls(false);
    }
}
