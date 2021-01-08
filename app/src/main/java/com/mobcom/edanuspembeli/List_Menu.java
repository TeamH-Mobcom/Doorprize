package com.mobcom.edanuspembeli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobcom.edanuspembeli.adapter.RecyclerViewAdapter;
import com.mobcom.edanuspembeli.model.data_acc2;
import com.mobcom.edanuspembeli.model.data_acc3;
import com.mobcom.edanuspembeli.model.data_menu;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_Menu extends AppCompatActivity implements RecyclerViewAdapter.dataListener {

    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference reference;
    private ArrayList<data_menu> dataMahasiswa;
    private ArrayList<data_menu> dataMenu;
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
        setContentView(R .layout.activity_list__menu);


        recyclerView = findViewById(R.id.datalist);
        getSupportActionBar().setTitle("Daftar Menu dan Toko");
        auth = FirebaseAuth.getInstance();
        MyRecyclerView();


        // init provider
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
                    GetData("");
                }else{

                    GetData(s.toString());
                }
            }
        });



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
                    data_menu mahasiswa = snapshot.getValue(data_menu.class);

                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    mahasiswa.setKey(snapshot.getKey());
                    dataMenu.add(mahasiswa);
                }


                //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                adapter = new RecyclerViewAdapter(dataMenu, List_Menu.this);

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
    private void GetData(){
        final String getToko = getIntent().getExtras().getString("dataNIM");
        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Pedagang").child(getToko)
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataMahasiswa = new ArrayList<>();

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            data_menu mahasiswa = snapshot.getValue(data_menu.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            mahasiswa.setKey(snapshot.getKey());
                            dataMahasiswa.add(mahasiswa);
                        }


                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        adapter = new RecyclerViewAdapter(dataMahasiswa, List_Menu.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                        Toast.makeText(getApplicationContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
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
    private void GetData(String string){
        final String getToko = getIntent().getExtras().getString("dataNIM");
        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Pedagang").child(getToko);
        Query query = reference.orderByChild("nama").startAt(string).endAt(string+"\uf8ff");
                query.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataMahasiswa = new ArrayList<>();

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            data_menu mahasiswa = snapshot.getValue(data_menu.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            mahasiswa.setKey(snapshot.getKey());
                            dataMahasiswa.add(mahasiswa);
                        }


                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        adapter = new RecyclerViewAdapter(dataMahasiswa, List_Menu.this);

                        //Memasang Adapter pada RecyclerView
                        recyclerView.setAdapter(adapter);

                        Toast.makeText(getApplicationContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
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

    @Override
    public void onDeleteData(data_menu data, int position) {
        /*
         * Kode ini akan dipanggil ketika method onDeleteData
         * dipanggil dari adapter pada RecyclerView melalui interface.
         * kemudian akan menghapus data berdasarkan primary key dari data tersebut
         * Jika berhasil, maka akan memunculkan Toast
         */


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


            startActivity(new Intent(List_Menu.this, Main2Activity.class));

            Toast.makeText(List_Menu.this, "Akun telah dinonaktifkan", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(List_Menu.this, "Data Loaded", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(List_Menu.this, "Data Loaded", Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(List_Menu.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });

        }









    }


}