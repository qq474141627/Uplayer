package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.PlaylistBean;
import com.opar.mobile.aplayer.ui.adapter.PlaylistAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.opar.mobile.uplayer.R;
import com.youku.login.widget.YoukuLoading;

public class Activity_Playlist extends ActivityBase implements OnItemClickListener ,OnScrollListener{
	private ListView listView; //展示数据的listview
	private PlaylistAdpter adpter;  //绑定数据的适配器
	private ArrayList<PlaylistBean> beans = new ArrayList<PlaylistBean>();   //放置视频对象的集合
	private boolean isResh; //是否获取数据结束，防止listview滚动到最下面，重复获取数据
	private Parameter parameter;
	//0 电影 1电视剧 2动漫 3综艺
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		setTitle(getIntent().getStringExtra("name"));
		parameter=new Parameter(getIntent().getStringExtra("name"));
		findViewById(R.id.hscroll).setVisibility(View.GONE);
		
		listView = (ListView) findViewById(R.id.listView);
		adpter = new PlaylistAdpter(this, beans);
		listView.setAdapter(adpter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		
		new MyThread().start();
		YoukuLoading.show(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Intent intent=new Intent(this, Activity_ShowInfo.class);
		intent.putExtra("name", beans.get(arg2));
		startActivity(intent);
	}
	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		if (arg1 + arg2 == arg3 && !isResh && arg3 > 0) {
			parameter.setPage(parameter.getPage()+1);
			new MyThread().start();
			YoukuLoading.show(this);
		}
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
			if (beans.isEmpty()) {
				UplayerConfig.showTips("获取数据失败");
				return;
			}
			isResh = false;
			adpter.notifyDataSetChanged();
		};
	};

	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			isResh = true;
			beans.addAll(XmlUtil.getPlaylistByCategory(parameter));
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
	
	
}
