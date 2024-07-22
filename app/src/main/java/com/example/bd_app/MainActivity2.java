package com.example.bd_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    Button Vender;
    Button consultar;
    Button actualizar;
    Button eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Vender = findViewById(R.id.vender);
        consultar = findViewById(R.id.consultar);
        actualizar = findViewById(R.id.actualizar);
        eliminar = findViewById(R.id.eliminar);

        Vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana = new Intent(MainActivity2.this, vender.class);
                startActivity(ventana);
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana1 = new Intent(MainActivity2.this, com.example.bd_app.consultar.class);
                startActivity(ventana1);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana2 = new Intent(MainActivity2.this, com.example.bd_app.actualizar.class);
                startActivity(ventana2);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventana3 = new Intent(MainActivity2.this, com.example.bd_app.eliminar.class);
                startActivity(ventana3);
            }
        });

    }
}