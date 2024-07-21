package com.example.bd_app;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class vender extends AppCompatActivity {

    Spinner spinnerOrigen;
    Spinner spinnerDestino;
    Button btnFecha;
    Button btnHora;
    Button btnGuardar;
    Button btnCancelar;
    EditText ETTotal;
    TextView ETID;
    TextView TVFechaSeleccionada;
    TextView TVHoraSeleccionada;
    String fechaSeleccionada = "";
    String horaSeleccionada = "";

    private Map<String, Integer> costos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        spinnerOrigen = findViewById(R.id.spinner_origen);
        spinnerDestino = findViewById(R.id.spinner_destino);
        btnFecha = findViewById(R.id.btn_fecha);
        btnHora = findViewById(R.id.btn_hora);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);
        ETTotal = findViewById(R.id.Ttotal);
        ETID = findViewById(R.id.edit_text_id);
        TVFechaSeleccionada = findViewById(R.id.tv_fecha_seleccionada);
        TVHoraSeleccionada = findViewById(R.id.tv_hora_seleccionada);

        configurarSpinner();
        configurarListeners();
        inicializarCostos();
    }

    private void inicializarCostos() {
        costos = new HashMap<>();

        costos.put("Valladolid_Tekax", 100);
        costos.put("Valladolid_Tulum", 150);
        costos.put("Valladolid_Izamal", 200);
        costos.put("Valladolid_Sisal", 250);

        costos.put("Tekax_Valladolid", 100);
        costos.put("Tekax_Tulum", 120);
        costos.put("Tekax_Izamal", 180);
        costos.put("Tekax_Sisal", 230);

        costos.put("Tulum_Valladolid", 150);
        costos.put("Tulum_Tekax", 120);
        costos.put("Tulum_Izamal", 160);
        costos.put("Tulum_Sisal", 210);

        costos.put("Izamal_Valladolid", 200);
        costos.put("Izamal_Tekax", 180);
        costos.put("Izamal_Tulum", 160);
        costos.put("Izamal_Sisal", 190);

        costos.put("Sisal_Valladolid", 250);
        costos.put("Sisal_Tekax", 230);
        costos.put("Sisal_Tulum", 210);
        costos.put("Sisal_Izamal", 190);
    }

    private void configurarSpinner() {
        ArrayAdapter<CharSequence> adapterOrigen = ArrayAdapter.createFromResource(this,
                R.array.origenes_array, android.R.layout.simple_spinner_item);
        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapterOrigen);

        ArrayAdapter<CharSequence> adapterDestino = ArrayAdapter.createFromResource(this,
                R.array.destinos_array, android.R.layout.simple_spinner_item);
        adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestino.setAdapter(adapterDestino);

        spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actualizarTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void configurarListeners() {
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePickerDialog();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarTimePickerDialog();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mostrarDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    month1++; // Los meses empiezan en 0
                    fechaSeleccionada = dayOfMonth + "/" + month1 + "/" + year1;
                    TVFechaSeleccionada.setText(fechaSeleccionada);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void mostrarTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    horaSeleccionada = hourOfDay + ":" + minute1;
                    TVHoraSeleccionada.setText(horaSeleccionada);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void actualizarTotal() {
        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();

        Integer costo = costos.get(origen + "_" + destino);

        if (costo != null) {
            ETTotal.setText(String.valueOf(costo));
        } else {
            ETTotal.setText("0");
        }
    }

    private void guardarDatos() {
        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();
        String fecha = TVFechaSeleccionada.getText().toString();
        String hora = TVHoraSeleccionada.getText().toString();
        int total = Integer.parseInt(ETTotal.getText().toString());

        Venta venta = new Venta(origen, destino, fecha, hora, total);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.agregarVenta(venta);

        ETID.setText(String.valueOf(venta.getId()));

        Toast.makeText(vender.this, "Guardado con éxito, ID: " + venta.getId(), Toast.LENGTH_SHORT).show();

        finish();
    }
}
