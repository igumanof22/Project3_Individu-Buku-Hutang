package com.example.project31bukubon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerCardDetailAdapter extends RecyclerView.Adapter<RecyclerCardDetailAdapter.CardViewHolder> {
    private List<DetailHutang> detailList;
    private Context context;
    private String nama, nomor;

    public  RecyclerCardDetailAdapter(Context context, List<DetailHutang> detailList, String nama, String nomor){
        this.context = context;
        this.detailList = detailList;
        this.nama = nama;
        this.nomor = nomor;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new CardViewHolder(v);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.detail.setText(detailList.get(position).getDetail());
        holder.harga.setText(detailList.get(position).getHarga());
        holder.detailcardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateDetailActivity.class);
            intent.putExtra("id", detailList.get(position).getId());
            intent.putExtra("idhutang", detailList.get(position).getIdhutang());
            intent.putExtra("detail", detailList.get(position).getDetail());
            intent.putExtra("harga", detailList.get(position).getHarga());
            intent.putExtra("nama", nama);
            intent.putExtra("nomor", nomor);

            context.startActivity(intent);
            ((Activity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView detail, harga;
        CardView detailcardView;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = itemView.findViewById(R.id.detailDetail);
            harga = itemView.findViewById(R.id.detailHarga);
            detailcardView = itemView.findViewById(R.id.detailcardView);
        }
    }
}
