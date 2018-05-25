package com.liao310.www.activity.mian4.adapter;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.domain.match.Match;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArticleListAdapter extends BaseAdapter {
    private ArrayList<Article> articlesLists;
    private Context _this;
    private LayoutInflater mInflater;
    private int _type;//0精选，1竞彩,2竞猜,3 混合
    private int _typeFovourite;//0显示下面两个时间，显示盈利率等1显示上面1个时间,2像是两个时间，不显示盈利率等

    public ArticleListAdapter(Context con, int type, int typeFovourite) {
        _this = con;
        _type = type;
        _typeFovourite = typeFovourite;
        mInflater=LayoutInflater.from(con);
    }

    public void setType(int type) {
        _type = type;
    }

    public void setDate(ArrayList<Article> orderListsFrom) {
        this.articlesLists = orderListsFrom;
    }

    @Override
    public int getCount() {
        if (articlesLists == null) {
            return 0;
        } else {
            return articlesLists.size();
        }
    }

    @Override
    public Article getItem(int position) {
        if (articlesLists == null) {
            return null;
        } else {
            return articlesLists.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        //多少种布局
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        switch (_type) {
            case 0:
            case 1:
                viewType = 1;
                break;
            case 2:
                viewType = 1;
                break;
            case 3:
                if (articlesLists.get(position).getArt_type() == 2) {
                    viewType = 0;
                } else {
                    viewType = 1;
                }
                break;
            default:
                break;
        }
        return viewType;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Article article = articlesLists.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (getItemViewType(position)) {
                case 0:
                    convertView = mInflater.inflate(R.layout.jingcai1_item, parent, false);

                    holder.head = (ShapedImageView) convertView.findViewById(R.id.head);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    holder.money = (TextView) convertView.findViewById(R.id.money);
                    holder.endtime = (TextView) convertView.findViewById(R.id.endtime);
                    holder.gamename1 = (TextView) convertView.findViewById(R.id.gamename1);
                    holder.hostteamname1 = (TextView) convertView.findViewById(R.id.hostteamname1);
                    holder.gustteamname1 = (TextView) convertView.findViewById(R.id.gustteamname1);
                    holder.wanfa = (TextView) convertView.findViewById(R.id.wanfa);
                    holder.pankou = (TextView) convertView.findViewById(R.id.pankou);
                    holder.yingban = (TextView) convertView.findViewById(R.id.yingban);
                    convertView.setTag(R.id.tag_first, holder);
                    break;
                case 1:
                    convertView = mInflater.inflate(R.layout.myfavourite_item, parent, false);

                    holder.head = (ShapedImageView) convertView.findViewById(R.id.head);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    holder.money = (TextView) convertView.findViewById(R.id.money);
                    holder.endtime = (TextView) convertView.findViewById(R.id.endtime);
                    holder.gamename1 = (TextView) convertView.findViewById(R.id.gamename1);
                    holder.hostteamname1 = (TextView) convertView.findViewById(R.id.hostteamname1);
                    holder.gustteamname1 = (TextView) convertView.findViewById(R.id.gustteamname1);
                    holder.biaoqian1 = (TextView) convertView.findViewById(R.id.biaoqian1);
                    holder.biaoqian2 = (TextView) convertView.findViewById(R.id.biaoqian2);
                    holder.biaoqian3 = (TextView) convertView.findViewById(R.id.biaoqian3);
                    holder.biaoqian4 = (TextView) convertView.findViewById(R.id.biaoqian4);
                    holder.shenglvtx = (TextView) convertView.findViewById(R.id.shenglvtx);
                    holder.shenglv = (TextView) convertView.findViewById(R.id.shenglv);

                    holder.matchs = (LinearLayout) convertView.findViewById(R.id.matchs);
                    holder.match1 = convertView.findViewById(R.id.match1);
                    holder.macthkey1 = (TextView) convertView.findViewById(R.id.macthkey1);

                    //holder.lasttime = (TextView) convertView.findViewById(R.id.lasttime);
                    holder.content = (TextView) convertView.findViewById(R.id.content);
                    holder.matchview = convertView.findViewById(R.id.matchview);
                    holder.bottom = convertView.findViewById(R.id.bottom);
                    holder.mFan = convertView.findViewById(R.id.result_Fan);
                    //holder.sendtime = (TextView) convertView.findViewById(R.id.sendtime);
                    convertView.setTag(R.id.tag_second, holder);
                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case 0:
                    holder = (ViewHolder) convertView.getTag(R.id.tag_first);
                    break;
                case 1:
                    holder = (ViewHolder) convertView.getTag(R.id.tag_second);
                    break;
            }
        }
        xUtilsImageUtils.display(holder.head, article.getAvatar(), R.drawable.defaultpic, true);
        holder.name.setText(article.getNickname());
        if ("0".equals(article.getPrice())) {
            holder.money.setText("免费");
        } else {
            holder.money.setText(article.getPrice() + "金币");
        }
        if (article.getMatch() != null &&
                article.getMatch().size() > 0) {
            holder.gamename1.setText(article.getMatch().get(0).getLs_cname());
            holder.hostteamname1.setText(article.getMatch().get(0).getZhuname());
            holder.gustteamname1.setText(article.getMatch().get(0).getKename());
        }
        switch (getItemViewType(position)) {
            case 0:
                if (article.getMatch() != null &&
                        article.getMatch().size() > 0) {
                    holder.endtime.setText(article.getMatch().get(0).getSc_time() + "");
                    holder.wanfa.setText(article.getMatch().get(0).getWanfa() + "");
                    holder.pankou.setText(article.getMatch().get(0).getPankou() + "");
                    String gameresult = "";
                    int color = _this.getResources().getColor(R.color.textred);
                    switch (article.getMatch().get(0).getGameresult()) {
                        case -1:
                            gameresult = "";
                            break;
                        case 0:
                            gameresult = "输";
                            color = _this.getResources().getColor(R.color.textlightlightgrey);
                            break;
                        case 1:
                            gameresult = "输半 ";
                            color = _this.getResources().getColor(R.color.textlightgrey);
                            break;
                        case 2:
                            gameresult = "走 ";
                            color = _this.getResources().getColor(R.color.textlightlightgrey);
                            break;
                        case 3:
                            gameresult = "赢半";
                            color = _this.getResources().getColor(R.color.textred);
                            break;
                        case 4:
                            gameresult = "赢";
                            color = _this.getResources().getColor(R.color.textred);
                            break;
                        case 5:
                            gameresult = "命中";
                            color = _this.getResources().getColor(R.color.textred);
                            break;
                        case 6:
                            gameresult = "未中";
                            color = _this.getResources().getColor(R.color.textlightlightgrey);
                            break;
                        case 7:
                            gameresult = "二中一";
                            color = _this.getResources().getColor(R.color.textred);
                            break;
                        default:
                            break;
                    }
                    holder.yingban.setTextColor(color);
                    holder.yingban.setText(gameresult);
                }

                break;
            case 1:
                String biaoqian = article.getType_text();
                holder.biaoqian1.setVisibility(View.GONE);
                holder.biaoqian2.setVisibility(View.GONE);
                holder.biaoqian3.setVisibility(View.GONE);
                holder.biaoqian4.setVisibility(View.GONE);
                if(article.isIf_roback()==1){
                    holder.mFan.setVisibility(View.VISIBLE);
                }else {
                    holder.mFan.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(biaoqian)) {
                    if (biaoqian.contains("</span> <span class=\"t-tag-i\">")) {

                        String[] biaoqians = biaoqian.split("</span> <span class=\"t-tag-i\">");
                        try {
                            if (biaoqians.length >= 2) {
                                String biaoqian1 = biaoqians[0].substring(biaoqians[0].indexOf(">") + 1, biaoqians[0].length()).trim();
                                if (biaoqian1.trim().length() > 7) {
                                    holder.biaoqian1.setText(biaoqian1.substring(0, 7) + "...");
                                } else {
                                    holder.biaoqian1.setText(biaoqian1);
                                }
                                holder.biaoqian1.setVisibility(View.VISIBLE);

                                String biaoqian2 = biaoqians[1].replace("</span>", "").trim();
                                if (biaoqian2.trim().length() > 7) {
                                    holder.biaoqian2.setText(biaoqian2.substring(0, 7) + "...");
                                } else {
                                    holder.biaoqian2.setText(biaoqian2);
                                }
                                holder.biaoqian2.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            biaoqian = biaoqian.replace("</span>", "");
                            biaoqian = biaoqian.substring(biaoqian.indexOf(">") + 1, biaoqian.length()).trim();
                            if (biaoqian.trim().length() > 7) {
                                holder.biaoqian1.setText(biaoqian.substring(0, 7) + "...");
                            } else {
                                holder.biaoqian1.setText(biaoqian);
                            }
                            holder.biaoqian1.setVisibility(View.VISIBLE);
                            holder.biaoqian2.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                if (article.getMatch() != null &&
                        article.getMatch().size() > 0) {
                    String matchKey = article.getMatch().get(0).getMatchKey();
                    if (TextUtils.isEmpty(matchKey)) {
                        holder.macthkey1.setVisibility(View.GONE);
                    } else {
                        holder.macthkey1.setVisibility(View.VISIBLE);
                        holder.macthkey1.setText(matchKey);
                    }
                    holder.matchs.removeAllViews();
                    if (article.getMatch().size() > 1) {
                        for (int i = 1; i < article.getMatch().size(); i++) {
                            Match rzm2 = article.getMatch().get(i);
                            View otherMatch = View.inflate(_this, R.layout.matchbase_item, null);
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
                            holder.matchs.addView(otherMatch);
                        }
                    }
                }

                if (_typeFovourite == 0) {
                    //holder.sendtime.setText(article.getPublish_time() + "");
                    holder.bottom.setVisibility(View.VISIBLE);
                    holder.matchview.setVisibility(View.VISIBLE);
                    holder.content.setVisibility(View.GONE);
                    //holder.lasttime.setVisibility(View.GONE);
                    if (_type == 1) {
                        holder.endtime.setVisibility(View.GONE);
                        setLv(holder.shenglvtx, holder.shenglv, "盈利率", article.getYinglilv());
                    } else if (_type == 0) {
                        holder.endtime.setText(article.getMatch().get(0).getSc_time());
                        holder.endtime.setVisibility(View.VISIBLE);

                        setLv(holder.shenglvtx, holder.shenglv, "胜率", article.getShenglv());
                    } else if (_type == 3) {
                        if (article.getArt_type() == 0) {
                            holder.endtime.setText(article.getMatch().get(0).getSc_time());
                            holder.endtime.setVisibility(View.VISIBLE);
                            setLv(holder.shenglvtx, holder.shenglv, "胜率", article.getShenglv());
                        }
                        if (article.getArt_type() == 1) {
                            holder.endtime.setVisibility(View.GONE);
                            setLv(holder.shenglvtx, holder.shenglv, "盈利率", article.getYinglilv());
                        }
                    }

                }
                if (_typeFovourite == 2) {
                    if (_type == 1) {
                        holder.endtime.setVisibility(View.GONE);
                    } else if (_type == 0) {
                        holder.endtime.setText(article.getMatch().get(0).getSc_time());
                        holder.endtime.setVisibility(View.VISIBLE);
                    } else if (_type == 3) {
                        if (article.getArt_type() == 0) {
                            holder.endtime.setText(article.getMatch().get(0).getSc_time());
                            holder.endtime.setVisibility(View.VISIBLE);
                        }
                        if (article.getArt_type() == 1) {
                            holder.endtime.setVisibility(View.GONE);
                        }
                    }
                    //holder.sendtime.setText(article.getPublish_time() + "");
                    holder.bottom.setVisibility(View.VISIBLE);
                    holder.matchview.setVisibility(View.VISIBLE);
                    holder.shenglvtx.setVisibility(View.GONE);
                    holder.shenglv.setVisibility(View.GONE);
                    holder.content.setVisibility(View.GONE);
                    //holder.lasttime.setVisibility(View.GONE);
                }
                if (_typeFovourite == 1) {
                    holder.shenglvtx.setVisibility(View.GONE);
                    holder.shenglv.setVisibility(View.GONE);
                    //holder.lasttime.setText(article.getPublish_time());
                    holder.content.setText(article.getContent());
                    holder.bottom.setVisibility(View.GONE);
                    holder.matchview.setVisibility(View.GONE);
                    //holder.lasttime.setVisibility(View.VISIBLE);
                    holder.content.setVisibility(View.VISIBLE);
                }

                break;
        }
        holder.head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(_this, ZhuanJiaDetailActivity.class);
                intent.putExtra("uid", article.getUid());
                _this.startActivity(intent);
            }
        });
        return convertView;

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

    class ViewHolder {
        public ShapedImageView head;
        public TextView name;
        public TextView money;

        public TextView gamename1;
        public TextView hostteamname1, gustteamname1;
        public TextView endtime;
        public TextView wanfa;
        public TextView pankou;
        public TextView yingban;

        public TextView biaoqian1;
        public TextView biaoqian2;
        public TextView biaoqian3;
        public TextView biaoqian4;
        public TextView shenglvtx, shenglv;
        public LinearLayout matchs;
        public View match1;
        public TextView macthkey1;
        public TextView content;
        //public TextView lasttime;

        public View matchview;
        public View bottom;
        public ImageView mFan;
        //public TextView sendtime;
    }
}
