package com.example.orderclient.SubActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.ChonThucDonAdapter;
import com.example.orderclient.ADAPTER.XacNhanThanhToanAdapter;
import com.example.orderclient.DAO.ChiTietHoaDonDao;
import com.example.orderclient.DAO.HoaDonDao;
import com.example.orderclient.DAO.KhuyenMaiDao;
import com.example.orderclient.MODEL.ChiTietHoaDon;
import com.example.orderclient.MODEL.HoaDon;
import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.MainActivity;
import com.example.orderclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XacNhanThanhToan extends AppCompatActivity {

    Button btn_xntt_quaylai, btn_xntt_thanhtoan, btn_xntt_apdung;
    TextView tv_xntt_tienhang, tv_xntt_khuyenmai, tv_xntt_tongthanhtoan;
    EditText edt_xntt_nhapma;
    private RecyclerView rcv_xacnhanthanhtoan;
    private XacNhanThanhToanAdapter xacNhanThanhToanAdapter;
    List<ChiTietHoaDon> lists;
    int pt, th, km, ttt;
    KhuyenMaiDao khuyenMaiDao;
    HoaDonDao hoaDonDao;
    HoaDon hoaDon;
    ChiTietHoaDonDao chiTietHoaDonDao;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_thanh_toan);
        setTitle("Xác Nhận Thanh Toán");
        anhXa();
        khuyenMaiDao = new KhuyenMaiDao(getApplicationContext());
        hoaDonDao = new HoaDonDao(getApplicationContext());
        chiTietHoaDonDao = new ChiTietHoaDonDao(getApplicationContext());

        //Xử lý hiển thị thực đơn lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_xacnhanthanhtoan.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_xacnhanthanhtoan.addItemDecoration(itemDecoration);
        xacNhanThanhToanAdapter = new XacNhanThanhToanAdapter(getApplicationContext(), getListCTHD());
        rcv_xacnhanthanhtoan.setAdapter(xacNhanThanhToanAdapter);

        //Xử lý nhập mã khuyến mại và đổ dữ liệu
        lists = getListCTHD();
        setDataView();

        btn_xntt_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_xntt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HoaDon> hoaDonList = hoaDonDao.getAll();
                if (hoaDonList == null) {
                    count = 1;
                } else {
                    count = hoaDonList.size() + 1;
                }

                hoaDon = new HoaDon();
                hoaDon.maNV = "Khong xac dinh";
                hoaDon.ngay = now();
                hoaDon.tienHang = th;
                hoaDon.khuyenMai = km;
                hoaDon.tienThanhToan = ttt;
                if (hoaDonDao.insert(hoaDon) > 0){
                    Toast.makeText(getApplicationContext(), "Lưu hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < lists.size(); i++) {
                        ChiTietHoaDon chiTietHoaDon = lists.get(i);
                        chiTietHoaDon.maHD = count;
                        if (chiTietHoaDonDao.insert(chiTietHoaDon) > 0){

                        } else {
                            Toast.makeText(getApplicationContext(), "Lưu CTHD thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "lưu hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<ChiTietHoaDon> getListCTHD() {
        ArrayList<ChiTietHoaDon> lists = new ArrayList<>();
        Intent intent = getIntent();
        lists = (ArrayList<ChiTietHoaDon>) intent.getBundleExtra("object_cthd").getSerializable("object_hoadon");
        return lists;
    }

    private void setDataView() {
        th = 0;
        km = 0;
        for (int i = 0; i < lists.size(); i++) {
            th += lists.get(i).tongTien;
        }
        ttt = th - km;
        tv_xntt_tienhang.setText(th+" VND");
        tv_xntt_khuyenmai.setText(km+" VND");
        tv_xntt_tongthanhtoan.setText((th - km)+" VND");

        btn_xntt_apdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_xntt_nhapma.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập mã khuyến mại trước",Toast.LENGTH_SHORT).show();
                } else {
                    KhuyenMai khuyenMai = khuyenMaiDao.getID(edt_xntt_nhapma.getText().toString());
                    if (khuyenMai.phanTram == 0) {
                        Toast.makeText(getApplicationContext(),"Mã khuyến mại không hợp lệ",Toast.LENGTH_SHORT).show();
                    } else {
                        pt = khuyenMai.phanTram;
                        km = th / 100 * pt;
                        ttt = th - km;
                        tv_xntt_khuyenmai.setText(String.valueOf(km)+" VND");
                        tv_xntt_tongthanhtoan.setText(String.valueOf(ttt)+" VND");
                        Toast.makeText(getApplicationContext(),"Đã áp dụng mã khuyến mại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void anhXa() {
        btn_xntt_quaylai = findViewById(R.id.btn_xntt_quaylai);
        btn_xntt_thanhtoan = findViewById(R.id.btn_xntt_thanhtoan);
        btn_xntt_apdung = findViewById(R.id.btn_xntt_apdung);
        rcv_xacnhanthanhtoan = findViewById(R.id.rcv_xacnhanthanhtoan);
        tv_xntt_tienhang = findViewById(R.id.tv_xntt_tienhang);
        tv_xntt_khuyenmai = findViewById(R.id.tv_xntt_khuyenmai);
        tv_xntt_tongthanhtoan = findViewById(R.id.tv_xntt_tongthanhtoan);
        edt_xntt_nhapma = findViewById(R.id.edt_xntt_nhapma);
    }

    private String now() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdfn = new SimpleDateFormat("yyyy-MM-dd");
        return sdfn.format(date).toString();
    }
}