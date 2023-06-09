package com.example.absensi.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KaryawanModel implements Serializable {


        @SerializedName("id_karyawan")
        private String idKaryawan;

        @SerializedName("username")
        private String username;

        @SerializedName("password")
        private String password;

        @SerializedName("nama")
        private String nama;

        @SerializedName("tmp_tgl_lahir")
        private String tmpTglLahir;

        @SerializedName("jenkel")
        private String jenkel;

        @SerializedName("agama")
        private String agama;

        @SerializedName("alamat")
        private String alamat;

        @SerializedName("no_tel")
        private String noTel;

        @SerializedName("jabatan")
        private String jabatan;

        @SerializedName("foto")
        private String foto;

        // Add getters and setters here

        public KaryawanModel(String idKaryawan, String username, String password, String nama, String tmpTglLahir, String jenkel, String agama, String alamat, String noTel, String jabatan, String foto) {
            this.idKaryawan = idKaryawan;
            this.username = username;
            this.password = password;
            this.nama = nama;
            this.tmpTglLahir = tmpTglLahir;
            this.jenkel = jenkel;
            this.agama = agama;
            this.alamat = alamat;
            this.noTel = noTel;
            this.jabatan = jabatan;
            this.foto = foto;
        }

        public String getIdKaryawan() {
            return idKaryawan;
        }

        public void setIdKaryawan(String idKaryawan) {
            this.idKaryawan = idKaryawan;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTmpTglLahir() {
            return tmpTglLahir;
        }

        public void setTmpTglLahir(String tmpTglLahir) {
            this.tmpTglLahir = tmpTglLahir;
        }

        public String getJenkel() {
            return jenkel;
        }

        public void setJenkel(String jenkel) {
            this.jenkel = jenkel;
        }

        public String getAgama() {
            return agama;
        }

        public void setAgama(String agama) {
            this.agama = agama;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getNoTel() {
            return noTel;
        }

        public void setNoTel(String noTel) {
            this.noTel = noTel;
        }

        public String getJabatan() {
            return jabatan;
        }

        public void setJabatan(String jabatan) {
            this.jabatan = jabatan;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }



}
