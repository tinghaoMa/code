package com.example.itck_mth.recyclerviewdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class NormalAdapter<T> extends RecyclerView.Adapter<NormalAdapter.VH> {

    private List<T> mList;

    public NormalAdapter(List<T> list) {
        mList = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VH.get(parent, getItemView(viewType));
    }

    protected abstract int getItemView(int viewtype);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        initData(holder, position, mList.get(position));
    }


    protected abstract void initData(VH holder, int position, T t);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;
        public VH(View itemView) {
            super(itemView);
            mConvertView = itemView;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId){
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(convertView);
        }


        public <T extends View> T getView(int id){
            View v = mViews.get(id);
            if(v == null){
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T)v;
        }

    }

}
