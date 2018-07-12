package com.universedeveloper.eeq.admintroublefix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.universedeveloper.eeq.admintroublefix.Detail.LaptopTerpilih;
import com.universedeveloper.eeq.admintroublefix.Insert.InputLaptop;
import com.universedeveloper.eeq.admintroublefix.Insert.InputTeknopedia;

public class MerkLaptop extends AppCompatActivity {
    RelativeLayout layout0, layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8;
    Button btn_input_laptopbaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merk_laptop);

        layout0 = (RelativeLayout) findViewById(R.id.layout0);
        layout0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Apple");
                startActivity(intent);
            }
        });


        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "ASUS");
                startActivity(intent);
            }
        });

        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "hp");
                startActivity(intent);
            }
        });

        layout3 = (RelativeLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Lenovo");
                startActivity(intent);
            }
        });

        layout4 = (RelativeLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Acer");
                startActivity(intent);
            }
        });

        layout5 = (RelativeLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Samsung");
                startActivity(intent);
            }
        });

        layout6 = (RelativeLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Toshiba");
                startActivity(intent);
            }
        });

        layout7 = (RelativeLayout) findViewById(R.id.layout7);
        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Fujitsu");
                startActivity(intent);
            }
        });

        layout8 = (RelativeLayout) findViewById(R.id.layout8);
        layout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, SeriLaptop.class);
                intent.putExtra("merk_laptop", "Axioo");
                startActivity(intent);
            }
        });

        btn_input_laptopbaru = (Button) findViewById(R.id.btn_input_laptopbaru);
        btn_input_laptopbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MerkLaptop.this, InputLaptop.class );
                startActivity(intent);
            }
        });
    }
}
