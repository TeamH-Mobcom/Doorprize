package com.example.test4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test4.model.data_user;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Actionbar1 extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference reference;
    private ArrayList<data_user> dataMahasiswa;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText Toko,Nama,Harga,Jumblah,Username,Napem,Lokasi;

    private Button Activate, Banned;
    FirebaseAuth auth;
    private String cekLokasi, cekNapem, cekJumblah;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar1);

        Activate = findViewById(R.id.activate);
        Activate.setOnClickListener(this);

        Banned= findViewById(R.id.banned);
        Banned.setOnClickListener(this);
    }
    @Override
    public void onClick (View v){

        final String getUser = getIntent().getExtras().getString("dataUser");
        final String getKey = getIntent().getExtras().getString("getPrimaryKey");
        final String getStatus = getIntent().getExtras().getString("dataStatus");
        String getTerima = "telah diterima mohon tunggu hingga pesanan anda sampai";
        String getTolak = "telah ditolak";

        DatabaseReference getRefrence;


        database = FirebaseDatabase.getInstance().getReference();
        getRefrence = database;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        DatabaseReference push1 = getReference.push();
        String succes = "Aktif";
        String key3 = push1.getKey();

        switch (v.getId()) {


            case R.id.activate:



                // Mendapatkan Referensi dari Database
                database.getReference().child("Admin").child("Status1").child(getKey).child(getUser).
                        setValue(new data_user(getUser,succes))
                        .addOnSuccessListener(this,new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(Actionbar1.this, "User telah dinonaktifkan", Toast.LENGTH_SHORT).show();

                            }
                        });
                String status = "Dibanned";
                database.getReference().child("Admin").child("CekStatus1").child(getKey).
                        setValue(new data_user(getUser,status))
                        .addOnSuccessListener(this,new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(Actionbar1.this, "User telah dinonaktifkan", Toast.LENGTH_SHORT).show();

                            }
                        });





                break;

            case R.id.banned:




                // Mendapatkan Referensi dari Database

                String status1="Aktif";

                database.getReference().child("Admin")

                        .child("Status1")
                        .child(getKey)
                        .child(getUser)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Actionbar1.this, "Aktivasi Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        });
                database.getReference().child("Admin").child("CekStatus1").child(getKey).
                        setValue(new data_user(getUser,status1))
                        .addOnSuccessListener(this,new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(Actionbar1.this, "Aktivasi Berhasil", Toast.LENGTH_SHORT).show();

                            }
                        });


                break;
        }

    }
}