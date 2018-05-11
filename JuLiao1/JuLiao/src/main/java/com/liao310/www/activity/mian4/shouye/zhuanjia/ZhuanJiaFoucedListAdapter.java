package com.liao310.www.activity.mian4.shouye.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.https.xUtilsImageUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuanJiaFoucedListAdapter   extends BaseAdapter{
	private ArrayList<ZhuanJia> zhuanjiaLists;
	private Context _this;
	public ZhuanJiaFoucedListAdapter(Context con) {
		_this = con;
	}
	public void setDate(ArrayList<ZhuanJia> orderListsFrom){
		this.zhuanjiaLists = orderListsFrom;
	}
	@Override
	public int getCount() {
		if(zhuanjiaLists == null){
			return 0; 
		}else{
			return zhuanjiaLists.size();
		}
	}
	@Override
	public ZhuanJia getItem(int position) {
		if(zhuanjiaLists == null){
			return null; 
		}else{
			return zhuanjiaLists.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ZhuanJia zhuanjia = zhuanjiaLists.get(position);
		ViewHolder holder;
		if (convertView == null) {  
			holder = new ViewHolder();  
			convertView = View.inflate(_this, R.layout.zhuanjia_item,null);
			holder.head = (ImageView) convertView.findViewById(R.id.head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.fensi = (TextView) convertView.findViewById(R.id.fensi);
			holder.wenzhang = (TextView) convertView.findViewById(R.id.wenzhang);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		} else {  
			holder = (ViewHolder)convertView.getTag();  
		} 
		
		xUtilsImageUtils.display(holder.head,R.drawable.defaultpic,zhuanjia.getAvatar());
		holder.name.setText(zhuanjia.getNickname());
		holder.fensi.setText(zhuanjia.getFans_num()+"");
		holder.wenzhang.setText(zhuanjia.getArticle_num()+"");
		holder.time.setText(zhuanjia.getLast_time());
		
		
		return convertView;

	}
	class ViewHolder {
		public ImageView head;
		public TextView name;
		public TextView fensi;
		public TextView wenzhang;
		public TextView time;
	}
}
