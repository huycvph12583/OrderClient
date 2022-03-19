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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.LoaiThucDonSpinnerAdapter;
import com.example.orderclient.ADAPTER.LoaiThucDonSpinnerAdapter2;
import com.example.orderclient.DAO.LoaiThucDonDao;
import com.example.orderclient.DAO.ThucDonDao;
import com.example.orderclient.FRAGMENT.ThucDonFragment;
import com.example.orderclient.MODEL.LoaiThucDon;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.MainActivity;
import com.example.orderclient.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

public class ThemThucDon extends AppCompatActivity {

    int REQUEST_CODE_CAMERA = 1;
    int REQUEST_CODE_FOLDER = 2;
    ImageView img_ttd_photo;
    Button btn_ttd_camera;
    EditText edt_ttd_tenmon, edt_ttd_giamon;

    LoaiThucDon cacheLTD;

    ThucDon thucDon;
    ThucDonDao thucDonDao;
    byte[] hinhAnh;

    Button btn_ttd_huybo, btn_ttd_luumon;
    Spinner sp_themthucdon;
    ArrayList<LoaiThucDon> listLoaiThucDon;
    LoaiThucDonDao loaiThucDonDao;
    LoaiThucDonSpinnerAdapter2 spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thuc_don);
        setTitle("Thêm Thực Đơn");
        anhXa();
        thucDonDao = new ThucDonDao(getApplicationContext());

        //Mở camera
        btn_ttd_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        //Chọn ảnh từ thư viện
        img_ttd_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        //Xử lý hiển thị loại tđ vào spinner
        addSpinnerMain();

        //Xử Lý chọn item spinner
        sp_themthucdon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cacheLTD = new LoaiThucDon();
                cacheLTD.maLoaiTD = listLoaiThucDon.get(position).maLoaiTD;
                cacheLTD.tenLoaiTD = listLoaiThucDon.get(position).tenLoaiTD;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_ttd_huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ttd_luumon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertImg();
                if (edt_ttd_tenmon.getText().length() == 0 || edt_ttd_giamon.getText().length() == 0 || cacheLTD == null) {
                    Toast.makeText(getApplicationContext(), "Vui lòng không bỏ trống loại thực đơn, tên món và giá món",Toast.LENGTH_SHORT).show();
                } else {
                    thucDon = new ThucDon();
                    thucDon.tenMon = edt_ttd_tenmon.getText().toString();
                    thucDon.donGia = Integer.valueOf(edt_ttd_giamon.getText().toString());
                    thucDon.maLoaiTD = cacheLTD.maLoaiTD;
                    thucDon.anhMon = hinhAnh;
                    if (thucDonDao.insert(thucDon) > 0){
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void anhXa() {
        img_ttd_photo = findViewById(R.id.img_ttd_photo);
        btn_ttd_camera = findViewById(R.id.btn_ttd_camera);
        sp_themthucdon = findViewById(R.id.sp_themthucdon);
        btn_ttd_huybo = findViewById(R.id.btn_ttd_huybo);
        btn_ttd_luumon = findViewById(R.id.btn_ttd_luumon);
        edt_ttd_giamon = findViewById(R.id.edt_ttd_giamon);
        edt_ttd_tenmon = findViewById(R.id.edt_ttd_tenmon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_ttd_photo.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_ttd_photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void convertImg() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_ttd_photo.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        hinhAnh = byteArray.toByteArray();
    }

    private void addSpinnerMain() {
        loaiThucDonDao = new LoaiThucDonDao(getApplicationContext());
        listLoaiThucDon = new ArrayList<LoaiThucDon>();
        listLoaiThucDon = (ArrayList<LoaiThucDon>) loaiThucDonDao.getAll();
        Collections.reverse(listLoaiThucDon);
        spinnerAdapter = new LoaiThucDonSpinnerAdapter2(getApplicationContext(), listLoaiThucDon);
        sp_themthucdon.setAdapter(spinnerAdapter);
    }
}