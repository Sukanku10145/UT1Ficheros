package ej2b;

import java.nio.file.Files; // Importa la clase Files para manejar archivos
import java.nio.file.Paths; // Importa la clase Paths para trabajar con rutas de archivos
import java.nio.file.Path; // Importa la clase Path que representa una ruta de archivo
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida
import java.util.List; // Importa la interfaz List para manejar listas de elementos

public class ej2b {
	public static void main(String[] args) {
		// Verifica que se ha pasado exactamente un argumento (la ruta del archivo)
		if (args.length != 1) {
			System.out.println("Uso: java -jar ej2b.jar <ruta_archivo>"); // Mensaje de uso correcto
			return; // Termina la ejecución si no se proporciona la ruta
		}

		// Obtiene la ruta del archivo a partir del argumento pasado
		Path filePath = Paths.get(args[0]);
		int wordCount = 0; // Inicializa el contador de palabras

		try {
			// Lee todas las líneas del archivo y las almacena en una lista
			List<String> lines = Files.readAllLines(filePath);
			// Itera a través de cada línea del archivo
			for (String line : lines) {
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
