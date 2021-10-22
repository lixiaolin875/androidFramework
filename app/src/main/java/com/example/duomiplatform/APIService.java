package com.example.duomiplatform;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("user/login" )
    Call<UserInfo> login(@Query("username") String username, @Query("password")String password);

}
