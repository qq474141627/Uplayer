package com.opar.mobile.uplayer.asyc;

import java.util.List;

import com.opar.mobile.uplayer.beans.ShowBean;
import com.opar.mobile.uplayer.util.HandlerUtil;
import com.opar.mobile.uplayer.util.UplayerConfig;
import com.opar.mobile.uplayer.xml.XmlUtil;

import android.os.AsyncTask;
import android.os.Handler;

public class Get_ShowByIds_AsyncTask extends AsyncTask<String, Void, List<ShowBean>> {

	private boolean isNetWork;
	private Handler handler;
	private List<String> list;
	public Get_ShowByIds_AsyncTask(Handler handler,List<String> list) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.list = list;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
	}
	@Override
	protected void onPostExecute(List<ShowBean> result) {
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
	
	protected List<ShowBean> doInBackground(String... params){
		if (isNetWork) {
			return XmlUtil.getShowByIds(list);
		}
		return null;
	}
}		