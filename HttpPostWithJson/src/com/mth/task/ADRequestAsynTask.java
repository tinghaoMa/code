package com.mth.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.example.httppost.MainActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * �ƹ�����Task
 * 
 * @author tinghaoma
 *
 */
public class ADRequestAsynTask extends AsyncTask<String, Void, String> {
	public static final String TAG = "MTH";
	private MainActivity activity;

	public ADRequestAsynTask(Context con) {
		if (con instanceof MainActivity) {
			activity = (MainActivity) con;
		}
	}

	@Override
	protected String doInBackground(String... params) {
		URL url;
		try {
			url = new URL(params[0]);
			URLConnection rulConnection = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
			httpUrlConnection.setRequestMethod("POST");// �ύģʽ
			httpUrlConnection.setConnectTimeout(30000);
			httpUrlConnection.setReadTimeout(30000);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			PrintWriter printWriter = new PrintWriter(
					httpUrlConnection.getOutputStream());
			// �����������
			// flush������Ļ���
			printWriter.write(params[1]);
			printWriter.flush();
			// ����ResponseCode�ж������Ƿ�ɹ�
			int responseCode = httpUrlConnection.getResponseCode();
			if (responseCode == 200) {
				InputStream connResponseInputStream = httpUrlConnection
						.getInputStream();
				int i = -1;
				byte[] buff = new byte[1024];
				StringBuffer mBuff = new StringBuffer();
				while ((i = connResponseInputStream.read(buff)) != -1) {
					mBuff.append(new String(buff, 0, i));
				}
				Log.e(TAG, "Post Success!---------");
				return mBuff.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// �жϽ��
		if (TextUtils.isEmpty(result)) {
			Log.e(TAG, "Post Fail onPostExecute!---------" + result);
		} else {
			Log.e(TAG, "Post Success onPostExecute!---------" + result);
			activity.dealResult(result);
		}
	}
}
