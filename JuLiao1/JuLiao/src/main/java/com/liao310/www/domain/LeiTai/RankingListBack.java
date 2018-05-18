package com.liao310.www.domain.LeiTai;

import com.liao310.www.domain.version.Back;

import java.util.List;

public class RankingListBack extends Back{
	List<RankingBean> data;

	public List<RankingBean> getData() {
		return data;
	}

	public void setData(List<RankingBean> data) {
		this.data = data;
	}

}
