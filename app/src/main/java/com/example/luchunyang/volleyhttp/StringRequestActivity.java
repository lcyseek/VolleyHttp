package com.example.luchunyang.volleyhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class StringRequestActivity extends AppCompatActivity {

    private static final String TAG = StringRequestActivity.class.getSimpleName();
    private String BAIDU = "http://www.baidu.com";
    private String KUGOU = "http://mobilecdn.kugou.com/api/v3/search/song?keyword=";


    private TextView tv;

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i(TAG, "onErrorResponse: " + error.getMessage());
        }
    };

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                //如果返回的结果响应头没有指定charset,那么Volley会使用默认的ISO-8859-1编码,所以这里要转化一下.
                //也可以在parseNetworkResponse这里面自己拦截
//                tv.setText(new String(response.getBytes("ISO-8859-1"),"utf-8"));

                tv.setText(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        tv = (TextView) findViewById(R.id.tv);
    }

    public void stringGet(View view) {
        StringRequest request = new StringRequest(BAIDU,listener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Charset", "utf-8");
                headers.put("name","luchunyang");
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                Set<String> keySet = response.headers.keySet();
//                for (String key : keySet){
//                    System.out.println(key+"="+response.headers.get(key));
//                }

                String parsed;
                try {
                    parsed = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                //HttpHeaderParser.parseCacheHeaders(response) 这是返回给NetworkDispatcher一个Cache.Entry,以便它缓存起来
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        request.setShouldCache(true);
        //设定初始化的超时时间：5000ms；最大的请求次数：3次；发生冲突时的重传延迟增加数：2f
        request.setRetryPolicy(new DefaultRetryPolicy(5000,3,2f));
        MyApplication.getQueue().add(request);
    }

    public void stringGetMp3(View view) {
        try {
            StringRequest stringRequest = new StringRequest(KUGOU+ URLEncoder.encode("沉默是金","utf-8"),listener,errorListener);
            MyApplication.getQueue().add(stringRequest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void stringPost(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,BAIDU,listener,errorListener){
            //如果上传的不是键值对,而是字节流,可以用这个重载方法
            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            //在上面getBody函数没有被重写情况下，此方法的返回值会被 key、value 分别编码后拼装起来转换为字节码作为 Body 内容。
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name1", "value1");
                map.put("name2", "value2");
                return map;
            }

        };

        MyApplication.getQueue().add(stringRequest);

    }
}
