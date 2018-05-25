package com.liao310.www.activity.mian4.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.domain.login.User;
import com.liao310.www.domain.shouye.ZJRecommend;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import java.util.List;

public class ZJTuiJianViewAdapter extends BaseAdapter {
    private List<ZJRecommend> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    public ZJTuiJianViewAdapter(Context context, List<ZJRecommend> mDatas, int curIndex, int pageSize) {
        mContext=context;
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tuijian_item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.Main_TuiJian_Name);
            viewHolder.msg =  convertView.findViewById(R.id.Main_TuiJian_Msg);
            viewHolder.head = convertView.findViewById(R.id.Main_TuiJian_head);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /** * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize */
        int pos = position + curIndex * pageSize;
        ZJRecommend user=mDatas.get(pos);
        viewHolder.name.setText(user.getUsername());
        viewHolder.msg.setText(user.getRe_tag());
        if(user.getRe_tag().isEmpty()){
            viewHolder.msg.setVisibility(View.GONE);
        }else {
            viewHolder.msg.setVisibility(View.VISIBLE);
        }
        try {
            GradientDrawable gd = (GradientDrawable) viewHolder.msg.getBackground();
            if (user.getRe_tag_color() != null && !user.getRe_tag_color().isEmpty()) {
                gd.setColor(Color.parseColor(user.getRe_tag_color()));
            } else {
                gd.setColor(mContext.getResources().getColor(R.color.textgreen));
            }
        }catch (IllegalArgumentException e){
            //抛出颜色异常
        }

        xUtilsImageUtils.display(viewHolder.head, user.getAvatar(), R.drawable.defaultpic, true);
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView msg;
        public ShapedImageView head;
    }
}

