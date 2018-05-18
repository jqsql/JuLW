package com.liao310.www.domain.LeiTai;

/**
 * 擂台 -- 已购
 */

public class ZhuanJBuyBean {
    private int id;//专家id
    private String nickname;//专家昵称
    private String avatar;//专家头像
    private String jcai_sheng_lv_7;//专家七日胜率
    private String recom_play;//1单场2大小球
    private String recom_result;//1主胜2主负
    private String zhu_name;//主队名称
    private String ke_name;//客队名称
    private String zhu_score;//主分
    private String ke_score;//客分
    private String sc_time;//比赛时间
    private String ls_cname;//联赛名称
    private int result;//-1未结算 0输 1输半 2走 3赢半 4赢 竞彩 结果 5命中 6未中 7二中一

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getJcai_sheng_lv_7() {
        return jcai_sheng_lv_7;
    }

    public void setJcai_sheng_lv_7(String jcai_sheng_lv_7) {
        this.jcai_sheng_lv_7 = jcai_sheng_lv_7;
    }

    public String getRecom_play() {
        return recom_play;
    }

    public void setRecom_play(String recom_play) {
        this.recom_play = recom_play;
    }

    public String getRecom_result() {
        return recom_result;
    }

    public void setRecom_result(String recom_result) {
        this.recom_result = recom_result;
    }

    public String getZhu_name() {
        return zhu_name;
    }

    public void setZhu_name(String zhu_name) {
        this.zhu_name = zhu_name;
    }

    public String getKe_name() {
        return ke_name;
    }

    public void setKe_name(String ke_name) {
        this.ke_name = ke_name;
    }

    public String getZhu_score() {
        return zhu_score;
    }

    public void setZhu_score(String zhu_score) {
        this.zhu_score = zhu_score;
    }

    public String getKe_score() {
        return ke_score;
    }

    public void setKe_score(String ke_score) {
        this.ke_score = ke_score;
    }

    public String getSc_time() {
        return sc_time;
    }

    public void setSc_time(String sc_time) {
        this.sc_time = sc_time;
    }

    public String getLs_cname() {
        return ls_cname;
    }

    public void setLs_cname(String ls_cname) {
        this.ls_cname = ls_cname;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
