package com.liao310.www.utils.BaseRecyclerAdapterUtils.interfaces;


import com.liao310.www.utils.BaseRecyclerAdapterUtils.BaseViewHolder;

/**
 * recycleView  item点击事件
 */

public interface OnRecycleItemClickListener<T> {
    void onClick(BaseViewHolder holder, T t, int position);
}
