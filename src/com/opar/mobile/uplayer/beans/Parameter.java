package com.opar.mobile.uplayer.beans;

import java.io.Serializable;

import android.text.TextUtils;

public class Parameter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String category="电视剧";//电视剧
	private String genre;//古装,言情
	private String area;//大陆,香港
	private String release_year;//2014
	private String orderby="view-today-count";
	/*
	 * view-count: 总播放数 
	 * comment-count: 总评论数
	 * view-today-count: 今日播放数 
	 * view-week-count: 本周播放数 
	 * score: 评分
	 * updated: 最后一个正片添加时间
	 * release-date: 上映日期
	 */
	private int page;//页数
	
	
	public Parameter(String category) {
		super();
		this.category = category;
	}
	public String getCategory() {
		if(TextUtils.isEmpty(category)){
			category="电视剧";
		}
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(int i) {
		/*
		 * view-count: 总播放数 
		 * comment-count: 总评论数
		 * view-today-count: 今日播放数 
		 * release-date: 本周播放数 
		 * score: 评分
		 */
		if(i==0){
			orderby="view-today-count";
		}else if(i==1){
			orderby="view-count";
		}else if(i==2){
			orderby="score";
		}else if(i==3){
			orderby="comment-count";
		}else if(i==4){
			orderby="view-today-count";
		}else if(i==5){
			orderby="updated";
		}
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void clearData(){
		page = 0;
		genre = null;
		area = null;
		release_year = null;
		orderby = "view-today-count";
	}
	
}
