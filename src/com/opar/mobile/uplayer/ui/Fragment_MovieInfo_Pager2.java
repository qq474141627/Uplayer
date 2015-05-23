package com.opar.mobile.uplayer.ui;


import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.ui.adapter.PlayButtonsAdapter;
import com.opar.mobile.uplayer.view.MyGridView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_MovieInfo_Pager2 extends SherlockFragment implements OnItemClickListener{
	   private View view;
	   private MyGridView grid_num;
	   private PlayButtonsAdapter adapterNum;
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
		}
		
		// 主线程中新建一个handler
		private	Handler  handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
	        	switch (msg.what) {}
	        }
	    };
					    
		@Override
	    public void setUserVisibleHint(boolean isVisibleToUser) {
	        super.setUserVisibleHint(isVisibleToUser);
	        if (isVisibleToUser) {
	            //相当于Fragment的onResume
	        } else {
	            //相当于Fragment的onPause
	        }
	    }
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			
		}
}
