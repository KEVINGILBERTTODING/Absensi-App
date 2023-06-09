package com.example.absensi.ui.main.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.model.KaryawanModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.ViewHolder> {
    Context context;
    List<KaryawanModel> karyawanModelList;



    public KaryawanAdapter(Context context, List<KaryawanModel> karyawanModelList) {
        this.context = context;
        this.karyawanModelList = karyawanModelList;
    }

    @NonNull
    @Override
    public KaryawanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_karyawan, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(karyawanModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvJabatan.setText(karyawanModelList.get(holder.getAdapterPosition()).getJabatan());
    }

    @Override
    public int getItemCount() {
        return karyawanModelList.size();
    }

    public void filter(ArrayList<KaryawanModel> filteredList) {
        karyawanModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJabatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJabatan = itemView.findViewById(R.id.tvJabatan);
        }
    }
}
