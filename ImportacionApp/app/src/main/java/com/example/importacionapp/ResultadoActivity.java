package com.example.importacionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import operaciones.Impuestos;
import operaciones.Moneda;
import operaciones.Pedido;

public class ResultadoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txv_total_clp,txv_valor_cif,
            txv_costo_envio,txv_aduana,txv_iva,
            txv_total_impuesto,txv_total_compra,txv_respuesta;
    Button btn_enviar,btn_volver;
    Intent intento;
    Bundle bundle;
    Impuestos impuestos;
    Pedido pedido;
    Moneda moneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        //Creamos objetos de la clase producto y impuestos
        pedido = new Pedido();
        impuestos = new Impuestos();
        moneda = new Moneda();
        //Vinculamos elementos
        vincularElementos();
        obtenerDatos();
        asignarValores();
        calcularValores();
        mostrarResultado();
        habilitarListener();

    }

    private void habilitarListener() {
        btn_enviar.setOnClickListener(this);
        btn_volver.setOnClickListener(this);
    }

    private void vincularElementos() {
        //Vinculamos con el activity main
        txv_total_clp = (TextView) findViewById(R.id.txv_total_clp);
        txv_valor_cif = (TextView) findViewById(R.id.txv_valor_cif);
        txv_costo_envio = (TextView) findViewById(R.id.txv_costo_envio);
        txv_aduana = (TextView) findViewById(R.id.txv_aduana);
        txv_iva = (TextView) findViewById(R.id.txv_iva);
        txv_total_impuesto = (TextView) findViewById(R.id.txv_total_impuesto);
        txv_total_compra = (TextView) findViewById(R.id.txv_total_compra);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        txv_respuesta = (TextView) findViewById(R.id.txv_respuesta);

    }

    private void obtenerDatos() {
        intento = getIntent();
        bundle = intento.getExtras();
    }

    private void asignarValores() {
        //Datos que vienen desde el MainActivity
        pedido.setCodigo(bundle.getInt("p_codigo"));
        pedido.setNombre(bundle.getString("p_nombre"));
        pedido.setCosto(bundle.getInt("p_costo"));
        pedido.setProveedor(bundle.getString("p_proveedor"));
        pedido.setCantidad(bundle.getInt("p_cantidad"));
        pedido.setValorEnvio(bundle.getInt("p_envio"));

    }

    private void calcularValores() {
        // Calculamos el total del pedido
        pedido.calcularTotalDelPedido();

        //Calculamos la tasa de importacion
        impuestos.calcularTasaImportacion(pedido.getValorCif());
        //Calculamos el Iva
        impuestos.calcularIva(pedido.getValorCif());
        //Calculamos el total de impuestos
        impuestos.calcularTotalImpuesto();


        // Calculamos el total del pedido a clp
        moneda.calcularTotalPedidoClp(pedido.getTotalPedido());
        // Calculamos el valor cif a clp
        moneda.calcularValorCifClp(pedido.getValorCif());
        // Calculamos el envio a clp
        moneda.calcularEnvioClp(pedido.getValorEnvio());

        // Calculamos el valor de la aduana a clp
        moneda.calcularAduanaClp(impuestos.getValorTasaAduana());
        // Calculamos el iva a clp
        moneda.calcularIvaClp(impuestos.getValorIva());
        // Calculamos el total de impuestos a clp
        moneda.calcularTotalImpuesto(impuestos.getTotalImpuestos());
        // Calculamos el total de la compra a clp
        moneda.calcularTotalCompraClp();
        // Calculamos el total de la compra a USD
        moneda.calcularTotalCompraUsd(impuestos.getTotalImpuestos(),pedido.getValorCif());



    }

    private void mostrarResultado() {
        //Traemos el resultado con getters de la clase moneda
        txv_total_clp.setText("Total del pedido: $"+moneda.getValorTotalPedidoClp() + " CLP");
        txv_valor_cif.setText("Valor CIF: $" + moneda.getValorCifClp() + " CLP");
        txv_costo_envio.setText("Costo de envío: $"+ moneda.getValorEnvioClp() +" CLP" );
        txv_aduana.setText("Tasa aduana: $"+ moneda.getValorAduanaClp() + " CLP");
        txv_iva.setText("IVA: $" + moneda.getValorIvaClp() + " CLP");
        txv_total_impuesto.setText("Total de Iva y aduana: $"+ moneda.getTotalImpuestosClp()+ " CLP");
        txv_total_compra.setText("Total de la compra: $" + moneda.getTotalCompraClp()+" CLP " + "Equivalente a: $"+ moneda.getTotalCompraUsd()+ "USD");
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_enviar:
                try {
                    int total_pedido = moneda.getValorTotalPedidoClp();
                    int valor_cif = moneda.getValorCifClp();
                    int costo_envio = moneda.getValorEnvioClp();
                    int aduana = moneda.getValorAduanaClp();
                    int iva = moneda.getValorIvaClp();
                    int total_impuesto = moneda.getTotalImpuestosClp();
                    int total_compra = moneda.getTotalCompraClp();
                    enviarDatos(total_pedido,valor_cif,costo_envio,aduana,iva,total_impuesto,total_compra);
                    break;
                }catch (Exception e){
                    e.printStackTrace();
                }
            case R.id.btn_volver:
                onBackPressed();
                break;
        }

    }


    private void enviarDatos(int total_pedido, int valor_cif, int costo_envio, int aduana, int iva,int total_impuesto, int total_compra) {
        //Definimos un objeto JSON con los datos a enviar a la API mediante PUT
        JSONObject objetoJsonEnvio = new JSONObject();
        try {
            //Aquí se definen los datos a enviar. En este caso sólo uno.
            objetoJsonEnvio.put("total_pedido",total_pedido);
            objetoJsonEnvio.put("valor_cif",valor_cif);
            objetoJsonEnvio.put("costo_envio",costo_envio);
            objetoJsonEnvio.put("tasa_aduana",aduana);
            objetoJsonEnvio.put("iva",iva);
            objetoJsonEnvio.put("total_impuestos",total_impuesto);
            objetoJsonEnvio.put("total_compra",total_compra);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El objeto JSON lo convertimos en un string para enviar a la API. Esto es requerido por api-platform.
        final String datosAEnviar = objetoJsonEnvio.toString();

        //Creamos un objeto RequestQueue para efectuar el envío con la librería Volley
        RequestQueue colaEnvioVolley = Volley.newRequestQueue(this);

        //Definimos la ruta al servicio.
        String urlServicioAPI ="https://x7qyx68c.directus.app/items/importacion";

        //Configuramos la solicitud a la API mediante un String Request el cual posteriormente agregaremos a la cola de envío (queue)
        //Observar que se utiliza método POST (Puede ser PUT, GET, etc)
        StringRequest cadenaSolicitud = new StringRequest(Request.Method.POST, urlServicioAPI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuestaRecibida) {
                        // Bloque de instrucciones cuando la operación es exitosa. Este String
                        // denominado respuestaRecibida puede ser convertido a otros formatos (Por ejemplo json)
                        // En este caso se concatena como texto en un elemento TextView
                        try {

                            txv_respuesta.setText("Respuesta "+respuestaRecibida);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Bloque de instrucciones en caso de error. En este caso se muestra en un TextView.
                txv_respuesta.setText("Se ha producido un error al agregar "+error.getMessage());
            }

        })
        {
            @Override
            public Map<String, String> getHeaders()  {
                //Se configuran los encabezados HTTP. Establece el formato de envío requerido por la API.
                //La documentación de la API nos indica como acepta solicitudes
                Map<String,String> headers=new HashMap<String,String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Accept-Encoding", "utf-8");
                return headers;
            }
            @Override
            public byte[] getBody() {
                try {
                    //Se extraen los bytes de los datos a enviar.
                    return datosAEnviar == null ? null : datosAEnviar.getBytes("utf-8");
                } catch (UnsupportedEncodingException ex) {
                    txv_respuesta.setText("Error al extraer bytes"+ex.getMessage());
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                //En este caso se está forzando a enviar el encabezado HTTP Content-type como se indica
                return "application/json";
            }
        };

        //Finalmente se agrega a la cola de envío con lo cual Volley gestiona la solicitud.
        colaEnvioVolley.add(cadenaSolicitud);
    }



}