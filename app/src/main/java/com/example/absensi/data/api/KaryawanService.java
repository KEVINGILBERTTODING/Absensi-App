package com.example.absensi.data.api;

import com.example.absensi.data.model.AbsenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KaryawanService {

    @GET("karyawan/getMyAbsenHistory")
    Call<List<AbsenModel>> getMyAbsenHistory(
            @Query("user_id") String userId
    );
 }
