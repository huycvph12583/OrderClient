package com.example.orderclient.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderclient.MODEL.LoaiThucDon;
import com.example.orderclient.R;

import java.util.ArrayList;

public class LoaiThucDonSpinnerAdapter extends ArrayAdapter<LoaiThucDon> {
    private Context context;
    private ArrayList<LoaiThucDon> lists;
    TextView tv_tenloaitd;

    public LoaiThucDonSpinnerAdapter(@NonNull Context context, ArrayList<LoaiThucDon> lists) {
        super(context,0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_thuc_don_item_spinner, null);
        }
        final LoaiThucDon item = lists.get(position);
        if (item != null) {
            tv_tenloaitd = v.findViewById(R.id.tv_tenloaitd);
            tv_tenloaitd.setText(item.tenLoaiTD);
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_thuc_don_item_spinner, null);
        }
        final LoaiThucDon item = lists.get(position);
        if (item != null) {
            tv_tenloaitd = v.findViewById(R.id.tv_tenloaitd);
            tv_tenloaitd.setText(item.tenLoaiTD);
        }
        return v;
    }
}