package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.beans.ShowType;
import com.opar.mobile.aplayer.ui.adapter.PageAdapter;
import com.opar.mobile.aplayer.ui.adapter.Show_Selection_Adapter;
import com.opar.mobile.aplayer.ui.adapter.ShowAdpter;
import com.opar.mobile.aplayer.util.StringUtils;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_ShowByCategory_AsyncTask;
import com.opar.mobile.uplayer.view.FlowLayout;
import com.opar.mobile.uplayer.view.MyGridView;
import com.opar.mobile.uplayer.view.PullToRefreshView;
import com.opar.mobile.uplayer.view.PullToRefreshView.OnHeaderRefreshListener;
import com.youku.login.util.Logger;
import com.youku.login.widget.YoukuLoading;

public class Activity_Show extends ActivityBase implements OnItemClickListener 
,OnScrollListener,OnCheckedChangeListener,OnPageChangeListener,OnHeaderRefreshListener{
	private int[] location; //保存频道button的位置
	private int position;
	private int width;
	private HorizontalScrollView scrollView; 
	private ScrollView layout_selection;
	private List<ShowAdpter> adapterList = new ArrayList<ShowAdpter>();
	private ViewPager vPager;
	private Animation downTranslateAnimation,upTranslateAnimation;
	private RadioGroup radioGroup;
	private ShowType showType;
	private List<Parameter> parameterList = new ArrayList<Parameter>();
	private TextView selectInfo,select;
	private String name;
	private Show_Selection_Adapter adapter1,adapter2,adapter3,adapter4;
	private LinearLayout ll_select;
	//0 电影 1电视剧 2动漫 3综艺
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		name = getIntent().getStringExtra("name");
        setTitle(name);
		downTranslateAnimation = AnimationUtils.loadAnimation(this, R.anim.my_translate_down);
        upTranslateAnimation = AnimationUtils.loadAnimation(this, R.anim.my_translate_up);
        layout_selection = (ScrollView) findViewById(R.id.layout_selection);
        ll_select = (LinearLayout) findViewById(R.id.ll_select);
		vPager = (ViewPager) findViewById(R.id.vPager);
		initRadioGroup();
		initViewPager();
		initSelection();
		getDatas(false);
	}
	
	private void initSelection(){
		selectInfo = (TextView)findViewById(R.id.selectInfo);
		select = (TextView) findViewById(R.id.select);
		select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSelection();
			}
		});
		final Parameter parameter = parameterList.get(0);
		
		MyGridView MyGridView1 = (MyGridView) findViewById(R.id.MyGridView1);
		adapter1 = new Show_Selection_Adapter(this, showType.getOrderbyList());
		MyGridView1.setAdapter(adapter1);
		MyGridView1.setOnItemClickListener(this);
		
		MyGridView MyGridView2 = (MyGridView) findViewById(R.id.MyGridView2);
		adapter2 = new Show_Selection_Adapter(this, showType.getgenreList());
		MyGridView2.setAdapter(adapter2);
		MyGridView2.setOnItemClickListener(this);
		
		MyGridView MyGridView3 = (MyGridView) findViewById(R.id.MyGridView3);
		adapter3 = new Show_Selection_Adapter(this, showType.getAreaList());
		MyGridView3.setAdapter(adapter3);
		MyGridView3.setOnItemClickListener(this);
		
		MyGridView MyGridView4 = (MyGridView) findViewById(R.id.MyGridView4);
		adapter4 = new Show_Selection_Adapter(this, showType.getYearList());
		MyGridView4.setAdapter(adapter4);
		MyGridView4.setOnItemClickListener(this);
		
		Button ok=(Button)findViewById(R.id.ok);
		Button cancle=(Button)findViewById(R.id.cancle);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String orderby = adapter1.getItem(adapter1.getPositinID());
				if(!TextUtils.isEmpty(orderby)){
					parameter.setOrderby(showType.getOrderbyKey(orderby));
				}
				String genre = adapter2.getItem(adapter2.getPositinID());
				if(!TextUtils.isEmpty(genre)){
					parameter.setGenre(genre.equals("全部")?"":genre);
				}
				String area = adapter3.getItem(adapter3.getPositinID());
				if(!TextUtils.isEmpty(area)){
					parameter.setArea(area.equals("全部")?"":area);
				}
				String year = adapter4.getItem(adapter4.getPositinID());
				if(!TextUtils.isEmpty(year)){
					parameter.setRelease_year(year.equals("全部")?"":year);
				}
				
				ArrayList<String> join = new ArrayList<String>(); 
				if(!TextUtils.isEmpty(orderby) && !orderby.equals("全部")){
					join.add(orderby);
				}
				if(!TextUtils.isEmpty(genre) && !genre.equals("全部")){
					join.add(genre);
				}
				if(!TextUtils.isEmpty(area) && !area.equals("全部")){
					join.add(area);
				}
				if(!TextUtils.isEmpty(year) && !year.equals("全部")){
					join.add(year);
				}
				String selection = StringUtils.join(join, " | ");
				if(!TextUtils.isEmpty(selection)){
					selectInfo.setText(selection);
				}else{
					selectInfo.setText("全部");
				}
				
				parameter.setPage(0);
				//layout_selection.setVisibility(8);
				getSelection();
				adapterList.get(0).clear();
				getDatas(false);
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getSelection();
			}
		});
	}
	
	private void initRadioGroup(){
		scrollView = (HorizontalScrollView)findViewById(R.id.hscroll);
		location = new int[2];
		width = getWindowManager().getDefaultDisplay().getWidth();
		//动态添加view
		int id = getIntent().getIntExtra("id", 0);
		showType = new ShowType(id);
		String[] names = null;
		switch (id) {
		case 0:
			names = getResources().getStringArray(R.array.dianying);
			break;
		case 1:
			names = getResources().getStringArray(R.array.dianshiju);
			break;
		case 2:
			names = getResources().getStringArray(R.array.zongyi);
			break;
		case 3:
			names = getResources().getStringArray(R.array.dongman);
			break;
		default:
			break;
		}
		radioGroup = (RadioGroup) findViewById(R.id.bar_rg);
		for(int i = 0;i < names.length;i++){
			RadioButton button = (RadioButton) getLayoutInflater().inflate(R.layout.layout_radiobutton, null);
			button.setText(names[i]);
			radioGroup.addView(button);
		}
		RadioButton button = (RadioButton) radioGroup.getChildAt(0);
		button.setChecked(true);
		radioGroup.setOnCheckedChangeListener(this);
	}
	
	private void initViewPager(){
    	List<View> ViewList = new ArrayList<View>();
    	for(int i = 0;i<radioGroup.getChildCount();i++){
    		View mView = LayoutInflater.from(this).inflate(R.layout.listview_pull, null);
    		
    		ListView listview=(ListView) mView.findViewById(R.id.listView);
    		ShowAdpter adapter = new ShowAdpter(this);
    		listview.setAdapter(adapter);
    		listview.setOnScrollListener(this);
    		listview.setOnItemClickListener(this);
    		ViewList.add(mView);
    		//生成数组
    		adapterList.add(adapter);
    		parameterList.add(new Parameter(name));
    		
    		PullToRefreshView pull_refresh_view = (PullToRefreshView) mView.findViewById(R.id.pull_refresh_view);
    		pull_refresh_view.setOnHeaderRefreshListener(this);
    		pull_refresh_view.setFooterRefresh(false);
    	}
    	vPager.setAdapter(new PageAdapter(ViewList));
    	vPager.setOnPageChangeListener(this);
    	//默认展示第二页
    	vPager.setCurrentItem(1);
    }
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if(arg0.getId() == R.id.MyGridView1){
			adapter1.setPositinID(arg2);
		}else if(arg0.getId() == R.id.MyGridView2){
			adapter2.setPositinID(arg2);
		}else if(arg0.getId() == R.id.MyGridView3){
			adapter3.setPositinID(arg2);
		}else if(arg0.getId() == R.id.MyGridView4){
			adapter4.setPositinID(arg2);
		}else{
			Intent intent=new Intent(this, Activity_ShowInfo.class);
			intent.putExtra("name", adapterList.get(position).getItem(arg2));
			startActivity(intent);
		}
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
				getDatas(true);
			}
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
				adapterList.get(position).addData((ArrayList<ShowBean>)msg.obj);
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
	public void onCheckedChanged(RadioGroup arg0, int arg1) {

		Logger.d("onCheckedChanged =="+arg1);
		//点击相同按钮，不做处理
		if(arg0.getChildAt(position).getId() == arg1){
			return;
		}
		
		for(int i=0;i<arg0.getChildCount();i++){
			if(arg1 == arg0.getChildAt(i).getId()){
				position = i;
				vPager.setCurrentItem(i,false);
			}
		}
		
		Parameter parameter = parameterList.get(position);
		parameter.clearData();
		switch (getIntent().getIntExtra("id", 0)) {
		case 0://电影
			switch (position) {
			case 2:
				parameter.setOrderby(5);
				break;
			case 3:
				parameter.setOrderby(1);
				break;
			case 4:
				parameter.setGenre("爱情");
				break;
			case 5:
				parameter.setGenre("喜剧");
				break;
			case 6:
				parameter.setGenre("动作");
				break;
			case 7:
				parameter.setOrderby(3);
				break;
			case 8:
				parameter.setGenre("恐怖");
				break;
			case 9:
				parameter.setGenre("剧情");
				break;
			case 10:
				parameter.setGenre("科幻");
				break;
			case 11:
				parameter.setGenre("战争");
				break;
			case 12:
				parameter.setGenre("悬疑");
				break;
			case 13:
				parameter.setGenre("动画");
				break;
			case 14:
				parameter.setGenre("犯罪");
				break;
			case 15:
				parameter.setGenre("惊悚");
				break;
			case 16:
				parameter.setGenre("纪录片");
				break;
			default:
				break;
			}
			break;
			
		case 1://电视剧
			switch (position) {
			case 2:
				parameter.setOrderby(5);
				break;
			case 3:
				parameter.setOrderby(2);
				break;
			case 4:
				parameter.setArea("韩国");
				break;
			case 5:
				parameter.setArea("大陆");
				break;
			case 6:
				parameter.setArea("美国");
				break;
			case 7:
				parameter.setArea("香港");
				break;
			case 8:
				parameter.setArea("日本");
				break;
			case 9:
				parameter.setArea("台湾");
				break;
			case 10:
				parameter.setArea("英国");
				break;
			case 11:
				parameter.setOrderby(3);
				break;
			case 12:
				parameter.setArea("泰国");
				break;
			default:
				break;
			}
			break;
		case 2://综艺
			switch (position) {
			case 2:
				parameter.setOrderby(2);
				break;
			case 3:
				parameter.setGenre("娱乐");
				break;
			case 4:
				parameter.setGenre("搞笑");
				break;
			case 5:
				parameter.setGenre("真人秀");
				break;
			case 6:
				parameter.setGenre("选秀");
				break;
			case 7:
				parameter.setGenre("音乐");
				break;
			case 8:
				parameter.setGenre("脱口秀");
				break;
			case 9:
				parameter.setGenre("生活");
				break;
			case 10:
				parameter.setGenre("美食");
				break;
			case 11:
				parameter.setGenre("时尚");
				break;
			case 12:
				parameter.setGenre("益智");
				break;
			case 13:
				parameter.setGenre("访谈");
				break;
			case 14:
				parameter.setGenre("少儿");
				break;
			default:
				break;
			}
			break;
		case 3://动漫
			switch (position) {
			case 2:
				parameter.setOrderby(2);
				break;
			case 3:
				parameter.setGenre("剧情");
				break;
			case 4:
				parameter.setGenre("冒险");
				break;
			case 5:
				parameter.setGenre("科幻");
				break;
			case 6:
				parameter.setGenre("搞笑");
				break;
			case 7:
				parameter.setGenre("美少女");
				break;
			case 8:
				parameter.setGenre("魔法");
				break;
			case 9:
				parameter.setGenre("忍者");
				break;
			case 10:
				parameter.setGenre("神魔");
				break;
			case 11:
				parameter.setGenre("战争");
				break;
			case 12:
				parameter.setGenre("教育");
				break;
			case 13:
				parameter.setGenre("益智");
				break;
			case 14:
				parameter.setGenre("童话");
				break;
			case 15:
				parameter.setGenre("青春");
				break;
			case 16:
				parameter.setGenre("励志");
				break;
			case 17:
				parameter.setGenre("竞技");
				break;
			default:
				break;
			}
			break;	
		default:
			break;
		}
		
		setPostion(radioGroup.getChildAt(position));
		//滚到第一页，无需加载数据
		if(position != 0){
			getDatas(false);
		}
	}

	private void setPostion(View view) {
		view.getLocationInWindow(location);
		int postion = location[0] - width / 2;
		if (postion != 0) {
			postion += 50;
			scrollView.smoothScrollBy(postion, 0);
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	if(position == 0 && layout_selection.getVisibility() == 0){
            		getSelection();
            		return true;
            	}
    	         break;
            case R.id.menu_search:
            	startActivity(new Intent(this, Activity_Search.class).putExtra("category", name));
    	         break;
               }
         return super.onOptionsItemSelected(item);
     }

	@Override
	public void onHeaderRefresh(final PullToRefreshView view) {
		// TODO Auto-generated method stub
		view.postDelayed(new Runnable() {
			//@Override
			public void run() {
				view.onHeaderRefreshComplete("");
			}
		},1500);
		if(adapterList.get(position).getCount()==0){
			getDatas(false);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		RadioButton radio = (RadioButton) radioGroup.getChildAt(arg0);
		radio.setChecked(true);
		position = arg0;
		if(arg0 == 0){
			if(ll_select.getVisibility() == 8){
				ll_select.setVisibility(0);
			}
			if(layout_selection.getVisibility() == 8){
				getSelection();	
			}
		}else{
			if(ll_select.getVisibility() == 0){
				ll_select.setVisibility(8);
			}
			if(layout_selection.getVisibility() == 0){
				getSelection();	
			}
		}
	}
	
	private void getSelection(){
		if(layout_selection.getVisibility()==8){
			layout_selection.setVisibility(0);
			layout_selection.startAnimation(downTranslateAnimation);
		}else{
			layout_selection.startAnimation(upTranslateAnimation);
			layout_selection.setVisibility(8);
		}
	}
	
	private void getDatas(boolean next){
		if(!next && adapterList.get(position).getCount() != 0){
			adapterList.get(position).notifyDataSetChanged();
			return;
		}
		if(!YoukuLoading.isShowing()){
			if(adapterList.get(position).getCount()%XmlUtil.showNum!=0){
				UplayerConfig.showTips(R.string.NO_MORE_DATE);
				return;
			}
			Parameter parameter = parameterList.get(position);
			parameter.setPage(parameter.getPage()+1);
			Logger.d("类型 = "+parameter.getGenre());
			new Get_ShowByCategory_AsyncTask(handler,parameter).execute();
			YoukuLoading.show(this);
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
        	if(position == 0 && layout_selection.getVisibility() == 0){
        		getSelection();
        		return true;
        	}
        }
        return super.onKeyDown(keyCode, event);
    }
	
}
