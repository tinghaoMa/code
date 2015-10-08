package com.mth.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mth.adapter.ImageAdapter;
import com.mth.bean.FolderBean;
import com.mth.popwindow.ListImgDirPopWindow;
import com.mth.popwindow.ListImgDirPopWindow.OnDirSelectedListener;

public class MainActivity extends Activity {

	private static final String TAG = "MTH";

	private GridView mGridView;
	private RelativeLayout mBotoomLy;
	private TextView mDirName;
	private TextView mDirCount;
	private ProgressDialog mProgressDialog;
	private ImageAdapter imageAdapter;

	private List<String> mImgs;
	private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();
	private File mCurrentDir;
	private int mMaxCount;

	private ListImgDirPopWindow mDirPopWindow;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			data2View();
			initDirPopWindow();
		}

	};

	private void initDirPopWindow() {
		mDirPopWindow = new ListImgDirPopWindow(this, mFolderBeans);
		mDirPopWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 变亮
				lightOn();
			}
		});
		mDirPopWindow.setOnDirSelectedListener(new OnDirSelectedListener() {

			@Override
			public void OnSelected(FolderBean bean) {
				mCurrentDir = new File(bean.getDir());
				mImgs = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String filename) {
						if (filename.endsWith(".jpg")
								|| filename.endsWith(".png")
								|| filename.endsWith(".jpeg"))
							return true;
						return false;
					}
				}));
				imageAdapter = new ImageAdapter(MainActivity.this, mImgs,
						mCurrentDir.getAbsolutePath());
				mGridView.setAdapter(imageAdapter);
				mDirCount.setText(mImgs.size() + "");
				mDirName.setText(bean.getName());
				
				mDirPopWindow.dismiss();
			}
		});
	}

	protected void lightOn() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
	}

	protected void lightOff() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
	}

	private void data2View() {
		if (mCurrentDir == null) {
			Toast.makeText(this, "为扫到任何图片", Toast.LENGTH_LONG).show();
		}
		mImgs = Arrays.asList(mCurrentDir.list());
		imageAdapter = new ImageAdapter(this, mImgs,
				mCurrentDir.getAbsolutePath());

		mGridView.setAdapter(imageAdapter);
		mDirCount.setText(mMaxCount + "");
		mDirName.setText(mCurrentDir.getName());
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
		initDatas();
		initEvent();
	}

	private void initViews() {
		mGridView = (GridView) findViewById(R.id.id_gridView);
		mBotoomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		mDirName = (TextView) findViewById(R.id.id_dir_name);
		mDirCount = (TextView) findViewById(R.id.id_dir_count);
	}

	/**
	 * 扫描本地图片
	 */
	private void initDatas() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "当前存储卡不可用!", Toast.LENGTH_LONG).show();
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		new Thread() {
			public void run() {
				Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = MainActivity.this.getContentResolver();
				Cursor cursor = cr.query(mImgUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
				Set<String> mDirPath = new HashSet<String>();
				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					System.out.println(path);
					File parentFile = new File(path).getParentFile();
					if (parentFile == null) {
						continue;
					}

					String dirPath = parentFile.getAbsolutePath();
					FolderBean bean = null;
					if (mDirPath.contains(dirPath)) {
						continue;
					} else {
						mDirPath.add(dirPath);
						bean = new FolderBean();
						bean.setDir(dirPath);
						bean.setFirstImgPath(path);
					}
					if (parentFile.list() == null) {
						continue;
					}

					int picSize = parentFile.list(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					}).length;
					bean.setCount(picSize);
					mFolderBeans.add(bean);
					if (picSize > mMaxCount) {
						mMaxCount = picSize;
						mCurrentDir = parentFile;
					}
				}
				if (cursor != null)
					cursor.close();
				mHandler.sendEmptyMessage(0X110);
			};
		}.start();
	}

	private void initEvent() {
		mBotoomLy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDirPopWindow.showAsDropDown(mBotoomLy, 0, 0);
				lightOff();
			}
		});
	}

}
