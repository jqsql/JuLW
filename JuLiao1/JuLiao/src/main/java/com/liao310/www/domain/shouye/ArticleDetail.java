package com.liao310.www.domain.shouye;

import com.liao310.www.domain.personal.SendCard;
import com.liao310.www.domain.zhuanjia.ZhuanJia;

public class ArticleDetail {
	boolean haveCard;
	SendCard card;
	ZhuanJia person;
	Article articledetail;
	
	public boolean isHaveCard() {
		return haveCard;
	}
	public void setHaveCard(boolean haveCard) {
		this.haveCard = haveCard;
	}
	public SendCard getCard() {
		return card;
	}
	public void setCard(SendCard card) {
		this.card = card;
	}
	public ZhuanJia getPerson() {
		return person;
	}
	public void setPerson(ZhuanJia person) {
		this.person = person;
	}
	public Article getArticledetail() {
		return articledetail;
	}
	public void setArticledetail(Article articledetail) {
		this.articledetail = articledetail;
	}

}
