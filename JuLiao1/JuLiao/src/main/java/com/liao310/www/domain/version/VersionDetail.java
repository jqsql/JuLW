package com.liao310.www.domain.version;

public class VersionDetail {
	String version;
	String url;
	int isForcedUpdate;//0不，1强制的
	int pageVersion;//轮播图版本号
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getIsForcedUpdate() {
		return isForcedUpdate;
	}
	public void setIsForcedUpdate(int isForcedUpdate) {
		this.isForcedUpdate = isForcedUpdate;
	}
	public int getPageVersion() {
		return pageVersion;
	}
	public void setPageVersion(int pageVersion) {
		this.pageVersion = pageVersion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
