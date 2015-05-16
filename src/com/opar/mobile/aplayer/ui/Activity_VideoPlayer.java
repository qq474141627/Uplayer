package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.opar.mobile.aplayer.beans.MovieSms;
import com.opar.mobile.aplayer.beans.UserBean;
import com.opar.mobile.aplayer.ui.adapter.MovieSmsAdapter;
import com.opar.mobile.aplayer.util.StringUtils;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_VideoComent_AsyncTask;
import com.youku.login.widget.YoukuLoading;
import com.youku.player.ApiManager;
import com.youku.player.VideoQuality;
import com.youku.player.base.YoukuBasePlayerActivity;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.OnCreateDownloadListener;

/**
 * 播放器播放界面，需要继承自YoukuBasePlayerActivity基类
 *
 */
public class Activity_VideoPlayer extends YoukuBasePlayerActivity implements OnScrollListener,OnClickListener{
	//播放器控件
	private YoukuPlayerView mYoukuPlayerView;
	
	//需要播放的视频id
	private String vid;
	
	//需要播放的本地视频的id
	private String local_vid;
	
	//标示是否播放的本地视频
	private boolean isFromLocal = false;
	
	//YoukuPlayer实例，进行视频播放控制
	private YoukuPlayer youkuPlayer;
	
	private int page ;//默认为第一页数据
	private ListView listView;
	private MovieSmsAdapter adapter;
	private TextView video_detail_title,video_detail_content,video_detail_up,video_detail_rate;
	private ArrayList<MovieSms> list;//临时获取的数组
	private boolean land;//是否强制横屏
	private String name;
	
	public static void startActivity(Context context,String id,String name,int viewCount,int upCount){
		Intent intent=new Intent(context, Activity_VideoPlayer.class);
		intent.putExtra("id", id);
		intent.putExtra("name", name);
		intent.putExtra("viewCount", viewCount);
		intent.putExtra("upCount", upCount);
		context.startActivity(intent);
	}
	
	public static void startActivity(Context context,String id,String name,int viewCount,int upCount,boolean land){
		Intent intent=new Intent(context, Activity_VideoPlayer.class);
		intent.putExtra("id", id);
		intent.putExtra("name", name);
		intent.putExtra("viewCount", viewCount);
		intent.putExtra("upCount", upCount);
		intent.putExtra("land", land);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_videoplayer);		
		listView=(ListView)findViewById(R.id.listView);
		adapter=new MovieSmsAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);	
		video_detail_title = (TextView) findViewById(R.id.video_detail_title);
		video_detail_content = (TextView) findViewById(R.id.video_detail_content);
		video_detail_up = (TextView) findViewById(R.id.video_detail_up);
		video_detail_rate = (TextView) findViewById(R.id.video_detail_rate);
		video_detail_up.setOnClickListener(this);
		video_detail_rate.setOnClickListener(this);
		//通过上个页面传递过来的Intent获取播放参数
		getIntentData(getIntent());
		
		if(TextUtils.isEmpty(id)){
			vid = "XODQwMTY4NDg0";	//默认视频
		}else{
			vid = id;
		}		
		
		//播放器控件
		mYoukuPlayerView = (YoukuPlayerView) this.findViewById(R.id.full_holder);

		//初始化播放器相关数据
		mYoukuPlayerView.initialize(this);
//		mYoukuPlayerView.initialize(this,pid);			//有pid的用户可以使用此接口配置pid参数
		//if(land){
			new Get_VideoComent_AsyncTask(handler, vid,page+1).execute();
		//}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		//通过Intent获取播放需要的相关参数
		getIntentData(intent);
		
		//进行播放
		//goPlay();
	}

	
	/**
	 * 获取上个页面传递过来的数据
	 */
	private void getIntentData(Intent intent){
		
		if(intent != null){
			//判断是不是本地视频
			isFromLocal = intent.getBooleanExtra("isFromLocal", false);
			
			if(isFromLocal){	//播放本地视频
				local_vid = intent.getStringExtra("video_id");				
			}else{				//在线播放
				id = intent.getStringExtra("id");
				name = intent.getStringExtra("name");
				findViewById(R.id.video_detail_rl).setVisibility(0);
				video_detail_title.setText(name);
				video_detail_content.setText(StringUtils.getViewNum(intent.getIntExtra("viewCount",0))+"次播放");
				video_detail_up.setText(StringUtils.getViewNum(intent.getIntExtra("upCount",0)));
				land = intent.getBooleanExtra("land", false);
			}
		}
		
	}

	@Override
	public void setPadHorizontalLayout() {}

	@Override
	public void onInitializationSuccess(YoukuPlayer player) {
		// TODO Auto-generated method stub
		//初始化成功后需要添加该行代码
		addPlugins();
		//实例化YoukuPlayer实例
		youkuPlayer = player;
		if(land){
			setPlayerFullScreen(true);
		}
		//进行播放
		goPlay();
	}
	
	private void goPlay(){
		if(isFromLocal){	//播放本地视频
			youkuPlayer.playLocalVideo(local_vid);			
		}else{				//播放在线视频
			youkuPlayer.playVideo(vid);
		}
	}

	@Override
	public void onFullscreenListener() {}

	@Override
	public void onSmallscreenListener() {}

	
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
				new Get_VideoComent_AsyncTask(handler, vid,page+1).execute();
				YoukuLoading.show(this);
			}
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
        		page++;
        		ArrayList<UserBean> users = (ArrayList<UserBean>)msg.obj;
        		for(int i = 0;i<list.size();i++){
        			if(users.size() > i){
        				list.get(i).setUser(users.get(i));
        			}
        		}
				adapter.addData(list);
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
			case UplayerConfig.EXEC_NORMOL_COMMENT:
				list = (ArrayList<MovieSms>)msg.obj;
				break;
			default:
				break;
			}
        };
	};


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//video_detail_title,video_detail_content,video_detail_up,video_detail_rate;
		switch (arg0.getId()) {
		case R.id.video_detail_up:
			
			break;
		case R.id.video_detail_rate:
			
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * 更改视频的清晰度
	 * @param quality
	 * 				VideoQuality有四种枚举值：{STANDARD,HIGHT,SUPER,P1080}，分别对应：标清，高清，超清，1080P
	 */
	
	private void change(int p){
		try{
			//默认标清
			VideoQuality quality = VideoQuality.STANDARD;
			switch (p) {
			case 0:
				quality = VideoQuality.STANDARD;
				break;
			case 1:
				quality = VideoQuality.HIGHT;
				break;
			case 2:
				quality = VideoQuality.SUPER;
				break;
			default:
				break;
			}
			int result = ApiManager.getInstance().changeVideoQuality(quality,this);
			if(result == 0) UplayerConfig.showTips("不支持此清晰度");
		}catch(Exception e){
			UplayerConfig.showTips(e.getMessage());
		}
	}
	
	/**
	 * 简单展示下载接口的使用方法，用户可以根据自己的
	 * 通过DownloadManager下载视频
	 */
	private void doDownload(){
		//通过DownloadManager类实现视频下载
		DownloadManager.getInstance().createDownload(vid, name, new OnCreateDownloadListener() {
			@Override
			public void onfinish(boolean isNeedRefresh) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
