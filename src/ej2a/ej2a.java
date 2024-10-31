package ej2a;

import java.io.BufferedReader; // Importa la clase BufferedReader para leer texto de entrada
import java.io.FileReader; // Importa la clase FileReader para leer archivos
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida

public class ej2a {
	public static void main(String[] args) {
		// Verifica que se ha pasado exactamente un argumento (la ruta del archivo)
		if (args.length != 1) {
			System.out.println("Uso: java -jar ej2a.jar <ruta_archivo>"); // Mensaje de uso correcto
			return; // Termina la ejecución si no se proporciona la ruta
		}

		// Guarda la ruta del archivo proporcionada como argumento
		String filePath = args[0];
		int wordCount = 0; // Inicializa el contador de palabras

		// Usa un bloque try-with-resources para asegurar el cierre automático del BufferedReader
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line; // Variable para almacenar cada línea leída
			// Lee líneas del archivo hasta que no haya más (es decir, hasta llegar al final del archivo)
			while ((line = reader.readLine()) != null) {
				// Divide la línea en palabras utilizando espacios como delimitadores
				String[] words = line.split("\\s+");
				// Suma la cantidad de palabras encontradas a la cuenta total
				wordCount += words.length;
			}
			// Imprime el número total de palabras en el archivo
			System.out.println("Número total de palabras: " + wordCount);
		} catch (IOException e) {
			// Maneja la excepción en caso de que no se pueda leer el archivo
			System.out.println("No se pudo leer el archivo: " + e.getMessage());
		}
	}
}
