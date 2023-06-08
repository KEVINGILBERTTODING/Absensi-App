package com.example.absensi.ui.main.karyawan.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absensi.R;
import com.example.absensi.databinding.FragmentKaryawanHomeBinding;

public class KaryawanHomeFragment extends Fragment {

    private FragmentKaryawanHomeBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKaryawanHomeBinding.inflate(inflater,container, false);

        return binding.getRoot();
    }
}