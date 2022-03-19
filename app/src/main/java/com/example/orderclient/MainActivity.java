package com.example.orderclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.orderclient.FRAGMENT.HoaDonFragment;
import com.example.orderclient.FRAGMENT.QuanLyFragment;
import com.example.orderclient.FRAGMENT.ThongKeFragment;
import com.example.orderclient.FRAGMENT.ThucDonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Chọn HoaDonFragment làm màn hình hiển thị khi mở app
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HoaDonFragment()).commit();
        setTitle("Hoá Đơn");

        //Xử lý chuyển Fragment bằng bottom nav
        mNavigationView = findViewById(R.id.bottom_nav);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_hoadon:
                        setTitle("Hoá Đơn");
                        selectedFragment = new HoaDonFragment();
                        break;

                    case R.id.action_thucdon:
                        setTitle("Thực Đơn");
                        Intent intentTD = getIntent();
                        String usernameTD = intentTD.getStringExtra("user");
                        selectedFragment = new ThucDonFragment();
                        Bundle bundleTD = new Bundle();
                        bundleTD.putString("usernametd", usernameTD);
                        selectedFragment.setArguments(bundleTD);
                        break;

                    case R.id.action_thongke:
                        setTitle("Thống Kê");
                        selectedFragment = new ThongKeFragment();
                        break;

                    case R.id.action_quanly:
                        setTitle("Quản Lý");
                        Intent intentCV = getIntent();
                        String username = intentCV.getStringExtra("user");
                        selectedFragment = new QuanLyFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        selectedFragment.setArguments(bundle);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });
    }
}