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
import com.liao310.www.domain.LeiTai.ZhuanJBuyBean;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.RoundProgressBar;
import com.liao310.www.widget.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

public class ZhuanJiaBuyAdapter extends BaseAdapter {
    private List<ZhuanJBuyBean> zhuanjiaLists;
    private Context _this;

    public ZhuanJiaBuyAdapter(Context con) {
        _this = con;
    }

    public void setDate(ArrayList<ZhuanJBuyBean> orderListsFrom) {
        this.zhuanjiaLists = orderListsFrom;
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
    public ZhuanJBuyBean getItem(int position) {
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_buy_item, parent, false);
            holder.mHead = (ShapedImageView) convertView.findViewById(R.id.head);
            holder.mName = (TextView) convertView.findViewById(R.id.name);
            holder.mBiaoqian=convertView.findViewById(R.id.LianSai_Biaoqian);
            holder.mLianSai_Name=convertView.findViewById(R.id.LianSai_Name);
            holder.mLianSai_Time=convertView.findViewById(R.id.LianSai_Time);
            holder.mLeiTai_Buy_zhu=convertView.findViewById(R.id.LeiTai_Buy_zhu);
            holder.mLeiTai_Buy_Number=convertView.findViewById(R.id.LeiTai_Buy_Number);
            holder.mLeiTai_Buy_ke=convertView.findViewById(R.id.LeiTai_Buy_ke);
            holder.mToLook=convertView.findViewById(R.id.result_State);
            holder.mMoney=convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ZhuanJBuyBean zhuanjia = zhuanjiaLists.get(position);
        holder.mMoney.setVisibility(View.GONE);
        if(zhuanjia!=null) {
            xUtilsImageUtils.display(holder.mHead, zhuanjia.getAvatar(), R.drawable.defaultpic, true);
            holder.mName.setText(zhuanjia.getNickname());
            holder.mBiaoqian.setText(zhuanjia.getRecom_play());

            holder.mLianSai_Name.setText(zhuanjia.getLs_cname());
            holder.mLianSai_Time.setText(zhuanjia.getSc_time());
            holder.mLeiTai_Buy_zhu.setText(zhuanjia.getZhu_name());
            holder.mLeiTai_Buy_ke.setText(zhuanjia.getKe_name());
            if(zhuanjia.getResult()==-1){
                //未结算
                holder.mLeiTai_Buy_Number.setText("VS");
            }else {
                holder.mLeiTai_Buy_Number.setText(zhuanjia.getZhu_score()+"-"+zhuanjia.getKe_score());
            }
            switch (zhuanjia.getResult()) {
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
        return convertView;

    }

    class ViewHolder {
        public ShapedImageView mHead;
        public TextView mName;
        public TextView mBiaoqian;
        public TextView mLianSai_Name;
        public TextView mLianSai_Time;
        public TextView mLeiTai_Buy_zhu;
        public TextView mLeiTai_Buy_Number;
        public TextView mLeiTai_Buy_ke;
        public TextView mToLook;
        public TextView mMoney;

    }
}
