package com.example.arckpaper;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AgregarProducto extends AppCompatActivity {

    private Button agregar, volver, irbucar;
    private EditText nompreproducto, descripcionProducto,cantidadProducto,precioProducto;
    private DaoProductos ejecucionCRUD;
    private ListView productosListView;
    private ArrayAdapter<String> adapter;
    private List<String> productosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nompreproducto = findViewById(R.id.etProductNameCreate);
        descripcionProducto = findViewById(R.id.etDescriptionCreate);
        cantidadProducto = findViewById(R.id.etCantCreate);
        precioProducto = findViewById(R.id.etPriceCreate);

        // inico conexion con ayuda del Dao
        ejecucionCRUD = new DaoProductos(this);
        ejecucionCRUD.establecerConexion();

        // array adapter para mostrar el producto
        productosList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
        productosListView.setAdapter(adapter);

        agregar.setOnClickListener(v -> {
            insertarProducto();
        });

    }

    private void insertarProducto() {
        String nombre = nompreproducto.getText().toString().trim();
        String descripcion = descripcionProducto.getText().toString().trim();
        String cantidadStr = cantidadProducto.getText().toString().trim();
        String precioStr = precioProducto.getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad;
        float precio;

        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad o precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // insertamos el numeo producto tipo long por que el metodo es tipo long
        long id = ejecucionCRUD.crearProducto(nombre, descripcion, cantidad, precio);

        if (id != -1) {
            Toast.makeText(this, "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
            // limpiar campos
            nompreproducto.setText("");
            descripcionProducto.setText("");
            cantidadProducto.setText("");
            precioProducto.setText("");

            // Mostrar el producto en la lista
            actualizarListaDeProductos(nombre);
        } else {
            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarListaDeProductos(String nombre) {
        List<ProductosEntidad> productos = ejecucionCRUD.buscarUnProducto(nombre);
        productosList.clear();
        for (ProductosEntidad producto : productos) {
            productosList.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        adapter.notifyDataSetChanged();
    }

    //Cerrar la conexsion cuando se destrulleo se salga de la actividad
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ejecucionCRUD != null) {
            ejecucionCRUD.cerrarConexion();
        }
    }
}