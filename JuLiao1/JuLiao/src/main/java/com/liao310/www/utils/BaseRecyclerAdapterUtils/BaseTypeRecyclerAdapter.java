package com.liao310.www.utils.BaseRecyclerAdapterUtils;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多种布局的RecyclerAdapter适配器
 */

public abstract class BaseTypeRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    private TypeViewSupport mViewSupport;
    private Context mContext;
    private List<T> mList;

    public interface TypeViewSupport<T> {
        int getLayoutId(int itemType);

        int getItemViewType(int position, T t);
    }

    public BaseTypeRecyclerAdapter(Context context, List<T> list, TypeViewSupport ViewSupport, boolean isRefresh) {
        super(context, -1, list, isRefresh);
        mList = list;
        mContext = context;
        mViewSupport = ViewSupport;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null)
            return mViewSupport.getItemViewType(position, mList.get(position));
        else
            return 0;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mViewSupport.getLayoutId(viewType);
        BaseViewHolder holder = BaseViewHolder.get(mContext, parent, layoutId);
        return holder;
    }
}
