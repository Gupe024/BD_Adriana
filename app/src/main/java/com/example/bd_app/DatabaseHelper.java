package com.example.bd_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ventas.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_VENTAS = "ventas";
    static final String COLUMN_ID = "id";
    private static final String COLUMN_ORIGEN = "origen";
    private static final String COLUMN_DESTINO = "destino";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_HORA = "hora";
    private static final String COLUMN_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_VENTAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORIGEN + " TEXT, " +
                COLUMN_DESTINO + " TEXT, " +
                COLUMN_FECHA + " TEXT, " +
                COLUMN_HORA + " TEXT, " +
                COLUMN_TOTAL + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTAS);
        onCreate(db);
    }

    public void agregarVenta(Venta venta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORIGEN, venta.getOrigen());
        values.put(COLUMN_DESTINO, venta.getDestino());
        values.put(COLUMN_FECHA, venta.getFecha());
        values.put(COLUMN_HORA, venta.getHora());
        values.put(COLUMN_TOTAL, venta.getTotal());

        long id = db.insert(TABLE_VENTAS, null, values);
        venta.setId((int) id);

        db.close();
    }

    @SuppressLint("Range")
    public Venta obtenerVentaPorID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VENTAS, new String[]{COLUMN_ID, COLUMN_ORIGEN, COLUMN_DESTINO, COLUMN_FECHA, COLUMN_HORA, COLUMN_TOTAL},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Venta venta = new Venta(
                    cursor.getString(cursor.getColumnIndex(COLUMN_ORIGEN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESTINO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_FECHA)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HORA)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL))
            );
            venta.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            cursor.close();
            db.close();
            return venta;
        } else {
            return null;
        }
    }

    public void actualizarVenta(Venta venta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORIGEN, venta.getOrigen());
        values.put(COLUMN_DESTINO, venta.getDestino());
        values.put(COLUMN_FECHA, venta.getFecha());
        values.put(COLUMN_HORA, venta.getHora());
        values.put(COLUMN_TOTAL, venta.getTotal());

        db.update(TABLE_VENTAS, values, COLUMN_ID + "=?", new String[]{String.valueOf(venta.getId())});
        db.close();
    }

}
