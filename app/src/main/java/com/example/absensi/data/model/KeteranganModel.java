package com.example.absensi.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeteranganModel implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("id_karyawan")
    private String idKaryawan;

    @SerializedName("nama")
    private String nama;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("alasan")
    private String alasan;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("bukti")
    private String bukti;

    public KeteranganModel(String id, String idKaryawan, String nama, String keterangan, String alasan, String waktu, String bukti) {
        this.id = id;
        this.idKaryawan = idKaryawan;
        this.nama = nama;
        this.keterangan = keterangan;
        this.alasan = alasan;
        this.waktu = waktu;
        this.bukti = bukti;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }
}
