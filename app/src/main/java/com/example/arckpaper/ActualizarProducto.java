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

public class ActualizarProducto extends AppCompatActivity {
    //creamos los elemtnso son los mismo de la vista agregar
    private Button actualizar;
    private ImageView ACTvolver, ACTirbuscar;
    private EditText ACTnompreproducto, ACTdescripcionProducto, ACTcantidadProducto, ACTprecioProducto, ACTIDProd;
    private DaoProductos ejecucionCRUD;
    private ListView ACTproductoListView;
    private ArrayAdapter<String> ACTadapter;//adaptador para mostrar lo datos en pantallas de la activity
    private List<String> ACTproductosList;
    private ArrayAdapter<String> adapterSimple;//adaptador para mostrar producto simple
    private List<String> productosListSimple; // lista que guarda los campos del registro de productos
    private ListView listaSimple;
    private EditText ActualizaretBuscar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ACTnompreproducto = findViewById(R.id.ActualizaNmbre);
        ACTdescripcionProducto = findViewById(R.id.ActualizaDescrip);
        ACTcantidadProducto = findViewById(R.id.Actualizacantidad);
        ACTprecioProducto = findViewById(R.id.ActualizaPecios);
        ACTproductoListView = findViewById(R.id.ActualizaproductoListView);
        actualizar = findViewById(R.id.ActualizaBtnActualiza);
        ACTvolver = findViewById(R.id.ActualizarirHome);
        ACTirbuscar = findViewById(R.id.Actualizarbuscar);
        listaSimple = findViewById(R.id.ActualizarBuscarproductoListView);
        ActualizaretBuscar = findViewById(R.id.ActualizaBuscar);
        ACTIDProd = findViewById(R.id.idproducto);

        // Instancia del DAO para iniciar la conexion con la base de datos
        ejecucionCRUD = new DaoProductos(this);
        ejecucionCRUD.establecerConexion();

        // Configuracion del adaptador para la lista
        ACTproductosList = new ArrayList<>();
        //se configura el adaptador para recibir la lista de productos con el diseño mas simple
        ACTadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ACTproductosList);
        ACTproductoListView.setAdapter(ACTadapter);

        //Configuracion de la lista
        productosListSimple = new ArrayList<>();
        //configuracion de adaptador
        adapterSimple = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosListSimple);
        //seteamos los listview
        listaSimple.setAdapter(adapterSimple);


        actualizar.setOnClickListener(v -> {
            actualizarProducto();
        });

        ACTvolver.setOnClickListener(v -> {
            Intent irHome = new Intent(ActualizarProducto.this, Home.class);
            startActivity(irHome);
        });

        ACTirbuscar.setOnClickListener(v -> {
            busquedaSimple();
        });
    }

    //metodo para preparar la insercion del producto
    private void actualizarProducto() {
        //variables intermedias
        String nomProd = ACTnompreproducto.getText().toString().trim();
        String descProd = ACTdescripcionProducto.getText().toString().trim();
        String cantidadProdst = ACTcantidadProducto.getText().toString().trim();
        String precioProdst = ACTprecioProducto.getText().toString().trim();
        int idProd = Integer.parseInt(ACTIDProd.getText().toString().trim());

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
        long actualizarPproducto = ejecucionCRUD.actualizarProducto(idProd,nomProd, descProd, cantidadProd, precioProd);
        //si no se genera un id valido no se actualiza el producto
        if (actualizarPproducto != -1) {
            Toast.makeText(this, "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
            Log.d("AgregarProducto", "Producto insertado con ID: " + actualizarPproducto);
            // limpiar campos cuando la creacion es exitosa
            ACTnompreproducto.setText("");
            ACTdescripcionProducto.setText("");
            ACTcantidadProducto.setText("");
            ACTprecioProducto.setText("");

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
        ACTproductosList.clear();
        //realizamos un recorrido por la lista de productos en busca del nombre del producto y traemos sus campos
        for (ProductosEntidad producto : productos) {
            ACTproductosList.add("ID: " + producto.getId() + ", Nombre: " + producto.getNombre()
                    + ", Descripción: " + producto.getDescripcion() + ", Cantidad: " + producto.getCantidad()
                    + ", Precio: " + producto.getPrecio());
        }
        //nofiticamos que los datos cambiaron para mostrar en el adpatador
        ACTadapter.notifyDataSetChanged();
        Log.d("ActualizarProducto", "Lista de productos actualizada.");
    }

    //metodo para la busqueda de porductos por palabra
    private void busquedaSimple(){
        String palabra = ActualizaretBuscar.getText().toString().trim();
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

    //cuando ce cierre el activity cerramos la conexion
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ejecucionCRUD != null) {
            ejecucionCRUD.cerrarConexion();
        }
    }
}