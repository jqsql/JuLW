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
import java.util.List;

public class ZJRankingAdapter extends BaseAdapter {
    private List<ZhuanJia> zhuanjiaLists;
    private Context _this;
    //专家模块 1 精选七日总胜率，2七日单场 ，3七日大小球 4 竞彩7日盈利率 5 竞彩7日单关盈利率
    private int _type = 0;

    public ZJRankingAdapter(Context con) {
        _this = con;
    }

    public void setDate(ArrayList<ZhuanJia> orderListsFrom) {
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
    public ZhuanJia getItem(int position) {
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

    @SuppressLint({"ViewHolder", "ResourceAsColor"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ZhuanJia zhuanjia = zhuanjiaLists.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_simple_item, parent, false);
            holder.numberTv = convertView.findViewById(R.id.number_tx);
            holder.numberIv =  convertView.findViewById(R.id.number_im);
            holder.statename =  convertView.findViewById(R.id.statename);
            holder.state =  convertView.findViewById(R.id.state);
            holder.mProgressBar = convertView.findViewById(R.id.zhuanjia_ProgressBar);
            holder.statetail =  convertView.findViewById(R.id.statetail);
            holder.head =  convertView.findViewById(R.id.head);
            holder.name =  convertView.findViewById(R.id.name);
            holder.mDay7HintLayout = convertView.findViewById(R.id.ZJ_PH_YingHintLayout);
            holder.mJCDay7HintLayout = convertView.findViewById(R.id.ZJ_PH_JCHintLayout);
            holder.mZJ_JC_Day7 = convertView.findViewById(R.id.ZJ_JC_Day7);
            holder.mZJ_JC_Day7Y = convertView.findViewById(R.id.ZJ_JC_Day7Y);
            holder.mC0 = convertView.findViewById(R.id.ZJ_PH_Shu);
            holder.mC1 = convertView.findViewById(R.id.ZJ_PH_ShuBan);
            holder.mC2 = convertView.findViewById(R.id.ZJ_PH_Zou);
            holder.mC3 = convertView.findViewById(R.id.ZJ_PH_YingBan);
            holder.mC4 = convertView.findViewById(R.id.ZJ_PH_Ying);
            holder.mLV_Layout = convertView.findViewById(R.id.zhuanjia_ProgressBarLV);
            holder.mLVText = convertView.findViewById(R.id.zhuanjia_ProgressBarLVText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
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
        if(_type == 4 || _type==5){
            holder.mLV_Layout.setVisibility(View.VISIBLE);
            holder.mProgressBar.setVisibility(View.GONE);
        }else {
            holder.mLV_Layout.setVisibility(View.GONE);
            holder.mProgressBar.setVisibility(View.VISIBLE);
        }
        if (_type == 4) {
            holder.mJCDay7HintLayout.setVisibility(View.VISIBLE);
            holder.mDay7HintLayout.setVisibility(View.GONE);
            holder.mLVText.setText(zhuanjia.getDay7Yinglilv()+"%");
            if (zhuanjia != null) {
                if (zhuanjia.getDays7() != null && !zhuanjia.getDays7().isEmpty()) {
                    String[] day7 = zhuanjia.getDays7().split(",");
                    if (day7.length >= 2) {
                        holder.mZJ_JC_Day7.setText(day7[0]+"中"+day7[1]);
                    }
                }
                holder.mZJ_JC_Day7Y.setText(zhuanjia.getDay7Mingzhonglv()+"%");
            }
        } else {
            holder.mJCDay7HintLayout.setVisibility(View.GONE);
            /*holder.statename.setText("胜率");
            holder.statetail.setVisibility(View.VISIBLE);*/
            if (_type == 1) {
                holder.mProgressBar.setProgress((int) Double.parseDouble(zhuanjia.getDay7Shenglv()));
                holder.mDay7HintLayout.setVisibility(View.VISIBLE);
                if (zhuanjia.getDays7() != null && !zhuanjia.getDays7().isEmpty()) {
                    String[] day7 = zhuanjia.getDays7().split(",");
                    if (day7.length >= 5) {
                        holder.mC4.setText(day7[0] + "赢");
                        holder.mC3.setText(day7[1] + "赢半");
                        holder.mC2.setText(day7[2] + "走");
                        holder.mC0.setText(day7[3] + "输");
                        holder.mC1.setText(day7[4] + "输半");
                    }
                }
            } else if (_type == 2) {
                holder.mDay7HintLayout.setVisibility(View.GONE);
                holder.mProgressBar.setProgress((int) Double.parseDouble(zhuanjia.getDay7_dc_sl()));
            } else if (_type == 3) {
                holder.mDay7HintLayout.setVisibility(View.GONE);
                holder.mProgressBar.setProgress((int) Double.parseDouble(zhuanjia.getDay7_dx_sl()));
            }else if(_type==5){
                holder.mDay7HintLayout.setVisibility(View.GONE);
                holder.mLVText.setText(zhuanjia.getDay7_single_yl()+"%");
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
        private LinearLayout mJCDay7HintLayout;//
        private TextView mZJ_JC_Day7;
        private TextView mZJ_JC_Day7Y;
        private TextView mC0;
        private TextView mC1;
        private TextView mC2;
        private TextView mC3;
        private TextView mC4;
        private LinearLayout mLV_Layout;//
        private TextView mLVText;//
    }
}
