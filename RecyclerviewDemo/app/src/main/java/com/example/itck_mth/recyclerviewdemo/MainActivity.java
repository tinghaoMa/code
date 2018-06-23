package com.example.itck_mth.recyclerviewdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itck_mth.recyclerviewdemo.adapter.ItemType;
import com.example.itck_mth.recyclerviewdemo.adapter.NormalAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NormalAdapter<String> mAdapter;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rl);
        mAdapter = new NormalAdapter<String>(mData = initData()) {
            @Override
            protected int getItemView(int viewType) {
                switch (viewType) {
                    case 1:
                        return R.layout.normal_item;
                    case 2:
                        return R.layout.image_item;
                    default:
                        return 0;
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position % 2 == 0) {
                    return ItemType.TEXT_ITEM;
                } else {
                    return ItemType.IMAGE_ITEM;
                }
            }

            @Override
            protected void initData(VH holder, int position, final String s) {
                if (getItemViewType(position) == ItemType.TEXT_ITEM) {
                    TextView tv = holder.getView(R.id.tv);
                    tv.setText(s);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), s, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (getItemViewType(position) == ItemType.IMAGE_ITEM) {

                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<String> initData() {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add(" i = " + i);
        }
        return list;
    }

    private int loop = 1;


    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (loop) {
                    case 0:
                        addItem();
                        break;
                    case 1:
                        removeItem();
                        break;
                    default:
                        break;
                }
            }
        }, 3000);

    }

    private void removeItem() {
        mData.remove(0);
        mAdapter.notifyItemRemoved(1);
    }

    private void addItem() {
        mData.add(0, "hello world");
        mAdapter.notifyItemChanged(0);
    }
}
