package com.opar.mobile.aplayer.ui;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.ui.adapter.PageFragmentAdapter;
import com.opar.mobile.aplayer.util.StringUtils;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.youku.login.widget.YoukuLoading;

public class Activity_ShowInfo extends ActivityBase implements OnClickListener, OnPageChangeListener,OnCheckedChangeListener{
	private ShowBean oBean;
	private ImageView show_movie_img;
	private TextView show_movie_area,show_movie_director,
	                 show_movie_performer,show_movie_genre,
	                 btn_play,btn_download,btn_like,
	                 text_favorite_count,show_movie_score;
    private ViewPager vPager;
    private PageFragmentAdapter adapter;
    private RadioGroup bar_radio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_info);
		oBean = (ShowBean) getIntent().getSerializableExtra("name");
		initView();
		new MyThread().start();
		YoukuLoading.show(this);
	}

	private void initView() {
		setTitle(oBean.getName());
		show_movie_img = (ImageView) findViewById(R.id.show_movie_info_img);
		show_movie_score = (TextView) findViewById(R.id.show_movie_info_score);
		show_movie_area = (TextView) findViewById(R.id.show_movie_area);
		show_movie_director = (TextView) findViewById(R.id.show_movie_director);
		show_movie_performer = (TextView) findViewById(R.id.show_movie_performer);
		show_movie_genre = (TextView) findViewById(R.id.show_movie_genre);
		btn_play = (TextView)findViewById(R.id.btn_play);
		btn_download = (TextView)findViewById(R.id.btn_download);
		btn_like = (TextView)findViewById(R.id.btn_like);
		text_favorite_count = (TextView)findViewById(R.id.text_favorite_count);
		btn_play.setOnClickListener(this);
		btn_download.setOnClickListener(this);
		btn_like.setOnClickListener(this);
		
		bar_radio=(RadioGroup)findViewById(R.id.bar_radio);
		bar_radio.setOnCheckedChangeListener(this);
		List<FragmentBase> list = new ArrayList<FragmentBase>();
		list.add(new Fragment_MovieInfo_Pager3());
		list.add(new Fragment_MovieInfo_Pager4());
		list.add(new Fragment_MovieInfo_Pager5());
		vPager = (ViewPager)findViewById(R.id.vPager);
		adapter = new PageFragmentAdapter(getSupportFragmentManager(),list);
        vPager.setAdapter(adapter);
        vPager.setCurrentItem(0,false);  
        vPager.setOnPageChangeListener(this); 
        vPager.setOffscreenPageLimit(adapter.getCount());  
        
	}


	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			YoukuLoading.dismiss();
			setDate();
		};
	};

	private void setDate() {
		ImageLoader.getInstance().displayImage(oBean.getThumbnail(), show_movie_img);
		show_movie_area.setText("国家/地区："+oBean.getArea());
		show_movie_genre.setText("类型："+oBean.getGenre());
		show_movie_director.setText(oBean.getDirector());
		show_movie_performer.setText(oBean.getPerformer());
		btn_play.setText(StringUtils.getViewNum(oBean.getView_count()));
		btn_download.setText(StringUtils.getViewNum(oBean.getDown_count()));
		btn_like.setText(StringUtils.getViewNum(oBean.getFavorite_count()));
		text_favorite_count.setText(StringUtils.getViewNum(oBean.getComment_count())+"人正在观看");
		setShowScore(show_movie_score, String.valueOf(oBean.getScore()));
		//更新详情fragment数据
		adapter.reflash(0, oBean.getDescription());
		//更新评论数据
		adapter.reflash(1, XmlUtil.getVid(oBean.getLink()));
		//更新相关数据
		adapter.reflash(2, XmlUtil.getVid(oBean.getLink()));
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			XmlUtil.getShowInfo(oBean);
			handler.sendEmptyMessage(1);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub ll_play,ll_down,ll_share,ll_save
		if(v.getId() == R.id.btn_play){
			if(oBean.getCategory().equals(getString(R.string.Movies))){
				Activity_VideoPlayer.startActivity(Activity_ShowInfo.this, 
						XmlUtil.getVid(oBean.getLink()), 
						oBean.getName(),
						oBean.getView_count(),
						oBean.getUp_count(),
						true);
			}else{
				Intent intent=new Intent();
				intent.putExtra("movie", oBean);
				intent.setClass(Activity_ShowInfo.this, Activity_SelectNum.class);
				startActivity(intent);
			}
		}else if(v.getId() == R.id.btn_download){
			
		}else if(v.getId() == R.id.btn_like){
			
		}
			
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case R.id.tv_guid1:
			vPager.setCurrentItem(0,false);
			break;
        case R.id.tv_guid2:
			vPager.setCurrentItem(1,false);
			break;
        case R.id.tv_guid3:
			vPager.setCurrentItem(2,false);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		RadioButton bt = (RadioButton) bar_radio.getChildAt(arg0);
		bt.setChecked(true);
	}
	
	private void setShowScore(TextView view , String score){
		try {
			Drawable drawableLeft= getResources().getDrawable(
					getResources().getIdentifier("iv_score_2_big_"+score.charAt(0), "drawable", getPackageName()));
			Drawable drawableRight= getResources().getDrawable(
					getResources().getIdentifier("iv_score_2_small_"+score.charAt(2), "drawable", getPackageName()));
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
