package com.liao310.www.domain.shouye;

import java.util.ArrayList;

public class ArticleList {
	int total;
	ArrayList<Article> items=new ArrayList<Article>();
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<Article> getItems() {
		return items;
	}
	public void setItems(ArrayList<Article> items) {
		this.items = items;
	}
	
}
