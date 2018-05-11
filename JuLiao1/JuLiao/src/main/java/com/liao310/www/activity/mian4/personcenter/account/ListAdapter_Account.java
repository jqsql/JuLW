package com.liao310.www.activity.mian4.personcenter.account;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.personal.Account;
import com.liao310.www.widget.ShapedImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter_Account   extends BaseAdapter{
	private ArrayList<Account> accoutLists;
	private Context _this;
	public ListAdapter_Account(Context con) {
		_this = con;
	}
	public void setDate(ArrayList<Account> orderListsFrom){
		this.accoutLists = orderListsFrom;
	}
	@Override
	public int getCount() {
		if(accoutLists == null){
			return 0; 
		}else{
			return accoutLists.size();
		}
	}
	@Override
	public Account getItem(int position) {
		if(accoutLists == null){
			return null; 
		}else{
			return accoutLists.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Account account = accoutLists.get(position);

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(_this).inflate(R.layout.account_item, parent, false);
			holder = new ViewHolder();
			holder.state = (TextView) convertView.findViewById(R.id.state);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.money = (TextView) convertView.findViewById(R.id.money);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);	
		}else{			
			holder = (ViewHolder) convertView.getTag();
		}
		holder.state.setText(account.getPay_type()+"成功");
		holder.name.setText(account.getPay_info());
		holder.money.setText(account.getMoney()+"金币");
		holder.time.setText(account.getTime());
		return convertView;

	}
	class ViewHolder {
		public ShapedImageView head;
		public TextView state;
		public TextView name;
		public TextView money;
		public TextView time;

	}
}
