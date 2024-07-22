package com.example.bd_app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class eliminar extends AppCompatActivity {

    private EditText editEliminar;
    private Button btnBuscar;
    private Button btnEliminar;
    private Button btnCancelar;
    private TextView textViewEliminar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        editEliminar = findViewById(R.id.editEliminar);
        btnBuscar = findViewById(R.id.btn_buscar);
        btnEliminar = findViewById(R.id.btneliminar);
        btnCancelar = findViewById(R.id.btcancelar_Regresar);
        textViewEliminar = findViewById(R.id.text_viewEliminar);

        databaseHelper = new DatabaseHelper(this);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarVenta();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarVenta();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresar al menú o actividad anterior
            }
        });
    }

    private void buscarVenta() {
        String idStr = editEliminar.getText().toString();
        if (idStr.isEmpty()) {
            textViewEliminar.setText("Ingrese un ID válido.");
            return;
        }

        int id = Integer.parseInt(idStr);
        Venta venta = databaseHelper.obtenerVentaPorID(id);
        if (venta != null) {
            textViewEliminar.setText("ID: " + venta.getId() + "\n" +
                    "Origen: " + venta.getOrigen() + "\n" +
                    "Destino: " + venta.getDestino() + "\n" +
                    "Fecha: " + venta.getFecha() + "\n" +
                    "Hora: " + venta.getHora() + "\n" +
                    "Total: " + venta.getTotal());
        } else {
            textViewEliminar.setText("Venta no encontrada.");
        }
    }

    private void eliminarVenta() {
        String idStr = editEliminar.getText().toString();
        if (idStr.isEmpty()) {
            textViewEliminar.setText("Ingrese un ID válido.");
            return;
        }

        int id = Integer.parseInt(idStr);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsAffected = db.delete(DatabaseHelper.TABLE_VENTAS, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        if (rowsAffected > 0) {
            textViewEliminar.setText("Venta eliminada.");
        } else {
            textViewEliminar.setText("Error al eliminar la venta. Verifique el ID.");
        }
    }
}