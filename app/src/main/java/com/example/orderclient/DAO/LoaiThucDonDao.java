package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.LoaiThucDon;

import java.util.ArrayList;
import java.util.List;

public class LoaiThucDonDao {

    private SQLiteDatabase db;

    public LoaiThucDonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiThucDon obj){
        ContentValues values = new ContentValues();
        values.put("tenLoaiTD", obj.tenLoaiTD);
        return db.insert("LoaiThucDon", null, values);
    }

    public int update(LoaiThucDon obj){
        ContentValues values = new ContentValues();
        values.put("tenLoaiTD", obj.tenLoaiTD);
        return db.update("LoaiThucDon", values, "maLoaiTD=?", new String[]{String.valueOf(obj.maLoaiTD)});
    }

    public int delete(String id){
        return db.delete("LoaiThucDon", "maLoaiTD=?", new String[]{id});
    }

    //Get tất cả loại thực đơn
    public List<LoaiThucDon> getAll() {
        String sql = "SELECT * FROM LoaiThucDon";
        return getData(sql);
    }

    //Get data theo tham số
    public LoaiThucDon getID(String id) {
        String sql = "SELECT * FROM LoaiThucDon WHERE maLoaiTD=?";
        List<LoaiThucDon> list = getData(sql, id);
        return list.get(0);
    }

    //Get data nhiều tham số
    private List<LoaiThucDon> getData(String sql, String...selectionArgs) {
        List<LoaiThucDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiThucDon obj = new LoaiThucDon();
            obj.maLoaiTD = Integer.parseInt(c.getString(c.getColumnIndex("maLoaiTD")));
            obj.tenLoaiTD = c.getString(c.getColumnIndex("tenLoaiTD"));
            list.add(obj);
        }
        return list;
    }
}

