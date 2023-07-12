package modelo;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Date;

public class Recepcion implements Serializable {

    //Creamos propiedades de una recepcion
    private  String patente,estado,comentario;
    private Date fecha;

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

    public Recepcion(String patente, String estado, String comentario, Date fecha) {
        this.patente = patente;
        this.estado = estado;
        this.comentario = comentario;
        this.fecha = fecha;
    }
    public Recepcion(){

    }
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
