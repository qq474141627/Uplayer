package com.opar.mobile.uplayer.ui;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_VideoById_AsyncTask;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.beans.VideoBean;
import com.opar.mobile.uplayer.ui.adapter.Show_Number_Adapter;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.youku.login.widget.YoukuLoading;

public class Dialog_Show_Num extends SherlockFragmentActivity implements OnItemClickListener,OnScrollListener{
	
    private ListView listView;
    private Show_Number_Adapter adapter;
    private ShowBean oBean;
	private int page=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_show_number);
		oBean=(ShowBean) getIntent().getSerializableExtra("movie");
        listView = (ListView) findViewById(R.id.listView);
        adapter = new Show_Number_Adapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
		if(oBean != null){
			new Get_VideoById_AsyncTask(handler,oBean.getId(),page+1).execute();
			YoukuLoading.show(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		VideoBean bean = adapter.getItem(arg2);
		Activity_VideoPlayer.startActivity(this, 
				bean.getId(), 
				bean.getName(),
				bean.getView_count(),
				bean.getUp_count());
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
