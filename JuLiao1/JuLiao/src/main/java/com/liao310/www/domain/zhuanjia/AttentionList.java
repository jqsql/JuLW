package com.liao310.www.domain.zhuanjia;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class AttentionList {
    private int total;
    private AttentionListT expert_items;


    public AttentionListT getExpert_items() {
        return expert_items;
    }

    public void setExpert_items(AttentionListT expert_items) {
        this.expert_items = expert_items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



    public class AttentionListT{
        private List<ZhuanJia> jx;
        private List<ZhuanJia> jc;
        public List<ZhuanJia> getJx() {
            return jx;
        }

        public void setJx(List<ZhuanJia> jx) {
            this.jx = jx;
        }

        public List<ZhuanJia> getJc() {
            return jc;
        }

        public void setJc(List<ZhuanJia> jc) {
            this.jc = jc;
        }
    }
}
