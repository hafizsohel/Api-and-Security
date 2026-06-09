package com.example.apiandsecurity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apiandsecurity.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                //stringRequest();
                //objectRequest();
                arrayRequest();

            }
        });
    }
    //=========================
    private void stringRequest(){
        String url = "http://10.0.2.2/myapp/sohel.php"; //http://192.168.0.102/myapp/sohel.php

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                binding.tvStatus.setText(s);
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.tvStatus.setText("Error: " + error.toString());
                binding.progressBar.setVisibility(View.GONE);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map= new HashMap<String ,String>();
                map.put("pass","112233");
                map.put("email","sohel@gmail.com");
                return map;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    //========================
    private void objectRequest(){
        String url = "http://10.0.2.2/myapp/sohel.php";

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("pass","112233");
            jsonObject.put("email","sohel@gmail.com");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    String type = jsonObject.getString("type");
                    String result = jsonObject.getString("result");

                    // আলাদা show
                    binding.tvStatus.setText("Type: " + type + "\n\n" + result);

                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.tvStatus.setText("Parse Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                binding.progressBar.setVisibility(View.GONE);
                binding.tvStatus.setText("Error"+volleyError.toString());
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(objectRequest);
    }
    //============================
    private void arrayRequest(){
        String url = "http://10.0.2.2/myapp/sohel.php";

        JSONArray jsonArray = new JSONArray();

        JsonArrayRequest arrayRequest= new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);
    }
}