package com.example.httppost;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mth.task.ADRequestAsynTask;

public class MainActivity extends Activity implements OnClickListener {

    private static final boolean TEST = false; // true 正式 false 测试
    public static final String TAG = "MTH";
    private static String URL_POST;
    private Button mPostBt;
    private boolean isRunning = true;
    private int requestCount = 0;

    static {
        if (TEST) {// 正式
            URL_POST = "http://api.tv.sohu.com/mobile_user/version/replacedownload.json";
        } else {// 测试
            URL_POST = "http://dev.app.yule.sohu.com/open_tv/mobile_user/version/replacedownload.json";
        }
    }

    public void init(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("SOHUVIDEO_CHANNEL");
            Log.e("MTH", "SOHUVIDEO_CHANNEL---is--" + channel);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        setListener();
        init(this);
    }

    /**
     * 获取参数UrlConnection
     *
     * @return
     */
    private String getUrlConnectionParams() {
        Map<String, Object> requestParamsMap = new HashMap<String, Object>();
        requestParamsMap.put("api_key", "d2965a1d8761bf484739f14c0bc299d6");
        requestParamsMap.put("sver", "4.1.0");
        requestParamsMap.put("poid", "16");
        requestParamsMap.put("sysver", "5.1.1");
        requestParamsMap.put("partner", "130000");
        requestParamsMap.put("uid", "e8cd5a91dcc093c8e8b0efc6b15442b5");
        requestParamsMap.put("plat", "6");
        requestParamsMap.put("apps", getAppJsonInfo());
        StringBuffer params = new StringBuffer();
        // 组织请求参数
        Iterator it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString();

    }

    /**
     * 获取参数HttpPost
     *
     * @return
     */
    private List<NameValuePair> getParmas() {
        // 初始化数据
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("api_key", "d2965a1d8761bf484739f14c0bc299d6"));
        requestParams.add(new BasicNameValuePair("sver", "4.1.0"));
        requestParams.add(new BasicNameValuePair("poid", "16"));
        requestParams.add(new BasicNameValuePair("sysver", "5.1.1"));
        requestParams.add(new BasicNameValuePair("partner", "130000"));
        requestParams.add(new BasicNameValuePair("uid", "e8cd5a91dcc093c8e8b0efc6b15442b5"));
        requestParams.add(new BasicNameValuePair("plat", "0"));
        String appInfo = getAppJsonInfo();
        // Log.e(TAG, "拼接完的JSON字符串是------->" + appInfo);
        requestParams.add(new BasicNameValuePair("apps", appInfo));
        return requestParams;
    }

    /**
     * 获取所有应用的信息
     *
     * @return
     */
    private String getAppJsonInfo() {
        // 初始化 json参数 拼接字符串
        ArrayList<Bean> mListInstalled = new ArrayList<MainActivity.Bean>();
        mListInstalled.add(new Bean("com.yiihua.texas.SOHUWAN", 41));
        ArrayList<Bean> mListDownloaded = new ArrayList<MainActivity.Bean>();
        mListDownloaded.add(new Bean("com.sohu.sohunews", 7));
        // 最外层的JSONObject
        JSONObject mObj = new JSONObject();
        // 里层集合
        JSONArray jArray = new JSONArray();
        try {
            mObj.put("download_size", "189623");
            // 初始化已经安装的
            for (int i = 0; i < mListInstalled.size(); i++) {
                JSONObject jsonInstall = new JSONObject();
                Bean bean = mListInstalled.get(i);
                jsonInstall.put("package_name", bean.package_name);
                jsonInstall.put("version", bean.version);
                jArray.put(jsonInstall);
            }
            mObj.put("installed", jArray);

            // 初始化已经下载的
            jArray = new JSONArray();
            for (int i = 0; i < mListDownloaded.size(); i++) {
                JSONObject jsonDown = new JSONObject();
                Bean bean = mListDownloaded.get(i);
                jsonDown.put("package_name", bean.package_name);
                jsonDown.put("version", bean.version);
                jArray.put(jsonDown);
            }
            mObj.put("downloaded", jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String appInfo = mObj.toString();
        return appInfo;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "post request", Toast.LENGTH_SHORT).show();
        // requestHttpPost();
        requestHttpURLConnection();
    }

    private void requestHttpURLConnection() {
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    requestCount++;
                    ADRequestAsynTask mTask = new ADRequestAsynTask(MainActivity.this);
                    mTask.execute(URL_POST, getUrlConnectionParams());
                    Log.e(TAG, "目前的请求次数是------->" + requestCount);
                    try {
                        sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    /**
     * HttpPost
     */
    private void requestHttpPost() {
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    postRequestWithJson();
                    requestCount++;
                    try {
                        sleep(3000);
                        Log.e(TAG, "目前的请求次数是------->" + requestCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    /**
     * HttpURLConnection
     */
    protected void postUrlConnectionRequestWithJson() {
        URL url;
        try {
            url = new URL(URL_POST);
            URLConnection rulConnection = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.setRequestMethod("POST");// 提交模式
            httpUrlConnection.setConnectTimeout(30000);
            httpUrlConnection.setReadTimeout(30000);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            PrintWriter printWriter = new PrintWriter(httpUrlConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(getUrlConnectionParams());
            // flush输出流的缓冲
            printWriter.flush();
            // 根据ResponseCode判断连接是否成功
            int responseCode = httpUrlConnection.getResponseCode();
            if (responseCode != 200) {
                Log.e(TAG, "Post Fail!---------" + responseCode);
            } else {
                InputStream connResponseInputStream = httpUrlConnection.getInputStream();
                int i = -1;
                byte[] buff = new byte[1024];
                StringBuffer mBuff = new StringBuffer();
                while ((i = connResponseInputStream.read(buff)) != -1) {
                    mBuff.append(new String(buff, 0, i));
                }
                Log.e(TAG, "Post Success!--responseCode=" + responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * HttpClient方式请求
     */
    private void postRequestWithJson() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL_POST);

        HttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(getParmas(), HTTP.UTF_8));
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Log.e(TAG, "请求状态码------->" + response.getStatusLine().getStatusCode());
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity);// 取出应答字符串
                Log.e(TAG, "请求结果---------------------->" + result);
                dealResult(result);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dealResult(String result) {
        JSONObject resultObj = null;
        try {
            resultObj = new JSONObject(result);
            JSONArray jsonArray = resultObj.optJSONArray("data");
            if (jsonArray.length() > 0) {
                isRunning = false;
                Log.e(TAG, "成功进入");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class Bean implements Serializable {
        private static final long serialVersionUID = -5444501040927429302L;
        public String package_name;
        public int version;

        public Bean() {
            super();
        }

        public Bean(String package_name, int version) {
            this.package_name = package_name;
            this.version = version;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }

    private void setListener() {
        mPostBt.setOnClickListener(this);
    }

    private void setUpView() {
        mPostBt = (Button) findViewById(R.id.bt_post);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
