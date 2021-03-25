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

import com.google.android.material.transition.Hold;

import java.util.List;

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.CardViewHolder> {
    private List<DataHutang> dataHutangList;
    private Context context;

    RecyclerCardAdapter(Context context, List<DataHutang> dataHutangList){
        this.context = context;
        this.dataHutangList = dataHutangList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.nama.setText(dataHutangList.get(position).getNama());
        holder.nomor.setText(dataHutangList.get(position).getNomor());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", dataHutangList.get(position).getId());
            intent.putExtra("nama", dataHutangList.get(position).getNama());
            intent.putExtra("nomor", dataHutangList.get(position).getNomor());
            intent.putExtra("total", dataHutangList.get(position).getTotal());

            context.startActivity(intent);
            ((Activity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return dataHutangList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nomor;
        CardView cardView;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.itemTitle);
            nomor = itemView.findViewById(R.id.itemSub);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
