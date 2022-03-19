package com.example.orderclient.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.DAO.ThucDonDao;
import com.example.orderclient.MODEL.ChiTietHoaDon;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;

import java.util.List;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.CTHDViewHolder>{

    private List<ChiTietHoaDon> lists;
    private Context context;

    public ChiTietHoaDonAdapter(Context context, List<ChiTietHoaDon> lists) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public CTHDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chi_tiet_hoa_don_item,parent,false);
        return new CTHDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonAdapter.CTHDViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = lists.get(position);
        if (chiTietHoaDon == null) {
            return;
        }

        ThucDonDao thucDonDao = new ThucDonDao(context);
        ThucDon thucDon = thucDonDao.getID(String.valueOf(chiTietHoaDon.maMon));

       holder.tv_cthd_ten.setText(thucDon.tenMon);
       holder.tv_cthd_soluong.setText(String.valueOf(chiTietHoaDon.soLuong));
       holder.tv_cthd_dongia.setText(String.valueOf(thucDon.donGia));
       holder.tv_cthd_thanhtien.setText(String.valueOf(chiTietHoaDon.tongTien));
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class CTHDViewHolder extends RecyclerView.ViewHolder {

        TextView tv_cthd_ten, tv_cthd_soluong, tv_cthd_dongia, tv_cthd_thanhtien;

        public CTHDViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_cthd_ten = itemView.findViewById(R.id.tv_cthd_ten);
            tv_cthd_soluong = itemView.findViewById(R.id.tv_cthd_soluong);
            tv_cthd_dongia = itemView.findViewById(R.id.tv_cthd_dongia);
            tv_cthd_thanhtien = itemView.findViewById(R.id.tv_cthd_thanhtien);
        }
    }

}
