package com.example.absensi.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthModel implements Serializable {
    @SerializedName("status")
    Integer status;
    @SerializedName("nama")
    String nama;
    @SerializedName("user_id")
    String userId;
    @SerializedName("role")
    Integer role;
    @SerializedName("jabatan")
    String jabatan;
    @SerializedName("message")
    String mesage;

    public AuthModel(Integer status, String message, String nama, String userId, Integer role, String jabatan) {
        this.status = status;
        this.nama = nama;
        this.userId = userId;
        this.role = role;
        this.jabatan = jabatan;
        this.mesage = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getMesage() {
        return mesage;
    }
}
