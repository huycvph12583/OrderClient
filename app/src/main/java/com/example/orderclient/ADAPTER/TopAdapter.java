package com.example.orderclient.ADAPTER;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderclient.MODEL.Top;
import com.example.orderclient.R;

import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder>{

    private List<Top> lists;

    public TopAdapter(List<Top> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.TopViewHolder holder, int position) {
        Top top = lists.get(position);
        if (top == null) {
            return;
        }

        holder.tv_top_tenmon.setText(top.tenMon);
        holder.tv_top_soluong.setText(String.valueOf(top.sl));
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_top_tenmon, tv_top_soluong;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_top_tenmon = itemView.findViewById(R.id.tv_top_tenmon);
            tv_top_soluong = itemView.findViewById(R.id.tv_top_soluong);
        }
    }
}
