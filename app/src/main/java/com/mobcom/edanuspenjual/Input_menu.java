package com.mobcom.edanuspenjual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.edanuspenjual.model.data_menu;
import com.mobcom.edanuspenjual.model.data_menu2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Input_menu extends AppCompatActivity implements View.OnClickListener{

    //Deklarasi Variable
    private ProgressBar progressBar;
    private EditText Toko, Nama, Harga;
    private TextView User;
    private FirebaseAuth auth;
    private Button  Simpan, ShowData, Gambar;
    private data_menu datmen;
    private ArrayList<data_menu2>datamenu;
    private ArrayList<data_menu2>datamenuu;
    private  static final int REQUEST_CODE_GALLERY=1;
    //Membuat Kode Permintaan
    private int RC_SIGN_IN = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_menu);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        //Inisialisasi ID (Button)

        Simpan = findViewById(R.id.save);
        Simpan.setOnClickListener(this);

        ShowData = findViewById(R.id.showdata);
        ShowData.setOnClickListener(this);


        auth = FirebaseAuth.getInstance(); //Mendapakan Instance Firebase Autentifikasi

        //Inisialisasi ID (EditText)

        Nama = findViewById(R.id.nama);
        Harga = findViewById(R.id.harga);
        User = findViewById(R.id.user);

        /*
         * Mendeteksi apakah ada user yang masuk, Jika tidak, maka setiap komponen UI akan dinonaktifkan
         * Kecuali Tombol Login. Dan jika ada user yang terautentikasi, semua fungsi/komponen
         * didalam User Interface dapat digunakan, kecuali tombol Logout
         */
        if (auth.getCurrentUser() == null) {
            defaultUI();
        } else {
            updateUI();
        }
    }
    //Tampilan Default pada Activity jika user belum terautentikasi
    private void defaultUI(){

        Simpan.setEnabled(false);
        ShowData.setEnabled(false);


        Nama.setEnabled(false);
        Harga.setEnabled(false);
        User.setEnabled(false);
    }

    //Tampilan User Interface pada Activity setelah user Terautentikasi
    private void updateUI(){

        Simpan.setEnabled(true);

        ShowData.setEnabled(true);

        Nama.setEnabled(true);
        Harga.setEnabled(true);
        User.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }



    // Mengecek apakah ada data yang kosong, akan digunakan pada Tutorial Selanjutnya
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }


    @Override
    public void onClick(View v) {
        final String getUserName = auth.getCurrentUser().getUid();
        final String getUserId = auth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        DatabaseReference push1 = getReference.push();
        DatabaseReference user=getReference.child(getUserName);
        final String user1=user.getKey();



        switch (v.getId()){


            case R.id.save:

                //Mendapatkan UserID dari pengguna yang Terautentikasi


                //Mendapatkan Instance dari Database@SuppressLint("SetTextI18n")
                //    private void getData () {
                //        //Menampilkan data dari item yang dipilih sebelumnya
                //        final String getNIM = getIntent().getExtras().getString("dataNIM");
                //        final String getNama = getIntent().getExtras().getString("dataNama");
                //        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
                //        final String key = getIntent().getExtras().getString("getPrimaryKey");
                //        final String getUsername = getIntent().getExtras().getString("User");
                //
                //        nimBaru.setText(getNIM);
                //        namaBaru.setText(getNama);
                //        jurusanBaru.setText(getJurusan);
                //        username.setText(getUsername);
                //        nama.setText("Nama Menu: " + getNama);
                //        harga.setText("Harga: " + getJurusan);
                //        key1.setText("Key: " + key);


                //Menyimpan Data yang diinputkan User kedalam Variable

                Intent intent = getIntent();

                String getToko = intent.getStringExtra("dataNIM");
                String getNama = Nama.getText().toString();
                String getHarga = Harga.getText().toString();
                String getUser=User.getText().toString();
                String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                String text = "";
                double r = (Math.floor(Math.random() * ((26 - 16) + 1)) + 16);
                for (int i = 0; i < r; i++) {
                    text += a.charAt((int) Math.floor(Math.random() * a.length()));
                }



                String key3 = push1.getKey();
                // Mengecek apakah ada data yang kosong
                if (isEmpty(getToko) && isEmpty(getNama) && isEmpty(getHarga)){
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(Input_menu.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else{

        /*
        Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
        Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
        */
                    getReference.child("Admin").child("Pedagang").child(getUserName).child(key3)
                            .setValue(new data_menu(getToko,getNama,getHarga,getUserName))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                    Nama.setText("");
                                    Harga.setText("");
                                    User.setText(getUserName);
                                    Toast.makeText(Input_menu.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                                }
                            });

                    getReference.child("Admin").child("Pedagang").child("Data").child(key3)
                            .setValue(new data_menu(getToko,getNama,getHarga, getUserName))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                    Nama.setText("");
                                    Harga.setText("");
                                    User.setText(getUserName);
                                    }
                            });
                    getReference.child("Admin").child("Pedagang").child(getToko).child(key3)
                            .setValue(new data_menu(getToko,getNama,getHarga, getUserName))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                    Nama.setText("");
                                    Harga.setText("");
                                    User.setText(getUserName);
                                    }
                            });





                }



            case R.id.showdata:
                startActivity(new Intent(Input_menu.this, List_Menu.class));
                break;


            }


    }

}
