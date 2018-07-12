package com.universedeveloper.eeq.admintroublefix;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("ferdirockers/list_detail/list_kerusakan.php")
    Call<JSONResponse> getKerusakan();

    @GET("ferdirockers/list_detail/list_teknopedia.php")
    Call<JSONResponse> getTeknopedia();

    @GET("ferdirockers/list_detail/list_laptop.php")
    Call<JSONResponse> getLaptop(@Query("merk_laptop") String merk_laptop);
}
