package com.liao310.www.activity.mian4.match;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.match.Match;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MacthListAdapter   extends BaseAdapter{
	private ArrayList<Match> matchLists;
	private Context _this;
	public MacthListAdapter(Context con) {
		_this = con;
	}
	public void setDate(ArrayList<Match> orderListsFrom){
		this.matchLists = orderListsFrom;
	}
	@Override
	public int getCount() {
		if(matchLists == null){
			return 0; 
		}else{
			return matchLists.size();
		}
	}
	@Override
	public Match getItem(int position) {
		if(matchLists == null){
			return null; 
		}else{
			return matchLists.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Match match = matchLists.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(_this, R.layout.match_item,null);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			holder.gamename = (TextView) convertView.findViewById(R.id.gamename);
			holder.matchkey = (TextView) convertView.findViewById(R.id.matchkey);
			holder.gametime = (TextView) convertView.findViewById(R.id.gametime);
			holder.hostteamname = (TextView) convertView.findViewById(R.id.hostteamname);
			holder.gustteamname = (TextView) convertView.findViewById(R.id.gustteamname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.number.setText(match.getRecom_num()+"");
		holder.gamename.setText(match.getLs_cname());
		holder.matchkey.setText(match.getMatchKey());
		holder.gametime.setText(match.getSc_time());
		holder.hostteamname.setText(match.getZhuname());
		holder.gustteamname.setText(match.getKename());
		
		return convertView;

	}
	class ViewHolder {
		public TextView number;
		public TextView gamename;
		public TextView matchkey;
		public TextView gametime;
		public TextView hostteamname;
		public TextView gustteamname;
	}
}
