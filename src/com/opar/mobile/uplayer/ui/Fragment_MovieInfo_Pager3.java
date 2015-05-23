package com.opar.mobile.uplayer.ui;

import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.ShowBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_MovieInfo_Pager3 extends SherlockFragment {
   private View view;
   private TextView moive_desc;
   private boolean hasDate;
   
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
    public void onResume() {
        super.onResume();
        if(!hasDate && isAdded()){
        	 ShowBean bean = ((Activity_ShowInfo)getActivity()).getoBean();
     		if(bean != null){
     			hasDate = true;
     			moive_desc.setText(bean.getDescription());
         	}
        }
    }

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		activity = (Activity_ShowInfo) getActivity();
	}
	
}
