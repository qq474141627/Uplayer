package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;

import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.ui.adapter.ShowAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_ShowById_AsyncTask;
import com.youku.login.widget.YoukuLoading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment_MovieInfo_Pager5 extends FragmentBase implements OnItemClickListener{
    private View view;
    private ListView listView; //展示数据的listview
    private ShowAdpter adapter;  //绑定数据的适配器
	private String vid;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.listview, null);
			listView=(ListView)view.findViewById(R.id.listView);
			adapter=new ShowAdpter(getActivity());
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Intent intent=new Intent(getActivity(), Activity_ShowInfo.class);
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

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser && !TextUtils.isEmpty(vid)) {
	    	if(adapter.getCount() == 0){
	    		YoukuLoading.show(getActivity());
	    		new Get_ShowById_AsyncTask(handler, vid).execute();
	    	}
	    } else {
	    }
	}
	
	@Override
	public void onLoad(String obj) {
		vid = obj;
	}
	
}
