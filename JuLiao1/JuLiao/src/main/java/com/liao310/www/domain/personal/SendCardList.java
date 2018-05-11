package com.liao310.www.domain.personal;

import java.util.ArrayList;

public class SendCardList {
	int total;
	ArrayList<SendCard> items=new ArrayList<SendCard>();
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<SendCard> getItems() {
		return items;
	}
	public void setItems(ArrayList<SendCard> items) {
		this.items = items;
	}
}
