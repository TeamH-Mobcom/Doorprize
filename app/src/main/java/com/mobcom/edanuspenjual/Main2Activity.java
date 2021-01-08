package com.mobcom.edanuspenjual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.edanuspenjual.model.data_acc;
import com.mobcom.edanuspenjual.model.data_acc2;
import com.mobcom.edanuspenjual.model.data_acc3;
import com.mobcom.edanuspenjual.model.data_menu;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 0007; // Any number you want
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out;
    Button btn_input_menu;
    Button btn_show_data;
    Button Lihatpesanan;
    FirebaseAuth auth;
    private TextView User;
    private data_menu datmen;
    private ArrayList<data_acc2> listMenu;
    private ArrayList<data_acc2> listData;
    private ArrayList<data_acc> listDataacc;
    private DatabaseReference reference;
    private Array adapter;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("Tolong Tunggu Aktivasi Dari Admin");

        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);






        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout
                AuthUI.getInstance()
                        .signOut(Main2Activity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);
                                showSignInOption();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Main2Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // init provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), // Email Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() // Google Builder
        );



        cekData();
        showSignInOption();



    }

    private void showSignInOption() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String getUserName = user.getUid();
                String getUserName2=user.getDisplayName();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference getReference;
                getReference = database.getReference(); // Mendapatkan Referensi dari Database
                DatabaseReference push1 = getReference.push();

                String key3 = push1.getKey();
                String status = "Belum Aktif";

                getReference.child("Admin").child("CekStatus").child(getUserName)
                        .setValue(new data_acc3(getUserName2, status))
                        .addOnSuccessListener(this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                                Toast.makeText(Main2Activity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                // set button sign out
                btn_sign_out.setEnabled(true);

            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

        }




    }

    public void cekData() {

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Status")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        listMenu = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            data_acc2 mahasiswa = snapshot.getValue(data_acc2.class);


                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            mahasiswa.setKey(snapshot.getKey());



                            listMenu.add(mahasiswa);

                        }



                        // data_acc2 data=new data_acc2(listMenu.get(0).toString());
                        if(listMenu.isEmpty()) {
                            setData("another");
                            Toast.makeText(Main2Activity.this, "Akun Belum Diaktivasi Admin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                        }else{
                            setData(listMenu.get(0).getKey());
                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                            Toast.makeText(Main2Activity.this, "Akun Belum Diaktivasi Admin", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


    }

    public void setData(String data) {
        final int posisi=1;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String getUserName = user.getUid();
        String getUserName2=user.getDisplayName();
        final String cekStatus = data;
        final String status = getUserName2;
        if (status.equals(cekStatus)) {

            startActivity(new Intent(Main2Activity.this, MainActivity.class));
            Toast.makeText(Main2Activity.this, "Akun telah diaktifkan", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
            Toast.makeText(Main2Activity.this, "Data Loaded", Toast.LENGTH_SHORT).show();


        }








    }
    private void getAuth(){


    }

}