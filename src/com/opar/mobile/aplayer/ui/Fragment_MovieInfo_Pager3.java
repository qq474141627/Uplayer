package com.opar.mobile.aplayer.ui;

import com.opar.mobile.uplayer.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_MovieInfo_Pager3 extends FragmentBase {
   private View view;
   private TextView moive_desc;
   private boolean hasData;
   
   @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.page_desc, null);
			moive_desc=(TextView) view.findViewById(R.id.moive_desc);
		}
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		activity = (Activity_ShowInfo) getActivity();
	}
	
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        } else {
        }
    }
	
	@Override
	public void onLoad(String obj) {
		if(view != null){
	    	moive_desc.setText(obj);
		}
	}

	    
}
