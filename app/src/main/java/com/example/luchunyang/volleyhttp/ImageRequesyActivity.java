package com.example.luchunyang.volleyhttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ImageRequesyActivity extends AppCompatActivity {

    public static final String TAG = ImageRequesyActivity.class.getSimpleName();

    private ImageView iv;
    private String LIUYAN = "http://y0.ifengimg.com/news_spider/dci_2013/07/48082ba59d0bbb1213a6e1771bcea2d9.jpg";
    private String FACEBOOK = "https://www.facebook.com/rsrc.php/v3/yx/r/pyNVUg5EM0j.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_requesy);

        iv = (ImageView) findViewById(R.id.iv);
    }

    public void imageRequest(View view) {
        final long start = System.currentTimeMillis();
        ImageRequest imageRequest = new ImageRequest(FACEBOOK, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: "+error.getMessage());

                //结果是onErrorResponse: time use = 52358  为什么是52秒?
                /**
                 * 2000的参数 Volley 会设置connectTimeout和readTimeOut 两个参数都是2000,所以初始的请求就要花费 2000 + 2000 = 4000时间
                 * 重试第一次:超时时间变成了 timeOut = 2000 + 2000 * 2f = 6000; 所以重试第一次请求需要耗时=6000+6000 = 12000;
                 * 重试第二次:超时时间变成了 timeOut = 6000 + 6000 * 2f = 18000;所以重试第二次请求需要耗时=18000+18000=36000;
                 *
                 * 总共耗时=36000 + 12000 + 4000 = 52000;
                 */
                Log.i(TAG, "onErrorResponse: time use = "+ (System.currentTimeMillis() - start));
            }
        });

        imageRequest.setRetryPolicy(new DefaultRetryPolicy(2000,2,2f));
        MyApplication.getQueue().add(imageRequest);
    }

    Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap response) {
            Log.i(TAG, "onResponse: bitmap width="+response.getWidth()+" height="+response.getHeight());
            iv.setImageBitmap(response);
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i(TAG, "onErrorResponse: ");
        }
    };
    public void imageRequest1(View view) {
        //683x1000
        ImageRequest imageRequest = new ImageRequest(LIUYAN,listener,0,0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,errorListener);
        imageRequest.setShouldCache(false);
        MyApplication.getQueue().add(imageRequest);
    }

    public void imageRequest2(View view) {
        //width=409 height=600
        //它会按照一个不为0的边的值，将图片进行等比缩放。
        ImageRequest imageRequest = new ImageRequest(LIUYAN,listener,0,600, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,errorListener);
        imageRequest.setShouldCache(false);
        MyApplication.getQueue().add(imageRequest);
    }

    public void imageRequest3(View view) {
        //width=300 height=439  height为什么不是600?看源码
        ImageRequest imageRequest = new ImageRequest(LIUYAN,listener,300,600, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888,errorListener);
        imageRequest.setShouldCache(false);
        MyApplication.getQueue().add(imageRequest);
    }

    public void imageRequest4(View view) {
        ImageRequest imageRequest = new ImageRequest(LIUYAN,listener,300,600, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,errorListener);
        imageRequest.setShouldCache(false);
        MyApplication.getQueue().add(imageRequest);
    }
}
