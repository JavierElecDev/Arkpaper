package com.example.arckpaper.arckpapersqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ArckProductos"; //nombre de la base de datos
    private static final int DATABASE_VERSION = 1;//version de la base de datos
    //SENTENCIA PARA CREAR LA TABLA
    private static final String crearTablaProductos = "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre_producto TEXT UNIQUE NOT NULL, " +
            "descripcion_producto TEXT, cantidad_producto INTEGER NOT NULL, precio_producto REAL NOT NULL)";
    private static final String nomTablaProductos = "productos";// nombre de la tabla que contendra los productos


    //construtor que sirve para llamar a la instancia de lDatabasehelper y empezar con el manejo de la base de datos
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //ejecutamos la sentencia de creacion de la tabla "productos'
        db.execSQL(crearTablaProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + nomTablaProductos);
        onCreate(db);
    }
}
