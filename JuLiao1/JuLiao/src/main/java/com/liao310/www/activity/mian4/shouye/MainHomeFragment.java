package com.liao310.www.activity.mian4.shouye;


import java.util.ArrayList;
import java.util.List;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.WebActivity;
import com.liao310.www.activity.mian4.adapter.ArticleListAdapter;
import com.liao310.www.activity.mian4.adapter.ViewPagerAdapter;
import com.liao310.www.activity.mian4.adapter.ZJTuiJianViewAdapter;
import com.liao310.www.activity.mian4.shouye.zhuanjia.ZhuanJiaFoucedActivity;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.shouye.LunBoTu;
import com.liao310.www.domain.shouye.LunBoTuList;
import com.liao310.www.domain.shouye.LunBoTuListBack;
import com.liao310.www.domain.shouye.Notice;
import com.liao310.www.domain.shouye.NoticeBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceShouYe;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.ImageCycleView;
import com.liao310.www.widget.ImageCycleView.ImageCycleViewListener;
import com.liao310.www.widget.LooperTextView;
import com.liao310.www.widget.MyGrideview;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

@SuppressLint("NewApi")
public class MainHomeFragment extends Fragment implements OnClickListener {
    public static final int LOGIN_PAY = 1;
    public static final int TOSign = 2;
    private BaseActivity _this;
    // 轮播
    private ImageCycleView mAdView;
    // 消息通知
    private RelativeLayout rl_news;
    private LooperTextView looperview;
    private TextView looperview1;
    /**
     * --------------专家推荐Begin-------------
     */
    private ViewPager mViewPager;//专家推荐布局
    private LinearLayout mLlDot;//指示点
    private List<View> mPagerList = new ArrayList<>();
    private List<User> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    /**
     * ----------------------END---------------
     */
    /**
     * --------------------Begin一部分修改----------------
     */
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private RelativeLayout mToolLayout;
    private RelativeLayout mNoctionLayout;
    private LinearLayout mIndicatorLayout;
    private int ScroolFlag=0;//滑动状态标志：0：展开 1：缩放 2：中间状态


    /**
     * ------------------------END-----------------------
     */

    private View guanzhuzhuanjia, vipwenzhang, mianfeizhuanqu;
    private TextView jingxuan, jingcai, yapan, attention;
    private TextView jingxuan_line, jingcai_line, yapan_line, attention_line;
    private RefreshListView refreshListView;
    private ArticleListAdapter myListAdapter = null;
    private int type = 0;//0精选，1竞彩,2竞猜,3关注

    private LunBoTuList lunboData;
    private ArrayList<Notice> noticelist;
    private ArrayList<Article> articles;
    private boolean isGetting = false;
    private int more = 1;
    boolean toShowRegisterPacket = false;
    Article art;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _this = (BaseActivity) getActivity();
        View view = inflater.inflate(R.layout.main4_fragment_shouye, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        initTuiJian();
        initLunBo();
        initNotice();
        setTitleBack(0);
        initdata(false);
        return view;
    }

    /**
     * 专家推荐布局初始化
     */
    private void initTuiJian() {
        getZJ_Tuijian();
        mInflater = LayoutInflater.from(getActivity());
        //总的页数 = 总数 / 每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
      /*  for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            MyGrideview gridView = (MyGrideview) mInflater.inflate(R.layout.main_gridview, mViewPager, false);
            //GridView gridView =new GridView(getActivity());
            gridView.setAdapter(new ZJTuiJianViewAdapter(getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                }
            });
        }*/
        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }


    public void initView(View view) {
        /******************************************/
        mCollapsingToolbarLayout = view.findViewById(R.id.CollapsingToolbarLayoutView);
        mAppBarLayout = view.findViewById(R.id.AppBarLayout);
        mToolLayout = view.findViewById(R.id.ToolLayout);
        mIndicatorLayout = view.findViewById(R.id.MainHome_IndicatorLayout);
        mNoctionLayout = view.findViewById(R.id.rl_news);
        mAdView = (ImageCycleView) view.findViewById(R.id.ad_view);
        rl_news = (RelativeLayout) view.findViewById(R.id.rl_news);
        looperview = (LooperTextView) view.findViewById(R.id.looperview);
        looperview1 = (TextView) view.findViewById(R.id.looperview1);
        mViewPager = view.findViewById(R.id.Main_ShouYe_TuijianView);
        mLlDot = view.findViewById(R.id.Main_ShouYe_TuijianOvalView);
        looperview1.setFocusable(true);
        looperview1.setSelected(true);
        looperview1.setOnClickListener(this);


        guanzhuzhuanjia = view.findViewById(R.id.guanzhuzhuanjia);
        guanzhuzhuanjia.setOnClickListener(this);
        vipwenzhang = view.findViewById(R.id.vipwenzhang);
        vipwenzhang.setOnClickListener(this);
        mianfeizhuanqu = view.findViewById(R.id.mianfeizhuanqu);
        mianfeizhuanqu.setOnClickListener(this);


        jingxuan = (TextView) view.findViewById(R.id.yesterday);
        jingxuan.setOnClickListener(this);
        jingcai = (TextView) view.findViewById(R.id.today);
        jingcai.setOnClickListener(this);
        yapan = (TextView) view.findViewById(R.id.tomorrow);
        yapan.setOnClickListener(this);
        attention = (TextView) view.findViewById(R.id.Main_Attention);
        attention.setOnClickListener(this);


        jingxuan_line = (TextView) view.findViewById(R.id.yesterday_line);
        jingcai_line = (TextView) view.findViewById(R.id.today_line);
        yapan_line = (TextView) view.findViewById(R.id.tomorrow_line);
        attention_line = (TextView) view.findViewById(R.id.Main_Attention_line);

        refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
        myListAdapter = new ArticleListAdapter(_this, 1, 0);
        refreshListView.setAdapter(myListAdapter);
        refreshListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                if (articles != null && position < articles.size() && position >= 0) {
                    art = articles.get(position);
                    if (art.getArt_type() != 2) {
                        Intent intent = new Intent(_this, ArticleDetailActivity.class);
                        intent.putExtra("rid", art.getRid());
                        startActivity(intent);
                    } else {
                        _this.toGetJC1Result(art.getRid(),art.getPrice(), "dialogToDo");
                    }
                }
            }
        });
        // 添加监听器
        refreshListView.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO 自动生成的方法存根
                requestDataFromServer(false); // 冲服务器中获取刷新的数据
            }

            @Override
            public void onLoadMore() {
                // TODO 自动生成的方法存根
                requestDataFromServer(true);
            }

            private void requestDataFromServer(boolean isLoadingMore) {
                // TODO 自动生成的方法存根
                if (isLoadingMore) {
                    initdata(true);
                } else {
                    initdata(false);
                }

            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (ScroolFlag != 0) {

                    }
                    ScroolFlag = 0;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (ScroolFlag != 1) {

                    }
                    ScroolFlag = 1;
                } else {
                    if(verticalOffset<-100){
                        mToolLayout.setVisibility(View.VISIBLE);
                    }else {
                        mToolLayout.setVisibility(View.GONE);
                    }
                    if (ScroolFlag != 2) {
                        
                    }
                    ScroolFlag = 2;
                }
                Log.e("jqs=",verticalOffset+"==="+appBarLayout.isHovered()+"===="+appBarLayout.getTotalScrollRange());

            }
        });
       /* final ViewTreeObserver treeObserver = mIndicatorLayout.getViewTreeObserver();
        treeObserver.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.e("CollapsingToolbarLayout", "执行了");
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.looperview1:
                Intent intentNT = new Intent(_this, ZhuanJiaDetailActivity.class);
                intentNT.putExtra("uid", noticelist.get(0).getRid());
                startActivity(intentNT);
                break;
            case R.id.guanzhuzhuanjia:
                if (!PreferenceUtil.getBoolean(getActivity(), "hasLogin")) {
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    intentLogin.putExtra("requestName", "mianhome");
                    startActivityForResult(intentLogin, TOSign);
                } else {
                    Intent intentGZ = new Intent(_this, ZhuanJiaFoucedActivity.class);
                    startActivity(intentGZ);
                }
                break;
            case R.id.vipwenzhang:
                Intent intentVIP = new Intent(_this, VipWZActivity.class);
                startActivity(intentVIP);
                break;
            case R.id.mianfeizhuanqu:
                Intent intentMF = new Intent(_this, FreeAreaActivity.class);
                startActivity(intentMF);
                break;
            case R.id.yesterday:
                if (type != 0) {
                    type = 0;
                    setTitleBack(0);
                    initdata(false);
                } else {
                    type = 0;
                }
                break;
            case R.id.today:
                if (type != 1) {
                    type = 1;
                    setTitleBack(1);
                    initdata(false);
                } else {
                    type = 1;
                }
                break;
            case R.id.tomorrow:
                if (type != 2) {
                    type = 2;
                    setTitleBack(2);
                    initdata(false);
                } else {
                    type = 2;
                }
                break;
            case R.id.Main_Attention:
                if (type != 3) {
                    type = 3;
                    setTitleBack(3);
                    initdata(false);
                } else {
                    type = 3;
                }
                break;
        }
    }

    void initdata(boolean isMore) {
        if (!isGetting) {
            isGetting = true;
            if (!isMore) {
                more = 1;
            } else {
                more++;
            }
            myListAdapter.setType(type);
            if (type == 2)
                getFree(isMore);
            else
                getWenZhang(isMore);
        }
    }

    private void getWenZhang(final boolean isMore) {
        ServiceShouYe.getInstance().getJingCaiGuoGuanList(_this, more, type,
                new CallBack<ArticleListBack>() {
                    @Override
                    public void onSuccess(ArticleListBack t) {
                        // TODO 自动生成的方法存根
                        dodata(t, !isMore);
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        if (isMore) {
                            more--;
                        }
                        ToastUtils.showShort(_this, errorMessage.msg);
                        if (myListAdapter != null) {
                            myListAdapter.notifyDataSetChanged();// 重新刷新数据
                        }
                        refreshListView.onRefreshComplete(false);
                        isGetting = false;
                    }
                });
    }

    /**
     * 获取免费
     *
     * @param isMore
     */
    private void getFree(final boolean isMore) {
        ServiceShouYe.getInstance().getFreeList(_this, more,
                new CallBack<ArticleListBack>() {
                    @Override
                    public void onSuccess(ArticleListBack t) {
                        // TODO 自动生成的方法存根
                        dodata(t, !isMore);
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        if (isMore) {
                            more--;
                        }
                        ToastUtils.showShort(_this, errorMessage.msg);
                        if (myListAdapter != null) {
                            myListAdapter.notifyDataSetChanged();// 重新刷新数据
                        }
                        refreshListView.onRefreshComplete(false);
                        isGetting = false;
                    }
                });
    }

    public synchronized void dodata(ArticleListBack t, boolean isRefresh) {
        if (isRefresh) {
            if (articles == null) {
                articles = new ArrayList<Article>();
            } else {
                articles.clear();
            }
            if (myListAdapter != null) {
                myListAdapter.notifyDataSetChanged();// 重新刷新数据
            }
            Log.e("结果=", "" + t.getData().getItems().size());
            if (t.getData() != null && t.getData().getItems() != null
                    && t.getData().getItems().size() > 0) {
                articles.addAll(t.getData().getItems());
            }
            myListAdapter.setDate(articles);
        } else {
            ArrayList<Article> newlists = new ArrayList<>();
            if (t.getData() != null && t.getData().getItems() != null
                    && t.getData().getItems().size() > 0) {
                newlists = t.getData().getItems();
            }
            if (newlists.size() == 0) {
                ToastUtils.showShort(_this, "已无更多数据");
                more--;
            }
            if (articles == null) {
                articles = new ArrayList<Article>();
            }
            articles.addAll(newlists);
        }
        if (myListAdapter != null) {
            myListAdapter.notifyDataSetChanged();// 重新刷新数据
        }
        refreshListView.onRefreshComplete(false);
        isGetting = false;
    }

    public void setTitleBack(int title) {
        jingxuan.setTextColor(getResources().getColor(R.color.white));
        jingxuan_line.setVisibility(View.INVISIBLE);
        jingcai.setTextColor(getResources().getColor(R.color.white));
        jingcai_line.setVisibility(View.INVISIBLE);
        yapan.setTextColor(getResources().getColor(R.color.white));
        yapan_line.setVisibility(View.INVISIBLE);
        attention.setTextColor(getResources().getColor(R.color.white));
        attention_line.setVisibility(View.INVISIBLE);
        if (title == 0) {
            jingxuan.setTextColor(getResources().getColor(R.color.textgreen));
            jingxuan_line.setVisibility(View.VISIBLE);
        } else if (title == 1) {
            jingcai.setTextColor(getResources().getColor(R.color.textgreen));
            jingcai_line.setVisibility(View.VISIBLE);
        } else if (title == 2) {
            yapan.setTextColor(getResources().getColor(R.color.textgreen));
            yapan_line.setVisibility(View.VISIBLE);
        } else if (title == 3) {
            attention.setTextColor(getResources().getColor(R.color.textgreen));
            attention_line.setVisibility(View.VISIBLE);
        }
    }

    private void initLunBo() {
        lunboData = MyDbUtils.getLunBoTuList();
        setAds();
        //if (lunboData == null||lunboData.getSlides()==null||lunboData.getSlides().size() ==0) {
        ServiceShouYe.getInstance().gettLunBoTuList(_this, new CallBack<LunBoTuListBack>() {
            @Override
            public void onSuccess(LunBoTuListBack t) {
                // TODO 自动生成的方法存根
                // 更新过跳转就要显示界面
                lunboData = t.getData();
                setAds();
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                // TODO 自动生成的方法存根
            }
        });
        //}

    }

    public void setAds() {
        if (lunboData != null) {
            ArrayList<LunBoTu> slides = lunboData.getSlides();
            ArrayList<String> mImageUrl = new ArrayList<String>();
            ArrayList<String> mImageTitle = new ArrayList<String>();
            if (slides != null && slides.size() != 0) {
                mAdView.setVisibility(View.VISIBLE);
                for (int i = 0; i < slides.size(); i++) {
                    mImageUrl.add(slides.get(i).getImg_url());
                    mImageTitle.add(slides.get(i).getInfo());
                    Log.e("测试轮播图= ", slides.get(i).toString());
                }
                mAdView.setImageResources(mImageUrl, mImageTitle,
                        mAdCycleViewListener, 2, false);
            } else {
                mAdView.setVisibility(View.GONE);
            }
        }
    }

    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
        @Override
        public void onImageClick(final int position, View imageView) {
            /*LunBoTu lunbo = lunboData.getSlides().get(position);
            if (!lunbo.getUrl().equals("")) {
				Intent intent = new Intent(getActivity(),WebActivity.class);// 跳转html活动界面
				intent.putExtra("url", lunbo.getUrl());
				startActivity(intent);
			}*/
        }
    };

    private void initNotice() {
        ServiceShouYe.getInstance().getNoticeList(_this, new CallBack<NoticeBack>() {
            @Override
            public void onSuccess(NoticeBack t) {
                // TODO 自动生成的方法存根
                // 更新过跳转就要显示界面
                noticelist = t.getData();
                setNotice();
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                // TODO 自动生成的方法存根
                rl_news.setVisibility(View.GONE);
            }
        });
    }

    private void setNotice() {
        if (noticelist != null && noticelist.size() > 0) {// 显示公告
            rl_news.setVisibility(View.VISIBLE);
            if (noticelist.size() == 1) {
                looperview1.setText(noticelist.get(0).getContent());
                looperview1.setVisibility(View.VISIBLE);
                looperview.setVisibility(View.GONE);
            } else {
                ArrayList<String> tipList = new ArrayList<String>();
                for (int i = 0; i < noticelist.size(); i++) {
                    tipList.add(noticelist.get(i).getContent());
                }
                looperview.setTipList(tipList);
                looperview1.setVisibility(View.GONE);
                looperview.setVisibility(View.VISIBLE);
                looperview.tv_tip_out.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        for (int i = 0; i < noticelist.size(); i++) {
                            if (looperview.tv_tip_out.getText().toString()
                                    .equals(noticelist.get(i).getContent())) {
                                Intent intentNT = new Intent(_this, ZhuanJiaDetailActivity.class);
                                intentNT.putExtra("uid", noticelist.get(i).getRid());
                                startActivity(intentNT);
                                break;
                            }
                        }
                    }
                });
                looperview.tv_tip_in.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        for (int i = 0; i < noticelist.size(); i++) {
                            if (looperview.tv_tip_in.getText().toString()
                                    .equals(noticelist.get(i).getContent())) {
                                Intent intentNT = new Intent(_this, ZhuanJiaDetailActivity.class);
                                intentNT.putExtra("uid", noticelist.get(i).getRid());
                                startActivity(intentNT);
                                break;
                            }

                        }
                    }
                });
            }
        } else {
            rl_news.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case TOSign:
                    Intent intentGZ = new Intent(_this, ZhuanJiaFoucedActivity.class);
                    startActivity(intentGZ);
                    break;
                case LOGIN_PAY:
                    Intent intentPay = new Intent(_this, PayActivity.class);
                    intentPay.putExtra("rid", art.getRid());
                    intentPay.putExtra("name", art.getNickname());
                    intentPay.putExtra("prize", art.getPrice());
                    startActivity(intentPay);
                    break;
            }
        }
    }

    public void onEventMainThread(String event) {
        if (!TextUtils.isEmpty(event)) {
            if ("dialogToDo".equals(event)) {
                dialogToDo();
            }
            if ("showRegisterPacket".equals(event)) {
                toShowRegisterPacket = true;
            }
            if ("showRegisterPacketNo".equals(event)) {
                toShowRegisterPacket = false;
            }
        }
    }

    public void dialogToDo() {
        if (!PreferenceUtil.getBoolean(_this, "hasLogin")) {
            Intent intentLogin = new Intent(_this, LoginActivity.class);
            intentLogin.putExtra("requestName", "intentLogin");
            startActivityForResult(intentLogin, LOGIN_PAY);
        } else {
            Intent intentPay = new Intent(_this, PayActivity.class);
            intentPay.putExtra("rid", art.getRid());
            intentPay.putExtra("name", art.getNickname());
            intentPay.putExtra("prize", art.getPrice());
            startActivity(intentPay);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (toShowRegisterPacket && !hidden) {//注册和登录告诉你要弹
            if (PreferenceUtil.getBoolean(_this, "hasLogin") && !PreferenceUtil.getBoolean(_this, "showRegisterPacket")) {//没弹过
                showPPW();
            }
        }
    }

    public void showPPW() {//弹起来了，就不要再去弹了
        PreferenceUtil.putBoolean(_this, "showRegisterPacket", true);
        toShowRegisterPacket = false;
        dialog();
    }

    public void dialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(_this).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.newregister);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.touming);


        View close = window.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 获取专家推荐列表
     */
    public void getZJ_Tuijian() {
        for (int i = 0; i < 21; i++) {
            User user = new User();
            user.setAvatar("");
            user.setNickname("" + i);
            mDatas.add(user);
        }
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        for (int i = 0; i < pageCount; i++) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(params);
            if (i == 0)
                // 默认显示第一页
                image.setImageResource(R.drawable.cicle_banner_dian_focus);
            else
                image.setImageResource(R.drawable.cicle_banner_dian_blur);
            mLlDot.addView(image);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                ((ImageView) mLlDot.getChildAt(curIndex)).setImageResource(R.drawable.cicle_banner_dian_blur);
                // 圆点选中
                ((ImageView) mLlDot.getChildAt(position)).setImageResource(R.drawable.cicle_banner_dian_focus);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

}
