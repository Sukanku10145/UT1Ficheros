package ej3b;

import java.io.FileInputStream;
import java.io.IOException;

public class ej3b {
    public static void main(String[] args) {
        // Verifica si se ha proporcionado un argumento (la ruta del archivo GIF)
        if (args.length == 0) {
            System.out.println("Por favor, proporciona la ruta del archivo GIF como argumento.");
            return;
        }

        String archivo = args[0]; // Obtiene la ruta del archivo desde los argumentos
        try (FileInputStream fis = new FileInputStream(archivo)) {
            // Crea un arreglo de bytes para leer los primeros 13 bytes (cabecera del GIF)
            byte[] cabecera = new byte[13];
            // Lee los primeros 13 bytes y verifica si se ha leído la cabecera completa
            if (fis.read(cabecera) != 13) {
                System.out.println("No es un GIF válido");
                return;
            }

            // Verifica que los primeros tres bytes correspondan a un archivo GIF ('GIF')
            if (cabecera[0] == 'G' && cabecera[1] == 'I' && cabecera[2] == 'F') {
                // Extrae la versión del GIF (por ejemplo, "87a" o "89a")
                String version = new String(new byte[]{cabecera[3], cabecera[4], cabecera[5]});
                System.out.println("Versión GIF: " + version);

                // Extrae el ancho de la imagen de los bytes correspondientes en la cabecera
                int ancho = (cabecera[7] & 0xFF) << 8 | (cabecera[6] & 0xFF);
                // Extrae el alto de la imagen de los bytes correspondientes en la cabecera
                int alto = (cabecera[9] & 0xFF) << 8 | (cabecera[8] & 0xFF);
                System.out.println("Ancho de la imagen: " + ancho + " píxeles");
                System.out.println("Alto de la imagen: " + alto + " píxeles");

                // Verifica si existe una tabla de colores global a través de un bit en la cabecera
                boolean tablaColoresGlobal = (cabecera[10] & 0x80) != 0;
                if (tablaColoresGlobal) {
                    // Calcula la cantidad de colores de la tabla global, si está presente
                    int tamanoTabla = 1 << ((cabecera[10] & 0x07) + 1);
                    System.out.println("Cantidad de colores: " + tamanoTabla);
                } else {
                    System.out.println("Cantidad de colores: Sin tabla de color global");
                }
            } else {
                System.out.println("No es un GIF válido");
            }
        } catch (IOException e) {
            // Muestra un mensaje de error en caso de que ocurra una excepción al leer el archivo
            System.out.println("Error al leer el archivo GIF: " + e.getMessage());
        }
    }
}
