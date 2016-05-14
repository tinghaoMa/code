package sohuvideo.activity_client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.client_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent mIntent = new Intent();
//                mIntent.setClassName("sohuvideo.activity_server", "sohuvideo.activity_server.MainActivity");
//                startActivity(mIntent);


                String url = "mth://action.cmd?name=%s";
                Uri uri = Uri.parse(String.format(url, "mth ftom client"));
                Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);

                if (isIntentAvailable(MainActivity.this, mIntent)){
                    startActivity(mIntent);
                }else{
                    Log.e("MTH", "no handle activity");
                }

            }
        });
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
