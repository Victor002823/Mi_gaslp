package com.navi.migaslp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerCargas;
    private CargaAdapter adapter;
    private DBHelper dbHelper;
    private FloatingActionButton btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerCargas = findViewById(R.id.recyclerCargas);
        btnAgregar = findViewById(R.id.btnAgregar);
        dbHelper = new DBHelper(this);
        recyclerCargas.setLayoutManager(new LinearLayoutManager(this));
        
        cargarCargas();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AgregarEditarCargaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarCargas();
    }

    private void cargarCargas() {
        List<Carga> lista = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerTodasLasCargas();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Carga carga = new Carga();
                    carga.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    carga.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                    carga.setCantidad(cursor.getDouble(cursor.getColumnIndexOrThrow("cantidad")));
                    carga.setCosto(cursor.getDouble(cursor.getColumnIndexOrThrow("costo")));
                    carga.setNotas(cursor.getString(cursor.getColumnIndexOrThrow("notas")));
                    lista.add(carga);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        adapter = new CargaAdapter(this, lista);
        recyclerCargas.setAdapter(adapter);
    }
}
