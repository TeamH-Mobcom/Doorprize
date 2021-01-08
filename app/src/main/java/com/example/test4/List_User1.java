package com.example.test4;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.test4.adapter.RecyclerViewAdapter2;

import com.example.test4.adapter.RecyclerViewAdapter3;
import com.example.test4.model.data_user;
import com.example.test4.model.data_user1;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_User1 extends AppCompatActivity implements RecyclerViewAdapter3.dataListener {

    //Deklarasi Variable untuk RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //Deklarasi Variable Database Reference dan ArrayList dengan Parameter Class Model kita.
    private DatabaseReference reference;
    private ArrayList<data_user1> dataMahasiswa;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_list__user);
        recyclerView = findViewById(R.id.datalist);
        getSupportActionBar().setTitle("Data Menu Pedagang");
        auth = FirebaseAuth.getInstance();
        MyRecyclerView();
        GetData();
    }

    //Berisi baris kode untuk mengambil data dari Database dan menampilkannya kedalam Adapter
    private void GetData(){
        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("CekStatus1")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataMahasiswa = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            data_user1 penerimaan = snapshot.getValue(data_user1.class);



                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            penerimaan.setKey(snapshot.getKey());




                            dataMahasiswa.add(penerimaan);

                        }

                        //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                        adapter = new RecyclerViewAdapter3(dataMahasiswa, List_User1.this);

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
    public void onDeleteData(data_user1 data, int position) {
        /*
         * Kode ini akan dipanggil ketika method onDeleteData
         * dipanggil dari adapter pada RecyclerView melalui interface.
         * kemudian akan menghapus data berdasarkan primary key dari data tersebut
         * Jika berhasil, maka akan memunculkan Toast
         */
        String userID = auth.getCurrentUser().getUid();;
        if(reference != null){
            reference.child("Admin")

                    .child("Pembeli")
                    .child(userID)
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(List_User1.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });


        }

    }


}

