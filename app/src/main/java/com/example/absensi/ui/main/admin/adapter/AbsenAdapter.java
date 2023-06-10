package com.example.absensi.ui.main.admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.data.api.AdminServices;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.model.AbsenModel;
import com.example.absensi.data.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenAdapter extends RecyclerView.Adapter<AbsenAdapter.ViewHolder> {
    Context context;
    List<AbsenModel> absenModelList;
    AdminServices adminServices;
    AlertDialog progressDialog;

    public AbsenAdapter(Context context, List<AbsenModel> absenModelList) {
        this.context = context;
        this.absenModelList = absenModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_absen_karyawan_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(absenModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvTanggal.setText(absenModelList.get(holder.getAdapterPosition()).getWaktu());

    }

    @Override
    public int getItemCount() {
        return absenModelList.size();
    }

    public void filter(ArrayList<AbsenModel> filteredList) {
        absenModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvTanggal;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvTanggal = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            adminServices = ApiConfig.getClient().create(AdminServices.class);


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar("Loading", "Menghapus data", true);
                    adminServices.deleteAbsen(absenModelList.get(getAdapterPosition()).getId())
                            .enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                                        showProgressBar("sds", "Sdd", false);
                                        showToast("success", "Berhasil menghapus data");
                                        absenModelList.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                    }else{
                                        showProgressBar("sds", "Sdd", false);
                                        showToast("error", "Gagal menghapus data");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    showProgressBar("sds", "Sdd", false);
                                    showToast("error", "Tidak ada koneksi internet");

                                }
                            });



                }
            });
        }
    }

    private void showProgressBar(String title, String message, boolean isLoading) {
        if (isLoading) {
            // Membuat progress dialog baru jika belum ada
            if (progressDialog == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setCancelable(false);
                progressDialog = builder.create();
            }
            progressDialog.show(); // Menampilkan progress dialog
        } else {
            // Menyembunyikan progress dialog jika ada
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
    private void showToast(String jenis, String text) {
        if (jenis.equals("success")) {
            Toasty.success(context, text, Toasty.LENGTH_SHORT).show();
        }else {
            Toasty.error(context, text, Toasty.LENGTH_SHORT).show();
        }


    }
}
