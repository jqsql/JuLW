package com.liao310.www.activity.mian4.shouye;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.domain.match.Match;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.BaseRecyclerAdapter;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.BaseTypeRecyclerAdapter;
import com.liao310.www.utils.BaseRecyclerAdapterUtils.BaseViewHolder;
import com.liao310.www.widget.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class MainHomeNewAdapter extends BaseRecyclerAdapter<Article> {
    private Context _this;
    private int _type;//0精选，1竞彩,2免费,3 混合
    private int _typeFovourite;//0显示下面两个时间，显示盈利率等1显示上面1个时间,2像是两个时间，不显示盈利率等

    public MainHomeNewAdapter(Context context, int layoutID, List list) {
        super(context, layoutID, list);
        _this = context;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    @Override
    protected void convert(BaseViewHolder holder, final Article article, int position) {
        ShapedImageView head = null;
        TextView name = null, money = null, endtime = null, gamename1 = null, hostteamname1 = null, gustteamname1 = null;
        head = (ShapedImageView) holder.getView(R.id.head);
        name = (TextView) holder.getView(R.id.name);
        money = (TextView) holder.getView(R.id.money);
        endtime = (TextView) holder.getView(R.id.endtime);
        gamename1 = (TextView) holder.getView(R.id.gamename1);
        hostteamname1 = (TextView) holder.getView(R.id.hostteamname1);
        gustteamname1 = (TextView) holder.getView(R.id.gustteamname1);
        TextView biaoqian1 = (TextView) holder.getView(R.id.biaoqian1);
        TextView biaoqian2 = (TextView) holder.getView(R.id.biaoqian2);
        TextView biaoqian3 = (TextView) holder.getView(R.id.biaoqian3);
        TextView biaoqian4 = (TextView) holder.getView(R.id.biaoqian4);
        TextView shenglvtx = (TextView) holder.getView(R.id.shenglvtx);
        TextView shenglv = (TextView) holder.getView(R.id.shenglv);

        LinearLayout matchs = (LinearLayout) holder.getView(R.id.matchs);
        LinearLayout match1 = holder.getView(R.id.match1);
        TextView macthkey1 = (TextView) holder.getView(R.id.macthkey1);

        TextView lasttime = (TextView) holder.getView(R.id.lasttime);
        TextView content = (TextView) holder.getView(R.id.content);
        LinearLayout matchview = holder.getView(R.id.matchview);
        RelativeLayout bottom = holder.getView(R.id.bottom);
        TextView sendtime = (TextView) holder.getView(R.id.sendtime);
        List<Article.ColorData> biaoqianList = article.getType_texts();

        //初始化标签颜色
        biaoqian1.setBackgroundResource(R.drawable.corner_main_greenline_whitefull);
        biaoqian2.setBackgroundResource(R.drawable.corner_main_greenline_whitefull);
        biaoqian3.setBackgroundResource(R.drawable.corner_main_greenline_whitefull);
        biaoqian4.setBackgroundResource(R.drawable.corner_main_greenline_whitefull);
        GradientDrawable gd1 = (GradientDrawable) biaoqian1.getBackground();
        GradientDrawable gd2 = (GradientDrawable) biaoqian2.getBackground();
        GradientDrawable gd3 = (GradientDrawable) biaoqian3.getBackground();
        GradientDrawable gd4 = (GradientDrawable) biaoqian4.getBackground();
        gd1.setStroke(1,_this.getResources().getColor(R.color.textgreen));
        gd2.setStroke(1,_this.getResources().getColor(R.color.textgreen));
        gd3.setStroke(1,_this.getResources().getColor(R.color.textgreen));
        gd4.setStroke(1,_this.getResources().getColor(R.color.textgreen));
        biaoqian1.setTextColor(_this.getResources().getColor(R.color.textgreen));
        biaoqian2.setTextColor(_this.getResources().getColor(R.color.textgreen));
        biaoqian3.setTextColor(_this.getResources().getColor(R.color.textgreen));
        biaoqian4.setTextColor(_this.getResources().getColor(R.color.textgreen));
        if(_type==2) {//关注
            String biaoqians = article.getType_text();
            if (TextUtils.isEmpty(biaoqians)) {
                biaoqian1.setVisibility(View.GONE);
                biaoqian2.setVisibility(View.GONE);
                biaoqian3.setVisibility(View.GONE);
                biaoqian4.setVisibility(View.GONE);
            } else {
                String[] tagDatas = biaoqians.split("<span class=\"t-tag-i\">");
                List<String> TagList = new ArrayList<>();
                for (int i = 0; i < tagDatas.length; i++) {
                    if (!tagDatas[i].isEmpty())
                        TagList.add(tagDatas[i].substring(0, tagDatas[i].indexOf("</span>")));
                }
                if (TagList.size() != 0) {
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian2.setVisibility(View.VISIBLE);
                    biaoqian3.setVisibility(View.VISIBLE);
                    biaoqian4.setVisibility(View.VISIBLE);
                    if (TagList.size() >= 4) {
                        biaoqian1.setText(TagList.get(0));
                        biaoqian2.setText(TagList.get(1));
                        biaoqian3.setText(TagList.get(2));
                        biaoqian4.setText(TagList.get(3));
                    } else if (TagList.size() >= 3) {
                        biaoqian1.setText(TagList.get(0));
                        biaoqian2.setText(TagList.get(1));
                        biaoqian3.setText(TagList.get(2));
                        biaoqian4.setVisibility(View.GONE);
                    } else if (TagList.size() >= 2) {
                        biaoqian1.setText(TagList.get(0));
                        biaoqian2.setText(TagList.get(1));
                        biaoqian3.setVisibility(View.GONE);
                        biaoqian4.setVisibility(View.GONE);
                    } else if (TagList.size() >= 1) {
                        biaoqian1.setText(TagList.get(0));
                        biaoqian2.setVisibility(View.GONE);
                        biaoqian3.setVisibility(View.GONE);
                        biaoqian4.setVisibility(View.GONE);
                    }
                } else {
                    biaoqian1.setVisibility(View.GONE);
                    biaoqian2.setVisibility(View.GONE);
                    biaoqian3.setVisibility(View.GONE);
                    biaoqian4.setVisibility(View.GONE);
                }

            }
        }else if (biaoqianList != null) {
            try {
                if (biaoqianList.size() == 1) {
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian1.setText(biaoqianList.get(0).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    if (biaoqianList.get(0).getColor() != null && !biaoqianList.get(0).getColor().isEmpty()) {
                        gd1.setStroke(1, Color.parseColor(biaoqianList.get(0).getColor()));
                        biaoqian1.setTextColor(Color.parseColor(biaoqianList.get(0).getColor()));
                    }

                    biaoqian2.setVisibility(View.GONE);
                    biaoqian3.setVisibility(View.GONE);
                    biaoqian4.setVisibility(View.GONE);
                } else if (biaoqianList.size() == 2) {
                    biaoqian1.setText(biaoqianList.get(0).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian2.setText(biaoqianList.get(1).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    if (biaoqianList.get(0).getColor() != null && !biaoqianList.get(0).getColor().isEmpty()) {
                        gd1.setStroke(1, Color.parseColor(biaoqianList.get(0).getColor()));
                        biaoqian1.setTextColor(Color.parseColor(biaoqianList.get(0).getColor()));
                    }
                    if (biaoqianList.get(1).getColor() != null && !biaoqianList.get(1).getColor().isEmpty()) {
                        gd2.setStroke(1, Color.parseColor(biaoqianList.get(1).getColor()));
                        biaoqian2.setTextColor(Color.parseColor(biaoqianList.get(1).getColor()));
                    }
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian2.setVisibility(View.VISIBLE);
                    biaoqian3.setVisibility(View.GONE);
                    biaoqian4.setVisibility(View.GONE);
                } else if (biaoqianList.size() == 3) {
                    biaoqian1.setText(biaoqianList.get(0).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian2.setText(biaoqianList.get(1).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian3.setText(biaoqianList.get(2).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    if (biaoqianList.get(0).getColor() != null && !biaoqianList.get(0).getColor().isEmpty()) {
                        gd1.setStroke(1, Color.parseColor(biaoqianList.get(0).getColor()));
                        biaoqian1.setTextColor(Color.parseColor(biaoqianList.get(0).getColor()));
                    }
                    if (biaoqianList.get(1).getColor() != null && !biaoqianList.get(1).getColor().isEmpty()) {
                        gd2.setStroke(1, Color.parseColor(biaoqianList.get(1).getColor()));
                        biaoqian2.setTextColor(Color.parseColor(biaoqianList.get(1).getColor()));
                    }
                    if (biaoqianList.get(2).getColor() != null && !biaoqianList.get(2).getColor().isEmpty()) {
                        gd3.setStroke(1, Color.parseColor(biaoqianList.get(2).getColor()));
                        biaoqian3.setTextColor(Color.parseColor(biaoqianList.get(2).getColor()));
                    }
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian2.setVisibility(View.VISIBLE);
                    biaoqian3.setVisibility(View.VISIBLE);
                    biaoqian4.setVisibility(View.GONE);
                } else if (biaoqianList.size() >= 4) {
                    biaoqian1.setText(biaoqianList.get(0).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian2.setText(biaoqianList.get(1).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian3.setText(biaoqianList.get(2).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    biaoqian4.setText(biaoqianList.get(3).getLab().replace("<span class=\"t-tag-i\">", "").replace("</span>", ""));
                    if (biaoqianList.get(0).getColor() != null && !biaoqianList.get(0).getColor().isEmpty()) {
                        gd1.setStroke(1, Color.parseColor(biaoqianList.get(0).getColor()));
                        biaoqian1.setTextColor(Color.parseColor(biaoqianList.get(0).getColor()));
                    }
                    if (biaoqianList.get(1).getColor() != null && !biaoqianList.get(1).getColor().isEmpty()) {
                        gd2.setStroke(1, Color.parseColor(biaoqianList.get(1).getColor()));
                        biaoqian2.setTextColor(Color.parseColor(biaoqianList.get(1).getColor()));
                    }
                    if (biaoqianList.get(2).getColor() != null && !biaoqianList.get(2).getColor().isEmpty()) {
                        gd3.setStroke(1, Color.parseColor(biaoqianList.get(2).getColor()));
                        biaoqian3.setTextColor(Color.parseColor(biaoqianList.get(2).getColor()));
                    }
                    if (biaoqianList.get(3).getColor() != null && !biaoqianList.get(3).getColor().isEmpty()) {
                        gd4.setStroke(1, Color.parseColor(biaoqianList.get(3).getColor()));
                        biaoqian4.setTextColor(Color.parseColor(biaoqianList.get(3).getColor()));
                    }
                    biaoqian1.setVisibility(View.VISIBLE);
                    biaoqian2.setVisibility(View.VISIBLE);
                    biaoqian3.setVisibility(View.VISIBLE);
                    biaoqian4.setVisibility(View.VISIBLE);
                } else {
                    biaoqian1.setVisibility(View.GONE);
                    biaoqian2.setVisibility(View.GONE);
                    biaoqian3.setVisibility(View.GONE);
                    biaoqian4.setVisibility(View.GONE);
                }
            }catch (IllegalArgumentException e){
                //抛出颜色异常
            }

        }

        if (article.getMatch() != null &&
                article.getMatch().size() > 0) {
            String matchKey = article.getMatch().get(0).getMatchKey();
            if (TextUtils.isEmpty(matchKey)) {
                macthkey1.setVisibility(View.GONE);
            } else {
                macthkey1.setVisibility(View.VISIBLE);
                macthkey1.setText(matchKey);
            }
            matchs.removeAllViews();
            if (article.getMatch().size() > 1) {
                for (int i = 1; i < article.getMatch().size(); i++) {
                    Match rzm2 = article.getMatch().get(i);
                    View otherMatch = LayoutInflater.from(_this).inflate(R.layout.matchbase_item, null);
                    TextView macthkey = (TextView) otherMatch.findViewById(R.id.macthkey);
                    String matchKeyStr = rzm2.getMatchKey();
                    if (TextUtils.isEmpty(matchKeyStr)) {
                        macthkey.setVisibility(View.GONE);
                    } else {
                        macthkey.setVisibility(View.VISIBLE);
                        macthkey.setText(matchKeyStr);
                    }
                    TextView gamename2 = (TextView) otherMatch.findViewById(R.id.gamename);
                    gamename2.setText(rzm2.getLs_cname());
                    TextView hostteamname2 = (TextView) otherMatch.findViewById(R.id.hostteamname);
                    hostteamname2.setText(rzm2.getZhuname());
                    TextView gustteamname2 = (TextView) otherMatch.findViewById(R.id.gustteamname);
                    gustteamname2.setText(rzm2.getKename());
                    matchs.addView(otherMatch);
                }
            }
        }

        if (article.isIf_roback() == 1) {
            holder.getView(R.id.result_Fan).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.result_Fan).setVisibility(View.GONE);
        }
        if (_typeFovourite == 0) {
            sendtime.setText(article.getPublish_time() + "");
            bottom.setVisibility(View.VISIBLE);
            matchview.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
            lasttime.setVisibility(View.GONE);
            if (_type == 1) {
                endtime.setVisibility(View.GONE);
                setLv(shenglvtx, shenglv, "盈利率", article.getYinglilv());
            } else if (_type == 0) {
                endtime.setText(article.getMatch().get(0).getSc_time());
                endtime.setVisibility(View.VISIBLE);

                setLv(shenglvtx, shenglv, "胜率", article.getShenglv());
            } else if (_type == 3) {
                if (article.getArt_type() == 0) {
                    endtime.setText(article.getMatch().get(0).getSc_time());
                    endtime.setVisibility(View.VISIBLE);
                    setLv(shenglvtx, shenglv, "胜率", article.getShenglv());
                }
                if (article.getArt_type() == 1) {
                    endtime.setVisibility(View.GONE);
                    setLv(shenglvtx, shenglv, "盈利率", article.getYinglilv());
                }
            }

        }
        if (_typeFovourite == 2) {
            if (_type == 1) {
                endtime.setVisibility(View.GONE);
            } else if (_type == 0) {
                endtime.setText(article.getMatch().get(0).getSc_time());
                endtime.setVisibility(View.VISIBLE);
            } else if (_type == 3) {
                if (article.getArt_type() == 0) {
                    endtime.setText(article.getMatch().get(0).getSc_time());
                    endtime.setVisibility(View.VISIBLE);
                }
                if (article.getArt_type() == 1) {
                    endtime.setVisibility(View.GONE);
                }
            }
            sendtime.setText(article.getPublish_time() + "");
            bottom.setVisibility(View.VISIBLE);
            matchview.setVisibility(View.VISIBLE);
            shenglvtx.setVisibility(View.GONE);
            shenglv.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            lasttime.setVisibility(View.GONE);
        }
        if (_typeFovourite == 1) {
            shenglvtx.setVisibility(View.GONE);
            shenglv.setVisibility(View.GONE);
            lasttime.setText(article.getPublish_time());
            content.setText(article.getContent());
            bottom.setVisibility(View.GONE);
            matchview.setVisibility(View.GONE);
            lasttime.setVisibility(View.VISIBLE);
            content.setVisibility(View.VISIBLE);
        }
        xUtilsImageUtils.display((ImageView) holder.getView(R.id.head), article.getAvatar(), R.drawable.defaultpic, true);
        name.setText(article.getNickname());
        if ("0".equals(article.getPrice())) {
            money.setText("免费");
        } else {
            money.setText(article.getPrice() + "金币");
        }
        if (article.getMatch() != null &&
                article.getMatch().size() > 0)

        {
            gamename1.setText(article.getMatch().get(0).getLs_cname());
            hostteamname1.setText(article.getMatch().get(0).getZhuname());
            gustteamname1.setText(article.getMatch().get(0).getKename());
        }

        holder.getView(R.id.head).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(_this, ZhuanJiaDetailActivity.class);
                intent.putExtra("uid", article.getUid());
                _this.startActivity(intent);
            }
        });

    }

    public void setLv(TextView lvtx, TextView lv, String lvName, String lvStr) {
        double lvDouble = 0.0;
        try {
            lvDouble = Double.parseDouble(lvStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lvDouble >= 50) {
            lvtx.setText(lvName);
            lv.setText(lvStr + "%");
            lvtx.setVisibility(View.VISIBLE);
            lv.setVisibility(View.VISIBLE);
        } else {
            lvtx.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
        }

    }
}
