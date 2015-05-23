package com.opar.mobile.uplayer.ui;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ActivityBase extends SherlockFragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_movie);
	}
	
	public void setTitle(String title){
		getSupportActionBar().setTitle(title);
	}
	
	public void setDisplayHomeAsUpEnabled(boolean enable){
		getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
	}
	
	public void setDisplayUseLogoEnabled(boolean enable){
		getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	   finish();
    	         break;
               }
         return super.onOptionsItemSelected(item);
     }
	
}
