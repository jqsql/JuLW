package com.liao310.www.domain.shouye;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "LunBoTu")
public class LunBoTu {
	@Column(name = "_id",isId = true,autoGen = true)  
	public int _id;  
	@Column(name = "param")
	String param;
	@Column(name = "info")
	String info;
	@Column(name = "img_url")
	String img_url;
	@Column(name = "url")
	String url;
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "LunBoTu{" +
				"_id=" + _id +
				", param='" + param + '\'' +
				", info='" + info + '\'' +
				", img_url='" + img_url + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
