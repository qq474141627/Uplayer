package com.opar.mobile.aplayer.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragment;
import com.opar.mobile.uplayer.R;

public class Fragment_Show extends SherlockFragment implements OnItemClickListener{
	private View view;
	private GridView grid;
    private SimpleAdapter adapter;
	private int[] icon_classify = new int[]{
		R.drawable.icon_classify_1,R.drawable.icon_classify_2,R.drawable.icon_classify_3,
		R.drawable.icon_classify_4,R.drawable.icon_classify_5,R.drawable.icon_classify_6,
		R.drawable.icon_classify_7,R.drawable.icon_classify_8,R.drawable.icon_classify_9,
		R.drawable.icon_classify_10,R.drawable.icon_classify_11,R.drawable.icon_classify_12,
		R.drawable.icon_classify_13,R.drawable.icon_classify_14,R.drawable.icon_classify_15,
		R.drawable.icon_classify_16,0,0
	};
	private int[] array_classify = new int[]{
			R.array.dianying,R.array.dianshiju,R.array.zongyi,
			R.array.dongman,R.array.zixun,R.array.yinyue,
			R.array.yule,R.array.gaoxiao,R.array.shenghuo,
			R.array.youxi,R.array.tiyu,R.array.shishang,
			R.array.lvyou,R.array.qiche,R.array.qinzi,
			R.array.keji
		};
	private String[] title_classify = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_classify = getResources().getStringArray(R.array.classify);
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
	        for(int i=0;i<icon_classify.length;i++){
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("image", icon_classify[i]);
	            if(icon_classify[i] != 0){
	            	map.put("text", title_classify[i]);
	            }else{
	            	map.put("text", "");
	            }
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
	        	((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.main_tab_classify);
	        }
	    }
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(icon_classify[arg2] == 0) return;
			if(arg2 < 4){
				Intent intent = new Intent(getActivity(),Activity_Show.class);
				intent.putExtra("id", arg2);
				intent.putExtra("name", title_classify[arg2]);
				startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(),Activity_Video.class);
				intent.putExtra("id", array_classify[arg2]);
				intent.putExtra("name", title_classify[arg2]);
				startActivity(intent);
			}
		}
	    
}
