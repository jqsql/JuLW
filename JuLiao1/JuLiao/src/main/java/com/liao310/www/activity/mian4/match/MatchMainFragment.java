package com.liao310.www.activity.mian4.match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.liao310.www.R;
import com.liao310.www.domain.match.Match;
import com.liao310.www.domain.match.MatchListBackBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceMatch;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MatchMainFragment extends Fragment
implements OnClickListener{
	private Activity _this;
	private TextView ya,line,jing;
	private TextView zuqiu,lanqiu;
	private TextView yesterday,today,tomorrow;
	private TextView yesterday_line,today_line,tomorrow_line;
	private RefreshListView refreshListView;
	private MacthListAdapter myListAdapter = null;

	private int title1 = 0;//0亚
	private String title2 = "football";
	private int title3 = 1;

	private ArrayList<Match> matchlist;
	private boolean isGetting = false;
	private int more = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_this = getActivity();
		View view = inflater.inflate(R.layout.main4_fragment_match,container, false);
		initView(view);
		initdata(false);
		return view;

	}
	public void initView(View view){
		ya = (TextView) view.findViewById(R.id.ya);
		ya.setOnClickListener(this);
		line = (TextView) view.findViewById(R.id.line);
		jing = (TextView) view.findViewById(R.id.jing);
		jing.setOnClickListener(this);

		zuqiu = (TextView) view.findViewById(R.id.zuqiu);
		zuqiu.setOnClickListener(this);
		lanqiu = (TextView) view.findViewById(R.id.lanqiu);
		lanqiu.setOnClickListener(this);
		initeChildView(view);
	}
	public void initeChildView(View view){
		yesterday = (TextView) view.findViewById(R.id.yesterday);
		yesterday.setOnClickListener(this);
		today = (TextView) view.findViewById(R.id.today);
		today.setOnClickListener(this);
		tomorrow = (TextView) view.findViewById(R.id.tomorrow);
		tomorrow.setOnClickListener(this);
		yesterday_line = (TextView) view.findViewById(R.id.yesterday_line);
		today_line = (TextView) view.findViewById(R.id.today_line);
		tomorrow_line = (TextView) view.findViewById(R.id.tomorrow_line);

		refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
		myListAdapter = new MacthListAdapter(_this);
		refreshListView.setAdapter(myListAdapter);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(matchlist!=null&&position<matchlist.size()&&position>=0){
					Intent intent = new Intent(_this,MatchDetailActivity.class);
					intent.putExtra("aid", matchlist.get(position).getAid());
					intent.putExtra("cid", matchlist.get(position).getCid());
					startActivity(intent);
				}
			}
		});
		// 添加监听器
		refreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO 自动生成的方法存根
				requestDataFromServer(false); // 冲服务器中获取刷新的数据
			}

			@Override
			public void onLoadMore() {
				// TODO 自动生成的方法存根
				requestDataFromServer(true);
			}

			private   void  requestDataFromServer(boolean isLoadingMore) {
				// TODO 自动生成的方法存根
				if (isLoadingMore) {
					initdata(true);
				} else {
					initdata(false);
				}

			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ya:
			if(title1 != 0) {//不是亚盘，换成亚盘;
				setMainTitleBackBase(0);//换成亚盘
				setMainTitleBack(0);//换成足球
				setTitleBack(1);//换成今天
				title1 = 0;
				title2 = "football";
				title3 = 1;
				initdata(false);
			}else{
				title1 = 0;
			}
			break;
		case R.id.jing:
			if(title1 != 1) {//不是竞彩，换成竞彩;
				setMainTitleBackBase(1);//换成竞彩
				setMainTitleBack(0);//换成足球
				setTitleBack(1);//换成今天
				title1 = 1;
				title2 = "football";
				title3 = 1;
				initdata(false);
			}else{
				title1 = 1;
			}
			break;
		case R.id.zuqiu:
			if(!"football".equals(title2)) {//不是足球，换成足球;
				setMainTitleBack(0);//换成足球
				setTitleBack(1);//换成今天
				title2 = "football";
				title3 = 1;
				initdata(false);
			}else {
				title2 = "football";
			}
			break;
		case R.id.lanqiu:
			if(!"basketball".equals(title2)) {//不是篮球，换成篮球;
				setMainTitleBack(1);//换成篮球
				setTitleBack(1);//换成今天
				title2 = "basketball";
				title3 = 1;
				initdata(false);
			} else {
				title2 = "basketball";
			}
			break;
		case R.id.yesterday:
			if(title3!=0){//不是昨天，换成昨天;
				setTitleBack(0);//换成昨天
				title3 = 0;
				initdata(false);
			} else {
				title3 = 0;
			}
			break;
		case R.id.today:
			if(title3!=1){//不是今天，换成今天;
				setTitleBack(1);//换成今天
				title3 = 1;
				initdata(false);
			}else{
				title3 = 1;
			}
			break;
		case R.id.tomorrow:
			if(title3!=2){//不是明天，换成明天;
				setTitleBack(2);//换成明天
				title3 = 2;
				initdata(false);
			}else{
				title3 = 2;
			}
			break;
		}
	}
	void initdata(boolean isMore) {
		if(!isGetting){
			isGetting = true;
			if(!isMore){
				more = 1;
			}else {
				more++;
			}
			getMatchs(isMore);
		}
	}
	private void getMatchs(final boolean isMore) {
		ServiceMatch.getInstance().getMatch(_this,title1, title2, title3,  more, 
				new CallBack<MatchListBackBack>() {
			@Override
			public void onSuccess(MatchListBackBack t) {
				// TODO 自动生成的方法存根
				dodata(t,!isMore);
			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				if(isMore) {
					more--;
				}
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myListAdapter!=null){
					myListAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				refreshListView.onRefreshComplete(false);
				isGetting = false;
			}
		});
	}
	public synchronized void dodata(MatchListBackBack t,boolean isRefresh){
		if(isRefresh){
			if(matchlist== null){
				matchlist = new ArrayList<Match>();
			}else{
				matchlist.clear();
			}
			if(t.getData()!=null&& t.getData().getRecom_items()!=null) {
				if(t.getData().getRecom_items().getTotal()>0
						&&t.getData().getRecom_items().getItems()!=null
						&&t.getData().getRecom_items().getItems().size()>0){
					matchlist.addAll(t.getData().getRecom_items().getItems());  
					myListAdapter.setDate(matchlist);
				}
				String getDate = t.getData().getRecom_items().getDate();
				if(getDate!=null) {
					yesterday.setText(getNextDay(getDate,-1));
					today.setText(getDate);
					tomorrow.setText(getNextDay(getDate,1));  
					/*switch (title3) {
					case 0:
						yesterday.setText(getDate);
						today.setText(getNextDay(getDate,1));
						tomorrow.setText(getNextDay(getDate,2));                    
						break;
					case 1:
						
						break;
					case 2:
						yesterday.setText(getNextDay(getDate,-2));
						today.setText(getNextDay(getDate,-1));
						tomorrow.setText(getDate);  
						break;
					default:
						break;
					}*/
				}
			}
		}else{
			ArrayList<Match> newlists = new ArrayList<>();
			if(t.getData()!=null&& t.getData().getRecom_items()!=null
					&&t.getData().getRecom_items().getTotal()>0
					&&t.getData().getRecom_items().getItems()!=null
					&&t.getData().getRecom_items().getItems().size()>0){
				newlists = t.getData().getRecom_items().getItems(); 
			}
			if (newlists.size()==0) {
				ToastUtils.showShort(_this, "已无更多数据");
				more--;
			}
			if(matchlist== null){
				matchlist = new ArrayList<Match>();
			}
			matchlist.addAll(newlists);
		}
		if(myListAdapter!=null){
			myListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
	}
	public void setTitleBack(int title) {
		yesterday.setTextColor(getResources().getColor(R.color.textgrey));
		yesterday_line.setVisibility(View.INVISIBLE);
		today.setTextColor(getResources().getColor(R.color.textgrey));
		today_line.setVisibility(View.INVISIBLE);
		tomorrow.setTextColor(getResources().getColor(R.color.textgrey));
		tomorrow_line.setVisibility(View.INVISIBLE);
		if(title == 0) {
			yesterday.setTextColor(getResources().getColor(R.color.textgreen));
			yesterday_line.setVisibility(View.VISIBLE);				
		} if(title == 1) {
			today.setTextColor(getResources().getColor(R.color.textgreen));
			today_line.setVisibility(View.VISIBLE);	 
		} if(title == 2) {
			tomorrow.setTextColor(getResources().getColor(R.color.textgreen));
			tomorrow_line.setVisibility(View.VISIBLE);	 
		}
	}	
	public void setMainTitleBack(int choose) {
		int rightZQ = zuqiu.getPaddingRight();  
		int topZQ = zuqiu.getPaddingTop();  
		int bottomZQ = zuqiu.getPaddingBottom();  
		int leftZQ = zuqiu.getPaddingLeft();
		int rightLQ = lanqiu.getPaddingRight();  
		int topLQ = lanqiu.getPaddingTop();  
		int bottomLQ = lanqiu.getPaddingBottom();  
		int leftLQ = lanqiu.getPaddingLeft();

		zuqiu.setTextColor(getResources().getColor(R.color.textgreen));
		zuqiu.setBackground(null);
		zuqiu.setPadding(leftZQ,topZQ,rightZQ,bottomZQ); 
		lanqiu.setTextColor(getResources().getColor(R.color.textgreen));
		lanqiu.setBackground(null);
		lanqiu.setPadding(leftLQ,topLQ,rightLQ,bottomLQ); 
		if(choose == 0) {//足球
			zuqiu.setTextColor(getResources().getColor(R.color.black));
			zuqiu.setBackgroundResource(R.drawable.corner13linenone_whitefull);
			zuqiu.setPadding(leftZQ,topZQ,rightZQ,bottomZQ);  
		} if(choose == 1) {//篮球
			lanqiu.setTextColor(getResources().getColor(R.color.black));
			lanqiu.setBackgroundResource(R.drawable.corner24linenone_whitefull);
			lanqiu.setPadding(leftLQ,topLQ,rightLQ,bottomLQ);  
		} 
	}
	public void setMainTitleBackBase(int choose) {
		if(choose == 0) {
			ya.setTextSize(20);
			ya.setTextColor(Color.parseColor("#ffffff"));
			line.setTextColor(Color.parseColor("#ffffff"));
			jing.setTextSize(18);
			jing.setTextColor(Color.parseColor("#000000"));
		}if(choose == 1) {
			jing.setTextSize(20);
			jing.setTextColor(Color.parseColor("#ffffff"));
			line.setTextColor(Color.parseColor("#ffffff"));
			ya.setTextSize(18);
			ya.setTextColor(Color.parseColor("#000000"));
		}

	}
	public static String getNextDay(String time,int days) {  
		SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
		Date date = null;
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);  
		calendar.add(Calendar.DAY_OF_MONTH, days);  
		date = calendar.getTime();  
		return new SimpleDateFormat("MM月dd日").format(date);  
	}  
}
