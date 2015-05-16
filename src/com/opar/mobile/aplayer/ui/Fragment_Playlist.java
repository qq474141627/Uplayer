package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.uplayer.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_Playlist extends SherlockFragment implements OnItemClickListener{
	private View view;
	private GridView grid;
    private SimpleAdapter adapter;
	private int[] icon = new int[]{
		R.drawable.icon_classify_news,R.drawable.icon_classify_movie,R.drawable.icon_classify_tv,
		R.drawable.icon_classify_zongyi,R.drawable.icon_classify_dongman,R.drawable.icon_classify_gaoxiao,
		R.drawable.icon_classify_jilu,R.drawable.icon_classify_tiyu,R.drawable.icon_classify_yule,
		R.drawable.icon_classify_yinyue,R.drawable.icon_classify_shenghuo,R.drawable.icon_classify_youxi,
		R.drawable.icon_classify_yinyue,R.drawable.icon_classify_shenghuo,R.drawable.icon_classify_youxi,
		R.drawable.icon_classify_yinyue
	};
	private String[] iconName = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconName = getResources().getStringArray(R.array.classify);
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
 
		if(view == null){
			view = inflater.inflate(R.layout.fragment_classify, null,false);
			//配置适配器
        	grid = (GridView) view.findViewById(R.id.classify_grid);
	        String [] from ={"image","text"};
	        int [] to = {R.id.item_classify_grid_img,R.id.item_classify_grid_text};
	        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.item_classify_grid, from, to);
	        grid.setAdapter(adapter);
	        grid.setOnItemClickListener(this);
		}
		return view;
	}
	
	public List<Map<String, Object>> getData(){  
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        	((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.main_tab_catagory);
        }
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(),Activity_Playlist.class);
		intent.putExtra("name", iconName[arg2]);
		startActivity(intent);
	}

}
