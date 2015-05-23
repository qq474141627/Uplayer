package com.opar.mobile.uplayer.ui;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_ShowByIds_AsyncTask;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.dao.DBHelperDao;
import com.opar.mobile.uplayer.ui.adapter.ShowAdpter;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.xml.XmlUtil;
import com.youku.login.widget.YoukuLoading;

public class Activity_Show_Save extends ActivityBase implements OnItemClickListener,OnScrollListener {
	private ListView listView; //展示数据的listview
    private ShowAdpter adapter;  //绑定数据的适配器
    private int page = 1;
    private int maxPage = 1;
    private List<String> ids;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		ids = DBHelperDao.getDBHelperDaoInstace().getSaveKeys();
		String title = getString(R.string.mine_save);
		if(ids!=null && ids.size()>0){
			title+="(共"+ids.size()+"部)";
		}
		setTitle(title);
		listView=(ListView)findViewById(R.id.listView);
		adapter=new ShowAdpter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		if(ids!=null && ids.size()>0){
			maxPage = (ids.size()-1)/XmlUtil.showNum+1;
			getDatas();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Intent intent=new Intent(Activity_Show_Save.this, Activity_ShowInfo.class);
		intent.putExtra("name", adapter.getItem(arg2));
		startActivity(intent);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
        		page ++;
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
        };
	};
	
	private void getDatas(){
		if(!YoukuLoading.isShowing()){
			if(page <= maxPage){
				List<String> list = ids.subList((page-1)*XmlUtil.showNum, ids.size()>page*XmlUtil.showNum?page*XmlUtil.showNum:ids.size());
				new Get_ShowByIds_AsyncTask(handler,list).execute();
				YoukuLoading.show(this);
			}
		}
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
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		// TODO Auto-generated method stub
		if(scrollState == SCROLL_STATE_IDLE && firstVisibleItem > 0){
			if(firstVisibleItem + visibleItemCount == totalItemCount && !YoukuLoading.isShowing()){
				getDatas();
			}
		}
	}

	
}
