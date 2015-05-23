package com.opar.mobile.uplayer.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.MovieSms;
import com.opar.mobile.uplayer.beans.UserBean;
import com.opar.mobile.uplayer.beans.VideoBean;
import com.opar.mobile.uplayer.util.StringUtils;
import com.youku.login.util.Logger;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class MovieSmsAdapter extends BaseAdapter{
	private List<MovieSms> beans = new ArrayList<MovieSms>();
	private LayoutInflater inflater;
	private ViewHolder holder;  
	private Context mContext;
	public MovieSmsAdapter(Context context){
		mContext = context;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		if(beans==null)return 0;
		return beans.size();
	}
	@Override
	public MovieSms getItem(int position) {
		
		return beans.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	public void addData(ArrayList<MovieSms> list) {
		if(list != null){
			beans.addAll(list);
			notifyDataSetChanged();
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView != null){
			holder=(ViewHolder)convertView.getTag();
		}else{
			holder=new ViewHolder();
			convertView = inflater.inflate( R.layout.item_movie_sms, null);
			holder.userName=(TextView) convertView.findViewById(R.id.userName); 
			holder.time=(TextView) convertView.findViewById(R.id.time); 
			holder.content=(TextView) convertView.findViewById(R.id.content); 
			holder.contentOther=(TextView) convertView.findViewById(R.id.contentOther); 
			holder.userIcon=(ImageView) convertView.findViewById(R.id.userIcon); 
			holder.userSex=(ImageView) convertView.findViewById(R.id.userSex); 
			convertView.setTag(holder);
		}
		if(beans == null || beans.size() == 0 ) return convertView;
		MovieSms movieSms = beans.get(position);
		holder.time.setText(StringUtils.getTimeDiff(movieSms.getTime()));
		holder.userName.setText(movieSms.getUserName());
		try {
			splitString(movieSms.getContent());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(movieSms.getUser() != null){
			if(!TextUtils.isEmpty(movieSms.getUser().getGender())){
				if(movieSms.getUser().getGender().equals("m")){
					holder.userIcon.setImageResource(R.drawable.icon_myinfo_gender_m);
				}else if(movieSms.getUser().getGender().equals("f")){
					holder.userIcon.setImageResource(R.drawable.icon_myinfo_gender_f);
				}else{
					holder.userIcon.setImageDrawable(null);
				}
			}
			ImageLoader.getInstance().displayImage(movieSms.getUser().getAvatar_large(),holder.userIcon);
		}
		return convertView;
	}
	
	private void splitString(String string){
		//转换字符串
		String[] split = string.split("//@");
		
		SpannableStringBuilder builder = new SpannableStringBuilder();
		for(int i = split.length - 1;i > 0;i--){
			SpannableString ss = new SpannableString(split[i]);  
			int index = split[i].indexOf(":");
			if(index > 0){
				ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.greentext2)), 0, index,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				if(i == 1){
					builder.append(ss);
				}else{
					builder.append(ss+"\n");
				}
			}
		}
		if(TextUtils.isEmpty(builder)){
			holder.contentOther.setVisibility(8);
		}else{
			holder.contentOther.setText(builder);
			holder.contentOther.setVisibility(0);
		}
		if(TextUtils.isEmpty(split[0])){
			holder.content.setVisibility(8);
		}else{
			holder.content.setText(split[0]);
			holder.content.setVisibility(0);
		}
	}
	
	 class ViewHolder{  
            TextView userName;  
            TextView content;  
            TextView contentOther;  
            TextView time; 
            ImageView userIcon;
            ImageView userSex;
        }  
}