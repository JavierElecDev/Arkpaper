package com.example.arckpaper;

import android.annotation.SuppressLint;
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

public class EliminarProducto extends AppCompatActivity {

    private ImageView BRimgbuscar, BRirHome;
    private EditText BRetBuscar, etnombre, etID;
    private Button Eliminar;
    private ListView BRlistaSimple;
    private ArrayAdapter<String> BRadapterSimple;//adaptador para mostrar producto simple
    private List<String> BRproductosListSimple; // lista que guarda los campos del registro de productos
    private DaoProductos ejecucionCRUD;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BRimgbuscar =findViewById(R.id.BorrarImgbuscar);
        BRirHome = findViewById(R.id.BorrarirHome);
        BRetBuscar = findViewById(R.id.BorrarBuscar);
        BRlistaSimple = findViewById(R.id.BorrarproductoListView);
        BRimgbuscar = findViewById(R.id.BorrarImgbuscar);
        Eliminar = findViewById(R.id.Borrarbutton);
        etnombre = findViewById(R.id.BorrarNmbre);
        etID = findViewById(R.id.BorrarporID);

        // Instancia del DAO para iniciar la conexion con la base de datos
        ejecucionCRUD = new DaoProductos(this);
        ejecucionCRUD.establecerConexion();

        //Configuracion de las listas
        BRproductosListSimple = new ArrayList<>();
        //configuracion de adaptadores
        BRadapterSimple = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BRproductosListSimple);
        //seteamos los listview
        BRlistaSimple.setAdapter(BRadapterSimple);

    }

    //metodo para borrar por el nombre
    public void borrarPorNombre(){
        
    }

    //metodo para la busqueda de porductos por palabra
    private void busquedaSimple(){
        String palabra = BRetBuscar.getText().toString().trim();
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
        BRproductosListSimple.clear();
        for (ProductosEntidad producto : productos) {
            BRproductosListSimple.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        BRadapterSimple.notifyDataSetChanged();
        Log.d("BuscarProducto", "Lista de productos actualizada.");
    }
}