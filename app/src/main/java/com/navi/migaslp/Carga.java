package com.navi.migaslp;

public class Carga {
    private int id;
    private String fecha;
    private double cantidad;
    private String notas;

    public Carga() {
    }

    public Carga(int id, String fecha, double cantidad, String notas) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.notas = notas;
    }

    public Carga(String fecha, double cantidad, String notas) {
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.notas = notas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}