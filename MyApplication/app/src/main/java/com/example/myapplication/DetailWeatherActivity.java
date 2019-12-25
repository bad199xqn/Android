package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailWeatherActivity extends AppCompatActivity {

    String tp="";
    TextView txtName;
    ListView lv;
    CustomAdaptor customAdaptor;
    ArrayList<Thoitiet> mangthoitiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);

        init();
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        if (city.equals("")){
            tp = "Ha noi";
            Get7DaysData(tp);
        }else {
            tp = city;
            Get7DaysData(tp);
        }
    }

    private void init(){
        txtName = (TextView) findViewById(R.id.textviewTenTP);
        lv = (ListView) findViewById(R.id.listview);
        mangthoitiet = new ArrayList<Thoitiet>();
        customAdaptor = new CustomAdaptor(DetailWeatherActivity.this,mangthoitiet);
        lv.setAdapter(customAdaptor);
    }

    private void Get7DaysData(String data) {
        RequestQueue queue = Volley.newRequestQueue(DetailWeatherActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&appid=d7122dc7db906d11a72fc9dfd815aa1f";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("KetQua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i<jsonArrayList.length(); i++){

                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");

                                long lDay = Long.valueOf(ngay);
                                Date date = new Date(lDay * 1000L);
                                SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
                                String strDay = sdf.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");
                                Double a = Double.valueOf(max);
                                String nhietdoMax = String.valueOf(a.intValue());
                                Double b = Double.valueOf(min);
                                String nhietdoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObject1Weather.getString("description");
                                String icon = jsonObject1Weather.getString("icon");

                                mangthoitiet.add(new Thoitiet(strDay,status,icon,nhietdoMax,nhietdoMin));

                            }
                            customAdaptor.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
        queue.add(stringRequest);
        }
}
