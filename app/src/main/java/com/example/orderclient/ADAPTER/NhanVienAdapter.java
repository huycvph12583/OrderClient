package com.example.orderclient.ADAPTER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.MODEL.NhanVien;
import com.example.orderclient.R;

import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanVienViewHolder>{

    private List<NhanVien> lists;

    public NhanVienAdapter(List<NhanVien> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhan_vien_item,parent,false);
        return new NhanVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = lists.get(position);
        if (nhanVien == null) {
            return;
        }

        if (nhanVien.anhNV != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.anhNV, 0, nhanVien.anhNV.length);
            holder.img_dsnv_anh.setImageBitmap(bitmap);
        }
        holder.tv_dsnv_manv.setText(nhanVien.maNV);
        holder.tv_dsnv_ten.setText(nhanVien.hoTen);
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_dsnv_anh;
        private TextView tv_dsnv_ten, tv_dsnv_manv;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);

            img_dsnv_anh = itemView.findViewById(R.id.img_dsnv_anh);
            tv_dsnv_ten = itemView.findViewById(R.id.tv_dsnv_ten);
            tv_dsnv_manv = itemView.findViewById(R.id.tv_dsnv_manv);
        }
    }

}
