package com.liao310.www.activity.mian4.zhuanjia.detail;

import java.util.ArrayList;
import java.util.List;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.match.MatchDetailActivity;
import com.liao310.www.domain.match.Match;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZhuanJiaDetailListAdapter extends BaseAdapter {
    private ArrayList<Article> articles;
    private Context _this;
    private int _type;//0 精选，1竞彩 ，2竞猜

    public ZhuanJiaDetailListAdapter(Context con) {
        _this = con;
    }

    public void setDate(ArrayList<Article> orderListsFrom) {
        this.articles = orderListsFrom;
    }

    public void setType(int type) {
        _type = type;
    }

    @Override
    public int getCount() {
        if (articles == null) {
            return 0;
        } else {
            return articles.size();
        }
    }

    @Override
    public Article getItem(int position) {
        if (articles == null) {
            return null;
        } else {
            return articles.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        //多少种布局
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return _type;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case 0:
                    convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_jx_new_item, parent, false);
                    holder.time = (TextView) convertView.findViewById(R.id.time);
                    holder.date = (TextView) convertView.findViewById(R.id.date);
                    //holder.state = (TextView) convertView.findViewById(R.id.state);
                    holder.biaoqian1 = (TextView) convertView.findViewById(R.id.biaoqian1);
                    holder.biaoqian2 = (TextView) convertView.findViewById(R.id.biaoqian2);
                    holder.biaoqian3 = (TextView) convertView.findViewById(R.id.biaoqian3);
                    holder.biaoqian4 = (TextView) convertView.findViewById(R.id.biaoqian4);
                    holder.content = (TextView) convertView.findViewById(R.id.content);
                    holder.match = convertView.findViewById(R.id.match);
                    holder.matchkey = (TextView) convertView.findViewById(R.id.matchkey);
                    holder.gameinfo = (TextView) convertView.findViewById(R.id.gameinfo);
                    holder.mResultState=convertView.findViewById(R.id.result_State);
                    holder.mFan=convertView.findViewById(R.id.result_Fan);
                    break;
                case 1:
                    convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_jc3_new_item, parent, false);
                    holder.biaoqian1 = (TextView) convertView.findViewById(R.id.biaoqian1);
                    holder.biaoqian2 = (TextView) convertView.findViewById(R.id.biaoqian2);
                    holder.biaoqian3 = (TextView) convertView.findViewById(R.id.biaoqian3);
                    holder.biaoqian4 = (TextView) convertView.findViewById(R.id.biaoqian4);
                    holder.content = (TextView) convertView.findViewById(R.id.content);
                    holder.match1 = convertView.findViewById(R.id.match1);
                    holder.matchkey1 = (TextView) convertView.findViewById(R.id.matchkey1);
                    holder.gameinfo1 = (TextView) convertView.findViewById(R.id.gameinfo1);

                    holder.match2 = convertView.findViewById(R.id.match2);
                    holder.matchkey2 = (TextView) convertView.findViewById(R.id.matchkey2);
                    holder.gameinfo2 = (TextView) convertView.findViewById(R.id.gameinfo2);
                    holder.time = (TextView) convertView.findViewById(R.id.time);
                    holder.date = (TextView) convertView.findViewById(R.id.date);

                    //holder.result = (ImageView) convertView.findViewById(R.id.result);
                    holder.mResultState=convertView.findViewById(R.id.result_State);
                    holder.mFan=convertView.findViewById(R.id.result_Fan);
                    break;
                case 2:
                    convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_buy_item, parent, false);
                    /*holder.state = (TextView) convertView.findViewById(R.id.state);
                    holder.vs = (TextView) convertView.findViewById(R.id.vs);
                    holder.endtime = (TextView) convertView.findViewById(R.id.endtime);
                    holder.wanfa = (TextView) convertView.findViewById(R.id.wanfa);
                    holder.pankou = (TextView) convertView.findViewById(R.id.pankou);
                    holder.gamename = (TextView) convertView.findViewById(R.id.gamename);
                    holder.hostteamname = (TextView) convertView.findViewById(R.id.hostteamname);
                    holder.gustteamname = (TextView) convertView.findViewById(R.id.gustteamname);*/
                    //holder.mHead = (ShapedImageView) convertView.findViewById(R.id.head);
                    //holder.mName = (TextView) convertView.findViewById(R.id.name);
                    holder.mBiaoqian=convertView.findViewById(R.id.LianSai_Biaoqian);
                    holder.mLianSai_Name=convertView.findViewById(R.id.LianSai_Name);
                    holder.mLianSai_Time=convertView.findViewById(R.id.LianSai_Time);
                    holder.mLeiTai_Buy_zhu=convertView.findViewById(R.id.LeiTai_Buy_zhu);
                    holder.mLeiTai_Buy_Number=convertView.findViewById(R.id.LeiTai_Buy_Number);
                    holder.mLeiTai_Buy_ke=convertView.findViewById(R.id.LeiTai_Buy_ke);
                    holder.mToLook=convertView.findViewById(R.id.result_State);
                    break;
            }
            holder.money = convertView.findViewById(R.id.money);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Article favourite = articles.get(position);

        if ("0".equals(favourite.getPrice())) {
            holder.money.setText("免费");
        } else {
            holder.money.setText(favourite.getPrice() + "金币");
        }
        switch (type) {//0精选，1竞彩，2竞猜
            case 0:
                biaoqian(favourite, holder);
                if (favourite.getMatch() != null &&
                        favourite.getMatch().size() > 0) {
                    String matchKey = favourite.getMatch().get(0).getMatchKey();
                    if (TextUtils.isEmpty(matchKey)) {
                        holder.matchkey.setVisibility(View.GONE);
                    } else {
                        holder.matchkey.setVisibility(View.VISIBLE);
                        holder.matchkey.setText(matchKey);
                    }
                    String info = favourite.getMatch().get(0).getLs_cname() + "  "
                            + favourite.getMatch().get(0).getZhuname() + "  VS  "
                            + favourite.getMatch().get(0).getKename() + "  "
                            + favourite.getMatch().get(0).getSc_time();
                    holder.gameinfo.setText(info);
                    holder.match.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(_this, MatchDetailActivity.class);
                            intent.putExtra("aid", favourite.getMatch().get(0).getAid());
                            intent.putExtra("cid", favourite.getMatch().get(0).getCid());
                            _this.startActivity(intent);
                        }
                    });
                }
                if(favourite.isIf_roback()==1){
                    holder.mFan.setVisibility(View.VISIBLE);
                }else {
                    holder.mFan.setVisibility(View.GONE);
                }

                //String artivleresult = "";
               // Drawable background = _this.getResources().getDrawable(R.drawable.cornerfullred);
                switch (favourite.getArticlresult()) {
                    case -1:
                       // artivleresult = "";
                        //background = null;
                        holder.mResultState.setText("立即查看");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.black));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_app_4);
                        break;
                    case 0:
                        holder.mResultState.setText("失  手");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_grey_4);
                        break;
                    case 1:
                        //artivleresult = "黑";
                        //background = _this.getResources().getDrawable(R.drawable.cornerfullblack);
                        holder.mResultState.setText("失  手");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_grey_4);
                        break;
                    case 2:
                        //artivleresult = "走";
                        //background = _this.getResources().getDrawable(R.drawable.cornerfullgrey15dp);
                        holder.mResultState.setText("平  手");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.black));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_yellow_4);
                        break;
                    case 3:
                        holder.mResultState.setText("红  单");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.white));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_red_4);
                        break;
                    case 4:
                        //artivleresult = "红";
                       // background = _this.getResources().getDrawable(R.drawable.cornerfullred);
                        holder.mResultState.setText("红  单");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.white));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_red_4);
                        break;
                    default:
                        break;
                }
                //holder.state.setBackgroundDrawable(background);
                //holder.state.setText(artivleresult);

                break;
            case 1:
                biaoqian(favourite, holder);
                if (favourite.getMatch() != null &&
                        favourite.getMatch().size() > 0) {
                    String matchKey1 = favourite.getMatch().get(0).getMatchKey();
                    if (TextUtils.isEmpty(matchKey1)) {
                        holder.matchkey1.setVisibility(View.GONE);
                    } else {
                        holder.matchkey1.setVisibility(View.VISIBLE);
                        holder.matchkey1.setText(matchKey1);
                    }
                    String info1 = favourite.getMatch().get(0).getLs_cname() + "  "
                            + favourite.getMatch().get(0).getZhuname() + "  VS  "
                            + favourite.getMatch().get(0).getKename();
                    holder.gameinfo1.setText(info1);
                    holder.match1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(_this, MatchDetailActivity.class);
                            intent.putExtra("aid", favourite.getMatch().get(0).getAid());
                            intent.putExtra("cid", favourite.getMatch().get(0).getCid());
                            _this.startActivity(intent);
                        }
                    });
                    if(favourite.getMatch().size()>=2) {
                        String matchKey2 = favourite.getMatch().get(1).getMatchKey();
                        if (TextUtils.isEmpty(matchKey2)) {
                            holder.matchkey2.setVisibility(View.GONE);
                        } else {
                            holder.matchkey2.setVisibility(View.VISIBLE);
                            holder.matchkey2.setText(matchKey2);
                        }
                        String info2 = favourite.getMatch().get(1).getLs_cname() + "  "
                                + favourite.getMatch().get(1).getZhuname() + "  VS  "
                                + favourite.getMatch().get(1).getKename();
                        holder.gameinfo2.setText(info2);
                        holder.match2.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Intent intent = new Intent(_this, MatchDetailActivity.class);
                                intent.putExtra("aid", favourite.getMatch().get(1).getAid());
                                intent.putExtra("cid", favourite.getMatch().get(1).getCid());
                                _this.startActivity(intent);
                            }
                        });
                    }
                }
                if(favourite.isIf_roback()==1){
                    holder.mFan.setVisibility(View.VISIBLE);
                }else {
                    holder.mFan.setVisibility(View.GONE);
                }
                switch (favourite.getArticlresult()) {
                    case -1:
                        //holder.result.setVisibility(View.INVISIBLE);
                        holder.mResultState.setText("立即查看");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.black));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_app_4);
                        break;
                    case 5:
                        //holder.result.setImageResource(R.drawable.zhong);
                        //holder.result.setVisibility(View.VISIBLE);
                        holder.mResultState.setText("命  中");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.white));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_red_4);
                        break;
                    case 6:
                        //holder.result.setImageResource(R.drawable.weizhong);
                        //holder.result.setVisibility(View.VISIBLE);
                        holder.mResultState.setText("未  中");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_grey_4);
                        break;
                    case 7:
                        //holder.result.setImageResource(R.drawable.zhong21);
                        //holder.result.setVisibility(View.VISIBLE);
                        holder.mResultState.setText("二中一");
                        holder.mResultState.setTextColor(_this.getResources().getColor(R.color.white));
                        holder.mResultState.setBackgroundResource(R.drawable.shape_red_4);
                        break;
                    default:
                        break;
                }
                break;

            case 2:
                if(favourite!=null) {
                    /*xUtilsImageUtils.display(holder.mHead, favourite.getAvatar(), R.drawable.defaultpic, true);
                    holder.mName.setText(favourite.getNickname());*/
                    if(favourite.getMatch()!=null && favourite.getMatch().size()>0) {
                        holder.mBiaoqian.setText(favourite.getMatch().get(0).getWanfa());

                        holder.mLianSai_Name.setText(favourite.getMatch().get(0).getLs_cname());
                        holder.mLianSai_Time.setText(favourite.getMatch().get(0).getSc_time());
                        holder.mLeiTai_Buy_zhu.setText(favourite.getMatch().get(0).getZhuname());
                        holder.mLeiTai_Buy_ke.setText(favourite.getMatch().get(0).getKename());
                        if (favourite.getMatch().get(0).getGameresult() == -1) {
                            //未结算
                            holder.mLeiTai_Buy_Number.setText("VS");
                        } else {
                            holder.mLeiTai_Buy_Number.setText(favourite.getMatch().get(0).getZhunamescore()
                                    + "-" + favourite.getMatch().get(0).getKenamescore());
                        }
                        switch (favourite.getMatch().get(0).getGameresult()) {
                            case -1:
                                // artivleresult = "";
                                //background = null;
                                holder.mToLook.setText("立即查看");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.black));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_app_4);
                                break;
                            case 0:
                                holder.mToLook.setText("输");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_grey_4);
                                break;
                            case 1:
                                holder.mToLook.setText("输  半");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_grey_4);
                                break;
                            case 2:
                                holder.mToLook.setText("走");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.black));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_yellow_4);
                                break;
                            case 3:
                                holder.mToLook.setText("赢");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.white));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_red_4);
                                break;
                            case 4:
                                holder.mToLook.setText("赢  半");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.white));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_red_4);
                                break;
                            case 5:
                                holder.mToLook.setText("命  中");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.white));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_red_4);
                                break;
                            case 6:
                                holder.mToLook.setText("未命中");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.whiteGrey));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_grey_4);
                                break;
                            case 7:
                                holder.mToLook.setText("二中一");
                                holder.mToLook.setTextColor(_this.getResources().getColor(R.color.white));
                                holder.mToLook.setBackgroundResource(R.drawable.shape_red_4);
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
        }
        return convertView;

    }

    public void biaoqian(Article favourite, ViewHolder holder) {
        String biaoqian = favourite.getType_text();
        if (TextUtils.isEmpty(biaoqian)) {
            holder.biaoqian1.setVisibility(View.GONE);
            holder.biaoqian2.setVisibility(View.GONE);
            holder.biaoqian3.setVisibility(View.GONE);
            holder.biaoqian4.setVisibility(View.GONE);
        } else {
            String[] tagDatas = biaoqian.split("<span class=\"t-tag-i\">");
            List<String> TagList = new ArrayList<>();
            for (int i = 0; i < tagDatas.length; i++) {
                if (!tagDatas[i].isEmpty())
                    TagList.add(tagDatas[i].substring(0, tagDatas[i].indexOf("</span>")));
            }
            if (TagList.size() != 0) {
                holder.biaoqian1.setVisibility(View.VISIBLE);
                holder.biaoqian2.setVisibility(View.VISIBLE);
                holder.biaoqian3.setVisibility(View.VISIBLE);
                holder.biaoqian4.setVisibility(View.VISIBLE);
                if (TagList.size() >= 4) {
                    holder.biaoqian1.setText(TagList.get(0));
                    holder.biaoqian2.setText(TagList.get(1));
                    holder.biaoqian3.setText(TagList.get(2));
                    holder.biaoqian4.setText(TagList.get(3));
                } else if (TagList.size() >= 3) {
                    holder.biaoqian1.setText(TagList.get(0));
                    holder.biaoqian2.setText(TagList.get(1));
                    holder.biaoqian3.setText(TagList.get(2));
                    holder.biaoqian4.setVisibility(View.GONE);
                } else if (TagList.size() >= 2) {
                    holder.biaoqian1.setText(TagList.get(0));
                    holder.biaoqian2.setText(TagList.get(1));
                    holder.biaoqian3.setVisibility(View.GONE);
                    holder.biaoqian4.setVisibility(View.GONE);
                } else if (TagList.size() >= 1) {
                    holder.biaoqian1.setText(TagList.get(0));
                    holder.biaoqian2.setVisibility(View.GONE);
                    holder.biaoqian3.setVisibility(View.GONE);
                    holder.biaoqian4.setVisibility(View.GONE);
                }
            } else {
                holder.biaoqian1.setVisibility(View.GONE);
                holder.biaoqian2.setVisibility(View.GONE);
                holder.biaoqian3.setVisibility(View.GONE);
                holder.biaoqian4.setVisibility(View.GONE);
            }
            /*if (biaoqian.contains("</span> <span class=\"t-tag-i\">")) {

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
            }*/
        }
        holder.date.setText(favourite.getRecom_date());
        holder.time.setText(favourite.getRecom_time());
        holder.content.setText(favourite.getContent());
    }

    class ViewHolder {
        public TextView time;
        public TextView date;
        public TextView state;

        public TextView biaoqian1;
        public TextView biaoqian2;
        public TextView biaoqian3;
        public TextView biaoqian4;
        public TextView money;
        public TextView content;

        public View match;
        public TextView matchkey;
        public TextView gameinfo;


        public View match1;
        public TextView matchkey1;
        public TextView gameinfo1;
        public View match2;
        public TextView matchkey2;
        public TextView gameinfo2;

        public TextView gamename;
        public TextView hostteamname, gustteamname;
        public TextView endtime;
        public TextView wanfa, pankou;
        //public ImageView result;
        public TextView vs;

        private TextView mResultState;//结果状态
        private ImageView mFan;//‘返’  标志

        //竞猜
        private TextView mBiaoqian;
        private TextView mLianSai_Name;
        private TextView mLianSai_Time;
        private TextView mLeiTai_Buy_zhu;
        private TextView mLeiTai_Buy_Number;
        private TextView mLeiTai_Buy_ke;
        private TextView mToLook;

    }

    /**
     * 截取5位，后顯示省略
     *
     * @param data
     * @return
     */
    private String setSpliteMessage(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        } else if (data.length() > 5) {
            String str = data.substring(0, 5);
            return str + "...";
        }
        return data;
    }
}
