package com.mth.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView mTv = new TextView(this);
        Intent intent = getIntent();
        User mUser = intent.getParcelableExtra("user");
        mTv.setText(mUser.getName() + "----" + mUser.getAge()+"---"+mUser.getLocation()+"book--"+mUser.getBook().getName());
        setContentView(mTv);
    }

}
