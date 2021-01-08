package com.mobcom.edanuspembeli.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcom.edanuspembeli.List_Menu;
import com.mobcom.edanuspembeli.List_Toko;
import com.mobcom.edanuspembeli.R;

import com.mobcom.edanuspembeli.model.data_menu2;

import java.util.ArrayList;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder>{


    //Deklarasi Variable
    private ArrayList<data_menu2> listMenu;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter3(ArrayList<data_menu2> listMenu, Context context) {
        this.listMenu = listMenu;
        this.context = context;
        this.listMenu = listMenu;
        this.context = context;
        listener = (List_Toko)context;

    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Napem, Nama, Harga, Lokasi, Username, Usernow, Toko, Jumblah, Status;

        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita

            Toko= itemView.findViewById(R.id.toko);
            ListItem = itemView.findViewById(R.id.list_item);
           }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_toko, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Toko=listMenu.get(position).getToko();

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)

        holder.Toko.setText("Toko: "+Toko);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Bundle bundle = new Bundle();
                bundle.putString("dataNIM", listMenu.get(position).getToko());
                Intent intent = new Intent(view.getContext(), List_Menu.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listMenu.size();
    }
    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(data_menu2 data, int position);
    }


    dataListener listener;




}

