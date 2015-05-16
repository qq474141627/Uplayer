package com.opar.mobile.aplayer.ui.adapter;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageFragmentAdapter extends FragmentPagerAdapter{
	private List<SherlockFragment> list;
 	public PageFragmentAdapter(FragmentManager fm,List<SherlockFragment> list) {
		super(fm);
		this.list = list;
	}
	@Override
	public SherlockFragment getItem(int arg0) {
		 return list.get(arg0);
	}
	@Override
	public int getCount() {
		return list.size();
	}
}
