package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.DAO.NhanVienDao;
import com.example.orderclient.MODEL.NhanVien;
import com.example.orderclient.R;

public class DoiMatKhau extends AppCompatActivity {

    ImageView img_dmk_photo;
    TextView tv_dmk_tennv;
    EditText edt_dmk_mkc, edt_dmk_mkm, edt_dmk_remkm;
    Button btn_dmk_huybo, btn_dmk_doimk;

    Bundle bundleCV;
    String userChange;
    NhanVienDao nhanVienDao;
    NhanVien nhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        setTitle("Đổi Mật Khẩu");
        anhXa();

        ////Set ảnh và tên cho nhân viên
        bundleCV = getIntent().getExtras();
        userChange = bundleCV.getString("userchange");
        setDataView();

        btn_dmk_huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_dmk_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nhanVien != null) {
                    if (validate() > 0) {
                        nhanVien.matKhau = edt_dmk_mkm.getText().toString();
                        if (nhanVienDao.updatePass(nhanVien) > 0){
                            edt_dmk_mkc.setText("");
                            edt_dmk_mkm.setText("");
                            edt_dmk_remkm.setText("");
                            Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void anhXa() {
        img_dmk_photo = findViewById(R.id.img_dmk_photo);
        tv_dmk_tennv = findViewById(R.id.tv_dmk_tennv);
        edt_dmk_mkc = findViewById(R.id.edt_dmk_mkc);
        edt_dmk_mkm = findViewById(R.id.edt_dmk_mkm);
        edt_dmk_remkm = findViewById(R.id.edt_dmk_remkm);
        btn_dmk_doimk = findViewById(R.id.btn_dmk_doimk);
        btn_dmk_huybo = findViewById(R.id.btn_dmk_huybo);
    }

    private void setDataView() {
        if (bundleCV != null) {
            if (userChange != null) {
                if (!userChange.equalsIgnoreCase("admin")) {
                    nhanVienDao = new NhanVienDao(getApplicationContext());
                    nhanVien = nhanVienDao.getID(userChange);
                    tv_dmk_tennv.setText(nhanVien.hoTen);
                    if (nhanVien.anhNV != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.anhNV, 0, nhanVien.anhNV.length);
                        img_dmk_photo.setImageBitmap(bitmap);
                    }
                } else {
                    tv_dmk_tennv.setText("Không xác định");
                    edt_dmk_mkc.setEnabled(false);
                    edt_dmk_mkm.setEnabled(false);
                    edt_dmk_remkm.setEnabled(false);
                    edt_dmk_mkc.setHint("Bạn không phải là nhân viên");
                    edt_dmk_mkm.setHint("Bạn không phải là nhân viên");
                    edt_dmk_remkm.setHint("Bạn không phải là nhân viên");
                }
            } else {
                tv_dmk_tennv.setText("Không xác định");
                edt_dmk_mkc.setEnabled(false);
                edt_dmk_mkm.setEnabled(false);
                edt_dmk_remkm.setEnabled(false);
                edt_dmk_mkc.setHint("Bạn không phải là nhân viên");
                edt_dmk_mkm.setHint("Bạn không phải là nhân viên");
                edt_dmk_remkm.setHint("Bạn không phải là nhân viên");
            }
        } else {
            tv_dmk_tennv.setText("Không xác định");
            edt_dmk_mkc.setEnabled(false);
            edt_dmk_mkm.setEnabled(false);
            edt_dmk_remkm.setEnabled(false);
            edt_dmk_mkc.setHint("Bạn không phải là nhân viên");
            edt_dmk_mkm.setHint("Bạn không phải là nhân viên");
            edt_dmk_remkm.setHint("Bạn không phải là nhân viên");
        }
    }

    private int validate() {
        int check = 1;
        if (edt_dmk_mkc.getText().length() == 0 || edt_dmk_mkm.getText().length() == 0 || edt_dmk_remkm.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String passOld = nhanVien.matKhau;
            String pass = edt_dmk_mkm.getText().toString();
            String rePass = edt_dmk_remkm.getText().toString();
            if (!passOld.equals(edt_dmk_mkc.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng "+passOld, Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}