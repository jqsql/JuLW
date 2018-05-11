package com.liao310.www.activity.mian4.zhuanjia;

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
import android.widget.ListView;
import android.widget.TextView;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.zhuanjia.ZhuanJiaRefreshListAdapter.GuanZhuClickItemListener;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.FouceBack;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.domain.zhuanjia.ZhuanJiaListBack;
import com.liao310.www.net.ServiceZhuanJia;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import java.util.ArrayList;

public class ZhuanJiaMainNewFragment extends Fragment
implements OnClickListener,GuanZhuClickItemListener {
	public static final int LOGIN = 0;
	private Activity _this;
	//private View mProgressView;
	private TextView jingxuan,jingcai3;
	private TextView day3,day7,day30;
	private TextView day3_line,day7_line,day30_line;
	private ListView listview;
	private ZhuanJiaListAdapter myListAdapter = null;
	private RefreshListView refreshListView;
	private ZhuanJiaRefreshListAdapter myRefreshListAdapter = null;

	private int titleType = 1;//1精选，2竞彩,3竞猜
	private int type = 1;

	private ArrayList<ZhuanJia> zhuanjialist;
	private boolean isGetting = false;
	private int more = 1;
	private int positon = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_this = getActivity();
		View view = inflater.inflate(R.layout.main4_fragment_zhuanjia,container, false);
		initView(view);
		setMainTitleBack(0);
		titleType = 1;
		setTitleBack(0);
		initRefreshdata(false);
		return view;

	}
	public void initView(View view){
		jingxuan = (TextView) view.findViewById(R.id.jingxuan);
		jingxuan.setOnClickListener(this);
		jingcai3 = (TextView) view.findViewById(R.id.jingcai3);
		jingcai3.setOnClickListener(this);

		initeChildView(view);
	}
	public void initeChildView(View view){
		day3 = (TextView) view.findViewById(R.id.yesterday);
		day3.setOnClickListener(this);
		day7 = (TextView) view.findViewById(R.id.today);
		day7.setOnClickListener(this);
		day30 = (TextView) view.findViewById(R.id.tomorrow);
		day30.setOnClickListener(this);

		day3_line = (TextView) view.findViewById(R.id.yesterday_line);
		day7_line = (TextView) view.findViewById(R.id.today_line);
		day30_line = (TextView) view.findViewById(R.id.tomorrow_line);

		refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
		listview  = (ListView) view.findViewById(R.id.rl_listview2);
		myListAdapter = new ZhuanJiaListAdapter(_this);
		listview.setAdapter(myListAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(zhuanjialist!=null&&position<zhuanjialist.size()&&position>=0){
					Intent intent = new Intent(_this,ZhuanJiaDetailActivity.class);
					intent.putExtra("uid", zhuanjialist.get(position).getUid());
					startActivity(intent);
				}
			}
		});
		myRefreshListAdapter = new ZhuanJiaRefreshListAdapter(_this,this);
		refreshListView.setAdapter(myRefreshListAdapter);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(zhuanjialist!=null&&position<zhuanjialist.size()&&position>=0){
					Intent intent = new Intent(_this,ZhuanJiaDetailActivity.class);
					intent.putExtra("uid", zhuanjialist.get(position).getUid());
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
					initRefreshdata(true);
				} else {
					initRefreshdata(false);
				}

			}
		});

		//mProgressView = view.findViewById(R.id.progress);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {		
		case R.id.jingxuan:
			if(isGetting)
				break;
			if(titleType != 1) {//不是精选，换成精选;
				setMainTitleBack(0);//换成精选
				titleType = 1;
				type = 1;
				setTitleBack(0);//换成第一列
				initRefreshdata(false);
			}else{
				titleType = 1;
			}
			break;
		case R.id.jingcai3:
			if(isGetting)
				break;
			if(titleType != 2) {//不是竞彩，换成竞彩;
				setMainTitleBack(1);//换成竞彩
				titleType = 2;
				type = 4;
				setTitleBack(0);//换成第一列
				initdata();
			}else{
				titleType = 2;
			}
			break;
		case R.id.yesterday:
			if(isGetting)
				break;
			switch (titleType) {
			case 1:
				if(type != 1) {//不是第一列，换第一列;
					type = 1;
					setTitleBack(0);//换成第一列
					initRefreshdata(false);
				}else{
					type = 1;
				}
				break;
			case 2:
				if(type != 4) {//不是第一列，换第一列;
					type = 4;
					setTitleBack(0);//换成第一列
					initdata();
				}else{
					type = 4;
				}
				break;
			case 3:
				if(type != 6) {//不是第一列，换第一列;
					type = 6;
					setTitleBack(0);//换成第一列
					initdata();
				}else{
					type = 6;
				}
				break;
			default:
				break;
			}

			break;
		case R.id.today:
			if(isGetting)
				break;
			switch (titleType) {
			case 1:
				if(type != 2) {//不是第一列，换第一列;
					type = 2;
					setTitleBack(1);//换成第一列
					initdata();
				}else{
					type = 2;
				}
				break;
			case 2:
				if(type != 5) {//不是第一列，换第一列;
					type = 5;
					setTitleBack(1);//换成第一列
					initdata();
				}else{
					type = 5;
				}
				break;
			case 3:
				if(type != 7) {//不是第一列，换第一列;
					type = 7;
					setTitleBack(1);//换成第一列
					initdata();
				}else{
					type = 7;
				}
				break;
			default:
				break;
			}
			break;
		case R.id.tomorrow:
			if(isGetting)
				break;
			switch (titleType) {
			case 1:
				if(type != 3) {//不是第一列，换第一列;
					type = 3;
					setTitleBack(2);//换成第一列
					initdata();
				}else{
					type = 3;
				}
				break;
			case 2:
				break;
			case 3:
				if(type != 8) {//不是第一列，换第一列;
					type = 8;
					setTitleBack(2);//换成第一列
					initdata();
				}else{
					type = 8;
				}
				break;
			default:
				break;
			}
			break;
		}
	}
	void initRefreshdata(boolean isMore) {
		if(!isGetting){
			//showProgress(true);
			isGetting = true;
			if(!isMore){
				more = 1;
			}else {
				more++;
			}
			getZhuanJiaRefresh(isMore);
		}
	}
	void initdata() {
		if(!isGetting){
			//showProgress(true);
			isGetting = true;
			myListAdapter.setType(titleType,type);
			getZhuanJia();
		}
	}
	private void getZhuanJiaRefresh(final boolean isMore) {
		ServiceZhuanJia.getInstance().getZhuanJia(_this,titleType,type,more,
				new CallBack<ZhuanJiaListBack>() {
			@Override
			public void onSuccess(ZhuanJiaListBack t) {
				// TODO 自动生成的方法存根
				dodata(t,!isMore);
			}
			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				if(isMore) {
					more--;
				}
				//showProgress(false);
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myRefreshListAdapter!=null){
					myRefreshListAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				refreshListView.onRefreshComplete(false);
				isGetting = false;
			}
		});
	}
	public synchronized void dodata(ZhuanJiaListBack t,boolean isRefresh){
		refreshListView.setVisibility(View.VISIBLE);
		listview.setVisibility(View.GONE);
		if(isRefresh){
			if(zhuanjialist== null){
				zhuanjialist = new ArrayList<ZhuanJia>();
			}else{
				zhuanjialist.clear();
			}
			if(t.getData()!=null&& t.getData().getItems()!=null
					&&t.getData().getItems().size()>0){
				zhuanjialist.addAll(t.getData().getItems());  
				myRefreshListAdapter.setDate(zhuanjialist);
			}
			refreshListView.smoothScrollToPosition(0);
		}else{
			ArrayList<ZhuanJia> newlists = new ArrayList<>();
			if(t.getData()!=null&& t.getData().getItems()!=null
					&&t.getData().getItems().size()>0){
				newlists = t.getData().getItems(); 
			}
			if (newlists.size()==0) {
				ToastUtils.showShort(_this, "已无更多数据");
				more--;
			}
			if(zhuanjialist== null){
				zhuanjialist = new ArrayList<ZhuanJia>();
			}
			zhuanjialist.addAll(newlists);
		}
		if(myRefreshListAdapter!=null){
			myRefreshListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
		//showProgress(false);
	}

	private void getZhuanJia() {
		ServiceZhuanJia.getInstance().getZhuanJia(_this,titleType,type,1,
				new CallBack<ZhuanJiaListBack>() {
			@Override
			public void onSuccess(ZhuanJiaListBack t) {
				// TODO 自动生成的方法存根
				dodata(t);
			}
			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				//showProgress(false);
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myListAdapter!=null){
					myListAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				isGetting = false;
			}
		});
	}
	public synchronized void dodata(ZhuanJiaListBack t){
		refreshListView.setVisibility(View.GONE);
		listview.setVisibility(View.VISIBLE);
		if(zhuanjialist== null){
			zhuanjialist = new ArrayList<ZhuanJia>();
		}else{
			zhuanjialist.clear();
		}
		if(t.getData()!=null&& t.getData().getItems()!=null
				&&t.getData().getItems().size()>0){
			zhuanjialist.addAll(t.getData().getItems());  
			myListAdapter.setDate(zhuanjialist);
		}

		if(myListAdapter!=null){
			myListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		isGetting = false;
		//showProgress(false);
	}
	public void setTitleBack(int title) {
		day3.setTextColor(getResources().getColor(R.color.white));
		day3_line.setVisibility(View.INVISIBLE);
		day7.setTextColor(getResources().getColor(R.color.white));
		day7_line.setVisibility(View.INVISIBLE);
		if(titleType == 1) {
			day3.setText("7日总胜率");
			day7.setText("7日单场");
			day30.setText("7日大小球");
			day30.setTextColor(getResources().getColor(R.color.white));
			day30.setVisibility(View.VISIBLE);				
			day30_line.setVisibility(View.INVISIBLE);
		}else if(titleType == 2) {
			day3.setText("7日总命中率");
			day7.setText("7日单关盈利率");
			day30.setVisibility(View.GONE);
			day30_line.setVisibility(View.GONE);
		}
		if(title == 0) {
			day3.setTextColor(getResources().getColor(R.color.textgreen));
			day3_line.setVisibility(View.VISIBLE);				
		} if(title == 1) {
			day7.setTextColor(getResources().getColor(R.color.textgreen));
			day7_line.setVisibility(View.VISIBLE);	 
		} if(title == 2) {
			day30.setTextColor(getResources().getColor(R.color.textgreen));
			day30_line.setVisibility(View.VISIBLE);	 
		}
	}	
	@SuppressLint("NewApi")
	public void setMainTitleBack(int choose) {
		int rightJX = jingxuan.getPaddingRight();  
		int topJX = jingxuan.getPaddingTop();  
		int bottomJX = jingxuan.getPaddingBottom();  
		int leftJX  = jingxuan.getPaddingLeft();
		int rightJC3 = jingcai3.getPaddingRight();  
		int topJC3 = jingcai3.getPaddingTop();  
		int bottomJC3 = jingcai3.getPaddingBottom();  
		int leftJC3 = jingcai3.getPaddingLeft();

		jingxuan.setTextColor(getResources().getColor(R.color.textgreen));
		jingxuan.setBackground(null);
		jingxuan.setPadding(leftJX,topJX,rightJX,bottomJX); 
		jingcai3.setTextColor(getResources().getColor(R.color.textgreen));
		jingcai3.setBackground(null);
		jingcai3.setPadding(leftJC3,topJC3,rightJC3,bottomJC3);

		if(choose == 0) {
			jingxuan.setTextColor(getResources().getColor(R.color.black));
			jingxuan.setBackgroundResource(R.drawable.corner13linenone_whitefull);
			jingxuan.setPadding(leftJX,topJX,rightJX,bottomJX);  
		} if(choose == 1) {
			jingcai3.setTextColor(getResources().getColor(R.color.black));
			jingcai3.setBackgroundResource(R.drawable.corner24linenone_whitefull);
			jingcai3.setPadding(leftJC3,topJC3,rightJC3,bottomJC3);  
		}
	}
	@Override
	public void guanzhuclick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {	
		case R.id.guanzhu:
			positon = (Integer)v.getTag();
			if (!PreferenceUtil.getBoolean(_this,"hasLogin")) {
				Intent intentLogin = new Intent(_this, LoginActivity.class); 
				intentLogin.putExtra("requestName", "intentLogin");
				startActivityForResult(intentLogin,LOGIN);
			}else{
				ZhuanJia zj = zhuanjialist.get(positon);
				setFouce(v,zj.getUid(),zj.getFollow_status());
			}
			break;
		}
	}
	public void setFouce(final View guanzhu,String uid,int  hasFouce){
		int hasFouceNew = 0;
		if(hasFouce == 1) {
			hasFouceNew = 0;
		}else{
			hasFouceNew = 1;
		}
		ServiceZhuanJia.getInstance().getFouce(_this,uid,hasFouceNew,
				new CallBack<FouceBack>() {
			@Override
			public void onSuccess(FouceBack t) {
				// TODO 自动生成的方法存根
				if(t.getData()!=null&&t.getData().getFollow_status() == 1) {
					ToastUtils.showShort(_this, "关注成功");
					updateItemView(positon,1);
				}else {					
					ToastUtils.showShort(_this, "取消关注");
					updateItemView(positon,0);
				}

			}
			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		if (resultCode == -1) {
			switch (requestCode) {
			case LOGIN:	
				initRefreshdata(false);
				break;	
			default:
				break;
			}
		}
	}
	/*public void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
			.alpha(show ? 1 : 0)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mProgressView.setVisibility(show ? View.VISIBLE
							: View.GONE);
				}
			});
		} else {
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		}
	}*/
	public void updateItemView(int position,int state) {
		int firstVisiblePosition = refreshListView.getFirstVisiblePosition(); //屏幕内当前可以看见的第一条数据
		if((position+1)-firstVisiblePosition>=0){
			//1.获取当前点击的条目的view
			View itemView = refreshListView.getChildAt((position+1) - firstVisiblePosition);
			//2.查找出相应的控件
			TextView textView= (TextView) itemView.findViewById(R.id.guanzhu);
			//3.更新ui
			if(state == 1) {
				textView.setText("√已关注");
				textView.setTextColor(Color.WHITE);
				textView.setBackgroundResource(R.drawable.cornerfullgreen15dp);
			}else{
				textView.setText("+  关注");
				textView.setTextColor(_this.getResources().getColor(R.color.textgrey));
				textView.setBackgroundResource(R.drawable.cornerfulllightgery);
			}
			//4.更新数据源
			zhuanjialist.get(position).setFollow_status(state);
		}

	}
}
