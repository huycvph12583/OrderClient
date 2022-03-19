package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {

    private SQLiteDatabase db;

    public HoaDonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HoaDon obj){
        ContentValues values = new ContentValues();
        values.put("maNV", obj.maNV);
        values.put("ngay", obj.ngay);
        values.put("tienHang", obj.tienHang);
        values.put("khuyenMai", obj.khuyenMai);
        values.put("tienThanhToan", obj.tienThanhToan);
        return db.insert("HoaDon", null, values);
    }

    public int update(HoaDon obj){
        ContentValues values = new ContentValues();
        values.put("maNV", obj.maNV);
        values.put("ngay", obj.ngay);
        values.put("tienHang", obj.tienHang);
        values.put("khuyenMai", obj.khuyenMai);
        values.put("tienThanhToan", obj.tienThanhToan);
        return db.update("HoaDon", values, "maHD=?", new String[]{String.valueOf(obj.maHD)});
    }

    public int delete(String id){
        return db.delete("HoaDon", "maHD=?", new String[]{id});
    }

    //Get tất cả hóa đơn
    public List<HoaDon> getAll() {
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }

    //Get data hôm nay
    public List<HoaDon> getHoaDonToday(String ngay) {
        String sql = "SELECT * FROM HoaDon WHERE ngay=?";
        List<HoaDon> list = getData(sql, ngay);
        return list;
    }

    //Get data tùy chọn
    public List<HoaDon> getHoaDonTuyChon(String tuNgay, String denNgay) {
        String sql = "SELECT * FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<HoaDon> list = getData(sql, new String[]{tuNgay,denNgay});
        return list;
    }

    //Get data theo tham số
    public HoaDon getID(String id) {
        String sql = "SELECT * FROM HoaDon WHERE maHD=?";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }

    //Get data nhiều tham số
    private List<HoaDon> getData(String sql, String...selectionArgs) {
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            HoaDon obj = new HoaDon();
            obj.maHD = Integer.parseInt(c.getString(c.getColumnIndex("maHD")));
            obj.maNV = c.getString(c.getColumnIndex("maNV"));
            obj.ngay = c.getString(c.getColumnIndex("ngay"));
            obj.tienHang = Integer.parseInt(c.getString(c.getColumnIndex("tienHang")));
            obj.khuyenMai = Integer.parseInt(c.getString(c.getColumnIndex("khuyenMai")));
            obj.tienThanhToan = Integer.parseInt(c.getString(c.getColumnIndex("tienThanhToan")));
            list.add(obj);
        }
        return list;
    }
}
