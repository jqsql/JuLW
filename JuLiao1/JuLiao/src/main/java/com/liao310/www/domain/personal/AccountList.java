package com.liao310.www.domain.personal;

import java.util.ArrayList;

public class AccountList {
	int total;
	ArrayList<Account> items=new ArrayList<Account>();
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<Account> getItems() {
		return items;
	}
	public void setItems(ArrayList<Account> items) {
		this.items = items;
	}
}
