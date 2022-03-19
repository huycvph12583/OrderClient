package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.orderclient.ADAPTER.ChonThucDonAdapter;
import com.example.orderclient.ADAPTER.NhanVienAdapter;
import com.example.orderclient.ANIMATION.TranslateAnimationUtil;
import com.example.orderclient.DAO.NhanVienDao;
import com.example.orderclient.DAO.ThucDonDao;
import com.example.orderclient.MODEL.NhanVien;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DanhSachNhanVien extends AppCompatActivity {

    FloatingActionButton fab_dsnhanvien;
    private RecyclerView rcv_dsnhanvien;
    private NhanVienAdapter nhanVienAdapter;
    NhanVienDao nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nhan_vien);
        setTitle("Danh Sách Nhân Viên");
        anhXa();

        ////Xử lý hiển thị nhân viên lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_dsnhanvien.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_dsnhanvien.addItemDecoration(itemDecoration);
        nhanVienAdapter = new NhanVienAdapter(getListThucDon());
        rcv_dsnhanvien.setAdapter(nhanVienAdapter);
        rcv_dsnhanvien.setOnTouchListener(new TranslateAnimationUtil(getApplicationContext(), fab_dsnhanvien));

        //Thêm nhân viên
        fab_dsnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThemTaiKhoan.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<NhanVien> getListThucDon() {
        List<NhanVien> lists = new ArrayList<>();
        nhanVienDao = new NhanVienDao(this);
        lists = nhanVienDao.getAll();
        return lists;
    }

    private void anhXa() {
        fab_dsnhanvien = findViewById(R.id.fab_dsnhanvien);
        rcv_dsnhanvien = findViewById(R.id.rcv_dsnhanvien);
    }
}