package com.opar.mobile.uplayer.asyc;

import java.util.ArrayList;

import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.util.HandlerUtil;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;

import android.os.AsyncTask;
import android.os.Handler;

public class Search_Show_AsyncTask extends AsyncTask<String, Void, ArrayList<ShowBean>> {

	private boolean isNetWork;
	private Handler handler;
	private int page ;
	private String key,category;
	public Search_Show_AsyncTask(Handler handler,String key,String category,int page) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
		this.key = key;
		this.category = category;
		this.page = page;
	}
	@Override
	protected void onPostExecute(ArrayList<ShowBean> result) {
		// TODO Auto-generated method stub
		if(!isNetWork){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NONETWORK);
			return;
		}
		if(result == null){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NO_DATA_RETURN);
		}else if(result.size()!=0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.EXEC_NORMOL,result);
		}else if(result.size()==0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.DATA_RETURN_ZERO);
		}
	}

	@Override
	protected void onCancelled() {}
	
	protected ArrayList<ShowBean> doInBackground(String... params){
		if (isNetWork) {
			ArrayList<ShowBean> arraylist = XmlUtil.getShowByKeyword(key,category, page);
			return arraylist;
		}
		return null;
	}
}		