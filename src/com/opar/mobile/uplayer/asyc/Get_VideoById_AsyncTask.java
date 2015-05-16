package com.opar.mobile.uplayer.asyc;

import java.util.ArrayList;

import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.beans.VideoBean;
import com.opar.mobile.aplayer.beans.VideoParameter;
import com.opar.mobile.aplayer.util.HandlerUtil;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;

import android.os.AsyncTask;
import android.os.Handler;

public class Get_VideoById_AsyncTask extends AsyncTask<String, Void, ArrayList<VideoBean>> {

	private boolean isNetWork;
	private Handler handler;
	private String id;
	private int page;
	public Get_VideoById_AsyncTask(Handler handler,String id,int page) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
		this.id = id;
		this.page = page;
	}
	@Override
	protected void onPostExecute(ArrayList<VideoBean> result) {
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
	
	protected ArrayList<VideoBean> doInBackground(String... params){
		if (isNetWork) {
			ArrayList<VideoBean> arraylist = XmlUtil.getVideoBeans(id,page);
			return arraylist;
		}
		return null;
	}
}		