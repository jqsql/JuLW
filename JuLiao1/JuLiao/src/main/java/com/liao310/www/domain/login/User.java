package com.liao310.www.domain.login;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "User") 
public class User{
	@Column(name = "_id",isId = true,autoGen = true)  
	public int _id;  
	@Column(name = "uid")
	String  uid;//用户id
	@Column(name = "token")
	String token;//令牌
	@Column(name = "mobile")
	String mobile ;//手机
	@Column(name = "nickname")
	String nickname ;//昵称
	@Column(name = "money")
	String money ;//
	@Column(name = "avatar")
	String avatar ;
	@Column(name = "paytype")
	int paytype;//支付类型
	@Column(name = "invite_code")
	int invite_code ;//邀请码
	int status_type;//安卓 1表示正常，2给应用宝审核用，只显示新闻类
	int new_sign = 0;//1就是新用户
	
	public int getNew_sign() {
		return new_sign;
	}
	public void setNew_sign(int new_sign) {
		this.new_sign = new_sign;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getPaytype() {
		return paytype;
	}
	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}
	public int getInvite_code() {
		return invite_code;
	}
	public void setInvite_code(int invite_code) {
		this.invite_code = invite_code;
	}
	public int getStatus_type() {
		return status_type;
	}
	public void setStatus_type(int status_type) {
		this.status_type = status_type;
	}
	
}
 