package com.opar.mobile.aplayer.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.youku.service.download.DownloadInfo;


public class CachingVideoAdapter extends BaseAdapter{
		
		private LayoutInflater inflater;
		private Handler h;
		private ArrayList<DownloadInfo> downloadingList_show; 
		
		public CachingVideoAdapter(Context context,ArrayList<DownloadInfo> downloadingList_show){
			inflater = LayoutInflater.from(context);
			this.downloadingList_show = downloadingList_show;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(downloadingList_show == null)return 0;
			return downloadingList_show.size();
		}

		@Override
		public DownloadInfo getItem(int position) {
			// TODO Auto-generated method stub
			if(downloadingList_show == null)return null;
			return downloadingList_show.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			GetView getView;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.movieinfo_list_item, null);
				getView = new GetView();
				getView.imageView = (ImageView) convertView.findViewById(R.id.hot_mv_img);
				getView.nameView = (TextView) convertView.findViewById(R.id.hot_mv_name);
				getView.textView = (TextView) convertView.findViewById(R.id.hot_mv_duration);
				getView.check = (CheckBox) convertView.findViewById(R.id.check);
				convertView.setTag(getView);
			} else {
				getView = (GetView) convertView.getTag();
				getView.imageView.setImageResource(R.drawable.logo);
			}
			final DownloadInfo info = downloadingList_show.get(position);			
			getView.nameView.setText(info.title);		
			
			if(info.state == DownloadInfo.STATE_DOWNLOADING){							//当前视频的下载状态：正在下载
				getView.textView.setText("正在下载");					
			}else if(info.state == DownloadInfo.STATE_PAUSE){								//当前视频的下载状态：暂停中
				getView.textView.setText("暂停中");
			}else if(info.state == DownloadInfo.STATE_INIT								//当前视频的下载状态：等待中
					|| info.state == DownloadInfo.STATE_EXCEPTION
					|| info.state == DownloadInfo.STATE_WAITING){
				getView.textView.setText("等待中");
			}
			ImageLoader.getInstance().displayImage(info.imgUrl,getView.imageView);
			return convertView;
		}
		private class GetView {
			ImageView imageView;
			TextView nameView;
			TextView textView;
			CheckBox check;
		}
	}
