package com.example.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_fecha,edt_patente,edt_comentario,edt_id;
    Spinner spn_estado;
    TextView txv_respuesta;
    Button btn_registrar,btn_eliminar,btn_actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vincularElementos();
        habilitarListener();
    }

    private void habilitarListener() {
        btn_registrar.setOnClickListener(this);
        btn_eliminar.setOnClickListener(this);
        btn_actualizar.setOnClickListener(this);
    }

    private void enviarDatos(String patente,String fecha,String estado, String comentario){
        //Definimos un objeto JSON con los datos a enviar a la API mediante PUT
        JSONObject objetoJsonEnvio = new JSONObject();
        try {
            //Aquí se definen los datos a enviar. En este caso sólo uno.
            objetoJsonEnvio.put("patente",patente);
            objetoJsonEnvio.put("estado",estado);
            objetoJsonEnvio.put("comentario",comentario);
            objetoJsonEnvio.put("fecha",fecha);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El objeto JSON lo convertimos en un string para enviar a la API. Esto es requerido por api-platform.
        final String datosAEnviar = objetoJsonEnvio.toString();

        //Creamos un objeto RequestQueue para efectuar el envío con la librería Volley
        RequestQueue colaEnvioVolley = Volley.newRequestQueue(this);

        //Definimos la ruta al servicio.
        String urlServicioAPI ="https://x7qyx68c.directus.app/items/recepcion";

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
                txv_respuesta.setText("Se ha producido un error al agregar "+error.getMessage() + "fecha");
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
    private void enviarDatosActualizar(int id,String patente,String fecha,String estado, String comentario){
        //Definimos un objeto JSON con los datos a enviar a la API mediante PUT
        JSONObject objetoJsonEnvio = new JSONObject();
        try {
            //Aquí se definen los datos a enviar. En este caso sólo uno.
            objetoJsonEnvio.put("id",id);
            objetoJsonEnvio.put("patente",patente);
            objetoJsonEnvio.put("estado",estado);
            objetoJsonEnvio.put("comentario",comentario);
            objetoJsonEnvio.put("fecha",fecha);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El objeto JSON lo convertimos en un string para enviar a la API. Esto es requerido por api-platform.
        final String datosAEnviar = objetoJsonEnvio.toString();

        //Creamos un objeto RequestQueue para efectuar el envío con la librería Volley
        RequestQueue colaEnvioVolley = Volley.newRequestQueue(this);

        //Definimos la ruta al servicio.
        String urlServicioAPI ="https://x7qyx68c.directus.app/items/recepcion/"+id;

        //Configuramos la solicitud a la API mediante un String Request el cual posteriormente agregaremos a la cola de envío (queue)
        //Observar que se utiliza método POST (Puede ser PUT, GET, etc)
        StringRequest cadenaSolicitud = new StringRequest(Request.Method.PATCH, urlServicioAPI,
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
                txv_respuesta.setText("Se ha producido un error al agregar "+error.getMessage() + "fecha");
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
    private void enviarDatosEliminar(int id){
        //Definimos un objeto JSON con los datos a enviar a la API mediante PUT
        JSONObject objetoJsonEnvio = new JSONObject();
        try {
            //Aquí se definen los datos a enviar. En este caso sólo uno.
            objetoJsonEnvio.put("id",id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El objeto JSON lo convertimos en un string para enviar a la API. Esto es requerido por api-platform.
        final String datosAEnviar = objetoJsonEnvio.toString();

        //Creamos un objeto RequestQueue para efectuar el envío con la librería Volley
        RequestQueue colaEnvioVolley = Volley.newRequestQueue(this);

        //Definimos la ruta al servicio.
        String urlServicioAPI ="https://x7qyx68c.directus.app/items/recepcion/"+id;

        //Configuramos la solicitud a la API mediante un String Request el cual posteriormente agregaremos a la cola de envío (queue)
        //Observar que se utiliza método POST (Puede ser PUT, GET, etc)
        StringRequest cadenaSolicitud = new StringRequest(Request.Method.DELETE, urlServicioAPI,
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
                txv_respuesta.setText("Se ha producido un error al agregar "+error.getMessage() + "fecha" );
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

    private void vincularElementos() {
        edt_id = (EditText) findViewById(R.id.edt_id) ;
        edt_fecha = (EditText) findViewById(R.id.edt_fecha);
        edt_patente = (EditText) findViewById(R.id.edt_patente);
        edt_comentario = (EditText) findViewById(R.id.edt_comentario);
        txv_respuesta = (TextView) findViewById((R.id.txv_respuesta));
        btn_registrar = (Button)  findViewById(R.id.btn_registrar);
        btn_eliminar = (Button)  findViewById(R.id.btn_eliminar);
        btn_actualizar = (Button)  findViewById(R.id.btn_actualizar);

        spn_estado = (Spinner) findViewById(R.id.spn_estado);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_estado, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spn_estado.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registrar:
                String fecha = edt_fecha.getText().toString();
                String patente = edt_patente.getText().toString();
                String estado = spn_estado.getSelectedItem().toString();
                String comentario = edt_comentario.getText().toString();

                enviarDatos(patente,fecha,estado,comentario);
                txv_respuesta.setText("Tap de agregar");
                break;
            case R.id.btn_eliminar:
                int id = Integer.parseInt(edt_id.getText().toString());
                enviarDatosEliminar(id);
                txv_respuesta.setText("Tap eliminar");
                break;

            case R.id.btn_actualizar:
                id = Integer.parseInt(edt_id.getText().toString());
                fecha = edt_fecha.getText().toString();
                patente = edt_patente.getText().toString();
                 estado = spn_estado.getSelectedItem().toString();
                comentario = edt_comentario.getText().toString();
                enviarDatosActualizar(id,patente,fecha,estado,comentario);
                txv_respuesta.setText("Tap actualizar");
                break;
        }


    }
}