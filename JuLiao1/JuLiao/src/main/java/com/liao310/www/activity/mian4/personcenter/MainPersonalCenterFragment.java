package com.liao310.www.activity.mian4.personcenter;

import com.liao310.www.R;
import com.liao310.www.activity.login.LoginActivity;
import com.liao310.www.activity.mian4.WebActivity;
import com.liao310.www.activity.mian4.personcenter.setting.SettingActivity;
import com.liao310.www.base.BaseActivity;
import com.liao310.www.db.MyDbUtils;
import com.liao310.www.domain.login.User;
import com.liao310.www.tool.xUtilsImageUtils;
import com.liao310.www.utils.PreferenceUtil;
import com.liao310.www.widget.PersonalItemView;
import com.liao310.www.widget.VerticalSwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class MainPersonalCenterFragment extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
	private ImageView namePic;
	private TextView name,kefutx,money,recharge;
	private PersonalItemView order,favourite,send,invitecode,kefu,set;
	private VerticalSwipeRefreshLayout mSwipeLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main4_fragment_personal, container, false);
		EventBus.getDefault().register(this);
		initView(view);
		updateUserInfo();
		return view;
	}

	private void initView(View view) {
		mSwipeLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);

		kefutx = (TextView) view.findViewById(R.id.kefutx);
		kefutx.setOnClickListener(this);
		
		namePic = (ImageView) view.findViewById(R.id.name_im);
		namePic.setOnClickListener(this);
		name = (TextView) view.findViewById(R.id.name_tv);
		money = (TextView) view.findViewById(R.id.money);
		recharge = (TextView) view.findViewById(R.id.recharge);
		recharge.setOnClickListener(this);

		order = (PersonalItemView) view.findViewById(R.id.order);
		order.setOnClickListener(this);
		favourite = (PersonalItemView) view.findViewById(R.id.favourite);
		favourite.setOnClickListener(this);
		send = (PersonalItemView) view.findViewById(R.id.send);
		send.setOnClickListener(this);
		invitecode = (PersonalItemView) view.findViewById(R.id.invitecode);
		invitecode.setOnClickListener(this);

		kefu = (PersonalItemView) view.findViewById(R.id.kefu);
		kefu.setOnClickListener(this);
		set = (PersonalItemView) view.findViewById(R.id.set);
		set.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.kefutx:
			Intent intentKefu = new Intent(getActivity(),WebActivity.class);
			intentKefu.putExtra("url", "http://tb.53kf.com/code/client/10167449/1");
			intentKefu.putExtra("titleStr", "在线客服");
			startActivity(intentKefu);		
			break;
		case R.id.name_im:
			if (!PreferenceUtil.getBoolean(getActivity(),"hasLogin")) {
				Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
				intentLogin.putExtra("from", 4);
				startActivity(intentLogin);
			}		
			break;
		case R.id.recharge:
			if (!PreferenceUtil.getBoolean(getActivity(),"hasLogin")) {
				Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
				intentLogin.putExtra("from", 4);
				startActivity(intentLogin);
			}else {
				startActivity(new Intent(getActivity(),ReChargeActivity.class));
			}		
			break;
		case R.id.order:
			if (!PreferenceUtil.getBoolean(getActivity(),"hasLogin")) {
				Intent intentLogin = new Intent(getActivity(), LoginActivity.class); 
				intentLogin.putExtra("from", 4);
				startActivity(intentLogin);
			}else {
				startActivity(new Intent(getActivity(),MyBuyActivity.class));
			}
			break;
		case R.id.favourite:
			if (!PreferenceUtil.getBoolean(getActivity(),"hasLogin")) {
				Intent intentLogin = new Intent(getActivity(), LoginActivity.class); 
				intentLogin.putExtra("from", 4);
				startActivity(intentLogin);
			}else {
				startActivity(new Intent(getActivity(),MyFavouriteActivity.class));
			}
			break;
		case R.id.send:
			if (!PreferenceUtil.getBoolean(getActivity(),"hasLogin")) {
				Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
				intentLogin.putExtra("from", 4);
				startActivity(intentLogin);
			}else {
				startActivity(new Intent(getActivity(),MySendActivity.class));
			}
			break;
		case R.id.kefu:
			startActivity(new Intent(getActivity(),KeFuActivity.class));	
			break;
		case R.id.set:
			startActivity(new Intent(getActivity(),SettingActivity.class));	
			break;
		default:
			break;
		}
	}

	private void updateUserInfo() {
		User user = MyDbUtils.getCurrentUser();
		if (user!= null&&PreferenceUtil.getBoolean(getActivity(), "hasLogin")) {
			String url = MyDbUtils.getCurrentUser().getAvatar();	
			xUtilsImageUtils.display(namePic,url,R.drawable.defaultpic);
			name.setText(user.getNickname());
			money.setText("金币:"+user.getMoney());
			invitecode.setInfoValue(user.getInvite_code()+"");
			invitecode.setImageIn(View.GONE);
		}else {
			namePic.setBackgroundResource(R.drawable.defaultpic);
			name.setText("未登录");
			money.setText("金币:0");
		}
	}
	public void onEventMainThread(String event) {
		if (!TextUtils.isEmpty(event)) {
			if ("updateUserInfo".equals(event)) {
				updateUserInfo();
			}
		}
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		((BaseActivity)this.getActivity()).refreshInfo();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 停止刷新
				mSwipeLayout.setRefreshing(false);
			}
		}, 3000); // 5秒后发送消息，停止刷新
	}
}
