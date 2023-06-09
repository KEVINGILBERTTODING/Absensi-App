package com.example.absensi.ui.main.admin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.model.KaryawanModel;
import com.example.absensi.ui.main.admin.karyawan.EditKaryawanFragment;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvJabatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJabatan = itemView.findViewById(R.id.tvJabatan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new EditKaryawanFragment();
            Bundle bundle = new Bundle();
            bundle.putString("user_id", karyawanModelList.get(getAdapterPosition()).getIdKaryawan());
            bundle.putString("image", karyawanModelList.get(getAdapterPosition()).getFoto());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                    .addToBackStack(null).commit();

        }
    }
}
