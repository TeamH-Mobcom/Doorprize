package com.mobcom.edanuspenjual.model;

public class data_menu2 {
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


    public data_menu2(){

    }

    public data_menu2(String toko){
        this.toko=toko;

    }
}
