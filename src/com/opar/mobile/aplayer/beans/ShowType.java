package com.opar.mobile.aplayer.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

public class ShowType {//电影
private List<String> orderbyList ;//排序
private List<String> genreList ;//动作 科幻
private List<String> areaList ;//大陆 欧美
private List<String> yearList ;//年份
private int id;
public ShowType(int id){
	this.id = id;
}
public List<String> getgenreList() {
	if(genreList == null){
	genreList = new ArrayList<String>();
	switch (id) {
	case 0:
		genreList.add("全部");
		genreList.add("喜剧");
		genreList.add("爱情");
		genreList.add("恐怖");
		genreList.add("动作");
		genreList.add("科幻");
		genreList.add("战争");
		genreList.add("警匪");
		genreList.add("犯罪");
		genreList.add("动画");
		genreList.add("奇幻");
		genreList.add("其他");
		break;
	case 1:
		genreList.add("全部");
		genreList.add("古装");
		genreList.add("警匪");
		genreList.add("搞笑");
		genreList.add("悬疑");
		genreList.add("神话");
		genreList.add("偶像");
		genreList.add("历史");
		genreList.add("言情");
		genreList.add("家庭");
		genreList.add("科幻");
		genreList.add("其它");
		break;
	case 2:
		genreList.add("全部");
		genreList.add("脱口秀");
		genreList.add("真人秀");
		genreList.add("选秀");
		genreList.add("美食");
		genreList.add("旅游");
		genreList.add("汽车");
		genreList.add("访谈");
		genreList.add("纪实");
		genreList.add("搞笑");
		genreList.add("其他");
		break;
	case 3:
		genreList.add("全部");
		genreList.add("搞笑");
		genreList.add("恋爱");
		genreList.add("热血");
		genreList.add("格斗");
		genreList.add("美少女");
		genreList.add("魔法");
		genreList.add("机战");
		genreList.add("校园");
		genreList.add("少儿");
		genreList.add("冒险");
		genreList.add("真人");
		genreList.add("LOLI");
		genreList.add("其它");
		break;
	default:
		break;
	}
    }
	return genreList;
}

public List<String> getAreaList() {
	if(areaList == null){
	areaList = new ArrayList<String>();
	switch (id) {
	case 1:
		areaList.add("全部");
		areaList.add("香港");
		areaList.add("美国");
		areaList.add("大陆");
		areaList.add("韩国");
		areaList.add("台湾");
		areaList.add("日本");
		areaList.add("其他");
		break;
	case 2:
		areaList.add("全部");
		areaList.add("大陆");
		areaList.add("韩国");
		areaList.add("香港");
		areaList.add("泰国");
		areaList.add("台湾");
		areaList.add("美国");
		areaList.add("其它");
		break;
	case 3:
		areaList.add("全部");
		areaList.add("日本");
		areaList.add("大陆");
		areaList.add("美国");
		areaList.add("韩国");
		areaList.add("其它");
		break;
	case 4:
		areaList.add("全部");
		areaList.add("大陆");
		areaList.add("香港");
		areaList.add("台湾");
		areaList.add("韩国");
		areaList.add("日本");
		areaList.add("美国");
		areaList.add("其他");
		break;
	default:
		break;
	}
	}
	return areaList;
}

public List<String> getYearList() {
	if(yearList == null){
		yearList = new ArrayList<String>();
		yearList.add("全部");
		yearList.add("2015");
		yearList.add("2014");
		yearList.add("2013");
		yearList.add("2012");
		yearList.add("2011");
		yearList.add("2010");
		yearList.add("2009");
		yearList.add("更早");
	}
	return yearList;
}

public List<String> getOrderbyList() {
	
	if(orderbyList==null){
		orderbyList=new ArrayList<String>();
		orderbyList.add("全部");
		orderbyList.add("最新");
		orderbyList.add("最热");
		orderbyList.add("评分");
	}
	return orderbyList;
}

public int getOrderbyKey(String key) {
	if(!TextUtils.isEmpty(key)){
		if(key.equals("最新")){
			return 5;
		}else if(key.equals("最热")){
			return 1;
		}else if(key.equals("评分")){
			return 2;
		}
	}
	return 0;
}


}
