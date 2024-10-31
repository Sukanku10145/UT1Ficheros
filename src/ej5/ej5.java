package ej5;

import org.jsoup.Jsoup; // Importa la librería Jsoup para manipular documentos HTML
import org.jsoup.nodes.Document; // Importa la clase Document para representar un documento HTML
import org.jsoup.nodes.Element; // Importa la clase Element para representar un elemento del HTML
import org.jsoup.select.Elements; // Importa la clase Elements para representar una colección de elementos

import java.io.IOException; // Importa la clase IOException para manejar errores de entrada/salida
import java.text.SimpleDateFormat; // Importa la clase para formatear fechas
import java.util.Date; // Importa la clase Date para manejar fechas
import java.util.HashMap; // Importa la clase HashMap para almacenar cotizaciones

public class ej5 {
    public static void main(String[] args) {
        // URL de la página web desde donde se extraerán las cotizaciones
        String url = "http://www.expansion.com/mercados/cotizaciones/indices/ibex35_I.IB.html?cid=SEM23201";
        // Crear un HashMap para almacenar las cotizaciones, donde la clave es el nombre de la empresa
        HashMap<String, String[]> cotizaciones = new HashMap<>();

        try {
            // Conectar a la página y obtener el documento HTML
            Document doc = Jsoup.connect(url).get();

            // Obtener la fecha y hora actuales en formatos específicos
            String fechaHoy = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String momento = new SimpleDateFormat("HH:mm").format(new Date());

            // Seleccionar la tabla de cotizaciones usando un selector CSS
            Element tabla = doc.selectFirst("table[id=\"listado_valores\"]");
            Elements filas = tabla.select("tr"); // Obtener todas las filas de la tabla

            // Recorrer las filas de la tabla, omitiendo la cabecera
            for (int i = 1; i < filas.size(); i++) {
                Element fila = filas.get(i); // Obtener la fila actual
                Elements columnas = fila.select("td"); // Obtener todas las celdas de la fila

                // Comprobar que hay más de una columna (para evitar errores)
                if (columnas.size() > 1) {
                    // Obtener el nombre de la empresa y el valor de cotización
                    String nombreEmpresa = columnas.get(0).text(); // Ajusta según el índice correcto
                    String valorCotizacion = columnas.get(1).text(); // Ajusta según el índice correcto

                    // Guardar en el HashMap, asociando el nombre de la empresa con su fecha, momento y valor de cotización
                    cotizaciones.put(nombreEmpresa, new String[]{fechaHoy, momento, valorCotizacion});
                }
            }

            // Mostrar las cotizaciones almacenadas
            for (String empresa : cotizaciones.keySet()) {
                String[] datos = cotizaciones.get(empresa); // Obtener los datos de la empresa
                // Imprimir los datos con formateo dependiendo de la longitud del nombre de la empresa
                switch (empresa.length()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        System.out.println("Empresa: " + empresa + ",            Fecha: " + datos[0] + ",        Momento: " + datos[1] + ",        Cotización: " + datos[2]);
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        System.out.println("Empresa: " + empresa + ",        Fecha: " + datos[0] + ",        Momento: " + datos[1] + ",        Cotización: " + datos[2]);
                        break;
                    default:
                        System.out.println("Empresa: " + empresa + ",    Fecha: " + datos[0] + ",        Momento: " + datos[1] + ",        Cotización: " + datos[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejar errores de conexión e impresión de la traza de la excepción
        }
    }
}
