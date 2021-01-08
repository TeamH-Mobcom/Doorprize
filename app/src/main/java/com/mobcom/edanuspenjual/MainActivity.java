package com.mobcom.edanuspenjual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.edanuspenjual.model.data_acc;
import com.mobcom.edanuspenjual.model.data_acc2;
import com.mobcom.edanuspenjual.model.data_acc3;
import com.mobcom.edanuspenjual.model.data_menu;
import com.mobcom.edanuspenjual.model.data_menu2;
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

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 0007; // Any number you want
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out;
    Button btn_input_menu;
    Button btn_show_data;
    Button Lihatpesanan;
    Button btn_input_toko;
    FirebaseAuth auth;
    private TextView User;
    private data_menu datmen;
    private ArrayList<data_acc2> listMenu;
    private ArrayList<data_acc2> listData;
    private ArrayList<data_acc> listDataacc;
    private ArrayList<data_menu2>datamenu;
    private DatabaseReference reference;
    private Array adapter;
    private Context context;
    private String Toko;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);
        btn_input_menu = (Button) findViewById(R.id.button);
        btn_show_data = (Button) findViewById(R.id.showdata);
        Lihatpesanan = (Button) findViewById(R.id.lihatpesanan);
        btn_input_toko= findViewById(R.id.buttontoko);
        auth = FirebaseAuth.getInstance();
        Lihatpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData();
                startActivity(new Intent(MainActivity.this, List_Penerimaan.class));
            }
        });
        User = findViewById(R.id.user);
        ;
        btn_show_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData();
                startActivity(new Intent(MainActivity.this, List_Menu.class));
            }
        });
        btn_input_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData();
                startActivity(new Intent(MainActivity.this, input_toko.class));
            }
        });


        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);
                                startActivity(new Intent(MainActivity.this, temp.class));
                                showSignInOption();
                                btn_input_toko.setEnabled(false);


                            }


                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        // init provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), // Email Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() // Google Builder
        );



        showSignInOption();







    }

    private void showSignInOption() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        );
        btn_input_toko.setEnabled(false);    }

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
                String status = "Aktif";


                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                // set button sign out
                btn_sign_out.setEnabled(true);

                cekData2();
                cekData3();

            } else {
                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

        }



    }

    public void cekData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String getUserName = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Status").child(getUserName)
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



                        if(listMenu.isEmpty()) {

                            setData("another");
                            Toast.makeText(MainActivity.this, "Akun Belum Diaktivasi Admin", Toast.LENGTH_SHORT).show();
                        }else{
                            setData(listMenu.get(0).getKey());
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
            btn_input_menu.setEnabled(true);
            btn_show_data.setEnabled(true);
            Lihatpesanan.setEnabled(true);
            Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
        }else {
            btn_input_menu.setEnabled(true);
            btn_show_data.setEnabled(true);
            Lihatpesanan.setEnabled(true);
            startActivity(new Intent(MainActivity.this, Main2Activity.class));

            Toast.makeText(MainActivity.this, "Akun Belum Diaktivasi Admin", Toast.LENGTH_SHORT).show();


        }

        if (cekStatus == null){
            btn_input_menu.setEnabled(false);
            btn_show_data.setEnabled(false);
            Lihatpesanan.setEnabled(false);


        }







        }
        private void getAuth(){


    }
    public void cekData2() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String getUserName = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Status").child(getUserName)
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



                        if(listMenu.isEmpty()) {

                            setData2("another");

                        }else{
                            setData2(listMenu.get(0).getKey());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });



    }

    public void setData2(String data) {
        final int posisi=1;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String getUserName = user.getUid();
        String getUserName2=user.getDisplayName();
        String getUserName= user.getUid();
        final String cekStatus = data;
        final String status = getUserName2;


        if (status.equals(cekStatus)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference getReference;
            getReference = database.getReference(); // Mendapatkan Referensi dari Database
            DatabaseReference push1 = getReference.push();

            String key3 = push1.getKey();
            String status1 = "Aktif";

            getReference.child("Admin").child("CekStatus").child(getUserName)
                    .setValue(new data_acc3(getUserName2,status1))
                    .addOnSuccessListener(this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                            Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference getReference;
            getReference = database.getReference(); // Mendapatkan Referensi dari Database
            DatabaseReference push1 = getReference.push();

            String key3 = push1.getKey();
            String status1 = "Belum Aktif";

            getReference.child("Admin").child("CekStatus").child(getUserName)
                    .setValue(new data_acc3(getUserName2,status1))
                    .addOnSuccessListener(this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                            Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });

        }









    }
    public void cekData3(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String getUserName= user.getUid();
        final String getUserId = auth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        getReference = database.getReference(); // Mendapatkan Referensi dari Database
        DatabaseReference push1 = getReference.push();
        //DatabaseReference user=getReference.child(getUserName);
        //final String user1=user.getKey();
        getReference.child("Admin").child("Toko").child(getUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                data_menu2 dataMenu = dataSnapshot.getValue(data_menu2.class);

                final String key_menu = dataSnapshot.getKey();

                datamenu=new ArrayList<>();


                datamenu.add(dataMenu);
                if(dataSnapshot.getValue() == null){
                    btn_input_toko.setEnabled((true));


                }else {
                    Toko = datamenu.get(0).getToko();
                }
                //namaMenu = dataMenuNew.get(0).getNamaMenu;
                //totalKalori = dataMenuNew.get(1).getTotalKalori;
                //tigaTotalKalori = dataMenuNew.get(2).getTigaTotalKalori;

                if (Toko==null){
                    Log.w("error", "Failed to read value.");
                    btn_input_toko.setEnabled((true));

                }else{
                    btn_input_toko.setEnabled(false);

                    btn_input_menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cekData();
                            Intent goActionMenu = new Intent(MainActivity.this, Input_menu.class);

                            goActionMenu.putExtra("dataNIM", Toko);

                            startActivity(goActionMenu);


                        }

                    });



                }
                //totalKalori = dataMenu.getTotalKalori();
                //tigaTotalKalori = dataMenu.getTigaTotalKalori();

                // Log.d("value menu", "Value is: " + dataMenu);





            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });

    }

    }
