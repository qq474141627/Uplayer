package com.opar.mobile.aplayer.ui.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class PageAdapter extends PagerAdapter{
   private List<View> viewList;
	public PageAdapter(List<View> mlist){
		this.viewList=mlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(viewList==null)return 0;
		 return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override  
    public void destroyItem(ViewGroup container, int position,  
            Object object) {  
        container.removeView(viewList.get(position));  

    }  
	
	@Override  
    public int getItemPosition(Object object) {  

        return super.getItemPosition(object);  
    }  
	
	 @Override  
     public Object instantiateItem(ViewGroup container, int position) {  
         container.addView(viewList.get(position));  
         return viewList.get(position);  
     }  
	 
}
