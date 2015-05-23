package com.opar.mobile.uplayer.ui.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.util.StringUtils;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.DownloadUtils;


public class CachingVideoAdapter extends BaseAdapter{
		
		private LayoutInflater inflater;
		private ArrayList<DownloadInfo> downloadingList_show; 
		private GetView getView;
		private long downloadedSize;
		
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
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_caching_list, null);
				getView = new GetView();
				getView.caching_img = (ImageView) convertView.findViewById(R.id.caching_img);
				getView.caching_name = (TextView) convertView.findViewById(R.id.caching_name);
				getView.caching_size = (TextView) convertView.findViewById(R.id.caching_size);
				getView.caching_state = (TextView) convertView.findViewById(R.id.caching_state);
				getView.caching_progress = (TextView) convertView.findViewById(R.id.caching_progress);
				convertView.setTag(getView);
			} else {
				getView = (GetView) convertView.getTag();
				getView.caching_img.setImageResource(R.drawable.logo);
			}
			final DownloadInfo info = downloadingList_show.get(position);			
			getView.caching_name.setText(info.title);	
			//保存下载大小，方便计算下载速度
			if(info.state == DownloadInfo.STATE_DOWNLOADING){
				long speed = (info.downloadedSize - downloadedSize)/2;
				getView.caching_state.setText(StringUtils.generateFileSize(speed>0?speed:0)+"/s");
				downloadedSize = info.downloadedSize;
			}else if(info.state == DownloadInfo.STATE_PAUSE){
				getView.caching_state.setText("暂停中");
			}else if(info.state == DownloadInfo.STATE_INIT								//当前视频的下载状态：等待中
					|| info.state == DownloadInfo.STATE_EXCEPTION
					|| info.state == DownloadInfo.STATE_WAITING){
				getView.caching_state.setText("等待中");
			}
			getView.caching_progress.setText(String.format("%.1f", info.progress)+"%");
			getView.caching_size.setText(StringUtils.generateFileSize(info.downloadedSize)
					+"/"+StringUtils.generateFileSize(info.size));
			//loadImage 解决屏幕闪的问题
			ImageLoader.getInstance().displayImage(info.imgUrl, getView.caching_img);
			return convertView;
		}
		private class GetView {
			private ImageView caching_img;
			private TextView caching_name;
			private TextView caching_size;
			private TextView caching_state;
			private TextView caching_progress;
		}
	}
