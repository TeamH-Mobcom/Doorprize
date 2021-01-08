package com.mobcom.edanuspenjual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobcom.edanuspenjual.model.data_acc;
import com.mobcom.edanuspenjual.model.data_menu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TerimaPesanan extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference reference;
    private ArrayList<data_menu> dataMahasiswa;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText Toko,Nama,Harga,Jumblah,Username,Napem,Lokasi;

    private Button beli, tolak;
    FirebaseAuth auth;
    private String cekLokasi, cekNapem, cekJumblah;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima_pesanan);
        Toko = findViewById(R.id.new_toko);
        Nama = findViewById(R.id.new_nama);
        Harga = findViewById(R.id.new_harga);
        auth=FirebaseAuth.getInstance();

        beli = findViewById(R.id.belimenu);
        beli.setOnClickListener(this);

        tolak= findViewById(R.id.tolak);
        tolak.setOnClickListener(this);
    }
    @Override
    public void onClick (View v){
        final String userID = auth.getCurrentUser().getUid();
        final String getHarga = getIntent().getExtras().getString("dataHarga");
        final String getLokasi = getIntent().getExtras().getString("dataLokasi");
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getKey = getIntent().getExtras().getString("getPrimaryKey");
        final String getNamapem = getIntent().getExtras().getString("dataNamapem");
        final String getToko = getIntent().getExtras().getString("dataToko");
        final String getUsername = getIntent().getExtras().getString("dataUsername");
        final String getUsernow = getIntent().getExtras().getString("dataUsernow");
        final String getJumblah = getIntent().getExtras().getString("dataJumblah");
        String getTerima = "telah diterima mohon tunggu hingga pesanan anda sampai";
        String getTolak = "telah ditolak";

        DatabaseReference getRefrence;


        database = FirebaseDatabase.getInstance().getReference();
        getRefrence = database;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        DatabaseReference push1 = getReference.push();
        String key3 = push1.getKey();

        switch (v.getId()) {


            case R.id.belimenu:



                    // Mendapatkan Referensi dari Database
                database.getReference().child("Pemesanan").child(getUsernow).child(getKey).
                    setValue(new data_acc(getHarga, getLokasi, getNama,  getNamapem, getToko, getUsername,getUsernow, getJumblah, getTerima))
                    .addOnSuccessListener(this,new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            Toast.makeText(TerimaPesanan.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                        }
                    });
                database.getReference().child("Admin")

                    .child("Pembeli")
                    .child(userID)
                    .child(getKey)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(TerimaPesanan.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });




                break;

            case R.id.tolak:




                // Mendapatkan Referensi dari Database
                database.getReference().child("Pemesanan").child(getUsernow).child(getKey).
                        setValue(new data_acc(getHarga, getLokasi, getNama,  getNamapem, getToko, getUsername,getUsernow, getJumblah, getTolak))
                        .addOnSuccessListener(this,new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                Toast.makeText(TerimaPesanan.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                            }
                        });
                database.getReference().child("Admin")

                        .child("Pembeli")
                        .child(userID)
                        .child(getKey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(TerimaPesanan.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    break;
                }

        }
    }






