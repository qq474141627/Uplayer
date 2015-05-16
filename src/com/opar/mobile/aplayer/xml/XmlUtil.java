package com.opar.mobile.aplayer.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.opar.mobile.aplayer.beans.Categorie;
import com.opar.mobile.aplayer.beans.MovieSms;
import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.PlaylistBean;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.beans.UserBean;
import com.opar.mobile.aplayer.beans.VideoParameter;
import com.opar.mobile.aplayer.util.StringUtils;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.JsonUtil;

public class XmlUtil {
	
	/*
	 *根据分类查找节目
	 */
	public static ArrayList<ShowBean> getShowByCategory(Parameter p){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/by_category.json?client_id="+UplayerConfig.getClientId());
			string.append("&category="+StringUtils.URLEncoder(p.getCategory()));
        if(!TextUtils.isEmpty(p.getGenre())){
        	string.append("&genre="+StringUtils.URLEncoder(p.getGenre()));
		}
        if(!TextUtils.isEmpty(p.getArea())){
        	string.append("&area="+StringUtils.URLEncoder(p.getArea()));
        }
        if(!TextUtils.isEmpty(p.getRelease_year())){
        	string.append("&release_year="+StringUtils.URLEncoder(p.getRelease_year()));
        }
        if(!TextUtils.isEmpty(p.getOrderby())){
        	string.append("&orderby="+StringUtils.URLEncoder(p.getOrderby()));
        }
        	string.append("&page="+StringUtils.URLEncoder(p.getPage()+""));
        	
		return JsonUtil.getShowByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	/*
	 *根据分类查找节目
	 */
	public static List<String> getShowIdsByCategory(Parameter p){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/by_category.json?client_id="+UplayerConfig.getClientId());
			string.append("&category="+StringUtils.URLEncoder(p.getCategory()));
        if(!TextUtils.isEmpty(p.getGenre())){
        	string.append("&genre="+StringUtils.URLEncoder(p.getGenre()));
		}
        if(!TextUtils.isEmpty(p.getArea())){
        	string.append("&area="+StringUtils.URLEncoder(p.getArea()));
        }
        if(!TextUtils.isEmpty(p.getRelease_year())){
        	string.append("&release_year="+StringUtils.URLEncoder(p.getRelease_year()));
        }
        if(!TextUtils.isEmpty(p.getOrderby())){
        	string.append("&orderby="+StringUtils.URLEncoder(p.getOrderby()));
        }
        	string.append("&page="+StringUtils.URLEncoder(p.getPage()+""));
        	
		return JsonUtil.getShowIdsByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	/*
	 *根据分类查找专辑
	 */
	public static ArrayList<PlaylistBean> getPlaylistByCategory(Parameter p){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/playlists/by_category.json?client_id="+UplayerConfig.getClientId());
			string.append("&category="+StringUtils.URLEncoder(p.getCategory()));
        if(!TextUtils.isEmpty(p.getOrderby())){
        	string.append("&orderby="+StringUtils.URLEncoder(p.getOrderby()));
        }
        	string.append("&page="+StringUtils.URLEncoder(p.getPage()+""));
        	
		return JsonUtil.getPlaylistByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	/*
	 *根据分类查找视频
	 */
	public static ArrayList<VideoBean> getVideoByCategory(VideoParameter p){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/videos/by_category.json?client_id="+UplayerConfig.getClientId());
			string.append("&category="+StringUtils.URLEncoder(p.getCategory()));
        if(!TextUtils.isEmpty(p.getOrderby())){
        	string.append("&orderby="+StringUtils.URLEncoder(p.getOrderby()));
        }
        if(!TextUtils.isEmpty(p.getGenre())){
        	string.append("&genre="+StringUtils.URLEncoder(p.getGenre()));
		}
        if(!TextUtils.isEmpty(p.getPeriod())){
        	string.append("&period="+StringUtils.URLEncoder(p.getPeriod()));
		}
        
        	string.append("&page="+StringUtils.URLEncoder(p.getPage()+""));
        	
		return JsonUtil.getVideoByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	
	/*
	 *根据show_id获取详情
	 */
	public static void getShowInfo(ShowBean bean){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/show.json?client_id="+UplayerConfig.getClientId()+"&show_id="+bean.getId());
		try {
			String url=HttpsUtil.readJsonFromUrl(string.toString());
			JSONObject obj=new JSONObject(url);
			JsonUtil.getShowInfo(obj);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 *根据多条show_id获取详情
	 */
	public static List<ShowBean> getShowByIds(List<String> ids){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/show_batch.json?client_id="+UplayerConfig.getClientId()+"&show_ids="+StringUtils.join(ids, ","));
		try {
			JSONObject obj=new JSONObject(HttpsUtil.readJsonFromUrl(string.toString()));
			JSONArray array = obj.optJSONArray("shows");
			List<ShowBean> list = new ArrayList<ShowBean>();
			for(int i = 0;i<array.length();i++){
				ShowBean bean = JsonUtil.getShowInfo(array.optJSONObject(i));
				list.add(bean);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/*
	 *根据playlist_id获取详情
	 */
	public static void getPlaylistInfo(PlaylistBean bean){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/playlists/show.json?client_id="+UplayerConfig.getClientId()+"&playlist_id="+bean.getId());
		try {
			String url=HttpsUtil.readJsonFromUrl(string.toString());
			JSONObject obj=new JSONObject(url);
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("thumbnail"));
				bean.setName(obj.optString("name"));
				bean.setLink(obj.optString("play_link"));
				bean.setVideo_count(obj.optInt("video_count"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setCategory(obj.optString("category"));
				bean.setPublished(obj.optString("published"));
				bean.setDescription(obj.optString("description"));
				bean.setDuration(obj.optInt("duration"));
				bean.setTags(obj.optString("tags"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 *节目分类
	 */
	public static ArrayList<Categorie> getCategorieShow(){
		ArrayList<Categorie> list=new ArrayList<Categorie>();
		String string="https://openapi.youku.com/v2/schemas/show/category.json";
		try {
			String url=HttpsUtil.readJsonFromUrl(string);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("categories");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				Categorie bean=new Categorie();
				bean.setName(obj.optString("label"));
				JSONArray arr=obj.optJSONArray("genre");
				List<String> genre=new ArrayList<String>();
				for(int j=0;j<arr.length();j++){
					genre.add(arr.optJSONObject(j).getString("label"));
				}
				bean.setGenre(genre);
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	/*
	 *视频分类
	 */
	public static ArrayList<Categorie> getCategorieVideo(){
		ArrayList<Categorie> list=new ArrayList<Categorie>();
		String string="https://openapi.youku.com/v2/schemas/video/category.json";
		try {
			String url=HttpsUtil.readJsonFromUrl(string);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("categories");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				Categorie bean=new Categorie();
				bean.setName(obj.optString("label"));
				JSONArray arr=obj.optJSONArray("genres");
				List<String> genre=new ArrayList<String>();
				for(int j=0;j<arr.length();j++){
					genre.add(arr.optJSONObject(j).getString("label"));
				}
				bean.setGenre(genre);
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	/*
	 *专辑分类
	 */
	public static ArrayList<Categorie> getCategoriePlaylist(){
		ArrayList<Categorie> list=new ArrayList<Categorie>();
		String string="https://openapi.youku.com/v2/schemas/playlist/category.json";
		try {
			String url=HttpsUtil.readJsonFromUrl(string);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("category");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				Categorie bean=new Categorie();
				bean.setName(obj.optString("label"));
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	/*
	 *排行榜分类
	 */
	public static ArrayList<Categorie> getCategorietop(){
		ArrayList<Categorie> list=new ArrayList<Categorie>();
		String string="https://openapi.youku.com/v2/schemas/playlist/category.json";
		try {
			String url=HttpsUtil.readJsonFromUrl(string);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("categories");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				Categorie bean=new Categorie();
				bean.setName(obj.optString("cateName"));
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	/*
	 *获取视频集数
	 */
	public static ArrayList<VideoBean> getVideoBeans(String id,int page){
		ArrayList<VideoBean> list=new ArrayList<VideoBean>();
		try {
			String url=HttpsUtil.readJsonFromUrl("https://openapi.youku.com/v2/shows/videos.json?client_id="+UplayerConfig.getClientId()+"&show_id="+id
					+"&count=100&page="+page);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("videos");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				VideoBean bean=new VideoBean();
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("thumbnail_v2"));
				bean.setName(obj.optString("title"));
				bean.setLink(obj.optString("link"));
				bean.setDuration(obj.optInt("duration"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setUp_count(obj.optInt("up_count"));
				bean.setPublished(obj.optString("published"));
				bean.setSeq(obj.optInt("seq"));
				/*JSONArray arr=obj.optJSONArray("streamtypes");
				List<String> types=new ArrayList<String>();
				for(int j=0;j<arr.length();j++){
					types.add(arr.opt(j).toString());
				}
				bean.setTypes(types);*/
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 *获取视频集数
	 */
	public static ArrayList<VideoBean> getSmallPlaylistBeans(String playlist_id,int page){
		ArrayList<VideoBean> list=new ArrayList<VideoBean>();
		try {
			String url=HttpsUtil.readJsonFromUrl("https://openapi.youku.com/v2/playlists/videos.json?client_id="+UplayerConfig.getClientId()+"&show_id="+playlist_id
					+"&count=100&page="+page);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("videos");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				VideoBean bean=new VideoBean();
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("thumbnail"));
				bean.setName(obj.optString("title"));
				bean.setLink(obj.optString("play_link"));
				bean.setDuration(obj.optInt("duration"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setUp_count(obj.optInt("up_count"));
				bean.setPublished(obj.optString("published"));
				bean.setSeq(obj.optInt("seq"));
				/*JSONArray arr=obj.optJSONArray("streamtypes");
				List<String> types=new ArrayList<String>();
				for(int j=0;j<arr.length();j++){
					types.add(arr.opt(j).toString());
				}
				bean.setTypes(types);*/
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
  
	/*
	 *获取视频vid
	 */
	public static String getVid(String url) {
        Pattern pattern = Pattern.compile(".*/id_([A-Za-z0-9]+)\\.html.*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) {
        	return matcher.group(1);
        } 
        return null;
    }
	
	/*
	 *根据视频id获取相关视频
	 *https://openapi.youku.com/v2/videos/by_related.json?client_id=426249cfc92d3b67&video_id=cc17f1e4962411de83b1
	 */
	public static ArrayList<VideoBean> getVideoByRelate(String id){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/by_category.json?client_id="+UplayerConfig.getClientId());
			string.append("&video_id="+id);
		return JsonUtil.getVideoByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	
	/*
	 *关键词联想
	 */
	public static ArrayList<String> getKeywords(String keyword){
		ArrayList<String> list=new ArrayList<String>();
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/searches/keyword/complete.json?client_id="+UplayerConfig.getClientId());
		string.append("&keyword="+StringUtils.URLEncoder(keyword));
		try {
			String url=HttpsUtil.readJsonFromUrl(string.toString());
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("r");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				list.add(obj.optString("c"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 *关键词排行
	 */
	public static ArrayList<String> getTopKeywords(String category){
		ArrayList<String> list=new ArrayList<String>();
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/searches/keyword/top.json?client_id="+UplayerConfig.getClientId());
		string.append("&category="+StringUtils.URLEncoder(category));
		string.append("&period=today");
		try {
			String url=HttpsUtil.readJsonFromUrl(string.toString());
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("keywords");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				list.add(obj.optString("keyword"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 *关键词查找节目
	 */
	public static ArrayList<ShowBean> getShowByKeyword(String keyword,String category,int page){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/searches/show/by_keyword.json?client_id="+UplayerConfig.getClientId());
			string.append("&category="+StringUtils.URLEncoder(category));
        	string.append("&keyword="+StringUtils.URLEncoder(keyword));
        	string.append("&page="+page);
		return JsonUtil.getShowByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	
	/*
	 *根据视频获取评论
	 */
	public static ArrayList<MovieSms> getMovieSms(String video_id,int page){
		ArrayList<MovieSms> list=new ArrayList<MovieSms>();
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/comments/by_video.json?client_id="+UplayerConfig.getClientId());
		string.append("&video_id="+video_id);
		string.append("&page="+page);
		try {
			String url=HttpsUtil.readJsonFromUrl(string.toString());
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("comments");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				MovieSms sms = new MovieSms();
				sms.setContent(obj.optString("content"));
				sms.setTime(obj.optString("published"));
				sms.setUserId(obj.optJSONObject("user").optString("id"));
				sms.setUserName(obj.optJSONObject("user").optString("name"));
				list.add(sms);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*
	 *查找相关节目
	 */
	public static ArrayList<ShowBean> getShowByShow_Id(String show_id){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/shows/by_related.json?client_id="+UplayerConfig.getClientId());
			string.append("&show_id="+StringUtils.URLEncoder(show_id));
		return JsonUtil.getShowByCategory(HttpsUtil.readJsonFromUrl(string.toString()));
	}
	
	/*
	 *获取我的详细信息
	 */
	public static UserBean getUserMyInfo(String access_token){
		UserBean bean = new UserBean();
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/users/myinfo.json?client_id="+UplayerConfig.getClientId());
			string.append("&access_token="+access_token);
			JSONObject obj = null;
			try {
				obj = new JSONObject(HttpsUtil.readJsonFromUrl(string.toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return JsonUtil.getUserInfo(obj,bean);
	}
	
	/*
	 *获取户详细信息
	 */
	public static void getUserInfoByName(UserBean bean){
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/users/show.json?client_id="+UplayerConfig.getClientId());
		string.append("&user_name="+StringUtils.URLEncoder(bean.getName()));
		try {
			JSONObject obj=new JSONObject(HttpsUtil.readJsonFromUrl(string.toString()));
			JsonUtil.getUserInfo(obj,bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 *获取用户详细信息   ids已拼接好  user_ids未拼接
	 */
	public static ArrayList<UserBean> getUserInfoByIds(String... user_ids ){
		
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/users/show_batch.json?client_id="+UplayerConfig.getClientId());
		string.append("&user_ids="+StringUtils.join(user_ids, ","));
			try {
				String url=HttpsUtil.readJsonFromUrl(string.toString());
				JSONObject obj=new JSONObject(url);
				JSONArray array=obj.optJSONArray("users");
				for(int i=0;i<array.length();i++){
					obj=array.optJSONObject(i);
					UserBean bean = new UserBean();
					JsonUtil.getUserInfo(obj,bean);
					list.add(bean);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		return list;
	}
	

    /*
	 *刷新Accesstoken 
	 */
    public static boolean getAccessToken(){
		
		StringBuffer string=new StringBuffer("https://openapi.youku.com/v2/oauth2/token?client_id="+UplayerConfig.getClientId());
		string.append("&client_secret="+UplayerConfig.getClientSecret());
		string.append("&grant_type=refresh_token");
		string.append("&refresh_token="+UplayerConfig.getRefreshToken());
		
			try {
				String url=HttpsUtil.readJsonFromUrl(string.toString());
				JSONObject obj=new JSONObject(url);
				if(TextUtils.isEmpty(obj.getString("access_token"))){
					UplayerConfig.saveAccessToken(obj.getString("access_token"));
				}
				if(TextUtils.isEmpty(obj.getString("expires_in"))){
					UplayerConfig.saveAccessToken(obj.getString("expires_in"));
				}
				if(TextUtils.isEmpty(obj.getString("refresh_token"))){
					UplayerConfig.saveRefreshToken(obj.getString("refresh_token"));
				}
				if(TextUtils.isEmpty(obj.getString("token_type"))){
					UplayerConfig.saveAccessToken(obj.getString("token_type"));
				}
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
		return false;
	}
    
	
}
