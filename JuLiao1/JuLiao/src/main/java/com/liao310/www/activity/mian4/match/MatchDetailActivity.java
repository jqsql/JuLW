package com.liao310.www.activity.mian4.match;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.mian4.adapter.ArticleListAdapter;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.match.Match;
import com.liao310.www.domain.match.MatchDetailBackBack;
import com.liao310.www.domain.pay.PayBack;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServiceMatch;
import com.liao310.www.net.ServicePay;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.StatusBarColor;
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

public class MatchDetailActivity extends BaseActivity{
	public static final int LOGIN_PAY = 1;
	private MatchDetailActivity _this;
	private ImageView back;
	private TextView title,name1,name2;
	private RefreshListView refreshListView;
	private ArticleListAdapter myFavouriteAdapter = null;

	private ArrayList<Article> matchdetialitems;
	private boolean isGetting = false;
	private int more = 0;
	private String aid;
	private int cid;
	private int payType=0;//支付方式
	Article art;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfavourite_new);
		StatusBarColor.setStatusBar(this);//修改状态栏颜色
		_this = this;
		aid = getIntent().getStringExtra("aid");
		cid = getIntent().getIntExtra("cid",-1);
		initView();
		initdata();
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
		title =(TextView) findViewById(R.id.tv_head_title2);
		name1 =(TextView) findViewById(R.id.name_new1);
		name2 =(TextView) findViewById(R.id.name_new2);

		refreshListView = (RefreshListView) findViewById(R.id.rl_listview);
		myFavouriteAdapter = new ArticleListAdapter(_this,1,1);
		refreshListView.setAdapter(myFavouriteAdapter);
		refreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				if(matchdetialitems!=null&&position<matchdetialitems.size()&&position>=0){
					art = matchdetialitems.get(position);
					if(art.getArt_type()!=2) {
						getArticledetail(art.getRid(),art.getPrice(), "");
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
				if(isGetting){
					return;
				}

				if (isLoadingMore) {
					if(!isGetting){
						isGetting = true;
					}
					// TODO 自动生成的方法存根
					// 加载更多
					more++;
					ServiceMatch.getInstance().getMatchDetail(_this,
							aid,more,cid,
							new CallBack<MatchDetailBackBack>() {

						@Override
						public void onSuccess(MatchDetailBackBack t) {
							// TODO 自动生成的方法存根
							dodata(t,false);
						}

						@Override
						public void onFailure(ErrorMsg errorMessage) {
							// TODO 自动生成的方法存根
							more--;
							ToastUtils.showShort(_this,errorMessage.msg);
							if(myFavouriteAdapter!=null){
								myFavouriteAdapter.notifyDataSetChanged();// 重新刷新数据
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
			getMatchDetail();
		}
	}

	private void getMatchDetail() {
		more = 1;
		ServiceMatch.getInstance().getMatchDetail(_this,aid,more,
				cid,new CallBack<MatchDetailBackBack>() {
			@Override
			public void onSuccess(MatchDetailBackBack t) {
				// TODO 自动生成的方法存根
				setMatch(t.getData().getMatch());
				dodata(t,true);
			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);
				if(myFavouriteAdapter!=null){
					myFavouriteAdapter.notifyDataSetChanged();// 重新刷新数据
				}
				refreshListView.onRefreshComplete(false);
				isGetting = false;
			}
		});
	}
	public void setMatch(Match match) {
		title.setText(match.getMatchKey()+"   "+match.getLs_cname()+"   "+match.getSc_time());
		name1.setText(match.getZhuname());
		name2.setText(match.getKename());
	}
	public synchronized void dodata(MatchDetailBackBack t,boolean isRefresh){
		if(isRefresh){
			if(matchdetialitems== null){
				matchdetialitems = new ArrayList<Article>();
			}else{
				matchdetialitems.clear();
			}
			if(t.getData()!=null&& t.getData().getRecom_items()!= null
					&&t.getData().getRecom_items().getTotal()>0
					&&t.getData().getRecom_items().getItems()!=null
					&&t.getData().getRecom_items().getItems().size()>0){
				matchdetialitems.addAll(t.getData().getRecom_items().getItems());  
				myFavouriteAdapter.setDate(matchdetialitems);
			}
		}else{
			ArrayList<Article> newlists = new ArrayList<>();
			if(t.getData()!=null&& t.getData().getRecom_items()!= null
					&&t.getData().getRecom_items().getTotal()>0
					&&t.getData().getRecom_items().getItems()!=null
					&&t.getData().getRecom_items().getItems().size()>0){
				newlists = t.getData().getRecom_items().getItems();
			}
			if (newlists.size()==0) {
				ToastUtils.showShort(_this, "已无更多数据");
				more--;
			}
			if(matchdetialitems== null){
				matchdetialitems = new ArrayList<Article>();
			}
			matchdetialitems.addAll(newlists);
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
