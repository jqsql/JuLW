package com.liao310.www.domain.shouye;

import java.util.ArrayList;

import com.liao310.www.domain.version.Back;

public class NoticeBack extends Back{
	ArrayList<Notice> data = new ArrayList<Notice>();

	public ArrayList<Notice> getData() {
		return data;
	}

	public void setData(ArrayList<Notice> data) {
		this.data = data;
	}

}
