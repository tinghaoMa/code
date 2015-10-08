package com.mth.threadpoolexecutor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mth.utils.Executor;
import com.mth.utils.TaskRunnable;

public class MainActivity extends Activity implements OnClickListener {

    private Button mBtAdd;
    private Button mBtStop;
    private Button mBtStart;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setListener();
    }

    private void setListener() {
        mBtStart.setOnClickListener(this);
        mBtStop.setOnClickListener(this);
        mBtAdd.setOnClickListener(this);
    }

    private void setUpViews() {
        mBtAdd = (Button) findViewById(R.id.bt_add);
        mBtStop = (Button) findViewById(R.id.bt_stop);
        mBtStart = (Button) findViewById(R.id.bt_start);
        mTvResult = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                addTask();
                break;
            case R.id.bt_start:
                startTask();
                break;
            case R.id.bt_stop:
                stopTask();
                break;
        }
    }

    MyTaskRunnable runOne = null;
    MyTaskRunnable runTwo = null;
    TaskRunnable task2 = null;

    private void stopTask() {
        Executor.removeTask(task2);
    }

    private void startTask() {
        runOne = new MyTaskRunnable("---线程1111", 0, this);
        TaskRunnable task1 = new TaskRunnable(runOne, "线程1111", 0);
        Executor.executTask(task1, "线程1111", 0);

        int random = (int) (1 + Math.random() * 10);
        runTwo = new MyTaskRunnable("---线程2222", random, this);
        task2 = new TaskRunnable(runTwo, "---线程2222", random);
        Executor.executTask(task2, "线程2222", random);
    }

    /**
     * 添加一个高优先级的任务
     */
    private void addTask() {
        MyTaskRunnable run = new MyTaskRunnable("---我优先级高我先执行----", 100, this);
        TaskRunnable task = new TaskRunnable(run, "---我优先级高我先执行----", 100);
        Executor.executTask(task, "优先级高", 100);
    }

    public class MyTaskRunnable implements Runnable {
        public String name;
        public int priority;
        public Context mCon;

        public MyTaskRunnable(String name, int priority, Context mCon) {
            this.name = name;
            this.priority = priority;
            this.mCon = mCon;
        }

        public void run() {
            Log.e("MTH", name + "开始执行");
            for (int i = 0; i < 15; i++) {
                final int a = i;
                ((MainActivity) mCon).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mTvResult.setText(a + "-------" + name + "优先级--------" + priority);
                    }
                });
                if (i == 14) {
                    ((MainActivity) mCon).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mTvResult.setText(a + "-------" + name + "完成任务***********************");
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
