package com.liao310.www.activity.mian4.shouye.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.mian4.zhuanjia.detail.ZhuanJiaDetailActivity;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.domain.zhuanjia.ZhuanJiaListBack;
import com.liao310.www.net.ServiceZhuanJia;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ZhuanJiaFrament extends Fragment  {
	private Activity _this;
	private RefreshListView refreshListView;

	private ZhuanJiaFoucedListAdapter myListAdapter = null;
	private ArrayList<ZhuanJia> zhuanjialist;

	private int more = 1;// 分页
	private boolean isGetting = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main4_fragment_shouye_item, container,false);
		_this = this.getActivity();
		initView(view);// 初使化控件
		initdata(false); // 获取数据
		return view;
	}
	private void initView(View view) {		
		refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
		myListAdapter = new ZhuanJiaFoucedListAdapter(_this);//其他，文章
		refreshListView.setAdapter(myListAdapter);
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
					initdata(true);
				} else {
					initdata(false);
				}

			}
		});
	}

	void initdata(boolean isMore) {
		if(!isGetting){
			isGetting = true;
			if(!isMore){
				more = 1;
			}else {
				more++;
			}
			getZhuanjia(isMore);
		}
	}
	private void getZhuanjia(final boolean isMore) {
		ServiceZhuanJia.getInstance().getZhuanJiaFouced(_this,"follow",more,
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
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myListAdapter!=null){
					myListAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				refreshListView.onRefreshComplete(false);
				isGetting = false;
			}
		});
	}
	public synchronized void dodata(ZhuanJiaListBack t,boolean isRefresh){
		if(isRefresh){
			if(zhuanjialist== null){
				zhuanjialist = new ArrayList<ZhuanJia>();
			}else{
				zhuanjialist.clear();
			}
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
				zhuanjialist.addAll(t.getData().getItems());  
				myListAdapter.setDate(zhuanjialist);
			}
		}else{
			ArrayList<ZhuanJia> newlists = new ArrayList<>();
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
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
		if(myListAdapter!=null){
			myListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
	}

}
