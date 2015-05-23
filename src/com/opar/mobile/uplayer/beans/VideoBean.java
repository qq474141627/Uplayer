package com.opar.mobile.uplayer.beans;

import java.io.Serializable;
import java.util.List;

public class VideoBean implements Serializable{

 private static final long serialVersionUID = 1L;
 private String id;      //视频唯一ID
 private String name;//视频标题
 private String link;//视频播放链接
 private String thumbnail;//视频截图
 private String category;//视频分类
 private int view_count;//总播放数
 private String score;//评分
 private String published;//发布时间
 private List<String> types;//视频格式flvhd flv 3gphd 3gp hd hd2
 private int favorite_count;//被评论
 private int duration;//时长
 private int seq;//节目中视频顺序号
 
 private String genre;//类型
 private String area;//地区
 private String description;//详情
 private int up_count;//顶数
 private int down_count;//踩数
 private String director;//导演
 private String performer;//主演
 private List<String> links;//资源链接
 private int comment_count;//评论数
 private List<String> operation_limit;//操作限制 COMMENT_DISABLED: 禁评论 DOWNLOAD_DISABLED: 禁下载
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
public String getScore() {
	return score;
}
public void setScore(String score) {
	this.score = score;
}
public String getPublished() {
	return published;
}
public void setPublished(String published) {
	this.published = published;
}
public List<String> getTypes() {
	return types;
}
public void setTypes(List<String> types) {
	this.types = types;
}
public int getFavorite_count() {
	return favorite_count;
}
public void setFavorite_count(int favorite_count) {
	this.favorite_count = favorite_count;
}
public int getDuration() {
	return duration;
}
public void setDuration(int duration) {
	this.duration = duration;
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
public int getComment_count() {
	return comment_count;
}
public void setComment_count(int comment_count) {
	this.comment_count = comment_count;
}
public List<String> getOperation_limit() {
	return operation_limit;
}
public void setOperation_limit(List<String> operation_limit) {
	this.operation_limit = operation_limit;
}
public int getSeq() {
	return seq;
}
public void setSeq(int seq) {
	this.seq = seq;
}
 


 
}
