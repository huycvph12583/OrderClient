package com.example.orderclient.FRAGMENT;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.example.orderclient.ADAPTER.MocThoiGianSpinnerAdapter;
import com.example.orderclient.ADAPTER.NhanVienAdapter;
import com.example.orderclient.ADAPTER.TopAdapter;
import com.example.orderclient.ANIMATION.TranslateAnimationUtil;
import com.example.orderclient.DAO.HoaDonDao;
import com.example.orderclient.DAO.ThongKeDao;
import com.example.orderclient.MODEL.ChiTietHoaDon;
import com.example.orderclient.MODEL.HoaDon;
import com.example.orderclient.MODEL.Top;
import com.example.orderclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ThongKeFragment extends Fragment {

    View v;
    Spinner spinner;
    MocThoiGianSpinnerAdapter spinnerAdapter;
    ArrayList<String> mocThoiGianList;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear, mMonth, mDay;
    TextView tv_tungay, tv_denngay;
    TextView tv_tk_doanhthu, tv_tk_soluong, tv_tk_doanhthu2;

    private RecyclerView rcv_thongke;
    private TopAdapter topAdapter;
    ThongKeDao thongKeDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        anhXa();
        thongKeDao = new ThongKeDao(getActivity());

        ////Xử lý hiển thị nhân viên lên rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcv_thongke.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcv_thongke.addItemDecoration(itemDecoration);
        topAdapter = new TopAdapter(getListTop());
        rcv_thongke.setAdapter(topAdapter);

        //Xử lý hiển thị mốc thời gian vào spinner
        String homNay = ("Hôm nay");
        String tatCa = ("Tất cả");
        String tuyChinh = ("Tùy chỉnh");
        mocThoiGianList = new ArrayList<>();
        mocThoiGianList.add(homNay);
        mocThoiGianList.add(tatCa);
        mocThoiGianList.add(tuyChinh);
        spinnerAdapter = new MocThoiGianSpinnerAdapter(getActivity(), mocThoiGianList);
        spinner.setAdapter(spinnerAdapter);

        //Xử Lý chọn item spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    tv_tk_doanhthu.setText(thongKeDao.getDoanhThuToday(now())+" VND");
                    tv_tk_doanhthu2.setText(thongKeDao.getDoanhThuToday(now())+" VND");
                    tv_tk_soluong.setText(thongKeDao.getSoLuongDonToday(now())+" đơn");
                }
                if (position == 1) {
                    HoaDonDao hoaDonDao = new HoaDonDao(getActivity());
                    List<HoaDon> lists = hoaDonDao.getAll();
                    int tongTien = 0;
                    for (int i = 0; i < lists.size(); i++) {
                        tongTien += lists.get(i).tienThanhToan;
                    }
                    tv_tk_doanhthu.setText(tongTien+" VND");
                    tv_tk_doanhthu2.setText(tongTien+" VND");
                    tv_tk_soluong.setText(lists.size()+" đơn");
                }
                if (position == 2) {
                    openDialog(Gravity.CENTER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    private List<Top> getListTop() {
        List<Top> lists = new ArrayList<>();
        lists = thongKeDao.getTop();
        return lists;
    }

    private void anhXa() {
        spinner = v.findViewById(R.id.sp_thongke);
        tv_tk_doanhthu = v.findViewById(R.id.tv_tk_doanhthu);
        tv_tk_soluong = v.findViewById(R.id.tv_tk_soluong);
        tv_tk_doanhthu2 = v.findViewById(R.id.tv_tk_doanhthu2);
        rcv_thongke = v.findViewById(R.id.rcv_thongke);
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
                tv_tk_doanhthu.setText(thongKeDao.getDoanhThu(tuNgay, denNgay)+" VND");
                tv_tk_doanhthu2.setText(thongKeDao.getDoanhThu(tuNgay, denNgay)+" VND");
                tv_tk_soluong.setText(thongKeDao.getSoLuongDon(tuNgay, denNgay)+" đơn");
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

    public String now() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdfn = new SimpleDateFormat("yyyy-MM-dd");
        return sdfn.format(date).toString();
    }
}