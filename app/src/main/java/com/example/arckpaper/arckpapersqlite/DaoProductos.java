package com.example.arckpaper.arckpapersqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DaoProductos {

    private SQLiteDatabase BD; //para interaccion con la bsse de datos
    private DatabaseHelper AsistenteCrud; // objeto de la clase DatabaseHelper que puede llamar los metoso nedesarfios para el CRUD


    //constructor para enlazar con los contextos y crear instnacias del helper
    public DaoProductos(Context context) {
        AsistenteCrud = new DatabaseHelper(context);
    }

    //metodos de apertura y cierre de conexion con la base de datos
    public void establecerConexion() {
        AsistenteCrud.getWritableDatabase();
    }

    public void cerrarConexion() {
        AsistenteCrud.close();
    }

    //metodos para realizar el CRUD
    //crear un producto
    public long crearProducto(String nombre, String descripcion, int cantidad, float precio) {
        //almacenamos los valores
        ContentValues productoNuevo = new ContentValues();
        productoNuevo.put("nombre_produto", nombre);
        productoNuevo.put("descripcion_producto", descripcion);
        productoNuevo.put("cantidad_producto", cantidad);
        productoNuevo.put("precio_producto", precio);
        return BD.insert("productos", null, productoNuevo);
    }

    //obtener lista de todos los productos
    public List<ProductosEntidad> obtenerTodoslosProductos() {
        List<ProductosEntidad> productos = new ArrayList<>(); // guardaremso todos los productos en el list
        Cursor cursor = BD.rawQuery("SELECT * FROM productos", null); // consutal sql

        // Iteración sobre los resultados del cursor para obtener los datos de cada usuario
        if (cursor.moveToFirst()) {
            do {
                ProductosEntidad productosEncontrados = new ProductosEntidad(); // instancia al la entidad
                productosEncontrados.setId(cursor.getInt(0)); // id de lproducto
                productosEncontrados.setNombre(cursor.getString(1)); // nombre del producto
                productosEncontrados.setDescripcion(cursor.getString(2)); // descripcion del producto
                productosEncontrados.setCantidad(cursor.getInt(3));// cantidad de producto
                productosEncontrados.setPrecio(cursor.getFloat(4)); //precio del producto
                productos.add(productosEncontrados); //agregamos el producto al list
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierre del cursor
        return productos; // Retorno los productos
    }

    //Buscar un producto
    public List<ProductosEntidad> buscarUnProducto(String nombre) {
        List<ProductosEntidad> productos = new ArrayList<>(); // Lista para almacenar el producto encontrado
        Cursor cursor = BD.rawQuery(
                "SELECT id, nombre_producto, descripcion_producto, cantidad_producto, precio_producto FROM productos WHERE nombre_producto = ?"
                , new String[]{nombre});

        // Iteración sobre los resultados del cursor para obtener los datos del producto
        if (cursor != null && cursor.moveToFirst()) {
            ProductosEntidad productoEncontrado = new ProductosEntidad(); // instnacia a la entidad
            productoEncontrado.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            productoEncontrado.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre_producto")));
            productoEncontrado.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_producto")));
            productoEncontrado.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow("cantidad_producto")));
            productoEncontrado.setPrecio(cursor.getFloat(cursor.getColumnIndexOrThrow("precio_producto")));

            productos.add(productoEncontrado); // Añadir el producto a la lista
        }
        if (cursor != null) {
            cursor.close(); // Cierre del cursor
        }
        return productos; // Retorno de la lista con el producto encontrado o vacía si no se encontró
    }

    //actualizar producto
    public long actualizarProducto(String nombre, String descripcion, int cantidad, float precio) {
        ContentValues productoNuevo = new ContentValues();
        productoNuevo.put("nombre_producto", nombre);
        productoNuevo.put("descripcion_producto", descripcion);
        productoNuevo.put("cantidad_producto", cantidad);
        productoNuevo.put("precio_producto", precio);

        //Argumento que se enviara a la sentencia
        String[] argumento = {nombre};

        // Actualizamos la base de datos y devolvemos el número de filas afectadas
        return BD.update("productos", productoNuevo, "nombre_producto = ?", argumento);
    }

    //metodo para eliminar un producto
    public void eliminarProducto(String nombre) {
        String[] argumento = {nombre};
        BD.delete("usuarios", "id = ?", argumento);
    }
}
