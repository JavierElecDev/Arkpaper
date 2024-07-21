package com.example.arckpaper.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.arckpaper.Home;
import com.example.arckpaper.Login;
import com.example.arckpaper.Register2;
import com.example.arckpaper.modelo_registro_usuarios.RegistroUsuario;
import com.google.firebase.auth.FirebaseAuth; //importamos dependencia para el uso de autenticacion de firebase
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference; //Importamos dependencia para la referencia a base NOSQL Firebase
import com.google.firebase.database.FirebaseDatabase; //importanmos dependencia(clase) para el unso de Firebase

import java.util.List;

public class RegistroUsuariosViewModel extends ViewModel {
    private MutableLiveData<List<RegistroUsuario>> usuarios;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    //Constructor de inicializacion donde se realiazara el registro
    public RegistroUsuariosViewModel() {
        //Obtencion de la instancia de autenticacion
        auth = FirebaseAuth.getInstance();
        //obtencion de la instancia y referencia la basse NOSQL de usuarios
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void registroDeUsuario(String nombre, String email, String password, String telefono) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            RegistroUsuario usuario = new RegistroUsuario(nombre, email, password, telefono);
                            databaseReference.child(userId).setValue(usuario);
                        } else {
                            try {
                                Toast.makeText(Register2.class.newInstance(), "Error de conexion o registro", Toast.LENGTH_SHORT).show();
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
    }

    //metodo para inciar sesion en firebase
    public void iniciarSesion(String email, String password, Context context) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            // Navegar a UserListActivity después del inicio de sesión
                            Intent intent = new Intent(context, Home.class);
                            context.startActivity(intent);
                            if (context instanceof Login) {
                                ((Login) context).finish();
                            }
                        }
                    } else {
                        // Manejar errores
                        Toast.makeText(context, "Fallo en la conexion: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
