package com.liao310.www.domain.pay;

import com.liao310.www.domain.version.Back;

import java.util.List;

public class RechargeBack extends Back{
	RechargeData price_list;

	public RechargeData getData() {
		return price_list;
	}

	public void setData(RechargeData data) {
		this.price_list = data;
	}

	public class RechargeData{
		private List<RechargeBean> list_1;//
		private List<RechargeBean> list_2;//

		public List<RechargeBean> getList_1() {
			return list_1;
		}

		public void setList_1(List<RechargeBean> list_1) {
			this.list_1 = list_1;
		}

		public List<RechargeBean> getList_2() {
			return list_2;
		}

		public void setList_2(List<RechargeBean> list_2) {
			this.list_2 = list_2;
		}
	}
}
