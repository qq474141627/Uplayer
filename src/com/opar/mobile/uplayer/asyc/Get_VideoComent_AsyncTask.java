package com.opar.mobile.uplayer.asyc;

import java.util.ArrayList;
import java.util.List;

import com.opar.mobile.aplayer.beans.MovieSms;
import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.beans.VideoParameter;
import com.opar.mobile.aplayer.util.HandlerUtil;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;
import com.youku.login.util.Logger;

import android.os.AsyncTask;
import android.os.Handler;

public class Get_VideoComent_AsyncTask extends AsyncTask<String, Void, ArrayList<MovieSms>> {

	private boolean isNetWork;
	private Handler handler;
	private String id;
	private int page;
	public Get_VideoComent_AsyncTask(Handler handler,String id,int page) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
		this.id = id;
		this.page = page;
	}
	@Override
	protected void onPostExecute(ArrayList<MovieSms> result) {
		// TODO Auto-generated method stub
		if(!isNetWork){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NONETWORK);
			return;
		}
		if(result == null){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NO_DATA_RETURN);
		}else if(result.size()!=0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.EXEC_NORMOL_COMMENT,result);
			//获取相关头像
			new Get_UserInfoByIds_AsyncTask(handler).execute(joinIds(result, ","));
		}else if(result.size()==0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.DATA_RETURN_ZERO);
		}
	}

	@Override
	protected void onCancelled() {}
	
	protected ArrayList<MovieSms> doInBackground(String... params){
		if (isNetWork) {
			ArrayList<MovieSms> arraylist =  XmlUtil.getMovieSms(id,page);
			return arraylist;
		}
		return null;
	}
	
	/**
	 * 拼接数组
	 */
	public String joinIds(final List<MovieSms> list,final String separator) {
		StringBuffer result = new StringBuffer();
		if (list != null && list.size()> 0) {
			for (MovieSms str : list) {
				result.append(str.getUserId());
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}
	
}		