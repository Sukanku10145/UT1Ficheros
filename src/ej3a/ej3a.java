package ej3a;

import java.io.FileInputStream;
import java.io.IOException;

public class ej3a {
    public static void main(String[] args) {
        // Verifica si se ha proporcionado un argumento (la ruta del archivo BMP)
        if (args.length == 0) {
            System.out.println("Por favor, proporciona la ruta del archivo BMP como argumento.");
            return;
        }

        String archivo = args[0]; // Obtiene la ruta del archivo desde los argumentos
        try (FileInputStream fis = new FileInputStream(archivo)) {
            // Crea un arreglo de bytes para leer los primeros 54 bytes (cabecera del BMP)
            byte[] cabecera = new byte[54];
            // Lee los primeros 54 bytes y verifica si se ha leído la cabecera completa
            if (fis.read(cabecera) != 54) {
                System.out.println("No es un BMP válido");
                return;
            }

            // Verifica que los primeros bytes correspondan a un archivo BMP ('BM')
            if (cabecera[0] == 'B' && cabecera[1] == 'M') {
                System.out.println("Parece un BMP válido");

                // Extrae el ancho de la imagen de los bytes correspondientes en la cabecera
                int ancho = ((cabecera[21] & 0xFF) << 24) | ((cabecera[20] & 0xFF) << 16) |
                            ((cabecera[19] & 0xFF) << 8) | (cabecera[18] & 0xFF);
                // Extrae el alto de la imagen de los bytes correspondientes en la cabecera
                int alto = ((cabecera[25] & 0xFF) << 24) | ((cabecera[24] & 0xFF) << 16) |
                           ((cabecera[23] & 0xFF) << 8) | (cabecera[22] & 0xFF);
                // Extrae el tamaño de la imagen de los bytes correspondientes en la cabecera
                int tamanoImagen = ((cabecera[37] & 0xFF) << 24) | ((cabecera[36] & 0xFF) << 16) |
                                   ((cabecera[35] & 0xFF) << 8) | (cabecera[34] & 0xFF);
                // Extrae el método de compresión de los bytes correspondientes en la cabecera
                int compresion = ((cabecera[33] & 0xFF) << 24) | ((cabecera[32] & 0xFF) << 16) |
                                 ((cabecera[31] & 0xFF) << 8) | (cabecera[30] & 0xFF);

                // Muestra la información extraída de la cabecera del BMP
                System.out.println("Ancho de la imagen: " + ancho + " píxeles");
                System.out.println("Alto de la imagen: " + alto + " píxeles");
                System.out.println("Tamaño de la imagen: " + tamanoImagen + " bytes");
                System.out.println("Compresión: " + (compresion != 0 ? "Sí" : "No"));
            } else {
                System.out.println("No es un BMP válido");
            }
        } catch (IOException e) {
            // Muestra un mensaje de error en caso de que ocurra una excepción al leer el archivo
            System.out.println("Error al leer el archivo BMP: " + e.getMessage());
        }
    }
}

