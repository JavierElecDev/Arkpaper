package com.example.arckpaper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    private ImageView add, edit, delete, search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add = findViewById(R.id.IMGAdd);
        edit = findViewById(R.id.IMGEdit);
        delete = findViewById(R.id.IMGDel);
        search = findViewById(R.id.IMGsearch);

        add.setOnClickListener(v -> {
            Intent Addproduct = new Intent(Home.this, AgregarProducto.class);
            startActivity(Addproduct);
        });

        add.setOnClickListener(v -> {
            Intent Addproduct = new Intent(Home.this, AgregarProducto.class);
            startActivity(Addproduct);
        });

        edit.setOnClickListener(v -> {
            Intent Editproduct = new Intent(Home.this, ActualizarProducto.class);
            startActivity(Editproduct);
        });

        delete.setOnClickListener(v -> {
            Intent Deleteproduct = new Intent(Home.this, EliminarProducto.class);
            startActivity(Deleteproduct);
        });

        search.setOnClickListener(v -> {
            Intent Searchroduct = new Intent(Home.this, BuscarProducto.class);
            startActivity(Searchroduct);
        });
    }
}