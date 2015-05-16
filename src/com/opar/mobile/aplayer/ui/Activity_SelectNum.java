package com.opar.mobile.aplayer.ui;




import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_VideoById_AsyncTask;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.ui.adapter.MovieinfoAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.youku.login.widget.YoukuLoading;

public class Activity_SelectNum extends ActivityBase implements OnItemClickListener,OnScrollListener{
	private ListView listView; //展示数据的listview
	private MovieinfoAdpter adapter;  //绑定数据的适配器
	private ShowBean oBean;
	private int page=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  oBean=(ShowBean) getIntent().getSerializableExtra("movie");
	  if(oBean.getEpisode_count() > 1){
		  setTitle(oBean.getName()+"(共"+oBean.getEpisode_count()+"个视频)");
	  }else{
		  setTitle(oBean.getName());
	  }
	  adapter = new MovieinfoAdpter( this);
	  setContentView(R.layout.activity_selectnum);
		listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		if(oBean != null){
			new Get_VideoById_AsyncTask(handler,oBean.getId(),page+1).execute();
			YoukuLoading.show(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		VideoBean bean = adapter.getItem(arg2);
		Activity_VideoPlayer.startActivity(Activity_SelectNum.this, 
				bean.getId(), 
				bean.getName(),
				bean.getView_count(),
				bean.getUp_count(),
				true);
	}
    private int firstVisibleItem,visibleItemCount,totalItemCount;
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE && firstVisibleItem > 0){
			if(firstVisibleItem + visibleItemCount == totalItemCount && !YoukuLoading.isShowing()){
				new Get_VideoById_AsyncTask(handler,oBean.getId(),page+1).execute();
				YoukuLoading.show(this);
			}
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
        		page++;
				adapter.addData((ArrayList<VideoBean>)msg.obj);
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
        };
	};
	
}


