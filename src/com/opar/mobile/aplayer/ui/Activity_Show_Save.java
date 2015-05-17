package com.opar.mobile.aplayer.ui;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.ui.adapter.ShowAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_ShowByIds_AsyncTask;
import com.opar.mobile.uplayer.dao.DBHelperDao;
import com.youku.login.widget.YoukuLoading;

public class Activity_Show_Save extends ActivityBase implements OnItemClickListener {
	private ListView listView; //展示数据的listview
    private ShowAdpter adapter;  //绑定数据的适配器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		setTitle(getString(R.string.mine_save));
		listView=(ListView)findViewById(R.id.listView);
		adapter=new ShowAdpter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		getDatas();
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
			List<String> ids = DBHelperDao.getDBHelperDaoInstace().getSaveKeys();
			if(ids!=null && ids.size()>0){
				new Get_ShowByIds_AsyncTask(handler,ids).execute();
				YoukuLoading.show(this);
			}
		}
	}

	
}
