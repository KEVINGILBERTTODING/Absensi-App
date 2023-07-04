package com.example.absensi.ui.main.karyawan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.model.AbsenModel;

import java.util.List;

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.ViewHolder> {
    Context context;
    List<AbsenModel> absenModelList;

    public AbsenAdapter(Context context, List<AbsenModel> absenModelList) {
        this.context = context;
        this.absenModelList = absenModelList;
    }

    @NonNull
    @Override
    public AbsenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_absen_karyawan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(absenModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvTanggal.setText(absenModelList.get(holder.getAdapterPosition()).getWaktu());
        holder.tvJenis.setText(absenModelList.get(holder.getAdapterPosition()).getJenis());

        if (absenModelList.get(holder.getAdapterPosition()).getJenis().equals("Pulang")) {
            holder.tvJenis.setTextColor(context.getColor(R.color.red));

        }

    }

    @Override
    public int getItemCount() {
        return absenModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTanggal, tvJenis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvDate);
            tvJenis = itemView.findViewById(R.id.tvJenis);
        }
    }
}
