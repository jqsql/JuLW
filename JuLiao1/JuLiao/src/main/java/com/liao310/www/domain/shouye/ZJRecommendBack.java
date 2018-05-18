package com.liao310.www.domain.shouye;

import com.liao310.www.domain.version.Back;

import java.util.List;

public class ZJRecommendBack extends Back{
	List<ZJRecommend> data;

	public List<ZJRecommend> getData() {
		return data;
	}

	public void setData(List<ZJRecommend> data) {
		this.data = data;
	}
}
