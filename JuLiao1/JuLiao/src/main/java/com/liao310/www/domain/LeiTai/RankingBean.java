package com.liao310.www.domain.LeiTai;

/**
 * 排行榜对象
 */

public class RankingBean {
    private int id;//专家id
    private String nickname;//专家昵称
    private String avatar;//专家头像
    private String jcai_sheng_lv_7;//专家七日胜率
    private String c0;//输
    private String c1;//输半
    private String c2;//走
    private String c3;//赢半
    private String c4;//赢

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

    public String getC0() {
        return c0;
    }

    public void setC0(String c0) {
        this.c0 = c0;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }
}
