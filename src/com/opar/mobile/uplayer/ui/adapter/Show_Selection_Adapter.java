package com.opar.mobile.uplayer.ui.adapter;

import java.util.List;

import com.opar.mobile.uplayer.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 public class Show_Selection_Adapter extends BaseAdapter{
    private Context mContext;
    private List<String> list;
    private int positinID;
    
    public int getPositinID() {
		return positinID;
	}
	public void setPositinID(int positinID) {
		this.positinID = positinID;
		notifyDataSetChanged();
	}
	public Show_Selection_Adapter(Context context,List<String> list) {
        mContext = context;
        this.list = list;
    }
    public int getCount() {
    	if(list == null)return 0;
        return list.size();
    }

    public String getItem(int i) {
    	if(list == null)return null;
        return list.get(i);
    }

    public long getItemId(int i) {
        return i;
    }
	
	public List<String> getList() {
		return list;
	}
	
	public View getView(int position, View convertView, ViewGroup vg) {
        // TODO Auto-generated method stub
		 ViewHolder holder;  

        if(convertView!=null){  
            holder = (ViewHolder)convertView.getTag();  
        }else{  
        	holder = new ViewHolder();
        	convertView=LayoutInflater.from(mContext).inflate(R.layout.item_selection_grid, null);
        	holder.title = (TextView) convertView.findViewById(R.id.text);  
        	convertView.setTag(holder);  
        }  
        if (list == null || position >= list.size() || position < 0) return convertView;
        holder.title.setText(list.get(position));
        if(position == getPositinID()){
    		holder.title.setTextColor(Color.WHITE);
            holder.title.setBackgroundResource(R.drawable.btn_selection_search_p);
    	}else{
    		holder.title.setTextColor(mContext.getResources().getColor(R.color.listview_text));
            holder.title.setBackgroundResource(R.drawable.btn_selection_search);
         }
        return convertView;
    }
    class ViewHolder{  
        TextView title;  
    }  
}