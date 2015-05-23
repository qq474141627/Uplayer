package com.opar.mobile.uplayer.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.UserBean;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.xml.XmlUtil;
import com.youku.login.service.ILogin.ICallBack;
import com.youku.login.service.LoginException;
import com.youku.login.sns.AuthorizationLoginActivity;
import com.youku.login.sns.LoginBySinaWeibo;
import com.youku.login.sns.util.ConfigUtil;
import com.youku.login.util.Logger;
import com.youku.login.util.Youku;
import com.youku.player.ApiManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Fragment_Mine extends SherlockFragment	implements OnItemClickListener,OnClickListener,ICallBack{
	private View view;
	private int[] icon_my = new int[]{
			R.drawable.icon_mine_collect,R.drawable.icon_mine_download,R.drawable.icon_mine_dynamic,R.drawable.icon_mine_record,
			R.drawable.icon_mine_card,R.drawable.icon_mine_msg,R.drawable.icon_mine_friend,
			R.drawable.icon_mine_subscribe
		};
	private String[] title_my = null;
	private GridView grid;
    private SimpleAdapter adapter;
    private TextView mine_name;
    private ImageView mine_gender,mine_login;
    private UserBean user = new UserBean();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_my = getResources().getStringArray(R.array.mine);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.fragment_mine, null,false);
			//配置适配器
        	grid = (GridView) view.findViewById(R.id.mine_grid);
	        String [] from ={"image","text"};
	        int [] to = {R.id.item_mine_grid_img,R.id.item_mine_grid_text};
	        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.item_mine_grid, from, to);
	        grid.setAdapter(adapter);
	        grid.setOnItemClickListener(this);
	        
	        mine_login = (ImageView) view.findViewById(R.id.mine_login);
	        mine_login.setOnClickListener(this);
	        mine_name = (TextView) view.findViewById(R.id.mine_name);
	        mine_gender = (ImageView) view.findViewById(R.id.mine_gender);
	        
	        if(ApiManager.getLoginUser() != null){
	        	user.setName(UplayerConfig.getUserName());
	        	new MyThread().start();
	        }
		}
		return view;
	}
	
	public List<Map<String, Object>> getData(){  
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<icon_my.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon_my[i]);
            map.put("text", title_my[i]);
            list.add(map);
        }
        return list;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();
	}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        	((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.main_tab_mine);
        }
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		//0收藏 1正在下载 2下载完成 3播放记录 4本地视频 5消息 6好友7我的订阅
		if(arg2 == 0){
			startActivity(new Intent(getActivity(),Activity_Show_Save.class));
		}else if(arg2 == 1){
			startActivity(new Intent(getActivity(),Activity_Caching.class));
		}else if(arg2 == 2){
			startActivity(new Intent(getActivity(),Activity_Cached.class));
		}
		
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mine_login:
			Logger.d("--onClick--mine_login");
			ApiManager.doLogin(getActivity());
			break;
		default:
			break;
		}
		
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			XmlUtil.getUserInfoByName(user);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mine_name.setText(user.getName());
			user.setHasData(true);
			if(!TextUtils.isEmpty(user.getGender())){
				if(user.getGender().equals("m")){
					mine_gender.setImageResource(R.drawable.icon_myinfo_gender_m);
				}else if(user.getGender().equals("f")){
					mine_gender.setImageResource(R.drawable.icon_myinfo_gender_f);
				}
			}
			ImageLoader.getInstance().displayImage(user.getAvatar_large(),mine_login);
		};
	};
	
	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		user.setName(ApiManager.getLoginUser());
		//ImageLoader.getInstance().displayImage(Youku.getPreference(YoukuConfig.userIcon),mine_login);
    	new MyThread().start();
	}
	@Override
	public void onFailed(LoginException e) {
		// TODO Auto-generated method stub
	}
	
}
