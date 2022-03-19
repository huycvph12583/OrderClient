package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.KhuyenMai;

import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDao {

    private SQLiteDatabase db;

    public KhuyenMaiDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KhuyenMai obj){
        ContentValues values = new ContentValues();
        values.put("maKM", obj.maKM);
        values.put("tenKM", obj.tenKM);
        values.put("phanTram", obj.phanTram);
        return db.insert("KhuyenMai", null, values);
    }

    public int update(KhuyenMai obj){
        ContentValues values = new ContentValues();
        values.put("tenKM", obj.tenKM);
        values.put("phanTram", obj.phanTram);
        return db.update("KhuyenMai", values, "maKM=?", new String[]{String.valueOf(obj.maKM)});
    }

    public int delete(String id){
        return db.delete("KhuyenMai", "maKM=?", new String[]{id});
    }

    //Get tất cả khuyến mãi
    public List<KhuyenMai> getAll() {
        String sql = "SELECT * FROM KhuyenMai";
        return getData(sql);
    }

    //Get data theo tham số
    public KhuyenMai getID(String id) {
        String sql = "SELECT * FROM KhuyenMai WHERE maKM=?";
        List<KhuyenMai> list = getData(sql, id);
        if (list.size() == 0){
            list.add(new KhuyenMai("a","a",0));
        }
        return list.get(0);
    }

    //Get data nhiều tham số
    private List<KhuyenMai> getData(String sql, String...selectionArgs) {
        List<KhuyenMai> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            KhuyenMai obj = new KhuyenMai();
            obj.maKM = c.getString(c.getColumnIndex("maKM"));
            obj.tenKM = c.getString(c.getColumnIndex("tenKM"));
            obj.phanTram = Integer.parseInt(c.getString(c.getColumnIndex("phanTram")));
            list.add(obj);
        }
        return list;
    }
}
