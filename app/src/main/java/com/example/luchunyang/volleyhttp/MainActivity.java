package com.example.luchunyang.volleyhttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String KUGOU = "http://mobilecdn.kugou.com/api/v3/search/song?keyword=";
    private String BAIDU = "http://www.baidu.com";
    private String LIUYAN = "http://pic4.zhongsou.com/image/480e60b389b60108a20.jpg";
    private String LIUYAN1 = "http://img1.ph.126.net/x2oUKeLSGda8RDwtMB6CAg==/6608255098445675320.jpg";
    private String LIUYAN2 = "http://g.hiphotos.baidu.com/imgad/pic/item/f603918fa0ec08fa9f0b7dd85eee3d6d55fbda42.jpg";

    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getQueue().cancelAll(this);
    }

    public void stringRequest(View view) {
        startActivity(new Intent(this,StringRequestActivity.class));
    }

    public void jsonRequest(View view) {
        startActivity(new Intent(this,JsonRequestActivity.class));
    }

    public void imageRequest(View view) {
        startActivity(new Intent(this,ImageRequesyActivity.class));
    }

    public void imageLoader(View view) {
        startActivity(new Intent(this,ImageLoaderActivity.class));
    }
}
