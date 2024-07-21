package com.example.arckpaper.arckpapersqlite;

//Representa la entidad de la base de datos
public class ProductosEntidad {
    //Campos que se relacionan con la tabla de SQLITE
    String nombre, descripcion;
    int cantidad, id;
    float precio;

    public  ProductosEntidad (){
        //Constructor vacio requerido
    }

    public  ProductosEntidad (int id, String nombre, String descripcion, int cantidad, float precio){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    //Metodos getter y setter para actua con la entidad en la tabla

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
