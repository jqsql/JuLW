package com.liao310.www.activity.mian4.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZhuanJiaRefreshListAdapter   extends BaseAdapter{
	private ArrayList<ZhuanJia> zhuanjiaLists;
	GuanZhuClickItemListener guanzhuClickItemListener;
	private Context _this;
	public ZhuanJiaRefreshListAdapter(Context con,GuanZhuClickItemListener callback) {
		_this = con;
		guanzhuClickItemListener = callback;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ZhuanJia zhuanjia = zhuanjiaLists.get(position);
		final ViewHolder holder ;
		if (convertView == null) {
			holder = new ViewHolder();  
			convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_complex_item, parent, false);
			holder.info = (TextView) convertView.findViewById(R.id.info);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.guanzhu = (TextView) convertView.findViewById(R.id.guanzhu);
			holder.head = (ShapedImageView) convertView.findViewById(R.id.head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag(); 
		}

		if(zhuanjia.getWeikaishi() == 0) {
			holder.info.setVisibility(View.INVISIBLE);
		}else {
			holder.info.setText("("+zhuanjia.getWeikaishi()+"场未开始)");
			holder.info.setVisibility(View.VISIBLE);
		}
		holder.time.setText(zhuanjia.getLianhong()+"");
		if(zhuanjia.getFollow_status() == 1) {
			holder.guanzhu.setText("√已关注");
			holder.guanzhu.setTextColor(Color.WHITE);
			holder.guanzhu.setBackgroundResource(R.drawable.cornerfullgreen15dp);
		}else{
			holder.guanzhu.setText("+  关注");
			holder.guanzhu.setTextColor(_this.getResources().getColor(R.color.textgrey));
			holder.guanzhu.setBackgroundResource(R.drawable.cornerfulllightgery);
		}	
		holder.guanzhu.setTag(position); 
		holder.guanzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				guanzhuClickItemListener.guanzhuclick(v); 
			}
		});
		xUtilsImageUtils.display(holder.head,zhuanjia.getAvatar(),R.drawable.defaultpic,true);
		holder.name.setText(zhuanjia.getNickname());
		return convertView;

	} 

	class ViewHolder {
		public ShapedImageView head;
		public TextView name;

		public TextView info;
		public TextView time;
		public TextView guanzhu;
	}
	public interface GuanZhuClickItemListener { 
		public void guanzhuclick(View v); 
	}
}
