package com.example.seguroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_marca,edt_modelo,edt_anio;
    Button btn_calcular;
    Boolean datosValidados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Vincular elementos
        vincularElementos();
        //Habilitar Listener
        habilitarListener();
    }

    private void habilitarListener() {
        btn_calcular.setOnClickListener(this);
    }

    private void vincularElementos() {
        edt_marca        = (EditText) findViewById(R.id.edt_marca);
        edt_modelo = (EditText) findViewById(R.id.edt_modelo);
        edt_anio = (EditText) findViewById(R.id.edt_anio);
        btn_calcular    = (Button) findViewById(R.id.btn_calcular);

    }

    private void alertar(){
        Context context = getApplicationContext();
        CharSequence text = "Ingresa todos los campos del formulario";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context,text,duration);
        toast.show();
    }
    @Override
    public void onClick(View view) {
        //Implementamos intento para enviar datos a ResultadosActivity
        String marca = edt_marca.getText().toString();
        String  modelo = edt_modelo.getText().toString();
        int anio = Integer.parseInt(edt_anio.getText().toString());

        //Configuramos intento
        Intent intento = new Intent(MainActivity.this,ResultadoActivity.class);
        //Agregamos datos a enviar
        intento.putExtra("p_marca",marca);
        intento.putExtra("p_modelo",modelo);
        intento.putExtra("p_anio",anio);


        startActivity(intento);
      


    }


}

