package com.example.arckpaper.CompRegLog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Comprobaciones {
    private String Correo, Contrasena;
    private Context Activity;
    private boolean compruebaCorreo, compruebaPassword; //se usan para comporbar correo y contraseña


    //Declaramos una expersion regular
    private static final Pattern reg_expresion = Pattern.compile(
            "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
    );

    //Construtor que pide el activity para invocar alert dialog
    public Comprobaciones(Context ventana) {
        this.Activity = ventana;
    }

    //Metodo que comprueba que los campos del login no esten vacios
    public boolean ComprobarEntradas(String CorreoE, String ContrasenaU) {

        this.Correo = CorreoE;
        this.Contrasena = ContrasenaU;

        //Alert dialog en caso de que no se llenen los campos
        AlertDialog.Builder alertaCamposVacios = new AlertDialog.Builder(Activity);
        alertaCamposVacios.setTitle("Atencion Campos vacios");
        alertaCamposVacios.setMessage("Debes ingresar los datos en los campos!!!");
        alertaCamposVacios.setCancelable(false);
        alertaCamposVacios.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        if (Correo.isEmpty() || Contrasena.isEmpty()) {
            AlertDialog alertDialog = alertaCamposVacios.create();
            alertDialog.show();
            return false;
        } else if (Correo.isEmpty()) {
            AlertDialog alertDialog = alertaCamposVacios.create();
            alertDialog.show();
            return false;
        } else if (Contrasena.isEmpty()) {
            AlertDialog alertDialog = alertaCamposVacios.create();
            alertDialog.show();
            return false;
        }else {
            return true;
        }
    }

    //metodo que verifica un correo valido para el registro
    public boolean VerficaCorreo(String CorreoE, String validaCorreo) {
        this.Correo = CorreoE;
        /*reg_expresion.matcher(Correo).matches()
         *comprueba que el correo tiene caracteristicas de la expresion regular
         */
        if (!reg_expresion.matcher(Correo).matches()) {
            AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(Activity);
            alertaCorreo.setTitle("Atencion Correo NO Valido");
            alertaCorreo.setMessage("Verifica el correo electronico!!!");
            alertaCorreo.setCancelable(false);
            alertaCorreo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertaCorreo.create();
            alertDialog.show();
            return false;
        }  else if(!Correo.equals(validaCorreo)){
            AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(Activity);
            alertaCorreo.setTitle("Correos no Coinciden");
            alertaCorreo.setMessage("Los correos ingresados no coinciden!!!!");
            alertaCorreo.setCancelable(false);
            alertaCorreo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertaCorreo.create();
            alertDialog.show();
            return false;
        }
        else {
            return true;
        }
    }


    //Metodo para comprobar que los campos del registro no esten vacios
    public boolean comprobarCamposRegistro(String nombre, String correo, String confCorreo,
                                           String password, String confPassword, String telefono) {

        AlertDialog.Builder alertaCamposVacios = new AlertDialog.Builder(Activity);
        alertaCamposVacios.setTitle("Atencion Campos Vacios");
        alertaCamposVacios.setMessage("Debes ingresar los datos en los todos campos!!!");
        alertaCamposVacios.setCancelable(false);
        alertaCamposVacios.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });

        if (nombre.isEmpty() || correo.isEmpty() || confCorreo.isEmpty() || password.isEmpty()
                || confPassword.isEmpty() || telefono.isEmpty()) {
            AlertDialog alertDialog = alertaCamposVacios.create();
            alertDialog.show();
            return false;
        } else {
            return true;
        }
    }

    //metodo que comprueba que el correo y la contraseña coincidan
    public boolean comprobarDatosDeLogueo(String correoElectronico, String validarCorreoE,
                                          String password, String validarPassword) {
        //validamos que el correo tengo un formato valido
        if (!reg_expresion.matcher(correoElectronico).matches()) {
            AlertDialog.Builder alertaCorreoReg = new AlertDialog.Builder(Activity);
            alertaCorreoReg.setTitle("Atencion Correo NO Valido");
            alertaCorreoReg.setMessage("Verifica el correo electronico!!!");
            alertaCorreoReg.setCancelable(false);
            alertaCorreoReg.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertaCorreoReg.create();
            alertDialog.show();
        }
        //Verficamos que los correos coincidan
        else if (correoElectronico.equals(validarCorreoE)) {
            compruebaCorreo = true;
        } else if (!correoElectronico.equals(validarCorreoE)) {
            AlertDialog.Builder alertaCorreo = new AlertDialog.Builder(Activity);
            alertaCorreo.setTitle("Correo No coincide.");
            alertaCorreo.setMessage("El correo no coincide, favor verifica los campos de correo," +
                    " y confirmacion de coreeo!");
            alertaCorreo.setCancelable(false);
            alertaCorreo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertaCorreo.create();
            alertDialog.show();
            compruebaCorreo = false;
        }


        if (password.equals(validarPassword)) {
            compruebaPassword = true;
        } else {
            AlertDialog.Builder alertaPassword = new AlertDialog.Builder(Activity);
            alertaPassword.setTitle("Contraseña No coincide.");
            alertaPassword.setMessage("La contraseña ingresada no conicide, favor verifica los campos de Contraseña," +
                    " y confirmacion de Contraseña!");
            alertaPassword.setCancelable(false);
            alertaPassword.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = alertaPassword.create();
            alertDialog.show();
            compruebaPassword = false;
        }

        if (compruebaCorreo == true && compruebaPassword == true) {
            return true;
        } else {
            return false;
        }
    }
}
