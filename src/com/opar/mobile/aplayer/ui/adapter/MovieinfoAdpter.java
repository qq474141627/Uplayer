package com.opar.mobile.aplayer.ui.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.util.StringUtils;
public class MovieinfoAdpter extends BaseAdapter {
	private Context context;
	private ArrayList<VideoBean> beans = new ArrayList<VideoBean>();
	public MovieinfoAdpter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if(beans != null){
			return beans.size();
		}
		return 0;
	}

	@Override
	public VideoBean getItem(int arg0) {
		if(beans != null){
			return beans.get(arg0);
		}
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void addData(ArrayList<VideoBean> list) {
		if(list != null){
			beans.addAll(list);
			notifyDataSetChanged();
		}
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		GetView getView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.movieinfo_list_item, null);
			getView = new GetView();
			getView.imageView = (ImageView) view.findViewById(R.id.hot_mv_img);
			getView.nameView = (TextView) view.findViewById(R.id.hot_mv_name);
			getView.textView = (TextView) view.findViewById(R.id.hot_mv_duration);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.imageView.setImageResource(R.drawable.logo);
		}
		getView.nameView.setText(beans.get(arg0).getName());
		try {
			String time=StringUtils.generateTime(beans.get(arg0).getDuration());
			getView.textView.setText("时长："+time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ImageLoader.getInstance().displayImage(beans.get(arg0).getThumbnail(),
				getView.imageView);
		return view;
	}

	static class GetView {
		ImageView imageView;
		TextView nameView;
		TextView textView;
	}

}
