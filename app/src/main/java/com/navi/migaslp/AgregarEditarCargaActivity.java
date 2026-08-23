package com.navi.migaslp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

                // Configurar DatePicker para la fecha
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
                final Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                        String fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        edtFecha.setText(fecha);
                },
                anio, mes, dia);

                datePickerDialog.show();
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
