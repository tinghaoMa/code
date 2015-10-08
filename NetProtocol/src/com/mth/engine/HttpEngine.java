package com.mth.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;

import android.util.Log;

import com.mth.protocol.BaseProtocol;

public class HttpEngine {
	private static HttpEngine mInstance;

	private HttpEngine() {
	}

	public static HttpEngine getInstance() {
		if (mInstance == null) {
			synchronized (HttpEngine.class) {
				if (mInstance == null) {
					mInstance = new HttpEngine();
				}
			}
		}
		return mInstance;
	}

	public <T> int excuteGet(BaseProtocol<T> protocol) {
		try {
			String netAddaress = protocol.makeRequest();
			URL url = new URL(netAddaress);
			URLConnection rulConnection = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
			httpUrlConnection.setConnectTimeout(5 * 1000);
			httpUrlConnection.setRequestMethod("GET");
			// 根据ResponseCode判断连接是否成功
			int responseCode = httpUrlConnection.getResponseCode();
			T t = protocol.handResponse("成功");
			protocol.setResult(t);
			return responseCode;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("mth", e.toString());
		}
		return -1;
	}
}
