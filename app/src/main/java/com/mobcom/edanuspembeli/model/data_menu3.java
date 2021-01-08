package com.mobcom.edanuspembeli.model;

public class data_menu3 {
    String key;
    String toko;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToko() {
        return toko;
    }

    public void setToko(String toko) {
        this.toko = toko;
    }


    public data_menu3(){

    }

    public data_menu3(String toko){
        this.toko=toko;

    }
}
