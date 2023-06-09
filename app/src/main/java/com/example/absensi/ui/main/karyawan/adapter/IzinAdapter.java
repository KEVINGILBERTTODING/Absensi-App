package com.example.absensi.ui.main.karyawan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.model.KeteranganModel;

import java.util.List;

public class IzinAdapter extends RecyclerView.Adapter<IzinAdapter.ViewHolder> {
    Context context;
    List<KeteranganModel> keteranganModelList;

    public IzinAdapter(Context context, List<KeteranganModel> keteranganModelList) {
        this.context = context;
        this.keteranganModelList = keteranganModelList;
    }

    @NonNull
    @Override
    public IzinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_izin_karyawan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IzinAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(keteranganModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvTanggal.setText(keteranganModelList.get(holder.getAdapterPosition()).getWaktu());
        holder.tvJenis.setText(keteranganModelList.get(holder.getAdapterPosition()).getKeterangan());

    }

    @Override
    public int getItemCount() {
        return keteranganModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTanggal, tvJenis;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJenis = itemView.findViewById(R.id.tvJenis);
            tvTanggal = itemView.findViewById(R.id.tvDate);
        }
    }
}
