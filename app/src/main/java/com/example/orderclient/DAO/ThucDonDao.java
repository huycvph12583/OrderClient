package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.ThucDon;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ThucDonDao {

    private SQLiteDatabase db;

    public ThucDonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThucDon obj){
        ContentValues values = new ContentValues();
        values.put("tenMon", obj.tenMon);
        values.put("maLoaiTD", obj.maLoaiTD);
        values.put("anhMon", obj.anhMon);
        values.put("donGia", obj.donGia);
        return db.insert("ThucDon", null, values);
    }

    public int update(ThucDon obj){
        ContentValues values = new ContentValues();
        values.put("tenMon", obj.tenMon);
        values.put("maLoaiTD", obj.maLoaiTD);
        values.put("anhMon", obj.anhMon);
        values.put("donGia", obj.donGia);
        return db.update("ThucDon", values, "maMon=?", new String[]{String.valueOf(obj.maMon)});
    }

    public int delete(String id){
        return db.delete("ThucDon", "maMon=?", new String[]{id});
    }

    //Get tất cả thực đơn
    public List<ThucDon> getAll() {
        String sql = "SELECT * FROM ThucDon";
        return getData(sql);
    }

    //Get data theo tham số
    public ThucDon getID(String id) {
        String sql = "SELECT * FROM ThucDon WHERE maMon=?";
        List<ThucDon> list = getData(sql, id);
        return list.get(0);
    }

    //Get data theo loại td
    public List<ThucDon> getMaLoaiTD(String maLoaiTD) {
        String sql = "SELECT * FROM ThucDon WHERE maLoaiTD=?";
        List<ThucDon> list = getData(sql, maLoaiTD);
        return list;
    }

    //Get data nhiều tham số
    private List<ThucDon> getData(String sql, String...selectionArgs) {
        List<ThucDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThucDon obj = new ThucDon();
            obj.maMon = Integer.parseInt(c.getString(c.getColumnIndex("maMon")));
            obj.tenMon = c.getString(c.getColumnIndex("tenMon"));
            obj.maLoaiTD = Integer.parseInt(c.getString(c.getColumnIndex("maLoaiTD")));
            obj.anhMon = c.getBlob(c.getColumnIndex("anhMon"));
            obj.donGia = Integer.parseInt(c.getString(c.getColumnIndex("donGia")));
            list.add(obj);
        }
        return list;
    }
}
