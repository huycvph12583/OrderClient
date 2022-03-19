package com.example.orderclient.ADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.KhuyenMai;
import com.example.orderclient.R;

import java.util.List;

public class MaKhuyenMaiAdapter extends RecyclerView.Adapter<MaKhuyenMaiAdapter.MaKMViewHolder>{

    private List<KhuyenMai> lists;
    private MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public MaKhuyenMaiAdapter(List<KhuyenMai> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public MaKMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ma_khuyen_mai_item,parent,false);
        return new MaKMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaKhuyenMaiAdapter.MaKMViewHolder holder, int position) {
        KhuyenMai khuyenMai = lists.get(position);
        if (khuyenMai == null) {
            return;
        }

        holder.tv_mkm_ma.setText(khuyenMai.maKM);
        holder.tv_mkm_ten.setText(khuyenMai.tenKM);
        holder.tv_mkm_pt.setText("Giảm: "+khuyenMai.phanTram+"%");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemClickListener.onClick(khuyenMai);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class MaKMViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_mkm_ma, tv_mkm_ten, tv_mkm_pt;

        public MaKMViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_mkm_ma = itemView.findViewById(R.id.tv_mkm_ma);
            tv_mkm_ten = itemView.findViewById(R.id.tv_mkm_ten);
            tv_mkm_pt = itemView.findViewById(R.id.tv_mkm_pt);
        }
    }

}
