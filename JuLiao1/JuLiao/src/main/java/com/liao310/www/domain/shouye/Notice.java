package com.liao310.www.domain.shouye;

public class Notice { 
	public int _id;  
	String id;//通知id
    String content;//通知内容
    String rid;//对应专家id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
    
}
