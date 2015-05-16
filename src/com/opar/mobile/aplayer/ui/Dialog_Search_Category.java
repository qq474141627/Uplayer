package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.opar.mobile.aplayer.ui.adapter.SearchKeyAdapter;
import com.opar.mobile.uplayer.R;

public class Dialog_Search_Category extends SherlockFragmentActivity implements OnItemClickListener{
	
    private ListView listView;
    private SearchKeyAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_search_category);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SearchKeyAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        
        List<String> list = new ArrayList<String>();
        list.add("全部");
        list.addAll(Arrays.asList(getResources().getStringArray(R.array.classify_big)));
        adapter.research(list);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("title", adapter.getItem(arg2));
		setResult(0, intent);
		finish();
	}
	

}
