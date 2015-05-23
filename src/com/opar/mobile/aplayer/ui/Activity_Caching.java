package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.opar.mobile.aplayer.util.UITimer.OnUITimer;

import com.opar.mobile.aplayer.ui.adapter.CachingVideoAdapter;
import com.opar.mobile.aplayer.util.UITimer;
import com.opar.mobile.uplayer.R;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;


/**
 * 简单展示正在的视频，用户可定制自己的界面
 *
 */

public class Activity_Caching extends Activity implements OnItemClickListener{
	//通过DownloadManager获取下载视频列表
	private DownloadManager downloadManager;
	//记录当前下载中的视频列表
	private ArrayList<DownloadInfo> downloadingList_show = new ArrayList<DownloadInfo>();
	//数据Adapter
	private CachingVideoAdapter adapter;
	
	private static final int MSG_STATE_CHANGE = 0;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == MSG_STATE_CHANGE){
				initData();
				//更改界面现实状态
				adapter.notifyDataSetChanged();
			}
		}
		
	};
	
	private UITimer timer = new UITimer(2000, new OnUITimer() {
		@Override
		public void onTimer() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessageDelayed(0, 200);
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_caching);
		setTitle("正在缓存");
		ListView lv = (ListView)this.findViewById(R.id.list);
		adapter = new CachingVideoAdapter(this,downloadingList_show);
		lv.setAdapter(adapter);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(timer!=null)
		   timer.start();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(timer!=null)
		   timer.stop();
	}
	
	/**
	 * 获取当前正在下载的视频信息
	 */
	private void initData(){
		downloadingList_show.clear();
		
		//通过DownloadManager类的getDownloadingData()接口函数获取已经下载的视频信息
		downloadManager = DownloadManager.getInstance();
		Iterator iter = downloadManager.getDownloadingData().entrySet().iterator();
		
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			//视频信息实体类用DownloadInfo表示
			DownloadInfo info = (DownloadInfo) entry.getValue();
			downloadingList_show.add(info);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		DownloadInfo info = adapter.getItem(arg2);
		if(info.state == DownloadInfo.STATE_DOWNLOADING
				|| info.state == DownloadInfo.STATE_WAITING
				|| info.state == DownloadInfo.STATE_INIT
				|| info.state == DownloadInfo.STATE_EXCEPTION){
			//处于以上几种状态时单击进行暂停任务
			downloadManager.pauseDownload(info.taskId);
		}else if(info.state == DownloadInfo.STATE_PAUSE){						
			//处于暂停状态时单击进行继续任务
			downloadManager.startDownload(info.taskId);
		}
		handler.sendEmptyMessageDelayed(0, 200);
		//删除视频
//		new AsyncTask<Void, Void, Boolean>(){
//			@Override
//			protected Boolean doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				return downloadManager.deleteDownloading(info.taskId);
//			}
//			
//		}.execute();
//		//通知更新
//		handler.sendEmptyMessageDelayed(0, 100);
	}
	
}
