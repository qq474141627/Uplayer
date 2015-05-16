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

import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.ui.adapter.ShowAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Search_Show_AsyncTask;
import com.youku.login.widget.YoukuLoading;

public class Activity_Search_Show extends ActivityBase implements OnItemClickListener ,OnScrollListener{
	private ListView listView; //展示数据的listview
	private ShowAdpter adapter;  //绑定数据的适配器
	private int page ;
	private String key,category;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_show);
		if(getIntent() != null){
			key = getIntent().getStringExtra("key");
			category = getIntent().getStringExtra("category");
		}
		setTitle(key);
		listView = (ListView) findViewById(R.id.listView);
		adapter = new ShowAdpter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		YoukuLoading.show(this);
		new Search_Show_AsyncTask(handler,key,category,page+1).execute();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Intent intent=new Intent(this, Activity_ShowInfo.class);
		intent.putExtra("name", adapter.getItem(arg2));
		startActivity(intent);
	}
	
	private int firstVisibleItem,visibleItemCount,totalItemCount;
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE && firstVisibleItem > 0){
			if(firstVisibleItem + visibleItemCount == totalItemCount && !YoukuLoading.isShowing()){
				new Search_Show_AsyncTask(handler,key,category,page+1).execute();
				YoukuLoading.show(this);
			}
		}
	}
	
	// 主线程中新建一个handler
	Handler  handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
				adapter.addData((ArrayList<ShowBean>)msg.obj);
				break;
			case UplayerConfig.NONETWORK:
				UplayerConfig.showTips(R.string.NONETWORK);
			        break;
			case UplayerConfig.NO_DATA_RETURN:
				UplayerConfig.showTips(R.string.NO_DATA_RETURN);
				break;
			case UplayerConfig.DATA_RETURN_ZERO:
				UplayerConfig.showTips(R.string.NO_SOURCE_FOUND);
				break;
			default:
				break;
			}
        }
     };	
	

}
