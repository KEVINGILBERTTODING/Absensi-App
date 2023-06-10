package com.example.absensi.ui.main.admin.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.absensi.R;
import com.example.absensi.data.api.AdminServices;
import com.example.absensi.data.api.ApiConfig;
import com.example.absensi.data.model.KeteranganModel;
import com.example.absensi.data.model.ResponseModel;
import com.example.absensi.ui.main.karyawan.izin.PreviewImageFragment;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IzinAdapter extends RecyclerView.Adapter<IzinAdapter.ViewHolder> {
    Context context;
    List<KeteranganModel> keteranganModelList;
    AlertDialog progressDialog;
    AdminServices adminServices;

    public IzinAdapter(Context context, List<KeteranganModel> keteranganModelList) {
        this.context = context;
        this.keteranganModelList = keteranganModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsit_izin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(keteranganModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvTanggal.setText(keteranganModelList.get(holder.getAdapterPosition()).getWaktu());
        holder.tvJenis.setText(keteranganModelList.get(holder.getAdapterPosition()).getKeterangan());

    }

    @Override
    public int getItemCount() {
        return keteranganModelList.size();
    }
    public void filter(ArrayList<KeteranganModel> filteredList) {
        keteranganModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvTanggal, tvJenis;
        Button btnDelete, btnLihat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJenis = itemView.findViewById(R.id.tvJenis);
            tvTanggal = itemView.findViewById(R.id.tvDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnLihat = itemView.findViewById(R.id.btnDetail);
            itemView.setOnClickListener(this);
            adminServices = ApiConfig.getClient().create(AdminServices.class);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressBar("Loading", "Menghapus data", true);
                    adminServices.deleteIzin(keteranganModelList.get(getAdapterPosition()).getId())
                            .enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                                        showProgressBar("sds", "Sdd", false);
                                        showToast("success", "Berhasil menghapus data");
                                        keteranganModelList.remove(getAdapterPosition());
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

            btnLihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_izin);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    EditText etJenis, etAlasan, etNama;
                    TextView etWaktu;
                    ImageView ivIzin = dialog.findViewById(R.id.ivIzin);
                    Button btnOke = dialog.findViewById(R.id.btnOke);
                    etJenis = dialog.findViewById(R.id.etJenis);
                    etAlasan  = dialog.findViewById(R.id.etAlasan);
                    etWaktu = dialog.findViewById(R.id.tvWaktu);
                    etNama = dialog.findViewById(R.id.etNama);

                    Glide.with(context)
                                    .load(keteranganModelList.get(getAdapterPosition()).getBukti())
                                            .into(ivIzin);


                    etJenis.setText(keteranganModelList.get(getAdapterPosition()).getKeterangan());
                    etAlasan.setText(keteranganModelList.get(getAdapterPosition()).getAlasan());
                    etNama.setText(keteranganModelList.get(getAdapterPosition()).getNama());
                    etWaktu.setText(keteranganModelList.get(getAdapterPosition()).getWaktu());
                    dialog.show();

                    btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    ivIzin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Fragment fragment = new PreviewImageFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("image", keteranganModelList.get(getAdapterPosition()).getBukti());
                            fragment.setArguments(bundle);
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameAdmin, fragment).addToBackStack(null).commit();
                        }
                    });
                }
            });
        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new PreviewImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image", keteranganModelList.get(getAdapterPosition()).getBukti());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameKaryawan, fragment).addToBackStack(null).commit();
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
