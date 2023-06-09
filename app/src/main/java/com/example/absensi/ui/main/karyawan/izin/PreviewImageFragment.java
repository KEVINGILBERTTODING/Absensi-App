package com.example.absensi.ui.main.karyawan.izin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.absensi.R;
import com.example.absensi.databinding.FragmentPreviewImageBinding;

public class PreviewImageFragment extends Fragment {

    private FragmentPreviewImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPreviewImageBinding.inflate(inflater, container, false);


        Glide.with(getContext())
                .load(getArguments().getString("image"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.image);
        return binding.getRoot();
    }
}