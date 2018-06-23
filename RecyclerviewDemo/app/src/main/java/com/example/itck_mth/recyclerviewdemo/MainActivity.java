package com.example.itck_mth.recyclerviewdemo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rl);
        mAdapter = new NormalAdapter<String>(initData()) {
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

        for (int i = 0; i < 10; i++) {
            list.add(" i = " + i);
        }
        return list;
    }
}
