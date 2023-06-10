package com.example.absensi.ui.main.admin.jabatan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.absensi.R;
import com.example.absensi.data.api.AdminServices;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.model.JabatanModel;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.databinding.FragmentJabatanBinding;
import com.example.absensi.databinding.FragmentKaryawanBinding;
import com.example.absensi.ui.main.admin.adapter.JabatanAdapter;
import com.example.absensi.ui.main.admin.adapter.KaryawanAdapter;
import com.example.absensi.ui.main.admin.karyawan.InsertKaryawanFragment;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JabatanFragment extends Fragment {
    private FragmentJabatanBinding binding;
    JabatanAdapter jabatanAdapter;
    List<JabatanModel> jabatanModelList;
    LinearLayoutManager linearLayoutManager;
    AlertDialog progressDialog;
    AdminServices adminServices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJabatanBinding.inflate(inflater, container, false);
        adminServices = ApiConfig.getClient().create(AdminServices.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        binding.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        listener();

    }

    private void listener() {
        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new InsertKaryawanFragment());
            }
        });
        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_insert_jabatan);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                EditText etJabatan = dialog.findViewById(R.id.etANamaJabatan);
                Button btnSubmit, btnBatal;
                btnSubmit = dialog.findViewById(R.id.btnSubmit);
                btnBatal = dialog.findViewById(R.id.btnBatal);
                dialog.show();

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etJabatan.getText().toString().isEmpty()) {
                            etJabatan.setError("Nama jabatan tidak boleh kosong");
                            etJabatan.requestFocus();
                        }else {
                            showProgressBar("Loading", "Menyimpan data...", true);
                            adminServices.insertJabatan(etJabatan.getText().toString()).enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                                        showToast("success", "Berhasil menambahkan jabatan");
                                        showProgressBar("sd", "dsdsd", false);
                                        dialog.dismiss();
                                        binding.rvJabatan.setAdapter(null);
                                        getData();
                                    }else {
                                        showToast("error", "Terjadi kesalahan");
                                        showProgressBar("ds", "sd", false);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    showToast("error", "Tidak ada koneksi internet");
                                    showProgressBar("ds", "sd", false);


                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void getData() {
        showProgressBar("Loading", "Memuat data...", true);
        adminServices.getAllJabatan().enqueue(new Callback<List<JabatanModel>>() {
            @Override
            public void onResponse(Call<List<JabatanModel>> call, Response<List<JabatanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    jabatanModelList = response.body();
                    jabatanAdapter = new JabatanAdapter(getContext(), jabatanModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvJabatan.setLayoutManager(linearLayoutManager);
                    binding.rvJabatan.setAdapter(jabatanAdapter);
                    binding.rvJabatan.setHasFixedSize(true);
                    showProgressBar("sdsdsd", "sdsd", false);

                }else {
                    showProgressBar("sdd", "ds", false);
                    binding.tvEmppty.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<JabatanModel>> call, Throwable t) {
                showProgressBar("sdd", "ds", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });

    }

    private void filter(String text) {
        ArrayList<JabatanModel> filteredList = new ArrayList<>();
        for (JabatanModel item : jabatanModelList){
            if (item.getJabatan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        jabatanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else{
            jabatanAdapter.filter(filteredList);
        }
    }

    private void showProgressBar(String title, String message, boolean isLoading) {
        if (isLoading) {
            // Membuat progress dialog baru jika belum ada
            if (progressDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .addToBackStack(null).commit();
    }
}