package com.example.absensi.ui.main.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.absensi.R;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.api.AuthService;
import com.example.absensi.data.model.AuthModel;
import com.example.absensi.ui.main.admin.AdminMainActivity;
import com.example.absensi.ui.main.karyawan.KaryawanMainActivity;
import com.example.util.Constans;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    AlertDialog progressDialog;
    AuthService authService;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        authService = ApiConfig.getClient().create(AuthService.class);
        sharedPreferences = getSharedPreferences(Constans.SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        listener();

        if (sharedPreferences.getBoolean("logged_in", false)) {
            if (sharedPreferences.getInt(Constans.SHARED_PREF_ROLE, 0) == 2) {
                startActivity(new Intent(LoginActivity.this, KaryawanMainActivity.class));
                finish();
            }else if (sharedPreferences.getInt(Constans.SHARED_PREF_ROLE, 0) == 1) {
                startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                finish();
            }
        }




    }


    private void listener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError("Username tidak boleh kosong");
                    etUsername.requestFocus();
                }else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                }else{
                    login();
                }
            }
        });
    }


    private void login() {
        showProgressBar("Loading", "Authentifikasi", true);
        authService.auth(etUsername.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    if (response.body().getRole() == 2) { // KARYAWAN
                        editor.putBoolean("logged_in", true);
                        editor.putString(Constans.SHARED_PREF_USER_ID, response.body().getUserId());
                        editor.putInt(Constans.SHARED_PREF_ROLE, response.body().getRole());
                        editor.putString(Constans.SHARED_PREF_JABATAN, response.body().getJabatan());
                        editor.putString(Constans.SHARED_PREF_NAMA_LENGKAP, response.body().getNama());
                        editor.apply();
                        showProgressBar("sdsd", "sds", false);
                        startActivity(new Intent(LoginActivity.this, KaryawanMainActivity.class));
                        finish();
                    }else if (response.body().getRole() == 1) { // ADMIN
                        editor.putBoolean("logged_in", true);
                        editor.putString(Constans.SHARED_PREF_USER_ID, response.body().getUserId());
                        editor.putInt(Constans.SHARED_PREF_ROLE, response.body().getRole());
                        editor.putString(Constans.SHARED_PREF_NAMA_LENGKAP, response.body().getNama());
                        editor.apply();
                        showProgressBar("sdsd", "sds", false);
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        finish();

                    }

                }else {
                    showProgressBar("sdsd", "sds", false);
                    showToast("error", response.body().getMesage());
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                showProgressBar("sdsd", "sds", false);
                showToast("error", "Tidak ada koneksi internet");

            }
        });

    }

    private void showProgressBar(String title, String message, boolean isLoading) {
        if (isLoading) {
            // Membuat progress dialog baru jika belum ada
            if (progressDialog == null) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this);
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
            Toasty.success(LoginActivity.this, text, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(LoginActivity.this, text, Toasty.LENGTH_SHORT).show();
        }
    }

}