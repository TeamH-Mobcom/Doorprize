package com.mobcom.edanuspenjual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobcom.edanuspenjual.model.data_menu2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class input_toko extends AppCompatActivity implements View.OnClickListener {

    private EditText Toko;
    private Button Save;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_toko);
        Save = findViewById(R.id.save_toko);
        Toko = findViewById(R.id.toko);
        Save.setOnClickListener(this);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            defaultUI();
        }else {
            updateUI();
        }

    }

    @Override
    public void onClick(View v) {
        //FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();
        //String getUserName = user3.getUid();
        final String getUserName = auth.getCurrentUser().getUid();
        final String getUserId = auth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        DatabaseReference push1 = getReference.push();
        DatabaseReference user=getReference.child(getUserName);
        final String user1=user.getKey();

        switch (v.getId()){


            case R.id.save_toko:

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
                String getToko = Toko.getText().toString();
                String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                String text = "";
                double r = (Math.floor(Math.random() * ((26 - 16) + 1)) + 16);
                for (int i = 0; i < r; i++) {
                    text += a.charAt((int) Math.floor(Math.random() * a.length()));
                }




                String key3 = push1.getKey();
                // Mengecek apakah ada data yang kosong
                if (isEmpty(getToko) ){
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(input_toko.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else{
                    Toko.setText("");
        /*
        Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
        Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
        */

                    getReference.child("Admin").child("Toko").child(getUserName)
                            .setValue(new data_menu2(getToko))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                }
                            });




                }


//                startActivity(new Intent(input_toko.this, MainActivity.class));

                break;
        }
    }
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }
    private void defaultUI(){



        Toko.setEnabled(false);
        }

    //Tampilan User Interface pada Activity setelah user Terautentikasi
    private void updateUI(){

        Toko.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }


}
