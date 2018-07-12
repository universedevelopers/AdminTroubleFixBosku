package com.universedeveloper.eeq.admintroublefix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView.AdapterRecyclerViewSeriLaptop;
import com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView.AdapterRecyclerViewSeriLaptop;
import com.universedeveloper.eeq.admintroublefix.Detail.LaptopTerpilih;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeriLaptop extends AppCompatActivity {

    RecyclerView list_laptop;
    List<ModelTroubleshoot> modelTroubleshootList;

    String merk_laptop;

    //Pencarian metode kedua
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private ArrayList<ModelTroubleshoot> mArrayList;
    AdapterRecyclerViewSeriLaptop adapterRecyclerViewSeriLaptop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seri_laptop);

        merk_laptop = getIntent().getStringExtra("merk_laptop");

        initViews();
        loadJSON();
    }

    private void initViews(){
        list_laptop = (RecyclerView) findViewById(R.id.list_laptop);
        list_laptop.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list_laptop.setLayoutManager(layoutManager);
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getLaptop(merk_laptop);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getTroubleshoot()));
                //mAdapter = new AdapterPencarianMenu(mArrayList);
                adapterRecyclerViewSeriLaptop = new AdapterRecyclerViewSeriLaptop(getApplicationContext(),mArrayList);
                list_laptop.setAdapter(adapterRecyclerViewSeriLaptop);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
