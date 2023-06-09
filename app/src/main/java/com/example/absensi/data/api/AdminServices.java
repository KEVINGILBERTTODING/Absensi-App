package com.example.absensi.data.api;

import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.data.model.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface AdminServices {
    @GET("admin/getallkaryawan")
    Call<List<KaryawanModel>> getAllKaryawan();

    @Multipart
    @POST("admin/insertKaryawan")
    Call<ResponseModel> insertKaryawan(
            @PartMap Map<String, RequestBody> textData,
            @Part MultipartBody.Part image
            );
}
