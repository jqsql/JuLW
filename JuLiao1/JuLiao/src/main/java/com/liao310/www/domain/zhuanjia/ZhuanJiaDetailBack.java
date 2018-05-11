package com.liao310.www.domain.zhuanjia;

import com.liao310.www.domain.shouye.ArticleList;

public class ZhuanJiaDetailBack{
	ZhuanJia  person;
	ArticleList recom_items;
	public ZhuanJia getPerson() {
		return person;
	}
	public void setPerson(ZhuanJia person) {
		this.person = person;
	}
	public ArticleList getRecom_items() {
		return recom_items;
	}
	public void setRecom_items(ArticleList recom_items) {
		this.recom_items = recom_items;
	}
	
}
