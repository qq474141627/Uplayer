package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.beans.VideoParameter;
import com.opar.mobile.aplayer.ui.adapter.VideoAdpter;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_VideoByCategory_AsyncTask;
import com.youku.login.widget.YoukuLoading;

public class Activity_Video extends ActivityBase implements OnItemClickListener ,OnScrollListener,OnCheckedChangeListener{
	private ListView listView; //展示数据的listview
	private VideoAdpter adapter;  //绑定数据的适配器
	private int[] location; //保存频道button的位置
	private int postion;
	private int width;
	private HorizontalScrollView scrollView; 
	private VideoParameter parameter=new VideoParameter();
	private String[] names;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		setTitle(getIntent().getStringExtra("name"));
		parameter.setCategory(getIntent().getStringExtra("name"));
		
		scrollView = (HorizontalScrollView)findViewById(R.id.hscroll);
		location = new int[2];
		width = getWindowManager().getDefaultDisplay().getWidth();
		
		names = getResources().getStringArray(getIntent().getIntExtra("id", R.array.zixun));
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bar_rg);
		for(int i =0;i<names.length;i++){
			RadioButton button = (RadioButton) getLayoutInflater().inflate(R.layout.layout_radiobutton, null);
			button.setText(names[i]);
			radioGroup.addView(button);
		}
		RadioButton button = (RadioButton) radioGroup.getChildAt(0);
		button.setChecked(true);
		radioGroup.setOnCheckedChangeListener(this);
		
		listView = (ListView) findViewById(R.id.listView);
		adapter = new VideoAdpter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		
		new Get_VideoByCategory_AsyncTask(handler,parameter).execute();
		YoukuLoading.show(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		VideoBean bean = adapter.getItem(arg2);
		Activity_VideoPlayer.startActivity(Activity_Video.this, 
				bean.getId(), 
				bean.getName(),
				bean.getView_count(),
				bean.getUp_count());
	}
	
private int firstVisibleItem,visibleItemCount,totalItemCount;
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE && firstVisibleItem > 0){
			if(firstVisibleItem + visibleItemCount == totalItemCount && !YoukuLoading.isShowing()){
				parameter.setPage(parameter.getPage()+1);
				new Get_VideoByCategory_AsyncTask(handler,parameter).execute();
				YoukuLoading.show(this);
			}
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
				adapter.addData((ArrayList<VideoBean>)msg.obj);
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton tempButton = (RadioButton)group.findViewById(checkedId);
		parameter.clearData();
		String text = tempButton.getText().toString();
		if(!TextUtils.isEmpty(text)){
			if(text.endsWith("今日热点")){
			}else if(text.endsWith("最新更新")){
				parameter.setOrderby(1);
			}else if(text.endsWith("本周排行")){
				parameter.setPeriod(1);
			}else{
				parameter.setGenre(text);
			}
			setPostion(tempButton);
		}
	}
	
	private void setPostion(View view) {
		view.getLocationInWindow(location);
		postion = location[0] - width / 2;
		if (postion != 0) {
			postion += 50;
			scrollView.smoothScrollBy(postion, 0);
		}
		adapter.clear();
		YoukuLoading.show(this);
		new Get_VideoByCategory_AsyncTask(handler,parameter).execute();
	}
	
}
