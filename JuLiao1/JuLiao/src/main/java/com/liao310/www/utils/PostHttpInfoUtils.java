package com.liao310.www.utils;

import com.liao310.www.domain.version.ErrorMsg;

import android.content.Context;
import android.text.TextUtils;

public class PostHttpInfoUtils {

	public static void doPostFail(Context mContext,ErrorMsg errorMessage,String defaultErrMsg) {
		if (errorMessage != null
				&& (!TextUtils.isEmpty(errorMessage.msg))) {
			String[] msgs = errorMessage.msg.split("-");
			if (msgs.length == 1) {

				ToastUtils.showShort(mContext, msgs[0]);
			} else {

				ToastUtils.showShort(mContext, msgs[1]);
			}
		} else {
			ToastUtils.showShort(mContext, defaultErrMsg);
		}
	}

}
