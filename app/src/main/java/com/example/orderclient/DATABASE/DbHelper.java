package com.example.orderclient.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "ORDER";
    static final int dbVersion = 1;

    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tạo bảng nhân viên
        String createTableNhanVien =
                "create table NhanVien (" +
                        "maNV TEXT PRIMARY KEY, " +
                        "hoTen TEXT NOT NULL, " +
                        "anhNV BLOB NOT NULL, " +
                        "matKhau TEXT NOT NULL)";
        db.execSQL(createTableNhanVien);

        //Tạo bảng loại thực đơn
        String createTableLoaiThucDon =
                "create table LoaiThucDon (" +
                        "maLoaiTD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tenLoaiTD TEXT NOT NULL)";
        db.execSQL(createTableLoaiThucDon);

        //Tạo bảng thực đơn
        String createTableThucDon =
                "create table ThucDon (" +
                        "maMon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maLoaiTD INTEGER REFERENCES LoaiThucDon(maLoaiTD), " +
                        "anhMon BLOB NOT NULL, " +
                        "tenMon TEXT NOT NULL, " +
                        "donGia INTERGER NOT NULL)";
        db.execSQL(createTableThucDon);

        //Tạo bảng hóa đơn
        String createTableHoaDon =
                "create table HoaDon (" +
                        "maHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "maNV TEXT REFERENCES NhanVien(maNV), " +
                        "ngay DATE NOT NULL, " +
                        "tienHang INTERGER NOT NULL, " +
                        "khuyenMai INTEGER NOT NULL, " +
                        "tienThanhToan INTEGER NOT NULL)";
        db.execSQL(createTableHoaDon);

        //Tạo bảng chi tiết hóa đơn
        String createTableChiTietHoaDon =
                "create table ChiTietHoaDon (" +
                        "maHD INTEGER REFERENCES HoaDon(maHD), " +
                        "maMon INTEGER REFERENCES ThucDon(maMon), " +
                        "soLuong INTERGER NOT NULL, " +
                        "tongTien INTEGER NOT NULL)";
        db.execSQL(createTableChiTietHoaDon);

        //Tạo bảng khuyến mại
        String createTableKhuyenMai =
                "create table KhuyenMai (" +
                        "maKM TEXT PRIMARY KEY, " +
                        "tenKM TEXT NOT NULL, " +
                        "phanTram INTERGER NOT NULL)";
        db.execSQL(createTableKhuyenMai);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropTableNhanVien = "drop table if exists NhanVien";
        db.execSQL(dropTableNhanVien);
        String dropTableLoaiThucDon = "drop table if exists LoaiThucDon";
        db.execSQL(dropTableLoaiThucDon);
        String dropTableThucDon = "drop table if exists ThucDon";
        db.execSQL(dropTableThucDon);
        String dropTableHoaDon = "drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);
        String dropTableChiTietHoaDon = "drop table if exists ChiTietHoaDon";
        db.execSQL(dropTableChiTietHoaDon);
        String dropTableKhuyenMai = "drop table if exists KhuyenMai";
        db.execSQL(dropTableKhuyenMai);

        onCreate(db);

    }
}

