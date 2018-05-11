package com.liao310.www.domain.match;

import java.io.Serializable;

public class Match {
	int cid ;// 彩种id：1亚盘足球，2亚盘篮球，3竞彩足球
	int type ;//  1亚足，2亚篮，3竞足
	String  aid;// 比赛id
	String  matchKey;//  足球需要的的，如周二007，有就显示，没有就空
	String  ls_cname;//  联赛名
	String  zhuname;// 主队
	String  zhunamescore = "";// 主队
	        
	String  kename ;//  客队
	String  kenamescore = "";// 主队
	String  sc_time;// 比赛时间

	String  pankou_type; //盘口类型:亚洲大小盘，亚洲让分盘
	String  wanfa;//足球：让球（单场），大小球       篮球：让分，大小分
	String  pankou; //盘口
	boolean if_roback;//0不返，1返款
	
	String  wanfa_num;//足球：让球数                       篮球：让分数，大小分数
	String  lv1;//赔率（足球：主胜，篮球：胜，大分）
	String  lv2; //赔率（足球：平）
	String  lv3;   //赔率（足球：主负，篮球：负，小分）

	boolean choose1 = false;//是否选择结果1
	boolean choose2 = false;//是否选择结果2
	boolean choose3 = false;//是否选择结果3

	boolean result1 = false;//结果是否1
	boolean result2 = false;//结果是否2
	boolean result3 = false;//结果是否3
	
	int  gameresult;//-1未结算
	//0输 1输半 2走 3赢半 4赢 ----竞猜，精选
	//5命中， 6未中，7 二中一-----竞彩

	int recom_num;// 该比赛的推荐数

	public boolean isIf_roback() {
		return if_roback;
	}

	public void setIf_roback(boolean if_roback) {
		this.if_roback = if_roback;
	}

	public String getZhunamescore() {
		return zhunamescore;
	}
	public void setZhunamescore(String zhunamescore) {
		this.zhunamescore = zhunamescore;
	}
	public String getKenamescore() {
		return kenamescore;
	}
	public void setKenamescore(String kenamescore) {
		this.kenamescore = kenamescore;
	}
	public String getPankou_type() {
		return pankou_type;
	}
	public void setPankou_type(String pankou_type) {
		this.pankou_type = pankou_type;
	}
	public boolean isChoose1() {
		return choose1;
	}
	public void setChoose1(boolean choose1) {
		this.choose1 = choose1;
	}
	public boolean isChoose2() {
		return choose2;
	}
	public void setChoose2(boolean choose2) {
		this.choose2 = choose2;
	}
	public boolean isChoose3() {
		return choose3;
	}
	public void setChoose3(boolean choose3) {
		this.choose3 = choose3;
	}
	public boolean isResult1() {
		return result1;
	}
	public void setResult1(boolean result1) {
		this.result1 = result1;
	}
	public boolean isResult2() {
		return result2;
	}
	public void setResult2(boolean result2) {
		this.result2 = result2;
	}
	public boolean isResult3() {
		return result3;
	}
	public void setResult3(boolean result3) {
		this.result3 = result3;
	}
	private static final long serialVersionUID = 1L;
	public String getWanfa() {
		return wanfa;
	}
	public void setWanfa(String wanfa) {
		this.wanfa = wanfa;
	}
	public String getPankou() {
		return pankou;
	}
	public void setPankou(String pankou) {
		this.pankou = pankou;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getZhuname() {
		return zhuname;
	}
	public void setZhuname(String zhuname) {
		this.zhuname = zhuname;
	}
	public String getKename() {
		return kename;
	}
	public void setKename(String kename) {
		this.kename = kename;
	}
	public String getLs_cname() {
		return ls_cname;
	}
	public void setLs_cname(String ls_cname) {
		this.ls_cname = ls_cname;
	}
	public String getSc_time() {
		return sc_time;
	}
	public void setSc_time(String sc_time) {
		this.sc_time = sc_time;
	}
	public int getRecom_num() {
		return recom_num;
	}
	public void setRecom_num(int recom_num) {
		this.recom_num = recom_num;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getMatchKey() {
		return matchKey;
	}
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}
	public int getGameresult() {
		return gameresult;
	}
	public void setGameresult(int gameresult) {
		this.gameresult = gameresult;
	}
	public String getWanfa_num() {
		return wanfa_num;
	}
	public void setWanfa_num(String wanfa_num) {
		this.wanfa_num = wanfa_num;
	}
	public String getLv1() {
		return lv1;
	}
	public void setLv1(String lv1) {
		this.lv1 = lv1;
	}
	public String getLv2() {
		return lv2;
	}
	public void setLv2(String lv2) {
		this.lv2 = lv2;
	}
	public String getLv3() {
		return lv3;
	}
	public void setLv3(String lv3) {
		this.lv3 = lv3;
	}
	
}
