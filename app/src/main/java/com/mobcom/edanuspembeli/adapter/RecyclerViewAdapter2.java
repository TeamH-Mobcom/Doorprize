package com.mobcom.edanuspembeli.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcom.edanuspembeli.List_Notif;
import com.mobcom.edanuspembeli.R;

import com.mobcom.edanuspembeli.model.data_acc;

import java.util.ArrayList;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{


    //Deklarasi Variable
    private ArrayList<data_acc> listMenu;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter2(ArrayList<data_acc> listMenu, Context context) {
        this.listMenu = listMenu;
        this.context = context;
        this.listMenu = listMenu;
        this.context = context;
        listener = (List_Notif)context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Napem, Nama, Harga, Lokasi, Username, Usernow, Toko, Jumblah, Status;

        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            Napem = itemView.findViewById(R.id.atasnama);
            Nama = itemView.findViewById(R.id.nama);
            Harga = itemView.findViewById(R.id.harga);
            Lokasi= itemView.findViewById(R.id.lokasi);
            Username=itemView.findViewById(R.id.username);
            Usernow = itemView.findViewById(R.id.usernow);
            Toko= itemView.findViewById(R.id.toko);
            Jumblah=itemView.findViewById(R.id.jumblah);
            ListItem = itemView.findViewById(R.id.list_pen);
            Status = itemView.findViewById(R.id.status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notif, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Harga = listMenu.get(position).getHarga();
        final String Lokasi = listMenu.get(position).getLokasi();
        final String Nama = listMenu.get(position).getNama();
        final String Napem=listMenu.get(position).getNamapem();
        final String Toko=listMenu.get(position).getToko();
        final String Username=listMenu.get(position).getUsername();
        final String Usernow=listMenu.get(position).getUsernow();
        final String Jumblah=listMenu.get(position).getJumblah();
        final String Status= listMenu.get(position).getStatus();

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)

        holder.Nama.setText("Nama Menu: "+Nama);
        holder.Harga.setText("Harga: "+Harga);
        holder.Napem.setText("Atas Nama: "+Napem);
        holder.Lokasi.setText("Lokasi:  "+Lokasi);
        holder.Toko.setText("Toko: "+Toko);
        holder.Username.setText("Username: "+Username);
        holder.Usernow.setText("Usernow: "+Usernow);
        holder.Jumblah.setText("Jumblah Pesanan: "+Jumblah);
        holder.Status.setText(Status);

        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                final String[] action = {"Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){

                            case 0 :
                                listener.onDeleteData(listMenu.get(position), position);
                            break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
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
        void onDeleteData(data_acc data, int position);
    }


    dataListener listener;




}
