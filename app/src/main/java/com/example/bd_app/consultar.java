package com.example.bd_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class consultar extends AppCompatActivity {

    EditText etIDBuscar;
    Button btnBuscar;
    Button btnRegresar;
    TextView tvResultado;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        etIDBuscar = findViewById(R.id.edit_text_id_consultar);
        btnBuscar = findViewById(R.id.btn_buscar);
        btnRegresar = findViewById(R.id.btn_regresar);
        tvResultado = findViewById(R.id.text_view_resultado);
        dbHelper = new DatabaseHelper(this);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarVenta();
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void buscarVenta() {
        String idStr = etIDBuscar.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);
        Venta venta = dbHelper.obtenerVentaPorID(id);

        if (venta != null) {
            String resultado = "ID: " + venta.getId() +
                    "\nOrigen: " + venta.getOrigen() +
                    "\nDestino: " + venta.getDestino() +
                    "\nFecha: " + venta.getFecha() +
                    "\nHora: " + venta.getHora() +
                    "\nTotal: " + venta.getTotal();
            tvResultado.setText(resultado);
        } else {
            tvResultado.setText("Venta no encontrada");
        }
    }
}
