package com.opar.mobile.aplayer.ui.adapter;

import java.util.List;

import com.opar.mobile.aplayer.ui.FragmentBase;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageFragmentAdapter extends FragmentPagerAdapter{
	private List<FragmentBase> list;
 	public PageFragmentAdapter(FragmentManager fm,List<FragmentBase> list) {
		super(fm);
		this.list = list;
	}
	@Override
	public FragmentBase getItem(int arg0) {
		 return list.get(arg0);
	}
	@Override
	public int getCount() {
		return list.size();
	}
	
	public void reflash(int arg0,String obj) {
	    getItem(arg0).onLoad(obj);
	}
}
