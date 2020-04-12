package com.lyshnia.jps.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.HashMap;

public interface Login {
    @POST("api/login")
    Call<LoginDetails> login(
            @Body HashMap<String, String> body
    );

    @POST("user")
    Call<HashMap<String, Object>> register(
            @Body HashMap<String, String> body
    );

}
