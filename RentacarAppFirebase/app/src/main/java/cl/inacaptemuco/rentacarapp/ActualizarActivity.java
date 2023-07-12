package cl.inacaptemuco.rentacarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import modelo.Recepcion;

public class ActualizarActivity extends AppCompatActivity implements View.OnClickListener {
    //Variables globales
    // creating variables for our edit text
    private EditText edtPatente,edtFecha,edtComentario;
    private Spinner spnEstado;
    private Button btnActualizar,btnEliminar;
    private Date fecha;

    private String idUpdate;

    // creating a strings for storing our values from Edittext fields.
    private String patente, comentario,estado;

    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;

    Recepcion recepcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        //Obtenemos valores actuales
        recepcion = (Recepcion) getIntent().getSerializableExtra("recepcion");


        //Preparaciòn de actualizaciòn
        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        vincularElementos();
        habilitarListener();


        edtFecha.setText(recepcion.getFecha().toString());
        edtPatente.setText(recepcion.getPatente());
        edtFecha.setText(recepcion.getFecha().toString());
        edtComentario.setText(recepcion.getComentario());

        idUpdate = recepcion.getId();
        Toast.makeText(ActualizarActivity.this, "ID " + recepcion.getId(), Toast.LENGTH_SHORT).show();

    }

    private void vincularElementos() {
        edtFecha = findViewById(R.id.edt_fecha);
        edtPatente = findViewById(R.id.edt_patente);
        edtComentario = findViewById(R.id.edt_comentario);
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        spnEstado = findViewById(R.id.spn_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adapter);
    }

    private void habilitarListener() {
        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_actualizar){

            //Obtenemos datos
            String fechaIngreso = edtFecha.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(fechaIngreso);
                fecha = date;
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            patente = edtPatente.getText().toString();

            comentario = edtComentario.getText().toString();
            estado = spnEstado.getSelectedItem().toString();

            updateRecepcion(patente,fecha,estado,comentario);
        }
        if (view.getId() == R.id.btn_eliminar){
            eliminarRecepcion(idUpdate);
        }
    }



    private void updateRecepcion( String patente, Date fecha, String estado, String comentario) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Recepcion recepcionActualizada = new Recepcion(patente,comentario,estado,fecha);
        //entradaActualizada.setId(idUpdate);
        HashMap<String, Object> updateRecepcion = new HashMap<>();
        //updateRecepcion.put("fecha", recepcionActualizada.getFecha());
        updateRecepcion.put("estado", recepcionActualizada.getEstado());
        updateRecepcion.put("patente", recepcionActualizada.getPatente());
        updateRecepcion.put("comentario", recepcionActualizada.getComentario());

        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Recepcion").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.

                        document(idUpdate).

                // after setting our document id we are
                // passing our whole object class to it.

                        update(updateRecepcion).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        Toast.makeText(ActualizarActivity.this, "Entrada Actulizada", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActualizarActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void eliminarRecepcion(String idUpdate) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Recepcion").
                // after that we are getting the document
                // which we have to delete.
                        document(idUpdate).

                // after passing the document id we are calling
                // delete method to delete this document.
                        delete().
                // after deleting call on complete listener
                // method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // inside on complete method we are checking
                        // if the task is success or not.
                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(ActualizarActivity.this, "Entrada eliminada.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ActualizarActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(ActualizarActivity.this, "Error al eliminar. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}