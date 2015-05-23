package com.opar.mobile.uplayer.ui;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.opar.mobile.uplayer.util.FileUtil;
import com.youku.login.base.YoukuLoginOperator;
import com.youku.player.YoukuPlayerBaseApplication;

/**
 * 接入时自定义的Application需要继承YoukuPlayerBaseApplication 
 *
 */
public class MyApplication extends YoukuPlayerBaseApplication {

	private static MyApplication mApplication;
	/** OPlayer SD卡缓存路径 */
	public static final String OPLAYER_CACHE_BASE = Environment.getExternalStorageDirectory() + "/uplayer";
	/** 视频截图缓冲路径 */
	public static final String OPLAYER_VIDEO_THUMB = OPLAYER_CACHE_BASE + "/thumb/";
	/** 首次扫描 */
	public static final String PREF_KEY_FIRST = "application_first";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplication = this;
		initImagerLoder();
		YoukuLoginOperator.initYoukuLogin(this);
	}
	
	/**
	 * 通过覆写该方法，返回“正在缓存视频信息的界面”，
	 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
	 * 用户需要定义自己的缓存界面
	 */
	@Override
	public Class<? extends Activity> getCachingActivityClass() {
		// TODO Auto-generated method stub
		return Activity_Caching.class;
	}
	
	/**
	 * 通过覆写该方法，返回“已经缓存视频信息的界面”，
	 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
	 * 用户需要定义自己的已缓存界面
	 */
	
	@Override
	public Class<? extends Activity> getCachedActivityClass() {
		// TODO Auto-generated method stub
		return Activity_Cached.class;
	}

	/**
	 * 配置视频的缓存路径，格式举例： /appname/videocache/
	 * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/ 
	 *
	 */
	@Override
	public String configDownloadPath() {
		//return "/myapp/videocache/";			//举例
		return null;
	}

	public static MyApplication getApplication() {
		return mApplication;
	}

	public static Context getContext() {
		return mApplication;
	}

	/** 销毁 */
	public void destory() {
		mApplication = null;
	}
	
//	private void initImagerLoder()
//	{
//		File cacheDir = StorageUtils.getCacheDirectory(context);  
//		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplication());
//		// 设定线程等级比普通低一点
//		builder.threadPriority(Thread.NORM_PRIORITY - 2);
//		// 设定内存图片缓存大小限制，不设置默认为屏幕的宽高
//        builder.memoryCacheExtraOptions(480, 800);
//        builder.diskCacheExtraOptions(480, 800, null);
//		// 设定内存缓存为弱缓存
//        builder.memoryCache(new WeakMemoryCache());
//        // 缓存到内存的最大数据
//        builder.memoryCacheSize(8 * 1024 * 1024);
//        // 文件数量
//        builder.discCacheFileCount(1000);
//        builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
//        // 设定只保存同一尺寸的图片在内存
//        builder.denyCacheImageMultipleSizesInMemory();
//        // 设定缓存的SDcard目录，UnlimitDiscCache速度最快
//        builder.discCache(new UnlimitedDiscCache(cacheDir));
//        // 设定网络连接超时 timeout: 10s 读取网络连接超时read timeout: 60s
//        builder.imageDownloader(new BaseImageDownloader(getApplication(), 10000, 20000));
//        builder.discCacheFileNameGenerator(new Md5FileNameGenerator());
//        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
//        builder.build();
//       ImageLoader.getInstance().init(builder.build());
//       
//	}
	
	private void initImagerLoder()
	{
		DisplayImageOptions  options = new DisplayImageOptions.Builder()            
        .cacheInMemory()                                             
        .cacheOnDisc()                                                   
        //.displayer(new RoundedBitmapDisplayer(5))       
        .build();
     ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .threadPriority(Thread.NORM_PRIORITY - 2)
        .defaultDisplayImageOptions(options)
        .denyCacheImageMultipleSizesInMemory()
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .build();
       ImageLoader.getInstance().init(config2);
       
	}
	
}
