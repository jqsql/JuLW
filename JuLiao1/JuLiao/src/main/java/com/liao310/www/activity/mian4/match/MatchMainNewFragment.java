package com.liao310.www.activity.mian4.match;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaBuyAdapter;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaRankingAdapter;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaRefreshListAdapter;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.LeiTai.RankingBean;
import com.liao310.www.domain.LeiTai.RankingListBack;
import com.liao310.www.domain.LeiTai.ZhuanJBuyBean;
import com.liao310.www.domain.LeiTai.ZhuanJBuyListBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceLeiTai;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * \
 * 原比赛板块更为擂台板块
 */
@SuppressLint("NewApi")
public class MatchMainNewFragment extends Fragment
        implements OnClickListener {
    private BaseActivity _this;
    private TextView ya, line, jing;
    private TextView zuqiu, lanqiu;
    private TextView yesterday, today, tomorrow;
    private TextView yesterday_line, today_line, tomorrow_line;//总榜、七日单场、七日大小球
    //private ListView listview;
    private ZhuanJiaRankingAdapter myNoRefreshListAdapter = null;
    private ZhuanJiaBuyAdapter mJiaBuyAdapter = null;
    private RefreshListView refreshListView;
    private ZhuanJiaRefreshListAdapter myListAdapter = null;

    private int title1 = 0;//0亚
    private String title2 = "paihang";
    private int title3 = 0;

    //private ArrayList<ZhuanJia> matchlist;
    private ArrayList<RankingBean> zhuanjialist;//专家排行list
    private ArrayList<ZhuanJBuyBean> mJBuyBeen;//擂台已购list
    private boolean isGetting = false;
    private int more = 1;
    private GetMainActivity mGetMainActivity;
    public interface GetMainActivity{
        MainActivity getMainActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mGetMainActivity= (GetMainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _this = (BaseActivity) getActivity();
        View view = inflater.inflate(R.layout.main4_fragment_match, container, false);
        initView(view);
        initTitle();
        initdata(false);
        return view;

    }

    public void initView(View view) {
        ya = (TextView) view.findViewById(R.id.ya);
        ya.setOnClickListener(this);
        line = (TextView) view.findViewById(R.id.line);
        jing = (TextView) view.findViewById(R.id.jing);
        jing.setOnClickListener(this);

        zuqiu = (TextView) view.findViewById(R.id.zuqiu);
        zuqiu.setOnClickListener(this);
        lanqiu = (TextView) view.findViewById(R.id.lanqiu);
        lanqiu.setOnClickListener(this);
        initeChildView(view);
    }

    public void initeChildView(View view) {
        yesterday = (TextView) view.findViewById(R.id.yesterday);
        yesterday.setOnClickListener(this);
        today = (TextView) view.findViewById(R.id.today);
        today.setOnClickListener(this);
        tomorrow = (TextView) view.findViewById(R.id.tomorrow);
        tomorrow.setOnClickListener(this);
        yesterday_line = (TextView) view.findViewById(R.id.yesterday_line);
        today_line = (TextView) view.findViewById(R.id.today_line);
        tomorrow_line = (TextView) view.findViewById(R.id.tomorrow_line);

       // listview = view.findViewById(R.id.rl_listview2);
        refreshListView = view.findViewById(R.id.rl_listview);
        myNoRefreshListAdapter = new ZhuanJiaRankingAdapter(_this);

        refreshListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 自动生成的方法存根
                if (!"paihang".equals(title2) && title3 == 0) {
                    if (mJBuyBeen != null && position < mJBuyBeen.size() && position >= 0) {
                        mGetMainActivity.getMainActivity().toGetJC1Result(mJBuyBeen.get(position).getId()+"","0","");
                    }
                } else {
                    if (zhuanjialist != null && position < zhuanjialist.size() && position >= 0) {
                        Intent intent = new Intent(_this, ZhuanJiaDetailActivity.class);
                        intent.putExtra("uid", zhuanjialist.get(position).getId()+"");
                        startActivity(intent);
                    }

                }
            }
        });

        // 添加监听器
        refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

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
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ya:
                if (title1 != 0) {//不是亚盘，换成亚盘;
                    title2 = "paihang";
                    setMainTitleBackBase(0);//换成亚盘
                    setMainTitleBack(0);//换成足球
                    setTitleBack(1);//换成今天
                    title1 = 0;
                    title3 = 1;
                    initdata(false);
                } else {
                    title1 = 0;
                }
                break;
            case R.id.jing:
                if (title1 != 1) {//不是竞彩，换成竞彩;
                    title2 = "paihang";
                    setMainTitleBackBase(1);//换成竞彩
                    setMainTitleBack(0);//换成足球
                    setTitleBack(1);//换成今天
                    title1 = 1;
                    title3 = 1;
                    initdata(false);
                } else {
                    title1 = 1;
                }
                break;
            case R.id.zuqiu:
                if (!"paihang".equals(title2)) {//不是足球，换成足球;
                    title2 = "paihang";
                    setMainTitleBack(0);//换成足球
                    setTitleBack(0);//换成今天
                    title3 = 0;
                    initdata(false);
                } else {
                    title2 = "paihang";
                }
                break;
            case R.id.lanqiu:
                if (!"leitai".equals(title2)) {//不是篮球，换成篮球;
                    title2 = "leitai";
                    setMainTitleBack(1);//换成篮球
                    setTitleBack(0);//换成今天
                    title3 = 0;
                    initdata(false);
                } else {
                    title2 = "leitai";
                }
                break;
            case R.id.yesterday:
                if (title3 != 0) {//不是昨天，换成昨天;
                    setTitleBack(0);//换成昨天
                    title3 = 0;
                    initdata(false);
                } else {
                    title3 = 0;
                }
                break;
            case R.id.today:
                if (title3 != 1) {//不是今天，换成今天;
                    setTitleBack(1);//换成今天
                    title3 = 1;
                    initdata(false);
                } else {
                    title3 = 1;
                }
                break;
            case R.id.tomorrow:
                if (title3 != 2) {//不是明天，换成明天;
                    setTitleBack(2);//换成明天
                    title3 = 2;
                    initdata(false);
                } else {
                    title3 = 2;
                }
                break;
        }
    }

    /**
     * 初始化title名称
     */
    private void initTitle() {
        if (title2.equals("paihang")) {
            //排行
            yesterday.setText("总榜");
            today.setText("7日单场");
            tomorrow.setText("七日大小球");
            tomorrow.setVisibility(View.VISIBLE);
        } else {
            //擂台
            yesterday.setText("已购");
            today.setText("关注");
            tomorrow.setVisibility(View.GONE);
            tomorrow_line.setVisibility(View.GONE);
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
            if ("paihang".equals(title2)) {
                //排行接口
                if (myNoRefreshListAdapter != null)
                    myNoRefreshListAdapter.setType(title3);
                if (title3 == 0) {
                    getAllRanking("rank", isMore);
                } else if (title3 == 1) {
                    getAllRanking("dan", isMore);
                } else {
                    getAllRanking("dxq", isMore);
                }
            } else {
                //擂台接口
                if (title3 == 0) {
                    getBuyRanking(isMore);
                } else if (title3 == 1) {
                    if (myNoRefreshListAdapter != null)
                        myNoRefreshListAdapter.setType(4);
                    getAllRanking("attention", isMore);
                }
            }
        }
    }

    /**
     * 获取总榜列表
     */
    private void getAllRanking(String type, final boolean isMore) {
        _this.showProgress();
        ServiceLeiTai.getInstance().getPHRanking(getActivity(), type, more, new CallBack<RankingListBack>() {
            @Override
            public void onSuccess(RankingListBack rankingListBack) {
                // TODO 自动生成的方法存根
                _this.dismissProgress();
                RankDoData(rankingListBack, !isMore);
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                // TODO 自动生成的方法存根
                _this.dismissProgress();
                if (isMore) {
                    more--;
                }
                ToastUtils.showShort(_this, errorMessage.msg);
                if (myNoRefreshListAdapter != null) {
                    myNoRefreshListAdapter.notifyDataSetChanged();// 重新刷新数据
                }
                isGetting = false;
            }
        });
    }

    /**
     * 获取已购列表
     */
    private void getBuyRanking(final boolean isMore) {
        _this.showProgress();
        ServiceLeiTai.getInstance().getPHBuyRanking(getActivity(), more, new CallBack<ZhuanJBuyListBack>() {
            @Override
            public void onSuccess(ZhuanJBuyListBack rankingListBack) {
                // TODO 自动生成的方法存根
                _this.dismissProgress();
                RankDoDataBuy(rankingListBack, !isMore);
            }

            @Override
            public void onFailure(ErrorMsg errorMessage) {
                // TODO 自动生成的方法存根
                _this.dismissProgress();
                if (isMore) {
                    more--;
                }
                ToastUtils.showShort(_this, errorMessage.msg);
                if (myNoRefreshListAdapter != null) {
                    myNoRefreshListAdapter.notifyDataSetChanged();// 重新刷新数据
                }
                isGetting = false;
            }
        });
    }

    /**
     * 没有刷新的数据处理
     *
     * @param t
     */
    public synchronized void RankDoData(RankingListBack t, boolean isRefresh) {
        if (myNoRefreshListAdapter == null) {
            myNoRefreshListAdapter = new ZhuanJiaRankingAdapter(_this);
        }
        refreshListView.setAdapter(myNoRefreshListAdapter);
        if (isRefresh) {
            if (zhuanjialist == null) {
                zhuanjialist = new ArrayList<>();
            } else {
                zhuanjialist.clear();
            }
            if (t.getData() != null && t.getData() != null && t.getData().size() > 0) {
                zhuanjialist.addAll(t.getData());
                myNoRefreshListAdapter.setDate(zhuanjialist);
            }
        } else {
            List<RankingBean> newlists = new ArrayList<>();
            if (t.getData() != null && t.getData() != null
                    && t.getData().size() > 0) {
                newlists = t.getData();
            }
            if (newlists.size() == 0) {
                ToastUtils.showShort(_this, "已无更多数据");
                more--;
            }
            if (zhuanjialist == null) {
                zhuanjialist = new ArrayList<>();
            }
            zhuanjialist.addAll(newlists);
            myNoRefreshListAdapter.setDate(zhuanjialist);
        }
        myNoRefreshListAdapter.notifyDataSetChanged();// 重新刷新数据
        refreshListView.onRefreshComplete(false);
        isGetting = false;
    }

    /**
     * 没有刷新的数据处理
     *
     * @param t
     */
    public synchronized void RankDoDataBuy(ZhuanJBuyListBack t, boolean isRefresh) {
        if (mJiaBuyAdapter == null) {
            mJiaBuyAdapter = new ZhuanJiaBuyAdapter(_this);
        }
        refreshListView.setAdapter(mJiaBuyAdapter);
        if (isRefresh) {
            if (mJBuyBeen == null) {
                mJBuyBeen = new ArrayList<>();
            } else {
                mJBuyBeen.clear();
            }
            if (t.getData() != null && t.getData() != null && t.getData().size() > 0) {
                mJBuyBeen.addAll(t.getData());
                mJiaBuyAdapter.setDate(mJBuyBeen);
            }
        } else {
            List<ZhuanJBuyBean> newlists = new ArrayList<>();
            if (t.getData() != null && t.getData() != null
                    && t.getData().size() > 0) {
                newlists = t.getData();
            }
            if (newlists.size() == 0) {
                ToastUtils.showShort(_this, "已无更多数据");
                more--;
            }
            if (mJBuyBeen == null) {
                mJBuyBeen = new ArrayList<>();
            }
            mJBuyBeen.addAll(newlists);
            mJiaBuyAdapter.setDate(mJBuyBeen);
        }
        mJiaBuyAdapter.notifyDataSetChanged();// 重新刷新数据
        refreshListView.onRefreshComplete(false);
        isGetting = false;
    }

    public void setTitleBack(int title) {
        yesterday.setTextColor(getResources().getColor(R.color.white));
        yesterday_line.setVisibility(View.INVISIBLE);
        today.setTextColor(getResources().getColor(R.color.white));
        today_line.setVisibility(View.INVISIBLE);
        tomorrow.setTextColor(getResources().getColor(R.color.white));
        tomorrow_line.setVisibility(View.INVISIBLE);
        if (title == 0) {
            yesterday.setTextColor(getResources().getColor(R.color.textgreen));
            yesterday_line.setVisibility(View.VISIBLE);
        } else if (title == 1) {
            today.setTextColor(getResources().getColor(R.color.textgreen));
            today_line.setVisibility(View.VISIBLE);
        } else if (title == 2) {
            tomorrow.setTextColor(getResources().getColor(R.color.textgreen));
            tomorrow_line.setVisibility(View.VISIBLE);
        }
        initTitle();
    }

    public void setMainTitleBack(int choose) {
        int rightZQ = zuqiu.getPaddingRight();
        int topZQ = zuqiu.getPaddingTop();
        int bottomZQ = zuqiu.getPaddingBottom();
        int leftZQ = zuqiu.getPaddingLeft();
        int rightLQ = lanqiu.getPaddingRight();
        int topLQ = lanqiu.getPaddingTop();
        int bottomLQ = lanqiu.getPaddingBottom();
        int leftLQ = lanqiu.getPaddingLeft();

        zuqiu.setTextColor(getResources().getColor(R.color.textgreen));
        zuqiu.setBackground(null);
        zuqiu.setPadding(leftZQ, topZQ, rightZQ, bottomZQ);
        lanqiu.setTextColor(getResources().getColor(R.color.textgreen));
        lanqiu.setBackground(null);
        lanqiu.setPadding(leftLQ, topLQ, rightLQ, bottomLQ);
        if (choose == 0) {//足球
            zuqiu.setTextColor(getResources().getColor(R.color.black));
            zuqiu.setBackgroundResource(R.drawable.corner13linenone_whitefull);
            zuqiu.setPadding(leftZQ, topZQ, rightZQ, bottomZQ);
        }
        if (choose == 1) {//篮球
            lanqiu.setTextColor(getResources().getColor(R.color.black));
            lanqiu.setBackgroundResource(R.drawable.corner24linenone_whitefull);
            lanqiu.setPadding(leftLQ, topLQ, rightLQ, bottomLQ);
        }
    }

    public void setMainTitleBackBase(int choose) {
        if (choose == 0) {
            ya.setTextSize(20);
            ya.setTextColor(Color.parseColor("#ffffff"));
            line.setTextColor(Color.parseColor("#ffffff"));
            jing.setTextSize(18);
            jing.setTextColor(Color.parseColor("#000000"));
        }
        if (choose == 1) {
            jing.setTextSize(20);
            jing.setTextColor(Color.parseColor("#ffffff"));
            line.setTextColor(Color.parseColor("#ffffff"));
            ya.setTextSize(18);
            ya.setTextColor(Color.parseColor("#000000"));
        }

    }

    public static String getNextDay(String time, int days) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        date = calendar.getTime();
        return new SimpleDateFormat("MM月dd日").format(date);
    }
}
