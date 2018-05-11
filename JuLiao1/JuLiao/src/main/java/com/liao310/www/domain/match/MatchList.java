package com.liao310.www.domain.match;

import java.util.ArrayList;

public class MatchList {
	int total;
	String time;
	ArrayList<Match> items=new ArrayList<Match>();
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<Match> getItems() {
		return items;
	}
	public void setItems(ArrayList<Match> items) {
		this.items = items;
	}
	public String getDate() {
		return time;
	}
	public void setDate(String date) {
		this.time = date;
	}
	
}
