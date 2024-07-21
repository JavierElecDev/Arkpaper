package com.example.arckpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.arckpaper.CompRegLog.Comprobaciones;
import com.example.arckpaper.viewmodel.RegistroUsuariosViewModel;

public class Login extends AppCompatActivity {

    private Button Registrarse, iniciar;
    private EditText correoElectronico, password;
    private Comprobaciones comprobar;
    private RegistroUsuariosViewModel login; // usamos el viewmodel que contine la logica para el login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Registrarse = findViewById(R.id.logBtnRegister);
        iniciar = findViewById(R.id.logBtnLogin);
        correoElectronico = findViewById(R.id.logetEmail);
        password = findViewById(R.id.logetPassword);
        comprobar = new Comprobaciones(Login.this); //objeto de la clase comporbar para poder comprobar campos
        login = new ViewModelProvider(this).get(RegistroUsuariosViewModel.class); //provider del viewmodel

        //eventos focus para no perder el hint de contraseña y usuario
        correoElectronico.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    correoElectronico.setHint(null);
                } else if (!b && correoElectronico.getText().toString().isEmpty()) {
                    correoElectronico.setHint("Ingresa el Correo Electronico.");
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    password.setHint(null);
                } else if (!b && password.getText().toString().isEmpty()) {
                    password.setHint("Ingresa la contraseña");
                }
            }
        });

        iniciar.setOnClickListener(v -> {
            String email = correoElectronico.getText().toString().trim();
            String passW = password.getText().toString().trim();

            boolean verifica1 = comprobar.ComprobarEntradas(email,passW);
            if(verifica1){
                login.iniciarSesion(email,passW, Login.this);
            }


        });

        Registrarse.setOnClickListener(v -> {
            Intent registrarse = new Intent(Login.this, Register.class);
            startActivity(registrarse);
        });
    }
}