package ej6;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Graba las cotizaciones obtenidas desde la web
        CotizacionManager.grabarCotizacion();
        
        // Lee y muestra todas las cotizaciones almacenadas
        CotizacionManager.leerCotizacion();

        // Ver una cotización específica en una fecha y hora dada
        Date fechaEspecifica = new Date();  // Se crea un objeto Date con la fecha y hora actuales
        String horaEspecifica = "12:00";    // Se define una hora específica para la búsqueda
        CotizacionManager.verCotizacion(fechaEspecifica, horaEspecifica);
    }
}