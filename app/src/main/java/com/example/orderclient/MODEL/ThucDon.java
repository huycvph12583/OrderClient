package com.example.orderclient.MODEL;

public class ThucDon {
    public int maMon;
    public int maLoaiTD;
    public byte[] anhMon;
    public String tenMon;
    public int donGia;

    public ThucDon() {
    }

    public ThucDon(int maMon, int maLoaiTD, byte[] anhMon, String tenMon, int donGia) {
        this.maMon = maMon;
        this.maLoaiTD = maLoaiTD;
        this.anhMon = anhMon;
        this.tenMon = tenMon;
        this.donGia = donGia;
    }
}
