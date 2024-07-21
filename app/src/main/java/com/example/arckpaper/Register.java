package com.example.arckpaper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.arckpaper.CompRegLog.Comprobaciones;
import com.example.arckpaper.viewmodel.RegistroUsuariosViewModel;

public class Register extends AppCompatActivity {
    private RegistroUsuariosViewModel registroUsuariosViewModel;
    private EditText etNombre, etCorreo, etConfCorreo, etPassword, etConfPassword, etTelefono;
    private Button volver, continuar;
    private CheckBox aceptarTerminos, aceptarCondicones;
    private boolean comp1, comp2, comp3; // variables para comprobar estado del registro
    Comprobaciones comprobar = new Comprobaciones(Register.this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Enlace de variables con el front
        etNombre = findViewById(R.id.regNombre);
        etCorreo = findViewById(R.id.regetEmail);
        etConfCorreo = findViewById(R.id.regConfetEamil);
        etPassword = findViewById(R.id.regetPass);
        etConfPassword = findViewById(R.id.regConfetPass);
        etTelefono = findViewById(R.id.regeditTextPhone);
        volver = findViewById(R.id.regBtnVolver);
        continuar = findViewById(R.id.regBtnContinuar);
        aceptarCondicones = findViewById(R.id.checkTerminos);
        aceptarTerminos = findViewById(R.id.checkInfo);

        //Instancia del ViewModel
        registroUsuariosViewModel = new ViewModelProvider(this).get(RegistroUsuariosViewModel.class);

        //Objeto de la clase comprobaciones para comprobar campos de registros
        Comprobaciones comprobar = new Comprobaciones(Register.this);

        continuar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String validaCorreo = etConfCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String validaPassword = etConfPassword.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            
            //invocamos los metodos para comprobaciones
            comp1 = comprobar.comprobarCamposRegistro(nombre, correo, validaCorreo, password, validaPassword, telefono);
            comp2 = comprobar.VerficaCorreo(correo, validaCorreo);

            if(aceptarCondicones.isChecked() && aceptarTerminos.isChecked()){
                comp3 = true;
            } else {
                Toast.makeText(Register.this, "Debes aceptar todos los terminos", Toast.LENGTH_SHORT).show();
                comp3 = false;
            }
            // si se verifican los pasos anteriores realiza el registro y limpia los cmapos
            if (comp1 == true && comp2 == true  && comp3 == true) {
                registroUsuariosViewModel.registroDeUsuario(nombre, correo, password, telefono);
                etNombre.setText("");
                etCorreo.setText("");
                etConfCorreo.setText("");
                etPassword.setText("");
                etConfPassword.setText("");
                etTelefono.setText("");
                Toast.makeText(Register.this, "Registro Completo", Toast.LENGTH_SHORT).show();
                //regresamos al login luego del registro
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();
            }else {
                Toast.makeText(Register.this, "No entro", Toast.LENGTH_SHORT).show();
            }
        });

    }
}