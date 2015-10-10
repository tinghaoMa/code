package com.mth.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButton = (Button) findViewById(R.id.id_bt);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, BActivity.class);
                User mUser = new User("mth1111111111111", 21111, "DL");
                mIntent.putExtra("user", mUser);
                startActivity(mIntent);
            }
        });
    }

}
