package com.example.absensi.data.api;

import com.example.absensi.data.model.KaryawanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminServices {
    @GET("admin/getallkaryawan")
    Call<List<KaryawanModel>> getAllKaryawan();
}
