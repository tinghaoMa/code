package com.mth.popwindow;

import java.util.List;

import com.mth.bean.FolderBean;
import com.mth.imageloader.ImageLoader;
import com.mth.test.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ListImgDirPopWindow extends PopupWindow {
	private int mWidth;
	private int mHeight;
	private View mConvertView;
	private ListView mListView;
	private List<FolderBean> mDatas;

	public ListImgDirPopWindow(Context context, List<FolderBean> mDatas) {
		this.mDatas = mDatas;
		calWidthAndHeight(context);
		mConvertView = LayoutInflater.from(context).inflate(
				R.layout.popup_main, null);
		setContentView(mConvertView);
		setWidth(mWidth);
		setHeight(mHeight);

		setTouchable(true);
		setFocusable(true);
		setOutsideTouchable(true);

		setBackgroundDrawable(new BitmapDrawable());
		setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});

		initViews(mConvertView, context);
		initEvents();
	}

	private void initViews(View mConvertView, Context context) {
		mListView = (ListView) mConvertView.findViewById(R.id.id_list_dir);
		mListView.setAdapter(new ListDirAdapter(context, mDatas));
	}

	public interface OnDirSelectedListener {
		void OnSelected(FolderBean bean);
	}

	public OnDirSelectedListener mListener;

	public void setOnDirSelectedListener(OnDirSelectedListener mListener) {
		this.mListener = mListener;
	}

	private void initEvents() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListener.OnSelected(mDatas.get(position));
			}
		});
	}

	private class ListDirAdapter extends ArrayAdapter<FolderBean> {
		private LayoutInflater mInflater;

		public ListDirAdapter(Context context, List<FolderBean> objects) {
			super(context, 0, objects);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder = null;
			if (convertView == null) {
				mHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_popup_main,
						parent, false);
				mHolder.mImg = (ImageView) convertView
						.findViewById(R.id.id_id_dir_item_image);
				mHolder.mDirCount = (TextView) convertView
						.findViewById(R.id.id_dir_item_count);
				mHolder.mDirName = (TextView) convertView
						.findViewById(R.id.id_dir_item_name);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}
			mHolder.mImg.setImageResource(R.drawable.pictures_no);
			FolderBean bean = getItem(position);
			ImageLoader.getInstance().loadImage(bean.getFirstImgPath(),
					mHolder.mImg);

			mHolder.mDirCount.setText(bean.getCount() + "");
			mHolder.mDirName.setText(bean.getName());
			return convertView;
		}
	}

	private class ViewHolder {
		ImageView mImg;
		TextView mDirName;
		TextView mDirCount;
	}

	/**
	 * 计算PopWindow高度
	 * 
	 * @param context
	 */
	private void calWidthAndHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mWidth = outMetrics.widthPixels;
		mHeight = (int) (outMetrics.heightPixels * 0.8);
	}

}
