package com.example.orderclient.MODEL;

public class Top {
    public String tenMon;
    public int sl;

    public Top() {
    }

    //android:layout_gravity="bottom|right"
    //app:layout_anchorGravity="bottom|right|end"

    public Top(String tenMon, int sl) {
        this.tenMon = tenMon;
        this.sl = sl;
    }
}