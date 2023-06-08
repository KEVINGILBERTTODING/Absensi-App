package com.example.absensi.data.api;

import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KaryawanService {

    @GET("karyawan/getMyAbsenHistory")
    Call<List<AbsenModel>> getMyAbsenHistory(
            @Query("user_id") String userId
    );

    @FormUrlEncoded
    @POST("karyawan/insertAbsen")
    Call<ResponseModel> insertAbsen(
            @Field("id_karyawan") String userId,
            @Field("nama") String nama,
            @Field("waktu") String waktu

    );
 }
