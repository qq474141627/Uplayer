package com.opar.mobile.uplayer.ui.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.beans.VideoBean;
import com.opar.mobile.uplayer.util.StringUtils;
public class VideoAdpter extends BaseAdapter {
	private Context context;
	public ArrayList<VideoBean> beans = new ArrayList<VideoBean>();
	public VideoAdpter(Context context) {
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
	
	public void clear() {
		if(beans != null){
			beans.clear();
			notifyDataSetChanged();
		}
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		GetView getView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.item_playlist_list, null);
			getView = new GetView();
			getView.item_playlist_list_bg = (ImageView) view.findViewById(R.id.item_playlist_list_bg);
			getView.item_playlist_list_title = (TextView) view.findViewById(R.id.item_playlist_list_title);
			getView.item_playlist_list_cotent = (TextView) view.findViewById(R.id.item_playlist_list_cotent);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.item_playlist_list_bg.setBackgroundColor(Color.GRAY);
		}
		if(beans.size() == 0)return view;
		getView.item_playlist_list_title.setText(beans.get(arg0).getName());
		getView.item_playlist_list_cotent.setText(
				"点播"+StringUtils.getViewNum(beans.get(arg0).getView_count())+"次"
				+"\n\n\n\n\n\n时长"+StringUtils.generateTime(beans.get(arg0).getDuration()));
		ImageLoader.getInstance().displayImage(beans.get(arg0).getThumbnail(),
				getView.item_playlist_list_bg);
		return view;
	}

	static class GetView {
		ImageView item_playlist_list_bg;
		TextView item_playlist_list_title;
		TextView item_playlist_list_cotent;
	}

}
