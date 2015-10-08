package com.mth.host;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import dalvik.system.DexClassLoader;

/**
 * @author tinghaoma
 */
public class MainActivity extends Activity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.id_bt);
        mButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                useDexClassLoader();
            }
        });
    }

    protected void useDexClassLoader() {
        // 确定目标class所在的位置
        Intent intent = new Intent("com.mth.test.plugin", null);
        // 通过PackageManager获取信息。
        PackageManager pm = getPackageManager();
        final List<ResolveInfo> plugins = pm.queryIntentActivities(intent, 0);
        ResolveInfo info = plugins.get(0);
        ActivityInfo ainfo = info.activityInfo;
        // 当存在多个dexPath路径时，需要此分隔符
        String div = System.getProperty("npath.separator");
        // 报名
        String packageName = ainfo.packageName;
        // 目标全路径
        String dexPath = ainfo.applicationInfo.sourceDir;
        // 本地解析输出路径
        String dexOutputDir = getApplicationInfo().dataDir;
        // 目标lib（C/C++）文件存放路径
        String libPath = ainfo.applicationInfo.nativeLibraryDir;
        // 创建我们自己的ClassLoader
        DexClassLoader cl = new DexClassLoader(dexPath, dexOutputDir, libPath, this.getClass().getClassLoader());
        try {
            // 通过反射机制去调用方法
            Class<?> clazz = cl.loadClass(packageName + ".PluginClass");
            Object obj = clazz.newInstance();
            Class[] params = new Class[2];
            params[0] = Integer.TYPE;
            params[1] = Integer.TYPE;
            Method action = clazz.getMethod("functionl", params);
            Integer ret = (Integer) action.invoke(obj, 55, 34);
            Log.i("Host", "returnvalueis==================" + ret);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Host", "Something wrong has happened!");
        }
    }
}
