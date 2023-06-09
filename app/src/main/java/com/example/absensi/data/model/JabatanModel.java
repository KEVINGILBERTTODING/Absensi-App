package com.example.absensi.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JabatanModel implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("jabatan")
    String jabatan;

    public JabatanModel(String id, String jabatan) {
        this.id = id;
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
