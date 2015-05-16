package com.opar.mobile.aplayer.beans;

import java.io.Serializable;

import android.text.TextUtils;

public class VideoParameter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String client_id="client_id";
	private String category="资讯";//社会资讯
	private String genre;//古装,言情
	private String period;//时间范围 today: 今日 week: 本周 month: 本月 history: 历史
	private String orderby="view-count";
	/*view-count: 总播放数
	 * published: 发布时间 
	 * comment-count: 总评论数 
	 * reference-count: 总引用
	 * favorite-count: 总收藏数
	 * favorite-time: 收藏时间
	 */
	private int page=1;//页数
	//private int count=20;//页个数
	
	
	public void clearData(){
		page=1;
		genre=null;
		period=null;
		setOrderby(0);
	}


	public String getClient_id() {
		return client_id;
	}


	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}


	public String getCategory() {
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


	public String getPeriod() {
		return period;
	}


	public void setPeriod(int period) {
		switch (period) {
		case 0:
			this.period="today";
			break;
		case 1:
			this.period="week";
			break;
		case 2:
			this.period="month";
			break;
		case 3:
			this.period="history";
			break;
		default:
			break;
		}
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		switch (orderby) {
		case 0:
			this.orderby="view-count";
			break;
		case 1:
			this.orderby="published";
			break;
		case 2:
			this.orderby="comment-count";
			break;
		case 3:
			this.orderby="favorite-count";
			break;
		case 4:
			this.orderby="favorite-time";
			break;
		default:
			break;
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
