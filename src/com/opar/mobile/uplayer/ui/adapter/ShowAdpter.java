package com.opar.mobile.uplayer.ui.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.util.StringUtils;
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
			ImageView show_img;
			TextView show_name;
			TextView show_num;
			TextView show_description;
			TextView show_score;
			view = LayoutInflater.from(context).inflate(
					R.layout.item_show_list, null);
			getView = new GetView();
			getView.show_img = (ImageView) view.findViewById(R.id.show_img);
			getView.show_name = (TextView) view.findViewById(R.id.show_name);
			getView.show_num = (TextView) view.findViewById(R.id.show_num);
			getView.show_description = (TextView) view.findViewById(R.id.show_description);
			getView.show_score=(TextView) view.findViewById(R.id.show_score);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.show_img.setImageResource(R.drawable.logo);
		}
		if(beans.size() == 0)return view;
		ShowBean bean = beans.get(arg0);
		getView.show_name.setText(beans.get(arg0).getName());
		if(bean.getCategory().equals("电影")){
			StringBuffer num = new StringBuffer("");
			if(!TextUtils.isEmpty(bean.getPublished()) && bean.getPublished().length() > 4){
				num.append(bean.getPublished().substring(0, 4)+" ");
			}
			if(!TextUtils.isEmpty(bean.getPerformer())){
				num.append(bean.getPerformer());
			}
			getView.show_num.setText(num);
		}else{
			getView.show_num.setText("更新至:"+bean.getEpisode_updated()+"集");
		}
		getView.show_description.setText(bean.getDescription());
		setShowScore(getView.show_score, String.valueOf(bean.getScore()));
		ImageLoader.getInstance().displayImage(bean.getThumbnail(),getView.show_img);
		return view;
	}

	static class GetView {
		ImageView show_img;
		TextView show_name;
		TextView show_num;
		TextView show_description;
		TextView show_score;
	}
	
	private void setShowScore(TextView view , String score){
		try {
			Drawable drawableLeft= context.getResources().getDrawable(
					context.getResources().getIdentifier("iv_score_big_"+score.charAt(0), "drawable", context.getPackageName()));
			Drawable drawableRight= context.getResources().getDrawable(
					context.getResources().getIdentifier("iv_score_small_"+score.charAt(2), "drawable", context.getPackageName()));
			/// 这一步必须要做,否则不会显示.
			drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
			drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
			view.setCompoundDrawables(drawableLeft,null,drawableRight,null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
