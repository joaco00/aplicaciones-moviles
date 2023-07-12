package cl.inacaptemuco.estudiantesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import modelo.Lista;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {
    private ArrayList<Lista> listaArrayList;
    private Context context;

    public ListaAdapter(ArrayList<Lista> listaArrayList, Context context) {
        this.listaArrayList = listaArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ListaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ListaAdapter.ViewHolder holder, int position) {
        //Objetivo individual obtenido desde la lista de objetos
        Lista lista = listaArrayList.get(position);
        //Se extraen propiedades y se asigna a TextView
        holder.txv_nombres.setText(lista.getNombres());
        holder.txv_apellidos.setText(lista.getApellidos());
        holder.txv_edad.setText(lista.getEdad());
        holder.txv_carrera.setText(lista.getCarrera());
        holder.txv_estado.setText(lista.getEstado());

    }
    @Override
    public int getItemCount() {
        return listaArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Variables de Objeto
        private final TextView txv_nombres,txv_apellidos,txv_edad,txv_carrera,txv_estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Cada variable de objeto se asocia a elementos de la interfaz archivos de layout
            txv_nombres = itemView.findViewById(R.id.txv_nombres);
            txv_apellidos = itemView.findViewById(R.id.txv_apellidos);
            txv_edad = itemView.findViewById(R.id.txv_edad);
            txv_carrera = itemView.findViewById(R.id.txv_carrera);
            txv_estado = itemView.findViewById(R.id.txv_estado);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    Lista lista = listaArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, ActualizarActivity.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("lista", lista);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);

                }
            });

        }
    }
}
