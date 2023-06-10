package com.example.absensi.ui.main.admin.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absensi.R;
import com.example.absensi.databinding.FragmentAdminHomeBinding;
import com.example.absensi.ui.main.admin.absen.AbsenFragment;
import com.example.absensi.ui.main.admin.izin.IzinFragment;
import com.example.absensi.ui.main.admin.jabatan.JabatanFragment;
import com.example.absensi.ui.main.admin.karyawan.KaryawanFragment;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener();
    }

    private void listener() {
        binding.cvDataKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replace(new KaryawanFragment());

            }
        });

        binding.cvDataJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new JabatanFragment());
            }
        });

        binding.cvDataAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replace(new AbsenFragment());
            }
        });
        binding.cvDataKeterangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new IzinFragment());
            }
        });
    }


    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .addToBackStack(null).commit();
    }
}
