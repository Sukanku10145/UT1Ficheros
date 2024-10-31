package ej6;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CotizacionManager {
    private static final String FILE_NAME = "cotizaciones.dat"; // Nombre del archivo donde se almacenarán las cotizaciones
    private static final String URL = "http://www.expansion.com/mercados/cotizaciones/indices/ibex35_I.IB.html"; // URL de la página de donde se obtienen las cotizaciones

    // Método para grabar cotizaciones desde la web en un archivo
    public static void grabarCotizacion() {
        try {
            // Eliminar el archivo existente si existe para evitar datos duplicados
            File file = new File(FILE_NAME);
            if (file.exists()) {
                file.delete();
            }

            // Conectar a la URL y obtener el documento HTML
            Document doc = Jsoup.connect(URL).get();
            Element tabla = doc.selectFirst("table[id=\"listado_valores\"]"); // Seleccionar la tabla de cotizaciones
            Elements rows = tabla.select("tbody tr"); // Obtener todas las filas de la tabla

            List<Cotizacion> cotizaciones = new ArrayList<>(); // Lista para almacenar las cotizaciones

            // Recorrer cada fila y extraer la información relevante
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 10) { // Verifica que haya suficientes columnas
                    String empresa = columns.get(0).text(); // Nombre de la empresa
                    String valorTexto = columns.get(1).text(); // Último valor
                    String hora = columns.get(9).text(); // Hora de la cotización
                    Date fecha = new Date(); // Fecha actual

                    // Si el valor no está vacío, lo procesamos
                    if (!valorTexto.isEmpty()) {
                        double valor = Double.parseDouble(valorTexto.replace(",", ".")); // Convertir a double
                        Cotizacion cotizacion = new Cotizacion(empresa, fecha, hora, valor); // Crear una nueva cotización
                        cotizaciones.add(cotizacion); // Agregar la cotización a la lista
                    } else {
                        System.out.println("Valor vacío para la empresa: " + empresa); // Avisar si el valor está vacío
                    }
                }
            }

            // Guardar las cotizaciones en el archivo
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME, true))) {
                for (Cotizacion cot : cotizaciones) {
                    oos.writeObject(cot); // Escribir cada cotización en el archivo
                }
            }
            System.out.println("Cotizaciones grabadas correctamente."); // Confirmar que se grabaron las cotizaciones

        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones para errores de I/O
        }
    }

    // Método para leer y mostrar las cotizaciones almacenadas
    public static void leerCotizacion() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                Cotizacion cot = (Cotizacion) ois.readObject(); // Leer una cotización del archivo
                System.out.println(cot); // Mostrar la cotización
            }
        } catch (EOFException eof) {
            // Fin del fichero alcanzado
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejo de excepciones para errores de I/O o de clase no encontrada
        }
    }

    // Método para ver una cotización específica dada una fecha y hora
    public static void verCotizacion(Date dia, String hora) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            boolean encontrada = false; // Bandera para verificar si se encontró la cotización
            while (true) {
                Cotizacion cot = (Cotizacion) ois.readObject(); // Leer una cotización del archivo
                // Verificar si la cotización coincide con la fecha y hora especificadas
                    System.out.println(cot); // Mostrar la cotización encontrada
                    encontrada = true; // Cambiar la bandera a verdadera
                    break; // Salir del bucle si se encontró la cotización
            }
            // Si no se encontró la cotización, mostrar un mensaje
            if (!encontrada) {
                System.out.println("No se encontró la cotización para la fecha y hora especificadas.");
            }
        } catch (EOFException eof) {
            // Fin del fichero alcanzado
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Manejo de excepciones para errores de I/O o de clase no encontrada
        }
    }
}