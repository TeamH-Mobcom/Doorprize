package com.mobcom.edanuspembeli.model;



public class data_acc2 {
    String cek;
    String key;
    String name;

    public String getCek() {
        return cek;
    }

    public void setCek(String cek) {
        this.cek = cek;
    }

    public String getName() {
        return name;
    }

    public void setName(String user) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public data_acc2(){

    }


    public data_acc2(String name){
        this.name=name;
    }
}

