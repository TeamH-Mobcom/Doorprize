package com.mobcom.edanuspembeli.model;

public class data_menu {
    private String nama;
    private String harga;
    private String toko;
    private String key;
    private String user;
    private String username;
    private  String lokasi;
    private String napem;
    private String usernow;
    private String jumblah;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser () {
        return user;
    }

    public void setUser(String user ) {
        this.user = user ;
    }

    public String getJumblah() {
        return jumblah;
    }

    public void setJumblah(String jumblah) {
        this.jumblah = jumblah;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.key = username;
    }

    public String getUsernow() {
        return usernow;
    }

    public void setUsernow(String username) {
        this.usernow = usernow;
    }

    public String getToko() {
        return toko;
    }

    public void setToko(String toko) {
        this.toko = toko;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga () {
        return harga;
    }

    public void setHarga(String harga ) {
        this.harga = harga ;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNamapem() {
        return napem;
    }

    public void setNamapem(String napem) {
        this.napem = napem;
    }


    //Membuat Konstuktor kosong untuk membaca data snapshot
    public data_menu(){
    }

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    public data_menu(String toko, String nama, String harga,String username, String lokasi,String napem,String usernow, String jumblah) {
        this.toko = toko;
        this.nama = nama;
        this.harga = harga;
        this.username=username;
        this.napem=napem;
        this.lokasi=lokasi;
        this.usernow=usernow;
        this.jumblah=jumblah;
    }
    public data_menu( String lokasi,String napem) {
        this.napem=napem;
        this.lokasi=lokasi;
    }

}