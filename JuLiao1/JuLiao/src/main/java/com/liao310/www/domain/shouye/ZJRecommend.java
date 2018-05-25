package com.liao310.www.domain.shouye;

/**
 * 专家推荐实体
 */

public class ZJRecommend {
    private int id;//专家id
    private String nickname;//用户名
    private int role;//角色 2精选，11竞彩
    private String if_recom;//是否推荐+推荐排序 0不推荐，1-n推荐+排序
    private String re_tag;//推荐标签
    private String re_tag_color;//推荐颜色标签
    private String avatar;//头像地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRe_tag_color() {
        return re_tag_color;
    }

    public void setRe_tag_color(String re_tag_color) {
        this.re_tag_color = re_tag_color;
    }

    public String getUsername() {
        return nickname;
    }

    public void setUsername(String username) {
        this.nickname = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getIf_recom() {
        return if_recom;
    }

    public void setIf_recom(String if_recom) {
        this.if_recom = if_recom;
    }

    public String getRe_tag() {
        return re_tag;
    }

    public void setRe_tag(String re_tag) {
        this.re_tag = re_tag;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
