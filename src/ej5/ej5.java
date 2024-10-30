package ej5;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ej5 {
    public static void main(String[] args) {
        // URL de la página de cotizaciones
        String url = "http://www.expansion.com/mercados/cotizaciones/indices/ibex35_I.IB.html?ci d=SEM23201";
        
        // HashMap para almacenar las cotizaciones
        HashMap<String, String> cotizaciones = new HashMap<>();
        
        try {
            // Conectar y obtener el documento HTML
            Document doc = Jsoup.connect(url).get();

            // Seleccionar la tabla que contiene las cotizaciones
            Elements filas = doc.select("table[class^=table] tbody tr");

            // Obtener la fecha y hora actual
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String fecha = ahora.format(fechaFormatter);
            String hora = ahora.format(horaFormatter);

            // Iterar sobre las filas de la tabla
            for (Element fila : filas) {
                // Obtener el nombre de la empresa y su cotización
                Elements celdas = fila.select("td");
                if (celdas.size() > 1) {
                    String nombreEmpresa = celdas.get(0).text(); // Nombre de la empresa
                    String valorCotizacion = celdas.get(1).text(); // Valor de la cotización
                    // Almacenar en el HashMap
                    cotizaciones.put(nombreEmpresa, String.format("Fecha: %s, Hora: %s, Valor: %s", fecha, hora, valorCotizacion));
                }
            }

            // Mostrar las cotizaciones almacenadas
            for (String empresa : cotizaciones.keySet()) {
                System.out.println(empresa + ": " + cotizaciones.get(empresa));
            }

        } catch (IOException e) {
            System.out.println("Error al conectar con la página: " + e.getMessage());
        }
    }
}
