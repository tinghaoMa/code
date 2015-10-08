package com.mth.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mth.imageloader.ImageLoader;
import com.mth.imageloader.ImageLoader.TYPE;
import com.mth.test.R;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static Set<String> mSelectedImg = new HashSet<String>();
	private String mDirPath;
	private List<String> mDatas;
	private LayoutInflater mInflater;

	public ImageAdapter(Context context, List<String> datas, String dirPath) {
		this.mDirPath = dirPath;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder mHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_gridview, parent,
					false);
			mHolder = new ViewHolder();
			mHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_item_image_gridview);
			mHolder.mSelect = (ImageButton) convertView
					.findViewById(R.id.id_item_select);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		// 重置状态
		mHolder.mImg.setImageResource(R.drawable.pictures_no);
		mHolder.mSelect.setImageResource(R.drawable.picture_unselected);
		final String path = mDirPath + "/" + mDatas.get(position);
		ImageLoader.getInstance(3, TYPE.LIFO).loadImage(path, mHolder.mImg);

		mHolder.mSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSelectedImg.contains(path)) {
					mHolder.mImg.setColorFilter(null);
					mSelectedImg.remove(path);
					mHolder.mSelect
							.setImageResource(R.drawable.picture_unselected);
				} else {
					mSelectedImg.add(path);
					mHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
					mHolder.mSelect
							.setImageResource(R.drawable.pictures_selected);
				}
			}
		});

		if (mSelectedImg.contains(path)) {
			mHolder.mImg.setColorFilter(Color.parseColor("#77000000"));
			mHolder.mSelect.setImageResource(R.drawable.pictures_selected);
		}

		return convertView;
	}

	private class ViewHolder {
		public ImageView mImg;
		public ImageButton mSelect;
	}
}
