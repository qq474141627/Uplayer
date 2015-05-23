package com.opar.mobile.uplayer.ui;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.asyc.Get_VideoComent_AsyncTask;
import com.opar.mobile.uplayer.beans.MovieSms;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.beans.UserBean;
import com.opar.mobile.uplayer.ui.adapter.MovieSmsAdapter;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.xml.XmlUtil;
import com.youku.login.widget.YoukuLoading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class Fragment_MovieInfo_Pager4 extends SherlockFragment implements OnScrollListener{
	private View view;
	private ListView listView;
	private MovieSmsAdapter adapter;
	private int page ;//默认为第一页数据
	private ArrayList<MovieSms> list;//临时获取的数组
	private String vid;
	private boolean hasDate;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.page_comment, null);
			listView=(ListView)view.findViewById(R.id.listView);
			adapter=new MovieSmsAdapter(getActivity());
			listView.setAdapter(adapter);
			listView.setOnScrollListener(this);
			
			view.findViewById(R.id.text_comment).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
		}
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser && isAdded() && !hasDate) {
	    	if(TextUtils.isEmpty(vid)){
	    		ShowBean bean = ((Activity_ShowInfo)getActivity()).getoBean();
	    		if(bean != null){
	    			vid = XmlUtil.getVid(bean.getLink());
	    		}
	    	}
	    	if(!TextUtils.isEmpty(vid)){
	    		YoukuLoading.show(getActivity());
	    		new Get_VideoComent_AsyncTask(handler, vid,page+1).execute();
	    	}
	    } else {
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
				new Get_VideoComent_AsyncTask(handler, vid,page+1).execute();
				YoukuLoading.show(getActivity());
			}
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
        	switch (msg.what) {
        	case UplayerConfig.EXEC_NORMOL:
        		hasDate = true;
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

}
