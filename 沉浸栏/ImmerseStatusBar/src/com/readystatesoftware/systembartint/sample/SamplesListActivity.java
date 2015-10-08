/*
 * Copyright (C) 2013 readyState Software Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.readystatesoftware.systembartint.sample;

import static android.content.Intent.ACTION_MAIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 *
 * 来自网上开源项目 https://github.com/hexiaochun/SystemBarTint
 *
 */
public class SamplesListActivity extends ListActivity {

	private final IntentAdapter mAdapter = new IntentAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(mAdapter);
		mAdapter.refresh();

		//布局内容会从actionbar以下开始
		findViewById(android.R.id.list).setFitsSystemWindows(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    //状态栏透明 需要在创建SystemBarTintManager 之前调用。
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        //使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
        tintManager.setStatusBarTintResource(R.color.actionbar_bg);
        // 设置状态栏的文字颜色
        tintManager.setStatusBarDarkMode(false, this);
	}

	@TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		startActivity(mAdapter.getItem(position));
	}

	private class IntentAdapter extends BaseAdapter {
		private final List<CharSequence> mNames;
		private final Map<CharSequence, Intent> mIntents;

		IntentAdapter() {
			mNames = new ArrayList<CharSequence>();
			mIntents = new HashMap<CharSequence, Intent>();
		}

		void refresh() {
			mNames.clear();
			mIntents.clear();

			final Intent mainIntent = new Intent(ACTION_MAIN, null);
			mainIntent.addCategory("com.readystatesoftware.systembartint.SAMPLE");

			PackageManager pm = getPackageManager();
			final List<ResolveInfo> matches = pm.queryIntentActivities(
					mainIntent, 0);
			for (ResolveInfo match : matches) {
				Intent intent = new Intent();
				intent.setClassName(match.activityInfo.packageName,
						match.activityInfo.name);
				final CharSequence name = match.loadLabel(pm);
				mNames.add(name);
				mIntents.put(name, intent);
			}

			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mNames.size();
		}

		@Override
		public Intent getItem(int position) {
			return mIntents.get(mNames.get(position));
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (convertView == null) {
				tv = (TextView) LayoutInflater.from(SamplesListActivity.this)
						.inflate(android.R.layout.simple_list_item_1, parent,
								false);
			}
			tv.setText(mNames.get(position));
			return tv;
		}
	}

}
