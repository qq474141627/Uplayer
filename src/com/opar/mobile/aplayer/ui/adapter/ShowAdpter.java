package com.opar.mobile.aplayer.ui.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.util.StringUtils;
public class ShowAdpter extends BaseAdapter {
	private Context context;
	public ArrayList<ShowBean> beans = new ArrayList<ShowBean>();
	public ShowAdpter(Context context) {
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
	public ShowBean getItem(int arg0) {
		if(beans != null){
			return beans.get(arg0);
		}
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	public void addData(ArrayList<ShowBean> list) {
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
			view = LayoutInflater.from(context).inflate(
					R.layout.item_show_list, null);
			getView = new GetView();
			getView.imageView = (ImageView) view.findViewById(R.id.hot_mv_img);
			getView.nameView = (TextView) view
					.findViewById(R.id.hot_mv_name_id);
			getView.hot_update = (TextView) view
					.findViewById(R.id.hot_mv_update_id);
			getView.hot_nums = (TextView) view.findViewById(R.id.hot_nums1);
			getView.hot_time=(TextView) view.findViewById(R.id.hot_time1);
			getView.hot_score=(TextView) view.findViewById(R.id.hot_score1);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.imageView.setImageResource(R.drawable.logo);
		}
		if(beans.size() == 0)return view;
		getView.nameView.setText(beans.get(arg0).getName());
		getView.hot_nums.setText("点播："+StringUtils.getViewNum(beans.get(arg0).getView_count()));
		getView.hot_time.setText("上映："+beans.get(arg0).getPublished());
		if(beans.get(arg0).getEpisode_count() == (beans.get(arg0).getEpisode_updated())){
			if(beans.get(arg0).getEpisode_updated() > 1){
				getView.hot_update.setText("共"+beans.get(arg0).getEpisode_updated()+"集全");
			}else{
				getView.hot_update.setText("被赞"+beans.get(arg0).getFavorite_count()+"次");
			}
		}else{
			getView.hot_update.setText("更新至第"+beans.get(arg0).getEpisode_updated()+"集");
		}	
		setShowScore(getView.hot_score, beans.get(arg0).getScore());
		ImageLoader.getInstance().displayImage(beans.get(arg0).getThumbnail(),
				getView.imageView);
		return view;
	}

	static class GetView {
		ImageView imageView;
		TextView nameView;
		TextView hot_nums;
		TextView hot_time;
		TextView hot_score;
		TextView hot_update;
	}
	
	private void setShowScore(TextView view , Double score){
		try {
			Drawable drawable = null;
			if(score < 2.5){
				drawable= context.getResources().getDrawable(R.drawable.iv_score_1_0);
				view.setTextColor(context.getResources().getColor(R.color.gray));
			}else if(score < 5.0){
				drawable= context.getResources().getDrawable(R.drawable.iv_score_1_1);
				view.setTextColor(context.getResources().getColor(R.color.blue));
			}else if(score < 7.5){
				drawable= context.getResources().getDrawable(R.drawable.iv_score_1_2);
				view.setTextColor(context.getResources().getColor(R.color.yellow));
			}else{
				drawable= context.getResources().getDrawable(R.drawable.iv_score_1_3);
				view.setTextColor(context.getResources().getColor(R.color.red));
			}
			/// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			view.setCompoundDrawables(null,drawable,null,null);
			view.setText(String.format("%.1f", score));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
