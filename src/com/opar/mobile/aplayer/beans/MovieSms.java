package com.opar.mobile.aplayer.beans;

import java.io.Serializable;

public class MovieSms implements Serializable{
	private static final long serialVersionUID = 1L;
private String content;
private String time;
private String userId;
private String userName;
private String userIcon;
private String userSex;
private UserBean user;

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}

public MovieSms() {
	super();
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getUserIcon() {
	return userIcon;
}
public void setUserIcon(String userIcon) {
	this.userIcon = userIcon;
}
public String getUserSex() {
	return userSex;
}
public void setUserSex(String userSex) {
	this.userSex = userSex;
}
public UserBean getUser() {
	return user;
}
public void setUser(UserBean user) {
	this.user = user;
}

}
