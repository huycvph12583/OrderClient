package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.MaKhuyenMaiAdapter;
import com.example.orderclient.ANIMATION.TranslateAnimationUtil;
import com.example.orderclient.DAO.KhuyenMaiDao;
import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MaKhuyenMai extends AppCompatActivity {

    FloatingActionButton fab_makhuyenmai;
    private RecyclerView rcv_makhuyenmai;
    private MaKhuyenMaiAdapter maKhuyenMaiAdapter;
    KhuyenMaiDao khuyenMaiDao;
    KhuyenMai item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_khuyen_mai);
        setTitle("Mã Khuyến Mại");
        anhXa();

        ////Xử lý hiển thị mã khuyến mại lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_makhuyenmai.setLayoutManager(linearLayoutManager);
        maKhuyenMaiAdapter = new MaKhuyenMaiAdapter(getListMaKM());
        rcv_makhuyenmai.setAdapter(maKhuyenMaiAdapter);
        rcv_makhuyenmai.setOnTouchListener(new TranslateAnimationUtil(getApplicationContext(), fab_makhuyenmai));

        //Xử lý xóa món
        maKhuyenMaiAdapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(ThucDon thucDon) {

            }

            @Override
            public void onClick(KhuyenMai khuyenMai) {
                item = khuyenMai;
                openDiaLogXoaMaKM();
            }
        });

        //Xử lý thêm món
        fab_makhuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThemMaKM(Gravity.CENTER);
            }
        });
    }

    private void openDiaLogXoaMaKM() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MaKhuyenMai.this);
        builder.setTitle("Xác nhận xóa "+item.maKM);
        builder.setMessage("Bạn có chắc chắn muốn xóa "+item.maKM+" khỏi thực đơn ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Xóa", (alertDialog, which) -> {
            khuyenMaiDao.delete(String.valueOf(item.maKM));
            alertDialog.cancel();
            Intent intent = new Intent(getApplicationContext(), MaKhuyenMai.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Đã xóa "+item.maKM,Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Hủy", (alertDialog, which) -> {
            alertDialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    private List<KhuyenMai> getListMaKM() {
        List<KhuyenMai> lists = new ArrayList<>();
        khuyenMaiDao = new KhuyenMaiDao(this);
        lists = khuyenMaiDao.getAll();
        return lists;
    }

    private void anhXa() {
        fab_makhuyenmai = findViewById(R.id.fab_makhuyenmai);
        rcv_makhuyenmai  = findViewById(R.id.rcv_makhuyenmai);
    }

    private void updateRecyclerView() {
        maKhuyenMaiAdapter = new MaKhuyenMaiAdapter(getListMaKM());
        rcv_makhuyenmai.setAdapter(maKhuyenMaiAdapter);
        rcv_makhuyenmai.setOnTouchListener(new TranslateAnimationUtil(getApplicationContext(), fab_makhuyenmai));
    }

    private void openDialogThemMaKM(int gravity) {
        final Dialog dialog = new Dialog(MaKhuyenMai.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.ma_khuyen_mai_dialog_them);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edt_ma = dialog.findViewById(R.id.edt_makhuyenmai_dialog_ma);
        EditText edt_ten = dialog.findViewById(R.id.edt_makhuyenmai_dialog_ten);
        EditText edt_phantram = dialog.findViewById(R.id.edt_makhuyenmai_dialog_phantram);
        Button btn_huy = dialog.findViewById(R.id.btn_makhuyenmai_dialog_huy);
        Button btn_luu = dialog.findViewById(R.id.btn_makhuyenmai_dialog_luu);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_ma.getText().toString().length() == 0 || edt_ten.getText().length() == 0 || edt_phantram.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                } else {
                    int pt = Integer.valueOf(edt_phantram.getText().toString());
                    if (pt < 1 || pt > 100) {
                        Toast.makeText(getApplicationContext(),"Phần trăm giảm giá trong khoảng 1% đến 100%",Toast.LENGTH_SHORT).show();
                    } else {
                        KhuyenMai khuyenMai = new KhuyenMai();
                        khuyenMai.tenKM = edt_ten.getText().toString();
                        khuyenMai.maKM = edt_ma.getText().toString();
                        khuyenMai.phanTram = Integer.valueOf(edt_phantram.getText().toString());
                        if (khuyenMaiDao.insert(khuyenMai) > 0){
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MaKhuyenMai.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        dialog.show();
    }
}