package com.opar.mobile.aplayer.beans;

import android.text.TextUtils;

public class UserBean {

	private static final long serialVersionUID = 1L;
	 private String id;      //用户ID
	 private String name;//用户名
	 private String link;//用户地址
	 private String avatar;//头像
	 private String avatar_large;//大头像
	 private String gender;//性别 男：m 女：f 未知：u
	 private String description;//描述
	 private int videos_count;//总视频数
	 private int playlists_count;//总专辑数
	 private int favorites_count;//总收藏视频数
	 private int followers_count;//粉丝数
	 private int following_count;//关注数
	 private int statuses_count;      //动态数
	 private int subscribe_count;      //被订阅数
	 private int vv_count;      //总视频播放数
	 private String regist_time;      //注册时间
	 private boolean hasData;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getAvatar_large() {
		return avatar_large;
	}
	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVideos_count() {
		return videos_count;
	}
	public void setVideos_count(int videos_count) {
		this.videos_count = videos_count;
	}
	public int getPlaylists_count() {
		return playlists_count;
	}
	public void setPlaylists_count(int playlists_count) {
		this.playlists_count = playlists_count;
	}
	public int getFavorites_count() {
		return favorites_count;
	}
	public void setFavorites_count(int favorites_count) {
		this.favorites_count = favorites_count;
	}
	public int getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	public int getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	public int getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}
	public int getSubscribe_count() {
		return subscribe_count;
	}
	public void setSubscribe_count(int subscribe_count) {
		this.subscribe_count = subscribe_count;
	}
	public int getVv_count() {
		return vv_count;
	}
	public void setVv_count(int vv_count) {
		this.vv_count = vv_count;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}
	public boolean isHasData() {
		return hasData;
	}
	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
	 
	 
}
