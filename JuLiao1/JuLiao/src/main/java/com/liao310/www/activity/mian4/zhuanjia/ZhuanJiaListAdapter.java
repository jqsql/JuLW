package com.liao310.www.activity.mian4.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.RoundProgressBar;
import com.liao310.www.widget.ShapedImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuanJiaListAdapter   extends BaseAdapter{
	private ArrayList<ZhuanJia> zhuanjiaLists;
	private Context _this;
	private int _type;//0 精选，1竞彩 ，2竞猜
	private int _type2;
	public ZhuanJiaListAdapter(Context con) {
		_this = con;
	}
	public void setDate(ArrayList<ZhuanJia> orderListsFrom){
		this.zhuanjiaLists = orderListsFrom;
	}
	public void setType(int type,int type2){
		_type = type;
		_type2 = type2;
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
	@SuppressLint({ "ViewHolder", "ResourceAsColor" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ZhuanJia zhuanjia = zhuanjiaLists.get(position);
		final ViewHolder holder ;
		if (convertView == null) {
			holder = new ViewHolder();  
			convertView = LayoutInflater.from(_this).inflate(R.layout.zhuanjia_simple_item, parent, false);
			holder.numberTv = (TextView) convertView.findViewById(R.id.number_tx);
			holder.numberIv = (ImageView) convertView.findViewById(R.id.number_im);
			holder.statename = (TextView) convertView.findViewById(R.id.statename);
			holder.state = (TextView) convertView.findViewById(R.id.state);
			holder.mProgressBar = convertView.findViewById(R.id.zhuanjia_ProgressBar);
			holder.statetail = (TextView) convertView.findViewById(R.id.statetail);
			holder.head = (ShapedImageView) convertView.findViewById(R.id.head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag(); 
		}	
		if(position == 0){
			holder.numberIv.setImageResource(R.drawable.top1);
			holder.numberTv.setVisibility(View.INVISIBLE);
			holder.numberIv.setVisibility(View.VISIBLE);
		}else if(position == 1){
			holder.numberIv.setImageResource(R.drawable.top2);
			holder.numberTv.setVisibility(View.INVISIBLE);
			holder.numberIv.setVisibility(View.VISIBLE);
		}else if(position == 2){
			holder.numberIv.setImageResource(R.drawable.top3);
			holder.numberTv.setVisibility(View.INVISIBLE);
			holder.numberIv.setVisibility(View.VISIBLE);
		}else{
			holder.numberTv.setText((position+1)+"");
			holder.numberIv.setVisibility(View.INVISIBLE);
			holder.numberTv.setVisibility(View.VISIBLE);
		}
		holder.statename.setText("胜率");
		holder.statetail.setVisibility(View.VISIBLE);
		switch (_type) {
		//精选
		case 1:
			switch (_type2) {
			case 2://7日胜率
				holder.state.setText(zhuanjia.getDay7Shenglv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay7Shenglv().isEmpty()? 0 :zhuanjia.getDay7Shenglv())+""));
				break;
			case 3://关注人数
				holder.statename.setText("");
				holder.state.setText(zhuanjia.getDay7Renqi()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay7Renqi()==0? 0 :zhuanjia.getDay7Renqi())+""));
				holder.statetail.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
			break;
			//竞彩
		case 2:
			switch (_type2) {
			case 4://7日命中
				holder.statename.setText("命中率");
				holder.state.setText(zhuanjia.getDay7Mingzhonglv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay7Mingzhonglv().isEmpty()? 0 :zhuanjia.getDay7Mingzhonglv())+""));
				break;
			case 5://7日盈利率
				holder.statename.setText("盈利率");
				holder.state.setText(zhuanjia.getDay7Yinglilv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay7Yinglilv().isEmpty()? 0 :zhuanjia.getDay7Yinglilv())+""));
				break;
			default:
				break;
			}
			break;
			//竞猜
		case 3:
			switch (_type2) {
			case 6:
				holder.state.setText(zhuanjia.getDay3Shenglv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay3Shenglv().isEmpty()? 0 :zhuanjia.getDay3Shenglv())+""));
				break;
			case 7:
				holder.state.setText(zhuanjia.getDay7Shenglv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay7Shenglv().isEmpty()? 0 :zhuanjia.getDay7Shenglv())+""));
				break;
			case 8:
				holder.state.setText(zhuanjia.getDay30Shenglv()+"");
				holder.mProgressBar.setProgress(Integer.parseInt((zhuanjia.getDay30Shenglv().isEmpty()? 0 :zhuanjia.getDay30Shenglv())+""));
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		xUtilsImageUtils.display(holder.head,zhuanjia.getAvatar(),R.drawable.defaultpic,true);
		holder.name.setText(zhuanjia.getNickname());
		return convertView;

	} 

	class ViewHolder {
		public ShapedImageView head;
		public TextView name;

		public ImageView numberIv;
		public TextView numberTv;
		public TextView statename;
		public TextView state;
		public TextView statetail;

		public TextView info;
		public TextView time;
		public TextView guanzhu;
		private RoundProgressBar mProgressBar;
	}
}
