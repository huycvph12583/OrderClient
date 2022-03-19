package com.example.orderclient.FRAGMENT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.DAO.NhanVienDao;
import com.example.orderclient.LoginActivity;
import com.example.orderclient.MODEL.NhanVien;
import com.example.orderclient.MainActivity;
import com.example.orderclient.R;
import com.example.orderclient.SubActivity.DanhSachNhanVien;
import com.example.orderclient.SubActivity.DoiMatKhau;
import com.example.orderclient.SubActivity.MaKhuyenMai;
import com.example.orderclient.SubActivity.ThemTaiKhoan;

public class QuanLyFragment extends Fragment {

    View v;
    LinearLayout linear_ql_themtaikhoan, linear_ql_dsnhanvien, linear_ql_doimatkhau, linear_ql_dangxuat, linear_ql_makhuyenmai;
    TextView tv_ql_ten, tv_ql_chucvu;
    ImageView img_ql_anh;

    Bundle bundle;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_quan_ly, container, false);
        anhXa();

        //Set ảnh, tên và chức vụ cho nhân viên
        bundle = getArguments();
        username = bundle.getString("username");
        setDataView();

        linear_ql_themtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemTaiKhoan.class);
                startActivity(intent);
            }
        });

        linear_ql_dsnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachNhanVien.class);
                startActivity(intent);
            }
        });


        linear_ql_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleCV = new Bundle();
                bundleCV.putString("userchange", username);
                Intent intent = new Intent(getActivity(), DoiMatKhau.class);
                intent.putExtras(bundleCV);
                startActivity(intent);
            }
        });

        linear_ql_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        linear_ql_makhuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MaKhuyenMai.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void setDataView() {
        if (bundle != null) {
            if (username != null){
                if (username.equalsIgnoreCase("admin")) {
                    tv_ql_ten.setText("Admin");
                    tv_ql_chucvu.setText("Quản trị viên");
                } else {
                    NhanVienDao nhanVienDao = new NhanVienDao(getActivity());
                    NhanVien nhanVien = nhanVienDao.getID(username);
                    if (nhanVien.maNV.equalsIgnoreCase("quanly")) {
                        tv_ql_ten.setText(nhanVien.hoTen);
                        tv_ql_chucvu.setText("Quản lý");
                        if (nhanVien.anhNV != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.anhNV, 0, nhanVien.anhNV.length);
                            img_ql_anh.setImageBitmap(bitmap);
                        }
                    } else {
                        tv_ql_ten.setText(nhanVien.hoTen);
                        tv_ql_chucvu.setText("Nhân viên");
                        if (nhanVien.anhNV != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.anhNV, 0, nhanVien.anhNV.length);
                            img_ql_anh.setImageBitmap(bitmap);
                        }
                        linear_ql_themtaikhoan.setVisibility(View.GONE);
                        linear_ql_dsnhanvien.setVisibility(View.GONE);
                    }
                }
            } else {
                tv_ql_ten.setText("Không xác định");
                tv_ql_chucvu.setText("Không xác định");
            }
        } else {
            tv_ql_ten.setText("Không xác định");
            tv_ql_chucvu.setText("Không xác định");
        }
    }

    private void anhXa() {
        linear_ql_themtaikhoan = v.findViewById(R.id.linear_ql_themtaikhoan);
        linear_ql_dsnhanvien = v.findViewById(R.id.linear_ql_dsnhanvien);
        linear_ql_doimatkhau = v.findViewById(R.id.linear_ql_doimatkhau);
        linear_ql_dangxuat = v.findViewById(R.id.linear_ql_dangxuat);
        linear_ql_makhuyenmai = v.findViewById(R.id.linear_ql_makhuyenmai);
        tv_ql_ten = v.findViewById(R.id.tv_ql_ten);
        tv_ql_chucvu = v.findViewById(R.id.tv_ql_chucvu);
        img_ql_anh = v.findViewById(R.id.img_ql_anh);
    }
}