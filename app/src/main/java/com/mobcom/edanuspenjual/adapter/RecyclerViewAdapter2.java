package com.mobcom.edanuspenjual.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcom.edanuspenjual.List_Penerimaan;
import com.mobcom.edanuspenjual.R;
import com.mobcom.edanuspenjual.TerimaPesanan;
import com.mobcom.edanuspenjual.model.data_acc;

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
        listener = (List_Penerimaan)context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Napem, Nama, Harga, Lokasi, Username, Usernow, Toko, Jumblah;

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
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_penerimaan, parent, false);
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

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)

        holder.Nama.setText("Nama Menu: "+Nama);
        holder.Harga.setText("Harga: "+Harga);
        holder.Napem.setText("Nama Pembeli: "+Napem);
        holder.Lokasi.setText("Lokasi:  "+Lokasi);
        holder.Toko.setText("Toko: "+Toko);
        holder.Username.setText("Username: "+Username);
        holder.Usernow.setText("Usernow: "+Usernow);
        holder.Jumblah.setText("Jumblah Pesanan: "+Jumblah);

        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                final String[] action = {"Action"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listMahasiswa, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataHarga", listMenu.get(position).getHarga());
                                bundle.putString("dataLokasi", listMenu.get(position).getLokasi());
                                bundle.putString("dataNama", listMenu.get(position).getNama());
                                bundle.putString("getPrimaryKey", listMenu.get(position).getKey());
                                bundle.putString("dataNamapem", listMenu.get(position).getNamapem());
                                bundle.putString("dataToko", listMenu.get(position).getToko());
                                bundle.putString("dataUsername", listMenu.get(position).getUsername());
                                bundle.putString("dataUsernow", listMenu.get(position).getUsernow());
                                bundle.putString("dataJumblah", listMenu.get(position).getJumblah());
                                Intent intent = new Intent(view.getContext(), TerimaPesanan.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

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
