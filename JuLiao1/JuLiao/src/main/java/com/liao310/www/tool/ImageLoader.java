package com.liao310.www.tool;


import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import com.liao310.www.R;
import com.liao310.www.base.ConstantsBase;
import com.liao310.www.utils.SilentDownHelp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;


public class ImageLoader
{

	// 图片缓存
	private HashMap<String, SoftReference<Bitmap>> imageCacheForZoom;
	// 回调对应
	private ImageCallBack callBack;
	private static ImageLoader instance;
	private static Context mcontext;
	private int needRemoveNumber = 6;
	private long beginTime = 0;// 获取图片的开始时间
	private long endTime = 0;// 获取图片的结束时间

	private ImageLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		imageCacheForZoom = new HashMap<String, SoftReference<Bitmap>>();
		DisplayMetrics dm;
		dm = new DisplayMetrics();
		((Activity) mcontext).getWindowManager().getDefaultDisplay()
		.getMetrics(dm);
		//	needRemoveNumber = (GridImageViewActivity.columNumb * (int) (Math
		//	.ceil(dm.heightPixels * 2.0 / dm.widthPixels)));// 计算一个屏幕上可以展示多少照片dm.heightPixels/(dm.widthPixels/2)
	}
	/*Bitmap bitmap = ImageLoader.getInstance(this).loadImage(0,
	head, url, new ImageCallBack() {

@Override
public void imageLoad(ImageView view, Bitmap bt) {
	if (bt != null && null != view) {
		view.setImageBitmap(bt);
	}

}
}, DirsUtil.getSD_DOWNLOADS());
if (bitmap != null) {
head.setImageBitmap(bitmap);
} else {
head.setImageDrawable(this.getResources()
		.getDrawable(R.drawable.defaultpic));
}*/

	/**
	 * 获取单例模式
	 *
	 * @param context
	 * @return
	 */
	public static ImageLoader getInstance(Context context)
	{

		mcontext = context;
		if (instance == null)
		{
			instance = new ImageLoader();
		}
		else
		{
		}
		return instance;
	}

	/**
	 * 获取单例模式
	 *
	 * @param context
	 * @param type
	 *            没有作用
	 * @return
	 */
	public static ImageLoader getInstance(Context context, int type)
	{

		mcontext = context;
		if (instance == null)
		{
			instance = new ImageLoader(0);
		}
		else
		{
		}
		return instance;
	}

	private ImageLoader(int type) {

		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	private byte[] synchronizedObject = new byte[] { '1' };
	public static ArrayList<MessageInfo> data = new ArrayList<MessageInfo>();

	/**
	 * 判断是否图片存在，如果存在则返回对应的图片，如果不存在则请求下载图片
	 * @return 如果存在图片则返回对应图片
	 */
	public Bitmap loadImage1(final String imagePath, int itemw)
	{

		// 判断 缓存中是否有对应的图片
		if (imageCache.containsKey(imagePath))
		{
			SoftReference<Bitmap> reference = imageCache.get(imagePath);
			Bitmap bitmap = reference.get();
			if (bitmap != null)
			{
				return bitmap;
			}
		}

		// 如果缓存中没有 则判断sd卡 或者对应地址中是否下载过对应的图片，如果下载过的话，就不用在下载，直接从sd卡中获取得到
		Bitmap imageBitmap = getDrawable(imagePath, itemw);
		if (imageBitmap != null)
		{

			imageCache.put(imagePath, new SoftReference<Bitmap>(imageBitmap));
			return imageBitmap;
		}

		return null;

	}

	/**
	 * 判断是否图片存在，如果存在则返回对应的图片，如果不存在则请求下载图片
	 *
	 * @param arg
	 *            位置
	 * @param imageView
	 *            图片对象
	 * @param url
	 *            请求id
	 * @param callBack
	 *            回调方法
	 * @return 如果存在图片则返回对应图片
	 */
	public Bitmap loadImage(int arg, final ImageView imageView,
			final String url, ImageCallBack callBack,String sdCardPath,int defaultPic)
	{
		this.callBack = callBack;
		if (null == url || "null".equalsIgnoreCase(url))
		{
			////return BitmapFactory.decodeResource(mcontext.getResources(),
			//	R.drawable.icon_examp);
			return null;

		}
		else
		{

			// 判断 缓存中是否有对应的图片
			if (imageCache.containsKey(url))
			{
				SoftReference<Bitmap> reference = imageCache.get(url);
				Bitmap bitmap = reference.get();
				if (bitmap != null)
				{
					return bitmap;
				}
			}

			// String bitmap = DirsUtil.getSD_DOWNLOADS()
			// + id.replace("/Resource/UserPhoto/", "")
			// + ComConstantsBaseNEW.ICON;

			String bitmap = "";
			if ("".equals(url) || "undefined".equals(url))
			{
				return null;
			}

			String[] name = url.split("/");
			System.out.println("-----loadImage--name.length--> " + name.length);
			if (name != null && name.length > 1)
			{
				String  filename =  name[name.length - 1];
				bitmap =sdCardPath+filename;
			}
			System.out.println("-----loadImage--bitmap--> " + bitmap);
			if (("").equals(bitmap))
			{

				return BitmapFactory.decodeResource(mcontext.getResources(),
						defaultPic);

			}

			// 如果缓存中没有 则判断sd卡 或者对应地址中是否下载过对应的图片，如果下载过的话，就不用在下载，直接从sd卡中获取得到
			Bitmap imageBitmap = setImageOptions(bitmap, true);
			System.out.println("-----loadImage--imageBitmap != null--> " + (imageBitmap != null));
			if (imageBitmap != null)
			{
				imageCache.put(url, new SoftReference<Bitmap>(imageBitmap));
				return imageBitmap;
			}

			// 如果没有 则启动线程去下载对应的附件
			if (SilentDownHelp.getInstance(true,sdCardPath).isAddDownTask(url))
			{
			}
			else
			{
				SilentDownHelp.getInstance(true,sdCardPath).addDownFile(mcontext,
						(long) arg, url,  attachmentHandler,imageView);
			}
			return null;
		}
	}


	/**
	 * 仅加载  本地图片
	 *
	 * @param arg
	 *            位置
	 * @param imageView
	 *            图片对象
	 * @param url
	 *            请求id
	 * @param callBack
	 *            回调方法
	 * @return 如果存在图片则返回对应图片
	 */
	public Bitmap loadNativeImage(int arg, final ImageView imageView,
			final String url, ImageCallBack callBack )
	{
		System.out.println("-----loadImage--url--> " + url);
		this.callBack = callBack;
		if (null == url || "null".equalsIgnoreCase(url))
		{
			return null;

		}
		else
		{
			// 判断 缓存中是否有对应的图片
			if (imageCache.containsKey(url))
			{
				SoftReference<Bitmap> reference = imageCache.get(url);
				Bitmap bitmap = reference.get();
				if (bitmap != null)
				{
					return bitmap;
				}
			}

			String bitmap =url;
			if (("").equals(bitmap))
			{
				return BitmapFactory.decodeResource(mcontext.getResources(),
						R.drawable.defaultpic);
			}

			// 如果缓存中没有 则判断sd卡 或者对应地址中是否下载过对应的图片，如果下载过的话，就不用在下载，直接从sd卡中获取得到
			Bitmap imageBitmap = setImageOptions(bitmap, true);
			if (imageBitmap != null)
			{
				imageCache.put(url, new SoftReference<Bitmap>(imageBitmap));
				return imageBitmap;
			}else{
				return BitmapFactory.decodeResource(mcontext.getResources(),
						R.drawable.defaultpic);		
			}

		}
	}



	// 有下载类将图片数据回调到对应的类
	Handler attachmentHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			int what = msg.what;
			Object[] obj = (Object[]) msg.obj;
			if (obj != null)
			{

				if (what == ConstantsBase.FILEDOWNOK)
				{
					System.out.println("文件  下载成功 》》》"+(String) obj[0]);
					//

					Bitmap imageBitmap = setImageOptions((String) obj[0], false);
					System.out.println("ComConstantsBaseNEW.FILEDOWNOK"
							+ imageBitmap.toString());
					imageCache.put((String) obj[1], new SoftReference<Bitmap>(
							imageBitmap));
					callBack.imageLoad((ImageView) obj[2], imageBitmap);
				}
				else if (what == ConstantsBase.FILEDOWNERROR)
				{
					System.err.println((String) obj[0]);
				}
			}
		};
	};

	/**
	 * 给予对应的路径，将图片压缩返回数据
	 *
	 * @param path
	 *            图片本地路径
	 * @param isEmpty
	 *            是否要返回空，true表示返回空，false表示返回nophoto的图片
	 * @return
	 */
	private Bitmap setImageOptions(String path, boolean isEmpty)
	{
		Bitmap imageBitmap = null;
		try
		{

			BitmapFactory.Options options_tmp = new BitmapFactory.Options();
			//options_tmp.inJustDecodeBounds = false;
			//options_tmp.inSampleSize = 10;   //width，hight设为原来的十分一
			imageBitmap = BitmapFactory.decodeFile(path, options_tmp);
		}
		catch (OutOfMemoryError e)
		{
			//FileLog.fLogException(e + "");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			//FileLog.fLogException(e + "");
			e.printStackTrace();
		}

		if (null != imageBitmap && !imageBitmap.isRecycled())
		{

			/*
			 * int oldWidth = imageBitmap.getWidth(); int oldHeight =
			 * imageBitmap.getHeight();
			 * 
			 * float bili = (float) oldHeight / (float) oldWidth; //
			 * 如果options定义为OPTIONS_RECYCLE_INPUT,则回收 imageBitmap =
			 * ThumbnailUtils.extractThumbnail(imageBitmap, widowWidth / 2,
			 * (int) (widowWidth / 2 * bili),
			 * ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			 */

		}
		else
		{
			if (isEmpty)
			{
				return null;
			}
			imageBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
					R.drawable.defaultpic);
		}
		return imageBitmap;

	}

	/**
	 * 释放缓存 for Bitmap
	 *
	 */
	public void releaseBitmap(String key)
	{
		if (imageCache.containsKey(key))
		{
			SoftReference<Bitmap> reference = imageCache.get(key);
			Bitmap bitmap = reference.get();
			if (bitmap != null && !bitmap.isRecycled())
			{
				bitmap.recycle();
			}
		}
	}

	private Bitmap getDrawable(String path, int itemw)
	{

		DisplayMetrics dm;
		dm = new DisplayMetrics();
		((Activity) mcontext).getWindowManager().getDefaultDisplay()
		.getMetrics(dm);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap tempimageBitmap = BitmapFactory.decodeFile(path, options);
		float maxlength = options.outHeight < options.outWidth ? options.outHeight
				: options.outWidth;
		options.inSampleSize = (int) (maxlength / (itemw * dm.density));
		options.inJustDecodeBounds = false;
		tempimageBitmap = BitmapFactory.decodeFile(path, options);

		/**
		 * 图片缩放
		 */
		// 因为关于图片操作是像素 因此这边需要将Px*密度
		if (tempimageBitmap == null)
		{
			Log.e("hh", "tempimageBitmap为空");
		}

		Bitmap imageBitmap;
		try
		{
			imageBitmap = ThumbnailUtils.extractThumbnail(tempimageBitmap,
					(int) (itemw * dm.density - 2 * dm.density), (int) (itemw
							* dm.density - 2 * dm.density),
							ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			if (tempimageBitmap != null && !tempimageBitmap.isRecycled())
			{
				tempimageBitmap.recycle();
			}
			return imageBitmap;
		}
		catch (OutOfMemoryError e)
		{

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 清除缓存目录
	 *
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime)
	{
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory())
		{
			try
			{
				for (File child : dir.listFiles())
				{
					if (child.isDirectory())
					{
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime)
					{
						if (child.delete())
						{
							deletedFiles++;
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	MessageInfo tempMessage;

	private HashMap<String, SoftReference<Bitmap>> imageCache;

	class SendUrl extends Thread
	{
		@Override
		public void run()
		{
			synchronized (synchronizedObject)
			{
				while (true)
				{
					if (data != null && data.size() > 0)
					{
						beginTime = System.currentTimeMillis();
						if (Math.abs(endTime - beginTime) <= 200)
						{
							try
							{
								this.sleep(200);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
						int len = data.size();

						if (len >= needRemoveNumber)
						{
							for (int i = 0; i < len - needRemoveNumber; i++)
							{
								data.remove(0);

							}
						}
						tempMessage = data.get(0);
						Bitmap bm = null;
						// 获取图片资源
						// 直接压缩图片
						if (tempMessage.getStyle() == MessageInfo.ZOOMSTYLE)
						{

							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = tempMessage.getZoomOrItemw();
							// 图片必须在adapter了里面获取 ，因为view可以在必要时进行展示
							if (imageCacheForZoom.containsKey(tempMessage
									.getPath()))
							{

								SoftReference<Bitmap> reference = imageCacheForZoom
										.get(tempMessage.getPath());
								bm = reference.get();

							}
							if (null == bm)
							{
								bm = BitmapFactory.decodeFile(
										tempMessage.getPath(), options);
								imageCacheForZoom.put(tempMessage.getPath(),
										new SoftReference<Bitmap>(bm));
							}
							else
							{
							}
						}// 裁剪图片
						else if (tempMessage.getStyle() == MessageInfo.ITEMW)
						{

							bm = loadImage1(tempMessage.getPath(),
									tempMessage.getZoomOrItemw());

						}

						if (null != bm)
						{
							if (tempMessage.getHandler() != null)
							{

								android.os.Message message = new android.os.Message();
								message.what = ConstantsBase.GETLOCATEDRAWABLE;
								message.obj = new Object[] { bm,
										tempMessage.getImage(),
										tempMessage.getKey() };
								tempMessage.getHandler().sendMessage(message);
							}
							endTime = System.currentTimeMillis();
							data.remove(0);
						}
						else
						{
						}

					}

					if (data == null || data.size() == 0)
					{
						try
						{
							synchronizedObject.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}

				}

			}
		}
	}

	public boolean getLocalDrawable(int Style, String path, Handler handler,
			int zoomOrItemw, ImageView imageView, int key)
	{

		/**
		 * 这里坚决不能使用同步锁
		 */
		/*
		 * synchronized (synchronizedObject) { if (data != null) {
		 * 
		 * // int len=data.size(); MessageInfo tempData; // 长度会实时变动，所以不能先获取长度
		 * for (int i = 0; i < data.size(); i++) {
		 * System.out.println("%%%%%%111data.size()" + data.size()); tempData =
		 * data.get(i); if (tempData.getStyle() == Style &&
		 * tempData.getPath().equals(path)) { tempData.setImage(imageView);
		 * tempData.setHandler(handler);
		 * System.out.println("%%%%在同步锁里面更新data里面的MessageInfo" + key); return
		 * true;
		 * 
		 * } } } }
		 */
		MessageInfo temp = new MessageInfo();
		temp.setHandler(handler);
		temp.setImage(imageView);
		temp.setPath(path);
		temp.setStyle(Style);
		temp.setZoomOrItemw(zoomOrItemw);
		temp.setKey(key);
		data.add(temp);

		if (null == sendable || !sendable.isAlive())
		{
			sendable = new Sendable();
			sendable.start();
		}
		else
		{
		}
		return false;

	}

	private Thread sendUrl = null;
	private Thread sendable = null;

	class Sendable extends Thread
	{
		@Override
		public void run()
		{

			synchronized (synchronizedObject)
			{

				synchronizedObject.notifyAll();

				if (null == sendUrl || !sendUrl.isAlive())
				{
					sendUrl = new SendUrl();
					sendUrl.start();
				}
				else
				{

				}
			}

		}
	};

	class MessageInfo
	{
		private int Style;// 0:直接按长宽比例缩放 1：在缩放到指定长，宽
		public static final int ZOOMSTYLE = 1;
		public static final int ITEMW = 2;

		/**
		 * 对应传递回去的imageView 用于下载图片
		 */
		private ImageView image;

		public ImageView getImage()
		{
			return image;
		}

		public void setImage(ImageView image)
		{
			this.image = image;
		}

		/**
		 * 对应请求 从activity传递的hander
		 */
		private Handler handler;

		public Handler getHandler()
		{
			return handler;
		}

		public void setHandler(Handler handler)
		{
			this.handler = handler;
		}

		private String path;

		public String getPath()
		{
			return path;
		}

		public void setPath(String path)
		{
			this.path = path;
		}

		private int zoomOrItemw;

		public int getStyle()
		{
			return Style;
		}

		public void setStyle(int style)
		{
			Style = style;
		}

		public int getZoomOrItemw()
		{
			return zoomOrItemw;
		}

		public void setZoomOrItemw(int zoomOrItemw)
		{
			this.zoomOrItemw = zoomOrItemw;
		}

		private int key;

		public int getKey()
		{
			return key;
		}

		public void setKey(int key)
		{
			this.key = key;
		}
	}
}

class LocalImageHander extends Handler
{
	public void handleMessage(Message msg)
	{
		int what = msg.what;
		Object[] obj = (Object[]) msg.obj;
		if (obj != null)
		{
			if (what == ConstantsBase.GETLOCATEDRAWABLE)
			{
				Bitmap imageBitmap = (Bitmap) obj[0];
				Drawable drawable = new BitmapDrawable(imageBitmap);
				((ImageView) obj[1]).setImageDrawable(drawable);
			}
		}
	}

}