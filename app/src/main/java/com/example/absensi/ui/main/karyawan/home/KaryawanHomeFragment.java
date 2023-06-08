package com.example.absensi.ui.main.karyawan.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.absensi.R;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.KaryawanService;
import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.databinding.FragmentKaryawanHomeBinding;
import com.example.absensi.ui.main.auth.LoginActivity;
import com.example.absensi.ui.main.karyawan.adapter.AbsenAdapter;
import com.example.util.Constans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanHomeFragment extends Fragment {

    private FragmentKaryawanHomeBinding binding;
    SharedPreferences sharedPreferences;
    String userId;
    List<AbsenModel> absenModelList;
    AbsenAdapter absenAdapter;
    LinearLayoutManager linearLayoutManager;

    KaryawanService karyawanService;
    AlertDialog progressDialog;

    private String formattedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKaryawanHomeBinding.inflate(inflater,container, false);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        binding.tvNama.setText(sharedPreferences.getString(Constans.SHARED_PREF_NAMA_LENGKAP, null));
        binding.tvNamaLengkap.setText(sharedPreferences.getString(Constans.SHARED_PREF_NAMA_LENGKAP, null));
        binding.tvNIP.setText(sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null));
        karyawanService = ApiConfig.getClient().create(KaryawanService.class);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH);
        formattedTime  = dateFormat.format(currentTime);
        getAbsenHistory();

        binding.tvWaktu.setText(formattedTime);

        listener();



    }

    private void listener() {
        binding.btnAbsentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar("Loading", "Validasi absen...", true);
                karyawanService.insertAbsen(userId, binding.tvNama.getText().toString(), formattedTime)
                        .enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                if (response.isSuccessful() && response.body().getStatus() == 200) {
                                    showProgressBar("sdsd", "sdsds", false);
                                    showToast("success", "Berhasil absen hari ini");
                                    binding.rvAbsen.setAdapter(null);
                                    getAbsenHistory();
                                }else {
                                    showProgressBar("sdsd", "sdsds", false);
                                    showToast("error", "Terjadi kesalahan");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                showProgressBar("sdsd", "sdsds", false);
                                showToast("error", "Tidak ada koneksi internet");

                            }
                        });
            }
        });
    }

    private void getAbsenHistory() {
        showProgressBar("Loading", "Memuat data...", true);
        karyawanService.getMyAbsenHistory(userId).enqueue(new Callback<List<AbsenModel>>() {
            @Override
            public void onResponse(Call<List<AbsenModel>> call, Response<List<AbsenModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    absenModelList = response.body();
                    binding.tvEmpty.setVisibility(View.GONE);
                    absenAdapter = new AbsenAdapter(getContext(), absenModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvAbsen.setLayoutManager(linearLayoutManager);
                    binding.rvAbsen.setAdapter(absenAdapter);
                    binding.rvAbsen.setHasFixedSize(true);
                    showProgressBar("sds", "dd", false);

                }else {
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    showProgressBar("sds", "dd", false);
                }
            }

            @Override
            public void onFailure(Call<List<AbsenModel>> call, Throwable t) {
                binding.tvEmpty.setVisibility(View.GONE);
                showProgressBar("sds", "dd", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });


    }


    private void showProgressBar(String title, String message, boolean isLoading) {
        if (isLoading) {
            // Membuat progress dialog baru jika belum ada
            if (progressDialog == null) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setCancelable(false);
                progressDialog = builder.create();
            }
            progressDialog.show(); // Menampilkan progress dialog
        } else {
            // Menyembunyikan progress dialog jika ada
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
    private void showToast(String jenis, String text) {
        if (jenis.equals("success")) {
            Toasty.success(getContext(), text, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(getContext(), text, Toasty.LENGTH_SHORT).show();
        }
    }
}