package com.navi.migaslp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "cargasGas.db";
	private static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE cargas (" +
		"id INTEGER PRIMARY KEY AUTOINCREMENT," +
		"fecha TEXT," +
		"cantidad REAL," +
		"notas TEXT)";
		db.execSQL(CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS cargas");
		onCreate(db);
	}
	
	// Agregar nueva carga
	public long agregarCarga(Carga carga) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("fecha", carga.getFecha());
		values.put("cantidad", carga.getCantidad());
		values.put("notas", carga.getNotas());
		return db.insert("cargas", null, values);
	}
	
	// Obtener todas las cargas ordenadas por creación (De la más nueva a la más antigua)
	public Cursor obtenerTodasLasCargas() {
		SQLiteDatabase db = this.getReadableDatabase();
		// Usamos 'id DESC' porque garantiza el orden cronológico estricto de inserción
		return db.rawQuery("SELECT * FROM cargas ORDER BY id DESC", null);
	}
	
	// Actualizar carga existente
	public int actualizarCarga(Carga carga) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("fecha", carga.getFecha());
		values.put("cantidad", carga.getCantidad());
		values.put("notas", carga.getNotas());
		return db.update("cargas", values, "id=?", new String[]{String.valueOf(carga.getId())});
	}
	
	// Borrar carga
	public void borrarCarga(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("cargas", "id=?", new String[]{String.valueOf(id)});
	}
}