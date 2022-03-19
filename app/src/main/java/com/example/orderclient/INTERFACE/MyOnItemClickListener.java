package com.example.orderclient.INTERFACE;

import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.MODEL.ThucDon;

public interface MyOnItemClickListener {
    void onClick(ThucDon thucDon);
    void onClick(KhuyenMai khuyenMai);
}
