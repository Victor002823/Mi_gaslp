package com.navi.migaslp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cargasGas.db";
    private static final int DATABASE_VERSION = 5;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE cargas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT," +
                "cantidad REAL," +
                "costo REAL," +
                "notas TEXT)";
        db.execSQL(CREATE_TABLE);
        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cargas");
        onCreate(db);
    }

    private void insertarDatosIniciales(SQLiteDatabase db) {
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('22/08/2026', 29.0, 300.0, '300')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('02/08/2026', 18.0, 200.0, '$200')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('29/06/2026', 18.0, 200.0, '$200')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('28/06/2026', 18.0, 200.0, '$200')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('22/05/2026', 28.0, 300.0, '$300')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('07/04/2026', 28.0, 300.0, '$300')");
        db.execSQL("INSERT INTO cargas (fecha, cantidad, costo, notas) VALUES ('07/03/2026', 27.0, 300.0, '$300')");
    }

    public long agregarCarga(Carga carga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", carga.getFecha());
        values.put("cantidad", carga.getCantidad());
        values.put("costo", carga.getCosto());
        values.put("notas", carga.getNotas());
        return db.insert("cargas", null, values);
    }

    public Cursor obtenerTodasLasCargas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cargas", null);
        // Si la tabla está vacía por alguna razón, insertamos los datos iniciales al vuelo
        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null) cursor.close();
            SQLiteDatabase writableDb = this.getWritableDatabase();
            insertarDatosIniciales(writableDb);
            cursor = writableDb.rawQuery("SELECT * FROM cargas", null);
        }
        return db.rawQuery("SELECT * FROM cargas ORDER BY id DESC", null);
    }

    public int actualizarCarga(Carga carga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", carga.getFecha());
        values.put("cantidad", carga.getCantidad());
        values.put("costo", carga.getCosto());
        values.put("notas", carga.getNotas());
        return db.update("cargas", values, "id=?", new String[]{String.valueOf(carga.getId())});
    }

    public void borrarCarga(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cargas", "id=?", new String[]{String.valueOf(id)});
    }
}
