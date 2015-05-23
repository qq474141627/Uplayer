package com.opar.mobile.uplayer.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.VideoBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 public class PlayButtonsAdapter extends BaseAdapter{
    private Context mContext;
    ArrayList<VideoBean> list;
    boolean isListview =false;
    int positinID;
    private boolean ordered=true;//默认反序
    public PlayButtonsAdapter(Context context,boolean isListview) {
        mContext = context;
        this.isListview=isListview;
    }
    public int getCount() {
    	if(list == null)return 0;
        return list.size();
    }

    public VideoBean getItem(int i) {
    	if(list == null)return null;
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }
    public void reflash(ArrayList<VideoBean> list){
    	this.list = list;
    	notifyDataSetChanged();
    }
    public int getPositinID() {//排序后的集数
    	if(ordered)
    		return positinID;
		else 
           return getCount()-1-positinID;
	}
	public void setPositinID(int positinID) {//当前观看的集数
		this.positinID=positinID;
		notifyDataSetChanged();
	}
	
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
		notifyDataSetChanged();
	}
	
	public ArrayList<VideoBean> getList() {
		return list;
	}
	public View getView(int position, View convertView, ViewGroup vg) {
        // TODO Auto-generated method stub
		 ViewHolder holder;  

        if(convertView!=null){  
            holder = (ViewHolder)convertView.getTag();  
        }else{  
        	holder=new ViewHolder();
        	if(isListview){
        		convertView = LayoutInflater.from(mContext).inflate(R.layout.item_num_list, null);
        		holder.title = (TextView) convertView.findViewById(R.id.text);  
        	}else{
        		convertView=LayoutInflater.from(mContext).inflate(R.layout.item_num_grid, null);
        		holder.title = (TextView) convertView.findViewById(R.id.text);  
        	}
        	convertView.setTag(holder);  
        }  
        if (list==null|| position >= list.size()||position<0  ) return convertView;
        if(ordered)holder.title.setText(String.valueOf(position+1));//从1-最后
        else holder.title.setText(String.valueOf(list.size()-position));//从最后到1
        if(isListview){
        	 if(position == getPositinID())
                 holder.title.setBackgroundColor(Color.RED);
                 else
                 holder.title.setBackgroundColor(Color.TRANSPARENT);
        }
        else{
        	if(position == getPositinID()){
        		holder.title.setTextColor(Color.RED);
                holder.title.setBackgroundResource(R.drawable.item_num_grid_2);
        	}else{
        		holder.title.setTextColor(Color.BLACK);
                holder.title.setBackgroundResource(R.drawable.item_num_grid_1);
                }
        }
        return convertView;
    }
    class ViewHolder{  
        TextView title;  
    }  
}