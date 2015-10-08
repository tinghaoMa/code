package com.mth.plugin;

import android.util.Log;

public class PluginClass {
    public PluginClass() {
        Log.i("Plugin", "PluginClass client initialized");
    }

    public int functionl(int a, int b) {
        return a + b;
    }
}