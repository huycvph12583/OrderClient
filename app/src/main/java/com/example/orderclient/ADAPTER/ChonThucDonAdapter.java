package com.example.orderclient.ADAPTER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;

import java.util.List;

public class ChonThucDonAdapter extends RecyclerView.Adapter<ChonThucDonAdapter.ThucDonViewHolder2>{

    private List<ThucDon> lists;
    private MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public ChonThucDonAdapter(List<ThucDon> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ThucDonViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chon_mon_item,parent,false);
        return new ThucDonViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonThucDonAdapter.ThucDonViewHolder2 holder, int position) {
        ThucDon thucDon = lists.get(position);
        if (thucDon == null) {
            return;
        }

        if (thucDon.anhMon != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(thucDon.anhMon, 0, thucDon.anhMon.length);
            holder.img_cm_anh.setImageBitmap(bitmap);
        }
        holder.tv_cm_tenmon.setText(thucDon.tenMon);
        holder.tv_cm_giamon.setText(String.valueOf(thucDon.donGia));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemClickListener.onClick(thucDon);
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

    public class ThucDonViewHolder2 extends RecyclerView.ViewHolder {

        private ImageView img_cm_anh;
        private TextView tv_cm_tenmon, tv_cm_giamon;

        public ThucDonViewHolder2(@NonNull View itemView) {
            super(itemView);

            img_cm_anh = itemView.findViewById(R.id.img_cm_anh);
            tv_cm_tenmon = itemView.findViewById(R.id.tv_cm_tenmon);
            tv_cm_giamon = itemView.findViewById(R.id.tv_cm_giamon);
        }
    }
}
