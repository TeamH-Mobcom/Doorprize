package com.mobcom.edanuspembeli;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.edanuspembeli.model.data_menu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class Pembelian extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference reference;
    private ArrayList<data_menu> dataMahasiswa;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText nimBaru, namaBaru, jurusanBaru, username, namapembeli, lokasi, jumblah;
    private TextView nama, harga;
    private Button beli;
    FirebaseAuth auth;
    private String cekLokasi, cekNapem, cekJumblah;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);
        nimBaru = findViewById(R.id.new_toko);
        namaBaru = findViewById(R.id.new_nama);
        jurusanBaru = findViewById(R.id.new_harga);
        nama = findViewById(R.id.teks1);
        harga = findViewById(R.id.teks3);
       auth=FirebaseAuth.getInstance();
       jumblah = findViewById(R.id.jumblah);
        username = findViewById(R.id.username);
        beli = findViewById(R.id.belimenu);
        beli.setOnClickListener(this);
        namapembeli = findViewById(R.id.atasnama);
        lokasi = findViewById(R.id.lokasi);
        getData();
    }
        @Override
        public void onClick (View v){
            final String userID = auth.getCurrentUser().getUid();

            switch (v.getId()) {


                case R.id.belimenu:


                    cekNapem = namapembeli.getText().toString();
                    cekLokasi = lokasi.getText().toString();
                    cekJumblah=jumblah.getText().toString();
                    //Mengecek agar tidak ada data yang kosong, saat proses update
                    if (isEmpty(cekNapem) || isEmpty(cekLokasi) ||isEmpty(cekJumblah) ) {
                        Toast.makeText(Pembelian.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                    } else {

                        final String getKey = getIntent().getExtras().getString("getPrimaryKey");
                        final String getUsername = getIntent().getExtras().getString("User");
                        //Menampilkan data dari item yang dipilih sebelumnya
                        final String getNIM = getIntent().getExtras().getString("dataNIM");
                        final String getNama = getIntent().getExtras().getString("dataNama");
                        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
                        final String key = getIntent().getExtras().getString("getPrimaryKey");
                        String getLokasi = lokasi.getText().toString();
                        String getNapem = namapembeli.getText().toString();
                        String getJumblah = jumblah.getText().toString();

                        DatabaseReference getRefrence;


                        database = FirebaseDatabase.getInstance().getReference();
                        getRefrence = database;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference getReference;
                        getReference = database.getReference(); // Mendapatkan Referensi dari Database
                        DatabaseReference push1 = getReference.push();
                        String key3 = push1.getKey();
                         // Mendapatkan Referensi dari Database

                        getRefrence.child("Admin")
                                .child("Pembeli")
                                .child(getUsername)
                                .child(key3)
                                .setValue(new data_menu(getNIM, getNama, getJurusan, getUsername, getNapem, getLokasi,userID, getJumblah))
                                .addOnSuccessListener(this,new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        nimBaru.setText(getNIM);
                                        namaBaru.setText(getNama);
                                        jurusanBaru.setText(getJurusan);
                                        username.setText(getUsername);
                                        namapembeli.setText("");
                                        lokasi.setText("");
                                        jumblah.setText("");

                                        Toast.makeText(Pembelian.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                                    }
                                });






                    }
                    break;
            }
        }






    @SuppressLint("SetTextI18n")
    private void getData () {
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getNIM = getIntent().getExtras().getString("dataNIM");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
        final String key = getIntent().getExtras().getString("getPrimaryKey");
        final String getUsername = getIntent().getExtras().getString("User");

        nimBaru.setText(getNIM);
        namaBaru.setText(getNama);
        jurusanBaru.setText(getJurusan);
        username.setText(getUsername);
        nama.setText("Nama Menu: " + getNama);
        harga.setText("Harga: " + getJurusan);



    }
}
