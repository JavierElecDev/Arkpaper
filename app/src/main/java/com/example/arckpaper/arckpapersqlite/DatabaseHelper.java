package com.example.arckpaper.arckpapersqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ArckProductos"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 1; // Versión de la base de datos
    // Sentencia para crear la tabla
    private static final String crearTablaProductos = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre_producto TEXT UNIQUE NOT NULL, " +
            "descripcion_producto TEXT, cantidad_producto INTEGER NOT NULL, precio_producto REAL NOT NULL)";
    private static final String nomTablaProductos = "productos"; // Nombre de la tabla que contendrá los productos

    // Constructor que sirve para llamar a la instancia de DatabaseHelper y empezar con el manejo de la base de datos
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(crearTablaProductos);
            Log.d("DatabaseHelper", "Tabla productos creada exitosamente.");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al crear la tabla productos.", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + nomTablaProductos);
            onCreate(db);
            Log.d("DatabaseHelper", "Tabla productos actualizada exitosamente.");
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al actualizar la tabla productos.", e);
        }
    }
}
