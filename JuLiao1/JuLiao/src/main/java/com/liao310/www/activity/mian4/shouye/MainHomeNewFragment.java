package com.liao310.www.activity.mian4.shouye;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.WebActivity;
import com.liao310.www.activity.mian4.adapter.ExpandableAdapter;
import com.liao310.www.activity.mian4.adapter.ViewPagerAdapter;
import com.liao310.www.activity.mian4.adapter.ZJTuiJianViewAdapter;
import com.liao310.www.activity.mian4.personcenter.ReChargeActivity;
import com.liao310.www.activity.mian4.shouye.zhuanjia.ZhuanJiaFoucedActivity;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.shouye.LunBoTuListNewBack;
import com.liao310.www.domain.shouye.LunBoTuNew;
import com.liao310.www.domain.shouye.Notice;
import com.liao310.www.domain.shouye.NoticeBack;
import com.liao310.www.domain.shouye.ZJRecommend;
import com.liao310.www.domain.shouye.ZJRecommendBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.AttentionListBack;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.ServiceShouYe;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.BaseViewHolder;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.interfaces.OnLoadMoreClickListener;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.interfaces.OnRecycleItemClickListener;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.utils.UiUtils;
import com.liao310.www.widget.Dialog_Hint;
import com.liao310.www.widget.ImageCycleView;
import com.liao310.www.widget.ImageCycleView.ImageCycleViewListener;
import com.liao310.www.widget.LooperTextView;
import com.liao310.www.widget.MyExpandableListView;
import com.liao310.www.widget.MyGrideview;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

@SuppressLint("NewApi")
public class MainHomeNewFragment extends Fragment implements OnClickListener {
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
    private List<ZJRecommend> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    private ZJTuiJianViewAdapter mTuiJianViewAdapter;
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
    private RelativeLayout mRecommendLayout;
    private MyExpandableListView mExpandableListView;
    private int ScroolFlag = 0;//滑动状态标志：0：展开 1：缩放 2：中间状态

    private String[] groups = {"精选", "竞彩"};
    private Map<String, List<ZhuanJia>> children;
    private ExpandableAdapter mAttentionAdapter;
    /**
     * ------------------------END-----------------------
     */

    private View guanzhuzhuanjia, vipwenzhang, mianfeizhuanqu;
    private TextView jingxuan, jingcai, yapan, attention;
    private TextView jingxuan_line, jingcai_line, yapan_line, attention_line;
    private RecyclerView mRecyclerView;
    //private ArticleListAdapter myListAdapter = null;
    private MainHomeNewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private int type = 0;//0精选，1竞彩,2免费,3关注

    private List<LunBoTuNew> lunboData;
    private ArrayList<Notice> noticelist;
    private ArrayList<Article> articles;
    private boolean isHavTopImg = true;//是否有顶部
    private boolean isGetting = false;
    private int more = 1;
    boolean toShowRegisterPacket = false;
    private int payType = 0;//支付方式
    Article art;

    private MainHandler mMainHandler;
    private boolean isLunGone=false;//轮播图是否隐藏
    private boolean isTJGone=false;//专家推荐是否隐藏

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _this = (BaseActivity) getActivity();
        View view = inflater.inflate(R.layout.main4_fragment_shouye_new, container, false);
        EventBus.getDefault().register(this);
        initView(view);
        getZJ_Tuijian();
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
        mInflater = LayoutInflater.from(getActivity());
        //总的页数 = 总数 / 每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        int height = 0;
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            MyGrideview gridView = (MyGrideview) mInflater.inflate(R.layout.main_gridview, mViewPager, false);
            mTuiJianViewAdapter = new ZJTuiJianViewAdapter(getActivity(), mDatas, i, pageSize);
            gridView.setAdapter(mTuiJianViewAdapter);
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Intent intent = new Intent(_this, ZhuanJiaDetailActivity.class);
                    intent.putExtra("uid", mDatas.get(pos).getId());
                    _this.startActivity(intent);
                }
            });
            height = setGridViewHeight(gridView, height);
        }
        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //动态设置高度
        ViewGroup.LayoutParams PagerParams = mViewPager.getLayoutParams();
        PagerParams.height = height + 10;
        mViewPager.setLayoutParams(PagerParams);
        //设置圆点
        if (pageCount > 1)
            setOvalLayout();
    }

    public int setGridViewHeight(GridView gridview, int height) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return height;
        }
        // 固定列宽，有多少列
        int numColumns = 4;
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        if (height < totalHeight)
            return totalHeight;
        else
            return height;

    }


    public void initView(View view) {
        /******************************************/
        mCollapsingToolbarLayout = view.findViewById(R.id.CollapsingToolbarLayoutView);
        mAppBarLayout = view.findViewById(R.id.AppBarLayout);
        mToolLayout = view.findViewById(R.id.ToolLayout);
        mExpandableListView = view.findViewById(R.id.expandableListView);
        mRecommendLayout = view.findViewById(R.id.Main_ShouYe_RecommendLayout);
        mAdView =  view.findViewById(R.id.ad_view);
        rl_news =  view.findViewById(R.id.rl_news);
        looperview =  view.findViewById(R.id.looperview);
        looperview1 = view.findViewById(R.id.looperview1);
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

        /**-----------------------------------**/
        mRecyclerView = view.findViewById(R.id.rl_listview);
        mRefreshLayout = view.findViewById(R.id.Main_SwipeRefreshLayout);

        mMainHandler=new MainHandler(this);
        //对recyclerview的常规配置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new MainHomeNewAdapter(getActivity(), R.layout.myfavourite_item, articles);
        mRecyclerView.setAdapter(mAdapter);

        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initdata(false);
            }
        });
        //上拉加载
        mAdapter.setLoadMoreListener(new OnLoadMoreClickListener() {
            @Override
            public void onRefresh() {
                initdata(true);
            }
        });
        mAdapter.setItemClickListener(new OnRecycleItemClickListener<Article>() {
            @Override
            public void onClick(BaseViewHolder holder, Article o, int position) {
                if (articles != null && position < articles.size() && position >= 0) {
                    art = articles.get(position);
                    if (art.getArt_type() != 2) {
                        _this.getArticledetail(art.getRid(), art.getPrice(), "dialogToDo");
                       /* if(art.isShow()) {
                            Intent intent = new Intent(_this, ArticleDetailActivity.class);
                            intent.putExtra("rid", art.getRid());
                            startActivity(intent);
                        }else {
                            _this.dialog("", art.getPrice() + "", "取消", true, "去支付", "dialogToDo");
                        }*/
                    } else {
                        if (!PreferenceUtil.getBoolean(_this, "hasLogin")) {
                            Intent intentLogin = new Intent(_this, LoginActivity.class);
                            intentLogin.putExtra("requestName", "intentLogin");
                            startActivityForResult(intentLogin, LOGIN_PAY);
                        } else {
                            _this.toGetJC1Result(art.getRid(), art.getPrice(), "dialogToDo");
                        }
                    }
                }
            }
        });

        //设置子条目的点击监听
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //这里return true的话子列表不会展开  return false才展开
                if (mAttentionAdapter != null) {
                    ZhuanJia zhuanJia = (ZhuanJia) mAttentionAdapter.getChild(groupPosition, childPosition);
                    if (zhuanJia != null) {
                        Intent intent = new Intent(_this, ZhuanJiaDetailActivity.class);
                        intent.putExtra("uid", zhuanJia.getUid());
                        _this.startActivity(intent);
                    }
                }
                return false;
            }
        });
        /*//设置父条目的点击监听
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                return false;
            }
        });*/
        /**--------------------------------------**/

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
                    if (isHavTopImg) {
                        if (verticalOffset < -100) {
                            mToolLayout.setVisibility(View.VISIBLE);
                        } else {
                            mToolLayout.setVisibility(View.GONE);
                        }
                    }
                    if (ScroolFlag != 2) {

                    }
                    ScroolFlag = 2;
                }

            }
        });
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
                mExpandableListView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                if (type != 0) {
                    type = 0;
                    setTitleBack(0);
                    initdata(false);
                } else {
                    type = 0;
                }
                break;
            case R.id.today:
                mExpandableListView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                if (type != 1) {
                    type = 1;
                    setTitleBack(1);
                    initdata(false);
                } else {
                    type = 1;
                }
                break;
            case R.id.tomorrow:
                mExpandableListView.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                if (type != 2) {
                    type = 2;
                    setTitleBack(2);
                    initdata(false);
                } else {
                    type = 2;
                }
                break;
            case R.id.Main_Attention:
                mExpandableListView.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
                if (type != 3) {
                    type = 3;
                    setTitleBack(3);
                    //initdata(false);
                    initAttention();
                } else {
                    type = 3;
                }
                break;
        }
    }

    /**
     * 获取关注列表
     */
    private void initAttention() {
        _this.showProgress();
        ServiceShouYe.getInstance().getZJAttentionList(getActivity(), new CallBack<AttentionListBack>() {
            @Override
            public void onSuccess(AttentionListBack attentionListBack) {
                _this.dismissProgress();
                children = new HashMap<>();
                if (attentionListBack != null && attentionListBack.getData() != null) {
                    if (attentionListBack.getData().getExpert_items() != null && attentionListBack.getData().getExpert_items().getJx() != null)
                        children.put(groups[0], attentionListBack.getData().getExpert_items().getJx());
                    if (attentionListBack.getData().getExpert_items() != null && attentionListBack.getData().getExpert_items().getJc() != null)
                        children.put(groups[1], attentionListBack.getData().getExpert_items().getJc());
                }
                //关注
                mAttentionAdapter = new ExpandableAdapter(getActivity(), groups, children);
                mExpandableListView.setAdapter(mAttentionAdapter);
                //遍历所有group,将所有项设置成默认展开
                int groupCount = mExpandableListView.getCount();
                for (int i = 0; i < groupCount; i++) {
                    mExpandableListView.expandGroup(i);
                }
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                _this.dismissProgress();
            }
        });
    }

    void initdata(boolean isMore) {
        if (!isGetting) {
            isGetting = true;
            if (!isMore) {
                more = 1;
            } else {
                more++;
            }
            mAdapter.setType(type);
            if (type == 2)
                getFree(isMore);
            else
                getWenZhang(isMore);
        }
    }

    private void getWenZhang(final boolean isMore) {
        _this.showProgress();
        ServiceShouYe.getInstance().getJingCaiGuoGuanList(_this, more, type,
                new CallBack<ArticleListBack>() {
                    @Override
                    public void onSuccess(ArticleListBack t) {
                        // TODO 自动生成的方法存根
                        _this.dismissProgress();
                        dodata(t, !isMore);
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        _this.dismissProgress();
                        if (isMore) {
                            more--;
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            mAdapter.setOnRefrash(articles);
                        }
                        ToastUtils.showShort(_this, errorMessage.msg);
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
        _this.showProgress();
        ServiceShouYe.getInstance().getFreeList(_this, more,
                new CallBack<ArticleListBack>() {
                    @Override
                    public void onSuccess(ArticleListBack t) {
                        // TODO 自动生成的方法存根
                        _this.dismissProgress();
                        dodata(t, !isMore);
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        _this.dismissProgress();
                        if (isMore) {
                            more--;
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            mAdapter.setOnRefrash(articles);
                        }
                        ToastUtils.showShort(_this, errorMessage.msg);
                        isGetting = false;
                    }
                });
    }

    public synchronized void dodata(ArticleListBack t, boolean isRefresh) {
        if (isRefresh) {
            if (articles == null) {
                articles = new ArrayList<>();
            } else {
                articles.clear();
            }
            if (t.getData() != null && t.getData().getItems() != null
                    && t.getData().getItems().size() > 0) {
                articles.addAll(t.getData().getItems());
            }
            mRefreshLayout.setRefreshing(false);
            mAdapter.setData(articles);
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
                articles = new ArrayList<>();
            }
            articles.addAll(newlists);
            mAdapter.setOnRefrash(articles);
        }

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
        //lunboData = MyDbUtils.getLunBoTuList();
        //setAds();
        ServiceShouYe.getInstance().gettLunBoTuListNew(_this, new CallBack<LunBoTuListNewBack>() {
            @Override
            public void onSuccess(LunBoTuListNewBack t) {
                // TODO 自动生成的方法存根
                // 更新过跳转就要显示界面
                lunboData = t.getData();
                setAds();
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                // TODO 自动生成的方法存根
                setAds();
            }
        });
    }

    public void setAds() {
        if (lunboData != null && lunboData.size() != 0) {
            mAdView.setVisibility(View.VISIBLE);
            ArrayList<String> mImageUrl = new ArrayList<String>();
            ArrayList<String> mImageTitle = new ArrayList<String>();
            for (int i = 0; i < lunboData.size(); i++) {
                mImageUrl.add(lunboData.get(i).getPicture());
                mImageTitle.add(lunboData.get(i).getTitle());
            }
            mAdView.setImageResources(mImageUrl, mImageTitle,
                    mAdCycleViewListener, 2, false);
        } else {
            mAdView.setVisibility(View.GONE);
            mToolLayout.setVisibility(View.VISIBLE);
            isHavTopImg = false;

            isLunGone=true;
            mMainHandler.sendEmptyMessage(1);
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mRecommendLayout.getLayoutParams();
            params.setMargins(0, UiUtils.dip2px(70),0,0);
            mRecommendLayout.setLayoutParams(params);

        }

    }

    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
        @Override
        public void onImageClick(final int position, View imageView) {
            LunBoTuNew lunbo = lunboData.get(position);
            if (lunbo != null) {
                switch (lunbo.getType()) {
                    case 2:
                        //不跳转
                        break;
                    case 3:
                        //外链跳转
                        Intent intent1 = new Intent(getActivity(), WebActivity.class);// 跳转html活动界面
                        intent1.putExtra("url", lunbo.getJump_url());
                        intent1.putExtra("titleStr", lunbo.getTitle());
                        startActivity(intent1);
                        break;
                    case 4:
                        //跳转专家
                        Intent intent2 = new Intent(_this, ZhuanJiaDetailActivity.class);
                        intent2.putExtra("uid", lunbo.getExp_id() + "");
                        _this.startActivity(intent2);
                        break;
                    case 5:
                        //轮播新闻
                        break;
                    case 6:
                        //轮播-跳转充值
                        startActivity(new Intent(getActivity(), ReChargeActivity.class));
                        break;
                }
            }
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
                looperview.tv_tip_out.setOnClickListener(new OnClickListener() {

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
                looperview.tv_tip_in.setOnClickListener(new OnClickListener() {

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
                    /*Intent intentPay = new Intent(_this, PayActivity.class);
                    intentPay.putExtra("rid", art.getRid());
                    intentPay.putExtra("name", art.getNickname());
                    intentPay.putExtra("prize", art.getPrice());
                    startActivity(intentPay);*/
                    break;
            }
        }
    }

    public void onEventMainThread(String event) {
        if (!TextUtils.isEmpty(event)) {
            if ("dialogToDo".equals(event)) {
                dialogToDo();
            } else if ("toPayForCard".equals(event)) {
                toPayForCard();
            } else if ("showRegisterPacket".equals(event)) {
                toShowRegisterPacket = true;
            } else if ("showRegisterPacketNo".equals(event)) {
                toShowRegisterPacket = false;
            }
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
        close.setOnClickListener(new OnClickListener() {
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
        ServiceShouYe.getInstance().getZJRecommend(getActivity(), new CallBack<ZJRecommendBack>() {
            @Override
            public void onSuccess(ZJRecommendBack zjRecommendBack) {
                if (zjRecommendBack != null && zjRecommendBack.getData() != null && zjRecommendBack.getData().size() != 0) {
                    mRecommendLayout.setVisibility(View.VISIBLE);
                    mDatas.clear();
                    mDatas.addAll(zjRecommendBack.getData());
                    initTuiJian();
                } else {
                    isTJGone=true;
                    mMainHandler.sendEmptyMessage(1);
                    mRecommendLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                isTJGone=true;
                mMainHandler.sendEmptyMessage(1);
                mRecommendLayout.setVisibility(View.GONE);
            }
        });
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


    public void dialogToDo() {
        ServicePay.getInstance().pay(_this, art.getRid(), payType,
                new CallBack<PayBack>() {
                    @Override
                    public void onSuccess(PayBack t) {
                        // TODO 自动生成的方法存根
                        final Dialog_Hint dialog_hint = new Dialog_Hint(_this, "购买成功", false);
                        dialog_hint.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog_hint.dismiss();
                                if (art.getArt_type() != 2) {
                                    Intent intent = new Intent(_this, ArticleDetailActivity.class);
                                    intent.putExtra("rid", art.getRid());
                                    startActivity(intent);
                                } else {
                                    _this.toGetJC1Result(art.getRid(), art.getPrice(), "dialogToDo");
                                }
                            }
                        }, 1000);
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        new Dialog_Hint(_this, "" + errorMessage.msg, false).showShort();
                    }
                });
    }

    public void toPayForCard() {
        payType = 1;
        _this.dialog("赠送卡支付", "您确认使用赠送卡支付吗？", "取消", true, "确认", "dialogToDo");
    }


    public static class MainHandler extends Handler{
        WeakReference<MainHomeNewFragment> mWeakReference;

        public MainHandler(MainHomeNewFragment mainActivity){
            mWeakReference=new WeakReference<>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            MainHomeNewFragment mainActivity = mWeakReference.get();
            if(mainActivity!=null){
                if(msg.what==1){
                    if(mainActivity.isLunGone && mainActivity.isTJGone){
                        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mainActivity.rl_news.getLayoutParams();
                        params.setMargins(0, UiUtils.dip2px(70),0,0);
                        mainActivity.rl_news.setLayoutParams(params);
                    }

                }
            }
        }
    }
}
