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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import modelo.Recepcion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_fecha,edt_patente,edt_comentario;
    Button btn_registrar,btn_listar;
    Spinner spn_estado;
    TextView txv_user;
    //Variable de objeto de firestore
    private FirebaseFirestore firebaseFirestore;
    //Declaramos variables para almacenar la recepcion
    private String patente,estado,comentario;
    private  Date fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vincularElementos();
        habilitarListener();
        mostrarUsuario();
        //Iniciamos firestore
        iniciarFireStore();
    }

    private void iniciarFireStore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void mostrarUsuario() {
        //Creamos objeto de la clase firebaseuser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txv_user.setText("Usuario " + user.getEmail());

    }

    private void habilitarListener() {
        btn_registrar.setOnClickListener(this);
        btn_listar.setOnClickListener(this);
    }

    private void vincularElementos() {
        edt_fecha = (EditText) findViewById(R.id.edt_fecha);
        edt_patente = (EditText) findViewById(R.id.edt_patente);
        edt_comentario = (EditText) findViewById(R.id.edt_comentario);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_listar = (Button) findViewById(R.id.btn_listar);
        txv_user = (TextView) findViewById(R.id.txv_user);

        spn_estado = (Spinner) findViewById(R.id.spn_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_estado.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        //Obtenemos datos de la interfaz
        String fechaIngreso = edt_fecha.getText().toString();
        //Convertimos de string a date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(fechaIngreso);
            fecha = date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.btn_registrar:
                patente = edt_patente.getText().toString();
                comentario = edt_comentario.getText().toString();
                estado = spn_estado.getSelectedItem().toString();

                agregarFirestore(fecha, patente, estado, comentario);
                break;
            case R.id.btn_listar:
                Intent intento = new Intent(MainActivity.this,ListaActivity.class);
                startActivity(intento);
                break;
        }
    }
    private void agregarFirestore(Date fecha, String patente, String estado, String comentario) {
        //Colección en Firestore
        CollectionReference coleccionEntradas = firebaseFirestore.collection("Recepcion");
        //Objeto Entrada
         Recepcion recepcion = new Recepcion(patente,estado,comentario,fecha);
        //intentamos agregar
        coleccionEntradas.add(recepcion).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //En caso de éxito mostramos mensaje mediante Toast
                Toast.makeText(MainActivity.this,"Entrada registrada correctamente",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //En caso de error, mensaje similar al anterior.
                Toast.makeText(MainActivity.this,"Error al agregar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}