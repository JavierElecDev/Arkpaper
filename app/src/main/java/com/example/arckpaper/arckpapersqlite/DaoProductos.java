package com.example.arckpaper.arckpapersqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoProductos {

    private SQLiteDatabase BD; // para interacción con la base de datos
    private DatabaseHelper AsistenteCrud; // Objeto de la clase DatabaseHelper que puede llamar los métodos necesarios para el CRUD

    // constructor para enlazar con los contextos y crear instancias de la clase helper
    public DaoProductos(Context context) {
        AsistenteCrud = new DatabaseHelper(context);
    }

    // metodos de apertura y cierre de conexion con la base de datos
    public void establecerConexion() {
        try {
            BD = AsistenteCrud.getWritableDatabase();
            Log.d("DaoProductos", "Conexión con la base de datos establecida.");
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al establecer conexión con la base de datos!!", e);
        }
    }

    public void cerrarConexion() {
        AsistenteCrud.close();
        Log.d("DaoProductos", "Conexión con la base de datos cerrada.");
    }

    // metodos para realizar el CRUD
    // Crear un producto
    public long crearProducto(String nombre, String descripcion, int cantidad, float precio) {
        //insgtnaciomos objeto de clase que puede almacenar claves con valor
        ContentValues productoNuevo = new ContentValues();
        //las claves enviaran los valores a los campos correcto de la base de datos
        productoNuevo.put("nombre_producto", nombre);
        productoNuevo.put("descripcion_producto", descripcion);
        productoNuevo.put("cantidad_producto", cantidad);
        productoNuevo.put("precio_producto", precio);

        //comporbamos si hay un fallo solo por consola
        try {
            long id = BD.insert("productos", null, productoNuevo);
            Log.d("DaoProductos", "Producto insertado con ID: " + id);
            return id;
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al insertar producto.", e);
            return -1;
        }
    }

    // metodo que obtiene la lista de todos los productos igual que el que se uso en la clase pasada pero lo adapte
    public List<ProductosEntidad> obtenerTodoslosProductos() {
        //array List para guardar los campos de los productos que encontremos
        List<ProductosEntidad> productos = new ArrayList<>();
        //Creamo un cursor para movernos por la tabla
        Cursor cursor = null;
        try {
            //sentencia sql de busqueda
            cursor = BD.rawQuery("SELECT * FROM productos", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProductosEntidad productosEncontrados = new ProductosEntidad();
                    productosEncontrados.setId(cursor.getInt(0));
                    productosEncontrados.setNombre(cursor.getString(1));
                    productosEncontrados.setDescripcion(cursor.getString(2));
                    productosEncontrados.setCantidad(cursor.getInt(3));
                    productosEncontrados.setPrecio(cursor.getFloat(4));
                    productos.add(productosEncontrados);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al obtener productos.", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productos;
    }

    // metodo para buscar solo un producto
    public List<ProductosEntidad> buscarUnProducto(String nombre) {
        //similar al anterior pero cambia la sentencia donde especifica una clausaula
        List<ProductosEntidad> productos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = BD.rawQuery(
                    "SELECT id, nombre_producto, descripcion_producto, cantidad_producto, precio_producto FROM productos WHERE nombre_producto = ?",
                    new String[]{nombre}
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProductosEntidad productoEncontrado = new ProductosEntidad();
                    productoEncontrado.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    productoEncontrado.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre_producto")));
                    productoEncontrado.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_producto")));
                    productoEncontrado.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow("cantidad_producto")));
                    productoEncontrado.setPrecio(cursor.getFloat(cursor.getColumnIndexOrThrow("precio_producto")));
                    productos.add(productoEncontrado);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al buscar producto.", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productos;
    }

    //metodo para buscar usando una palabra
    public List<ProductosEntidad> buscarConParteDelNombre(String nombre) {
        List<ProductosEntidad> productos = new ArrayList<>();
        Cursor cursor = null;
        try {
            // se ajusta la consulta SQL para usar LIKE con % en la cláusula WHERE
            String consulta = "SELECT id, nombre_producto, descripcion_producto, cantidad_producto, precio_producto " +
                    "FROM productos " +
                    "WHERE nombre_producto LIKE ?";

            // argumento ajustado para usar en la consulta de busqueda
            String[] argumentos = new String[] { "%" + nombre + "%" };

            // recurrimos al cursor para ejecutar la consulta
            cursor = BD.rawQuery(consulta, argumentos);

            // iteramos con el cursor para la busqueda
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProductosEntidad productoEncontrado = new ProductosEntidad();
                    productoEncontrado.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    productoEncontrado.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre_producto")));
                    productoEncontrado.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion_producto")));
                    productoEncontrado.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow("cantidad_producto")));
                    productoEncontrado.setPrecio(cursor.getFloat(cursor.getColumnIndexOrThrow("precio_producto")));
                    productos.add(productoEncontrado);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al buscar producto.", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productos;
    }

    // metodo para actualizar valores de un producto
    public long actualizarProducto(int id,String nombre, String descripcion, int cantidad, float precio) {
        ContentValues productoNuevo = new ContentValues();
        productoNuevo.put("nombre_producto", nombre);
        productoNuevo.put("descripcion_producto", descripcion);
        productoNuevo.put("cantidad_producto", cantidad);
        productoNuevo.put("precio_producto", precio);
        //se usa para establecer la clausula de argumento codicionado al nombre del producto
        String[] argumento = {String.valueOf(id)};

        try {
            long filasAfectadas = BD.update("productos", productoNuevo, "id = ?", argumento);
            Log.d("DaoProductos", "Producto actualizado, filas afectadas: " + filasAfectadas);
            return filasAfectadas;
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al actualizar producto.", e);
            return -1;
        }
    }

    // metodo para borrar un producto
    public void eliminarProducto(String nombre) {
        String[] argumento = {nombre};
        //en caso de lagun error como en otras estrucutras que usamos
        try {
            int filasAfectadas = BD.delete("productos", "nombre_producto = ?", argumento);
            Log.d("DaoProductos", "Producto eliminado, filas afectadas: " + filasAfectadas);
        } catch (SQLException e) {
            Log.e("DaoProductos", "Error al eliminar producto.", e);
        }
    }
}
