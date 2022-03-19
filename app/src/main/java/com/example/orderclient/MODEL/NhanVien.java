package com.example.orderclient.MODEL;

public class NhanVien {
    public String maNV;
    public byte[] anhNV;
    public String hoTen;
    public String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNV, byte[] anhNV, String hoTen, String matKhau) {
        this.maNV = maNV;
        this.anhNV = anhNV;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }
}
