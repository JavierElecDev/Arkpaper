package com.example.arckpaper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.arckpaper.arckpapersqlite.DaoProductos;
import com.example.arckpaper.arckpapersqlite.ProductosEntidad;

import java.util.ArrayList;
import java.util.List;

public class BuscarProducto extends AppCompatActivity {

    private ImageView imgbuscar, irHome;
    private EditText etBuscar;
    private Button buscarTodo;
    private ListView listaSimple, listaCompleta;
    private ArrayAdapter<String> adapterSimple;//adaptador para mostrar producto simple
    private List<String> productosListSimple; // lista que guarda los campos del registro de productos
    private ArrayAdapter<String> adapterCompleto;//adaptador para mostrar todos los productos
    private List<String> productosListCompleto; //lista que almacena todos los productos
    private DaoProductos ejecucionCRUD;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgbuscar =findViewById(R.id.BSImageBuscar);
        irHome = findViewById(R.id.BSimgirHome);
        etBuscar = findViewById(R.id.BSetBuscar);
        buscarTodo = findViewById(R.id.BSBuscarTodo);
        listaSimple = findViewById(R.id.BSBuscarproductoListView);
        listaCompleta = findViewById(R.id.BSBuscarproductosListView);

        // Instancia del DAO para iniciar la conexion con la base de datos
        ejecucionCRUD = new DaoProductos(this);
        ejecucionCRUD.establecerConexion();

        //Configuracion de las listas
        productosListSimple = new ArrayList<>();
        productosListCompleto = new ArrayList<>();
        //configuracion de adaptadores
        adapterSimple = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosListSimple);
        adapterCompleto = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosListCompleto);

        //seteamos los listview
        listaSimple.setAdapter(adapterSimple);
        listaCompleta.setAdapter(adapterCompleto);

        imgbuscar.setOnClickListener(v -> {
            busquedaSimple();
        });

        buscarTodo.setOnClickListener(v -> {
            busquedaCompleta();
        });

        irHome.setOnClickListener(v -> {
            Intent irHome = new Intent(BuscarProducto.this, Home.class);
            startActivity(irHome);
        });
    }

    //metodo para la busqueda de porductos por palabra
    private void busquedaSimple(){
        String palabra = etBuscar.getText().toString().trim();
        if (palabra.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una palabra", Toast.LENGTH_SHORT).show();
            return; // si la palabra esta vacia salimos
        }

        // usamos el metodo para busqueda con parte del nombre del producto
        List<ProductosEntidad> buscarProducto = ejecucionCRUD.buscarConParteDelNombre(palabra);

        if (!buscarProducto.isEmpty()) {
            Toast.makeText(this, "Producto encontrado con éxito", Toast.LENGTH_SHORT).show();
            Log.d("BuscarProducto", "Productos encontrados: " + buscarProducto);

            // actualizamos la lista con los productos encontrados
            actualizarListasimple(buscarProducto);
        } else {
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo de actualizacion de lista de productos simple
    private void actualizarListasimple(List<ProductosEntidad> productos) {
        productosListSimple.clear();
        for (ProductosEntidad producto : productos) {
            productosListSimple.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        adapterSimple.notifyDataSetChanged();
        Log.d("BuscarProducto", "Lista de productos actualizada.");
    }

    //metodo para la busqueda todos los productos
    private void busquedaCompleta(){

        // usamos el metodo para busqueda de todos los productos
        List<ProductosEntidad> buscarProductos = ejecucionCRUD.obtenerTodoslosProductos();

        if (!buscarProductos.isEmpty()) {
            Toast.makeText(this, "Productos encontrado con éxito", Toast.LENGTH_SHORT).show();
            Log.d("BuscarProducto", "Productos encontrados: " + buscarProductos);

            // actualizamos la lista con los productos encontrados
            actualizarListaCompleta(buscarProductos);
        } else {
            Toast.makeText(this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo de actualizacion de la lista de todos los porductos
    private void actualizarListaCompleta(List<ProductosEntidad> productos) {
        productosListCompleto.clear();
        for (ProductosEntidad producto : productos) {
            productosListCompleto.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        adapterCompleto.notifyDataSetChanged();
        Log.d("BuscarProducto", "Lista de productos actualizada.");
    }



}