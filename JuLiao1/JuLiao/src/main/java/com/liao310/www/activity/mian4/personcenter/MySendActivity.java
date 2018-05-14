package com.liao310.www.activity.mian4.personcenter;

import java.util.ArrayList;

import com.liao310.www.R;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.domain.personal.SendCard;
import com.liao310.www.domain.personal.SendCardListBackBack;
import com.liao310.www.domain.version.ErrorMsg;
import com.liao310.www.net.ServicePersonal;
import com.liao310.www.net.https.ServiceABase.CallBack;
import com.liao310.www.utils.ToastUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MySendActivity extends BaseActivity {
	private MySendActivity _this;
	private ImageView back;
	private TextView title;
	private TextView wx;
	private ListView listView;
	private MySendAdapter mySendAdapter = null;

	private ArrayList<SendCard> sendCards = new ArrayList<SendCard>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mysend);
		_this = this;
		initView();
		getMySend();
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
		title.setText("我的赠送");

		listView = (ListView) findViewById(R.id.rl_listview);
		View footer = LayoutInflater.from(_this).inflate(R.layout.mysend_footer, null);
		listView.addFooterView(footer);
		
		String wxStr = "<html><font color=\"#ffffff\">赶快添加聚料客服微信"
				+ "</font><font color=\"#02DB84\">juliao310</font><font color=\"#ffffff\">来领取吧~</font></html>";
	
		wx = (TextView) footer.findViewById(R.id.towx);
		wx.setText(Html.fromHtml(wxStr));
		wx.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("weixin://"));    
				startActivity(intent);
			}
		});
		mySendAdapter = new MySendAdapter();
		listView.setAdapter(mySendAdapter);
	}
	private void getMySend() {
		ServicePersonal.getInstance().getMySend(_this,
				new CallBack<SendCardListBackBack>() {
			@Override
			public void onSuccess(SendCardListBackBack t) {
				// TODO 自动生成的方法存根
				dodata(t);
			}

			@Override
			public void onFailure(ErrorMsg errorMessage) {
				// TODO 自动生成的方法存根
				ToastUtils.showShort(_this, errorMessage.msg);
				if(mySendAdapter!=null){
					mySendAdapter.notifyDataSetChanged();// 重新刷新数据
				}
			}
		});
	}
	public  void dodata(SendCardListBackBack t){
		if(sendCards== null){
			sendCards = new ArrayList<SendCard>();
		}else{
			sendCards.clear();
		}
		if(t.getData()!=null&& t.getData().getCard_items()!=null
				&&t.getData().getCard_items().getTotal()>0
				&&t.getData().getCard_items().getItems()!=null
				&&t.getData().getCard_items().getItems().size()>0){
			sendCards.addAll(t.getData().getCard_items().getItems());  
			mySendAdapter.setDate(sendCards);
		}

		if(mySendAdapter!=null){
			mySendAdapter.notifyDataSetChanged();// 重新刷新数据
		}
	}
	class MySendAdapter extends BaseAdapter{
		private ArrayList<SendCard> cardsLists;
		public void setDate(ArrayList<SendCard> orderListsFrom){
			this.cardsLists = orderListsFrom;
		}
		@Override
		public int getCount() {
			if(cardsLists == null){
				return 0; 
			}else{
				return cardsLists.size();
			}
		}
		@Override
		public SendCard getItem(int position) {
			if(cardsLists == null){
				return null; 
			}else{
				return cardsLists.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SendCard card = cardsLists.get(position);
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(_this, R.layout.mysend_item,null);
				holder.money = (TextView) convertView.findViewById(R.id.money);
				holder.num = (TextView) convertView.findViewById(R.id.num);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.money.setText(card.getMoney()+"");
			holder.num.setText(card.getNum()+"张");
			return convertView;

		}
		class ViewHolder {
			public TextView money;
			public TextView num;
		}
	}
}
