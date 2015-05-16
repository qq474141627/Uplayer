package com.opar.mobile.aplayer.ui.adapter;


import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {
	private List<SherlockFragment> list;
	public MainFragmentAdapter(FragmentManager fm,List<SherlockFragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public SherlockFragment getItem(int id) {
		if(list == null) return null;
		return list.get(id);
	}

	@Override
	public int getCount() {
		if(list == null)return 0;
		return list.size();
	}
	
	// 初始化每个页卡选项  
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        return super.instantiateItem(container, position);  
    }  
    
	@Override  
    public void destroyItem(ViewGroup container, int position, Object object) {  
        //super.destroyItem(container, position, object);
        //container.removeView(getItem(position).getView()); 
    }  
	
}
