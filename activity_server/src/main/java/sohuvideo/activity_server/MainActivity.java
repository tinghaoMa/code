package sohuvideo.activity_server;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.getAction() == Intent.ACTION_VIEW && "mth".equals(intent.getScheme())) {
            String data = intent.getDataString();
            Uri uri = Uri.parse(data);
            String scheme = uri.getScheme();
            Log.e("MTH", "scheme----=" + scheme);
            String name = uri.getQueryParameter("name");
            Log.e("MTH", "name----=" + name);
        }
    }
}
