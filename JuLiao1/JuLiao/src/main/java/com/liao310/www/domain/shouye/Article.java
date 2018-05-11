package com.liao310.www.domain.shouye;

import java.io.Serializable;
import java.util.ArrayList;

import com.liao310.www.domain.match.Match;

public class Article  implements Serializable{
	String rid  ;//文章id
	int is_vip  ;//是不是vip文章 0：不是，1：是
	int favorite_status;//  //是否收藏
	String last_time;// 最后发布
	String publish_time;//发布日期时间
	String recom_date;//发布日期
	String recom_time ;//发布时间

	String  content; //文章摘要内容
	String price;//文章价格
	String type_text;//文章标签
	int art_type;//0精选，1竞彩，2竞猜
	String  contenttx; //分析内容
	boolean  isShow;//是否显示分析结果
	
	
	String uid  ;//专家id
	String avatar ;// 头像
	String nickname;//专家昵称
	String mingzhong;
	String shenglv;//对应专家胜率
	String yinglilv;//对应专家盈利率
	int cid ;// 彩种id：1亚盘足球，2亚盘篮球，3竞彩足球，4竞彩篮球
	int type ;//  1亚足，2亚篮，3竞足，4竞篮
	int game_num;//赛事总数
	int  articlresult;//-1未结算
	//0输 1输半 2走 3赢半 4赢 ----竞猜，精选
	//5命中， 6未中，7 二中一-----竞彩
	ArrayList<Match> match = new  ArrayList<>();

	private static final long serialVersionUID = 1L;
	public int getArticlresult() {
		return articlresult;
	}
	public void setArticlresult(int articlresult) {
		this.articlresult = articlresult;
	}
	public String getMingzhong() {
		return mingzhong;
	}
	public void setMingzhong(String mingzhong) {
		this.mingzhong = mingzhong;
	}
	public ArrayList<Match> getMatch() {
		return match;
	}
	public void setMatch(ArrayList<Match> match) {
		this.match = match;
	}
	public String getShenglv() {
		return shenglv;
	}
	public void setShenglv(String shenglv) {
		this.shenglv = shenglv;
	}
	public String getYinglilv() {
		return yinglilv;
	}
	public void setYinglilv(String yinglilv) {
		this.yinglilv = yinglilv;
	}

	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getRecom_date() {
		return recom_date;
	}
	public void setRecom_date(String recom_date) {
		this.recom_date = recom_date;
	}
	public String getRecom_time() {
		return recom_time;
	}
	public void setRecom_time(String recom_time) {
		this.recom_time = recom_time;
	}
	public int getIs_vip() {
		return is_vip;
	}
	public void setIs_vip(int is_vip) {
		this.is_vip = is_vip;
	}
	public String getType_text() {
		return type_text;
	}
	public void setType_text(String type_text) {
		this.type_text = type_text;
	}
	public int getGame_num() {
		return game_num;
	}
	public void setGame_num(int game_num) {
		this.game_num = game_num;
	}
	public int getArt_type() {
		return art_type;
	}
	public void setArt_type(int art_type) {
		this.art_type = art_type;
	}

	public int getFavorite_status() {
		return favorite_status;
	}
	public void setFavorite_status(int favorite_status) {
		this.favorite_status = favorite_status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentTX() {
		return contenttx;
	}
	public void setContentTX(String contentTX) {
		this.contenttx = contentTX;
	}
	
}
