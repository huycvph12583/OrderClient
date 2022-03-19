package com.example.orderclient.ADAPTER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.INTERFACE.MyOnItemClickListener;
import com.example.orderclient.MODEL.ThucDon;
import com.example.orderclient.R;

import java.util.List;

public class ThucDonAdapter extends RecyclerView.Adapter<ThucDonAdapter.ThucDonViewHolder> {

    private Context context;
    private List<ThucDon> lists;
    private MyOnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public ThucDonAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThucDon> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThucDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thuc_don_item,parent,false);
        return new ThucDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThucDonAdapter.ThucDonViewHolder holder, int position) {
        ThucDon thucDon = lists.get(position);
        if (thucDon == null) {
            return;
        }

        if (thucDon.anhMon != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(thucDon.anhMon, 0, thucDon.anhMon.length);
            holder.img_thucdon.setImageBitmap(bitmap);
        }
        holder.tv_tenmon.setText(thucDon.tenMon);
        holder.tv_giamon.setText(Integer.toString(thucDon.donGia)+ " VND");
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

    public class ThucDonViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_thucdon;
        private TextView tv_tenmon, tv_giamon;

        public ThucDonViewHolder(@NonNull View itemView) {
            super(itemView);

            img_thucdon = itemView.findViewById(R.id.img_thucdon);
            tv_tenmon = itemView.findViewById(R.id.tv_td_tenmon);
            tv_giamon = itemView.findViewById(R.id.tv_td_giamon);
        }
    }
}
