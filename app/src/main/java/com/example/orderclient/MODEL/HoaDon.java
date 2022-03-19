package com.example.orderclient.MODEL;

import java.io.Serializable;

public class HoaDon implements Serializable {
    public int maHD;
    public String maNV;
    public String ngay;
    public int tienHang;
    public int khuyenMai;
    public int tienThanhToan;

    public HoaDon() {
    }

    public HoaDon(int maHD, String maNV, String ngay, int tienHang, int khuyenMai, int tienThanhToan) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.ngay = ngay;
        this.tienHang = tienHang;
        this.khuyenMai = khuyenMai;
        this.tienThanhToan = tienThanhToan;
    }
}
