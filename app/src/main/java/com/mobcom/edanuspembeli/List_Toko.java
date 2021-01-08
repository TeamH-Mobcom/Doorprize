package com.mobcom.edanuspembeli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobcom.edanuspembeli.adapter.RecyclerViewAdapter3;
import com.mobcom.edanuspembeli.adapter.RecyclerViewAdapter4;
import com.mobcom.edanuspembeli.model.data_acc2;
import com.mobcom.edanuspembeli.model.data_acc3;
import com.mobcom.edanuspembeli.model.data_menu;
import com.mobcom.edanuspembeli.model.data_menu2;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class List_Toko extends AppCompatActivity implements RecyclerViewAdapter3.dataListener, RecyclerViewAdapter4.dataListener  {

    //Deklarasi Variable untuk RecyclerView
    private RecyclerView  recyclerView, recyclerView2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference reference;
    private ArrayList<data_menu2> dataMahasiswa;
    private ArrayList<data_menu2> dataMenu;
    private ArrayList<data_menu> dataMenu2;
    private EditText editText;

    private FirebaseAuth auth;
    private ArrayList<data_acc2> listMenu;
    private static final int MY_REQUEST_CODE = 0007; // Any number you want
    List<AuthUI.IdpConfig> providers;
    Button btn_sign_out, notifikasi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_list__toko);

        recyclerView= findViewById(R.id.datalist);
        //recyclerView2=findViewById(R.id.datalist2);
        //recyclerView2= findViewById(R.id.datalist2);
        getSupportActionBar().setTitle("Daftar Menu dan Toko");
        auth = FirebaseAuth.getInstance();
        MyRecyclerView();
        //MyRecyclerView2();
        notifikasi=findViewById(R.id.notifikasi);
        notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData();
                startActivity(new Intent(List_Toko.this, List_Notif.class));
            }
        });
        btn_sign_out = (Button)findViewById(R.id.btn_sign_out);



        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logout
                AuthUI.getInstance()
                        .signOut(List_Toko.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btn_sign_out.setEnabled(false);
                                showSignInOption();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(List_Toko.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        editText = findViewById(R.id.search_field);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){


                    GetData();
                }else{

                    Thread.interrupted();
                    GetData(s.toString());
                    GetData2(s.toString());

                }
            }
        });

        // init provider
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), // Email Builder
                new AuthUI.IdpConfig.GoogleBuilder().build() // Google Builder
        );

        showSignInOption();
        GetData();

    }
    private void getData(){
        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Pedagang");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataMenu=new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    data_menu2 mahasiswa = snapshot.getValue(data_menu2.class);

                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    mahasiswa.setKey(snapshot.getKey());
                    dataMenu.add(mahasiswa);
                }


                //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                adapter = new RecyclerViewAdapter3(dataMenu, List_Toko.this);

                //Memasang Adapter pada RecyclerView
                recyclerView.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

    }

    //Berisi baris kode untuk mengambil data dari Database dan menampilkannya kedalam Adapter
    private void GetData(String string){

        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Toko");

        Query query = reference.orderByChild("toko").startAt(string.trim()).endAt(string.trim()+"\uf8ff");

                query.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            //Inisialisasi ArrayList
                            dataMahasiswa = new ArrayList<>();
                            dataMahasiswa.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                                data_menu2 mahasiswa = snapshot.getValue(data_menu2.class);

                                //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                                mahasiswa.setKey(snapshot.getKey());
                                dataMahasiswa.add(mahasiswa);
                            }
                        }
                        adapter = new RecyclerViewAdapter3(dataMahasiswa, List_Toko.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);



                        Toast.makeText(getApplicationContext(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();

                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });


    }
    private void GetData2(String string){

        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        //final String getToko = getIntent().getExtras().getString("dataNIM");
        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Pedagang").child("Data");
        Query query = reference.orderByChild("nama").startAt(string).endAt(string+"\uf8ff");

        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    //Inisialisasi ArrayList
                    dataMenu2 = new ArrayList<>();
                    dataMenu2.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                        data_menu mahasiswa = snapshot.getValue(data_menu.class);

                        //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                        mahasiswa.setKey(snapshot.getKey());
                        dataMenu2.add(mahasiswa);
                    }
                }
                adapter = new RecyclerViewAdapter4(dataMenu2, List_Toko.this);

                //Memasang Adapter pada RecyclerView
                recyclerView.setAdapter(adapter);



                Toast.makeText(getApplicationContext(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();

                //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });


    }
    private void GetData(){
        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Admin").child("Toko").orderByChild("toko");
        query
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            //Inisialisasi ArrayList
                            dataMahasiswa = new ArrayList<>();


                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                                data_menu2 mahasiswa = snapshot.getValue(data_menu2.class);

                                //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                                mahasiswa.setKey(snapshot.getKey());
                                dataMahasiswa.add(mahasiswa);
                            }
                        }
                        adapter = new RecyclerViewAdapter3(dataMahasiswa, List_Toko.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);



                        Toast.makeText(getApplicationContext(), "Data Berhasil Dimuat", Toast.LENGTH_LONG).show();

                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });


    }

    //Methode yang berisi kumpulan baris kode untuk mengatur RecyclerView
    private void MyRecyclerView(){
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

    }
    private void MyRecyclerView2(){
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setHasFixedSize(true);


        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView2.addItemDecoration(itemDecoration);

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
        if (requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                // Get User
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, ""+user.getEmail() , Toast.LENGTH_SHORT).show();
                // set button sign out
                btn_sign_out.setEnabled(true);

                cekData2();
                cekData();

            }
            else {
                Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void cekData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String getUserName = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Status1").child(getUserName)
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


            startActivity(new Intent(List_Toko.this, Main2Activity.class));

            Toast.makeText(List_Toko.this, "Akun telah dinonaktifkan", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(List_Toko.this, "Data Loaded", Toast.LENGTH_SHORT).show();

        }








    }
    private void getAuth(){


    }
    public void cekData2() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String getUserName = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Status1").child(getUserName)
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
            String status1 = "Dibanned";

            getReference.child("Admin").child("CekStatus1").child(getUserName)
                    .setValue(new data_acc3(getUserName2,status1))
                    .addOnSuccessListener(this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                            Toast.makeText(List_Toko.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference getReference;
            getReference = database.getReference(); // Mendapatkan Referensi dari Database
            DatabaseReference push1 = getReference.push();

            String key3 = push1.getKey();
            String status1 = "Aktif";

            getReference.child("Admin").child("CekStatus1").child(getUserName)
                    .setValue(new data_acc3(getUserName2,status1))
                    .addOnSuccessListener(this, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database

                            Toast.makeText(List_Toko.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });

        }









    }


    @Override
    public void onDeleteData(data_menu2 data, int position) {

    }


    @Override
    public void onDeleteData(data_menu data, int position) {

    }
}