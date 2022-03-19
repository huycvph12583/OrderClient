package com.example.orderclient.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderclient.DATABASE.DbHelper;
import com.example.orderclient.MODEL.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDao {

    private SQLiteDatabase db;

    public NhanVienDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien obj){
        ContentValues values = new ContentValues();
        values.put("maNV", obj.maNV);
        values.put("anhNV", obj.anhNV);
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);
        return db.insert("NhanVien", null, values);
    }

    public int updatePass(NhanVien obj){
        ContentValues values = new ContentValues();
        values.put("anhNV", obj.anhNV);
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);
        return db.update("NhanVien", values, "maNV=?", new String[]{String.valueOf(obj.maNV)});
    }

    public int delete(String id){
        return db.delete("NhanVien", "maNV=?", new String[]{id});
    }

    //Get tất cả nhân viên
    public List<NhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    //Get data theo tham số
    public NhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }

    //Get data nhiều tham số
    private List<NhanVien> getData(String sql, String...selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            NhanVien obj = new NhanVien();
            obj.maNV = c.getString(c.getColumnIndex("maNV"));
            obj.anhNV = c.getBlob(c.getColumnIndex("anhNV"));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));
            list.add(obj);
        }
        return list;
    }

    //Check Login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=? AND matKhau=?";
        List<NhanVien> list = getData(sql, id, password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
