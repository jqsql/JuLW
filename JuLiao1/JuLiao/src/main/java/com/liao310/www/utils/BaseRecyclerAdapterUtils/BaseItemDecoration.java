package com.liao310.www.utils.BaseRecyclerAdapterUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liao310.www.utils.BaseRecyclerAdapterUtils.util.ManagerType;
import com.liao310.www.utils.UiUtils;

/**
 * 自定义item分隔布局
 */

public class BaseItemDecoration extends RecyclerView.ItemDecoration{
    private ManagerType mManagerType=ManagerType.LINEAR;
    private int leftRight;
    private int topBottom;
    private int size=3;//每一行的个数（Grid）默认3

    public BaseItemDecoration(Context context, int leftRight, int topBottom,ManagerType manager) {
        this.mManagerType=manager;
        this.leftRight = UiUtils.dip2px(leftRight);
        this.topBottom = UiUtils.dip2px(topBottom);
    }
    public BaseItemDecoration(Context context,ManagerType manager) {
        this.mManagerType=manager;
        this.topBottom = UiUtils.dip2px(8);
    }
    public BaseItemDecoration(Context context,int space ,ManagerType manager, int size) {
        this.size=size;
        this.mManagerType=manager;
        this.leftRight=this.topBottom = UiUtils.dip2px(space);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(mManagerType==ManagerType.LINEAR) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            //竖直方向的
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //最后一项需要 bottom
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = 0;
                }
                outRect.bottom = topBottom;
                //outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                //最后一项需要right
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = leftRight;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.bottom = topBottom;
            }
        }else if(mManagerType==ManagerType.GRID){
            outRect.left = leftRight;
            outRect.bottom = topBottom;
            //每一行的第一个不设置left
            if (parent.getChildLayoutPosition(view) %size==0) {
                outRect.left = 0;
            }
        }
    }
}
