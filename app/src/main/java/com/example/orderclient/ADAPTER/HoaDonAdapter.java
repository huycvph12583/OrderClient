package com.example.orderclient.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.MODEL.HoaDon;
import com.example.orderclient.R;
import com.example.orderclient.SubActivity.ChiTietHoaDon;

import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{

    private List<HoaDon> lists;
    private Context context;

    public HoaDonAdapter(Context context, List<HoaDon> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoa_don_item,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.HoaDonViewHolder holder, int position) {
        final HoaDon hoaDon = lists.get(position);
        if (hoaDon == null) {
            return;
        }

        holder.tv_hd_mahoadon.setText("Mã HD: "+Integer.toString(hoaDon.maHD));
        holder.tv_hd_tongtien.setText(hoaDon.tienThanhToan+" VND");
        holder.layout_hoadon_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(hoaDon);
            }
        });
    }

    private void onClickGoToDetail(HoaDon hoaDon) {
        Intent intent = new Intent(context, ChiTietHoaDon.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_hoadon", hoaDon);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void release() {
        context = null;
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_hoadon_item;
        private TextView tv_hd_mahoadon, tv_hd_tongtien;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_hoadon_item = itemView.findViewById(R.id.layout_hoadon_item);
            tv_hd_mahoadon = itemView.findViewById(R.id.tv_hd_mahoadon);
            tv_hd_tongtien = itemView.findViewById(R.id.tv_hd_tongtien);
        }
    }
}
