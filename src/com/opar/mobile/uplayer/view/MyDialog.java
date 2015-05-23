package com.opar.mobile.uplayer.view;

import java.util.List;

import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.ui.adapter.SearchKeyAdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MyDialog extends Dialog implements OnItemClickListener{

    private Context context;
    private TextView dialog_title;
    private ListView listView;
    private SearchKeyAdapter adapter;
    
    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public MyDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_search_category);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SearchKeyAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void setTitle(String title){
    	dialog_title.setText(title);
    }
    
    private void setData(List<String> list){
        adapter.research(list);
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}
    
}