package com.example.orderclient.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderclient.R;

import java.util.ArrayList;

public class MocThoiGianSpinnerAdapter2 extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> lists;
    TextView tv_mocthoigian;

    public MocThoiGianSpinnerAdapter2(@NonNull Context context, ArrayList<String> lists) {
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
            v = inflater.inflate(R.layout.moc_thoi_gian_item_spinner2, null);
        }
        final String item = lists.get(position);
        if (item != null) {
            tv_mocthoigian = v.findViewById(R.id.tv_mocthoigian);
            tv_mocthoigian.setText(item);
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.moc_thoi_gian_item_spinner, null);
        }
        final String item = lists.get(position);
        if (item != null) {
            tv_mocthoigian = v.findViewById(R.id.tv_mocthoigian);
            tv_mocthoigian.setText(item);
        }
        return v;
    }
}