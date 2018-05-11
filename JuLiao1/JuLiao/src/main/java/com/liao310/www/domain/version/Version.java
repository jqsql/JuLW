package com.liao310.www.domain.version;

public class Version extends Back{
	VersionDetail data;
	
	public VersionDetail getDetail() {
		return data;
	}

	public void setDetail(VersionDetail detail) {
		this.data = detail;
	}
}
