package com.liao310.www.activity.mian4.personcenter.account;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.domain.personal.Account;
import com.liao310.www.domain.personal.AccountBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServicePersonal;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AccountFragment  extends Fragment  {
	private Activity _this;
	private RefreshListView refreshListView;

	private ListAdapter_Account myListAdapter = null;
	private ArrayList<Account> accoutlist;

	private int more = 1;// 分页
	private boolean isGetting = false;
	public String from;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main4_fragment_shouye_item, container,false);
		_this = this.getActivity();
		initView(view);// 初使化控件
		initdata(); // 获取数据
		return view;
	}
	private void initView(View view) {		
		refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
		myListAdapter = new ListAdapter_Account(_this);//其他，文章
		refreshListView.setAdapter(myListAdapter);
		
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
				if(isGetting){
					return;
				}
				if (isLoadingMore) {
					if(!isGetting){
						isGetting = true;
					}
					more++;
					ServicePersonal.getInstance().getMyAccount(_this,from,more,
							new CallBack<AccountBack>() {

						@Override
						public void onSuccess(AccountBack t) {
							// TODO 自动生成的方法存根
							dodata(t,false);
						}

						@Override
						public void onFailure(ErrorMsg errorMessage) {
							// TODO 自动生成的方法存根
							more--;
							ToastUtils.showShort(_this,errorMessage.msg);
							if(myListAdapter!=null){
								myListAdapter.notifyDataSetChanged();// 重新刷新数据
							}
							refreshListView.onRefreshComplete(false);
							isGetting = false;
						}
					});
				} else {
					initdata();
				}

			}
		});
	}

	void initdata() {
		if(!isGetting){
			isGetting = true;
			getAccount();
		}
	}

	private void getAccount() {
		more = 1;
		ServicePersonal.getInstance().getMyAccount(_this,from,more,
				new CallBack<AccountBack>() {
			@Override
			public void onSuccess(AccountBack t) {
				// TODO 自动生成的方法存根
				dodata(t,true);
			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myListAdapter!=null){
					myListAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				refreshListView.onRefreshComplete(false);
				isGetting = false;
			}
		});
	}
	public synchronized void dodata(AccountBack t,boolean isRefresh){
		if(isRefresh){
			if(accoutlist== null){
				accoutlist = new ArrayList<Account>();
			}else{
				accoutlist.clear();
			}
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
				accoutlist.addAll(t.getData().getItems());  
				myListAdapter.setDate(accoutlist);
			}
		}else{
			ArrayList<Account> newlists = new ArrayList<>();
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
				newlists = t.getData().getItems();
			}
			if (newlists.size()==0) {
				ToastUtils.showShort(_this, "已无更多数据");
				more--;
			}
			if(accoutlist== null){
				accoutlist = new ArrayList<Account>();
			}
			accoutlist.addAll(newlists);
		}
		if(myListAdapter!=null){
			myListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
	}

}