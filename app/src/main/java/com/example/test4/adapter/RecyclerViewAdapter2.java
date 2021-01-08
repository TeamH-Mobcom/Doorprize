package com.example.test4.adapter;

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

import com.example.test4.Actionbar;
import com.example.test4.List_User;
import com.example.test4.R;
import com.example.test4.model.data_user;

import java.util.ArrayList;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{


    //Deklarasi Variable
    private ArrayList<data_user> listMenu;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter2(ArrayList<data_user> listMenu, Context context) {
        this.listMenu = listMenu;
        this.context = context;
        this.listMenu = listMenu;
        this.context = context;
        listener = (List_User)context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView User, Key1, Status;

        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            User = itemView.findViewById(R.id.user);
            Key1=itemView.findViewById(R.id.key);
            Status = itemView.findViewById(R.id.status);
            ListItem = itemView.findViewById(R.id.list_user);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String Key1 = listMenu.get(position).getKey();
        final String user = listMenu.get(position).getCek();
        final String Status=listMenu.get(position).getStatus();



        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.User.setText("User: "+user);
        holder.Key1.setText("Key: "+Key1);
        holder.Status.setText("Status :"+Status);


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

                                bundle.putString("dataUser", listMenu.get(position).getCek());
                                bundle.putString("getPrimaryKey", listMenu.get(position).getKey());
                                bundle.putString("dataStatus", listMenu.get(position).getStatus());
                                Intent intent = new Intent(view.getContext(), Actionbar.class);
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
        void onDeleteData(data_user data, int position);
    }


    dataListener listener;




}

