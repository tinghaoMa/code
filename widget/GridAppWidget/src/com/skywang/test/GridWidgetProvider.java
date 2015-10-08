package com.skywang.test;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.skywang.test.R;

public class GridWidgetProvider extends AppWidgetProvider {
	
	private static final String TAG = "SKYWANG";

	public static final String BT_REFRESH_ACTION = "com.skywang.test.BT_REFRESH_ACTION";
	public static final String COLLECTION_VIEW_ACTION = "com.skywang.test.COLLECTION_VIEW_ACTION";
	public static final String COLLECTION_VIEW_EXTRA = "com.skywang.test.COLLECTION_VIEW_EXTRA";
	
    @Override  
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,  
            int[] appWidgetIds) {  

        Log.d(TAG, "GridWidgetProvider onUpdate");
    	for (int appWidgetId:appWidgetIds) {
	    	// 获取AppWidget对应的视图
	        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
	        
	        // 设置响应 “按钮(bt_refresh)” 的intent
	        Intent btIntent = new Intent().setAction(BT_REFRESH_ACTION);
	        PendingIntent btPendingIntent = PendingIntent.getBroadcast(context, 0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	        rv.setOnClickPendingIntent(R.id.bt_refresh, btPendingIntent);	        
	        
	        // 设置 “GridView(gridview)” 的adapter。
	        // (01) intent: 对应启动 GridWidgetService(RemoteViewsService) 的intent  
	        // (02) setRemoteAdapter: 设置 gridview的适配器
	        //    通过setRemoteAdapter将gridview和GridWidgetService关联起来，
	        //    以达到通过 GridWidgetService 更新 gridview 的目的
	        Intent serviceIntent = new Intent(context, GridWidgetService.class);        
	        rv.setRemoteAdapter(R.id.gridview, serviceIntent);	        
	        
	        
	        // 设置响应 “GridView(gridview)” 的intent模板	        
	        // 说明：“集合控件(如GridView、ListView、StackView等)”中包含很多子元素，如GridView包含很多格子。
	        //     它们不能像普通的按钮一样通过 setOnClickPendingIntent 设置点击事件，必须先通过两步。
	        //        (01) 通过 setPendingIntentTemplate 设置 “intent模板”，这是比不可少的！
	        //        (02) 然后在处理该“集合控件”的RemoteViewsFactory类的getViewAt()接口中 通过 setOnClickFillInIntent 设置“集合控件的某一项的数据”
	        Intent gridIntent = new Intent();
	        gridIntent.setAction(COLLECTION_VIEW_ACTION);
	        gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	        // 设置intent模板
	        rv.setPendingIntentTemplate(R.id.gridview, pendingIntent);
	        // 调用集合管理器对集合进行更新
	        appWidgetManager.updateAppWidget(appWidgetId, rv);
    	}
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	String action = intent.getAction();    	
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        Log.d(TAG, "GridWidgetProvider onReceive : "+intent.getAction());
        if (action.equals(COLLECTION_VIEW_ACTION)) {
        	// 接受“gridview”的点击事件的广播
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(COLLECTION_VIEW_EXTRA, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        } else if (action.equals(BT_REFRESH_ACTION)) {
        	// 接受“bt_refresh”的点击事件的广播
            Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
