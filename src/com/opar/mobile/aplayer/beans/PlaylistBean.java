package com.opar.mobile.aplayer.beans;

import java.io.Serializable;

public class PlaylistBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;//专辑ID
	private String name;//专辑名称
	private String link;//专辑链接
	private String thumbnail;//专辑截图
	private int video_count;//专辑视频数量
	private int view_count;//专辑总播放数
	private int duration;//专辑总时长，单位：秒
	private String published;//发布时间
	private String description;//专辑描述信息
	private String category;//分类
	private String tags;//标签，多个标签用逗号分隔
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getVideo_count() {
		return video_count;
	}
	public void setVideo_count(int video_count) {
		this.video_count = video_count;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	
	
}
