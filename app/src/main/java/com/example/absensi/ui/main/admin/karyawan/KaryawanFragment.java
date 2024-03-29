package com.example.absensi.ui.main.admin.karyawan;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absensi.R;
import com.example.absensi.data.api.AdminServices;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.KaryawanService;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.databinding.FragmentKaryawanBinding;
import com.example.absensi.databinding.FragmentKaryawanHomeBinding;
import com.example.absensi.ui.main.admin.adapter.KaryawanAdapter;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KaryawanFragment extends Fragment {
    private FragmentKaryawanBinding binding;
    List<KaryawanModel> karyawanModelList;
    KaryawanAdapter karyawanAdapter;
    LinearLayoutManager linearLayoutManager;
    AlertDialog progressDialog;
    AdminServices adminServices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKaryawanBinding.inflate(inflater, container, false);
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
    }

    private void getData() {
        showProgressBar("Loading", "Memuat data...", true);
        adminServices.getAllKaryawan().enqueue(new Callback<List<KaryawanModel>>() {
            @Override
            public void onResponse(Call<List<KaryawanModel>> call, Response<List<KaryawanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    karyawanModelList = response.body();
                    karyawanAdapter = new KaryawanAdapter(getContext(), karyawanModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvKaryawan.setLayoutManager(linearLayoutManager);
                    binding.rvKaryawan.setAdapter(karyawanAdapter);
                    binding.rvKaryawan.setHasFixedSize(true);
                    showProgressBar("sdsdsd", "sdsd", false);

                }else {
                    showProgressBar("sdd", "ds", false);
                    binding.tvEmppty.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<KaryawanModel>> call, Throwable t) {
                showProgressBar("sdd", "ds", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });

    }

    private void filter(String text) {
        ArrayList<KaryawanModel> filteredList = new ArrayList<>();
        for (KaryawanModel item : karyawanModelList){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        karyawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else{
            karyawanAdapter.filter(filteredList);
        }
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

    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .addToBackStack(null).commit();
    }
}