package com.example.orderclient.MODEL;

import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    public int maHD;
    public int maMon;
    public int soLuong;
    public int tongTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maHD, int maMon, int soLuong, int tongTien) {
        this.maHD = maHD;
        this.maMon = maMon;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }
}
