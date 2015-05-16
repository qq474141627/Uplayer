package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;


import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.ui.adapter.PlayButtonsAdapter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.view.MyGridView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_MovieInfo_Pager1 extends SherlockFragment implements OnItemClickListener{
   private View view;
   private MyGridView grid_num;
   private PlayButtonsAdapter adapterNum;
   private ArrayList<VideoBean> beans=new ArrayList<VideoBean>();
   private Activity_ShowInfo activity;
   private boolean isResh ;
   private String id;
   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.page_selectnum, null);
			view.setVisibility(4);
			
			grid_num=(MyGridView) view.findViewById(R.id.grid_num);
			adapterNum=new PlayButtonsAdapter(getActivity(),false);
			grid_num.setAdapter(adapterNum);
			grid_num.setOnItemClickListener(this);
		}
		return view;
	}

	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (Activity_ShowInfo) activity;
	}
	
    class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			isResh = true;
			beans = XmlUtil.getVideoBeans(id, 1);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
				    
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        	if(!isResh && beans.size() == 0 ){
    			new MyThread().start();
    	      }
        } else {
            //相当于Fragment的onPause
        }
    }
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//pView.setVisibility(View.GONE);
			isResh = false;
			if (beans.isEmpty()) {
				UplayerConfig.showTips("获取数据失败");
				return;
			}
			adapterNum.reflash(beans);
		};
	};
	
}
