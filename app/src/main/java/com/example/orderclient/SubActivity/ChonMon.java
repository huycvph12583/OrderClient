package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.ChonThucDonAdapter;
import com.example.orderclient.DAO.ThucDonDao;
import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.ChiTietHoaDon;
import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChonMon extends AppCompatActivity {

    Button btn_cm_huybo, btn_cm_tieptheo;
    private RecyclerView rcv_chonmon;
    private ChonThucDonAdapter chonThucDonAdapter;
    ThucDonDao thucDonDao;
    ThucDon item;
    ArrayList<ChiTietHoaDon> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);
        setTitle("Chọn Món");
        anhXa();
        item = new ThucDon();
        lists = new ArrayList<>();

        ////Xử lý hiển thị thực đơn lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_chonmon.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_chonmon.addItemDecoration(itemDecoration);
        chonThucDonAdapter = new ChonThucDonAdapter(getListThucDon());
        rcv_chonmon.setAdapter(chonThucDonAdapter);

        chonThucDonAdapter.setMyOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onClick(ThucDon thucDon) {
                item = thucDon;
                openDialog(Gravity.CENTER);
            }

            @Override
            public void onClick(KhuyenMai khuyenMai) {

            }
        });

        btn_cm_huybo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_cm_tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.size() != 0) {
                    Intent intent = new Intent(ChonMon.this, XacNhanThanhToan.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("object_hoadon", lists);
                    intent.putExtra("object_cthd", bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn món trước",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(ChonMon.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.nhap_so_luong_dialog);

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

        TextView tv_cm_dialog_tenmon = dialog.findViewById(R.id.tv_cm_dialog_tenmon);
        ImageView img_cm_dialog_anhmon = dialog.findViewById(R.id.img_cm_dialog_anhmon);
        ImageView img_cm_dialog_giam = dialog.findViewById(R.id.img_cm_dialog_giam);
        ImageView img_cm_dialog_tang = dialog.findViewById(R.id.img_cm_dialog_tang);
        EditText edt_soluong = dialog.findViewById(R.id.edt_cm_dialog_nhapsl);
        Button btn_huy = dialog.findViewById(R.id.btn_cm_dialog_huy);
        Button btn_them = dialog.findViewById(R.id.btn_cm_dialog_them);

        //Set thông tin món đã chọn lên dialog
        tv_cm_dialog_tenmon.setText("Chọn Món ("+item.tenMon+")");
        if (item.anhMon != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.anhMon, 0, item.anhMon.length);
            img_cm_dialog_anhmon.setImageBitmap(bitmap);
        }
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).maMon == item.maMon) {
                edt_soluong.setText(String.valueOf(lists.get(i).soLuong));
                break;
            }
        }

        img_cm_dialog_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_soluong.getText().length() == 0) {
                    edt_soluong.setText("0");
                } else {
                    int sl = Integer.valueOf(edt_soluong.getText().toString());
                    if (sl > 0)
                    sl--;
                    edt_soluong.setText(String.valueOf(sl));
                }
            }
        });

        img_cm_dialog_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_soluong.getText().length() == 0) {
                    edt_soluong.setText("1");
                } else {
                    int sl = Integer.valueOf(edt_soluong.getText().toString());
                    sl++;
                    edt_soluong.setText(String.valueOf(sl));
                }
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_soluong.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),"Vui lòng không bỏ trống số lượng",Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.valueOf(edt_soluong.getText().toString()) == 0) {
                        for (int i = 0; i < lists.size(); i++) {
                            if (lists.get(i).maMon == item.maMon) {
                                lists.remove(i);
                                break;
                            }
                        }
                        dialog.dismiss();
                        btn_cm_tieptheo.setText("TIẾP THEO ("+countSL()+")");
                    } else {
                        for (int i = 0; i < lists.size(); i++) {
                            if (lists.get(i).maMon == item.maMon) {
                                lists.remove(i);
                                break;
                            }
                        }
                        com.example.orderclient.MODEL.ChiTietHoaDon chiTietHoaDon = new com.example.orderclient.MODEL.ChiTietHoaDon();
                        ThucDonDao thucDonDao = new ThucDonDao(getApplicationContext());
                        chiTietHoaDon.maHD = 0;
                        chiTietHoaDon.maMon = item.maMon;
                        chiTietHoaDon.soLuong = Integer.valueOf(edt_soluong.getText().toString());
                        chiTietHoaDon.tongTien = item.donGia * chiTietHoaDon.soLuong;
                        lists.add(chiTietHoaDon);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Đã thêm: "+thucDonDao.getID(String.valueOf(chiTietHoaDon.maMon)).tenMon+", Số lượng: "+chiTietHoaDon.soLuong,Toast.LENGTH_SHORT).show();
                        btn_cm_tieptheo.setText("TIẾP THEO ("+countSL()+")");
                    }
                }
            }
        });

        dialog.show();
    }

    private int countSL() {
        int count = 0;
        for (int i = 0; i < lists.size(); i++) {
            count += lists.get(i).soLuong;
        }
        return count;
    }

    private List<ThucDon> getListThucDon() {
        List<ThucDon> lists = new ArrayList<>();
        thucDonDao = new ThucDonDao(this);
        lists = thucDonDao.getAll();
        return lists;
    }

    private void anhXa() {
        btn_cm_huybo = findViewById(R.id.btn_cm_huybo);
        btn_cm_tieptheo = findViewById(R.id.btn_cm_tieptheo);
        rcv_chonmon = findViewById(R.id.rcv_chonmon);
    }
}