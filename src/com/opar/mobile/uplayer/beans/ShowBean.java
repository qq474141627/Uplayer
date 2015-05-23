package com.opar.mobile.uplayer.beans;

import java.io.Serializable;
import java.util.List;

public class ShowBean implements Serializable{

 private static final long serialVersionUID = 1L;
 private String id;      //视频唯一ID
 private String name;//视频标题
 private String link;//视频播放链接
 private String thumbnail;//视频截图
 private int episode_count;//视频总集数
 private int episode_updated;//视频更新至
 private String category;//视频分类
 private int view_count;//总播放数
 private Double score;//评分
 private String published;//发布时间
 private String paid;//是否付费 0.否 1.是
 private List<String> types;//视频格式flvhd flv 3gphd 3gp hd hd2
 private int favorite_count;//被评论
 private int duration;//时长
 
 private String genre;//类型
 private String area;//地区
 private String description;//详情
 private int up_count;//顶数
 private int down_count;//踩数
 private String director;//导演
 private String performer;//主演
 private List<String> links;//资源链接
 private int view_week_count;//本周观看人数
 private int comment_count;//评论数
 
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
public int getEpisode_count() {
	return episode_count;
}
public void setEpisode_count(int episode_count) {
	this.episode_count = episode_count;
}
public int getEpisode_updated() {
	return episode_updated;
}
public void setEpisode_updated(int episode_updated) {
	this.episode_updated = episode_updated;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public int getView_count() {
	return view_count;
}
public void setView_count(int view_count) {
	this.view_count = view_count;
}
public String getPublished() {
	return published;
}
public void setPublished(String published) {
	this.published = published;
}
public String getPaid() {
	return paid;
}
public void setPaid(String paid) {
	this.paid = paid;
}
public List<String> getTypes() {
	return types;
}
public void setTypes(List<String> types) {
	this.types = types;
}
public Double getScore() {
	return score;
}
public void setScore(Double score) {
	this.score = score;
}
public int getFavorite_count() {
	return favorite_count;
}
public void setFavorite_count(int favorite_count) {
	this.favorite_count = favorite_count;
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
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int getUp_count() {
	return up_count;
}
public void setUp_count(int up_count) {
	this.up_count = up_count;
}
public int getDown_count() {
	return down_count;
}
public void setDown_count(int down_count) {
	this.down_count = down_count;
}
public String getDirector() {
	return director;
}
public void setDirector(String director) {
	this.director = director;
}
public String getPerformer() {
	return performer;
}
public void setPerformer(String performer) {
	this.performer = performer;
}
public List<String> getLinks() {
	return links;
}
public void setLinks(List<String> links) {
	this.links = links;
}
public int getView_week_count() {
	return view_week_count;
}
public void setView_week_count(int view_week_count) {
	this.view_week_count = view_week_count;
}
public int getComment_count() {
	return comment_count;
}
public void setComment_count(int comment_count) {
	this.comment_count = comment_count;
}
public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
}

 
}
