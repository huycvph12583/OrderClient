package com.example.orderclient.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.MODEL.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDao(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //Liệt kê top 10
    public List<Top> getTop() {
        String sqlTop = "SELECT maMon, count(maMon) as sl FROM ChiTietHoaDon GROUP BY maMon ORDER BY sl DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        ThucDonDao thucDonDao = new ThucDonDao(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()){
            Top top = new Top();
            ThucDon thucDon = thucDonDao.getID(c.getString(c.getColumnIndex("maMon")));
            top.tenMon = thucDon.tenMon;
            top.sl = Integer.parseInt(c.getString(c.getColumnIndex("sl")));
            list.add(top);
        }
        return list;
    }

    //Thống kê doanh thu
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(tienThanhToan) as doanhThu FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay,denNgay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    //Thống kê doanh thu hôm nay
    public int getDoanhThuToday(String ngay) {
        String sqlDoanhThu = "SELECT SUM(tienThanhToan) as doanhThuNgay FROM HoaDon WHERE ngay=?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{ngay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThuNgay"))));
            }catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    //Đếm hóa đơn hôm nay
    public int getSoLuongDonToday(String ngay) {
        String sqlSoLuongDon = "SELECT count(maHD) as soLuongDonNgay FROM HoaDon WHERE ngay=?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlSoLuongDon, new String[]{ngay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("soLuongDonNgay"))));
            }catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    //Đếm hóa đơn theo ngày
    public int getSoLuongDon(String tuNgay, String denNgay) {
        String sqlSoLuongDon = "SELECT count(maHD) as soLuongDon FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlSoLuongDon, new String[]{tuNgay,denNgay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("soLuongDon"))));
            }catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
