package com.example.seguroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import operaciones.Seguro;
import operaciones.Vehiculo;

public class ResultadoActivity extends AppCompatActivity {
    TextView txv_valor_seguro;
    Intent intento;
    Bundle bundle;
    Vehiculo vehiculo;
    Seguro seguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        //cREAMOS OBJETOS DE LA CLASE VEHICulo y seguro
        vehiculo = new Vehiculo();
        seguro = new Seguro();
        //Vincular los elementos
        vincularElementos();
        obtenerDatos();
        asignarValores();
        calcularValores();
        mostrarResultado();
    }

    private void mostrarResultado() {
        txv_valor_seguro.setText("Valor seguro: "+ seguro.getValorSeguro());
    }

    private void calcularValores() {
        //Calculamos la antiguedad
        vehiculo.calcularAntiguedad();
        //Calculamos el valor del seguro
        seguro.calcularValorSeguro(vehiculo.getAntiguedad());
    }


    private void asignarValores() {
        vehiculo.setMarca(bundle.getString("p_marca"));
        vehiculo.setModelo(bundle.getString("p_modelo"));
        vehiculo.setAnio(bundle.getInt("p_anio"));
    }

    private void obtenerDatos() {
        intento = getIntent();
        bundle = intento.getExtras();
    }

    private void vincularElementos() {
        txv_valor_seguro = (TextView) findViewById(R.id.txv_valor_seguro);
    }
}