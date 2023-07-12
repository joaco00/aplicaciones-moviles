package operaciones;

import java.util.Calendar;

public class Vehiculo {

    private String marca;
    private String  modelo;
    private int anio;
    private int antiguedad;
    /*Calculamos antiguedad del vehiculo comparando el año actual con el año del vehiculo */
    public void calcularAntiguedad(){
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        this.antiguedad = anioActual - anio;
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }



}
