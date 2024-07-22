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
import androidx.appcompat.app.AppCompatActivity;
import com.example.arckpaper.arckpapersqlite.DaoProductos;
import com.example.arckpaper.arckpapersqlite.ProductosEntidad;
import java.util.ArrayList;
import java.util.List;

public class AgregarProducto extends AppCompatActivity {

    //elementos para conexsion con el front
    private Button agregar;
    private ImageView volver, irbucar;
    private EditText nompreproducto, descripcionProducto, cantidadProducto, precioProducto;
    private DaoProductos ejecucionCRUD;
    private ListView productoListView;
    private ArrayAdapter<String> adapter;//adaptador para mostrar lo datos en pantallas de la activity
    private List<String> productosList; // lista que guarda los campos del registro de productos

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        nompreproducto = findViewById(R.id.etProductNameCreate);
        descripcionProducto = findViewById(R.id.etDescriptionCreate);
        cantidadProducto = findViewById(R.id.etCantCreate);
        precioProducto = findViewById(R.id.etPriceCreate);
        productoListView = findViewById(R.id.productoListView);
        agregar = findViewById(R.id.AddbtnAgregar);
        volver = findViewById(R.id.AddimgirHome);
        irbucar = findViewById(R.id.AddimgIrbuscar);

        // Instancia del DAO para iniciar la conexion con la base de datos
        ejecucionCRUD = new DaoProductos(this);
        ejecucionCRUD.establecerConexion();

        // Configuracion del adaptador para la lista
        productosList = new ArrayList<>();
        //se configura el adaptador para recibir la lista de productos con el diseño mas simple
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
        productoListView.setAdapter(adapter);

        agregar.setOnClickListener(v -> {
            insertarProducto();
        });

        volver.setOnClickListener(v -> {
            Intent irHome = new Intent(AgregarProducto.this, Home.class);
            startActivity(irHome);
        });

        irbucar.setOnClickListener(v -> {
            Intent irbuscar = new Intent(AgregarProducto.this, BuscarProducto.class);
            startActivity(irbuscar);
        });
    }

    //metodo para preparar la insercion del producto
    private void insertarProducto() {
        //variables intermedias
        String nomProd = nompreproducto.getText().toString().trim();
        String descProd = descripcionProducto.getText().toString().trim();
        String cantidadProdst = cantidadProducto.getText().toString().trim();
        String precioProdst = precioProducto.getText().toString().trim();

        //comprobamso que ningun campo este vacios
        if (nomProd.isEmpty() || descProd.isEmpty() || cantidadProdst.isEmpty() || precioProdst.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;//para finalizar la reivison
        }

        int cantidadProd;
        float precioProd;
        //Se realiza validacion, solo es opcional
        try {
            cantidadProd = Integer.parseInt(cantidadProdst);
            precioProd = Float.parseFloat(precioProdst);
            //encaso de lagun fallo por no cumplir con el formato
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad o precio no válidos", Toast.LENGTH_SHORT).show();
            Log.e("AgregarProducto", "Error al convertir cantidad o precio", e);
            return;
        }

        // se crea variable ya que el metodo es de tipo long para ejecutar el metodo de insersion
        long insertaproducto = ejecucionCRUD.crearProducto(nomProd, descProd, cantidadProd, precioProd);
        //si no se genera un id valido no se creara el producto
        if (insertaproducto != -1) {
            Toast.makeText(this, "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
            Log.d("AgregarProducto", "Producto insertado con ID: " + insertaproducto);
            // limpiar campos cuando la creacion es exitosa
            nompreproducto.setText("");
            descripcionProducto.setText("");
            cantidadProducto.setText("");
            precioProducto.setText("");

            // llamamos al metodo para actualizar la lista de prodcutos
            actualizarListaDeProductos(nomProd);
        } else {
            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo de actualizacion de lista de productos se usa junto con el productosList que se envia al adapter
    private void actualizarListaDeProductos(String nombre) {
        //la lista recibe al metodo de busqueda de prodcutos (en este caso estamos buscando el que se acaba de ingresar
        List<ProductosEntidad> productos = ejecucionCRUD.buscarUnProducto(nombre);
        //si la lista tiene elementos los eleiminamos para poder visualizar el nuevo producto ingresado
        productosList.clear();
        //realizamos un recorrido por la lista de productos en busca del nombre del producto y traemos sus campos
        for (ProductosEntidad producto : productos) {
            productosList.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        //nofiticamos que los datos cambiaron para mostrar en el adpatador
        adapter.notifyDataSetChanged();
        Log.d("AgregarProducto", "Lista de productos actualizada.");
    }

    //cuando ce cierre el activity cerramos la conexion
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ejecucionCRUD != null) {
            ejecucionCRUD.cerrarConexion();
        }
    }
}