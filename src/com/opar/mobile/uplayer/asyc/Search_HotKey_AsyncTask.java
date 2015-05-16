package com.opar.mobile.uplayer.asyc;

import java.util.ArrayList;

import com.opar.mobile.aplayer.util.HandlerUtil;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;

import android.os.AsyncTask;
import android.os.Handler;

public class Search_HotKey_AsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

	private boolean isNetWork;
	private Handler handler;
	public Search_HotKey_AsyncTask(Handler handler) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
	}
	@Override
	protected void onPostExecute(ArrayList<String> result) {
		// TODO Auto-generated method stub
		if(!isNetWork){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NONETWORK);
			return;
		}
		if(result == null){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.NO_DATA_RETURN);
		}else if(result.size()!=0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.EXEC_NORMOL_HOTKEY,result);
		}else if(result.size()==0){
			HandlerUtil.sendMsgToHandler(handler, UplayerConfig.DATA_RETURN_ZERO);
		}
	}

	@Override
	protected void onCancelled() {}
	
	protected ArrayList<String> doInBackground(String... params){
		if (isNetWork) {
			ArrayList<String> arraylist=XmlUtil.getTopKeywords(params[0]);
			return arraylist;
		}
		return null;
	}
}		