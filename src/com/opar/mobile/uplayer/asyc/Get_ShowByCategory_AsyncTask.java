package com.opar.mobile.uplayer.asyc;

import java.util.List;

import com.opar.mobile.aplayer.beans.Parameter;
import com.opar.mobile.aplayer.beans.ShowBean;
import com.opar.mobile.aplayer.util.HandlerUtil;
import com.opar.mobile.aplayer.util.UplayerConfig;
import com.opar.mobile.aplayer.xml.XmlUtil;

import android.os.AsyncTask;
import android.os.Handler;

public class Get_ShowByCategory_AsyncTask extends AsyncTask<String, Void, List<ShowBean>> {

	private boolean isNetWork;
	private Handler handler;
	private Parameter parameter;
	public Get_ShowByCategory_AsyncTask(Handler handler,Parameter parameter) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		if(UplayerConfig.hasInternet()){
			isNetWork=true;
		}
		this.parameter = parameter;
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
			return XmlUtil.getShowByIds(XmlUtil.getShowIdsByCategory(parameter));
		}
		return null;
	}
}		