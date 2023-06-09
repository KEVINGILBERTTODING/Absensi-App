package com.example.absensi.ui.main.karyawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.absensi.R;
import com.example.absensi.ui.main.karyawan.home.KaryawanHomeFragment;
import com.example.absensi.ui.main.karyawan.profile.EditProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class KaryawanMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan_main);
        bottomNavigationView = findViewById(R.id.btmBar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new KaryawanHomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuPerson) {
                    replace(new EditProfileFragment());
                    return true;
                }
                return false;
            }
        });

        replace(new KaryawanHomeFragment());

    }

    private void replace (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameKaryawan, fragment).commit();
    }
}