package com.navi.migaslp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CargaAdapter extends RecyclerView.Adapter<CargaAdapter.ViewHolder> {

    private Context context;
    private List<Carga> lista;

    public CargaAdapter(Context context, List<Carga> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carga carga = lista.get(position);
        holder.txtFecha.setText("Fecha: " + carga.getFecha());
        holder.txtCantidad.setText("Cantidad: " + carga.getCantidad() + " L");
        holder.txtNotas.setText("Notas: " + carga.getNotas());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AgregarEditarCargaActivity.class);
            intent.putExtra("id", carga.getId());
            intent.putExtra("fecha", carga.getFecha());
            intent.putExtra("cantidad", carga.getCantidad());
            intent.putExtra("notas", carga.getNotas());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFecha, txtCantidad, txtNotas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtNotas = itemView.findViewById(R.id.txtNotas);
        }
    }
}