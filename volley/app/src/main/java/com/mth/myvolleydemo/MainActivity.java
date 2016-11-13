package com.mth.myvolleydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
    private RequestQueue mRequestQueue;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(this);
        mImageView = (ImageView) findViewById(R.id.iv);
        stringRequest();
        imageRequest();
    }


    private void imageRequest() {
        ImageRequest imageRequest = new ImageRequest(
                "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size" +
                        "=b4000_4000&sec=1478994805&di=ea6830d3e3e6c4356318dc68e6ba5a13&src=http" +
                        "://b.hiphotos.baidu" +
                        ".com/image/pic/item/d009b3de9c82d15825ffd75c840a19d8bd3e42da.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        System.out.println("segg6575----MainActivity.onResponse 111111");
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("segg6575----MainActivity.onErrorResponse 222222222222");
                mImageView.setImageResource(R.mipmap.ic_launcher);
            }
        });

        mRequestQueue.add(imageRequest);

    }


    public void stringRequest() {
        StringRequest stringRequest = new StringRequest("http://qf.56.com/home/v4/moreAnchor" +
                ".union.do?index=0&size=20&ts=1478994946437&type=0&signature" +
                "=ef6214ede084035fc20f26c0ac12d7a3",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("segg6575----response = " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("segg6575----error.toString() = " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
    }

}
