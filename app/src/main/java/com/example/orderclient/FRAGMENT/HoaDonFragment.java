package com.example.orderclient.FRAGMENT;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderclient.ADAPTER.HoaDonAdapter;
import com.example.orderclient.ADAPTER.MocThoiGianSpinnerAdapter;
import com.example.orderclient.ADAPTER.MocThoiGianSpinnerAdapter2;
import com.example.orderclient.ANIMATION.TranslateAnimationUtil;
import com.example.orderclient.DAO.HoaDonDao;
import com.example.orderclient.DAO.ThongKeDao;
import com.example.orderclient.MODEL.HoaDon;
import com.example.orderclient.R;
import com.example.orderclient.SubActivity.ChonMon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HoaDonFragment extends Fragment {

    View v;
    private HoaDonAdapter hoaDonAdapter;
    private RecyclerView rcv_hoadon;
    private HoaDonDao hoaDonDao;
    private ThongKeDao thongKeDao;
    FloatingActionButton fab_hoadon;
    TextView tv_hoadon_ngay, tv_hoadon_tongtien;
    Spinner sp_hoadon;
    MocThoiGianSpinnerAdapter2 spinnerAdapter;
    ArrayList<String> mocThoiGianList;
    private int mYear, mMonth, mDay;
    private TextView tv_tungay, tv_denngay;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_hoa_don, container, false);
        anhXa();
        thongKeDao = new ThongKeDao(getActivity());

        ////Xử lý hiển thị thực đơn lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_hoadon.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv_hoadon.addItemDecoration(itemDecoration);
        hoaDonAdapter = new HoaDonAdapter(getActivity(), getListHoaDon());
        rcv_hoadon.setAdapter(hoaDonAdapter);
        rcv_hoadon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), fab_hoadon));


        //Xử lý hiển thị mốc thời gian vào spinne
        String homNay = ("Hôm nay");
        String tatCa = ("Tất cả");
        String tuyChinh = ("Tùy chỉnh");
        mocThoiGianList = new ArrayList<>();
        mocThoiGianList.add(homNay);
        mocThoiGianList.add(tatCa);
        mocThoiGianList.add(tuyChinh);
        spinnerAdapter = new MocThoiGianSpinnerAdapter2(getActivity(), mocThoiGianList);
        sp_hoadon.setAdapter(spinnerAdapter);

        //Xử Lý chọn item spinner
        sp_hoadon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    hoaDonAdapter = new HoaDonAdapter(getActivity(), getListHoaDonToday());
                    rcv_hoadon.setAdapter(hoaDonAdapter);
                    rcv_hoadon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), fab_hoadon));
                    tv_hoadon_ngay.setText(now());
                    tv_hoadon_tongtien.setText(thongKeDao.getDoanhThuToday(now())+" VND");
                }
                if (position == 1) {
                    hoaDonAdapter = new HoaDonAdapter(getActivity(), getListHoaDon());
                    rcv_hoadon.setAdapter(hoaDonAdapter);
                    rcv_hoadon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), fab_hoadon));
                    tv_hoadon_ngay.setText("Tất cả");
                    HoaDonDao hoaDonDao = new HoaDonDao(getActivity());
                    List<HoaDon> lists = hoaDonDao.getAll();
                    int tongTien = 0;
                    for (int i = 0; i < lists.size(); i++) {
                        tongTien += lists.get(i).tienThanhToan;
                    }
                    tv_hoadon_tongtien.setText(tongTien+" VND");
                }
                if (position == 2) {
                    openDialog(Gravity.CENTER);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChonMon.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void anhXa() {
        sp_hoadon = v.findViewById(R.id.sp_hoadon);
        fab_hoadon = v.findViewById(R.id.fab_hoadon);
        rcv_hoadon = v.findViewById(R.id.rcv_hoadon);
        tv_hoadon_ngay = v.findViewById(R.id.tv_hoadon_ngay);
        tv_hoadon_tongtien = v.findViewById(R.id.tv_hoadon_tongtien);
    }

    private List<HoaDon> getListHoaDon() {
        List<HoaDon> lists = new ArrayList<>();
        hoaDonDao = new HoaDonDao(getActivity());
        lists = hoaDonDao.getAll();
        Collections.reverse(lists);
        return lists;
    }

    private List<HoaDon> getListHoaDonToday() {
        List<HoaDon> lists = new ArrayList<>();
        hoaDonDao = new HoaDonDao(getActivity());
        lists = hoaDonDao.getHoaDonToday(now());
        Collections.reverse(lists);
        return lists;
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.moc_thoi_gian_dialog);

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

        tv_tungay = dialog.findViewById(R.id.tv_hd_tk_tungay);
        tv_denngay = dialog.findViewById(R.id.tv_hd_tk_denngay);
        Button btn_huy = dialog.findViewById(R.id.btn_hd_tk_huy);
        Button btn_dongy = dialog.findViewById(R.id.btn_hd_tk_dongy);

        tv_tungay.setOnClickListener(v1 -> {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
            d.show();
        });

        tv_denngay.setOnClickListener(v1 -> {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
            d.show();
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = tv_tungay.getText().toString();
                String denNgay = tv_denngay.getText().toString();
                tv_hoadon_ngay.setText(tuNgay+" đến "+denNgay);
                tv_hoadon_tongtien.setText(thongKeDao.getDoanhThu(tuNgay, denNgay)+" VND");
                List<HoaDon> lists = hoaDonDao.getHoaDonTuyChon(tuNgay, denNgay);
                Collections.reverse(lists);
                hoaDonAdapter = new HoaDonAdapter(getActivity(), lists);
                rcv_hoadon.setAdapter(hoaDonAdapter);
                rcv_hoadon.setOnTouchListener(new TranslateAnimationUtil(getActivity(), fab_hoadon));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = ((view, year, month, dayOfMonth) -> {
        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        tv_tungay.setText(sdf.format(c.getTime()));
    });

    DatePickerDialog.OnDateSetListener mDateDenNgay = ((view, year, month, dayOfMonth) -> {
        mYear = year;
        mMonth = month;
        mDay = dayOfMonth;
        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
        tv_denngay.setText(sdf.format(c.getTime()));
    });

    private String now() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdfn = new SimpleDateFormat("yyyy-MM-dd");
        return sdfn.format(date).toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hoaDonAdapter != null) {
            hoaDonAdapter.release();
        }
    }
}