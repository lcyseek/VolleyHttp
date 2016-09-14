package com.example.luchunyang.volleyhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonRequestActivity extends AppCompatActivity {
    private String URL = "http://apis.baidu.com/apistore/idservice/id?id=220381198706176211";

    private Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            System.out.println(response);
            try {
                JSONObject data = response.getJSONObject("retData");
                System.out.println(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(JsonRequestActivity.this,response.toString(),Toast.LENGTH_LONG).show();
        }
    } ;

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_request);
    }

    public void jsonObjectRequest(View view) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(URL, listener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("apikey","ff377538c313440c090cf91808880e97");
                return headers;
            }
        };
        MyApplication.getQueue().add(jsonRequest);
    }
}
