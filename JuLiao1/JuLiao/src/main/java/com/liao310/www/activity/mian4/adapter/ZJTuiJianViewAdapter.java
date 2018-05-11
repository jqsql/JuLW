package com.liao310.www.activity.mian4.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.domain.login.User;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import java.util.List;

/**
 * Created by lijuan on 2016/9/12.
 */
public class ZJTuiJianViewAdapter extends BaseAdapter {
    private List<User> mDatas;
    private LayoutInflater inflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    public ZJTuiJianViewAdapter(Context context, List<User> mDatas, int curIndex, int pageSize) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tuijian_item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.Main_TuiJian_Name);
            viewHolder.msg =  convertView.findViewById(R.id.Main_TuiJian_Msg);
            viewHolder.head = convertView.findViewById(R.id.Main_TuiJian_head);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        } /** * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize */
        int pos = position + curIndex * pageSize;
        User user=mDatas.get(pos);
        viewHolder.name.setText(user.getNickname());
        xUtilsImageUtils.display(viewHolder.head, user.getAvatar(), R.drawable.defaultpic, true);
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView msg;
        public ShapedImageView head;
    }
}

