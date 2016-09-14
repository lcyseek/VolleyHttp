package com.example.luchunyang.volleyhttp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by luchunyang on 16/9/7.
 */
public class MyApplication extends Application {
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        // 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
        requestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getQueue(){
        return requestQueue;
    }
}
