package com.universedeveloper.eeq.admintroublefix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView.AdapterRecyclerViewKerusakan;
import com.universedeveloper.eeq.admintroublefix.Detail.KerusakanTerpilih;
import com.universedeveloper.eeq.admintroublefix.Insert.InputKerusakan;

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

public class Kerusakan extends AppCompatActivity {

    Button btn_input_kerusakan;

    RecyclerView list_kerusakan;
    List<ModelTroubleshoot> modelTroubleshootList;

    //Pencarian metode kedua
    public static final String BASE_URL = "http://universedeveloper.com/gudangandroid/";
    private ArrayList<ModelTroubleshoot> mArrayList;
    AdapterRecyclerViewKerusakan adapterRecyclerViewKerusakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan);

        initViews();
        loadJSON();

        btn_input_kerusakan = (Button) findViewById(R.id.btn_input_kerusakan);
        btn_input_kerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Kerusakan.this, InputKerusakan.class );
                startActivity(intent);
            }
        });
    }
    private void initViews(){
        list_kerusakan = (RecyclerView) findViewById(R.id.list_kerusakan);
        list_kerusakan.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list_kerusakan.setLayoutManager(layoutManager);
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
        Call<JSONResponse> call = request.getKerusakan();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getTroubleshoot()));
                //mAdapter = new AdapterPencarianMenu(mArrayList);
                adapterRecyclerViewKerusakan = new AdapterRecyclerViewKerusakan(getApplicationContext(),mArrayList);
                list_kerusakan.setAdapter(adapterRecyclerViewKerusakan);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}
