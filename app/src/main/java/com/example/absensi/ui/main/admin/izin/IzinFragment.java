package com.example.absensi.ui.main.admin.izin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.absensi.R;
import com.example.absensi.data.api.AdminServices;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.KeteranganModel;
import com.example.absensi.databinding.FragmentAbsenBinding;
import com.example.absensi.databinding.FragmentIzinBinding;
import com.example.absensi.ui.main.admin.adapter.AbsenAdapter;
import com.example.absensi.ui.main.admin.adapter.IzinAdapter;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IzinFragment extends Fragment {
    private FragmentIzinBinding binding;
    IzinAdapter izinAdapter;
    List<KeteranganModel> keteranganModelList;
    LinearLayoutManager linearLayoutManager;
    AlertDialog progressDialog;
    AdminServices adminServices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIzinBinding.inflate(inflater, container, false);
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

    }

    private void getData() {
        showProgressBar("Loading", "Memuat data...", true);
        adminServices.getAllIzin().enqueue(new Callback<List<KeteranganModel>>() {
            @Override
            public void onResponse(Call<List<KeteranganModel>> call, Response<List<KeteranganModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    keteranganModelList = response.body();
                   izinAdapter = new IzinAdapter(getContext(), keteranganModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.rvIzin.setLayoutManager(linearLayoutManager);
                    binding.rvIzin.setAdapter(izinAdapter);
                    binding.rvIzin.setHasFixedSize(true);
                    showProgressBar("sdsdsd", "sdsd", false);

                }else {
                    showProgressBar("sdd", "ds", false);
                    binding.tvEmppty.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<KeteranganModel>> call, Throwable t) {
                showProgressBar("sdd", "ds", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });

    }

    private void filter(String text) {
        ArrayList<KeteranganModel> filteredList = new ArrayList<>();
        for (KeteranganModel item : keteranganModelList){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

       izinAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {

        }else{
           izinAdapter.filter(filteredList);
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