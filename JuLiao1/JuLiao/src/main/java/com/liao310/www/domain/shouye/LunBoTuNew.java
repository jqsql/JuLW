package com.liao310.www.domain.shouye;

public class LunBoTuNew {
	private String id;//id参数
	private String title;//幻灯片标题
	private String picture;//图片地址
	private String jump_type;//跳转地址 news:新闻页 expert:专家页
	private String jump_url;//跳转外链
	private String content;//内容
	private int type;//类型：2不跳转,3外链跳转，4跳转专家，5轮播新闻，6轮播-跳转充值
	private String exp_id;//跳转专家id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getJump_type() {
		return jump_type;
	}

	public void setJump_type(String jump_type) {
		this.jump_type = jump_type;
	}

	public String getJump_url() {
		return jump_url;
	}

	public void setJump_url(String jump_url) {
		this.jump_url = jump_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getExp_id() {
		return exp_id;
	}

	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}
}
