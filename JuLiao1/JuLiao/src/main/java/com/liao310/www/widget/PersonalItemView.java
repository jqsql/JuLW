package com.liao310.www.widget;

import com.liao310.www.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonalItemView extends RelativeLayout {
	ImageView imageView = null;
	ImageView imageIn = null;
	TextView itemTitle = null;
	TextView itemValue = null;

	String name;
	String value;
	private Drawable drawable;
	private boolean isHave;

	public PersonalItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.PersonalItemView);
		name = a.getString(R.styleable.PersonalItemView_pname);

		value = a.getString(R.styleable.PersonalItemView_pvalue);

		drawable = a.getDrawable(R.styleable.PersonalItemView_src);
		isHave = a.getBoolean(R.styleable.PersonalItemView_isHave,true);

		a.recycle();
		// this.setBackgroundResource(R.drawable.list_selector);

		init();
	}

	public PersonalItemView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		// TODO 自动生成的方法存根

		LayoutInflater.from(getContext()).inflate(R.layout.personal_item_view,this);
		imageView = (ImageView) findViewById(R.id.iv_item_icon);
		itemTitle = (TextView) findViewById(R.id.tv_item_title);
		itemValue = (TextView) findViewById(R.id.tv_item_value);
		imageIn = (ImageView) findViewById(R.id.ImageView01);

		itemTitle.setText(name);
		itemValue.setText(value);
		imageView.setImageDrawable(drawable); // 注意吧 ！！
		if(isHave){
			imageIn.setVisibility(VISIBLE);
		}else {
			imageIn.setVisibility(GONE);
		}
	}

	public void setResource(int drawable, String titleString) {
		//
		imageView.setBackgroundResource(drawable);
		itemTitle.setText(titleString);

	}
	public void setImageIn(int value) {
		imageIn.setVisibility(value);
	}
	public void setInfoValue(String value) {
		itemValue.setText(value);
	}
	public void setInfoValue(String value, int color) {
		itemValue.setText(value);
		itemValue.setTextColor(color);
	}
	public String getValue() {
		return itemValue.getText().toString();
	}
}
