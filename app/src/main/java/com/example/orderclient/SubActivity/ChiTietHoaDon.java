package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orderclient.ADAPTER.ChiTietHoaDonAdapter;
import com.example.orderclient.ADAPTER.XacNhanThanhToanAdapter;
import com.example.orderclient.DAO.ChiTietHoaDonDao;
import com.example.orderclient.MODEL.HoaDon;
import com.example.orderclient.MainActivity;
import com.example.orderclient.R;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDon extends AppCompatActivity {

    Button btn_cthd_xacnhan;
    TextView tv_cthd_mahoadon, tv_cthd_ngay, tv_cthd_nhanvien, tv_cthd_tienhang, tv_cthd_khuyenmai, tv_cthd_tongthanhtoan;
    HoaDon hoaDon;

    private RecyclerView rcv_chitiethoadon;
    private ChiTietHoaDonAdapter chiTietHoaDonAdapter;
    private ChiTietHoaDonDao chiTietHoaDonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        setTitle("Chi Tiết Hoá Đơn");
        anhXa();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        hoaDon = (HoaDon) bundle.get("object_hoadon");
        tv_cthd_mahoadon.setText(String.valueOf("Mã hóa đơn: "+hoaDon.maHD));
        tv_cthd_ngay.setText("Ngày: "+hoaDon.ngay);
        tv_cthd_nhanvien.setText("Nhân viên: "+hoaDon.maNV);
        tv_cthd_tienhang.setText(hoaDon.tienHang+" VND");
        tv_cthd_khuyenmai.setText(hoaDon.khuyenMai+" VND");
        tv_cthd_tongthanhtoan.setText(hoaDon.tienThanhToan+" VND");

        //Xử lý hiển thị thực đơn lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_chitiethoadon.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_chitiethoadon.addItemDecoration(itemDecoration);
        chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(getApplicationContext(), getListCTHD());
        rcv_chitiethoadon.setAdapter(chiTietHoaDonAdapter);

        btn_cthd_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<com.example.orderclient.MODEL.ChiTietHoaDon> getListCTHD() {
        List<com.example.orderclient.MODEL.ChiTietHoaDon> lists = new ArrayList<>();
        chiTietHoaDonDao = new ChiTietHoaDonDao(getApplicationContext());
        lists = chiTietHoaDonDao.getID(String.valueOf(hoaDon.maHD));
        return lists;
    }

    private void anhXa() {
        btn_cthd_xacnhan = findViewById(R.id.btn_cthd_xacnhan);
        tv_cthd_mahoadon = findViewById(R.id.tv_cthd_mahoadon);
        tv_cthd_ngay = findViewById(R.id.tv_cthd_ngay);
        tv_cthd_nhanvien = findViewById(R.id.tv_cthd_nhanvien);
        tv_cthd_tienhang = findViewById(R.id.tv_cthd_tienhang);
        tv_cthd_khuyenmai = findViewById(R.id.tv_cthd_khuyenmai);
        tv_cthd_tongthanhtoan = findViewById(R.id.tv_cthd_tongthanhtoan);
        rcv_chitiethoadon = findViewById(R.id.rcv_chitiethoadon);
    }
}