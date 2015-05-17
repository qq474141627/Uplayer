package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.opar.mobile.aplayer.ui.adapter.SearchKeyAdapter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Search_HotKey_AsyncTask;
import com.opar.mobile.uplayer.asyc.Search_Key_AsyncTask;
import com.opar.mobile.uplayer.dao.DBHelperDao;
import com.opar.mobile.uplayer.view.FlowLayout;
import com.umeng.analytics.MobclickAgent;
import com.youku.login.widget.YoukuLoading;

public class Activity_Search extends SherlockFragmentActivity implements OnClickListener{
	private EditText searchKey;
	private ListView listView;
	private View view;
	private DBHelperDao dao;
	private SearchKeyAdapter adapter;
	private ImageView img_clear;
	private String category ;
	private TextView text_category;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view=getLayoutInflater().inflate( R.layout.activity_search, null );
		setContentView(view);
		dao=DBHelperDao.getDBHelperDaoInstace();
        ImageView img_back = (ImageView) findViewById(R.id.img_back);  
        ImageView img_search = (ImageView) findViewById(R.id.img_search);
        img_clear = (ImageView) findViewById(R.id.img_clear);  
        text_category = (TextView) findViewById(R.id.text_category); 
        img_search.setOnClickListener(this);  
        img_back.setOnClickListener(this); 
        img_clear.setOnClickListener(this);
        text_category.setOnClickListener(this);
        listView=(ListView) findViewById(R.id.listView);  
        adapter=new SearchKeyAdapter(this);
        listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				search(adapter.getItem(arg2));
			}
		});
		if(getIntent() != null){
			category = getIntent().getStringExtra("category");
		}
		if(!TextUtils.isEmpty(category)){
			text_category.setText(category);
		}else{
			text_category.setText("全部");
		}
		initHistroyKey(dao.getSearchKeys(6));
		initEditText();
        new Search_HotKey_AsyncTask(handler).execute(category);
        YoukuLoading.show(this);
	}
	@Override
    protected void onResume() {
            // TODO Auto-generated method stub
            super.onResume();
            MobclickAgent.onResume(this); 
    }
    @Override
    protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            MobclickAgent.onPause(this);
    }  
	private void initEditText(){
		searchKey=(EditText)findViewById(R.id.searchKey);
		searchKey.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchKey.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void afterTextChanged(Editable s) {
				try {
					String key=searchKey.getText().toString();
					if(key.length()<1){
						hideSearchListView(true);
					}else {
						hideSearchListView(false);
						new Search_Key_AsyncTask(handler).execute(key.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        searchKey.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_ENTER||keyCode==KeyEvent.KEYCODE_SEARCH){
					String key=searchKey.getText().toString();
					if(key.length()>0){
						search(key);
					}
				//	hideSearchListView(true);//停止联想
				}
				return false;
			}

		});
	}
	
	private void initHistroyKey(List<String> list){
		 FlowLayout flowlayout = (FlowLayout) findViewById(R.id.flowlayout1);
		 flowlayout.removeAllViews();//清空记录
		 for(final String string:list){
			 View v=LayoutInflater.from(this).inflate(R.layout.item_search_hotkey, null);
			 final Button bt = (Button) v.findViewById(R.id.bt);
			 bt.setText(string);
			 flowlayout.addView(v);
			 bt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					search(bt.getText().toString());
				}
			});
		 }
	        
	    }
	 private void initHotKey(List<String> list){
		 FlowLayout flowlayout = (FlowLayout) findViewById(R.id.flowlayout2);
		 flowlayout.removeAllViews();
		 for(final String string:list){
			 View v=LayoutInflater.from(this).inflate(R.layout.item_search_hotkey, null);
			 final Button bt = (Button) v.findViewById(R.id.bt);
			 bt.setText(string);
			 flowlayout.addView(v);
			 bt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					search(bt.getText().toString());
				}
			});
		 }
	        
	    }
	private void hideSearchListView(boolean hide){
		if(hide){
			adapter.clear();
			listView.setVisibility(8);
		}else{
			listView.setVisibility(0);
		}
	}
	//隐藏软件盘
    protected void hideKeyBoard()
    {
        try
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch( Exception e )
        {
        	e.printStackTrace();
        }
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.img_back){
			finish();
		}else if(v.getId() == R.id.img_search){
			String key=searchKey.getText().toString();
			if(key.length()>0){
				search(key);
			}
		}else if(v.getId() == R.id.img_clear){
			dao.delSearchKeys();
	    	initHistroyKey(dao.getSearchKeys(6));
		}else if(v.getId() == R.id.text_category){
			startActivityForResult(new Intent(Activity_Search.this,Dialog_Search_Category.class), 0);
		}
	}
	private void search( String key ){
    	//把键盘隐藏起来
    	hideKeyBoard();
    	if (0 >= key.length()) return;
    	 dao.insertSearchKey(key);
    	 initHistroyKey(dao.getSearchKeys(6));
    	 Intent intent =new Intent(this,Activity_Search_Show.class);
    	 intent.putExtra("key", key);
    	 intent.putExtra("category", category);
    	 startActivity(intent);
    }
	// 主线程中新建一个handler
	Handler  handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
				adapter.research((ArrayList<String>)msg.obj);
				break;
			case UplayerConfig.NONETWORK:
				YoukuLoading.dismiss();
				UplayerConfig.showTips(R.string.NONETWORK);
			        break;
			case UplayerConfig.NO_DATA_RETURN:
				YoukuLoading.dismiss();
				UplayerConfig.showTips(R.string.NO_DATA_RETURN);
				break;
			case UplayerConfig.DATA_RETURN_ZERO:
				YoukuLoading.dismiss();
				UplayerConfig.showTips(R.string.NO_SOURCE_FOUND);
				break;
			case UplayerConfig.EXEC_NORMOL_HOTKEY://热门搜索词汇获取成功
				YoukuLoading.dismiss();
				initHotKey((ArrayList<String>)msg.obj);
		        break;
			default:
				break;
			}
        }
     };
     
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
     	if (listView.getVisibility() == 0 && keyCode == KeyEvent.KEYCODE_BACK) {
     		hideSearchListView(true);
 			return true;
     	}
     	return super.onKeyDown(keyCode, event);
     }
       
       
     @Override  
     protected void onActivityResult(int requestCode, int resultCode, Intent intent)  
     {  
         if(resultCode == 0 && intent != null)   
         {  
        	 String title = intent.getStringExtra("title");
        	 if(!TextUtils.isEmpty(title)){
        		 text_category.setText(title);
        		 if(title.equals("全部")){
        			 title = "";
        		 }
        		 if(!category.equals(title)){
        			 category = title;
        			 new Search_HotKey_AsyncTask(handler).execute(category);
        		     YoukuLoading.show(this);
        		 }
        	 }
         }  
         super.onActivityResult(requestCode, resultCode, intent);  
     }  

	
      

	
}
