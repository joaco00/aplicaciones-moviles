package cl.inacaptemuco.rentacarapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import modelo.Recepcion;

public class RecepcionAdapter extends RecyclerView.Adapter<RecepcionAdapter.ViewHolder> {
    private ArrayList<Recepcion> recepcionArrayList;
    private Context context;

    public RecepcionAdapter(ArrayList<Recepcion> recepcionArrayList, Context context) {
        this.recepcionArrayList = recepcionArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecepcionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recepcion_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecepcionAdapter.ViewHolder holder, int position) {
        //Objetivo individual obtenido desde la lista de objetos
        Recepcion recepcion = recepcionArrayList.get(position);
        //Se extraen propiedades y se asigna a TextView
        holder.txv_patente.setText(recepcion.getPatente());
        holder.txv_fecha.setText(recepcion.getFecha().toString());
        holder.txv_estado.setText(recepcion.getEstado());
        holder.txv_comentario.setText(recepcion.getComentario());

    }

    @Override
    public int getItemCount() {
        return recepcionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Variables de Objeto
        private final TextView txv_patente,txv_fecha,txv_estado,txv_comentario;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Cada variable de objeto se asocia a elementos de la interfaz archivos de layout
            txv_patente = itemView.findViewById(R.id.txv_patente);
            txv_fecha = itemView.findViewById(R.id.txv_fecha);
            txv_estado = itemView.findViewById(R.id.txv_estado);
            txv_comentario = itemView.findViewById(R.id.txv_comentario);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    Recepcion recepcion = recepcionArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, ActualizarActivity.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("recepcion", recepcion);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }
            });

        }
    }
}
