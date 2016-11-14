package com.mth.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * http://blog.csdn.net/thm521888/article/details/53155697
 */
public class MainActivity extends Activity {
    private static final String LOGTAG = "MainActivity";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView myWebView = (WebView) findViewById(R.id.myWebView);
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new JsInteration(), "control");// js中调用方法需写为window.control

        myWebView.setWebChromeClient(new WebChromeClient() {});
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                testMethod(myWebView);
            }

        });
        myWebView.loadUrl("file:///android_asset/js_java_interaction.html");
    }

    private void testMethod(WebView webView) {
        String call = "javascript:sayHello()";

//        call = "javascript:alertMessage(\"" + "content" + "\")";
//
        call = "javascript:toastMessage(\"" + "content" + "\")";
//
//        call = "javascript:sumToJava(1,2)";
        webView.loadUrl(call);

    }

    public class JsInteration {

        @JavascriptInterface
        public void toastMessage(String message) {
            System.out.println("segg6575---JsInteration.toastMessage");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void onSumResult(int result) {
            System.out.println("segg6575--onSumResult-result = " + result);
        }
    }

}