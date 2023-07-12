package modelo;

import java.io.Serializable;
import com.google.firebase.firestore.Exclude;

public class Lista implements Serializable {

    //Creamos propiedades de la lista de estudiantes
    private String nombres,apellidos,carrera,estado,edad;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    // we are using exclude because
    // we are not saving our id
    @Exclude
    private String id;


    public Lista(String nombres, String apellidos, String edad,String carrera, String estado ) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.carrera = carrera;
        this.estado = estado;
        this.edad = edad;
    }
    public Lista(){

    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
