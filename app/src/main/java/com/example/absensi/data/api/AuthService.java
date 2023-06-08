package com.example.absensi.data.api;

import com.example.absensi.data.model.AuthModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {

    @FormUrlEncoded
    @POST("auth/auth")
    Call<AuthModel> auth(
            @Field("username") String username,
            @Field("password") String password
    );
}
