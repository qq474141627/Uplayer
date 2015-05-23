package com.opar.mobile.uplayer.ui;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.dao.DBHelperDao;
import com.opar.mobile.uplayer.ui.adapter.PageFragmentAdapter;
import com.opar.mobile.uplayer.util.HandlerUtil;
import com.opar.mobile.uplayer.util.StringUtils;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.xml.XmlUtil;
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
	public ShowBean getoBean() {
		return oBean;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_info);
		oBean = (ShowBean) getIntent().getSerializableExtra("name");
		if(oBean == null) return;
		initView();
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
		//添加数据
		setDate();
		
		bar_radio=(RadioGroup)findViewById(R.id.bar_radio);
		bar_radio.setOnCheckedChangeListener(this);
		List<SherlockFragment> list = new ArrayList<SherlockFragment>();
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

	private void setDate() {
		setShowSave();
		ImageLoader.getInstance().displayImage(oBean.getThumbnail(), show_movie_img);
		show_movie_area.setText("国家/地区："+oBean.getArea());
		show_movie_genre.setText("类型："+oBean.getGenre());
		show_movie_director.setText("导演："+oBean.getDirector());
		show_movie_performer.setText("演员："+oBean.getPerformer());
		btn_play.setText(StringUtils.getViewNum(oBean.getView_count()));
		btn_download.setText(StringUtils.getViewNum(oBean.getDown_count()));
		btn_like.setText(StringUtils.getViewNum(oBean.getFavorite_count()));
		text_favorite_count.setText(StringUtils.getViewNum(oBean.getComment_count())+"人正在观看");
		setShowScore(show_movie_score, String.valueOf(oBean.getScore()));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub ll_play,ll_down,ll_share,ll_save
		if(oBean == null) return;
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
				intent.putExtra("download", false);
				intent.setClass(Activity_ShowInfo.this, Activity_SelectNum.class);
				startActivity(intent);
			}
		}else if(v.getId() == R.id.btn_download){
			Intent intent=new Intent();
			intent.putExtra("movie", oBean);
			intent.putExtra("download", true);
			intent.setClass(Activity_ShowInfo.this, Activity_SelectNum.class);
			startActivity(intent);
		}else if(v.getId() == R.id.btn_like){
			if(DBHelperDao.getDBHelperDaoInstace().isSaveKey(oBean.getId())){
				DBHelperDao.getDBHelperDaoInstace().delSaveKey(oBean.getId());
				//UplayerConfig.showTips("收藏取消");
			}else{
				DBHelperDao.getDBHelperDaoInstace().insertSaveKey(oBean.getId());
				//UplayerConfig.showTips("收藏成功");
			}
			setShowSave();
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
	
	private void setShowSave(){
		boolean save = DBHelperDao.getDBHelperDaoInstace().isSaveKey(oBean.getId());
		try {
			Drawable drawableTop= getResources().getDrawable(save?R.drawable.btn_like_fav:R.drawable.btn_like_select);
			/// 这一步必须要做,否则不会显示.
			drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
			btn_like.setCompoundDrawables(null,drawableTop,null,null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
