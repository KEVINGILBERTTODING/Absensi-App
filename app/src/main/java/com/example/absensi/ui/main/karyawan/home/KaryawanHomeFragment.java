package com.example.absensi.ui.main.karyawan.home;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.absensi.R;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.KaryawanService;
import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.databinding.FragmentKaryawanHomeBinding;
import com.example.absensi.ui.main.karyawan.adapter.AbsenAdapter;
import com.example.util.Constans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanHomeFragment extends Fragment {

    private FragmentKaryawanHomeBinding binding;
    SharedPreferences sharedPreferences;
    String userId;
    List<AbsenModel> absenModelList;
    String [] opsiJenis = {"Izin", "Sakit"};
    AbsenAdapter absenAdapter;
    LinearLayoutManager linearLayoutManager;
    private EditText etPdfPath;

    KaryawanService karyawanService;
    AlertDialog progressDialog;
    private File file;


    private String formattedTime, jenis;

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
        binding.btnTidakHadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserIzin();
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

    private void inserIzin() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_insert_izin);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit, btnBatal, btnFilePicker;
        EditText etAlasan = dialog.findViewById(R.id.etAlasan);
        etPdfPath = dialog.findViewById(R.id.etPdfPath);
        TextView tvWaktu = dialog.findViewById(R.id.tvWaktu);
        Spinner spJenis = dialog.findViewById(R.id.spJenis);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnBatal = dialog.findViewById(R.id.btnBatal);
        btnFilePicker = dialog.findViewById(R.id.btnPdfPicker);

        ArrayAdapter adapterJenis = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiJenis);
        adapterJenis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenis.setAdapter(adapterJenis);

        spJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenis = opsiJenis[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvWaktu.setText(formattedTime);
        dialog.show();

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFilePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAlasan.getText().toString().isEmpty()) {
                    etAlasan.setError("Alasan tidak boleh kosong");
                    etAlasan.requestFocus();
                }else if (etPdfPath.getText().toString().isEmpty()) {
                    showToast("error", "Lampiran tidak boleh kosong");
                    etPdfPath.requestFocus();
                }else {
                    showProgressBar("Loading", "Validasi izin...", true);
                    HashMap map =new HashMap();
                    map.put("id_karyawan", RequestBody.create(MediaType.parse("text/plain"), userId));
                    map.put("nama", RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString(Constans.SHARED_PREF_NAMA_LENGKAP, null)));
                    map.put("keterangan", RequestBody.create(MediaType.parse("text/plain"), jenis));
                    map.put("alasan", RequestBody.create(MediaType.parse("text/plain"), etAlasan.getText().toString()));
                    map.put("waktu", RequestBody.create(MediaType.parse("text/plain"), formattedTime));

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part image = MultipartBody.Part.createFormData("lampiran", file.getName(), requestBody);


                    karyawanService.insertIzin(map, image).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                showProgressBar("dsd", "dsd", false);
                                showToast("success", "Berhasil insert keterangan");
                                dialog.dismiss();
                            }else {
                                showProgressBar("dsd", "dsd", false);
                                showToast("error", "Berhasil insert keterangan");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            showProgressBar("dsd", "dsd", false);
                            showToast("error", "Tidak ada koneksi internet");

                        }
                    });
                }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                file = new File(pdfPath);
                etPdfPath.setText(file.getName());

            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}