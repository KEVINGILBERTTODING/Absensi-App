package com.example.absensi.data.api;

import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.JabatanModel;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.data.model.KeteranganModel;
import com.example.absensi.data.model.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("karyawan/insertIzin")
    Call<ResponseModel> insertIzin(
            @PartMap Map<String, RequestBody> text,
            @Part MultipartBody.Part image
            );

    @GET("karyawan/getalljabatan")
    Call<List<JabatanModel>> getAllJabatan();

    @Multipart
    @POST("karyawan/editProfile")
    Call<ResponseModel> editProfile(
            @PartMap Map<String, RequestBody> text
    );

    @GET("karyawan/getProfile")
    Call<KaryawanModel> getProfile(
            @Query("id") String id
    );

    @GET("karyawan/getAllKeterangan")
    Call<List<KeteranganModel>> getAllKeterangan(
            @Query("id") String id
    );
 }
