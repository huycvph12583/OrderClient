package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.ChiTietHoaDon;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDao {

    private SQLiteDatabase db;

    public ChiTietHoaDonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ChiTietHoaDon obj){
        ContentValues values = new ContentValues();
        values.put("maHD", obj.maHD);
        values.put("maMon", obj.maMon);
        values.put("soLuong", obj.soLuong);
        values.put("tongTien", obj.tongTien);
        return db.insert("ChiTietHoaDon", null, values);
    }

//    public int update(ChiTietHoaDon obj){
//        ContentValues values = new ContentValues();
//        values.put("soLuong", obj.soLuong);
//        return db.update("ChiTietHoaDon", values, "maHD=?", new String[]{String.valueOf(obj.maHD)});
//    }

    public int delete(String id){
        return db.delete("ChiTietHoaDon", "maHD=?", new String[]{id});
    }

    //Get tất cả chi tiết hóa đơn
    public List<ChiTietHoaDon> getAll() {
        String sql = "SELECT * FROM ChiTietHoaDon";
        return getData(sql);
    }

    //Get data theo tham số
    public List<ChiTietHoaDon> getID(String id) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE maHD=?";
        List<ChiTietHoaDon> list = getData(sql, id);
        return list;
    }

    //Get data nhiều tham số
    private List<ChiTietHoaDon> getData(String sql, String...selectionArgs) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ChiTietHoaDon obj = new ChiTietHoaDon();
            obj.maHD = Integer.parseInt(c.getString(c.getColumnIndex("maHD")));
            obj.maMon = Integer.parseInt(c.getString(c.getColumnIndex("maMon")));
            obj.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            obj.tongTien = Integer.parseInt(c.getString(c.getColumnIndex("tongTien")));
            list.add(obj);
        }
        return list;
    }
}

