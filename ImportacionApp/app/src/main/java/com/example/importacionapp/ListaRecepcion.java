package com.example.importacionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import operaciones.Recepcion;

public class ListaRecepcion extends AppCompatActivity {
    private ArrayList<Recepcion> listaRecepcion;
    private RequestQueue requestQueue;
    private RecyclerView rcvRecepcion;
    private AdaptadorRecepcion adaptadorRecepcion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_recepcion);
        // Iniciar componentes
        iniciarComponentes();
        // Inicializamos la lista
        listaRecepcion = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        cargarRecepcion();

        //Iniciamos layout manager
        iniciarLayoutManager();
        establecerAdaptador();


    }

    private void establecerAdaptador() {
        adaptadorRecepcion = new AdaptadorRecepcion();
        rcvRecepcion.setAdapter(adaptadorRecepcion);
    }

    private void iniciarLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRecepcion.setLayoutManager(linearLayoutManager);
    }
    //Inicializar componentes
    private void iniciarComponentes() {
        rcvRecepcion = (RecyclerView) findViewById(R.id.rcv_recepcion);
    }

    private void cargarRecepcion() {
        String url = "https://x7qyx68c.directus.app/items/importacion";
        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respuesta = null;
                        try {
                            respuesta = response.get("data").toString();
                            JSONArray jsonArray = new JSONArray(respuesta);


                            int tamano = jsonArray.length();
                            for(int i=0;i< tamano;i++){
                                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                                int id     = jsonObject.getInt("id");
                                String total_pedido    = jsonObject.getString("total_pedido");
                                String valor_cif     = jsonObject.getString("valor_cif");
                                String costo_envio  = jsonObject.getString("costo_envio");
                                String tasa_aduana        = jsonObject.getString("tasa_aduana");
                                String iva        = jsonObject.getString("iva");
                                String total_impuesto       = jsonObject.getString("total_impuestos");
                                String total_compra      = jsonObject.getString("total_compra");
                                Recepcion recepcion = new Recepcion(id,total_pedido,valor_cif,costo_envio,tasa_aduana,iva,total_impuesto,total_compra);
                                listaRecepcion.add(recepcion);
                            }


                            adaptadorRecepcion.notifyItemRangeInserted(listaRecepcion.size(),1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
    ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    private void eliminarRecepcion(int id,int posicion) {
        String url = "https://x7qyx68c.directus.app/items/importacion/"+id;
        StringRequest stringRequestDelete   = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ListaRecepcion.this, "Eliminado "+ id, Toast.LENGTH_SHORT).show();
                listaRecepcion.remove(posicion);
                adaptadorRecepcion.notifyItemRemoved(posicion);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaRecepcion.this, "Error "+ error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequestDelete);
    }


    //Clase interna
    private class AdaptadorRecepcion extends RecyclerView.Adapter<AdaptadorRecepcion.AdaptadorRecepcionHolder> {

        @NonNull
        @Override
        public AdaptadorRecepcionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorRecepcionHolder(getLayoutInflater().inflate(R.layout.layout_recepcion,parent,false));

        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorRecepcionHolder holder, int position) {
            holder.mostrarValores(position);

        }

        @Override
        public int getItemCount() {
            return listaRecepcion.size();
        }

        class AdaptadorRecepcionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView txvPedido,txvValorCif,txvCostoEnvio,txvAduana,txvId,txvIva,txvImpuestos,txvCompra;
            ImageButton imbEliminar;
            int idEliminar,idPosicion;


            public AdaptadorRecepcionHolder(@NonNull View itemView) {
                super(itemView);
                txvId = itemView.findViewById(R.id.txv_id);
                txvPedido = itemView.findViewById(R.id.txv_total_pedido);
                txvValorCif = itemView.findViewById(R.id.txv_valor_cif);
                txvCostoEnvio = itemView.findViewById(R.id.txv_costo_envio);
                txvAduana = itemView.findViewById(R.id.txv_aduana);
                txvIva = itemView.findViewById(R.id.txv_iva);
                txvImpuestos = itemView.findViewById(R.id.txv_total_impuesto);
                txvCompra = itemView.findViewById(R.id.txv_total_compra);
                imbEliminar = itemView.findViewById(R.id.imb_eliminar);
                imbEliminar.setOnClickListener(this);
            }

            @SuppressLint("SetTextI18n")
            public void mostrarValores(int posicion) {
                idEliminar = listaRecepcion.get(posicion).getId();
                idPosicion = posicion;
                txvId.setText("ID : " + listaRecepcion.get(posicion).getId());
                txvPedido.setText("Total del pedido : $" + listaRecepcion.get(posicion).getTotal_pedido() +" CLP");
                txvValorCif.setText("Valor CIF : $"+ listaRecepcion.get(posicion).getValor_cif()+ " CLP");
                txvCostoEnvio.setText("Costo envio :  $" + listaRecepcion.get(posicion).getCosto_envio()+ " CLP");
                txvAduana.setText("Aduana : $" + listaRecepcion.get(posicion).getAduana()+ " CLP");
                txvIva.setText("Iva : $" + listaRecepcion.get(posicion).getIva() + " CLP");
                txvImpuestos.setText("Total Impuestos : $" + listaRecepcion.get(posicion).getTotal_impuesto() + " CLP");
                txvCompra.setText("Total Compra : $" + listaRecepcion.get(posicion).getTotal_compra() + " CLP");
            }

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ListaRecepcion.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Confirma eliminación?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                               eliminarRecepcion(idEliminar,idPosicion);
                               //Toast.makeText(ListaRecepcion.this, "Eliminado "+ idEliminar, Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        }
    }
}