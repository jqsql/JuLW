package com.liao310.www.activity.mian4.zhuanjia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.domain.LeiTai.RankingBean;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.RoundProgressBar;
import com.liao310.www.widget.ShapedImageView;

import java.util.ArrayList;

public class ZhuanJiaRankingAdapter extends BaseAdapter {
    private ArrayList<RankingBean> zhuanjiaLists;
    private Context _this;
    //擂台模块 0 总榜，1七日单场 ，2七日大小球 4关注
    private int _type=0;

    public ZhuanJiaRankingAdapter(Context con) {
        _this = con;
    }

    public void setDate(ArrayList<RankingBean> orderListsFrom) {
        this.zhuanjiaLists = orderListsFrom;
    }

    public void setType(int type) {
        _type = type;
    }

    @Override
    public int getCount() {
        if (zhuanjiaLists == null) {
            return 0;
        } else {
            return zhuanjiaLists.size();
        }
    }

    @Override
    public RankingBean getItem(int position) {
        if (zhuanjiaLists == null) {
            return null;
        } else {
            return zhuanjiaLists.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(_type==4){
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RankingBean zhuanjia = zhuanjiaLists.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if(getItemViewType(position)==1){
                convertView = LayoutInflater.from(_this).inflate(R.layout.main_attention_child, parent, false);
                holder.name = convertView.findViewById(R.id.ZJ_JX_NickName);
                holder.mZJ_JX_Ying = convertView.findViewById(R.id.ZJ_JX_Ying);
                holder.mZJ_JX_YingBan = convertView.findViewById(R.id.ZJ_JX_YingBan);
                holder.mZJ_JX_Zou = convertView.findViewById(R.id.ZJ_JX_Zou);
                holder.mZJ_JX_Shu = convertView.findViewById(R.id.ZJ_JX_Shu);
                holder.mZJ_JX_ShuBan = convertView.findViewById(R.id.ZJ_JX_ShuBan);
                holder.mNumber = convertView.findViewById(R.id.ZJ_JX_MsgHintText);
                holder.head = convertView.findViewById(R.id.ZJ_JX_Head);
                convertView.setTag(R.id.attention_first,holder);
            }else {
                convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_simple_item, parent, false);
                holder.numberTv = (TextView) convertView.findViewById(R.id.number_tx);
                holder.numberIv =  convertView.findViewById(R.id.number_im);
                holder.statename = (TextView) convertView.findViewById(R.id.statename);
                holder.state = (TextView) convertView.findViewById(R.id.state);
                holder.mProgressBar = convertView.findViewById(R.id.zhuanjia_ProgressBar);
                holder.statetail = (TextView) convertView.findViewById(R.id.statetail);
                holder.head = (ShapedImageView) convertView.findViewById(R.id.head);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.mDay7HintLayout = convertView.findViewById(R.id.ZJ_PH_YingHintLayout);
                holder.mC0 = convertView.findViewById(R.id.ZJ_PH_Shu);
                holder.mC1 = convertView.findViewById(R.id.ZJ_PH_ShuBan);
                holder.mC2 = convertView.findViewById(R.id.ZJ_PH_Zou);
                holder.mC3 = convertView.findViewById(R.id.ZJ_PH_YingBan);
                holder.mC4 = convertView.findViewById(R.id.ZJ_PH_Ying);
                convertView.setTag(R.id.attention_second,holder);
            }

        } else {
            if(getItemViewType(position)==1){
                holder = (ViewHolder) convertView.getTag(R.id.attention_first);
            }else {
                holder = (ViewHolder) convertView.getTag(R.id.attention_second);
            }
        }
        if (_type == 4) {
            if (zhuanjia != null) {
                holder.mZJ_JX_Ying.setText(zhuanjia.getC4() + "赢");
                holder.mZJ_JX_YingBan.setText(zhuanjia.getC3() + "赢半");
                holder.mZJ_JX_Zou.setText(zhuanjia.getC2()+ "走");
                holder.mZJ_JX_Shu.setText(zhuanjia.getC1() + "输");
                holder.mZJ_JX_ShuBan.setText(zhuanjia.getC0() + "输半");
            }
            holder.mNumber.setVisibility(View.GONE);

        }else {
            if (position == 0) {
                holder.numberIv.setText("1");
                holder.numberTv.setVisibility(View.INVISIBLE);
                holder.numberIv.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                holder.numberIv.setText("2");
                holder.numberTv.setVisibility(View.INVISIBLE);
                holder.numberIv.setVisibility(View.VISIBLE);
            } else if (position == 2) {
                holder.numberIv.setText("3");
                holder.numberTv.setVisibility(View.INVISIBLE);
                holder.numberIv.setVisibility(View.VISIBLE);
            } else {
                holder.numberTv.setText((position + 1) + "");
                holder.numberIv.setVisibility(View.INVISIBLE);
                holder.numberTv.setVisibility(View.VISIBLE);
            }
            holder.statename.setText("胜率");
            holder.statetail.setVisibility(View.VISIBLE);
            holder.mProgressBar.setProgress((int) Double.parseDouble(zhuanjia.getJcai_sheng_lv_7()));
            if (_type == 0) {
                holder.mDay7HintLayout.setVisibility(View.VISIBLE);
                holder.mC0.setText(zhuanjia.getC0() + "输");
                holder.mC1.setText(zhuanjia.getC1() + "输半");
                holder.mC2.setText(zhuanjia.getC2() + "走");
                holder.mC3.setText(zhuanjia.getC3() + "赢半");
                holder.mC4.setText(zhuanjia.getC4() + "赢");
            } else {
                holder.mDay7HintLayout.setVisibility(View.GONE);
            }
        }
        xUtilsImageUtils.display(holder.head, zhuanjia.getAvatar(), R.drawable.defaultpic, true);
        holder.name.setText(zhuanjia.getNickname());
        return convertView;

    }

    class ViewHolder {
        public ShapedImageView head;
        public TextView name;

        public TextView numberIv;
        public TextView numberTv;
        public TextView statename;
        public TextView state;
        public TextView statetail;

        public TextView info;
        public TextView time;
        public TextView guanzhu;
        private RoundProgressBar mProgressBar;
        private LinearLayout mDay7HintLayout;//
        private TextView mC0;
        private TextView mC1;
        private TextView mC2;
        private TextView mC3;
        private TextView mC4;


        //关注
        private TextView mZJ_JX_Ying;
        private TextView mZJ_JX_YingBan;
        private TextView mZJ_JX_Zou;
        private TextView mZJ_JX_Shu;
        private TextView mZJ_JX_ShuBan;
        private TextView mNumber;
    }
}
