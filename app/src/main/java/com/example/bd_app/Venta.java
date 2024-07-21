package com.example.bd_app;

public class Venta {
    private int id;
    private String origen;
    private String destino;
    private String fecha;
    private String hora;
    private int total;

    public Venta(String origen, String destino, String fecha, String hora, int total) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getTotal() {
        return total;
    }
}
