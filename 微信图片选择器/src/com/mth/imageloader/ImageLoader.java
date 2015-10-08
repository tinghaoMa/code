package com.mth.imageloader;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageLoader {
	/**
	 * 默认线程数
	 */
	private static final int DEAFULT_THREAD_COUNT = 1;
	private static ImageLoader mInstance;
	/**
	 * 图片缓存的核心对象
	 */
	private LruCache<String, Bitmap> mLruCache;

	/**
	 * 线程池
	 */
	private ExecutorService mThreadPool;
	/**
	 * 队列的调度方式
	 */
	private TYPE mType = TYPE.LIFO;

	public enum TYPE {
		FIFO, LIFO;
	}

	/**
	 * 任务队列
	 */
	private LinkedList<Runnable> mTaskQueue;
	/**
	 * 后台轮询线程
	 */
	private Thread mPoolThread;

	private Handler mPoolThreadHandler;
	/**
	 * UI线程的Handler
	 */
	private Handler mUiHandler;

	private Semaphore mSemaPhore = new Semaphore(0);

	private Semaphore mSemaphoreThreadPool;

	private ImageLoader(int threadCount, TYPE type) {
		init(threadCount, type);
	}

	/**
	 * 初始化
	 * 
	 * @param threadCount
	 * @param type
	 */
	private void init(int threadCount, TYPE type) {
		mPoolThread = new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				mPoolThreadHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// 线程池去取一个任务来执行
						mThreadPool.execute(getTask());
						try {
							mSemaphoreThreadPool.acquire();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				};
				// 释放一个信号量
				mSemaPhore.release();
				Looper.loop();
			}
		};
		mPoolThread.start();
		/**
		 * 获取应用的最大可用内存
		 */
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheMemory = maxMemory / 8;

		mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 每行字节数乘以高度
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
		/**
		 * 创建线程池
		 */
		mThreadPool = Executors.newFixedThreadPool(DEAFULT_THREAD_COUNT);
		mTaskQueue = new LinkedList<Runnable>();
		mType = type;
		mSemaphoreThreadPool = new Semaphore(threadCount);
	}

	public static ImageLoader getInstance(int threadCount, TYPE type) {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(threadCount, type);
				}
			}
		}
		return mInstance;
	}

	public static ImageLoader getInstance() {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(DEAFULT_THREAD_COUNT, TYPE.LIFO);
				}
			}
		}
		return mInstance;
	}

	/**
	 * 根据Path为ImageView设置图片
	 * 
	 * @param path
	 * @param imageView
	 */
	public void loadImage(final String path, final ImageView imageView) {
		imageView.setTag(path);
		if (mUiHandler == null) {
			mUiHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// 获取得到的图片显示到ImageView上
					ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
					Bitmap bm = holder.bm;
					ImageView imageView = holder.imageView;
					String path = holder.path;
					if (imageView.getTag().toString().equals(path)) {
						imageView.setImageBitmap(bm);
					}
				}
			};
		}
		// 根据path在缓存中获取
		Bitmap bm = getBitmapFromLruCache(path);
		if (bm != null) {
			refreshBitmap(path, imageView, bm);
		} else {
			addTask(new Runnable() {

				@Override
				public void run() {
					// 加载图片
					// 图片压缩
					// 1.获得图片需要显示的大小
					ImgSize imgSize = getImageViewSize(imageView);
					// 2.压缩图片
					Bitmap bm = decodeSampledBitmapFromPath(path,
							imgSize.imageWidth, imgSize.imageHeight);
					// 3.放到内存
					addBitmapToLruCache(path, bm);
					// 4.回调,显示图片
					refreshBitmap(path, imageView, bm);
					// 释放信号量
					mSemaphoreThreadPool.release();
				}

			});
		}
	}

	/**
	 * 显示图片
	 * 
	 * @param path
	 * @param imageView
	 * @param bm
	 */
	private void refreshBitmap(final String path, final ImageView imageView,
			Bitmap bm) {
		Message msg = Message.obtain();
		ImageBeanHolder holder = new ImageBeanHolder();
		holder.bm = bm;
		holder.path = path;
		holder.imageView = imageView;
		msg.obj = holder;
		mUiHandler.sendMessage(msg);
	}

	/**
	 * 将图片放入缓存
	 * 
	 * @param path
	 * @param bm
	 */
	protected void addBitmapToLruCache(String path, Bitmap bm) {
		if (getBitmapFromLruCache(path) == null) {
			if (bm != null) {
				mLruCache.put(path, bm);
			}
		}
	}

	/**
	 * 根据图片显示的宽和高来压缩图片 Options
	 * 
	 * @param path
	 * @param imageWidth
	 *            需求的宽
	 * @param imageHeight
	 *            需求的高
	 * @return
	 */
	protected Bitmap decodeSampledBitmapFromPath(String path, int imageWidth,
			int imageHeight) {
		// 获取到图片的宽和高但是并不把图片加载到内存中
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options); // options里面包含真正的宽和高
		options.inSampleSize = caculateInSampleSize(options, imageHeight,
				imageWidth);
		// 解析图片 加载到内存
		options.inJustDecodeBounds = false;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		return bm;
	}

	/**
	 * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
	 * 
	 * @param options
	 * @param imageHeight
	 * @param imageWidth
	 * @return
	 */
	private int caculateInSampleSize(Options options, int reqHeight,
			int reqWidth) {
		int width = options.outWidth;
		int height = options.outHeight;
		int inSampleSize = 1;
		if (width > reqWidth || height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);
			inSampleSize = Math.max(widthRadio, heightRadio); // 越小越好 策略根据项目来定
		}
		return inSampleSize;
	}

	/**
	 * 根据ImageView获取到需要显示的大小
	 * 
	 * @param imageView
	 * @return
	 */
	protected ImgSize getImageViewSize(ImageView imageView) {
		ImgSize mImgSize = new ImgSize();
		DisplayMetrics metrics = imageView.getContext().getResources()
				.getDisplayMetrics();

		LayoutParams lp = imageView.getLayoutParams();
		int width = imageView.getWidth(); // 获取imageview的实际宽度
		if (width <= 0) {
			width = lp.width; // 获取imageview在layout中声明的宽度
		}
		if (width <= 0) {
			width = getImageFieldValue(imageView, "mMaxWidth");// 检查最大值
		}
		if (width <= 0) {
			width = metrics.widthPixels; // 屏幕宽度
		}
		mImgSize.imageWidth = width;
		int height = imageView.getHeight(); // 获取imageview的实际高度
		if (height <= 0) {
			height = lp.height; // 获取imageview在layout中声明的高度
		}
		if (height <= 0) {
			height = getImageFieldValue(imageView, "mMaxHeight");// 检查最大值
		}
		if (height <= 0) {
			height = metrics.heightPixels; // 屏幕高度
		}
		mImgSize.imageHeight = height;
		return mImgSize;
	}

	/**
	 * 通过反射ImageView的某个属性值兼容到API 8
	 * 
	 * @param object
	 * @param filedName
	 * @return
	 */
	private static int getImageFieldValue(Object object, String filedName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(filedName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 从任务队列取出一个方法
	 * 
	 * @return
	 */
	private Runnable getTask() {
		if (mType == TYPE.FIFO) {
			return mTaskQueue.removeFirst();
		} else if (mType == TYPE.LIFO) {
			return mTaskQueue.removeLast();
		}
		return null;
	}

	private synchronized void addTask(Runnable runnable) {
		try {
			mTaskQueue.add(runnable);
			if (mPoolThreadHandler == null)
				mSemaPhore.acquire(); // 此方法会阻塞
			mPoolThreadHandler.sendEmptyMessage(0x110);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据path在缓存中获取
	 * 
	 * @param path
	 * @return
	 */
	private Bitmap getBitmapFromLruCache(String path) {
		return mLruCache.get(path);
	}

	private class ImageBeanHolder {
		Bitmap bm;
		ImageView imageView;
		String path;
	}

	private class ImgSize {
		int imageWidth;
		int imageHeight;
	}
}
