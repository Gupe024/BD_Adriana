package com.example.bd_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class actualizar extends AppCompatActivity {

    private EditText editBuscarActual, textActualTotal;
    private Spinner spinnerOrigen, spinnerDestino;
    private Button btnBuscar, btnFecha, btnHora, btnActualizar, btnCancelar;
    private TextView textFecha, textHora;

    private DatabaseHelper dbHelper;
    private Venta venta;
    private Map<String, Integer> costos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        dbHelper = new DatabaseHelper(this);
        costos = new HashMap<>(); // Inicializa la variable

        editBuscarActual = findViewById(R.id.Editbuscar_actual);
        spinnerOrigen = findViewById(R.id.actual_origen);
        spinnerDestino = findViewById(R.id.actual_destino);
        textFecha = findViewById(R.id.Textctual_fecha);
        textHora = findViewById(R.id.Textactual_hora);
        textActualTotal = findViewById(R.id.Textactual_Total);
        btnBuscar = findViewById(R.id.btnbuscar_actual);
        btnFecha = findViewById(R.id.btnactual_fecha);
        btnHora = findViewById(R.id.btnactual_hora);
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnCancelar = findViewById(R.id.btnactual_cancelar);

        ArrayAdapter<CharSequence> adapterOrigen = ArrayAdapter.createFromResource(this,
                R.array.origenes_array, android.R.layout.simple_spinner_item);
        adapterOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapterOrigen);

        ArrayAdapter<CharSequence> adapterDestino = ArrayAdapter.createFromResource(this,
                R.array.destinos_array, android.R.layout.simple_spinner_item);
        adapterDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestino.setAdapter(adapterDestino);

        cargarCostos();
        configurarListenersSpinners(); // Configura los listeners de los spinners

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarVenta();
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePickerDialog();
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePickerDialog();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarVenta();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actualizar.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cargarCostos() {
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

    private void configurarListenersSpinners() {
        spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void actualizarTotal() {
        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();
        String claveCosto = origen + "_" + destino;

        Integer costo = costos.get(claveCosto);
        if (costo != null) {
            textActualTotal.setText(String.valueOf(costo));
        } else {
            textActualTotal.setText("0");
        }
    }

    private void buscarVenta() {
        String idStr = editBuscarActual.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);
        venta = dbHelper.obtenerVentaPorID(id);
        if (venta != null) {
            // Configurar los spinners con los valores obtenidos
            int origenPos = ((ArrayAdapter) spinnerOrigen.getAdapter()).getPosition(venta.getOrigen());
            spinnerOrigen.setSelection(origenPos);

            int destinoPos = ((ArrayAdapter) spinnerDestino.getAdapter()).getPosition(venta.getDestino());
            spinnerDestino.setSelection(destinoPos);

            textFecha.setText(venta.getFecha());
            textHora.setText(venta.getHora());
            textActualTotal.setText(String.valueOf(venta.getTotal()));
        } else {
            Toast.makeText(this, "Venta no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void mostrarTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textHora.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void actualizarVenta() {
        if (venta == null) {
            Toast.makeText(this, "No se puede actualizar una venta no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();
        String fecha = textFecha.getText().toString();
        String hora = textHora.getText().toString();
        String totalStr = textActualTotal.getText().toString();

        if (origen.isEmpty() || destino.isEmpty() || fecha.isEmpty() || hora.isEmpty() || totalStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = Double.parseDouble(totalStr);

        venta.setOrigen(origen);
        venta.setDestino(destino);
        venta.setFecha(fecha);
        venta.setHora(hora);
        venta.setTotal(total);

        dbHelper.actualizarVenta(venta);
        Toast.makeText(this, "Venta actualizada correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(actualizar.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}