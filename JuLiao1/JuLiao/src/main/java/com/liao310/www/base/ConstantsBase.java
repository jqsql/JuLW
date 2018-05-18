package com.liao310.www.base;

public class ConstantsBase {  
	public static final int http = 0;
	public static final int https = 1;
	public static final int httpOrhttps = http;

	/************************线上环境*************************/
	public static final String IP_Commen = "http://ad.310liao.com/";//http://liao.liuh5.com/
	public static final String IP_TEST = "http://test.310liao.com/";
	public static final String IP = IP_TEST;
	/**
	 * 附件下载成功状态位
	 */
	public final static int FILEDOWNOK = 1;
	/**
	 * 附件下载进度
	 */
	public final static int FILEDOWNNOW = 3;
	/**
	 * 附件下载失败状态位
	 */
	public final static int FILEDOWNERROR = 2;
	/**
	 * 附件下载开始状态位
	 */
	public final static int FILEDOWNSTART = 5;
	/**
	 * 图片本地下载
	 */
	public static final int GETLOCATEDRAWABLE = 7;

	public static final String APPIP_WEIXIN = "wx06fdc03da52b965a";
}
