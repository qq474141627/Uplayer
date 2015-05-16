package com.opar.mobile.aplayer.xml;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.opar.mobile.aplayer.beans.PlaylistBean;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.beans.UserBean;
import com.opar.mobile.aplayer.beans.VideoBean;

public class JsonUtil {
	
	/*
	 *解析节目视频
	 */
	public static ArrayList<ShowBean> getShowByCategory(String json){
		ArrayList<ShowBean> list = new ArrayList<ShowBean>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array=obj.optJSONArray("shows");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				ShowBean bean=new ShowBean();
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("poster"));
				bean.setName(obj.optString("name"));
				bean.setLink(obj.optString("link"));
				bean.setEpisode_count(obj.optInt("episode_count"));
				bean.setEpisode_updated(obj.optInt("episode_updated"));
				bean.setCategory(obj.optString("category"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setPublished(obj.optString("published"));
				bean.setScore(obj.optDouble("score"));
				bean.setFavorite_count(obj.optInt("favorite_count"));
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
	 *解析专辑视频
	 */
	public static ArrayList<PlaylistBean> getPlaylistByCategory(String json){
		ArrayList<PlaylistBean> list = new ArrayList<PlaylistBean>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array=obj.optJSONArray("playlists");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				PlaylistBean bean=new PlaylistBean();
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("thumbnail"));
				bean.setName(obj.optString("name"));
				bean.setLink(obj.optString("link"));
				bean.setVideo_count(obj.optInt("video_count"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setPublished(obj.optString("published"));
				bean.setDuration(obj.optInt("duration"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 *解析专辑视频
	 */
	public static ArrayList<VideoBean> getVideoByCategory(String json){
		ArrayList<VideoBean> list = new ArrayList<VideoBean>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray array=obj.optJSONArray("videos");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				VideoBean bean=new VideoBean();
				bean.setId(obj.optString("id"));
				bean.setThumbnail(obj.optString("bigThumbnail"));
				bean.setName(obj.optString("title"));
				bean.setLink(obj.optString("link"));
				bean.setView_count(obj.optInt("view_count"));
				bean.setPublished(obj.optString("published"));
				bean.setDuration(obj.optInt("duration"));
				bean.setFavorite_count(obj.optInt("favorite_count"));
				bean.setComment_count(obj.optInt("comment_count"));
				bean.setUp_count(obj.optInt("up_count"));
				bean.setDown_count(obj.optInt("down_count"));
				list.add(bean);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 *获取用户详细信息
	 */
	public static UserBean getUserInfo(JSONObject obj,UserBean bean){
		if(obj == null || bean == null)
			return bean;
				bean.setId(obj.optString("id"));
				bean.setName(obj.optString("name"));
				bean.setAvatar(obj.optString("avatar"));
				bean.setAvatar_large(obj.optString("avatar_large"));
				bean.setDescription(obj.optString("description"));
				bean.setFavorites_count(obj.optInt("favorites_count"));
				bean.setFollowers_count(obj.optInt("followers_count"));
				bean.setFollowing_count(obj.optInt("following_count"));
				bean.setGender(obj.optString("gender"));
				bean.setLink(obj.optString("link"));
				bean.setPlaylists_count(obj.optInt("playlists_count"));
				bean.setRegist_time(obj.optString("regist_time"));
				bean.setStatuses_count(obj.optInt("statuses_count"));
				bean.setSubscribe_count(obj.optInt("subscribe_count"));
				bean.setVideos_count(obj.optInt("videos_count"));
				bean.setVv_count(obj.optInt("vv_count"));
				Log.i("tag", "======getUserInfo getAvatar_large="+bean.getAvatar_large());
		return bean;
	}

}
