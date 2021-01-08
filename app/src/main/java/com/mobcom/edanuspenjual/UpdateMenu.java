package com.mobcom.edanuspenjual;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobcom.edanuspenjual.model.data_menu;

public class UpdateMenu extends AppCompatActivity {

    //Deklarasi Variable
    private EditText nimBaru, namaBaru, jurusanBaru;
    private TextView User;
    private Button update;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekNIM, cekNama, cekJurusan, cekUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
        getSupportActionBar().setTitle("Update Data");
        nimBaru = findViewById(R.id.new_toko);
        namaBaru = findViewById(R.id.new_nama);
        jurusanBaru = findViewById(R.id.new_harga);
        User = findViewById(R.id.user);
        update = findViewById(R.id.update);

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Mahasiswa yang akan dicek
                cekNIM = nimBaru.getText().toString();
                cekNama = namaBaru.getText().toString();
                cekJurusan = jurusanBaru.getText().toString();
                cekUser=User.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if(isEmpty(cekNIM) || isEmpty(cekNama) || isEmpty(cekJurusan)){
                    Toast.makeText(UpdateMenu.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
                    data_menu setMahasiswa = new data_menu();
                    setMahasiswa.setToko(nimBaru.getText().toString());
                    setMahasiswa.setNama(namaBaru.getText().toString());
                    setMahasiswa.setHarga(jurusanBaru.getText().toString());
                    setMahasiswa.setUser(User.getText().toString());
                    updateMahasiswa(setMahasiswa);
                }
            }
        });
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getNIM = getIntent().getExtras().getString("dataNIM");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
        final String getUser = getIntent().getExtras().getString("User");
        nimBaru.setText(getNIM);
        namaBaru.setText(getNama);
        jurusanBaru.setText(getJurusan);
        User.setText(getUser);
    }

    //Proses Update data yang sudah ditentukan
    private void updateMahasiswa(data_menu mahasiswa){


        final String userID = auth.getCurrentUser().getUid();;
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        final String getNIM = getIntent().getExtras().getString("dataNIM");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getJurusan = getIntent().getExtras().getString("dataJurusan");
        final String getUser = getIntent().getExtras().getString("User");

        database.child("Admin")

                .child("Pedagang")
                .child(userID)
                .child(getKey)
                .setValue(mahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nimBaru.setText("");
                        namaBaru.setText("");
                        jurusanBaru.setText("");
                        User.setText(userID);
                        Toast.makeText(UpdateMenu.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        database.child("Admin")

                .child("Pedagang")
                .child("Data")
                .child(getKey)
                .setValue(mahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nimBaru.setText("");
                        namaBaru.setText("");
                        jurusanBaru.setText("");
                        User.setText(userID);
                        Toast.makeText(UpdateMenu.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        database.child("Admin")

                .child("Pedagang")
                .child(getNIM)
                .child(getKey)
                .setValue(mahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nimBaru.setText("");
                        namaBaru.setText("");
                        jurusanBaru.setText("");
                        User.setText(userID);
                        Toast.makeText(UpdateMenu.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
}