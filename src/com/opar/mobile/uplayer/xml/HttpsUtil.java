package com.opar.mobile.uplayer.xml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.youku.login.util.Logger;

public class HttpsUtil {
	public static  String readJsonFromUrl(String path) { 
		Logger.d("url--"+path);
		String result = "";
		try{
			URL url = new URL(path);
			SSLContext sslctxt = SSLContext.getInstance("TLS");
			sslctxt.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(sslctxt.getSocketFactory());
			conn.setHostnameVerifier(new MyHostnameVerifier());
			//conn.setRequestMethod("POST"); 
			conn.setRequestProperty("Charset", "UTF-8");
			//conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setConnectTimeout(10* 1000);
			int respCode = conn.getResponseCode();
			Logger.d("respCode --"+respCode);
			if(respCode==200){
	             BufferedReader bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
	             String inputLine  = "";  
	             while((inputLine = bufferReader.readLine()) != null){  
	            	 result += inputLine + "\n"; 
	            	 Logger.d("inputLine--"+inputLine);
	             }  
			}
			conn.disconnect();
			}catch(Exception e){
				e.printStackTrace();
				Logger.d("e--"+e.getMessage());
			}
		Logger.d("result--"+result);
		Logger.d("length"+result.length());
		  return result;
	      } 
	
	
	private static String ConvertStream2Json(InputStream inputStream)
    {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }

	static class MyX509TrustManager implements X509TrustManager{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
	}
	
	static class MyHostnameVerifier implements HostnameVerifier{
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
		
	}
}
