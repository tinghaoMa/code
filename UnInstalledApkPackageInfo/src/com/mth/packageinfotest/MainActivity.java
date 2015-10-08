package com.mth.packageinfotest;

import java.io.File;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private String spreadPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 下载的文件路径
		// /storage/emulated/0/Android/data/com.mth.packageinfotest/files/spreadApkFile
		File file = getExternalFilesDir("spreadApkFile");
		if (file.exists()) {
			spreadPath = file.getAbsolutePath();
			Log.e(TAG, file.getAbsolutePath());
		}
		getUnstalledApkInfo();
	}

	/**
	 * 获得该目录下所有的未安装的app的版本号和包名
	 */
	// TODO 文件没有下载完毕的情况
	private void getUnstalledApkInfo() {
		File rootFile = getExternalFilesDir("spreadApkFile");
		if (rootFile.exists()) {
			// 遍历文件 获取文件的名字
			File[] files = rootFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				String name = file.getName();
				// 拼接地址
				String path = spreadPath + "/" + name;
				PackageManager pm = getPackageManager();
				PackageInfo info = pm.getPackageArchiveInfo(path,
						PackageManager.GET_ACTIVITIES);
				if (info != null) {
					ApplicationInfo appInfo = info.applicationInfo;
					String appName = pm.getApplicationLabel(appInfo).toString();// 得到名字
					String packageName = appInfo.packageName; // 得到包名称
					String version = info.versionName; // 得到版本信息
					Log.e(TAG, "appName---------" + appName);
					Log.e(TAG, "packageName:-------------" + packageName);
					Log.e(TAG, "version-------" + version);
					Log.e(TAG, "************第" + (i + 1) + "个**************");
				}
			}
		}
	}
}
