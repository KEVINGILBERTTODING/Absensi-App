package com.example.absensi.ui.main.admin.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.KaryawanService;
import com.example.absensi.data.model.JabatanModel;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.databinding.FragmentEditProfileAdminBinding;
import com.example.absensi.databinding.FragmentEditProfileBinding;
import com.example.absensi.ui.main.adapter.SpinnerJabatanAdapter;
import com.example.absensi.ui.main.auth.LoginActivity;
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

    private FragmentEditProfileAdminBinding binding;
    private SpinnerJabatanAdapter spinnerJabatanAdapter;
    AlertDialog progressDialog;
    KaryawanService karyawanService;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    List<JabatanModel> jabatanModelList;
    String [] opsiJk = {"Laki-laki", "Perempuan"};
    String [] opsiAgama = {"Kristen", "Islam", "Hindu", "Budha", "Konghucu"};
    String userId, jk, agama, jabatan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileAdminBinding.inflate(inflater, container, false);
        karyawanService = ApiConfig.getClient().create(KaryawanService.class);
        sharedPreferences = getContext().getSharedPreferences(Constans.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Constans.SHARED_PREF_USER_ID, null);
        editor = sharedPreferences.edit();

        Log.d("user id", "onCreateView: " + userId);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listner();
    }

    private void listner() {






        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOust();
            }
        });
    }


    private void logOust() {
        editor.clear().apply();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();

    }



}