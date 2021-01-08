package com.example.test4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launcher extends AppCompatActivity {
Button Pedagang, Pembeli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Pedagang =findViewById(R.id.btn_list_pedagang);
        Pembeli=findViewById(R.id.btn_list_pembeli);
        Pedagang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Launcher.this, List_User.class));
            }
        });
        Pembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Launcher.this, List_User1.class));
            }
        });
    }
}
