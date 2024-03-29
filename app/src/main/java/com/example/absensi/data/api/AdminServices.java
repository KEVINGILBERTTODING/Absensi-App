package com.example.absensi.data.api;

import com.airbnb.lottie.L;
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

public interface AdminServices {
    @GET("admin/getallkaryawan")
    Call<List<KaryawanModel>> getAllKaryawan();

    @Multipart
    @POST("admin/insertKaryawan")
    Call<ResponseModel> insertKaryawan(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST("admin/deleteKarayawan")
    Call<ResponseModel> deleteKarywan(
            @Field("id") String id
    );

    @GET("admin/getAllJabatan")
    Call<List<JabatanModel>> getAllJabatan();

    @FormUrlEncoded
    @POST("admin/insertJabatan")
    Call<ResponseModel> insertJabatan(
            @Field("jabatan") String jabatan
    );

    @GET("admin/getAllAbsensi")
    Call<List<AbsenModel>> getAllAbsensi();

    @FormUrlEncoded
    @POST("admin/deleteAbsen")
    Call<ResponseModel> deleteAbsen(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("admin/deleteizin")
    Call<ResponseModel> deleteIzin(
            @Field("id") String id
    );

    @GET("admin/getAllIzin")
    Call<List<KeteranganModel>> getAllIzin();
}
