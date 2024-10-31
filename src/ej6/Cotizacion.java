package ej6;

import java.io.*;
import java.util.Date;

public class Cotizacion implements Serializable {
    private String empresa; // Nombre de la empresa
    private Date fecha; // Fecha de la cotización
    private String hora; // Hora de la cotización
    private double valor; // Valor de la cotización

    // Constructor para inicializar una cotización
    public Cotizacion(String empresa, Date fecha, String hora, double valor) {
        this.empresa = empresa;
        this.fecha = fecha;
        this.hora = hora;
        this.valor = valor;
    }

    // Métodos getter para acceder a los atributos
    public String getEmpresa() {
        return empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public double getValor() {
        return valor;
    }

    // Método para representar la cotización como una cadena de texto
    @Override
    public String toString() {
        switch (empresa.length()) { // Formatear la salida en función de la longitud del nombre de la empresa
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return "Empresa: " + empresa + "			Fecha: " + fecha + "		Hora: " + hora + "		Valor: " + valor;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return "Empresa: " + empresa + "		Fecha: " + fecha + "		Hora: " + hora + "		Valor: " + valor;
            default:
                return "Empresa: " + empresa + "	Fecha: " + fecha + "		Hora: " + hora + "		Valor: " + valor;
        }
    }
}
