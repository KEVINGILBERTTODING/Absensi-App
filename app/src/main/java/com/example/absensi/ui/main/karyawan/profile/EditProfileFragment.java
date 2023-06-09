package com.example.absensi.ui.main.karyawan.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.KaryawanService;
import com.example.absensi.data.model.JabatanModel;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.databinding.FragmentEditProfileBinding;
import com.example.absensi.ui.main.adapter.SpinnerJabatanAdapter;
import com.example.util.Constans;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private SpinnerJabatanAdapter spinnerJabatanAdapter;
    AlertDialog progressDialog;
    KaryawanService karyawanService;
    SharedPreferences sharedPreferences;
    List<JabatanModel> jabatanModelList;
    String [] opsiJk = {"Laki-laki", "Perempuan"};
    String [] opsiAgama = {"Kristen", "Islam", "Hindu", "Budha", "Konghucu"};
    String userId, jk, agama, jabatan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        karyawanService = ApiConfig.getClient().create(KaryawanService.class);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);

        Log.d("user id", "onCreateView: " + userId);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllJabatan();
        getProfile();

        ArrayAdapter adapterJk = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiJk);
        adapterJk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spJk.setAdapter(adapterJk);

        ArrayAdapter adapterAgama = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiAgama);
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAgama.setAdapter(adapterAgama);
        listner();
    }

    private void listner() {
        binding.btnUbahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nestedScrollView.setVisibility(View.VISIBLE);
            }
        });
        binding.btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nestedScrollView.setVisibility(View.GONE);
            }
        });

        binding.spAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                agama = opsiAgama[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spJk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jk = opsiJk[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jabatan = spinnerJabatanAdapter.getJabatan(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void getAllJabatan() {
        showProgressBar("Loading", "Memuat data...", true);
        karyawanService.getAllJabatan().enqueue(new Callback<List<JabatanModel>>() {
            @Override
            public void onResponse(Call<List<JabatanModel>> call, Response<List<JabatanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0 ) {
                    jabatanModelList = response.body();
                    spinnerJabatanAdapter = new SpinnerJabatanAdapter(getContext(), jabatanModelList);
                    binding.spJabatan.setAdapter(spinnerJabatanAdapter);
                    showProgressBar("sdds", "fsd", false);
                    binding.btnSimpan.setEnabled(true);

                }else {
                    showProgressBar("sdds", "fsd", false);
                    binding.btnSimpan.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<JabatanModel>> call, Throwable t) {
                showProgressBar("sdds", "fsd", false);
                showToast("error", "Tidak ada koneksi internet");
                binding.btnSimpan.setEnabled(false);

            }
        });

    }

    private void getProfile() {
        showProgressBar("Loading", "Memuat data pengguna...", true);
        karyawanService.getProfile(userId).enqueue(new Callback<KaryawanModel>() {
            @Override
            public void onResponse(Call<KaryawanModel> call, Response<KaryawanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showProgressBar("sds", "dsd", false);
                    binding.etNik.setText(response.body().getIdKaryawan());
                    binding.etNama.setText(response.body().getNama());
                    binding.etUsername.setText(response.body().getUsername());
                    binding.etTtl.setText(response.body().getTmpTglLahir());
                    binding.etAlamat.setText(response.body().getAlamat());
                    binding.etNoTelp.setText(response.body().getNoTel());

                }else {
                    showProgressBar("Sds", "sdd", false);
                    showToast("error", "Terjadi kesalahan");
                    binding.btnSimpan.setEnabled(false);

                }
            }

            @Override
            public void onFailure(Call<KaryawanModel> call, Throwable t) {
                showProgressBar("Sds", "sdd", false);
                showToast("error", "Tidak ada koneksi internet");
                binding.btnSimpan.setEnabled(false);

            }
        });
    }

    private void updateProfile() {
        showProgressBar("Loading", "Menyimpan perubahan...", true);
        HashMap map = new HashMap();
        map.put("id", RequestBody.create(MediaType.parse("text/plain"), binding.etNik.getText().toString()));
        map.put("nama",RequestBody.create(MediaType.parse("text/plain"),  binding.etNama.getText().toString()));
        map.put("username",RequestBody.create(MediaType.parse("text/plain"),  binding.etUsername.getText().toString()));
        map.put("tmp_tgl_lahir",RequestBody.create(MediaType.parse("text/plain"),  binding.etTtl.getText().toString()));
        map.put("alamat",RequestBody.create(MediaType.parse("text/plain"),  binding.etAlamat.getText().toString()));
        map.put("password",RequestBody.create(MediaType.parse("text/plain"),  binding.etPassword.getText().toString()));
        map.put("no_tel",RequestBody.create(MediaType.parse("text/plain"),  binding.etNoTelp.getText().toString()));
        map.put("jenkel",RequestBody.create(MediaType.parse("text/plain"),  jk));
        map.put("agama",RequestBody.create(MediaType.parse("text/plain"),  agama));
        map.put("jabatan",RequestBody.create(MediaType.parse("text/plain"),  jabatan));

        karyawanService.editProfile(map).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    showToast("success", "Berhasil mengubah profile");
                    showProgressBar("Sdd", "dsd", false);
                    binding.nestedScrollView.setVisibility(View.GONE);
                }else {
                    showToast("error", response.body().getMessage());
                    showProgressBar("Sdd", "dsd", false);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                showToast("error", "Tidak ada koneksi internet");
                showProgressBar("Sdd", "dsd", false);

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