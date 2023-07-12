package cl.inacaptemuco.estudiantesapp;

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



import modelo.Lista;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txv_user;
    Button btn_registrar,btn_listar;
    EditText edt_nombre,edt_apellido,edt_edad,edt_carrera;
    Spinner spn_estado;

    private FirebaseFirestore firebaseFirestore;
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

    private void mostrarUsuario() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txv_user.setText("Email " + user.getDisplayName());
    }

    private void iniciarFireStore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    private void habilitarListener(){
        btn_registrar.setOnClickListener(this);
        btn_listar.setOnClickListener(this);
    }
    private void vincularElementos(){
        edt_nombre = (EditText) findViewById(R.id.edt_nombre);
        edt_apellido= (EditText) findViewById(R.id.edt_apellido);
        edt_edad = (EditText) findViewById(R.id.edt_edad);
        edt_carrera = (EditText) findViewById(R.id.edt_carrera);

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
        switch (view.getId()) {
            case R.id.btn_registrar:
                String nombres = edt_nombre.getText().toString();
                String apellidos = edt_apellido.getText().toString();
                String carrera = edt_carrera.getText().toString();
                String estado = spn_estado.getSelectedItem().toString();
                String edad = edt_edad.getText().toString();
                agregarFirestore(nombres,apellidos,edad,carrera,estado);
                break;
            case R.id.btn_listar:
                Intent intento = new Intent(MainActivity.this,ListaActivity.class);
                startActivity(intento);
                break;
        }

    }
    private void agregarFirestore(String nombres, String apellidos,String edad, String carrera, String estado) {
        //Colección en Firestore
        CollectionReference coleccionLista = firebaseFirestore.collection("Lista");
        //Objeto Entrada
        Lista lista = new Lista(nombres,apellidos,edad,carrera, estado);
        //intentamos agregar
        coleccionLista.add(lista).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //En caso de éxito mostramos mensaje mediante Toast
                Toast.makeText(MainActivity.this,"Estudiante registrada correctamente",Toast.LENGTH_SHORT).show();

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