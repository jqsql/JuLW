package com.liao310.www.activity.mian4.personcenter;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.adapter.ArticleListAdapter;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.shouye.ArticleListBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.ServicePersonal;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.Dialog_Hint;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFavouriteActivity  extends BaseActivity {
	public static final int LOGIN_PAY = 1;
	private MyFavouriteActivity _this;
	private ImageView back;
	private TextView title;
	private RefreshListView refreshListView;
	private ArticleListAdapter myFavouriteAdapter = null;

	private ArrayList<Article> articles;
	private boolean isGetting = false;
	private int more = 0;
	private int payType=0;//支付方式
	Article art;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfavourite);
		_this = this;
		initView();
		initdata(false);
	}
	private void initView() {
		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_this.finish();
			}
		});
		title =(TextView) findViewById(R.id.tv_head_title);
		title.setText("我的收藏");

		refreshListView = (RefreshListView) findViewById(R.id.rl_listview);
		myFavouriteAdapter = new ArticleListAdapter(_this,3,2);
		refreshListView.setAdapter(myFavouriteAdapter);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(articles!=null&&position<articles.size()&&position>=0){
					art = articles.get(position);
					if(art.getArt_type()!=2) {
						_this.getArticledetail(art.getRid(),art.getPrice(), "");
					}else {
						if (!PreferenceUtil.getBoolean(_this,"hasLogin")) {
							Intent intentLogin = new Intent(_this, LoginActivity.class);
							intentLogin.putExtra("requestName", "intentLogin");
							startActivityForResult(intentLogin,LOGIN_PAY);
						}else{
							toGetJC1Result(art.getRid(),art.getPrice(),"");
						}
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
			getMyFavourite(isMore);
		}
	}

	private void getMyFavourite(final boolean isMore) {
		ServicePersonal.getInstance().getMyFavourite(_this,more,
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
				if(myFavouriteAdapter!=null){
					myFavouriteAdapter.notifyDataSetChanged();// 重新刷新数据
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
			if(t.getData()!=null&& t.getData().getTotal()>0
					&&t.getData().getItems()!=null
					&&t.getData().getItems().size()>0){
				articles.addAll(t.getData().getItems());  
				myFavouriteAdapter.setDate(articles);
			}
		}else{
			ArrayList<Article> newlists = new ArrayList<>();
			if(t.getData()!=null&& t.getData().getTotal()>0
					&&t.getData().getItems()!=null
					&&t.getData().getItems().size()>0){
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
		if(myFavouriteAdapter!=null){
			myFavouriteAdapter.notifyDataSetChanged();// 重新刷新数据
		}
		refreshListView.onRefreshComplete(false);
		isGetting = false;
	}
	public void dialogToDo() {
		ServicePay.getInstance().pay(_this, art.getRid(), payType,
				new CallBack<PayBack>() {
					@Override
					public void onSuccess(PayBack t) {
						// TODO 自动生成的方法存根
						final Dialog_Hint dialog_hint=new Dialog_Hint(_this,"购买成功",false);
						dialog_hint.show();
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								dialog_hint.dismiss();
								if(art.getArt_type()!=2) {
									Intent intent = new Intent(_this, ArticleDetailActivity.class);
									intent.putExtra("rid", art.getRid());
									startActivity(intent);
								}else {
									toGetJC1Result(art.getRid(), art.getPrice(), "");
								}
							}
						},1000);
					}

					@Override
					public void onFailure(ErrorMsg errorMessage) {
						// TODO 自动生成的方法存根
						new Dialog_Hint(_this,""+errorMessage.msg,false).showShort();
					}
				});
	}

	public void toPayForCard() {
		payType=1;
		dialog("赠送卡支付", "您确认使用赠送卡支付吗？", "取消", true, "确认", "");
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case LOGIN_PAY:	
				break;
			default:
				break;
			}
		}
	}
}
