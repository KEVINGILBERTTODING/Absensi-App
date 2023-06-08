package com.example.absensi.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AbsenModel implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("id_karyawan")
    String idKaryawan;
    @SerializedName("nama")
    String nama;
    @SerializedName("waktu")
    String waktu;

    public AbsenModel(String id, String idKaryawan, String nama, String waktu) {
        this.id = id;
        this.idKaryawan = idKaryawan;
        this.nama = nama;
        this.waktu = waktu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
