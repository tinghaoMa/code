package com.mth.net;

import com.mth.protocol.BaiduProtocl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button mConnBt;
	private TextView mTvResult;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
		setListeners();
	}

	private void setListeners() {
		mConnBt.setOnClickListener(this);
	}

	private void setUpViews() {
		pb = (ProgressBar) findViewById(R.id.pb);
		mConnBt = (Button) findViewById(R.id.bt_conn);
		mTvResult = (TextView) findViewById(R.id.tv_result);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_conn:
			conn_net();
			break;
		}
	}

	private void conn_net() {
		showProgressBar();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				BaiduProtocl baidu = new BaiduProtocl();
				final String result = baidu.request();
				runOnUiThread(new Runnable() {
					public void run() {
						mTvResult.setText(result);
						hideProgressBar();
					}

				});
			};
		}.start();

	}

	protected void showProgressBar() {
		pb.setVisibility(View.VISIBLE);
		mTvResult.setVisibility(View.GONE);
	}

	private void hideProgressBar() {
		pb.setVisibility(View.GONE);
		mTvResult.setVisibility(View.VISIBLE);
	}
}
