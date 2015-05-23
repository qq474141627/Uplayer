package com.opar.mobile.uplayer.ui.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.VideoBean;
import com.opar.mobile.uplayer.util.StringUtils;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.DownloadUtils;
public class MovieinfoAdpter extends BaseAdapter {
	private Context context;
	private boolean download;
	private ArrayList<VideoBean> beans = new ArrayList<VideoBean>();
	public MovieinfoAdpter(Context context,boolean download) {
		this.context = context;
		this.download = download;
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
			view = LayoutInflater.from(context).inflate(R.layout.movieinfo_list_item, null);
			getView = new GetView();
			getView.imageView = (ImageView) view.findViewById(R.id.hot_mv_img);
			getView.nameView = (TextView) view.findViewById(R.id.hot_mv_name);
			getView.textView = (TextView) view.findViewById(R.id.hot_mv_duration);
			getView.check = (CheckBox) view.findViewById(R.id.check);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.imageView.setImageResource(R.drawable.logo);
		}
		final VideoBean bean = beans.get(arg0);
		getView.nameView.setText(bean.getName());
		try {
			String time=StringUtils.generateTime(bean.getDuration());
			getView.textView.setText("时长："+time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(download){
			getView.check.setVisibility(0);
			if(DownloadManager.getInstance().existsDownloadInfo(bean.getId())){
				getView.check.setChecked(true);
			}else{
				getView.check.setChecked(false);
			}
			getView.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					if(arg1){
						DownloadManager.getInstance().createDownload(bean.getId(), bean.getName(), null);
					}else{
						//DownloadManager.getInstance().deleteDownloaded(DownloadManager.getInstance().getDownloadInfo(bean.getId()));
					}
				}
			});
		}
		ImageLoader.getInstance().displayImage(bean.getThumbnail(),
				getView.imageView);
		return view;
	}

	private class GetView {
		ImageView imageView;
		TextView nameView;
		TextView textView;
		CheckBox check;
	}

}
