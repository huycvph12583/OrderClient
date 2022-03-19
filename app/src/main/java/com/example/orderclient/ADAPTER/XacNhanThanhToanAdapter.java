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
import com.example.orderclient.SubActivity.XacNhanThanhToan;

import java.util.List;

public class XacNhanThanhToanAdapter extends RecyclerView.Adapter<XacNhanThanhToanAdapter.XNTTViewHolder>{

    private List<ChiTietHoaDon> lists;
    private Context context;



    public XacNhanThanhToanAdapter(Context context, List<ChiTietHoaDon> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public XNTTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xac_nhan_thanh_toan_item,parent,false);
        return new XNTTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanThanhToanAdapter.XNTTViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = lists.get(position);
        if (chiTietHoaDon == null) {
            return;
        }

        ThucDonDao thucDonDao = new ThucDonDao(context);
        ThucDon thucDon = thucDonDao.getID(String.valueOf(chiTietHoaDon.maMon));

        holder.tv_xntt_tenmon.setText(thucDon.tenMon);
        holder.tv_xntt_slndg.setText(chiTietHoaDon.soLuong+" x "+thucDon.donGia);
        holder.tv_xntt_thanhtien.setText("= "+chiTietHoaDon.tongTien+" VND");
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class XNTTViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_xntt_tenmon, tv_xntt_slndg, tv_xntt_thanhtien;

        public XNTTViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_xntt_tenmon = itemView.findViewById(R.id.tv_xntt_tenmon);
            tv_xntt_slndg = itemView.findViewById(R.id.tv_xntt_slndg);
            tv_xntt_thanhtien = itemView.findViewById(R.id.tv_xntt_thanhtien);
        }
    }
}
