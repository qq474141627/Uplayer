package com.opar.mobile.aplayer.ui.adapter;
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
import com.opar.mobile.aplayer.beans.PlaylistBean;
import com.opar.mobile.aplayer.util.StringUtils;
public class PlaylistAdpter extends BaseAdapter {
	private Context context;
	public ArrayList<PlaylistBean> beans;
	public PlaylistAdpter(Context context, ArrayList<PlaylistBean> beans) {
		this.context = context;
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return beans.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
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
				+"\n\n\n\n\n\n时长"+StringUtils.formatDate(beans.get(arg0).getDuration()));
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
