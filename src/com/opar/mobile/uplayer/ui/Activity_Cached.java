package com.opar.mobile.uplayer.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.opar.mobile.uplayer.PlayerActivity;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.ui.adapter.CachedVideoAdapter;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

/**
 * 简单展示已经缓存的视频，用户可定制自己的界面
 *
 */
public class Activity_Cached extends ActivityBase implements OnItemClickListener{
	//数据Adapter
	private CachedVideoAdapter adapter;
	//通过DownloadManager获取下载视频列表
	private DownloadManager downloadManager;
	//记录当前已经下载的视频列表
	private ArrayList<DownloadInfo> downloadedList_show = new ArrayList<DownloadInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cache);
		ListView lv = (ListView)this.findViewById(R.id.list);
		initData();
		adapter = new CachedVideoAdapter(this,downloadedList_show);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}
	
	/**
	 * 获取已下载视频信息
	 */
	private void initData(){
		downloadedList_show.clear();
		
		//通过DownloadManager类的getDownloadedData()接口函数获取已经下载的视频信息
		downloadManager = DownloadManager.getInstance();
		Iterator iter = downloadManager.getDownloadedData().entrySet().iterator();
		
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			//视频信息实体类用DownloadInfo表示
			DownloadInfo info = (DownloadInfo) entry.getValue();
			downloadedList_show.add(info);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//获取点击item表示视频信息
		DownloadInfo info = downloadedList_show.get(arg2);
		
		//跳转到播放界面进行播放，用户可以修改为自己的播放界面
		Intent intent = new Intent(Activity_Cached.this, PlayerActivity.class);
		
		//点击缓存视频播放时需要传递给播放界面的两个参数
		intent.putExtra("isFromLocal", true);		
		intent.putExtra("video_id", info.videoid);
		
		startActivity(intent);
	}
	

}
