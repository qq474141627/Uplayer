package com.opar.mobile.aplayer.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.uplayer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class Show_Number_Adapter extends BaseAdapter{
	private Context mContext;
	private List<VideoBean> list;
	ViewHolder holder;
	public Show_Number_Adapter(Context context){
		mContext = context;
	}
	//@Override
	public int getCount() {
		if(list==null)return 0;
		return list.size();
	}
	public void addData(List<VideoBean> arraylist){
		if(arraylist == null )
			return;
		if(list == null){
			list = new ArrayList<VideoBean>();
		}
		list.addAll(arraylist);
		notifyDataSetChanged();
	}
	public void clear(){
		if(list!=null){
			list.clear();
			notifyDataSetChanged();
		}
	}
	//@Override
	public VideoBean getItem(int position) {
		return list.get(position);
	}
	//@Override
	public long getItemId(int position) {
		return position;
	}
	//@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView != null){
			holder=(ViewHolder)convertView.getTag();
		}else{
			holder=new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate( R.layout.item_search_key, null);
			holder.text=(TextView) convertView.findViewById(R.id.text); 
			 convertView.setTag(holder);
		}
		if(list==null||list.size()==0||list.size()<=position)return convertView;
		holder.text.setText(list.get(position).getName());
		return convertView;
	}
	 class ViewHolder{  
            TextView text;  
        }  
}