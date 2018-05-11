package com.liao310.www.activity.mian4.zhuanjia.detail;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.ArticleDetailActivity;
import com.liao310.www.activity.pay.PayActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.shouye.ArticalJCBack;
import com.liao310.www.domain.shouye.Article;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.domain.zhuanjia.FouceBack;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.domain.zhuanjia.ZhuanJiaDetailBackBack;
import com.liao310.www.net.ServiceZhuanJia;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.utils.ToastUtils;
import com.liao310.www.widget.Dialog_Hint;
import com.liao310.www.widget.RefreshListView;
import com.liao310.www.widget.RefreshListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuanJiaDetailActivity extends BaseActivity {
	public static final int LOGIN_PAY = 1;
	public static final int LOGIN = 0;
	private ZhuanJiaDetailActivity _this;
	private ImageView back;
	private ImageView head;
	private TextView name,fensi,wenzhang,guanzhu,content;
	private View  lv3view;
	private TextView lv1,lv1notice,lv2,lv2notice,lv2line,lv3,lv3notice;
	private TextView mIntroduce;


	private String uid;
	private ArrayList<Article> articles;
	private boolean isGetting = false;
	private boolean hasFouce = false,isFoucing = false;
	private RefreshListView refreshListView;
	private ZhuanJiaDetailListAdapter myListAdapter = null;
	private int more = 1;
	Article art;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuanjia_detail);
		_this = this;
		uid = getIntent().getStringExtra("uid");		
		initView();
		initdata(false);
	}
	private void initView() {
		mIntroduce=findViewById(R.id.zhuanjia_lv_detail);
		back =(ImageView) findViewById(R.id.iv_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_this.finish();
			}
		});
		head = (ImageView) findViewById(R.id.head);
		name = (TextView) findViewById(R.id.name);
		fensi = (TextView) findViewById(R.id.fensi);
		wenzhang = (TextView) findViewById(R.id.wenzhang);
		guanzhu = (TextView) findViewById(R.id.guanzhu);
		guanzhu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!PreferenceUtil.getBoolean(_this,"hasLogin")) {
					Intent intentLogin = new Intent(_this, LoginActivity.class); 
					intentLogin.putExtra("requestName", "intentLogin");
					startActivityForResult(intentLogin,LOGIN);
				}else{
					if(!isFoucing){
						isFoucing = true;
						setFouce();
					}
				}
			}
		});
		content = (TextView) findViewById(R.id.content);
		lv1 = (TextView) findViewById(R.id.lv1);
		//lv1name = (TextView) findViewById(R.id.lv1name);
		lv1notice = (TextView) findViewById(R.id.lv1notice);

		lv2 = (TextView) findViewById(R.id.lv2);
		//lv2name = (TextView) findViewById(R.id.lv2name);
		lv2notice = (TextView) findViewById(R.id.lv2notice);
		lv2line = (TextView) findViewById(R.id.lv2line);
		lv3view = findViewById(R.id.lv3view);
		lv3 = (TextView) findViewById(R.id.lv3);
		//lv3name = (TextView) findViewById(R.id.lv3name);
		lv3notice = (TextView) findViewById(R.id.lv3notice);

		refreshListView = (RefreshListView) findViewById(R.id.rl_listview);
		myListAdapter = new ZhuanJiaDetailListAdapter(_this);
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
						intent.putExtra("rid", art.getRid() );
						startActivity(intent);
					}else {
						toGetJC1Result(art,"");
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

		//胜率等的算法介绍
		mIntroduce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Dialog_Hint dialogHint = new Dialog_Hint(ZhuanJiaDetailActivity.this, "盈利率统计说明","说明内容...");
				dialogHint.show();
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
			getZhuanJiaDetail(isMore);
		}
	}
	private void getZhuanJiaDetail(final boolean isMore) {
		ServiceZhuanJia.getInstance().getZhuanJiaDetail(_this,uid,more, 
				new CallBack<ZhuanJiaDetailBackBack>() {
			@Override
			public void onSuccess(ZhuanJiaDetailBackBack t) {
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
	public synchronized void dodata(ZhuanJiaDetailBackBack t,boolean isRefresh){
		if(isRefresh){
			if(articles== null){
				articles = new ArrayList<Article>();
			}else{
				articles.clear();
			}
			if(t.getData()!=null&&
					t.getData().getPerson()!=null) {
				setBaseData(t.getData().getPerson());
				myListAdapter.setType(t.getData().getPerson().getArt_type());
			}
			if(t.getData()!=null&& t.getData().getRecom_items()!=null
					&&t.getData().getRecom_items().getTotal()>0
					&&t.getData().getRecom_items().getItems()!=null
					&&t.getData().getRecom_items().getItems().size()>0){
				articles.addAll(t.getData().getRecom_items().getItems());  
				myListAdapter.setDate(articles);
			}
		}else{
			ArrayList<Article> newlists = new ArrayList<>();
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
	public void setBaseData(ZhuanJia person) {
		xUtilsImageUtils.display(head,R.drawable.defaultpic,person.getAvatar());
		name.setText(person.getNickname());
		fensi.setText(person.getFans_num()+"");
		wenzhang.setText(person.getArticle_num()+"");
		if(person.getFollow_status() == 0){
			hasFouce = false;
			guanzhu.setText("+ 关注");
		}else{
			hasFouce = true;
			guanzhu.setText("√已关注");
		}
		content.setText(person.getDescription());
		switch (person.getArt_type()) {
		case 0:
			lv2line.setVisibility(View.GONE);
			lv3view.setVisibility(View.GONE);

			lv1.setText(person.getDay7Shenglv()+"%");
			//lv1name.setText("胜率");
			lv1notice.setText("7日总胜率");

			lv2.setText(person.getLianhong()+"");
			//lv2name.setText("最近连红");
			lv2notice.setText("最长"+person.getLianhongMost()+"连红");
			break;
		case 1:
			lv2line.setVisibility(View.GONE);
			lv3view.setVisibility(View.GONE);

			lv1.setText(person.getDay7Mingzhonglv()+"%");
			//lv1name.setText("命中率");
			lv1notice.setText("近7日胜率");

			lv2.setText(person.getDay7Yinglilv()+"%");
			//lv2name.setText("盈利率");
			lv2notice.setText("7日盈利率");
			break;
		case 2:
			lv2line.setVisibility(View.VISIBLE);
			lv3view.setVisibility(View.VISIBLE);

			lv1.setText(person.getDay3Shenglv()+"%");
			//lv1name.setText("胜率");
			lv1notice.setText("近3日胜率");

			lv2.setText(person.getDay7Shenglv()+"%");
			//lv2name.setText("胜率");
			lv2notice.setText("近7日胜率");

			lv3.setText(person.getDay30Shenglv()+"%");
			//lv3name.setText("胜率");
			lv3notice.setText("近30日胜率");
			content.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	public void setFouce(){
		int hasFouceNew = 0;
		if(hasFouce) {
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
					hasFouce = true;
					ToastUtils.showShort(_this, "关注成功");
					guanzhu.setText("√已关注");
					guanzhu.setBackgroundResource(R.drawable.shape_app_15);
				}else {					
					hasFouce = false;
					ToastUtils.showShort(_this, "取消关注");
					guanzhu.setText("+ 关注");
					guanzhu.setBackgroundResource(R.drawable.cornerfulllightgery);
				}
				isFoucing = false;
			}
			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);
				isFoucing = false;
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case LOGIN:	
				if(!isFoucing){
					isFoucing = true;
					setFouce();
				}
				break;
			case LOGIN_PAY:	
				break;
			default:
				break;
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
}
