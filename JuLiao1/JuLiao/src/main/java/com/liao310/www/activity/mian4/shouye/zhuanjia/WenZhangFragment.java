package com.liao310.www.activity.mian4.shouye.zhuanjia;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.adapter.ArticleListAdapter;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceShouYe;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import de.greenrobot.event.EventBus;

public class WenZhangFragment extends Fragment  {
	public static final int LOGIN_PAY = 1;
	private BaseActivity _this;
	private RefreshListView refreshListView;

	private ArticleListAdapter myListAdapter = null;
	private ArrayList<Article> articles;

	private int more = 1;// 分页
	private boolean isGetting = false;
	Article art;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main4_fragment_shouye_item, container,false);
		_this = (BaseActivity) this.getActivity();
		EventBus.getDefault().register(this);
		initView(view);// 初使化控件
		initdata(false); // 获取数据
		return view;
	}
	private void initView(View view) {		
		refreshListView = (RefreshListView) view.findViewById(R.id.rl_listview);
		myListAdapter = new ArticleListAdapter(_this,3,0);//其他，文章
		refreshListView.setAdapter(myListAdapter);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(articles!=null&&position<articles.size()&&position>=0){
					art = articles.get(position);
					if(art.getArt_type()!=2) {
						Intent intent = new Intent(_this,ArticleDetailActivity.class);
						intent.putExtra("rid", art.getRid());
						startActivity(intent);
					}else {
						_this.toGetJC1Result(art.getRid(),art.getPrice(),"dialogToDo");
					}
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
			getZhuanJiaWenZhang(isMore);
		}
	}

	private void getZhuanJiaWenZhang(final boolean isMore) {
		ServiceShouYe.getInstance().getFocueWenZhangList(_this,more,
				new CallBack<ArticleListBack>() {
			@Override
			public void onSuccess(ArticleListBack t) {
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
	public synchronized void dodata(ArticleListBack t,boolean isRefresh){
		if(isRefresh){
			if(articles== null){
				articles = new ArrayList<Article>();
			}else{
				articles.clear();
			}
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
				articles.addAll(t.getData().getItems());  
				myListAdapter.setDate(articles);
			}
		}else{
			ArrayList<Article> newlists = new ArrayList<>();
			if(t.getData()!=null&&t.getData().getItems()!=null
					&& t.getData().getItems().size()>0){
				newlists = t.getData().getItems();
			}
			if (newlists.size()==0) {
				ToastUtils.showShort(_this, "已无更多数据");
				more--;
			}
			if(articles== null){
				articles = new ArrayList<Article>();
			}
			articles.addAll(newlists);
		}
		if(myListAdapter!=null){
			myListAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
	}
	public void onEventMainThread(String event) {
		if (!TextUtils.isEmpty(event)) {
			if ("dialogToDo".equals(event)) {
				dialogToDo();
			}
		}
	}
	public void dialogToDo() {
		if (!PreferenceUtil.getBoolean(_this,"hasLogin")) {
			Intent intentLogin = new Intent(_this, LoginActivity.class); 
			intentLogin.putExtra("requestName", "intentLogin");
			startActivityForResult(intentLogin,LOGIN_PAY);
		}else{
			Intent intentPay = new Intent(_this, PayActivity.class); 
			intentPay.putExtra("rid", art.getRid());
			intentPay.putExtra("name", art.getNickname());
			intentPay.putExtra("prize", art.getPrice());
			startActivity(intentPay);
		}	
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		if (resultCode == -1) {
			switch (requestCode) {
			case LOGIN_PAY:	
				break;
			default:
				break;
			}
		}
	}
}
