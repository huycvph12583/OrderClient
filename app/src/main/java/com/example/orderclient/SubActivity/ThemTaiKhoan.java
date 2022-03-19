package com.example.orderclient.SubActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderclient.DAO.NhanVienDao;
import com.example.orderclient.MODEL.NhanVien;
import com.example.orderclient.MainActivity;
import com.example.orderclient.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemTaiKhoan extends AppCompatActivity {

    Button btn_ttk_huybo, btn_ttk_them, btn_ttk_camera;
    EditText edt_ttk_manv, edt_ttk_tennv, edt_ttk_mk, edt_ttk_remk;
    ImageView img_ttk_photo;

    private int REQUEST_CODE_CAMERA = 1;
    private int REQUEST_CODE_FOLDER = 2;
    private byte[] hinhAnh;

    NhanVien nhanVien;
    NhanVienDao nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tai_khoan);
        setTitle("Thêm Tài Khoản");
        anhXa();
        nhanVienDao = new NhanVienDao(getApplicationContext());

        //Mở camera
        btn_ttk_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        //Chọn ảnh từ thư viện
        img_ttk_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btn_ttk_huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ttk_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertImg();
                nhanVien = new NhanVien();
                nhanVien.maNV = edt_ttk_manv.getText().toString();
                nhanVien.hoTen = edt_ttk_tennv.getText().toString();
                nhanVien.matKhau = edt_ttk_mk.getText().toString();
                nhanVien.anhNV = hinhAnh;
                if (validate() > 0) {
                    if (nhanVienDao.insert(nhanVien) > 0){
                        edt_ttk_manv.setText("");
                        edt_ttk_tennv.setText("");
                        edt_ttk_mk.setText("");
                        edt_ttk_remk.setText("");
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void anhXa() {
        btn_ttk_huybo = findViewById(R.id.btn_ttk_huybo);
        btn_ttk_them = findViewById(R.id.btn_ttk_them);
        btn_ttk_camera = findViewById(R.id.btn_ttk_camera);
        edt_ttk_manv = findViewById(R.id.edt_ttk_manv);
        edt_ttk_tennv = findViewById(R.id.edt_ttk_tennv);
        edt_ttk_mk = findViewById(R.id.edt_ttk_mk);
        edt_ttk_remk = findViewById(R.id.edt_ttk_remk);
        img_ttk_photo = findViewById(R.id.img_ttk_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_ttk_photo.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_ttk_photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void convertImg() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_ttk_photo.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        hinhAnh = byteArray.toByteArray();
    }

    private int validate() {
        int check = 1;
        if (edt_ttk_manv.getText().length() == 0 || edt_ttk_tennv.getText().length() == 0 || edt_ttk_mk.getText().length() == 0
                || edt_ttk_remk.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = edt_ttk_mk.getText().toString();
            String rePass = edt_ttk_remk.getText().toString();
            if (!pass.equals(rePass)){
                Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}