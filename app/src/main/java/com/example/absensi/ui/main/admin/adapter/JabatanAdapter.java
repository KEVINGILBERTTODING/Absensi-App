package com.example.absensi.ui.main.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.model.JabatanModel;

import java.util.ArrayList;
import java.util.List;

public class JabatanAdapter extends RecyclerView.Adapter<JabatanAdapter.ViewHolder> {
    Context context;
    List<JabatanModel> jabatanModelList;

    public JabatanAdapter(Context context, List<JabatanModel> jabatanModelList) {
        this.context = context;
        this.jabatanModelList = jabatanModelList;
    }

    @NonNull
    @Override
    public JabatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_jabatan,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JabatanAdapter.ViewHolder holder, int position) {
        holder.tvJabatan.setText(jabatanModelList.get(holder.getAdapterPosition()).getJabatan());

    }

    @Override
    public int getItemCount() {
        return jabatanModelList.size();
    }

    public void filter (ArrayList<JabatanModel> filteredList) {
        jabatanModelList = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJabatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJabatan = itemView.findViewById(R.id.tvJabatan);
        }
    }
}
