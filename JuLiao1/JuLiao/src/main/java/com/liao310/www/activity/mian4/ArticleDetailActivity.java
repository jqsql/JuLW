package com.liao310.www.activity.mian4;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.personal.SendCard;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleDetailBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.FavouriteBack;
import com.liao310.www.domain.zhuanjia.FouceBack;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.ServiceZhuanJia;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.utils.MathUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.utils.UiUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("ResourceAsColor")
public class ArticleDetailActivity extends BaseActivity {
    public static final int LOGIN = 0;
    public static final int LOGIN_PAY = 2;
    public static final int PAY = 1;
    public static final int FAVOURITE = 3;
    private ImageView back, head;
    private TextView title, name, fensi, wenzhang;
    private TextView guanzhu;

    public TextView time, biaoqian1, biaoqian2, titleTx;
    private LinearLayout contentView;
    private View favourite, share;
    private ImageView favourite_im;
    private ArticleDetailActivity _this;
    private boolean hasFouce = false, isFoucing = false;
    private boolean hasFavourite = false, isFavouriting = false;
    private String rid;
    private ZhuanJia zhuanjia;
    private Article article;
    private SendCard card;
    private boolean showResult = true, showResult1 = true, showResult2 = true;

    private MyHandler myHandler = new MyHandler(this);
    private View jx;
    private View jc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articledetail);
        _this = this;
        rid = getIntent().getStringExtra("rid");
        initView();
        initData();


    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                _this.finish();
            }
        });
        /*title =(TextView) findViewById(R.id.tv_head_title);
        title.setText("详情");*/

        head = (ImageView) findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (zhuanjia != null && !TextUtils.isEmpty(zhuanjia.getUid())) {
                    Intent intentZJ = new Intent(_this, ZhuanJiaDetailActivity.class);
                    intentZJ.putExtra("uid", zhuanjia.getUid());
                    startActivity(intentZJ);
                }
            }
        });
        name = (TextView) findViewById(R.id.name);
        fensi = (TextView) findViewById(R.id.fensi);
        wenzhang = (TextView) findViewById(R.id.wenzhang);
        guanzhu = (TextView) findViewById(R.id.guanzhu);
        guanzhu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!PreferenceUtil.getBoolean(_this, "hasLogin")) {
                    Intent intentLogin = new Intent(_this, LoginActivity.class);
                    intentLogin.putExtra("requestName", "intentLogin");
                    startActivityForResult(intentLogin, LOGIN);
                } else {
                    if (!isFoucing) {
                        isFoucing = true;
                        setFouce();
                    }
                }
            }
        });

        time = (TextView) findViewById(R.id.time);
        biaoqian1 = (TextView) findViewById(R.id.biaoqian1);
        biaoqian2 = (TextView) findViewById(R.id.biaoqian2);
        titleTx = (TextView) findViewById(R.id.title);
        contentView = (LinearLayout) findViewById(R.id.content);
        favourite = findViewById(R.id.favourite);
        favourite_im = (ImageView) findViewById(R.id.favourite_im);
        favourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!PreferenceUtil.getBoolean(_this, "hasLogin")) {
                    Intent intentLogin = new Intent(_this, LoginActivity.class);
                    intentLogin.putExtra("requestName", "intentLogin");
                    startActivityForResult(intentLogin, FAVOURITE);
                } else {
                    if (!isFavouriting) {
                        isFavouriting = true;
                        setFavouriteData();
                    }
                }
            }
        });
        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ToastUtils.showShort(_this, "分享稍后上线");
            }
        });
    }

    private void initData() {
        ServiceZhuanJia.getInstance().getRecommendDetail(_this, rid,
                new CallBack<ArticleDetailBack>() {
                    @Override
                    public void onSuccess(ArticleDetailBack t) {
                        // TODO 自动生成的方法存根
                        if (t.getData() != null) {
                            zhuanjia = t.getData().getPerson();
                            if (zhuanjia != null) {
                                setPersonData();
                            }
                            article = t.getData().getArticledetail();
                            if (article != null) {
                                card = t.getData().getCard();
                                setArticleData(t.getData().isHaveCard());
                            }
                        }

                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(_this, errorMessage.msg);
                    }
                });
    }

    public void setPersonData() {
        if (zhuanjia.getFollow_status() == 0) {
            hasFouce = false;
            guanzhu.setText("+ 关注");
            guanzhu.setTextColor(_this.getResources().getColor(R.color.textgrey));
            guanzhu.setBackgroundResource(R.drawable.cornerfulllightgery);
        } else {
            hasFouce = true;
            guanzhu.setText("√已关注");
            guanzhu.setTextColor(Color.WHITE);
            guanzhu.setBackgroundResource(R.drawable.cornerfullgreen15dp);
        }
        xUtilsImageUtils.display(head, R.drawable.defaultpic, zhuanjia.getAvatar());
        name.setText(zhuanjia.getNickname());
        fensi.setText(zhuanjia.getFans_num() + "");
        wenzhang.setText(zhuanjia.getArticle_num() + "");
    }

    public void setArticleData(boolean isHaveCard) {
        if (article.getFavorite_status() == 0) {
            hasFavourite = false;
            favourite_im.setBackgroundResource(R.drawable.shoucang);
        } else {
            hasFavourite = true;
            favourite_im.setBackgroundResource(R.drawable.shoucanged);
        }
        time.setText("发布于：" + article.getPublish_time());
        String biaoqian = article.getType_text();
        if (TextUtils.isEmpty(biaoqian)) {
            biaoqian1.setVisibility(View.GONE);
            biaoqian2.setVisibility(View.GONE);
        } else {
            if (biaoqian.contains("</span> <span class=\"t-tag-i\">")) {
                String[] biaoqians = biaoqian.split("</span> <span class=\"t-tag-i\">");
                try {
                    if (biaoqians.length >= 2) {
                        String bq1 = biaoqians[0].substring(biaoqians[0].indexOf(">") + 1, biaoqians[0].length()).trim();
                        if (bq1.trim().length() > 7) {
                            biaoqian1.setText(bq1.substring(0, 7) + "...");
                        } else {
                            biaoqian1.setText(bq1);
                        }
                        biaoqian1.setVisibility(View.VISIBLE);

                        String bq2 = biaoqians[1].replace("</span>", "").trim();
                        if (bq2.trim().length() > 7) {
                            biaoqian2.setText(bq2.substring(0, 7) + "...");
                        } else {
                            biaoqian2.setText(bq2);
                        }
                        biaoqian2.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    biaoqian = biaoqian.replace("</span>", "");
                    biaoqian = biaoqian.substring(biaoqian.indexOf(">") + 1, biaoqian.length()).trim();
                    if (biaoqian.trim().length() > 7) {
                        biaoqian1.setText(biaoqian.substring(0, 7) + "...");
                    } else {
                        biaoqian1.setText(biaoqian);
                    }
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian2.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        titleTx.setText(article.getContent());
        setMacth();
        if (!article.isShow()) {
            findViewById(R.id.notfree).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.money)).setText(article.getPrice() + "金币");
            findViewById(R.id.moneyview).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    toPay();
                }
            });
            if (isHaveCard && card != null) {
                findViewById(R.id.send).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.sendmoney)).setText(card.getMoney() + "金币以下");
                findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        toPaySendCard();
                    }
                });
            } else {
                findViewById(R.id.send).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.notfree).setVisibility(View.GONE);
        }

    }

    public void setMacth() {
        if (article.getMatch() == null || article.getMatch().size() == 0) {
            return;
        }
        if (article.getArt_type() == 0) {//精选
            jx = View.inflate(_this, R.layout.articledetail_jx, null);
            ((TextView) jx.findViewById(R.id.time)).setText(article.getMatch().get(0).getSc_time());
            ((TextView) jx.findViewById(R.id.matchkey)).setText(article.getMatch().get(0).getMatchKey());
            ((TextView) jx.findViewById(R.id.gamename)).setText(article.getMatch().get(0).getLs_cname());
            ((TextView) jx.findViewById(R.id.hostteamname)).setText(article.getMatch().get(0).getZhuname());
            ((TextView) jx.findViewById(R.id.gustteamname)).setText(article.getMatch().get(0).getKename());
            if (article.getMatch().get(0).getGameresult() != -1) {
                ((TextView) jx.findViewById(R.id.vs)).setText(article.getMatch().get(0).getZhunamescore()
                        + " - "
                        + article.getMatch().get(0).getKenamescore());
            } else {
                ((TextView) jx.findViewById(R.id.vs)).setText("VS");
            }

            View shouqi = jx.findViewById(R.id.shouqi);
            if(article.getMatch().get(0).isIf_roback()){
                (jx.findViewById(R.id.ArticleDetail_jx_Fan)).setVisibility(View.VISIBLE);
            }
            if (article.isShow()) {
                String wanfa = article.getMatch().get(0).getWanfa();
                //分析內容
                //Log.e("文章id=",""+article.getRid());
                setHtmlText(1, article.getContentTX());
                if (TextUtils.isEmpty(wanfa)) {
                    shouqi.setClickable(false);
                    jx.findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
                    jx.findViewById(R.id.result).setVisibility(View.GONE);
                } else {
                    shouqi.setClickable(true);
                    jx.findViewById(R.id.free).setVisibility(View.VISIBLE);

                    ((TextView) jx.findViewById(R.id.type)).setText("[" + article.getMatch().get(0).getPankou_type() + "]");
                    ((TextView) jx.findViewById(R.id.wanfa)).setText(wanfa);
                    ((TextView) jx.findViewById(R.id.pankou)).setText(article.getMatch().get(0).getPankou());
                    if (article.getMatch().get(0).isChoose1()) {
                        if ("大小分".equals(wanfa)) {
                            ((TextView) jx.findViewById(R.id.fang)).setText("大分");
                        } else if ("大小球".equals(wanfa)) {
                            ((TextView) jx.findViewById(R.id.fang)).setText("大球");
                        } else {
                            ((TextView) jx.findViewById(R.id.fang)).setText("主队");
                        }
                        ((TextView) jx.findViewById(R.id.peilv)).setText(article.getMatch().get(0).getLv1());
                    } else if (article.getMatch().get(0).isChoose3()) {
                        if ("大小分".equals(wanfa)) {
                            ((TextView) jx.findViewById(R.id.fang)).setText("小分");
                        } else if ("大小球".equals(wanfa)) {
                            ((TextView) jx.findViewById(R.id.fang)).setText("小球");
                        } else {
                            ((TextView) jx.findViewById(R.id.fang)).setText("客队");
                        }
                        ((TextView) jx.findViewById(R.id.peilv)).setText(article.getMatch().get(0).getLv3());
                    }
                    shouqi.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            if (!showResult) {
                                showResult = true;
                                ((ImageView) jx.findViewById(R.id.arrow)).setImageResource(R.drawable.up_arrow);
                                jx.findViewById(R.id.result).setVisibility(View.VISIBLE);
                            } else {
                                showResult = false;
                                ((ImageView) jx.findViewById(R.id.arrow)).setImageResource(R.drawable.down_arrow);
                                jx.findViewById(R.id.result).setVisibility(View.GONE);
                            }
                        }
                    });
                }
            } else {
                shouqi.setClickable(false);
                jx.findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
                jx.findViewById(R.id.free).setVisibility(View.GONE);
            }
            contentView.addView(jx);
        }
        if (article.getArt_type() == 1) {//竞彩可见
            if (article.getMatch().size() < 2) {
                return;
            }
            jc = View.inflate(_this, R.layout.articledetail_jc, null);
            ((TextView) jc.findViewById(R.id.time1)).setText(article.getMatch().get(0).getSc_time());
            ((TextView) jc.findViewById(R.id.matchkey1)).setText(article.getMatch().get(0).getMatchKey());
            ((TextView) jc.findViewById(R.id.gamename1)).setText(article.getMatch().get(0).getLs_cname());
            ((TextView) jc.findViewById(R.id.hostteamname1)).setText(article.getMatch().get(0).getZhuname());
            ((TextView) jc.findViewById(R.id.gustteamname1)).setText(article.getMatch().get(0).getKename());
            ((TextView) jc.findViewById(R.id.time2)).setText(article.getMatch().get(1).getSc_time());
            ((TextView) jc.findViewById(R.id.matchkey2)).setText(article.getMatch().get(1).getMatchKey());
            ((TextView) jc.findViewById(R.id.gamename2)).setText(article.getMatch().get(1).getLs_cname());
            ((TextView) jc.findViewById(R.id.hostteamname2)).setText(article.getMatch().get(1).getZhuname());
            ((TextView) jc.findViewById(R.id.gustteamname2)).setText(article.getMatch().get(1).getKename());
            if (article.getMatch().get(0).getGameresult() != -1) {
                ((TextView) jc.findViewById(R.id.vs1)).setText(article.getMatch().get(0).getZhunamescore()
                        + " - "
                        + article.getMatch().get(0).getKenamescore());
            }

            if (article.getMatch().get(1).getGameresult() != -1) {
                ((TextView) jc.findViewById(R.id.vs2)).setText(article.getMatch().get(1).getZhunamescore()
                        + " - "
                        + article.getMatch().get(1).getKenamescore());
            }
            View shouqi1 = jc.findViewById(R.id.shouqi1);
            View shouqi2 = jc.findViewById(R.id.shouqi2);
            if(article.getMatch().get(0).isIf_roback()){
                (jx.findViewById(R.id.ActicleDetail_Jc_Fan1)).setVisibility(View.VISIBLE);
            }
            if(article.getMatch().get(1).isIf_roback()){
                (jx.findViewById(R.id.ActicleDetail_Jc_Fan2)).setVisibility(View.VISIBLE);
            }
            if (article.isShow()) {
                ((TextView) jc.findViewById(R.id.wanfa1)).setText(article.getMatch().get(0).getWanfa());
                ((TextView) jc.findViewById(R.id.wanfa_num1)).setText(article.getMatch().get(0).getWanfa_num());
                setSigleView(article.getMatch().get(0).getLv1(),
                        article.getMatch().get(0).isChoose1(),
                        article.getMatch().get(0).isResult1(),
                        jc.findViewById(R.id.shengview1),
                        (TextView) jc.findViewById(R.id.sheng1),
                        (TextView) jc.findViewById(R.id.shenglv1),
                        (ImageView) jc.findViewById(R.id.resultsheng1),
                        (ImageView) jc.findViewById(R.id.resultsheng1_));

                setSigleView(article.getMatch().get(0).getLv3(),
                        article.getMatch().get(0).isChoose3(),
                        article.getMatch().get(0).isResult3(),
                        jc.findViewById(R.id.fuview1),
                        (TextView) jc.findViewById(R.id.fu1),
                        (TextView) jc.findViewById(R.id.fulv1),
                        (ImageView) jc.findViewById(R.id.resultfu1),
                        (ImageView) jc.findViewById(R.id.resultfu1_));
               /* if (article.getMatch().get(0).getGameresult() == 5) {//命中
                    ((ImageView) jc.findViewById(R.id.resultsheng1_)).setImageResource(R.drawable.zhongyi);
                    jc.findViewById(R.id.resultsheng1_).setVisibility(View.VISIBLE);
                }
                if (article.getMatch().get(0).getGameresult() == 6) {//未中
                    ((ImageView) jc.findViewById(R.id.resultzhong1)).setImageResource(R.drawable.zhongwei);
                    jc.findViewById(R.id.resultzhong1).setVisibility(View.VISIBLE);
                }*/

                if ("让球".equals(article.getMatch().get(0).getWanfa())) {
                    ((TextView) jc.findViewById(R.id.sheng1)).setText("主胜");
                    ((TextView) jc.findViewById(R.id.ping1)).setText("平局");
                    ((TextView) jc.findViewById(R.id.fu1)).setText("主负");
                    jc.findViewById(R.id.pingview1).setVisibility(View.VISIBLE);
                    setSigleView(article.getMatch().get(0).getLv2(),
                            article.getMatch().get(0).isChoose2(),
                            article.getMatch().get(0).isResult2(),
                            jc.findViewById(R.id.pingview1),
                            (TextView) jc.findViewById(R.id.ping1),
                            (TextView) jc.findViewById(R.id.pinglv1),
                            (ImageView) jc.findViewById(R.id.resultping1),
                            (ImageView) jc.findViewById(R.id.resultping1_));

                } else {
                    jc.findViewById(R.id.pingview1).setVisibility(View.GONE);
                    if ("让分".equals(article.getMatch().get(0).getWanfa())) {
                        ((TextView) jc.findViewById(R.id.sheng1)).setText("主胜");
                        ((TextView) jc.findViewById(R.id.fu1)).setText("主负");
                    } else if ("大小分".equals(article.getMatch().get(0).getWanfa())) {
                        ((TextView) jc.findViewById(R.id.sheng1)).setText("大分");
                        ((TextView) jc.findViewById(R.id.fu1)).setText("小分");
                    }
                }

                ((TextView) jc.findViewById(R.id.wanfa2)).setText(article.getMatch().get(1).getWanfa());
                ((TextView) jc.findViewById(R.id.wanfa_num2)).setText(article.getMatch().get(1).getWanfa_num());

                setSigleView(article.getMatch().get(1).getLv1(),
                        article.getMatch().get(1).isChoose1(),
                        article.getMatch().get(1).isResult1(),
                        jc.findViewById(R.id.shengview2),
                        (TextView) jc.findViewById(R.id.sheng2),
                        (TextView) jc.findViewById(R.id.shenglv2),
                        (ImageView) jc.findViewById(R.id.resultsheng2),
                        (ImageView) jc.findViewById(R.id.resultsheng2_));

                setSigleView(article.getMatch().get(1).getLv3(),
                        article.getMatch().get(1).isChoose3(),
                        article.getMatch().get(1).isResult3(),
                        jc.findViewById(R.id.fuview2),
                        (TextView) jc.findViewById(R.id.fu2),
                        (TextView) jc.findViewById(R.id.fulv2),
                        (ImageView) jc.findViewById(R.id.resultfu2),
                        (ImageView) jc.findViewById(R.id.resultfu2_));
               /* if (article.getMatch().get(1).getGameresult() == 5) {
                    ((ImageView) jc.findViewById(R.id.resultzhong2)).setImageResource(R.drawable.zhongyi);
                    jc.findViewById(R.id.resultzhong2).setVisibility(View.VISIBLE);
                }
                if (article.getMatch().get(1).getGameresult() == 6) {
                    ((ImageView) jc.findViewById(R.id.resultzhong2)).setImageResource(R.drawable.zhongwei);
                    jc.findViewById(R.id.resultzhong2).setVisibility(View.VISIBLE);
                }*/
                if ("让球".equals(article.getMatch().get(1).getWanfa())) {
                    ((TextView) jc.findViewById(R.id.sheng2)).setText("主胜");
                    ((TextView) jc.findViewById(R.id.ping2)).setText("平局");
                    ((TextView) jc.findViewById(R.id.fu2)).setText("主负");
                    jc.findViewById(R.id.pingview2).setVisibility(View.VISIBLE);
                    setSigleView(article.getMatch().get(1).getLv2(),
                            article.getMatch().get(1).isChoose2(),
                            article.getMatch().get(1).isResult2(),
                            jc.findViewById(R.id.pingview2),
                            (TextView) jc.findViewById(R.id.ping2),
                            (TextView) jc.findViewById(R.id.pinglv2),
                            (ImageView) jc.findViewById(R.id.resultping2),
                            (ImageView) jc.findViewById(R.id.resultping2_));

                } else {
                    jc.findViewById(R.id.pingview2).setVisibility(View.GONE);
                    if ("让分".equals(article.getMatch().get(1).getWanfa())) {
                        ((TextView) jc.findViewById(R.id.sheng2)).setText("主胜");
                        ((TextView) jc.findViewById(R.id.fu2)).setText("主负");
                    } else if ("大小分".equals(article.getMatch().get(1).getWanfa())) {
                        ((TextView) jc.findViewById(R.id.sheng2)).setText("大分");
                        ((TextView) jc.findViewById(R.id.fu2)).setText("小分");
                    }
                }

                jc.findViewById(R.id.free1).setVisibility(View.VISIBLE);
                jc.findViewById(R.id.free2).setVisibility(View.VISIBLE);
                jc.findViewById(R.id.content).setVisibility(View.VISIBLE);

                //分析內容
                setHtmlText(2, article.getContentTX());
                //((TextView) jc.findViewById(R.id.content)).setText(Html.fromHtml(""+article.getContentTX()));

                shouqi1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (!showResult1) {
                            showResult1 = true;
                            ((ImageView) jc.findViewById(R.id.arrow1)).setImageResource(R.drawable.up_arrow);
                            jc.findViewById(R.id.free1).setVisibility(View.VISIBLE);
                        } else {
                            showResult1 = false;
                            ((ImageView) jc.findViewById(R.id.arrow1)).setImageResource(R.drawable.down_arrow);
                            jc.findViewById(R.id.free1).setVisibility(View.GONE);
                        }
                    }
                });
                shouqi2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (!showResult2) {
                            showResult2 = true;
                            ((ImageView) jc.findViewById(R.id.arrow2)).setImageResource(R.drawable.up_arrow);
                            jc.findViewById(R.id.free2).setVisibility(View.VISIBLE);
                        } else {
                            showResult2 = false;
                            ((ImageView) jc.findViewById(R.id.arrow2)).setImageResource(R.drawable.down_arrow);
                            jc.findViewById(R.id.free2).setVisibility(View.GONE);
                        }
                    }
                });

            } else {
                shouqi1.setClickable(false);
                jc.findViewById(R.id.arrow1).setVisibility(View.INVISIBLE);
                shouqi2.setClickable(false);
                jc.findViewById(R.id.arrow2).setVisibility(View.INVISIBLE);
                jc.findViewById(R.id.free1).setVisibility(View.GONE);
                jc.findViewById(R.id.free2).setVisibility(View.GONE);
                jc.findViewById(R.id.content).setVisibility(View.GONE);
            }
            contentView.addView(jc);
        }

    }

    public void setFouce() {
        ServiceZhuanJia.getInstance().getFouce(_this, zhuanjia.getUid(), hasFouce ? 0 : 1,
                new CallBack<FouceBack>() {
                    @Override
                    public void onSuccess(FouceBack t) {
                        // TODO 自动生成的方法存根
                        if (t.getData() != null && t.getData().getFollow_status() == 1) {
                            hasFouce = true;
                            ToastUtils.showShort(_this, "关注成功");
                            guanzhu.setText("√已关注");
                            guanzhu.setTextColor(Color.WHITE);
                            guanzhu.setBackgroundResource(R.drawable.cornerfullgreen15dp);
                        } else {
                            hasFouce = false;
                            ToastUtils.showShort(_this, "取消关注");
                            guanzhu.setText("+  关注");
                            guanzhu.setTextColor(_this.getResources().getColor(R.color.textgrey));
                            guanzhu.setBackgroundResource(R.drawable.cornerfulllightgery);
                        }
                        isFoucing = false;
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(_this, errorMessage.msg);
                        isFoucing = false;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOGIN:
                    if (!isFoucing) {
                        isFoucing = true;
                        setFouce();
                    }
                    break;
                case LOGIN_PAY:
                    Intent intentPay = new Intent(_this, PayActivity.class);
                    intentPay.putExtra("rid", rid);
                    intentPay.putExtra("name", zhuanjia.getNickname());
                    intentPay.putExtra("prize", article.getPrice());
                    startActivityForResult(intentPay, PAY);
                    break;
                case PAY:
                    contentView.removeAllViews();
                    initData();
                    break;
                case FAVOURITE:
                    if (!isFavouriting) {
                        isFavouriting = true;
                        setFavouriteData();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void setSigleView(String lv, boolean choose, boolean result, View view, TextView tv,
                             TextView tvlv, ImageView resultView, ImageView resultView_r) {
        tvlv.setText(lv);
        if (choose) {
            //用户选择了这个
            resultView.setVisibility(View.VISIBLE);
            resultView_r.setVisibility(View.VISIBLE);
            //view.setBackgroundResource(R.drawable.cornerfullgreen);
            if (result) {
                //这个是结果
                tvlv.setTextColor(Color.BLACK);
                tvlv.setBackgroundColor(getResources().getColor(R.color.textgreen));
                resultView.setImageResource(R.drawable.zhongwhite);
                resultView_r.setImageResource(R.drawable.zhongyi);
            } else {
                resultView.setImageResource(R.drawable.zhonggreen);
                resultView_r.setImageResource(R.drawable.zhongwei);
            }
        } else {
            //用户没有选择了这个
            resultView.setVisibility(View.GONE);
            resultView_r.setVisibility(View.GONE);
            if (result) {
                //这个是结果
                //view.setBackgroundResource(R.drawable.cornerfullgrey);
                tvlv.setTextColor(Color.BLACK);
                tvlv.setBackgroundColor(getResources().getColor(R.color.textgreen));
            }
        }
    }

    public void toPay() {
        if (!PreferenceUtil.getBoolean(_this, "hasLogin")) {
            Intent intentLogin = new Intent(_this, LoginActivity.class);
            intentLogin.putExtra("requestName", "intentLogin");
            startActivityForResult(intentLogin, LOGIN_PAY);
        } else {
            Intent intentPay = new Intent(_this, PayActivity.class);
            intentPay.putExtra("rid", rid);
            intentPay.putExtra("name", zhuanjia.getNickname());
            intentPay.putExtra("prize", article.getPrice());
            startActivityForResult(intentPay, PAY);
        }
    }

    public void toPaySendCard() {
        dialog("赠送卡支付", "您确认使用赠送卡支付吗？", "取消", true, "确认", "");
    }

    public void dialogToDo() {
        ServicePay.getInstance().pay(_this, rid, card.getMoney(), 4,
                new CallBack<PayBack>() {
                    @Override
                    public void onSuccess(PayBack t) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(_this, "购买成功");
                        contentView.removeAllViews();
                        initData();
                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        ToastUtils.showShort(_this, errorMessage.msg);
                    }
                });
    }

    private void setFavouriteData() {
        int type = 0;
        if (hasFavourite) {
            type = 0;
        } else {
            type = 1;
        }
        ServiceZhuanJia.getInstance().setFavourite(_this, rid, type,
                new CallBack<FavouriteBack>() {
                    @Override
                    public void onSuccess(FavouriteBack t) {
                        // TODO 自动生成的方法存根
                        isFavouriting = false;
                        if (t.getData() != null) {

                            if (1 == t.getData().getFavorite_status()) {
                                ToastUtils.showShort(_this, "已收藏");
                                hasFavourite = true;
                                favourite_im.setBackgroundResource(R.drawable.shoucanged);
                            }
                            if (0 == t.getData().getFavorite_status()) {
                                hasFavourite = false;
                                ToastUtils.showShort(_this, "取消收藏");
                                favourite_im.setBackgroundResource(R.drawable.shoucang);
                            }
                        }

                    }

                    @Override
                    public void onFailure(ErrorMsg errorMessage) {
                        // TODO 自动生成的方法存根
                        isFavouriting = false;
                        ToastUtils.showShort(_this, errorMessage.msg);
                    }
                });
    }


    private void setHtmlText(final int type, String html) {
        if (html.contains("<img")) {
            for (int i = -1; i <= html.lastIndexOf("<img"); ++i) {
                StringBuffer stringBuffer1 = new StringBuffer(html);
                i = html.indexOf("<img", i);
                html = stringBuffer1.insert(i, "<br>").toString();
                i += 4;
                StringBuffer stringBuffer2 = new StringBuffer(html);
                int n = html.indexOf(">", i);
                html = stringBuffer2.insert(n + 1, "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").toString();
            }
        }
        final String htmlfin = html;
        //Log.e("Html文本=","htmlfin="+htmlfin);
        Thread thread = new Thread(new Runnable() {
            Message msg = Message.obtain();

            @Override
            public void run() {
                //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
                Html.ImageGetter imgGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
                        Drawable drawable = null;
                        URL url = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
                            drawable.setBounds(0, 0, UiUtils.getWindowsWidth(ArticleDetailActivity.this),
                                    (int) MathUtils.mul(MathUtils.div(drawable.getIntrinsicHeight(), drawable.getIntrinsicWidth()),
                                            UiUtils.getWindowsWidth(ArticleDetailActivity.this)));
                        } catch (MalformedURLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                CharSequence test = Html.fromHtml(htmlfin, imgGetter, null);
                if (type == 1) {
                    msg.what = 0x101;
                } else {
                    msg.what = 0x102;
                }
                msg.obj = test;
                myHandler.sendMessage(msg);
            }
        });
        thread.start();
    }


    private static class MyHandler extends Handler {
        WeakReference<ArticleDetailActivity> mActivity;

        public MyHandler(ArticleDetailActivity activity) {
            // TODO Auto-generated constructor stub
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            ArticleDetailActivity theActivity = mActivity.get();
            if (msg.what == 0x101) {
                ((TextView) theActivity.jx.findViewById(R.id.content)).setText((CharSequence) msg.obj);
            } else if (msg.what == 0x102) {
                ((TextView) theActivity.jc.findViewById(R.id.content)).setText((CharSequence) msg.obj);
            }
            super.handleMessage(msg);
        }
    }

}
