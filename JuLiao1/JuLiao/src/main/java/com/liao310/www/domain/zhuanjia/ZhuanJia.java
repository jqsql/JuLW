package com.liao310.www.domain.zhuanjia;

public class ZhuanJia {
	String uid;// 专家id
	String nickname;// 专家昵称
	String description;// 专家介绍
	String avatar;// 专家头像
	int article_num;// 专家的文章数
	int fans_num;// 专家的关注数
	String last_time;// 最后发布时间
	int follow_status;//  是否关注 0未关注 1已关注
	int like_num;//  点赞数      
	int art_type;//0精选，1竞彩，2竞猜

	String day3Shenglv;//近3日胜率（返回值带%）
	String day7Shenglv;//近7日胜率（返回值带%）
	String day30Shenglv;//近30日胜率（返回值带%）

	String day7Mingzhonglv;//近7日命中率（返回值带%）
	String day7Yinglilv;//近7日盈利率（返回值带%）

	int day7Renqi;//近7日人气，既被关注人数
	int lianhong;//连红数
	int lianhongMost;//最高连红数
	int weikaishi;//未开始数

	private String day7_dc_sl;//单场胜率
	private String day7_dx_sl;//大小球胜率
	private String day7_single_yl;//单关盈利率
	private int jc_lian_win;//连盈数
	private int jc_max_win;//最高连盈数
	private int qipao;//未结算文章数


	//关注专家列表时使用
	private String days7;//逗号分割 — 精选：赢，赢半，走，输半，输 ； 竞彩： 总场数，命中场数，盈利率

	public int getQipao() {
		return qipao;
	}

	public void setQipao(int qipao) {
		this.qipao = qipao;
	}

	public String getDays7() {
		return days7;
	}

	public int getJc_lian_win() {
		return jc_lian_win;
	}

	public void setJc_lian_win(int jc_lian_win) {
		this.jc_lian_win = jc_lian_win;
	}

	public int getJc_max_win() {
		return jc_max_win;
	}

	public void setJc_max_win(int jc_max_win) {
		this.jc_max_win = jc_max_win;
	}

	public void setDays7(String days7) {
		this.days7 = days7;
	}

	public String getDay7_dc_sl() {
		return day7_dc_sl;
	}

	public void setDay7_dc_sl(String day7_dc_sl) {
		this.day7_dc_sl = day7_dc_sl;
	}

	public String getDay7_dx_sl() {
		return day7_dx_sl;
	}

	public void setDay7_dx_sl(String day7_dx_sl) {
		this.day7_dx_sl = day7_dx_sl;
	}

	public String getDay7_single_yl() {
		return day7_single_yl;
	}

	public void setDay7_single_yl(String day7_single_yl) {
		this.day7_single_yl = day7_single_yl;
	}

	public int getLianhongMost() {
		return lianhongMost;
	}
	public void setLianhongMost(int lianhongMost) {
		this.lianhongMost = lianhongMost;
	}
	public int getArt_type() {
		return art_type;
	}
	public void setArt_type(int art_type) {
		this.art_type = art_type;
	}
	public String getDay3Shenglv() {
		return day3Shenglv;
	}
	public void setDay3Shenglv(String day3Shenglv) {
		this.day3Shenglv = day3Shenglv;
	}
	public String getDay7Shenglv() {
		return day7Shenglv;
	}
	public void setDay7Shenglv(String day7Shenglv) {
		this.day7Shenglv = day7Shenglv;
	}
	public String getDay30Shenglv() {
		return day30Shenglv;
	}
	public void setDay30Shenglv(String day30Shenglv) {
		this.day30Shenglv = day30Shenglv;
	}
	public String getDay7Mingzhonglv() {
		return day7Mingzhonglv;
	}
	public void setDay7Mingzhonglv(String day7Mingzhonglv) {
		this.day7Mingzhonglv = day7Mingzhonglv;
	}
	public String getDay7Yinglilv() {
		return day7Yinglilv;
	}
	public void setDay7Yinglilv(String day7Yinglilv) {
		this.day7Yinglilv = day7Yinglilv;
	}
	public int getDay7Renqi() {
		return day7Renqi;
	}
	public void setDay7Renqi(int day7Renqi) {
		this.day7Renqi = day7Renqi;
	}
	public int getLianhong() {
		return lianhong;
	}
	public void setLianhong(int lianhong) {
		this.lianhong = lianhong;
	}
	public int getWeikaishi() {
		return weikaishi;
	}
	public void setWeikaishi(int weikaishi) {
		this.weikaishi = weikaishi;
	}
	public int getLike_num() {
		return like_num;
	}
	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getArticle_num() {
		return article_num;
	}
	public void setArticle_num(int article_num) {
		this.article_num = article_num;
	}
	public int getFans_num() {
		return fans_num;
	}
	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public int getFollow_status() {
		return follow_status;
	}
	public void setFollow_status(int follow_status) {
		this.follow_status = follow_status;
	}
}
