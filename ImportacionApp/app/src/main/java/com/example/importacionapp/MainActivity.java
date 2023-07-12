package com.example.importacionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_cantidad, edt_costo, edt_nombre, edt_proveedor, edt_codigo, edt_envio;
    Button btn_calcular, btn_listar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vincularElementos();
        habilitarListener();
    }

    private void habilitarListener() {
        btn_calcular.setOnClickListener(this);
        btn_listar.setOnClickListener(this);
    }

    private void vincularElementos() {
        //Vinculamos elementos con las id definidas en el layout
        edt_cantidad = (EditText) findViewById(R.id.edt_cantidad);
        edt_costo = (EditText) findViewById(R.id.edt_costo);
        edt_envio = (EditText) findViewById(R.id.edt_envio);
        edt_nombre = (EditText) findViewById(R.id.edt_nombre);
        edt_proveedor = (EditText) findViewById(R.id.edt_proveedor);
        edt_codigo = (EditText) findViewById(R.id.edt_codigo);
        btn_calcular = (Button) findViewById(R.id.btn_calcular);
        btn_listar = (Button) findViewById(R.id.btn_listar);
    }

    public boolean validar() {
        //Iniciamos una variable booleana en verdadero
        boolean retorno = true;
        //Llamamos a los campos para validar
        int c1 = Integer.parseInt(edt_cantidad.getText().toString());
        int c2 = Integer.parseInt(edt_costo.getText().toString());
        int c3 = Integer.parseInt(edt_envio.getText().toString());
        int c4= Integer.parseInt(edt_codigo.getText().toString());
        String c5 = edt_nombre.getText().toString();
        String c6 = edt_proveedor.getText().toString();

        if (c1 <=0){
                edt_cantidad.setError("Este campo debe ser mayor a 0");
                retorno = false;

        }
        if (c2 <=0){
            edt_costo.setError("Este campo debe ser mayor a 0");
            retorno = false;

        }
        if (c3 <=0){
            edt_envio.setError("Este campo debe ser mayor a 0");
            retorno = false;

        }
        if (c4 <=0){
            edt_codigo.setError("Este campo debe ser mayor a 0");
            retorno = false;

        }

        if (c5.isEmpty()) {
            edt_nombre.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        if (c6.isEmpty()) {
            edt_proveedor.setError("Este campo no puede quedar vacio");
            retorno = false;
        }
        return retorno;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calcular:
                if(validar()) {
                    Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                    //Implementamos intento para enviar datos a ResultadoActivity
                    int cantidad = Integer.parseInt(edt_cantidad.getText().toString());
                    int costo = Integer.parseInt(edt_costo.getText().toString());
                    int envio = Integer.parseInt(edt_envio.getText().toString());
                    int codigo = Integer.parseInt(edt_codigo.getText().toString());
                    String nombre = edt_nombre.getText().toString();
                    String proveedor = edt_proveedor.getText().toString();


                    Intent intento = new Intent(MainActivity.this, ResultadoActivity.class);

                    //Agregamos los datos a enviar a ResultadoActivity
                    intento.putExtra("p_cantidad", cantidad);
                    intento.putExtra("p_costo", costo);
                    intento.putExtra("p_envio", envio);
                    intento.putExtra("p_codigo", codigo);
                    intento.putExtra("p_nombre", nombre);
                    intento.putExtra("p_proveedor", proveedor);

                    startActivity(intento);

                }else{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_listar:
                Intent intento2 = new Intent(MainActivity.this,ListaRecepcion.class);
                startActivity(intento2);
                break;

        }
    }
}



