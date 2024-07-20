package com.example.arckpaper.modelo_registro_usuarios;

public class RegistroUsuario {
    private String nombre;
    private String correElectronico;
    private String Passeord;
    private String numeroTelefono;

    public RegistroUsuario(){
        //Constructor vacio requerido para usar con firebase
    }

    //Contructor para obtener y almacenar los datos
    public RegistroUsuario(String nombre, String correo, String password, String numeroTelefono){
        this.nombre = nombre;
        this.correElectronico = correo;
        this.Passeord = password;
        this.numeroTelefono = numeroTelefono;
    }

    //Metodos setter y getter para obtenery modificar los datos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreElectronico() {
        return correElectronico;
    }

    public void setCorreElectronico(String correElectronico) {
        this.correElectronico = correElectronico;
    }

    public String getPasseord() {
        return Passeord;
    }

    public void setPasseord(String passeord) {
        Passeord = passeord;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}
