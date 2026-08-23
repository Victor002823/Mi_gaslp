package com.navi.migaslp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AgregarEditarCargaActivity extends AppCompatActivity {

    private TextInputEditText edtFecha, edtCantidad, edtCosto;
    private Button btnGuardar, btnBorrar;
    private DBHelper dbHelper;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editar_carga);

        edtFecha = findViewById(R.id.edtFecha);
        edtCantidad = findViewById(R.id.edtCantidad);
        edtCosto = findViewById(R.id.edtCosto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBorrar = findViewById(R.id.btnBorrar);

        dbHelper = new DBHelper(this);

        // Configurar calendario compacto para la fecha
        edtFecha.setShowSoftInputOnFocus(false);
        edtFecha.setOnClickListener(v -> mostrarDatePicker());

        // Revisar si vienen datos para editar
        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
            edtFecha.setText(getIntent().getStringExtra("fecha"));
            edtCantidad.setText(String.valueOf(getIntent().getDoubleExtra("cantidad", 0)));
            edtCosto.setText(String.valueOf(getIntent().getDoubleExtra("costo", 0)));
            btnBorrar.setVisibility(View.VISIBLE);
        }

        btnGuardar.setOnClickListener(v -> guardarCarga());
        btnBorrar.setOnClickListener(v -> borrarCarga());
    }

    private void mostrarDatePicker() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calendario, null);
        CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelarFecha);
        Button btnAceptar = dialogView.findViewById(R.id.btnAceptarFecha);

        final long[] seleccionMillis = {calendarView.getDate()};
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            seleccionMillis[0] = c.getTimeInMillis();
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
        btnAceptar.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(seleccionMillis[0]);
            String fecha = String.format("%02d/%02d/%04d",
                    c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
            edtFecha.setText(fecha);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void guardarCarga() {
        String fecha = edtFecha.getText().toString().trim();
        String cantidadStr = edtCantidad.getText().toString().trim();
        String costoStr = edtCosto.getText().toString().trim();

        if (fecha.isEmpty() || cantidadStr.isEmpty() || costoStr.isEmpty()) {
            if (fecha.isEmpty()) edtFecha.setError("Requerido");
            if (cantidadStr.isEmpty()) edtCantidad.setError("Requerido");
            if (costoStr.isEmpty()) edtCosto.setError("Requerido");
            return;
        }

        double cantidad = Double.parseDouble(cantidadStr);
        double costo = Double.parseDouble(costoStr);
        Carga carga = new Carga(fecha, cantidad, costo);

        if (id == -1) {
            dbHelper.agregarCarga(carga);
        } else {
            carga.setId(id);
            dbHelper.actualizarCarga(carga);
        }

        finish();
    }

    private void borrarCarga() {
        if (id != -1) {
            dbHelper.borrarCarga(id);
        }
        finish();
    }
}
