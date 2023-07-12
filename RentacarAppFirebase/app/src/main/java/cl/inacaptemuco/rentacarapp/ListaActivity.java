package cl.inacaptemuco.rentacarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import modelo.Recepcion;

public class ListaActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView rcvRecepcion;
    //Lista de objetos de la clase Entrada
    private ArrayList<Recepcion> recepcionArrayList;
    //Adaptador
    private RecepcionAdapter recepcionAdapter;
    //Referencia a base de datos en Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        rcvRecepcion =  findViewById(R.id.rcv_recepcion);
        db = FirebaseFirestore.getInstance();

        recepcionArrayList = new ArrayList<>();
        //Propiedades de RecylerView
        rcvRecepcion.setHasFixedSize(true);
        rcvRecepcion.setLayoutManager(new LinearLayoutManager(this));
        //Se inicia adaptador
        recepcionAdapter = new RecepcionAdapter(recepcionArrayList,this);
        //Se asigna adaptador a Recycler
        rcvRecepcion.setAdapter(recepcionAdapter);

        //Definimos la colección desde la cual obtenemos datos
        db.collection("Recepcion").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Chequeamos si viene vacío
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Si no viene vacío conformamos lista
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                //Se obtienen los datos y se instancia por cada documento un objeto de la clase Recepcion
                                Recepcion recepcion = d.toObject(Recepcion.class);

                                //ID para la actualización
                                recepcion.setId(d.getId());

                                //El objeto se agrega a la lista de objetos de la clase Recepcion
                                recepcionArrayList.add(recepcion);

                            }
                            //Notificación de actualización de datos
                            recepcionAdapter.notifyDataSetChanged();

                        } else {
                            // Si no se encuentran datos
                            Toast.makeText(ListaActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // En caso de excepciones
                        Toast.makeText(ListaActivity.this, "Error al obtener datos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}