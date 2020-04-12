package com.lyshnia.jps.user;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/username")
    Call<User> getDetails(
            @Header("Authorization") String authorization,
            @Query("username") String username
    );
}
